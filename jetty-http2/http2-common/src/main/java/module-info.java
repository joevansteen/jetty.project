//
//  ========================================================================
//  Copyright (c) 1995-2019 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

module org.eclipse.jetty.http2.common
{
    exports org.eclipse.jetty.http2;
    exports org.eclipse.jetty.http2.api;
    exports org.eclipse.jetty.http2.api.server;
    exports org.eclipse.jetty.http2.frames;
    exports org.eclipse.jetty.http2.generator;
    exports org.eclipse.jetty.http2.parser;

    requires org.eclipse.jetty.http;
    requires org.eclipse.jetty.http2.hpack;
    requires org.eclipse.jetty.io;
    requires org.eclipse.jetty.util;
}