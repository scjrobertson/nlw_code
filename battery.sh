#!/bin/bash

HAMCREST="src/test/resources/hamcrest-core-1.3.jar";
JUNIT="src/test/resources/junit-4.12.jar";

input="src.test.$1";

javac -cp .:$HAMCREST:$JUNIT src/test/$1/*.java;
java -cp .:$HAMCREST:$JUNIT src/test/$1/TestRunner;
