# DO NOT EDIT - See: https://www.eclipse.org/jetty/documentation/current/startup-modules.html

[description]
Download and JAAS Demo Webapp.

[tags]
demo
webapp

[depends]
deploy

[files]
maven://org.eclipse.jetty.tests/test-jaas-webapp/${jetty.version}/jar/config|extract:/
maven://org.eclipse.jetty.tests/test-jaas-webapp/${jetty.version}/war|webapps/test-jaas.war
