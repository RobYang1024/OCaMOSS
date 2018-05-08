(* this will be the main file that does everything *)
open Preprocessing
open Comparison
open Dictionary

type state = {display:string; directory:string; results:CompDict.t option;
              params:(int*int)}

type cmd = RUN of (string*string)| DIR | HELP | SETDIR of string
         | RESULTS of string | COMPARE of (string*string)| ERROR

type input = cmd

let newstate = {display = "Welcome to MOSS. Type 'help' for a list of commands" ;
                directory = "./" ;
                results = None; params = (5,25)}

let help =
"
run : runs MOSS on the specified directory. If you would like, specify k and
window sizes(If you do not specify any, the defaults values will be 5 for k
and 25 for window size). \n
dir : lists the working directory \n
setdir [dir]: sets the directory to look for files \n
results : lists the file names for which there are results \n
results [filename] : lists the detailed results of overlap for that file
(Make sure to include the extension of the file)\n
compare [fileA] [fileB] : prints out specific overlapping sections of files A and B
(Make sure to include the extension of the files) \n
quit : exits the program \n
"

let parse str =
  let input_split = String.split_on_char ' ' (String.trim str) in
	match input_split with
  | ["help"] -> HELP
  | "run"::k::w::[] -> RUN (k,w)
  | ["run"] -> RUN ("5","25")
  | ["dir"] -> DIR
  | "setdir"::d::[] -> SETDIR d
  | "results"::f::[] -> RESULTS f
  | ["results"] -> RESULTS ""
  | "compare"::a::b::[] -> COMPARE (a,b)
	| _ -> ERROR

let concat_str_list lst =
  List.fold_left (fun s a -> a ^ "\n" ^ s) "" lst

let concat_int_list lst =
  let int_list = List.fold_left (fun a x -> string_of_int x ^ "," ^ a) "" lst in
  if int_list = "" then "" else String.sub int_list 0
      (String.length int_list - 1)

let rec parse_dir dir dict dir_name k w =
  try
    let f_name = Unix.readdir dir in
    if String.get f_name 0 = '.' then parse_dir dir dict dir_name k w else
    let new_dict = Comparison.FileDict.insert f_name
        (Winnowing.winnow (Preprocessing.hash_file
                             (dir_name ^ Filename.dir_sep ^ f_name) k) w) dict in
    parse_dir dir new_dict dir_name k w
  with
  | End_of_file -> dict

let rec print_dir_files dir str =
  try
    let f_name = Unix.readdir dir in
    if String.get f_name 0 = '.' then print_dir_files dir str else
    print_dir_files dir (str^f_name^"\n")
  with
  | End_of_file -> str

let rec repl st =
  print_endline st.display;
  print_string  "> ";
  match String.lowercase_ascii (read_line ()) with
    | exception End_of_file -> ()
    | "quit" -> print_endline "You have exited the REPL.";
  	| input -> begin
      match parse input with
      |HELP -> repl {st with display = help}
      |RUN (k,w)-> begin
          try begin
            print_endline "parsing files...";
            let parsefiles = (parse_dir (Unix.opendir st.directory)
                                Comparison.FileDict.empty st.directory
                                (int_of_string k) (int_of_string w)) in
            print_endline "comparing files...";
            let comparison = Comparison.compare parsefiles in
            print_endline "generating results...";
            repl {st with display = "Success. The list of plagiarised files are:\n" ^
                            concat_str_list (Comparison.create_sim_list comparison);
                          results = Some comparison}
          end
          with
          | Failure f_msg when f_msg = "int_of_string" ->
            repl {st with display = "Error: Invalid argument(s)"}
          | Failure f_msg -> repl {st with display = f_msg}
          | _ -> repl {st with display = "Error: Something went wrong"}
      end
      |DIR -> repl {st with display = "Current working directory: " ^ st.directory}
      |SETDIR d -> begin
          if d = "" || not (Sys.is_directory d) then repl {st with display = "Error: Invalid directory"}
              else repl {st with directory = d ; display = "Successfully set working directory to: " ^ d
                                                            ^ "\n Files: " ^ (print_dir_files (Unix.opendir d) "")}
            end
      (*TODO: Avoid triple nesting here. *)
      |RESULTS f -> begin
        match st.results with
        |Some r -> begin
            if f = "" then repl {st with display =  "Results for files: \n" ^
                                                    concat_str_list (List.map (fst) (CompDict.to_list r))}
            else handle_results st f
        end
        |None -> repl {st with display = "Error: no results to display. Run MOSS first"}
      end
      (*TODO: Avoid triple nesting here. *)
      |COMPARE (a,b) -> begin
        match st.results with
        |Some r -> handle_compare st a b
        |None -> repl {st with display = "Error: no results to display. Run MOSS first"}
  	  end
      |ERROR -> repl {st with display = "Error: invalid command"}
  	end

and handle_compare st a b =
  match st.results with
  |None -> failwith "unexpected"
  |Some r -> begin
    match (CompDict.find a r, CompDict.find b r) with
      | (Some v1, Some v2) -> begin
      (*TODO: Avoid triple nesting here. *)
      match (FileDict.find b v1, FileDict.find a v2) with
      | (Some r1, Some r2) -> begin
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
