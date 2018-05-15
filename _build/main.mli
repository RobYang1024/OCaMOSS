open Preprocessing
open Comparison

type color = RED | GREEN | CYAN | TEXT

(* type representing the state of the interface *)
type state = {display: (color * string) list; directory: string; results:
                CompDict.t option; result_files: (color * string) list;
              threshold: float}

(* type for commands recognised by MOSS
 * RUN - runs MOSS on the current directory of the state, no arguments
 * HELP - lists the available commands, no arguments
 * SETDIR - sets the working directory where MOSS looks for files, takes
 * 1 argument, displays the working directory
 * DIR - displays the working directory and all the files it contains
 * RESULTS - displays results, takes 0 arguments
 * (lists files that are contained in results, and their similarity scores)
 * or 1 argument (lists similarity scores for that file vs other files)
 * COMPARE - compares two files, printing out the similar sections, takes 2
 * arguments
 *)
type cmd = RUN of string | DIR | HELP | SETDIR of string
         | RESULTS of string | PAIR | COMPARE of (string*string)| ERROR

(* this is printed every time the user enters the help command *)
val help: string

(* represents the initial state of OCaMoss when it starts:
 * displays a welcome message and a list of commands
 * default params of k=35 w=40
 * no results loaded and no working directory preset *)
val newstate: state

(* parses the string of a user into specific commands for MOSS *)
val parse: string -> cmd

(* [repl st] the REPL for entering commands to MOSS,
 * side effects: prints the string in the display field in [st] and
 * some progress indicators when running and displaying results
 * expected behavior for commands:
 *
 * [run]
 * - runs MOSS on the current directory of the state
 * - displays an error message if current directory is not set,
 *   if files are invalid file type, or if not all files have same extn
 * - displays list of files that are likely to be plagiarized
 * - two optional params for w and k, with the following conditions:
 *   w is in range [20,100], k is in range [15,40], k < w
 * - if only one param is provided, if params are invalid an error
 * message will be displayed
 *
 * [help]
 * - lists the available commands, takes no arguments
 *
 * [setdir]
 * - sets the working directory where MOSS looks for files, takes
 *   1 argument, the directory path relative to the folder main.ml is in
 * - displays the files in the new directory
 * - displays error message if directory is incorrect
 *
 * [dir]
 * - displays the working directory and all the files it contains
 *
 * [results]
 * if 0 arguments:
 * - displays all files that have results, and their overall similarity scores
 * if 1 argument (name of a file:
 * - if valid file name of a file with results, displays the similarity score
 *   of that file compared to all other files in results
 * - if there are no results to display, an error message will be shown
 *
 * [compare]
 * - if results exist, and both arguments are valid file names with results,
 *   a side by side printout of the matching patterns in the fingerprints of
 *   files will be displayed
 * - otherwise, an error message will be displayed
 *
 * [quit]
 * - exits the repl
 *
 * invalid commands will display an error message
 *)
val repl : state -> unit
