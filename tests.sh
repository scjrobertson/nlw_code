#!/bin/bash

for i in {1..1000}
do
	./generate.sh gen 0;
	./generate.sh gen 1;

	./runmain.sh gen embed 0 0;
	./runmain.sh gen embed 0 1;
	./runmain.sh gen embed 1 1;
done
