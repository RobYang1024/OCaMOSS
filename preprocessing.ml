(* Variables into v
   Types into t
   Module type into MT
   Function names into f
   Functors names into F
   args into a
   *)

let rem_white_space code_string =
  code_string |>
  String.split_on_char ' ' |>
  List.filter (fun str -> str != "")

type acc_state =
  | Empty
  | Comment of int
  | Let
  | Fun
  | Type
  | Module
  | Sig

let token_acc_fun acc_st str =
  let (acc_ar, st, idx) = acc_st in


let gen_token_stream code_array =


let remove_noise code_string =
  code_string |>
  rem_white_space |>
