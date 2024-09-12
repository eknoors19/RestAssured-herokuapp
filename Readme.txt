Introduction -
--------------

This automation framework is designed to test different APIs of https://restful-booker.herokuapp.com/ with help of automated scripts.



Requirements -
--------------

In order to run this automation suite, you will need following reqirements configured in your system:
1. Java 1.8 (including both JDK and JRE with version 1.8)
2. Eclipse
3. Maven



Installation -
--------------

1. Java 1.8-
	Install jdk and and jre from links given below.
	https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

	Once you have install both, update your environment variables as given below-
For user variable- create new varible with name as 'JAVA_HOME' and value as path of installed jdk. (Include path till jdk folder.

2. Eclipse-
	Install eclipse by downloading from link given below.
https://www.eclipse.org/downloads/packages/release/photon/r/eclipse-ide-java-ee-developers

3. Maven-
	Download maven bin.zip file from given link.
https://maven.apache.org/download.cgi
	Once download finished, follow instructions from given link to configure in your system.
https://docs.wso2.com/display/IS323/Installing+Apache+Maven+on+Windows




Configuration/Execution -
-------------------------

To run from testng.xml file in eclipse:-
1. Open eclipse and open downloaded project in eclipse by clicking 'Open projects from file system' option present in File menu.
2. Install testng plugin from Eclipse Marketplace given in Help menu.
3. Open project folder path in command prompt
4. run 'mvn compile'
5. run 'mvn install'
6. run 'mvn test'
7. run restassuredapitest.xml file from eclipse



Report and log file path -
--------------------------
1. Log file (generated using log4j) can be found in project root path with name as 'logs'
2. Extent report can be found in following path-
> if run from restassuredapitest.xml then report path is "./ExtentReports/"


Points to remember -
--------------------

1. Before running this automation suite, please update maven project.
2. Make sure all the plugins are installed in the pom.xml file.