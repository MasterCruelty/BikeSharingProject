![License](https://img.shields.io/github/license/MasterCruelty/BikeSharingProject)
[![image](https://img.shields.io/github/stars/MasterCruelty/BikeSharingProject)](https://github.com/MasterCruelty/BikeSharingProject/stargazers)
[![image](https://img.shields.io/github/forks/MasterCruelty/BikeSharingProject)](https://github.com/MasterCruelty/BikeSharingProject/network/members)
![CodeSize](https://img.shields.io/github/languages/code-size/MasterCruelty/BikeSharingProject)
[![image](https://img.shields.io/github/issues/MasterCruelty/BikeSharingProject)](https://github.com/MasterCruelty/BikeSharingProject/issues)
![image](https://img.shields.io/github/languages/top/MasterCruelty/BikeSharingProject)
![image](https://img.shields.io/github/commit-activity/w/MasterCruelty/BikeSharingProject)

# BikeSharingProject
### What is it?
This is a Bike sharing simulator. You register and buy a subscription and you rent a bike, then you give back to the system and you pay.
### Requirements
You have to install MySQL DBMS to import my dumps. Connector file .jar is already in this repo.<br>
If you would like to open the .mdj file you need to install StarUML.

---

### Usage
#### compile
<code>javac -cp libs/mysql-connector-java-8.0.24.jar:. SistemaBikeSharing.java</code>
#### Exec
<code>java -cp libs/mysql-connector-java-8.0.24.jar:. SistemaBikeSharing</code>
#### Tests
<code>javac -cp libs/junit-4.13.2.jar:Test/Test*.java</code><br>
<code>java -cp libs/junit-4.13.2.jar:libs/hamcrest-core1.3.jar:Test/ org.junit.runner.JUnitCore "fileclasstest"</code>

---
### Language
The project is all in Italian(source comments, diagrams and relation), just saying.
