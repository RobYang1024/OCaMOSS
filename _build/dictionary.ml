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
  type value
  type t
  val empty : t
  val member : key -> t -> bool
  val find : key -> t -> value option
  val insert : key -> int -> t -> t
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

  let rec insert k v d =
    (*let ins_fix_leaf d = failwith "Unimplemented" in
    let ins_fix_two (k,v) l r = failwith "Unimplemented" in
    (*let ins_fix_three d = failwith "Unimplemented" in*)

    match d with
    | Leaf -> ins_fix_leaf ((k,v),Leaf,Leaf)
    | TwoNode ((k1,v1),Leaf,Leaf) -> if Key.compare k k1 < 0
      then ThreeNode ((k,v),(k1,v1),Leaf,Leaf,Leaf)
      else ThreeNode ((k1,v1),(k,v),Leaf,Leaf,Leaf)
    | ThreeNode ((k1,v1),(k2,v2),Leaf,Leaf,Leaf) -> if Key.compare k k1 < 0
      then ins_fix_two (k1,v1) (TwoNode ((k,v),Leaf,Leaf))
          (TwoNode ((k2,v2),Leaf,Leaf))
      else if Key.compare k k2 > 0
      then ins_fix_two (k2,v2) (TwoNode ((k1,v1),Leaf,Leaf))
          (TwoNode ((k,v),Leaf,Leaf))
      else ins_fix_two (k,v) (TwoNode ((k1,v1),Leaf,Leaf))
          (TwoNode ((k2,v2),Leaf,Leaf))
    | TwoNode ((k1,v1),l,r) -> if Key.compare k k1 < 0
      then TwoNode ((k1,v1),(insert k v l),r)
      else TwoNode ((k1,v1),l,(insert k v r))
    | ThreeNode ((k1,v1),(k2,v2),l,m,r) -> if Key.compare k k1 < 0
      then ThreeNode ((k1,v1),(k2,v2),(insert k v l),m,r)
      else if Key.compare k k2 > 0
      then ThreeNode ((k1,v1),(k2,v2),l,m,(insert k v r))
      else ThreeNode ((k1,v1),(k2,v2),l,(insert k v m),r)*)

    failwith "Unimplemented"

  let to_list d = failwith "Unimplemented"

end
