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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.spdy.StreamException;
import org.eclipse.jetty.spdy.api.SettingsInfo;
import org.eclipse.jetty.spdy.frames.SettingsFrame;

public class SettingsBodyParser extends ControlFrameBodyParser
{
    private final Map<SettingsInfo.Key, Integer> settings = new HashMap<>();
    private final ControlFrameParser controlFrameParser;
    private State state = State.COUNT;
    private int cursor;
    private int count;
    private SettingsInfo.Key key;
    private int value;

    public SettingsBodyParser(ControlFrameParser controlFrameParser)
    {
        this.controlFrameParser = controlFrameParser;
    }

    @Override
    public boolean parse(ByteBuffer buffer) throws StreamException
    {
        while (buffer.hasRemaining())
        {
            switch (state)
            {
                case COUNT:
                {
                    if (buffer.remaining() >= 4)
                    {
                        count = buffer.getInt();
                        state = State.KEY;
                    }
                    else
                    {
                        state = State.COUNT_BYTES;
                        cursor = 4;
                    }
                    break;
                }
                case COUNT_BYTES:
                {
                    byte currByte = buffer.get();
                    --cursor;
                    count += (currByte & 0xFF) << 8 * cursor;
                    if (cursor == 0)
                    {
                        state = State.KEY;
                    }
                    break;
                }
                case KEY:
                {
                    if (buffer.remaining() >= 4)
                    {
                        key = new SettingsInfo.Key(buffer.getInt());
                        state = State.VALUE;
                    }
                    else
                    {
                        state = State.KEY_BYTES;
                        cursor = 4;
                    }
                    break;
                }
                case KEY_BYTES:
                {
                    byte currByte = buffer.get();
                    --cursor;
                    value += (currByte & 0xFF) << 8 * cursor;
                    if (cursor == 0)
                    {
                        key = new SettingsInfo.Key(value);
                        state = State.VALUE;
                    }
                    break;
                }
                case VALUE:
                {
                    if (buffer.remaining() >= 4)
                    {
                        value = buffer.getInt();
                        if (onPair())
                            return true;
                    }
                    else
                    {
                        state = State.VALUE_BYTES;
                        cursor = 4;
                        value = 0;
                    }
                    break;
                }
                case VALUE_BYTES:
                {
                    byte currByte = buffer.get();
                    --cursor;
                    value += (currByte & 0xFF) << 8 * cursor;
                    if (cursor == 0)
                    {
                        if (onPair())
                            return true;
                    }
                    break;
                }
                default:
                {
                    throw new IllegalStateException();
                }
            }
        }
        return false;
    }

    private boolean onPair()
    {
        settings.put(key, value);
        state = State.KEY;
        key = null;
        value = 0;
        --count;
        if (count == 0)
        {
            onSettings();
            return true;
        }
        return false;
    }

    private void onSettings()
    {
        SettingsFrame frame = new SettingsFrame(controlFrameParser.getVersion(), controlFrameParser.getFlags(), new HashMap<>(settings));
        controlFrameParser.onControlFrame(frame);
        reset();
    }

    private void reset()
    {
        settings.clear();
        state = State.COUNT;
        cursor = 0;
        count = 0;
        key = null;
        value = 0;
    }

    private enum State
    {
        COUNT, COUNT_BYTES, KEY, KEY_BYTES, VALUE, VALUE_BYTES
    }
}
