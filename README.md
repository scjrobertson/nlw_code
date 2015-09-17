# README #

## What is this repository for? ##

* Java code for the natural language watermarking approach described by Atallah et al. (2001) .

## How do I get set up? ##

* A (preferably Debian-based) Linux distribution, this setup requires running bash scripts.
* JDK and JRE 8 (7 may work as well)
* Download **Stanford coreNLP** and **simpleNLG-v.4.4.2**. A bash script, using **wget** and the **Ubuntu** archive manager **file-roller**, is placed in the **nlw_code/src/main/resources** folder
which will automatically download them upon execution. 
To compile and run this, use the following commands:

        chmod a+x download.sh
        ./download.sh

    **Note**: If the script doesn't work, follow its links and extract the relevant files manually.

* Install **XMLStarlet**, used for searching XML documents: 

        sudo apt-get install xmlstarlet

## Running current setup ##

### Parsing a plain text document ###
The current set up is run by three bash scripts **runmain.sh**, **generate.sh** and **test.sh** found in **nlw_code/**. 

#### generate.sh ###
generate.sh creates simple cover text consisting solely of active sentences, it is complied and run as follows:

     chmod a+x generate.sh
     ./generate [output_file] [compile/run]

This generates a 1000 simple active sentences, a reasonable length for testing purposes and does not require excessive parsing time. **output_file** is the cover text's file name and is placed in **nlw_code/src/main/output**, choose a name. **compile/run** is a binary flag which determine whether the **nlw_code/src/main/nlg/NLG.java** is complied or run.

* 0: Compile the program.
* 1: Run the program.

#### runmain.sh ####
runmain.sh controls the **embedding**, **attacking** and **extraction** procedures. The embedding algorithm embeds a random binary message within the plain text, at a pre-set length ( a percentage of the unique marker sentences ). The attacks include random and precise **deletion**, **shuffling** and **conjunction**. The extraction algorithm extracts the embedded message and determines the bit error rate (**BER**). runmain.sh parses the cover text using the **Stanford coreNLP**, it is run and complied as follows:
 
    chmod a+x generate.sh
    ./runmain.sh [input_file] [output_root] [compile/run] [embed/extract] [attack_type]

**input_root** is the output file produce by generate.sh, found in **nlw_code/src/main/output**. **output_root** creates two folders within**nlw_code/src/main/output**. 


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