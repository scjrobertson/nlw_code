#!/bin/bash

./tests.sh random_del 2 0 1000;
./tests.sh precise_del 2 1 1000;
./tests.sh shuffle 2 2 1000;
./tests.sh conjunction 2 3 1000;
./tests.sh insertion 2 4 1000;
