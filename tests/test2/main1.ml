open Ast

(* [is_value e] is whether [e] is a syntactic value *)
let is_value : expr -> bool = function
  | I _ | B _ -> true
  | Bop _ | Var _ | Let _ | If _ -> false

(* [subst e v x] is e{v/x}, that is, [e] with [v]
 * substituted for [x]. *)
let rec subst e v x = match e with
  | Var y       -> if x=y then v else e
  | B b      -> B b (* NEW *)
  | I n       -> I n
  | Bop(op,el,er) -> Bop(op, subst el v x, subst er v x)  (* NEW *)
  | Let(y,ebind,ebody) ->
      let ebind' = subst ebind v x in
      if x=y
      then Let(y, ebind', ebody)
      else Let(y, ebind', subst ebody v x)
  | If(eguard,ethen,eelse) ->  (* NEW *)
      If(subst eguard v x, subst ethen v x, subst eelse v x)

(* A single step of evaluation. *)
let rec step = function
  | I _ | B _ -> failwith "Does not step"
  | Var _ -> failwith "Unbound variable"
  | Bop(Plus, I n1, I n2) -> I (n1+n2)  (* NEW *)
  | Bop(Mult, I n1, I n2) -> I (n1*n2)  (* NEW *)
  | Bop(Leq, I n1, I n2) -> B (n1<=n2)  (* NEW *)
  | Bop(op, I n1, e2) -> Bop(op, I n1, step e2)  (* NEW *)
  | Bop(op, e1, e2) -> Bop(op, step e1, e2)
  | Let(x,I n,e2) -> subst e2 (I n) x
  | Let(x,B b,e2) -> subst e2 (B b) x
  | Let(x,e1,e2) -> Let(x,step e1, e2)
  | If(B true, e2, _) -> e2
  | If(B false, _, e3) -> e3
  | If(e1,e2,e3) -> If(step e1, e2, e3)

(* [eval e] is the [e -->* v] judgement.  That is,
 * keep applying [step] until a value is produced.  *)
let rec eval : expr -> expr = fun e ->
  if is_value e then e
  else eval (step e)

(* Parse a string Io an ast *)
let parse s =
  let lexbuf = Lexing.from_string s in
  let ast = Parser.prog Lexer.read lexbuf in
  ast

(* Extract a value from an ast node.
   Raises Failure if the argument is a node containing a value. *)
let extract_value = function
  | I i -> VI i
  | B b -> VB b  (* NEW *)
  | _ -> failwith "Not a value"

(* Interpret an expression *)
let interp e =
  e |> parse |> eval |> extract_value



