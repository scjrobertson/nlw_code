#!/bin/bash

CPATH="src/main/trees/RunMain.java"
COMPILE="src.main.trees.RunMain";

input=$1;
run=$2;

inpath="src/main/output/$input";
parse="$inpath/$input""_parse.txt";
dep="$inpath/$input""_dep.txt";
lemma="$inpath/$input""_lemma.txt";

javac $CPATH;
if [ $run = 1 ]
	then java -Xmx6g $COMPILE $parse $dep $lemma;
fi
