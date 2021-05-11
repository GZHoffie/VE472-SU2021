# VE472 Lab 1 Java Introduction
## Java Installation
On Linux, we can install Java 8 on your computer using the following lines
```bash
# Install jdk8
sudo apt-get install openjdk-8-jdk-headless -qq > /dev/null

# Set environment variable JAVA_HOME
echo "export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64" >> ~/.bashrc
source ~/.bashrc
sudo update-alternatives --set java /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java
```
Or you can follow the steps on the lab manual. You can then check whether installation has been completed with `java -version`.

## Maven Installation

```bash
wget https://mirrors.ocf.berkeley.edu/apache/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.tar.gz
tar xzvf apache-maven-3.8.1-bin.tar.gz

# Set PATH environment variable
cd ./apache-maven-3.8.1/bin
echo "export PATH=$(pwd):$PATH" >> ~/.bashrc
source ~/.bashrc
```

Confirm installation with `mvn -v`

## Compilation and Running

Compile using IntelliJ IDEA using maven and type in the command line,

```bash
java -jar target/ve472l1-1.0-SNAPSHOT.jar --hall config --query input/test.in
```

## Trouble shooting

1. When using IntelliJ IDEA to build a maven project and meeting

   ```
   No goals have been specified for this build.
   ```
   bug, add the following line after `<build>`
   
   ```xml
   <defaultGoal>package</defaultGoal>
   ```
   which would give you the same effect as `mvn package` when building.

1. When we want to add a dependency, for example `Apache Commons CLI`, then we can search on [mvnrepository](mvnrepository.com) for the package, find the maven configuration, then copy and paste the configuration into the `<dependencies>` block in `pom.xml`.

   For example, on this [website](https://mvnrepository.com/artifact/commons-cli/commons-cli/1.4) we can find the configuration to Apache Common CLI 1.4. Copying it into the file gives us
   
   ```xml
       <dependencies>
           <!-- https://mvnrepository.com/artifact/commons-cli/commons-cli -->
           <dependency>
               <groupId>commons-cli</groupId>
               <artifactId>commons-cli</artifactId>
               <version>1.4</version>
           </dependency>
       </dependencies>
   ```
   
   If you are using IntelliJ IDEA, a `load maven changes` button would appear, and clicking it would automatically install the package for you. You can find the library in `External Libraries` under your project.



