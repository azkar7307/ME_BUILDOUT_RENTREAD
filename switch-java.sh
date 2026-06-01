#!/bin/bash

echo "Select Java Version:"
echo "1) JDK 11.0.31"
echo "2) JDK 17"
echo "3) JDK 25.0.2"

read choice

case $choice in
  1)
    export JAVA_HOME="/c/Program Files/Java/jdk-11.0.31"
    ;;
  2)
      export JAVA_HOME="/c/Program Files/Java/jdk-17"
      ;;
  3)
    export JAVA_HOME="/c/Program Files/Java/jdk-25.0.2"
    ;;
  *)
    echo "Invalid choice"
    exit 1
    ;;
esac

export PATH=$JAVA_HOME/bin:$PATH

printf "\nexecuting 'java -version' command to confirm\n"
java -version

printf "\nalso executin 'javac -version' command\n"
javac -version