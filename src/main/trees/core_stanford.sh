#!/bin/bash

core="../resources/stanford-corenlp-3.5.2.jar";
models="../resources/stanford-corenlp-3.5.2-models.jar";
xom="../resources/xom.jar";
joda="../resources/joda-time.jar";
jollyday="../resources/jollyday.jar";
ejml="ejml-0.23.jar";
class="edu.stanford.nlp.pipeline.StanfordCoreNLP";
options="tokenize,ssplit,pos,lemma,ner,parse"; #"dcoref";

input=$1;

IFS='.' read -a myarray <<< "$input"
lemma="${myarray[0]}""_lemma.txt";
parse="${myarray[0]}""_parse.txt";
dep="${myarray[0]}""_dep.txt";

java -Xmx6g -cp .:$core:$models:$xom:$joda:$jollyday:$ejml $class -annotators $options -file $input;
xmlstarlet tr lemma.xsl $input.xml > $lemma;
xmlstarlet tr parse.xsl $input.xml > $parse;
xmlstarlet tr dep.xsl $input.xml > $dep;
rm $input.xml;
