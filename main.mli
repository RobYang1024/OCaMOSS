open Preprocessing
open Comparison

(* type representing the state of the interface *)
type state = {display : string; directory : string; results : unit}

(* type for commands recognised by MOSS
 * RUN - runs MOSS on the current directory of the state, no arguments
 * HELP - lists the available commands, no arguments
 * SETDIR - sets the working directory where MOSS looks for files, takes 1 argument
 * RESULTS - displays results, takes 0 arguments (lists files with results) or 1 argument
 * (lists details for that file)
 * COMPARE - compares two files, printing out the similarities, takes 2 arguments *)
type cmd = RUN | HELP | SETDIR | RESULTS | COMPARE | ERROR

(* type for an input for MOSS, containing a command and 2 optional arguments
 * arguments beyond what the command recognizes will be ignored *)
type input = cmd * string option * string option

(* parses the string of a user into specific commands for MOSS *)
val parse: string -> input

(* [repl st] the REPL for entering commands to MOSS,
 * side effects: prints the string in the display field in [st] *)
val repl : state -> unit
