open Winnowing
open Unix
open Filename

(* Variables into v
   Types into t
   Module type into MT
   Function names into f
   Functors names into F
   args into a
*)

let special_chars =
  ['(';')';'[';']';'{';'}';'<';'>';'"';';';'|';'+';'-';'/';'*';'=';'&';'~';'.';':';'\"';'\'']

let rem_white_space code_string =
  code_string |>
  String.split_on_char '\n' |> String.concat " " |>
  String.split_on_char ' ' |>
  List.filter (fun str -> str <> "")

let rec str_to_chr_arr str =
  let tail_len = String.length str - 1 in
  match str with
  | "" -> []
  | str -> (String.get str 0)::(str_to_chr_arr (String.sub str 1 tail_len))

let split_and_keep_on_spec_chars str =
  let char_array = str_to_chr_arr str in
  (List.fold_left
    (fun acc_arr chr ->
       let str_of_chr = String.make 1 chr in
       if List.mem chr special_chars then
         List.cons "" (List.cons str_of_chr acc_arr)
       else
         match acc_arr with
         | h::t -> (String.concat "" [h;str_of_chr])::t
         | [] -> failwith "Array should never be empty"
    )
    [""]
    char_array) |> List.filter (fun str -> str <> "") |> List.rev

let replace_generics keywords str_arr =
  List.map
    (fun str ->
       if List.mem str keywords ||
        ((String.length str = 1) && (List.mem (String.get str 0) special_chars))
       then str
       else
         try
           int_of_string str |> string_of_int
         with Failure _ -> "v" )
    str_arr

let keywords_list keywords_file_name =
  (* [file_is_present file_name dir_handle] checks if [file_name] is present in
     the current directory. [dir_handle] represents the directory handle for
     the current directory.
  *)
  let rec file_is_present file_name dir_handle =
    try
      let curr_file = readdir dir_handle in
      if curr_file = keywords_file_name then
        true
      else file_is_present file_name dir_handle
    with End_of_file -> (
      closedir dir_handle;
      false)
  in
  (* [get_keywords acc in_channel] returns [acc] where [acc] is the accumulated
     list of keywords in the current file that [in_channel] is scanning through
  *)
  let rec get_keywords acc in_channel =
    try
      let new_line_words =
        input_line in_channel |>
        String.split_on_char ' ' |>
        List.filter (fun str -> str <> "") in
      get_keywords (acc @ new_line_words) in_channel
    with End_of_file -> (
      close_in in_channel;
      acc)
  in

  let dir_handle = opendir current_dir_name in

  if (file_is_present keywords_file_name dir_handle) then
    let in_channel = open_in
        (String.concat "" [current_dir_name; dir_sep; keywords_file_name]) in
    get_keywords [] in_channel
  else []

let remove_noise code_string keywords =
  code_string |>
  rem_white_space |>
  List.map split_and_keep_on_spec_chars |> List.flatten |>
  replace_generics keywords |> String.concat ""

let rec k_grams_helper acc s n =
  try
    let n_sub_str = String.sub s 0 n in
    let tail_str = String.sub s 1 ((String.length s)-1) in
    k_grams_helper (n_sub_str::acc) tail_str n
  with Invalid_argument _ -> List.rev acc

let k_grams s n = k_grams_helper [] s n

let hash_file f =
  let rec hash_helper f_channel s =
  try
    let line = input_line f_channel in
    hash_helper f_channel (s^line)
  with
  | End_of_file -> s in

  let f_string = hash_helper (open_in f) "" in

  let n_grams = k_grams (remove_noise f_string (keywords_list "")) 5 in

  List.map (Hashtbl.hash) n_grams
