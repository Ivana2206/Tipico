test-automation-Tipico Project:

java Tipico Project for UI Automation testing.

Concepts Included :

TestNG framework Page Object pattern Common web page interaction methods

Tools :

Maven Selenium Webdriver Eclipse JDK 11 chromedriver

Requirements

In order to utilise this project you need to have the following installed locally:

Chrome (UI tests use Chrome by default) and chromedriver (which is already added inside the project) JDK 11 Maven 3

Usage

Download the zip or clone the Git repository. Unzip the zip file (if you downloaded one). Open Command Prompt and Change directory (cd) to folder containing pom.xml

To run test navigate to Tipico directory and run command in cmd (Command Prompt):

mvn clean test

Another option to run the test is directly from Eclipse ide:

Download the zip file on desired location open Eclipse or other ide setting the path to point on downloaded zip file.

In Eclipse : Go to File -> Import -> General -> Existing Projects into Workspace -> Next -> Select root directory -> Finsih

Run test as TestNg Test executing testng.xml or TestPage.java class

Reporting

Report for this module is written into /Report directory after a successful run. UI tests result in a HTML report. In the case of test failures, a screen-shoot of the UI at the point of failure is embedded into the report.
