/*
 * Copyright (c) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.eclipse.jetty.spdy.generator;

import java.nio.ByteBuffer;
import java.util.Map;

import org.eclipse.jetty.spdy.StreamException;
import org.eclipse.jetty.spdy.api.SettingsInfo;
import org.eclipse.jetty.spdy.frames.ControlFrame;
import org.eclipse.jetty.spdy.frames.SettingsFrame;

public class SettingsGenerator extends ControlFrameGenerator
{
    @Override
    public ByteBuffer generate(ControlFrame frame) throws StreamException
    {
        SettingsFrame settings = (SettingsFrame)frame;

        Map<SettingsInfo.Key, Integer> pairs = settings.getSettings();
        int size = pairs.size();
        int frameBodyLength = 4 + 8 * size;
        int totalLength = ControlFrame.HEADER_LENGTH + frameBodyLength;
        ByteBuffer buffer = ByteBuffer.allocate(totalLength);
        generateControlFrameHeader(settings, frameBodyLength, buffer);

        buffer.putInt(size);

        for (Map.Entry<SettingsInfo.Key, Integer> entry : pairs.entrySet())
        {
            buffer.putInt(entry.getKey().getKey());
            buffer.putInt(entry.getValue());
        }

        buffer.flip();
        return buffer;
    }
}
