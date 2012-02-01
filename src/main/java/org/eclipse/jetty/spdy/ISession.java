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

package org.eclipse.jetty.spdy;

import java.nio.ByteBuffer;

import org.eclipse.jetty.spdy.api.DataInfo;
import org.eclipse.jetty.spdy.api.Session;
import org.eclipse.jetty.spdy.frames.ControlFrame;

public interface ISession extends Session
{
    public void control(IStream stream, ControlFrame frame) throws StreamException;

    public void data(IStream stream, DataInfo dataInfo);

    public interface Controller
    {
        public int write(ByteBuffer buffer, Handler handler);

        public void close(boolean onlyOutput);

        public interface Handler
        {
            public void complete();
        }
    }
}
