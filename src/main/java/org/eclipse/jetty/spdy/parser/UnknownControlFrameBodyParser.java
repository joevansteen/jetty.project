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

package org.eclipse.jetty.spdy.parser;

import java.nio.ByteBuffer;

import org.eclipse.jetty.spdy.StreamException;

public class UnknownControlFrameBodyParser extends ControlFrameBodyParser
{
    private int remaining;

    public UnknownControlFrameBodyParser(ControlFrameParser controlFrameParser)
    {
        this.remaining = controlFrameParser.getLength();
    }

    @Override
    public boolean parse(ByteBuffer buffer) throws StreamException
    {
        int consumed = Math.min(remaining, buffer.remaining());
        buffer.position(buffer.position() + consumed);
        remaining -= consumed;
        return remaining == 0;
    }
}
