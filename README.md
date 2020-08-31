# MicroSpringBoot

A Solution for AREP Workshop "Micro Spring boot - server"
[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://microspringarep.herokuapp.com/)
[![CircleCI](https://circleci.com/gh/AndresMarcelo7/MicroSpringboot.svg?style=svg)](https://circleci.com/gh/AndresMarcelo7/MicroSpringboot)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
### Prerequisites

The things you need in order to run this proyect on your computer are:
- Maven
- Git  
- Java
- Heroku CLI (If you want to deploy this app by yourself)

Make sure you have this installed with the commands
```
mvn --version
```
```
git --version
```
```
java -showversion
```
and 
```
heroku -v
```

This project was developed with Java V1.8

### Installing
Please clone this repository on your computer with the command:

```
git clone https://github.com/AndresMarcelo7/MicroSpringboot
```
Once finished, you must navigate into the project and execute the command 'mvn package' like this:
```
C:\Users\User > cd /TallerHerokuAREP
```
```
C:\Users\User > mvn package
```
Here, mvn package Builds the project and packages the resulting JAR file into the target directory.

## Running the tests

if you want to run the tests again please run the following command:

```
C:\Users\User > mvn test
```
## Run

If you want to locally deploy the app use the following command:
```
 java -cp target/classes edu.eci.arep.microspringboot.MicroSpringBoot edu.eci.arep.microspringboot.controllers.TestController
```
Then go to http://localhost:36000/ in order to see the index page

the available springboot endpoints are the following:
- /SpringBoot
- /Pi
- /webapp

The application is deployed with heroku and the link is at the top of this document (Heroku button)