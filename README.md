# README #

## What is this repository for? ##

* Code for the natural language watermarking approach described by Atallah et al. (2001).This project is implemented in Java and held together with bash scripts and some string.

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
The current set up is run by three bash scripts **runmain.sh**, **generate.sh** and **test.sh** found in **nlw_code/**. 

### Parsing a plain text document ###

#### generate.sh ###
generate.sh creates simple cover text consisting solely of active sentences, it is complied and run as follows:

     chmod a+x generate.sh
     ./generate [output_root] [compile/run]

This generates a 1000 simple active sentences, a reasonable length for testing purposes and does not require excessive parsing time. **output_root** is the cover text's file name and is placed in **nlw_code/src/main/output**, choose a name. **compile/run** is a binary flag which determine whether the **nlw_code/src/main/nlg/NLG.java** is complied or run.

* 0: Compile the program.
* 1: Run the program.

### runmain.sh ###
runmain.sh controls the **embedding**, **attacking** and **extraction** procedures. The embedding algorithm embeds a random binary message within the plain text, at a pre-set length ( a percentage of the unique marker sentences ). The attacks include:
 * Random deletion 
 * Precise deletion 
 * Rearrangement 
 * Conjunction
 * Sentence insertion
 * Word insertion

The extraction algorithm extracts the embedded message and determines the bit error rate (**BER**). 

runmain.sh parses the cover text using the **Stanford coreNLP**, it is run and complied as follows:
 
    chmod a+x generate.sh
    ./runmain.sh [in_root] [out_root] [comp/run] [em/ex/att] [unsup/sup] [att_type] [results] [message]

The flags are as follows:
* **in_root**: The name of the output file produce by generate.sh, found in **nlw_code/src/main/output**. **nlw_code/src/main/output/inp_root** is contains the files for the input cover text.
* **out_root**: The chosen name of the watermark output folders. Creates two folders within **nlw_code/src/main/output**, both containing parse, dependency, lemma and key files. The key files contain the secret key specific to the embedding and the embedded message for the BER.  **nlw_code/src/main/output/out_root** the output files for the watermarked text.
* **compile/run** Whether to run the Java code or simply compile it:
    * **0**: Compile
    * **1**: Run
* **em/ex/att**: Choice of embedding, extracting or attacking the watermark.
    * **0**: Extract
    * **1**: Embed
    * **2**: Attack
* **unsup/sup**: Supervised or unsupervised embedding/extraction. Unsupervised embedding/extraction will make use of the automatic transformations/verfication. Supervised embedding will make use of manual transformations/verification.
    * **0**: Unsupervised
    * **1**: Supervised



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
