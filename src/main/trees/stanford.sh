#!/bin/bash

input=$1;
output=$2;

IFS='.' read -a myarray <<< "$output"
output_conll="${myarray[0]}""_conll.txt";

java -mx200m -cp ../resources/stanford-parser.jar edu.stanford.nlp.parser.lexparser.LexicalizedParser -retainTmpSubcategories -originalDependencies -outputFormat "penn" -outputFormatOptions "treeDependencies" ../resources/englishPCFG.ser.gz $input > $output;

java -mx200m -cp ../resources/stanford-parser.jar edu.stanford.nlp.trees.EnglishGrammaticalStructure -treeFile $output -conllx -keepPunc > $output_conll;
