open Preprocessing
open Comparison
open Interface

type state = {display : string; directory : string; results : unit}

type cmd = RUN | HELP | SETDIR | RESULTS | COMPARE | ERROR

type input = cmd * string option * string option

val parse: string -> input

val repl : state -> unit
