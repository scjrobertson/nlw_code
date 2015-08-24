#!/bin/bash

CPATH="src/main/nlg/NLG.java"
COMPILE="src.main.nlg.NLG";
SIMPLE="src/main/resources/simplenlg-v4.4.2.jar"
OUTPUT="src/main/output";

out=$1;
run=$2;

NOUNS="src/main/nlg/nouns.txt";
VERBS="src/main/nlg/verbs.txt";
ADVERBS="src/main/nlg/verbs.txt";
ADJECTIVES="src/main/nlg/adjectives.txt";
PREPOSITIONS="src/main/nlg/prepositions.txt";
PRONOUNS="src/main/nlg/pronouns.txt";

javac -cp .:$SIMPLE $CPATH;
if [ $run = 1 ]
	then java -Xmx6g -cp .:$SIMPLE $COMPILE $NOUNS $VERBS $ADVERBS $ADJECTIVES $PREPOSITIONS  > $OUTPUT/$out.txt;
	elif [ $run = 2 ]
	then java -Xmx6g -cp .:$SIMPLE $COMPILE $NOUNS $VERBS $ADVERBS $ADJECTIVES $PREPOSITIONS;
fi

