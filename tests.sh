#!/bin/bash

store_file=$1;
test_type=$2;
attack=$3;
batch=$4;

if [ $store_file = "--help" ]
then
	echo -e "\nRuns a batch of watermarking tests.";
	echo -e "1. store_file - The file name, excluding extention, of the output.
      	\tThe file is placed in nlw_code/.";
	echo -e "2. Test type";
	echo -e "3. Batch size - The number of tests to run.\n";
	exit 1;
fi

./generate.sh gen 0;
./runmain.sh gen embed 0 0 0 0 0 $store_file.txt;
for (( i = 0; i < $batch; i++ ))
do
	./generate.sh gen 1;

	if [ $test_type = 0 ]
	then
		./runmain.sh gen embed 1 0 0 0 0 $store_file.txt; 
		./runmain.sh gen embed 1 1 0 0 0 $store_file.txt;
	elif [ $test_type = 1 ]
	then
		j=0;
		ber=50;
		pr="0";
		m="0";
		msg="0";

		while [ $ber -gt 20 ]
		do
			./runmain.sh gen embed 1 0 0 $m 0 $store_file.txt $msg;
			result=$(./runmain.sh gen embed 1 1 0 0 0 $store_file.txt);

			IFS='.' read -a err <<< "$result";
			IFS=";" read -a out <<< "$result";
			ber=$(( err[0]  ));
			pr2=${out[1]};

			msg=$(sed -n '3p' src/main/output/embed_key.txt | tr "\t" -);

			if [ $pr != $pr2 ]
			then
				j=$(( j + 1  ));
				pr=$pr2;
			fi
			m="1";
		done
		echo $j >> $store_file"_tries.txt";
	elif [ $test_type = 2 ]
	then
		./runmain.sh gen embed 1 0 0 0 0 $store_file.txt; 
		./runmain.sh gen embed 1 2 0 0 $attack $store_file.txt;
		./runmain.sh gen embed 1 1 0 0 0 $store_file.txt;
	fi
done
