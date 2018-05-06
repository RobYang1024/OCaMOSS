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

let concat_int_list lst =
  List.fold_left (fun a x -> a ^ "," ^ string_of_int x) "" lst

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
          repl {st with display = "Success. The list of plagiarised files are:\n" ^ 
                          concat_str_list (Comparison.create_sim_list comparison);
                        results = Some comparison}
        end
        with _ -> repl {st with display = "Error: Something went wrong"}
      end
      |DIR -> repl {st with display = "Current working directory: " ^ st.directory}
      |SETDIR -> begin
        (* TODO: check to see if valid directory, print out file names in directory *)
      	match x with
      	|Some d -> repl {st with directory = d ; display = "Successfully set working directory to: " ^ d}
      	|None -> repl {st with display = "Error: Could not set directory"}
      end
      |RESULTS -> begin 
        match st.results with
        |Some r -> begin
          match x with
          |Some f -> handle_results st f
          |None -> repl {st with display =  "Results for files: \n" ^
              concat_str_list (List.map (fst) (CompDict.to_list r))}
        end
        |None -> repl {st with display = "Error: no results to display. Run MOSS first"}
      end
      |COMPARE -> begin
        match st.results with
        |Some r -> begin 
            match (x,y) with
            |Some a,Some b -> handle_compare st a b
            |_,_ -> repl {st with display = "Error: please specify which two files to compare"}
        end
        |None -> repl {st with display = "Error: no results to display. Run MOSS first"}
  	  end
      |ERROR -> repl {st with display = "Error: invalid command"}
  	end

and handle_compare st a b = 
  match st.results with
  |None -> failwith "unexpected"
  |Some r -> begin
    match (CompDict.find a r, CompDict.find b r) with
    |(Some v1, Some v2) -> begin
      match (FileDict.find b v1, FileDict.find a v2) with
      |(Some r1, Some r2) -> begin
        let l1 = List.map (snd) r1 |> concat_int_list in
        let l2 = List.map (snd) r2 |> concat_int_list in
        let res = concat_str_list [a ; l1 ; b ; l2] in
        repl {st with display = "Fingerprint matches: \n" ^ res}
      end
      |_,_ -> failwith "unexpected"
    end
    |_,_ -> repl {st with display = "Error: no results to display for files " ^ a ^","^ b}
  end


and handle_results st f = 
  match st.results with
  |None -> failwith "unexpected"
  |Some r -> begin
    match CompDict.find f r with
    |Some v -> begin
      let v_list = FileDict.to_list v in
      let d = List.map (fun x -> 
        fst x ^ "\n" ^ concat_int_list (List.map (fun y -> snd y) (snd x))) 
      v_list in
      repl {st with display = "Results for file " ^ f ^": \n"^ concat_str_list d}
    end
    |None -> repl {st with display = "Error: no results to display for file " ^ f}
  end

let main () = repl newstate

let () = main ()
