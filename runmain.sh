#!/bin/bash

SNLG="src/main/resources/simplenlg-v4.4.2.jar";
CPATH="src/main/trees/RunMain.java"
COMPILE="src.main.trees.RunMain";

input=$1;

inpath="src/main/output/$input";
parse="$inpath/$input""_parse.txt";
dep="$inpath/$input""_dep.txt";
lemma="$inpath/$input""_lemma.txt";

echo $parse
echo $dep
echo $lemma
javac -cp .:$SNLG $CPATH;
java -Xmx6g -cp .:$SNLG $COMPILE $parse $dep $lemma;
