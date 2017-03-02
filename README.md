# java-jersey-neo4j-test

This is small test repository made to hunt down an issue with shutting down Neo4J embedded in a Jersey web app running on Tomcat. 

The issue is that when a Neo4J db connection is started with a remote shell on some port (5555 in this case), the port is not released on shutdown, but this is probably caused by a problem earlier issue in the shutdown procedure because Tomcat warns about a thread not shutting down, which happens even if not remote shell connection is started. 

### Running

Prerequisite: you need to have Tomcat running on default port 8080. Otherwise, adjust the build information in pom.xml.

1. clone repo
2. change to repo dir
3. run mvn tomcat7:deploy

This probably leads to a test failure in `NeoRemoteShellTest.java` because it doesn't properly shut down and release the remote shell port. To work around this, comment out the final assert in `testPort` that the port is available.
