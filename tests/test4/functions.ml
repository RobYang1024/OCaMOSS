(********************************************************************
 * exercise: fib
 ********************************************************************)

(* returns: element [n] of the Fibonacci sequence *)
(* requires: [n >= 0] *)
let rec fib n =
  if n = 0 then 0
  else if n = 1 then 1
  else fib (n-1) + fib (n-2)

(********************************************************************
 * exercise: fib fast
 ********************************************************************)

(* requires: n > 0 *)
(* returns: element [n] of the Fibonacci-like sequence, assuming
 *   the first two elements are [pp] and [p]. *)
let rec h n pp p =
  if n = 1 then p
  else h (n-1) p (pp+p)

(* returns: element [n] of the Fibonacci sequence
 * requires: [n >= 0] *)
let fib_fast n =
  if n=0 then 0
  else h n 0 1
  
(* on a 64-bit OCaml implementation, fib_fast 91 overflows. *)
  
(********************************************************************
 * exercise: poly types
 ********************************************************************)

let f x = if x then x else x
(* bool -> bool *)
(* x must be a bool to to be used as the conditional in the if expression *)

let g x y = if y then x else x
(* 'a -> bool -> 'a *)
(* x could have any type *)

let h x y z = if x then y else z
(* bool -> 'a -> 'a -> 'a *)
(* both branches of the if expression must have the same type,
 *  so y and z must have the same type (which could be anything) *)

let i x y z = if x then y else y
(* bool -> 'a -> 'b -> 'a *)
(* z could have any type, and moreover, that type could be different
 *  than the type of y *)

(********************************************************************
 * exercise: divide
 ********************************************************************)

let divide ~numerator:x ~denominator:y = x /. y

(********************************************************************
 * exercise: addx
 ********************************************************************)

let addx x = fun y -> x + y
(* int -> int -> int*)

let add5 = addx 5
(* int -> int *)
(* [add 5 2] is equivalent to [fun y -> 5 y]
   so [add5 2] evaluates to 7 *)

let add3 = addx 3
(* int -> int *)
(* add3 8 = 11 *)

(********************************************************************
 * exercise: associativity 
 ********************************************************************)

let add x y = x+y

(* Only add (5 1) produces an error, because it tries to apply
 * the value 5 to the argument 1, but 5 is not a function, so it
 * cannot be applied. *)

(********************************************************************
 * exercise: average 
 ********************************************************************)

let ($$) a b =
  (a +. b) /. 2.
  