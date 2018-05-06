(* this will be the main file that does everything *)
open Preprocessing
open Comparison
open Dictionary

type state = {display : string; directory : string; results : CompDict.t option}

type cmd = RUN | DIR | HELP | SETDIR | RESULTS | COMPARE | ERROR

type input = cmd * string option * string option

let newstate = {display = "Welcome to MOSS. Type 'help' for a list of commands" ;
                directory = "./" ;
                results = None}

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

let concat_str_list lst =
  List.fold_left (fun s a -> a ^ "\n" ^ s) "" lst

let rec parse_dir dir dict =
  try
    let f_name = Unix.readdir dir in
    let new_dict = Comparison.FileDict.insert f_name
        (Winnowing.winnow (Preprocessing.hash_file f_name) 5) dict in
    parse_dir dir new_dict
  with
  | End_of_file -> dict

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
      |RUN -> begin
        try begin
          print_endline "parsing files...";
          let parsefiles = (parse_dir (Unix.opendir st.directory) Comparison.FileDict.empty) in
          print_endline "comparing files...";
          let comparison = Comparison.compare parsefiles in
          print_endline "generating results...";
          repl {st with display = "Success. The list of plagiarised files are:" ^ 
                          concat_str_list (Comparison.create_sim_list comparison);
                        results = Some comparison}
        end
        with _ -> repl {st with display = "Error: Something went wrong"}
      end
      |DIR -> repl {st with display = st.directory}
      |SETDIR -> begin
        (* TODO: check to see if valid directory, print out file names in directory *)
      	match x with
      	|Some d -> repl {st with directory = d ; display = "Successfully set directory to: " ^ d}
      	|None -> repl {st with display = "Error: Could not set directory"}
      end
      |RESULTS -> begin 
        match st.results with
        |Some r -> begin
          match x with
          |Some f -> failwith "unimplemented"
          |None -> failwith "unimplemented"
        end
        |None -> repl {st with display = "Error: There are no results to display. Run MOSS first"}
      end
      |COMPARE -> begin
        match st.results with
        |Some r -> begin 
            match (x,y) with
            |Some a,Some b -> failwith "unimplemented"
            |_,_ -> repl {st with display = "Error: please specify which two files to compare"}
        end
        |None -> repl {st with display = "Error: There are no results to display. Run MOSS first"}
  	  end
      |ERROR -> repl {st with display = "Error: invalid command"}
  	end

let main () = repl newstate

let () = main ()
