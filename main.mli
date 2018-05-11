open Preprocessing
open Comparison

type color = RED | BLACK | GREEN | CYAN | WHITE

(* type representing the state of the interface *)
type state = {display: (color * string) list; directory: string; results:
                CompDict.t option; result_files: string; params: (int*int)}

(* type for commands recognised by MOSS
 * RUN - runs MOSS on the current directory of the state, no arguments
 * HELP - lists the available commands, no arguments
 * SETDIR - sets the working directory where MOSS looks for files, takes
 * 1 argument
 * RESULTS - displays results, takes 0 arguments (lists files with results)
 * or 1 argument
 * (lists details for that file)
 * COMPARE - compares two files, printing out the similarities, takes 2
 * arguments
 *)
type cmd = RUN of (string*string)| DIR | HELP | SETDIR of string
         | RESULTS of string | COMPARE of (string*string)| ERROR

(* parses the string of a user into specific commands for MOSS *)
val parse: string -> cmd

(* [repl st] the REPL for entering commands to MOSS,
 * side effects: prints the string in the display field in [st] *)
val repl : state -> unit
