type 'a tree = Leaf | Node of 'a tree * 'a * 'a tree

let rec preorder a = function
  | Leaf -> (match a with
     | [] -> []
     | h::t -> preorder t h)
  | Node (l, v, r) -> v:: preorder (r::a) l

let rec inorder a = function
  | Leaf -> []
  | Node (Leaf, v, _) -> (match a with
      | [] -> []
      | h::t -> v::inorder t h)
  | Node (l,v,r) -> inorder 
