(* this will be the main file that does everything *)
open Preprocessing
open Comparison
open Dictionary
open ANSITerminal

type color = RED | BLACK | GREEN | CYAN | WHITE

type state = {display: (color * string) list; directory:string;
              results:CompDict.t option; params:(int*int)}

type cmd = RUN of (string*string)| DIR | HELP | SETDIR of string
         | RESULTS of string | COMPARE of (string*string)| ERROR

let help =
"Commands: \n
run [words per hash] [window size] --- runs oCaMoss on the working directory.
params are optional, defaults to 5 words per hash, 25 per window \n
dir --- lists the working directory and the files that it contains \n
setdir [dir] --- sets the relative directory to look for files \n
results --- lists the file names for which there are results \n
results [filename] --- lists the detailed results of overlap for that file 
(Make sure to include the extension of the file) \n
compare [fileA] [fileB] --- prints out specific overlaps of files A and B
(Make sure to include the extension of the files) \n
quit --- exits the REPL
"

let newstate = {display = [(GREEN,
"

                _______             __   __   _______   _______   _______
               |       |           |  |_|  | |       | |       | |       |
      _______  |     __|  _______  |       | |   _   | |  _____| |  _____|
     |   _   | |    |    |   _   | |       | |  | |  | | |_____  | |_____
     |  | |  | |    |__  |  |_|  | |       | |  |_|  | |_____  | |_____  |
     |  |_|  | |       | |   _   | | ||_|| | |       |  _____| |  _____| |
     |_______| |_______| |__| |__| |_|   |_| |_______| |_______| |_______|
");
(WHITE,"Welcome to oCaMOSS!! The following are the instructions for how to run
this program. Type 'help' to see the list of commands again.\n");(CYAN,help)];
                directory = "./" ; results = None; params = (10,25)}

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

let create_display_list color lst =
  List.map (fun x -> (color,x)) lst

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

let rec print_display d =
  match d with
  |[] -> ()
  |(RED, s)::t -> ANSITerminal.(print_string [red] (s^"\n")); print_display t
  |(BLACK, s)::t -> print_endline s; print_display t
  |(GREEN, s)::t -> ANSITerminal.(print_string [green] (s^"\n"));print_display t
  |(CYAN, s)::t -> ANSITerminal.(print_string [cyan] (s^"\n")); print_display t
  |(WHITE, s)::t -> ANSITerminal.(print_string [white] (s^"\n"));print_display t

let rec repl st =
  print_display st.display;
  print_string  [black] "> ";
  match String.lowercase_ascii (read_line ()) with
    | exception End_of_file -> ()
    | "quit" -> print_endline "You have exited the REPL.";
  	| input -> handle_input st input

and handle_input st input =
  match parse input with
  |HELP -> repl {st with display = [(CYAN,help)]}
  |RUN (k,w)-> begin
      try begin
        if st.directory = "./"
        then repl {st with display =
            [(RED,"Error: Directory has not been set")]}
        else
        let k' = int_of_string k in
        let w' = int_of_string w in
        if (k' >= 5 && k' <=50 && w' >=10 && w' <=100) then handle_run st k' w'
        else repl {st with display = 
        [(RED,"Error: k must be in range [5,50] and w must be in range [10,100]")]}
      end 
      with
      | Failure f_msg when f_msg = "int_of_string" ->
        repl {st with display = [(RED,"Error: Invalid argument(s)")]}
      | Failure f_msg -> repl {st with display = [(RED,f_msg)]}
      | _ -> repl {st with display = [(RED,"Error: Something went wrong")]}
  end
  |DIR -> if st.directory = "./"
    then repl {st with display =
                         [(RED,"Error: Directory has not been set")]}
    else repl {st with display = [(BLACK, "Current working directory: " ^
                                          st.directory ^ "\n Files: " ^
                        (print_dir_files (Unix.opendir st.directory) ""))]}
  |SETDIR d -> begin
      try
        if d = "" || not (Sys.is_directory d)
        then repl {st with display = [(RED,"Error: Invalid directory")]}
        else repl {st with directory = d ; display = [(GREEN,
        "Successfully set working directory to: " ^ d);
        (BLACK,"Files: \n" ^ (print_dir_files (Unix.opendir d) ""))]}
      with _ -> repl {st with display = [(RED,"Error: Invalid directory")]}
  end
  |RESULTS f -> begin
    match st.results with
    |Some r -> begin
        if f = "" then repl {st with display = 
          [(BLACK, "Results for files: \n" ^ 
          concat_str_list (List.map (fst) (CompDict.to_list r)))]}
        else handle_results st f
    end
    |None -> repl {st with display = 
    [(RED,"Error: no results to display. Run oCaMoss first")]}
  end
  |COMPARE (a,b) -> begin
    match st.results with
    |Some r -> handle_compare st a b
    |None -> repl {st with display = 
    [(RED,"Error: no results to display. Run oCaMoss first")]}
  end
  |ERROR -> repl {st with display = [(RED,"Error: invalid command")]}

and handle_compare st a b =
  match st.results with
  |None -> failwith "unexpected"
  |Some r -> begin
    match (CompDict.find a r, CompDict.find b r) with
      | (Some v1, Some v2) -> begin
      (*TODO: Avoid triple nesting here. *)
        match (FileDict.find b v1, FileDict.find a v2) with
          | (Some r1, Some r2) -> begin
            let l1 = List.map (snd) r1 |> List.rev in
            let l2 = List.map (snd) r2 |> List.rev in
            let res1 = Preprocessing.get_file_positions (Unix.opendir st.directory) st.directory (fst st.params) a l1 in
            let res2 = Preprocessing.get_file_positions (Unix.opendir st.directory) st.directory (fst st.params) b l2 in
            (* TODO: make both lists same length, sort by increasing order of hash instead of by position
             * just rehash fist k characters *)
            let newdispl = List.fold_left2 (fun acc r1 r2 -> 
              if String.length (snd r1) >= 40 then
                [(BLACK, Printf.sprintf "%-40s%s" (a ^ " position " ^ fst r1) (b ^ " position " ^ fst r2));
                (RED, Printf.sprintf "%-40s%s"  (snd r1 ^ "\n") (snd r2 ^ "\n"))]
                @ acc
              else
                [(BLACK, Printf.sprintf "%-40s%s" (a ^ " position " ^ fst r1) (b ^ " position " ^ fst r2));
                (RED, Printf.sprintf "%-40s%s"  (snd r1) (snd r2 ^ "\n"))]
                @ acc
            ) [] res1 res2 in
            repl {st with display = newdispl}
            end
          |_,_ -> failwith "unexpected"
    end
      |_,_ -> repl {st with display = [(RED,
      "Error: no results to display for files " ^ a ^","^ b)]}
  end


and handle_results st f =
  match st.results with
  |None -> failwith "unexpected"
  |Some r -> begin
    match CompDict.find f r with
    |Some v -> begin
      let v_list = List.filter (fun x  -> fst x <> f) (FileDict.to_list v) in
      let d = List.map (fun x ->
        fst x ^ "\n" ^ concat_int_list (List.map (fun y -> snd y) (snd x)))
      v_list in
      repl {st with display = [(BLACK, "Results for file " ^ f ^
        ": \n" ^ concat_str_list d)]}
    end
    |None -> repl {st with display = [(RED,
    "Error: no results to display for file " ^ f)]}
  end


and handle_run st k w = 
  print_endline "parsing files...";
  let parsefiles = (parse_dir (Unix.opendir st.directory)
                      Comparison.FileDict.empty st.directory
                      k w) in
  print_endline "comparing files...";
  let comparison = Comparison.compare parsefiles in
  print_endline "generating results...";
  repl {st with display = [(GREEN,"Success. The list of plagiarised files are:");
                  (BLACK, concat_str_list (Comparison.create_sim_list comparison))];
                results = Some comparison;
                params = (k, w)}


let main () = repl newstate

let () = main ()
