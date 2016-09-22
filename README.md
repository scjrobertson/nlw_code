# README #

## What is this repository for? ##

* Code for the natural language watermarking approach described by Atallah et al. (2001).This is a reference implementation used to set a becnhmark and determine the feasibility of the approach based on current NLP technologies. This project is implemented in Java and tied together with bash scripts and some string.

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

### generate.sh ##
generate.sh creates simple cover text consisting solely of active sentences, it is complied and run as follows:

     chmod a+x generate.sh
     ./generate [output_root] [compile/run]

This generates a 1000 simple active sentences, a reasonable length for testing purposes and does not require excessive parsing time. **output_root** is the cover text's file name and is placed in **nlw_code/src/main/output**, choose a name. **compile/run** is a binary flag which determine whether the **nlw_code/src/main/nlg/NLG.java** is complied or run.

* 0: Compile the program.
* 1: Run the program.

### runmain.sh ###
runmain.sh controls the **embedding**, **attacking** and **extraction** procedures. The embedding algorithm embeds an arbitrary message within the cover text using the text's constituent representation. The extraction algorithm extracts the embedded message and determines the bit error rate (**BER**). 

runmain.sh parses the cover text using the **Stanford coreNLP**, it is run and complied as follows:
 
    chmod a+x generate.sh
    ./runmain.sh [in_root] [out_root] [comp/run] [em/ex/att] [unsup/sup] [spec] [att_type] [results] [msg]

The flags are as follows:
* **in_root**: The name of the output file produce by generate.sh, found in **nlw_code/src/main/output**. **nlw_code/src/main/output/inp_root** contains the  parse, dependency, lemma files for the input cover text.
* **out_root**: The chosen name of the watermark output folder. Creates folder within **nlw_code/src/main/output**, containing parse, dependency, lemma and key files.  **nlw_code/src/main/output/out_root** the output files for the watermarked text.
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
* **spec**: Embed a specific message within the text.
    * **0**: Random message
    * **1**: Specific message
* **att_type**: The choice of attack, if attacking the watermark:
    * **0**: Random deletion 
    * **1**: Precise deletion 
    * **2**: Rearrangement 
    * **3**: Conjunction
    * **4**: Sentence insertion
    * **5**: Word insertion
    * **6**: Modification
* **results**: The chosen name of a .txt to hold the BER results. The file will be placed in **nlw_code/**.
* **message**: Embed a specific message within the text. The binary message must be separated by dashes, e.g 0-1-0-1-0.

### tests.sh ###
**tests.sh** coordinates the testing procedures. Executes **runmain.sh** under given conditions for a certain number of trials. It run in a similar way to **generates.sh**:

    chmod a+x tests.sh
    ./tests.sh [results] [test_type] [att_type] [batch]
    
The flags are as follows:
* **results**: The chosen name of a .txt to hold the BER results. The file will be placed in **nlw_code/**.
* **test_type**: The test conditions:
    * **0**: Ideal conditions. The watermark is embedded and extracted under ideal conditions.
    * **1**: Tries. The watermark is embedded and extracted under ideal conditions. If the BER is over 20% the same message is embedded in the same text using a different prime and the number of attempts recorded.
    * **2**: Attack conditions. The watermark is attack by one of the 7 attack types before extraction.
* **att_type**: The choice of attack, if attacking the watermark:
    * **0**: Random deletion 
    * **1**: Precise deletion 
    * **2**: Rearrangement 
    * **3**: Conjunction
    * **4**: Sentence insertion
    * **5**: Word insertion
    * **6**: Modification
* **batch**: The number of tests to be run. On average a single test takes 44 seconds to complete.

##API##

The compilation instruction for each individual Java file is included in the comments. The original code makes use of **javadoc** comments and the API can be extracted from the root folder **nlw_code/** as follows:

    mkdir documentation
    javadoc -d documentation src.main.trees src.main.utils

**Warning**: There may be some errors in the javadoc comments causing the extraction to fail, src.main.trees has some guarantee of working. The API can now be viewed comfortably in a browser rather than sifting through code.

## Who do I talk to? ##

SCJ Robertson

**Contact**
* robertsonscj@gmail.com
