(* Variables into v
   Types into t
   Function names into f
   *)

let rem_white_space code_string =
  code_string |>
  String.split_on_char ' ' |>
  List.filter (fun str -> str != "")

let FunctionName test_arg =

let remove_noise code_string =
  code_string |>
  rem_white_space
