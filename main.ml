(* this will be the main file that does everything *)
open Preprocessing
open Comparison
open Dictionary
open ANSITerminal

type color = RED | GREEN | CYAN | TEXT

type state = {display: (color * string) list; directory: string; results:
                CompDict.t option; result_files: (color * string) list;
              threshold: float}

type cmd = RUN of string | DIR | HELP | SETDIR of string | RESULTS of string
         | PAIR | COMPARE of (string*string)| ERROR

let help =
"Commands (case-sensitive): \n
run [threshold] --- runs oCaMoss on the working directory.
The threshold argument gives the program the percentage of the file to match with
another for it to be flagged as plagiarised. It is optional with default value
0.5, and must be at least 0.4 and at most 1
dir --- lists the working directory and the files that it contains
setdir [dir] --- sets the relative directory to look for files and resets any results
results --- lists the file names for which there are results
results [filename] --- lists the detailed results of overlap for that file
pairresults -- lists all the pairs of files for which there are results
compare [fileA] [fileB] --- prints out specific overlaps of files A and B
quit --- exits the REPL
help --- display instructions again"

let newstate = {display = [(GREEN,
"
      _______   _______             __   __   _______   _______   _______
     |       | |       |           |  |_|  | |       | |       | |       |
     |   _   | |     __|  _______  |       | |   _   | |  _____| |  _____|
     |  | |  | |    |    |   _   | |       | |  | |  | | |_____  | |_____
     |  |_|  | |    |__  |  |_|  | |       | |  |_|  | |_____  | |_____  |
     |       | |       | |   _   | | ||_|| | |       |  _____| |  _____| |
     |_______| |_______| |__| |__| |_|   |_| |_______| |_______| |_______|
");
(TEXT,"Welcome to OCaMOSS!!");(CYAN,help)];
                directory = "./" ; results = None; result_files = [];
                threshold = 0.5}

let parse str =
  let input_split = String.split_on_char ' ' str in
	match input_split with
  | ["help"] -> HELP
  | "run"::[t] -> RUN t
  | ["run"] -> RUN "0.5"
  | ["dir"] -> DIR
  | "setdir"::d::[] -> SETDIR d
  | "results"::f::[] -> RESULTS f
  | ["results"] -> RESULTS ""
  | ["pairresults"] -> PAIR
  | "compare"::a::b::[] -> COMPARE (a,b)
	| _ -> ERROR

let rec repl st =
  let rec print_display d =
    match d with
    |[] -> ()
    |(RED, s)::t -> ANSITerminal.(print_string [red] (s^"\n")); print_display t
    |(TEXT, s)::t -> print_endline s; print_display t
    |(GREEN, s)::t -> ANSITerminal.(print_string [green] (s^"\n")); print_display t
    |(CYAN, s)::t -> ANSITerminal.(print_string [cyan] (s^"\n")); print_display t
  in
  print_display st.display;
  print_string  [black] "> ";
  match String.trim (read_line ()) with
    | exception End_of_file -> ()
    | "quit" -> ANSITerminal.(print_string [green]
                                ("Thank you for using OCaMOSS!!\n"));
  	| input -> handle_input st input

and handle_input st input =
  let rec print_dir_files dir str suf =
    try
      let f_name = Unix.readdir dir in
      if String.get f_name 0 = '.' || not (String.contains f_name '.')
      then print_dir_files dir str suf else
      if suf != "" && not (Filename.check_suffix f_name suf)
      then failwith "Different file extensions"
      else
        print_dir_files dir (str^f_name^"\n") (Filename.extension f_name)
    with
    | Failure f -> f
    | End_of_file -> str
  in
  match parse input with
  |HELP -> repl {st with display = [(CYAN,help)]}
  |RUN t -> begin
      try begin
        if st.directory = "./"
        then repl {st with display =
            [(RED,"Error: Directory has not been set")]}
        else
        let t' = float_of_string t in
        if t' >= 0.4 && t' <= 1.0
          then handle_run st t'
        else
          repl {st with display =
                          [(RED, "Error: Threshold must between 0.4 and 1")]}
      end
      with
      | Failure f_msg when f_msg = "float_of_string" ->
        repl {st with display = [(RED,"Error: Invalid argument(s)")]}
      | Failure f_msg -> repl {st with display = [(RED,f_msg)]}
      | _ -> repl {st with display = [(RED,"Error: Something went wrong")]}
  end
  |DIR ->
    if st.directory = "./"
    then repl {st with display =
                         [(RED,"Error: Directory has not been set")]}
    else let dir_files = print_dir_files (Unix.opendir st.directory) "" "" in
      repl {st with display = [(TEXT, "Current working directory: " ^
                                      st.directory^"\n Files: \n"^ dir_files)]}
  |SETDIR d -> begin
      try
        if d = "" || not (Sys.is_directory d)
        then repl {st with display = [(RED,"Error: Invalid directory")]}
        else let dir_files = print_dir_files (Unix.opendir d) "" "" in
          if dir_files = ""
          then repl {st with display = [(RED,"Error: Directory has no files")]}
          else if dir_files = "Different file extensions"
          then repl {st with display =
          [(RED,"Error: Not all files in this directory are of the same type")]}
          else repl {newstate with directory = d ; display = [(GREEN,
          "Successfully set working directory to: " ^ d);
                                            (TEXT,"Files: \n" ^ dir_files)]}

      with _ -> repl {st with display = [(RED,"Error: Invalid directory")]}
  end
  |RESULTS f -> begin
    match st.results with
    |Some r -> begin
        if f = "" then repl {st with display =
          (TEXT, "Results for files:")::st.result_files}
        else handle_results st f
    end
    |None -> repl {st with display =
    [(RED,"Error: no results to display. Run OCaMoss first")]}
  end
  | PAIR -> begin
      match st.results with
      | Some r -> if st.result_files = []
        then repl {st with display =
        [(GREEN,"Success. There were no plagarised files found.\n")];}
        else handle_pair r st
      | None -> repl {st with display =
      [(RED,"Error: no results to display. Run OCaMoss first")]}
  end
  |COMPARE (a,b) -> begin
    match st.results with
    |Some r -> handle_compare st a b
    |None -> repl {st with display =
    [(RED,"Error: no results to display. Run OCaMoss first")]}
  end
  |ERROR -> repl {st with display = [(RED,"Error: invalid command")]}

and handle_compare st a b =
  let rec pad l n =
    if List.length l >= n then l else pad (l@[("","")]) n
  in
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
            let res1 = Preprocessing.get_file_positions (Unix.opendir st.directory)
                      st.directory a l2 in
            let res2 = Preprocessing.get_file_positions (Unix.opendir st.directory)
                      st.directory b l1 in
            print_endline "generating display...";
            let padded1 = pad res1 (List.length res2) in
            let padded2 = pad res2 (List.length res1) in
            let newdispl = List.fold_left2 (fun acc r1 r2 ->
              if String.length (snd r1) >= 40 then
                (TEXT, Printf.sprintf "%-40s%s" (a ^ " position " ^ fst r1)
                  (b ^ " position " ^ fst r2))::
                (RED, Printf.sprintf "%-40s%s"  (snd r1 ^ "\n") (snd r2 ^ "\n"))::acc
              else
                (TEXT, Printf.sprintf "%-40s%s" (a ^ " position " ^ fst r1)
                  (b ^ " position " ^ fst r2))::
                (RED, Printf.sprintf "%-40s%s"  (snd r1) (snd r2 ^ "\n"))::acc
            ) [] padded1 padded2 in
            repl {st with display = newdispl}
            end
          |_,_ -> failwith "unexpected"
    end
      |_,_ -> repl {st with display = [(RED,
      "Error: no results to display for files " ^ a ^","^ b)]}
  end

and handle_results st f =
  let concat_result_list lst is_pair =
    List.fold_left (fun a (f,ss) ->
        (TEXT, Printf.sprintf "%-40s%s" ("File: " ^ f)
        ((if is_pair then "Similarity score: " else "Overall score: ") ^
        (string_of_float ss)))::a) [] lst
  in
  match st.results with
  |None -> failwith "unexpected"
  |Some r -> begin
    match CompDict.find f r with
    |Some v -> begin
      let r_list = Comparison.create_pair_sim_list f (FileDict.to_list v) in
      repl {st with display = (TEXT, "Results for file " ^ f ^
        ": \n")::(concat_result_list r_list true)}
    end
    |None -> repl {st with display = [(RED,
    "Error: no results to display for file " ^ f)]}
  end

and handle_run st t =
  let rec parse_dir dir dict dir_name =
    try
      let f_name = Unix.readdir dir in
      if String.get f_name 0 = '.' || not (String.contains f_name '.')
      then parse_dir dir dict dir_name else begin
      print_endline f_name;
      let new_dict = Comparison.FileDict.insert f_name
          (Winnowing.winnow (Preprocessing.hash_file
          (dir_name ^ Filename.dir_sep ^ f_name)) 40) dict in
      parse_dir dir new_dict dir_name
    end
    with
    | End_of_file -> dict
  in
  let concat_result_list lst is_pair =
    List.fold_left (fun a (f,ss) ->
        (TEXT, Printf.sprintf "%-40s%s" ("File: " ^ f)
        ((if is_pair then "Similarity score: " else "Overall score: ") ^
        (string_of_float ss)))::a) [] lst
  in
  print_endline "parsing files...";
  let parsefiles = parse_dir (Unix.opendir st.directory)
                        Comparison.FileDict.empty st.directory in
  print_endline "generating results...";
  let comparison = Comparison.compare parsefiles in
  let files = concat_result_list
      (Comparison.create_sim_list comparison t) false in
  if files = [] then repl {st with display =
                  [(GREEN,"Success. There were no plagarised files found.\n")];
                            results = Some comparison; threshold = t}
  else repl {st with display =
              (GREEN,"Success. The list of plagiarised files are:")::files;
              results = Some comparison; result_files = files; threshold = t}

and handle_pair r st =
  let disp = List.fold_left (fun d (f,v) -> d ^ (match CompDict.find f r with
      | None -> ""
      | Some f_d -> List.fold_left (fun s (f2,ss) ->
          if ss < st.threshold && f != f2 then s else s ^ f ^ " " ^ f2 ^ "\n")
                      "" (create_pair_sim_list f (FileDict.to_list f_d))))
      "" (CompDict.to_list r) in
  repl {st with display = [(TEXT,disp)]}


let main () = repl newstate
(* hi anna i am potato *)
let () = main ()
