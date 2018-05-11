oCaMOSS User's Guide
-------------------------------------------------------------------------------
To run:
navigate to the root directory of this project, and use "make run"
-------------------------------------------------------------------------------
Commands: (note - commands are case-sensitive)

run [words per hash] [window size] --- runs oCaMoss on the working directory.
params are optional, defaults to 35 words per hash, 40 per window. 

dir --- lists the working directory and the files that it contains

setdir [dir] --- sets the relative directory to look for files and resets any
results

results --- lists the file names for which there are results

results [filename] --- lists the detailed results of overlap for that file
(Make sure to include the extension of the file)

compare [fileA] [fileB] --- prints out specific overlaps of files A and B
(Make sure to include the extension of the files)

quit --- exits the REPL

help --- display the available commands
-------------------------------------------------------------------------------
Usage instructions/tutorial:

1. [setdir] to folder you want to test. requirements: file names have no spaces
and all files have the same extension 
(example: [setdir tests/test1])

2. [run] with desired params (example: [run 35 40] is the same as [run])

3. [results] to view list of results, [results filename] to view list of
results for specific file, and [compare A B] to compare matching patterns for
two files (example: [results Camel.txt])
-------------------------------------------------------------------------------
Other information:

Similarity score:
- used as a measure of how likely file A plagiarized from file B
- ratio of # matching hashes between A and B : # hashes in fingerprint for A
- overall similarity score for A is the average of all similarity scores 
for file A that are > 0.5
- threshold score for detecting possible plagiarism: 0.5

Supported languages/file formats:
 - oCaml: .ml, .mli
 - English: .txt (note: english comparison does NOT account for semantics)
 - More coming soon(?)

-------------------------------------------------------------------------------
Sample test cases description (test case N is in tests/testN):

1) exact duplicates - should return positive result
2) variable names changed - should return positive result
3) functions/comments reordered  - should return positive result
4) functions1.ml is a copy of functions.ml but with large sections deleted
- should return positive result
5) different implementations of the same algorithm - should NOT return positive
result
6) completely different files - should NOT return positive result
7) functions/comments reordered  - should return positive result
8) 
9)
10)
11)

