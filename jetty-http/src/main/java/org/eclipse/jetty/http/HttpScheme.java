//
// ========================================================================
// Copyright (c) 1995-2020 Mort Bay Consulting Pty Ltd and others.
//
// This program and the accompanying materials are made available under
// the terms of the Eclipse Public License 2.0 which is available at
// https://www.eclipse.org/legal/epl-2.0
//
// This Source Code may also be made available under the following
// Secondary Licenses when the conditions for such availability set
// forth in the Eclipse Public License, v. 2.0 are satisfied:
// the Apache License v2.0 which is available at
// https://www.apache.org/licenses/LICENSE-2.0
//
// SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
// ========================================================================
//

package org.eclipse.jetty.http;

import java.nio.ByteBuffer;

import org.eclipse.jetty.util.ArrayTrie;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Trie;

/**
 * HTTP and WebSocket Schemes
 */
public enum HttpScheme
{
    HTTP("http", 80),
    HTTPS("https", 443),
    WS("ws", 80),
    WSS("wss", 443);

    public static final Trie<HttpScheme> CACHE = new ArrayTrie<HttpScheme>();

    static
    {
        for (HttpScheme version : HttpScheme.values())
        {
            CACHE.put(version.asString(), version);
        }
    }

    private final String _string;
    private final ByteBuffer _buffer;
    private final int _defaultPort;

    HttpScheme(String s, int port)
    {
        _string = s;
        _buffer = BufferUtil.toBuffer(s);
        _defaultPort = port;
    }

    public ByteBuffer asByteBuffer()
    {
        return _buffer.asReadOnlyBuffer();
    }

    public boolean is(String s)
    {
        return _string.equalsIgnoreCase(s);
    }

    public String asString()
    {
        return _string;
    }

    public int getDefaultPort()
    {
        return _defaultPort;
    }

    public int normalizePort(int port)
    {
        return port == _defaultPort ? 0 : port;
    }

    @Override
    public String toString()
    {
        return _string;
    }

    public static int getDefaultPort(String scheme)
    {
        HttpScheme httpScheme = scheme == null ? null : CACHE.get(scheme);
        return httpScheme == null ? HTTP.getDefaultPort() : httpScheme.getDefaultPort();
    }

    public static int normalizePort(String scheme, int port)
    {
        HttpScheme httpScheme = scheme == null ? null : CACHE.get(scheme);
        return httpScheme == null ? port : httpScheme.normalizePort(port);
    }
}
