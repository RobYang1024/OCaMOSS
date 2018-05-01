(* this will be the main file that does everything *)
open Preprocessing
open Comparison

type state = {display : string; directory : string; results : unit}

type cmd = RUN | DIR | HELP | SETDIR | RESULTS | COMPARE | ERROR

type input = cmd * string option * string option

let newstate = {display = "Welcome to MOSS. Type 'help' for a list of commands" ; directory = "./" ; results = ()}

let help =
"
run : runs MOSS on the specified directory \n
dir : lists the working directory \n
setdir [dir]: sets the relative directory to look for files \n
results : lists the file names for which there are results \n
results [filename] : lists the detailed results of overlap for that file \n
compare [fileA] [fileB] : prints out specific overlapping sections of files A and B \n
"

let parse str =
	let input_split = String.split_on_char ' ' str in
	match input_split with
	|"help"::_ -> (HELP, None, None)
	|"run"::_ -> (RUN, None, None)
  |"dir"::_ -> (DIR, None, None)
	|"setdir"::d::_ -> (SETDIR, Some d, None)
	|"results"::f::_ -> (RESULTS, Some f, None)
	|"results"::_ -> (RESULTS, None, None)
	|"compare"::a::b::_ -> (COMPARE, Some a, Some b)
	|_ -> (ERROR, None, None)

let rec repl st =
  print_endline st.display;
  print_string  "> ";
  match String.lowercase_ascii (read_line ()) with
    | exception End_of_file -> ()
    | "quit" -> print_endline "You have exited the REPL.";
  	| input -> begin
  	  let (c,x,y) = parse input in
      match c with
      |HELP -> repl {st with display = help}
      |RUN -> failwith "unimplemented"
      |DIR -> repl {st with display = st.directory}
      |SETDIR -> begin
      	match x with
      	|Some d -> repl {st with directory = d ; display = "Successfully set directory to: " ^ d}
      	|None -> repl {st with display = "Error: Something went wrong"}
      end
      |RESULTS -> failwith "unimplemented"
      |COMPARE -> begin
        match (x,y) with
      	|Some a,Some b -> failwith "unimplemented"
      	|_,_ -> repl {st with display = "Error: Something went wrong"}
  	  end
      |ERROR -> repl {st with display = "Error: Something went wrong"}
  	end

let main () = repl newstate

let () = main ()
