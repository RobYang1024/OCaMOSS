open Winnowing
open Unix
open Filename
open Yojson.Basic.Util

let rem_white_space code_string =
  code_string |>
  String.split_on_char '\t' |> String.concat " " |>
  String.split_on_char '\n' |> String.concat " " |>
  String.split_on_char '\r' |> String.concat " " |>
  String.split_on_char ' ' |>
  List.filter (fun str -> str <> "")

let rec str_to_chr_arr str =
  let tail_len = String.length str - 1 in
  match str with
  | "" -> []
  | str -> (String.get str 0)::(str_to_chr_arr (String.sub str 1 tail_len))

let special_chars keywords_file_name =
  let path_to_keywords_file =
    String.concat "/" ["keywords_files"; keywords_file_name] in
  Yojson.Basic.from_file path_to_keywords_file
  |> member "special characters"
  |> to_list |> List.map to_string |> List.map (fun str -> String.get str 0)

let keywords_list keywords_file_name =
  let path_to_keywords_file =
    String.concat "/" ["keywords_files"; keywords_file_name] in
  Yojson.Basic.from_file path_to_keywords_file
  |> member "keywords"
  |> to_list |> List.map to_string

let comment_info keywords_file_name =
  let path_to_keywords_file =
    "keywords_files" ^ Filename.dir_sep ^ keywords_file_name in
  try
  let json = Yojson.Basic.from_file path_to_keywords_file in
  let one_line_comm_st = json |> member "comment" |> to_string in
  let mult_line_comm_st = json |> member "comment-start" |> to_string in
  let mult_line_comm_end = json |> member "comment-end" |> to_string in
  let comments_nest = json |> member "comments-nest" |> to_bool in
  let rm_string = json |> member "strings" |> to_bool in
  (one_line_comm_st, mult_line_comm_st, mult_line_comm_end,
   comments_nest, rm_string)
  with
  | _ -> failwith "z"

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

let rec split_on_str str_to_split_on acc_str_arr str_to_split =
  let str_to_split_on_len = String.length str_to_split_on in
  let str_to_split_len = String.length str_to_split in
  let new_acc_arr_else_case acc_str_arr str_to_split_on str_to_add =
    match acc_str_arr with
    | [] -> [str_to_add]
    | h::t ->
      if h = str_to_split_on then str_to_add::acc_str_arr
      else
        let new_acc_arr_hd = String.concat "" [h; str_to_add] in
        new_acc_arr_hd::t
  in
  if String.length str_to_split = 0 then List.rev acc_str_arr
  else
    try
      if str_to_split_on = (String.sub str_to_split 0 str_to_split_on_len) then
        let head_str = str_to_split_on in
        let tail_len = str_to_split_len - str_to_split_on_len in
        let tail_str = String.sub str_to_split str_to_split_on_len tail_len in
        split_on_str str_to_split_on (head_str::acc_str_arr)tail_str
      else
        let head_str = String.sub str_to_split 0 1 in
        let tail_len = str_to_split_len - 1 in
        let tail_str = String.sub str_to_split 1 tail_len in
        let new_acc_arr =
          new_acc_arr_else_case acc_str_arr str_to_split_on head_str in
        split_on_str str_to_split_on new_acc_arr tail_str
    with Invalid_argument _ ->
      let new_acc_arr =
        new_acc_arr_else_case acc_str_arr str_to_split_on str_to_split in
      List.rev new_acc_arr

let remove_strings code_str =
  let filter_from_arr (acc_arr, start) str =
    if str = "\"" && start = false then
      (" "::acc_arr, true)
    else if str = "\"" then
      (" "::acc_arr, false)
    else
      if start then (" s "::acc_arr, start)
      else (" "::str::acc_arr, false)
  in
  let split_on_comments_arr = split_on_str "\"" [] code_str in
  let acc_tup =
    List.fold_left filter_from_arr ([], false) split_on_comments_arr in
  match acc_tup with
  | (acc_arr, _) -> List.rev acc_arr

