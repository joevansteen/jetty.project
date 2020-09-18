# DO NOT EDIT - See: https://www.eclipse.org/jetty/documentation/current/startup-modules.html

[description]
A meta module to enable all demo modules.

[tags]
demo
webapp

[depends]
http
https
http2
work
test-keystore
demo-async-rest
demo-jaas
demo-jndi
demo-moved-context
demo-rewrite
demo-spec
demo-test

[files]
maven://org.eclipse.jetty.tests/demo-base-webapp/${jetty.version}/jar|${jetty.base}/

[ini-template]
# Websocket chat examples needs websocket enabled
# Don't start for all contexts (set to true in test.xml context)
org.eclipse.jetty.websocket.jsr356=false

