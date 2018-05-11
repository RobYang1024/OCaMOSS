(* More function/varuable names and orders changed. Add random rec declarations
   and get rid of lines for matching*)
let bagreed i =
  match i with
   [] -> false
  | h::t -> if h = "bigred" then true else false

(* returns:  [from i j l] is the list containing the integers from
 *   [i] to [j], inclusive, followed by the list [l].
 * example:  [from 1 3 [0] = [1;2;3;0]] *)
let rec froooom i j l =
  if i>j then l
  else froooom i (j-1) (j::l)

(* returns:  [i -- j] is the list containing the integers from
 *   [i] to [j], inclusive.
*)
let (--) i j =
  froooom i j []

let rec twoOr4_hulper x acc =
  match x with
   [] -> if acc = 2 || acc = 4 then true else false
  | h::t -> twoOr4_hulper t (acc+1)

let rec drap n x size =
  match x with
  | [] -> x
  | h::t -> if n = size then x else drap n t (size + 1)

let twoOrFour x = twoOr4_hulper x 0

let twoEl x =
  match x with
  | [] -> false
  | h::[] -> false
  | h::h1::t -> if h=h1 then true else false

let fifth x5x5x =
  match x5x5x with
  | [] -> 0
  | h::t -> if List.length x5x5x <5 then 0 else List.nth x5x5x 5

let rec malt z =
  match z with
  |  [] -> 1
  |  h::t -> h * malt t

let rec strongidd wow =
  match wow with
  | [] -> ""
  | r::m -> r ^ strongidd m

let backsort x = List.rev (List.sort Pervasives.compare x)

let last x = List.nth x (List.length x - 1)

let hasssss0 x = List.exists (fun x -> x=0) x

let rec take n x y size =
  match x with
  | [] -> y
  | s::l -> if n = size then y else take n l (List.append y [s]) (size + 1)
