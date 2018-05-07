(* Abstraction Function: [[x_1, ..., x_n]] represents the set
 *   ${x_1, ..., x_n}$.
 * Representation Invariant: [[x_1, ..., x_n]] must form a strictly increasing
 *  sequence. That is, the list should be sorted with no duplicates.
 *)
type t =
  int list

let rep_ok s =
  if s = List.sort_uniq compare s then
    s
  else
    failwith "RI"


let empty =
  [] |> rep_ok

let size s =
  List.length (rep_ok s)

let insert x s =
  List.sort_uniq compare (x :: rep_ok s) |> rep_ok

let member x s =
  List.mem x (rep_ok s)

let remove x s =
  List.filter (fun y -> x <> y) (rep_ok s) |> rep_ok

let choose s =
  match (rep_ok s) with
  | [] -> None
  | x :: xs -> Some x


let to_list s =
  rep_ok s
