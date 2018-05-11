let rec mult x =
  match x with
  |  [] -> 1
  |  h::t -> h * mult t

let rec stringadd x =
  match x with
  | [] -> ""
  | h::t -> h ^ stringadd t

let bigred x =
  match x with
  | [] -> false
  | h::t -> if h = "bigred" then true else false

let rec twoOrFour_helper x acc =
  match x with
  | [] -> if acc = 2 || acc = 4 then true else false
  | h::t -> twoOrFour_helper t (acc+1)

let twoOrFour x = twoOrFour_helper x 0

let twoEl x =
  match x with
  | [] -> false
  | h::[] -> false
  | h::h1::t -> if h=h1 then true else false

let fifth x =
  match x with
  | [] -> 0
  | h::t -> if List.length x <5 then 0 else List.nth x 5

let backsort x = List.rev (List.sort Pervasives.compare x)

let last x = List.nth x (List.length x - 1)

let has0 x = List.exists (fun x -> x=0) x

let rec take n x y size =
  match x with
  | [] -> y
  | h::t -> if n = size then y else take n t (List.append y [h]) (size + 1)

let rec drop n x size =
  match x with
  | [] -> x
  | h::t -> if n = size then x else drop n t (size + 1)

  (* returns:  [from i j l] is the list containing the integers from
   *   [i] to [j], inclusive, followed by the list [l].
   * example:  [from 1 3 [0] = [1;2;3;0]] *)
  let rec from i j l =
    if i>j then l
    else from i (j-1) (j::l)

  (* returns:  [i -- j] is the list containing the integers from
   *   [i] to [j], inclusive.
   *)
  let (--) i j =
    from i j []
