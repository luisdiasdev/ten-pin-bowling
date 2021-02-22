# 10-pin Bowling Result Viewer

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=luisdiasdev_ten-pin-bowling&metric=coverage)](https://sonarcloud.io/dashboard?id=luisdiasdev_ten-pin-bowling) 
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=luisdiasdev_ten-pin-bowling&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=luisdiasdev_ten-pin-bowling) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=luisdiasdev_ten-pin-bowling&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=luisdiasdev_ten-pin-bowling)

The application goal is to read a text file containing the results of several players bowling 10 frames each, then print a nicely formatted table showing the results of each player.

![](images/ten-pin-bowling.gif)

## Usage
### Compiling

#### Prerequisites

- Java Development Kit (JDK) at least version 1.8 (recommended: OpenJDK)

#### Building

In the root folder of the repository, we can run the following command in a terminal in order to build the application:

##### Linux
```sh
./gradlew clean build
```
##### Windows
```bat
.\gradlew.bat clean build
```

Now the Gradle Wrapper will download the Gradle binaries in order to resolve all dependencies and build our application.

#### Running

After the build process is completed successfully, we should have the bundled application at the following location: `<rootDir>/build/libs/ten-pin-bowling-1.0.0-all.jar`

So, if you're not familiar with running Java `jar`s, we can do so by running the following command (assuming `java` binary is on your system's `PATH` and the working directory is the root of this repo):

```sh
java -jar build/libs/ten-pin-bowling-1.0.0-all.jar
```

If everything went well, you should see something like this on your terminal:

```
Missing required parameters: '<outputType>', '<inputFile>'
Usage: processor <outputType> <inputFile>
      <outputType>   The type of output: CONSOLE or FILE
      <inputFile>    The input file containing the results of the bowling game
```

In the following table is brief explanation of the input parameters:

| Parameter Name | Description                                                                                                  | Possible Values                              |
|----------------|--------------------------------------------------------------------------------------------------------------|----------------------------------------------|
| outputType     | Used to decide whether to print the results table to the console or to a file named `output.txt` at the current directory | `CONSOLE`, `FILE`                            |
| inputFile      | The input file containing the results of the bowling game                                                    | any valid file path on your operating system |


#### Example

```sh
java -jar build/libs/ten-pin-bowling-1.0.0-all.jar CONSOLE input.txt
```

### Running tests

#### Unit Tests

To execute only unit tests we can use the following command:

##### Linux
```sh
./gradlew test -PdisableIntegrationTests
```

##### Windows
```bat
.\gradlew.bat test -PdisableIntegrationTests
```

#### Integration Tests

To execute only integration tests we use the following command:

##### Linux
```sh
./gradlew integrationTest
```

##### Windows
```bat
.\gradlew.bat integrationTest
```

## Technologies

The most important technologies used for this application are:

- [Java](https://www.java.com)
- [Gradle](https://gradle.org)
- [JUnit 5](https://junit.org/junit5)
- [Mockito](https://site.mockito.org)
- [AssertJ](https://assertj.github.io/doc/)

## Author

Luis Gustavo de Oliveira Dias 

[<image src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white">](https://www.linkedin.com/in/luisgustavodias94)