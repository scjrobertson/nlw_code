#!/bin/bash

SIMPLENLG="https://simplenlg.googlecode.com/files/simplenlg-v442.zip";
CORENLP="nlp.stanford.edu/software/stanford-corenlp-full-2015-04-20.zip";
ZIP_NLG="simplenlg-v442.zip";
ZIP_CORE="stanford-corenlp-full-2015-04-20.zip";
JAR_NLG="simplenlg-v4.4.2.jar";
DIR_NLG="simplenlg-v442"
DIR_CORE="stanford-corenlp-full-2015-04-20";

core="stanford-corenlp-3.5.2.jar";
models="stanford-corenlp-3.5.2-models.jar";
xom="xom.jar";
joda="joda-time.jar";
jollyday="jollyday.jar";
ejml="ejml-0.23.jar";

wget -r --tries=2 -nd $SIMPLENLG
wget -r --tries=2 -nd $CORENLP

file-roler -h $ZIP_NLG ;
sleep 2;
mv $DIR_NLG/$JAR_NLG . ;

file-roller -h $ZIP_CORE;
sleep 2;
mv $DIR_CORE/$core . ;
mv $DIR_CORE/$models . ;
mv $DIR_CORE/$xom . ;
mv $DIR_CORE/$joda . ;
mv $DIR_CORE/$jollyday . ;
mv $DIR_CORE/$ejml . ;
rm -r $DIR_CORE ;wget -r --tries=2 $SIMPLENLG -o log
