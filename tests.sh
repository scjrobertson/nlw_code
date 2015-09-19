#!/bin/bash

store_file=$1;
batch=$2;

for (( i = 0; i < $batch; i++ ))
do
	./generate.sh gen 0;
	./generate.sh gen 1;

	./runmain.sh gen embed 0 0 0 0 $store_file.txt;
	./runmain.sh gen embed 1 0 0 0 $store_file.txt; 
	./runmain.sh gen embed 1 1 0 0 $store_file.txt;
done
