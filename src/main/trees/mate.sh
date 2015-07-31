#!/bin/bash

stanford="../resources/stanford-parser.jar";
anna="../resources/anna-3.3.jar";
trans="../resources/transition-1.30.jar";
model_lem="../resources/model.lemmatizer.model";
model_joint="../resources/per-eng-S2b-40.mdl";

input=$1;
output=$2;

IFS='.' read -a myarray <<< "$output"
output_toke="${myarray[0]}""_toke.txt";
output_anna="${myarray[0]}""_anna.txt";
output_lem="${myarray[0]}""_lem.txt";
output_joint="${myarray[0]}""_joint.txt";

java -cp $stanford edu.stanford.nlp.process.DocumentPreprocessor $input > $output_toke;
java -cp $anna is2.util.Split $output_toke > $output_anna;
java -Xmx2G -cp $anna is2.lemmatizer.Lemmatizer -model $model_lem -test $output_anna -out  $output_lem;
java -Xmx6g -cp $trans is2.transitionS2a.Parser -model $model_joint -test $output_lem -out $output_joint;

#cat $output_joint | awk 'NF==0{print ""} NF{print $1, $4, $4, $4, $6, $6, $8, $8, $10, $10, $12, $12, $13, $14}' OFS="\t" > $output;
cat $output_joint | awk '{print $1, $4, $6, $10, $12}' OFS="\t" > $output;
rm $output_toke $output_anna $output_lem; #$output_joint;
