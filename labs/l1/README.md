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

## Compilation
Compile using IntelliJ IDEA using maven and type in the command line,
```bash
java -jar target/ve472l1-1.0-SNAPSHOT.jar
```



