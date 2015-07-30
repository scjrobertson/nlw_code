#!/bin/bash

core="../resources/stanford-corenlp-3.5.2.jar";
models="../resources/stanford-corenlp-3.5.2-models.jar";
xom="../resources/xom.jar";
joda="../resources/joda-time.jar";
jollyday="../resources/jollyday.jar";
ejml="ejml-0.23.jar";
class="edu.stanford.nlp.pipeline.StanfordCoreNLP";
options="tokenize,ssplit,pos,lemma,ner,parse"; #"dcoref";

read -p "Input : " input;

java -Xmx2g -cp .:$core:$models:$xom:$joda:$jollyday:$ejml $class -annotators $options -file $input;
XMLTreeEdit $input.xml & 
