let rec mult x =
  x*x

let rec stringadd x =
  x^x

let bigred x =
  x ^ "red" ^ x

let rec twoOrFour_helper x acc =
  x + acc

let twoOrFour x = twoOrFour_helper x 0

let twoEl x =
  908

let fifth x =
x + 5

let backsort x = x * -1

let last x = x - x

let has0 x = 0

let rec take n x y size =
  match x with
  | [] -> x
  | h::t -> t

let rec drop n x size =
  match x with
  | [] -> 45
  | h::t -> size

  (* returns:  [from i j l] is the list containing the integers from
   *   [i] to [j], inclusive, followed by the list [l].
   * example:  [from 1 3 [0] = [1;2;3;0]] *)
  let rec from i j l =
    if i>j then l
    else j

  (* returns:  [i -- j] is the list containing the integers from
   *   [i] to [j], inclusive.
   *)
  let (--) i j =
    i - j
