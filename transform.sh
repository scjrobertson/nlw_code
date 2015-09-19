#!/bin/bash

CPATH="src/main/trees/RunMain.java";
CPATH_ATTACK="src/main/trees/Attack.java";
COMPILE="src.main.trees.RunMain";
COMPILE_ATTACK="src.main.trees.Attack";
RESOURCES="src/main/resources";
TREES="src/main/trees";
OUTPATH="src/main/output";

core="$RESOURCES/stanford-corenlp-3.5.2.jar";
models="$RESOURCES/stanford-corenlp-3.5.2-models.jar";
xom="$RESOURCES/xom.jar";
joda="$RESOURCES/joda-time.jar";
jollyday="$RESOURCES/jollyday.jar";
ejml="$RESOURCES/ejml-0.23.jar";
class="edu.stanford.nlp.pipeline.StanfordCoreNLP";
options="tokenize,ssplit,pos,lemma,ner,parse"; #"dcoref";
lm_path="$TREES/lemma.xsl";
pm_path="$TREES/parse.xsl";
dp_path="$TREES/dep.xsl";

stanford_parse () {
	java -Xmx6g -cp .:$core:$models:$xom:$joda:$jollyday:$ejml $class -annotators $options -file $1;
	mv $2.xml $3;
	xmlstarlet tr $lm_path $3/$2.xml > $3/$4;
	xmlstarlet tr $pm_path $3/$2.xml > $3/$5;
	xmlstarlet tr $dp_path $3/$2.xml > $3/$6;
	mv $3/$2.xml $3/$7;
}

delete_existing () {
	rm -r $1;
	mkdir -p $1;
}

transform="${@:1}";

root_in="perfect";
file_in=$OUTPATH/$root_in.txt;
path_in="$OUTPATH/$root_in";
parse_in="$root_in""_parse.txt";
dep_in="$root_in""_dep.txt";
lemma_in="$root_in""_lemma.txt";
xml_in="$root_in.xml";

echo $transform > $file_in;
delete_existing $OUTPATH/$root_in;
stanford_parse $file_in $root_in.txt $path_in $lemma_in $parse_in $dep_in $xml_in;
