# README #

## What is this repository for? ##

* Java code for the natural language watermarking approach described by Atallah et al. (2001) .

### How do I get set up? ###

* A Debian-based Linux distribution (requires running bash scripts).
+ JDK and JRE 8
- Download Stanford coreNLP, simpleNLG-v.4.4.2. A bash script using wget is placed in the nlw_code/src/main/resources folder
which will automatically dowload them upon execution. To compile this, run the following commands:

    * chmod a+x download.sh
    * ./download.sh

- Install XMLStarlet : sudo apt-get install xmlstarlet

## Running current setup ##

### Parsing a plain text document ###
To parse an input body of text using the Stanford coreNLP, compile and run core_standford.sh. This bash script is 
found in nlw_code/src/main/trees, it is compiled as follows:

    * chmod a+x core_stanford.txt
    * ./core_stanford.sh text.txt

This will create three output files for each input file:
    * text_lemma.txt - The POS tags and lemmas of the sentence.
    * text_dep.txt - The basic dependencies describing the text.
    * text_parse.txt - The syntacting parse trees s-expressions.

### Output parse trees and binary strings ###
The current implementation only re-parses the Stanford coreNLP output and
allows the binary strings of each sentence to be generated. The Java files can be compiled
from the root folder nlw_code/ as follows:
* javac src/main/trees/ReadFile.java
The file is then run using one of two flags. For dependency output, the -d flag is used:
* java src.main.trees.ReadFile -d src/main/trees/text_dep.txt src/main/trees/text_lemma.txt
For syntactic parse trees the -p flag is used:
* java src.main.trees.ReadFile -p src/main/trees/text_parse.txt

### Who do I talk to? ###

* SCJ Robertson, 16579852