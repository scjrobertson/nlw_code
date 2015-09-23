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

if [ $out = "--help" ]
then
	echo -e "\nGenerate cover text for the toy embedding algorithm.
Consists of 1000 simple, past tense active sentences."
	echo -e "1. root_out - The file name, excluding extention, of the output.
      	\tThe file is placed in nlw_code/src/main/output."
	echo -e "2. run/compile - Run or compile the source code.
	\t0: Compile\n\t\t1: Run\n"
	exit 1;
fi

javac -cp .:$SIMPLE $CPATH;
if [ $run = 1 ]
	then java -Xmx2g -cp .:$SIMPLE $COMPILE $NOUNS $VERBS $ADVERBS $ADJECTIVES $PREPOSITIONS  > $OUTPUT/$out.txt;
	elif [ $run = 2 ]
	then java -Xmx2g -cp .:$SIMPLE $COMPILE $NOUNS $VERBS $ADVERBS $ADJECTIVES $PREPOSITIONS;
fi

