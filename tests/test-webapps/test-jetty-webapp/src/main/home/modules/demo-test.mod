# DO NOT EDIT - See: https://www.eclipse.org/jetty/documentation/current/startup-modules.html

[description]
Download and deploy the Test Spec webapp demo.

[tags]
demo
webapp

[depends]
deploy
jdbc
jsp
annotations
test-realm
ext
servlets
websocket-javax
websocket-jetty

[files]
maven://org.eclipse.jetty/test-jetty-webapp/${jetty.version}/jar/config|extract:/
maven://org.eclipse.jetty/test-jetty-webapp/${jetty.version}/war|webapps/test.war

