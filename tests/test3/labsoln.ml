(********************************************************************
 * exercise: list expressions
 ********************************************************************)

let lst1 = [1;2;3;4;5]
let lst2 = 1::2::3::4::5::[]
let lst3 = [1]@[2;3;4]@[5]

(********************************************************************
 * exercise: product
 ********************************************************************)

(* returns: the product of all the elements of [lst], or [1] if [lst]
 *   is empty. 
 *)
let rec product lst =
  match lst with
  | [] ->  1
  | h::t -> h * product t

(* Here's a simpler way to write that using the [function] syntactic sugar
 *   discussed in the notes...
 *)
let rec product' = function
  | [] ->  1
  | h::t -> h * product' t

(********************************************************************
 * exercise: bad add
 ********************************************************************)

(* see the files add.ml and add_test.ml *)

(********************************************************************
 * exercise: patterns
 ********************************************************************)

(* returns:  whether the first element of the input is ["bigred"] 
 *)
let first_is_bigred = function
  | [] -> false
  | h::_ -> h = "bigred"

(* returns:  whether the input has exactly two or four elements 
 *)
let two_or_four_elements = function
  | _::_::[] -> true
  | _::_::_::_::[] -> true
  | _ -> false
  
(* returns: whether the first two elements of the input are equal 
 *)
let eq_first_two = function
  | a::b::_ -> a = b
  | _ -> false

(********************************************************************
 * exercise: library
 ********************************************************************)
 
(* returns:  the fifth element of the input list, or zero if the 
 *   list is empty 
 *)
let fifth_element lst =
  if (List.length lst) >= 5 then List.nth lst 4 else 0

(* returns: the input list, sorted in descending order 
 *)
let sort_list_descending lst = 
  List.rev (List.sort Pervasives.compare lst)
  
(* A more idiomatic way to write the above function is 
 *   with the pipeline operator.  This makes use of 
 *   partial application with List.sort. 
 *)
let sort_list_descending' lst = 
  lst |> List.sort Pervasives.compare |> List.rev 
  
(********************************************************************
 * exercise: library puzzle
 ********************************************************************)

(* returns: the last element of [lst]
 * requires: [lst] is nonempty
 *)
let last_element lst = 
  List.nth lst (List.length lst - 1)

(* another solution... 
 *)
let last_element' lst = 
  lst |> List.rev |> List.hd

(* returns: whether [lst] contains any zeros
 *)
let any_zeros lst = 
  List.exists (fun x -> x = 0) lst

(********************************************************************
 * exercise: take drop
 ********************************************************************)

(* returns:  [take n lst] is the first [n] elements of [lst], or
 *   just [lst] if [lst] has fewer than [n] elements. 
 * requires: [n >= 0]
 *)
let rec take n lst =
  if n = 0 then [] else match lst with
    | [] -> []
    | x::xs -> x :: take (n-1) xs

(* returns:  [drop n lst] is all but the first [n] elements of [lst],
 *   or just [[]] if [lst] has fewer than [n] elements.
 * requires: [n >= 0]
 *)
let rec drop n lst =
  if n = 0 then lst else match lst with
    | [] -> []
    | x::xs -> drop (n-1) xs
    
(********************************************************************
 * exercise: take drop tail
 ********************************************************************)

(* returns: [take_rev n xs acc] is [lst1 @ acc], where [lst] is
 *   the first [n] elements of [xs] (or just [xs] if [xs] has 
 *   fewer than [n] elements) in reverse order. 
 * requires: [n >= 0] *)
let rec take_rev n xs acc = 
  if n = 0 then acc else match xs with
    | [] -> acc
    | x::xs' -> take_rev (n-1) xs' (x::acc)

(* returns:  [take n lst] is the first [n] elements of [lst], or
 *   just [lst] if [lst] has fewer than [n] elements. 
 * requires: [n >= 0]
 *)
let take_tr n lst =
  take_rev n lst [] |> List.rev
  
(* In the solution above, we factored out a helper function called
 * [take_rev].  It is tail recursive.  The helper function takes
 * the same arguments as the original function, and one additional
 * argument [acc] called an *accumulator*.  We accumulate the answer
 * in it, eventually returning it when either [xs] is empty
 * or [n] is 0.  This is a general recipe one can follow in making
 * any recursive function be tail recursive. That same recipe was
 * followed in creating the definition of [(--)] below. *)

(* the "natural" solution to [drop] above is already tail recursive *)
let drop_tr = drop

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

let longlist = 0 -- 1_000_000

(* [take 1_000_000 longlist] should produce a stack overflow,
 * but [take_tr 1_000_000 longlist] should not. *)
 