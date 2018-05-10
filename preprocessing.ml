open Winnowing
open Unix
open Filename
open Yojson.Basic.Util

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

let special_chars keywords_file_name =
  Yojson.Basic.from_file keywords_file_name
  |> member "special characters"
  |> to_list |> List.map to_string |> List.map (fun str -> String.get str 0)

let keywords_list keywords_file_name =
  Yojson.Basic.from_file keywords_file_name
  |> member "keywords"
  |> to_list |> List.map to_string

let split_and_keep_on_spec_chars spec_chars str =
  let char_array = str_to_chr_arr str in
  (List.fold_left
    (fun acc_arr chr ->
       let str_of_chr = String.make 1 chr in
       if List.mem chr spec_chars then
         List.cons "" (List.cons str_of_chr acc_arr)
       else
         match acc_arr with
         | h::t -> (String.concat "" [h;str_of_chr])::t
         | [] -> failwith "Array should never be empty")
    [""]
    char_array) |> List.filter (fun str -> str <> "") |> List.rev

let replace_generics keywords spec_chars str_arr =
  List.map
    (fun str ->
       if List.mem str keywords ||
        ((String.length str = 1) && (List.mem (String.get str 0) spec_chars))
       then str
       else
         try
           int_of_string str |> string_of_int
         with Failure _ -> "v" )
    str_arr

let remove_noise code_string keywords spec_chars =
  code_string |>
  rem_white_space |>
  List.map (split_and_keep_on_spec_chars spec_chars) |> List.flatten |>
  replace_generics keywords spec_chars |> String.concat ""

let k_grams s n =
  let rec k_grams_helper acc s n =
    try
      let n_sub_str = String.sub s 0 n in
      let tail_str = String.sub s 1 ((String.length s)-1) in
      k_grams_helper (n_sub_str::acc) tail_str n
    with Invalid_argument _ -> List.rev acc
  in
  k_grams_helper [] s n

let determine_keywords_file f =
  if check_suffix f "ml" || check_suffix f "mli" then "ocaml_keywords.json"
  else failwith "This file format is not supported"

let hash_file f k =
  let rec hash_helper f_channel s =
    try
      let line = input_line f_channel in
      hash_helper f_channel (s^line)
    with
    | End_of_file -> s in

    let keywords_file = determine_keywords_file f in
    let keywords = keywords_list keywords_file in
    let spec_chars = special_chars keywords_file in
    let f_string = hash_helper (open_in f) keywords_file in
    let n_grams = k_grams (remove_noise f_string keywords spec_chars) k in
    List.map (Hashtbl.hash) n_grams

let rec get_file_positions dir dir_name k filename positions =
  let rec hash_helper f_channel s =
      try
        let line = input_line f_channel in
        hash_helper f_channel (s^line)
      with
      | End_of_file -> s 
  in
  try
    let f_name = Unix.readdir dir in
    if f_name <> filename then get_file_positions dir dir_name k filename positions 
    else begin
      let keywords_file = determine_keywords_file filename in
      let keywords = keywords_list keywords_file in
      let spec_chars = special_chars keywords_file in
      let f_string = hash_helper (open_in filename) keywords_file in
      let n_grams = k_grams (remove_noise f_string keywords spec_chars) k in
      List.fold_left (fun a x -> (List.nth n_grams x)::a) [] (List.rev positions)
    end
  with
  | End_of_file -> []
