(* comments are exactly same as prelimpract. functions are completely different

unit -> 'a
  True -> P

  ('a, ('b,'c))
  P and (Q and R)

  (('a*'b)or'*'c) or'
  (P or Q) or R

  void -> 'a
  False -> P

  ('a*'a neg) or'
  P \/ ~P

  'a*'b or' -> 'b*'a or'

  ('a*'b neg) or' -> ('b->'a)

  ('a*'b)-> 'a
  (P/\Q) -> P
  f(1,2) -> substitution {(1,2)/x} snd ((fun x -> x,x) (fst x)) -> snd ((fun x -> x,x) (fst (1,2)))
  function application-> snd ((fun x -> x,x) (1))
  substituion ->  snd ({1/x} x,x) -> snd (1,1)
  function app -> 1

  desugar
  let fact = rec fact -> fun x ->
  if x <= 1 then 1 else x * (fact (x - 1)) in
  fact 3

  step rec
  let fact =
  fun x ->
  if x <= 1 then 1 else x * (fact (x - 1)) {F/fact} in fact 3

  substitute
  let fact = fun x ->
  if x <= 1 then 1 else x * (F (x - 1)) in
  (fact) 3

  application
  if x <= 1 then 1 else x * (F (x - 1)) {3/x}
  -> if 3 <= 1 then 1 else 3 * (F (3 - 1))

  step bool
  if false then 1 else 3 * (F (3 - 1))

  3 * (rec fact -> fun x -> if x <= 1 then 1 else x * (fact (x-1))) 2

  3 * fun x -> if x <= 1 then 1 else x * (fact (x-1))) 2 {F/fact}

  3 * fun x -> if x <= 1 then 1 else x * (F (x-1))) 2

  3 * (if x <= 1 then 1 else x * (F (x-1))) {2/x})

  3 * (if 2 <= 1 then 1 else 2 * (F (2-1))))
  3 * (if false then 1 else 2 * (F (2-1))))

  3 * 2 * F (2-1)

  3 * 2 * rec fact -> fun x -> if x <= 1 then 1 else x * (fact (x-1)) (1)

  3 * 2 * (fun x -> if x <= 1 then 1 else x * (fact (x-1)) (1) {F/fact})

  3 * 2 * (fun x -> if x <= 1 then 1 else x * (F (x-1)) (1))

  3 * 2 * (if 1 <= 1 then 1 else 1 * (fact (1-1))

  3 * 2 * (if true then 1 else 1 * (fact (1-1))

  3 * 2 * 1

  6

  ('a*'b neg) or' -> ('b->'a)


  {} 110 + 3*1000 => 3110 op rule
    because {} 110 => 110 const rule
    and {} 3*1000 => 3000 op rule
      because {}3=> 3 const rule
      and {}1000=>1000 const rule
      and 3*1000 = 3000
    and 110 + 3000 = 3110

  {} if 2+3<4 then 1+1 else 2+2 => 4 if rule
    because {}2+3<4 => false op rule
      because {} 2+3 => 5 op rule
        because {} 2 => 2 const
        and {} 3 => 3 const
        and 2+3=5
      and {}4=> 4 const
      and 5<4 => false
    and {}2+2 => 4 op
      because {}2 => 2 const
      and {}2 => 2 const
      and 2++2 = 4

  {} let x=0 in x + (let x=1 in x) => 1 let rule
    because {}0=> 0 const rule
    and {(x,0)} (x + (let x=1 in x))=> 1 op +
      because {(x,0)} x => 0 var rule
      and {(x,0)} (let x = 1 in x) => 1 let rule
        because {x,0} 1=>1 const
        and {(x,1)} x => 1 var rule
      and 0 + 1 = 1

    {} match Left 2 with Left x-> x+1| Right x -> x-1 =>3 match rule
      because {}Left 2 => Left 2 left rule
        because 2=>2 const rule
      and {(x,2)} x+1 => 3 op +
        because x=>2 var rule
        and 1=>1 const rule
        and 2+1 = 3

  {} (fun x -> x+1) 2 => 3 application
    because {},fun x->x+1=> (|fun x -> x+1,{}|) closure
    and {}2 => 2 const
    and {(x,2)} x + 1 => 3 op +
      because x=>2 var
      and 1=>1 const
      and 1+2 = 3

  {} let f = fun x -> x+1 in f 2 => 3 let rule
    because {} fun x -> x+1 => (|fun x -> x+1|,{}) closure
    and {(f, fun x -> x+1)} f 2 => 3 application
      because {(f, fun x -> x+1)} f=> fun x -> x+1 var rule
      and {(f, fun x -> x+1)} 2=>2 const
      and {(x,2)} x + 1 => 3 op +
        because {(x,2)} x => 2 var
        and {(x,2)}1=>1 const
        and 2+1 = 3

  {} let x = 1 in let f = fun y -> x in let x = 2 in f 0 => 1 let rule
    because {}1=> 1 const
    and {(x,1)} let f = fun y -> x in let x = 2 in f 0 => 1 let rule
      because {(x,1)} fun y -> x => {fun y -> x, (x,1)} closure
      and {(x,1); (f, fun y -> x)} let x = 2 in f 0 => 1 let rule
        because {(x,1); (f, fun y -> x)} 2=>2 const
        and {(x,2); (f, closure)} f 0 => 1 application
          because f => {fun y -> x, (x,1)} var
          and {(x,2); (f, closure)} 0 =>0 const
          and {(x,1);(y,0)} x => 1 var rule



*)

type student = {
  name: string;
  mutable gpa: float;
}

let student1 = {
  name = "Alice";
  gpa = 3.7;
}

type vector = float array

let norm vec= sqrt (Array.fold_left (+.) 0. (Array.map (fun x -> x *. x) vec))

let normalize vec =
  for x = 0 to Array.length vec -1
  do vec.(x) <- vec.(x) * 2 done