let remove_comments
    comment_start comment_end comments_nest code_str =
  let do_filter_from_arr (acc_arr, nesting) str =
    if str = comment_start then
      if comments_nest then (" "::acc_arr, nesting + 1)
      else (" "::acc_arr, 1)
    else if str = comment_end then
      if comments_nest then (" "::acc_arr, nesting - 1)
      else (" "::acc_arr, 0)
    else
      if nesting > 0 then (acc_arr, nesting)
      else (" "::str::acc_arr, 0)
  in
  let split_on_comments_arr =
    split_on_str comment_start [] code_str |>
    List.map (split_on_str comment_end []) |> List.flatten
  in
  let acc_tup =
    List.fold_left do_filter_from_arr ([], 0) split_on_comments_arr in
  match acc_tup with
  | (acc_arr, _) -> List.rev acc_arr

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

let remove_noise comment_tuple code_string keywords spec_chars is_txt =
  if is_txt then code_string
  else
    match comment_tuple with
    | (one_line_st, mult_line_st, mult_line_end, comments_nest, rm_string) ->
      let rm_one_line_comment s =
        if one_line_st = "" then s
        else
          remove_comments one_line_st "\n" false s
          |> String.concat ""
      in
      let rm_mult_line_comment s =
        if mult_line_st = "" then s
        else
          remove_comments mult_line_st mult_line_end comments_nest s
          |> String.concat ""
      in
      let rm_strings s =
        if not rm_string then s
        else remove_strings s
        |> String.concat ""
      in
      rm_strings code_string |> rm_mult_line_comment |> rm_one_line_comment |>
      split_and_keep_on_spec_chars spec_chars |>
      List.map rem_white_space |> List.flatten |>
      replace_generics keywords spec_chars |>
      String.concat ""

let k_grams s k =
  let rec k_grams_helper acc s n =
    try
      let n_sub_str = String.sub s 0 n in
      let tail_str = String.sub s 1 ((String.length s)-1) in
      k_grams_helper (n_sub_str::acc) tail_str n
    with Invalid_argument _ -> List.rev acc
  in
  k_grams_helper [] s k

let determine_keywords_file f =
  if check_suffix f "txt" then "txt_keywords.json"
  else if check_suffix f "ml" then "ocaml_keywords.json"
  else if check_suffix f "c" then "c_keywords.json"
  else if check_suffix f "java" then "java_keywords.json"
  else if check_suffix f "py" then "python_keywords.json"
  else failwith "This file format is not supported"

let hash_file f =
  let rec hash_helper f_channel s =
    try
      let line = input_line f_channel in
      hash_helper f_channel (s^line^"\n")
    with
    | End_of_file -> s in
    let keywords_file = determine_keywords_file f in
    let keywords = keywords_list keywords_file in
    let spec_chars = special_chars keywords_file in
    let f_string = hash_helper (open_in f) keywords_file in
    let is_txt = check_suffix f "txt" in
    let com_info = comment_info keywords_file in
    let noise_removed_str =
    remove_noise com_info f_string keywords spec_chars is_txt in
    let n_grams = k_grams noise_removed_str 35 in
    List.map (Hashtbl.hash) n_grams

let rec get_file_positions dir dir_name filename positions =
  let rec hash_helper f_channel s =
      try
        let line = input_line f_channel in
        hash_helper f_channel (s^line^"\n")
      with
      | End_of_file -> s
  in
  try
    let f_name = Unix.readdir dir in
    if f_name <> filename then get_file_positions dir dir_name filename
        positions
    else begin
      let f = dir_name ^ Filename.dir_sep ^ f_name in
      let keywords_file = determine_keywords_file f in
      let keywords = keywords_list keywords_file in
      let spec_chars = special_chars keywords_file in
      let channel = open_in f in
      let f_string = hash_helper channel keywords_file in
      let is_txt = check_suffix f "txt" in
      let com_info = comment_info keywords_file in
      let noise_removed_str =
        remove_noise com_info f_string keywords spec_chars is_txt in
      let n_grams = k_grams noise_removed_str 35 in
      let file = n_grams in
      let results = List.map (fun x ->
        (string_of_int x, List.nth file (x - 1))
      ) positions in
      List.sort (fun a b ->
        Pervasives.compare (snd a |> Hashtbl.hash) (snd b |> Hashtbl.hash)
      ) results
    end
  with
  | _ -> []
