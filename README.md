# README #

## What is this repository for? ##

* Java code for the natural language watermarking approach described by Atallah et al. (2001) .

## How do I get set up? ##

* A (preferably Debian-based) Linux distribution, this setup requires running bash scripts.
* JDK and JRE 8 (7 may work as well)
* Download **Stanford coreNLP** and **simpleNLG-v.4.4.2**. A bash script, using **wget** and the **Ubuntu** archive manager **file-roller**, is placed in the **nlw_code/src/main/resources** folder
which will automatically download them upon execution. 
To compile this, run the following commands:

        chmod a+x download.sh
        ./download.sh

    **Note**: If the script doesn't work, follow its links and extract the relevant files manually.

* Install **XMLStarlet**, used for searching XML documents: 

        sudo apt-get install xmlstarlet

## Running current setup ##

### Parsing a plain text document ###
To parse an input body of text using the Stanford coreNLP, compile and run **core_standford.sh**. This bash script is 
found in **nlw_code/src/main/trees**, it is compiled as follows:

     chmod a+x core_stanford.txt

The script can now be run as follows, an input body of text must always be placed in **nlw_code/src/main/output**:

     ./core_stanford.sh ../output/text.txt

This will create three output files, in the folder **src/main/output/text**, for each input file:

* text_lemma.txt - The POS tags and lemmas of the sentences.
* text_dep.txt - The basic dependencies describing the text.
* text_parse.txt - The syntactic parse trees' s-expressions.

Two bodies of text **moby.txt** and **sense.txt** have been included in **nlw_code/src/main/output/** to run as examples. Any body of plain text
is suitable for parsing.

### Output parse trees and binary strings ###
The current implementation only re-parses the Stanford coreNLP output and
allows the binary strings of each sentence to be generated. The Java files can be compiled
from the root folder **nlw_code/** as follows:

    javac -cp .:src/main/resources/simplenlg-v4.4.2.jar src/main/trees/ReadFile.java

The file is then run using one of two flags. For dependency output, the **-d** flag is used:

    java -cp .:src/main/resources/simplenlg-v4.4.2.jar src.main.trees.ReadFile -d src/main/output/text/text_dep.txt src/main/output/text/text_lemma.txt

For syntactic parse trees the **-p** flag is used:

    java -cp .:src/main/resources/simplenlg-v4.4.2.jar src.main.trees.ReadFile -p src/main/output/text/text_parse.txt

##API##

The compilation instruction for each individual Java file is included in the comments. The original code makes use of **javadoc** comments and the API can be extracted from the root folder **nlw_code/** as follows:

    mkdir documentation
    javadoc -d documentation src.main.trees src.main.utils

**Warning**: There may be some errors in the javadoc comments causing the extraction to fail, src.main.trees has some guarantee of working. The API can now be viewed comfortably in a browser rather than sifting through code.

## Who do I talk to? ##

SCJ Robertson

**Contact**

* 16579852@sun.ac.za
* robertsonscj@gmail.com