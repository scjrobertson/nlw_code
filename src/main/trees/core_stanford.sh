#!/bin/bash

core="../resources/stanford-corenlp-3.5.2.jar";
models="../resources/stanford-corenlp-3.5.2-models.jar";
xom="../resources/xom.jar";
joda="../resources/joda-time.jar";
jollyday="../resources/jollyday.jar";
ejml="../resources/ejml-0.23.jar";
class="edu.stanford.nlp.pipeline.StanfordCoreNLP";
options="tokenize,ssplit,pos,lemma,ner,parse"; #"dcoref";

input=$1;

IFS='/' read -a path <<< "$input"
IFS="." read -a myarray <<< "${path[2]}"
file=${myarray[0]}.${myarray[1]};
root=${myarray[0]};
path=${path[0]}/${path[1]}/$root;
lemma="$root""_lemma.txt";
parse="$root""_parse.txt";
dep="$root""_dep.txt";
xml="$root"".xml";

rm -r $path;
mkdir -p $path;

java -Xmx6g -cp .:$core:$models:$xom:$joda:$jollyday:$ejml $class -annotators $options -file $input;
mv $file.xml $path;
xmlstarlet tr lemma.xsl $path/$file.xml > $path/$lemma;
xmlstarlet tr parse.xsl $path/$file.xml > $path/$parse;
xmlstarlet tr dep.xsl $path/$file.xml > $path/$dep;
mv $path/$file.xml $path/$xml;
