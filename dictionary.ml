module type Comparable = sig
  type t
  val compare: t -> t -> int
end

module type Formattable = sig
  type t
  val format: t -> unit
end

type ('k,'v) two_three =
  | Leaf
  | TwoNode of ('k * 'v) * ('k,'v) two_three * ('k,'v) two_three
  | ThreeNode of ('k * 'v) * ('k * 'v) * ('k,'v) two_three * ('k,'v) two_three
                 * ('k,'v) two_three

module type Dictionary = sig
  module Key : Comparable
  module Value : Formattable
  type key = Key.t
  type value =  Value.t
  type t
  val empty : t
  val size : t -> int
  val member : key -> t -> bool
  val find : key -> t -> value option
  val insert : key -> value -> t -> t
  val to_list : t -> (key * value) list
end

module type DictionaryMaker =
  functor (K : Comparable) (V : Formattable)
    -> Dictionary with module Key = K and module Value = V

module TreeDictionary (K : Comparable) (V : Formattable) = struct

  module Key = K

  module Value = V

  type key = Key.t

  type value = Value.t

  type t = (key, value) two_three

  let empty = Leaf

  let rec size d =
    match d with
    | Leaf -> 0
    | TwoNode ((k1,v1),l,r) -> 1 + (size l) + (size r)
    | ThreeNode ((k1,v1),(k2,v2),l,m,r) -> 2 + (size l) + (size m) + (size r)

  let rec member k d = match d with
    | Leaf -> false
    | TwoNode ((k1,v1),l,r) -> if Key.compare k k1 = 0
      then true else if Key.compare k k1 < 0 then member k l else member k r
    | ThreeNode ((k1,v1),(k2,v2),l,m,r) ->
      if Key.compare k k1 = 0 || Key.compare k k2 = 0 then true
      else if Key.compare k k1 < 0 then member k l else if Key.compare k k2 > 0
      then member k r else member k m

  let rec find k d = match d with
    | Leaf -> None
    | TwoNode ((k1,v1),l,r) -> if Key.compare k k1 = 0
      then Some v1 else if Key.compare k k1 < 0 then find k l else find k r
    | ThreeNode ((k1,v1),(k2,v2),l,m,r) ->
      if Key.compare k k1 = 0 then Some v1 else if Key.compare k k2 = 0
      then Some v2 else if Key.compare k k1 < 0 then find k l else
      if Key.compare k k2 > 0 then find k r else find k m

  let insert k v d =
    let rec insert_helper k v d =
      match d with
        | Leaf -> (TwoNode((k,v),Leaf,Leaf),true)
        | TwoNode ((k1,v1),Leaf,Leaf) -> if Key.compare k k1 <= 0
          then (ThreeNode ((k,v),(k1,v1),Leaf,Leaf,Leaf),false)
          else (ThreeNode ((k1,v1),(k,v),Leaf,Leaf,Leaf), false)
        | ThreeNode ((k1,v1),(k2,v2),Leaf,Leaf,Leaf) -> if Key.compare k k1 <= 0
          then ((TwoNode((k1,v1),TwoNode((k,v),Leaf,Leaf),
                         TwoNode((k2,v2),Leaf,Leaf))),true)
          else if Key.compare k k2 > 0
          then ((TwoNode((k2,v2),TwoNode((k1,v1),Leaf,Leaf),
                         TwoNode((k,v),Leaf,Leaf))),true)
          else ((TwoNode((k,v),TwoNode((k1,v1),Leaf,Leaf),
                                 TwoNode((k2,v2),Leaf,Leaf))), true)
        | TwoNode ((k1,v1),l,r) -> if Key.compare k k1 < 0
          then (let leftinserted = insert_helper k v l in
              if snd leftinserted = true then
                (match (fst leftinserted) with
                | TwoNode((key, value), left, middle) ->
                  (ThreeNode((key,value), (k1,v1), left, middle ,r), false)
                | _ -> failwith "incorrect kickup")
              else (TwoNode((k1,v1), fst leftinserted, r)), false)
          else (let rightinserted = insert_helper k v r in
                if snd rightinserted = true then
                  (match fst rightinserted with
                   | TwoNode((key, value), middle, right)->
                     (ThreeNode((k1,v1), (key,value), l, middle, right), false)
                   | _ -> failwith "incorrect kickup" )
                else (TwoNode((k1,v1),l,fst rightinserted)), false)
        | ThreeNode ((k1,v1),(k2,v2),l,m,r) -> if Key.compare k k1 < 0
          then (let leftinserted = insert_helper k v l in
                if snd leftinserted = true then (match fst leftinserted with
                    | TwoNode((key, value), a, b) ->  (TwoNode((k1,v1),
                      TwoNode((key,value),a,b), TwoNode((k2,v2),m,r)), true)
                    | _ -> failwith "incorrect kickup")
                else (ThreeNode ((k1,v1),(k2,v2),(fst leftinserted),m,r),false))
          else if Key.compare k k2 > 0
          then (let rightinserted = insert_helper k v r in
                if snd rightinserted = true then (match fst rightinserted with
                    | TwoNode((key, value), c, d) -> (TwoNode((k2,v2),
                      TwoNode((k1,v1),l,m), TwoNode((key,value),c,d)), true)
                    | _ -> failwith "incorrect kickup")
                else (ThreeNode ((k1,v1),(k2,v2),l,m,fst rightinserted), false))
          else (let midinserted = insert_helper k v m in
                if snd midinserted = true then (match fst midinserted with
                    | TwoNode((key, value), b, c) -> (TwoNode((key,value),
                      TwoNode((k1,v1),l,b), TwoNode((k2,v2),c,r)), true)
                    | _ -> failwith "incorrect kickup")
                else (ThreeNode((k1,v1),(k2,v2),l,fst midinserted,r), false)) in
    fst (insert_helper k v d)

  let rec to_list d =
    let rec append lst1 lst2 =
      match lst1 with
      | [] -> lst2
      | h::t -> append t (h::lst2) in
    let rec to_list_helper d =
      match d with
      | Leaf -> []
      | TwoNode (a, b, c) -> (a::(append (List.rev(to_list b)) (to_list c)))
      | ThreeNode (a, b, c, d, e) ->
        (a::b ::(append (List.rev(append (List.rev(to_list c))(to_list d)))
                   (to_list e))) in
    List.sort (fun (k1,v1) (k2,v2) -> Key.compare k1 k2) (to_list_helper d)
end
