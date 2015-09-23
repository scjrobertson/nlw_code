#!/bin/bash

CPATH="src/main/trees/RunMain.java";
CPATH_ATTACK="src/main/trees/Attack.java";
COMPILE="src.main.trees.RunMain";
COMPILE_ATTACK="src.main.trees.Attack";
RESOURCES="src/main/resources";
TREES="src/main/trees";
SIMPLE="src/main/resources/simplenlg-v4.4.2.jar"
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
	java -Xmx2g -cp .:$core:$models:$xom:$joda:$jollyday:$ejml $class -annotators $options -file $1;
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

delete_embed () {
	line=$(grep -nr '####EOEA####' $2 | cut -f1 -d:);
	line=$(( line + 1 ));
	tail -n +$line $2 > $1/swap.txt;
	mv $1/swap.txt $2;
}

delete_extract () {
	l1=$(grep -nr '####BOEA####' $1 | cut -f1 -d:);
	l2=$(grep -nr '####EOEA####' $1 | cut -f1 -d:);
	l3="-i.bak -e $l1,$l2"d" $1" ;
	sed $l3;
}

extract_key () {
	cat $2 | awk 'NR < 4' > $1/$3;
	tail -n +4 $2 > $1/swap.txt;
	mv $1/swap.txt $2;
}

root_in=$1;
root_out=$2;
run=$3;
embed=$4;
supervise=$5;
specific=$6;
attack=$7;
store_file=$8;
message=$9;

file_in=$OUTPATH/$root_in.txt;
path_in="$OUTPATH/$root_in";
parse_in="$root_in""_parse.txt";
dep_in="$root_in""_dep.txt";
lemma_in="$root_in""_lemma.txt";
xml_in="$root_in.xml";

file_out=$OUTPATH/$root_out.txt;
path_out="$OUTPATH/$root_out";
parse_out="$root_out""_parse.txt";
dep_out="$root_out""_dep.txt";
lemma_out="$root_out""_lemma.txt";
key="$root_out""_key.txt";
xml_out="$root_out.xml";


if [ $root_in = "--help" ]
	then
	echo -e "Below are the flags required to run the program, all must be input even if they are not required.\nNo extensions are required for input/output files - assumed to be .txt.\n"
	echo -e "1. root_in - The folder of input cover text in src/main/output";
	echo -e "2. root_out - The output file in src/main/output"
	echo -e "3. run - Determines whether the program compiles or runs source code.
	0: Compile\n\t1: Run";
	echo -e "4. command - Determines what process is to be completed.
	0: Embed\n\t1: Extract\n\t2: Attack";
	echo -e "5. supervision - Determines whether the transforms are completed automatically or by hand.
	0: Unsupervised\n\t1: Supervised";
	echo -e "6. specific - Embed a specific message.
	0: Random\n\t1: Specific";
	echo -e "7. Attack type - Choose which attack is performed on the watermarked text.
	0: Random deletion\n\t1: Precise deletion 
	2: Shuffle\n\t3: Conjunction";
	echo -e "8. Store file - The file in nlw_code/ where BER data is stored.";
	echo -e "9. message - The specific message to be embedded, if any. Separated with dashes: i.e 0-1-1-1-0-1\n"
	exit 1;

fi

javac $CPATH;
javac -cp .:$SIMPLE $CPATH_ATTACK;
if [ $run = 1 ]
	then 
		if [ $embed = 0 ] 
			then 
				delete_existing $OUTPATH/$root_in;
				stanford_parse $file_in $root_in.txt $path_in $lemma_in $parse_in $dep_in $xml_in;
				java -Xmx2g $COMPILE $path_in/$parse_in $path_in/$dep_in $path_in/$lemma_in $embed $path_out/$key $supervise $specific $message | tee $OUTPATH/$root_out.txt;
				if [ $supervise = 1 ]
					then
						delete_embed $OUTPATH $file_out;
				fi
				extract_key $OUTPATH $file_out $key;

		elif [ $embed = 1 ]
			then
				delete_existing $OUTPATH/$root_out;
				stanford_parse $file_out $root_out.txt $path_out $lemma_out $parse_out $dep_out $xml_out;
				cp $OUTPATH/$key $OUTPATH/$root_out
				java -Xmx2g $COMPILE $path_out/$parse_out $path_out/$dep_out $path_out/$lemma_out $embed $path_out/$key $supervise | tee -a $store_file;
				if [ $supervise = 1 ]
					then
						delete_extract $store_file;
				fi
		elif [ $embed = 2 ]
			then
				if [ $attack = 1 ]
					then
						delete_existing $OUTPATH/$root_out;
						stanford_parse $file_out $root_out.txt $path_out $lemma_out $parse_out $dep_out $xml_out;
						cp $OUTPATH/$key $OUTPATH/$root_out;
				fi
				java -Xmx2g -cp .:$SIMPLE $COMPILE_ATTACK $OUTPATH/$root_out.txt $path_out/$dep_out $path_out/$lemma_out $attack | tee $OUTPATH/attacked.txt;
				mv $OUTPATH/attacked.txt $OUTPATH/$root_out.txt
		fi
fi
