(* all comments gone. Constructor names/ type variables changed.
*)

type ('z,'f) or' = Blah of 'z | Bloo of 'f

let p_and_q_comm (p,q) = (q,p)

let p_or_q_comm p_or_q = match p_or_q with
  | Blah a -> Bloo a
  | Bloo b -> Blah b

type hi = {hehe : 'm .'m}
type 'va neg = 'va -> hi
let explode (f:hi) : 'b = f.hehe

let ex (p, (q:'a neg)) = explode (q p)
let f x  = fst x
