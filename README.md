# T-JAV-501-PAR-5-1-dashboard-amyr.el-zenary


___The Project:___

The purpose of this project is to go further with Java through the creation of a dashboard app.
The Dashboard app offers the following features:
- The user can register on the application in order to obtain an account
-The registered user then can confirm their enrollment on the application before being able to use it: 
Authentication / Identification
- The application then asks the authenticated user to subscribe to the Services
- Each Service offers Widgets
- The authenticated user can compose his Dashboard by inserting previously configured widget instances


_**Project structure**_

The project is composed of two main folders, first one is the Front-end named: react-social and the second one is the Back-end named: security.

The Front-end language is React, it is separated in multiple parts with the body in the folder Public 
and the skeleton in Src:  each folder and files correspond a domain. Example Folder User regroup the
login.js and .ccs for all the web display of the user login.
The Front-end is managed by "Npm", npm is the package manager for the Node JavaScript platform.

The Back-end language is Java, is also separated in multiple parts; with the main in Dashboard/security/src/main/java
and each configurations are dispatched in different folder like a body skeleton, by example:
the folder "controller" contain every route of the project that are useful for each front page display on the web services.
The Back-end is managed by "Gradle", Gradle is a build automation tool for multi-language software development. 


_**How to run the project**_

1. Download the project : git clone the project on Github with the command line :
- git clone git@github.com:EpitechMscProPromo2025/T-JAV-501-PAR-5-1-dashboard-amyr.el-zenary.git

2. Open two terminal in project folder

3. In the second terminal move to the security folder, then in this same terminal:
- Download Gradle (https://gradle.org/install/)
- Make sure to have Java17 downloader (not java 18 or 19 it must be 17), to check this you can run the command line: java -version
- Download Tomcat9: (https://tomcat.apache.org/download-90.cgi)
- Download MariaDB: (https://mariadb.com/fr/downloads/)
- Create a dashboard database with MariaDB: - Run Mariadb with the command line: mysql -u root -p
                                            - Run the command line in MariaDB: CREATE dashboard;
                                             - Leave MariaDB (ctrl +c)                                      
- Run the command line to run the Back-end: ./gradlew bootRun
- - Keep this terminal open with Gradle running (to stop if you can use ctrl + c)

4. In the first terminal move to the react-social folder, then in this same terminal:
- Download Npm with the command line: npm install
- Run the command line to run the Front-end: npm start
- Keep this terminal open with Npm running (to stop if you can use ctrl + c)

5. Success! An Internet page will be automatically open on the Dashboard Application

6. If there are no errors on both terminals with Npm and Gradle running but the Internet page did not automatically open then:
- Open an internet page and enter the url: http://localhost:3000/


_**Availabe Services and widgets**_


| Services    |       widgets 1      |        widgets 2      |       widgets 3 |
| :---------- | :------------------: | :-------------------: |---------------: |
| Ringtone    |     Download song    |      Listen song      |                 |
| Weather     |     humidity wind    |         Cloud         |            Wind |
| Steam       | Nb of players online |                       |                 |
| Deezer      |  Cover albulm title  |  Title album singer   |          Singer |
| News        |         News         |             News Area |                 |
| Youtube     |      Search video    |                       |                 |
| Exchange    |  currency conversion |                       |                 |
| Timezone    |   Time of the zone   |                       |                 |
| Coronavirus |   Number of case     |       Number of death |                 | 

