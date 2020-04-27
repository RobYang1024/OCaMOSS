open Hashtbl
open Seq

module type Comparable = sig
  type t
  val compare: t -> t -> int
end

module type Formattable = sig
  type t
  val format: t -> unit
end

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

module HashtblDict (K : Comparable) (V : Formattable) = struct

  module Key = K

  module Value = V

  type key = Key.t

  type value = Value.t

  type t = Tbl of (key, value) Hashtbl.t

  let empty = Tbl(Hashtbl.create 10000)

  let size (Tbl d) = Hashtbl.length d

  let member k (Tbl d) = 
    match Hashtbl.find_opt d k with
    |Some _ -> true
    |None -> false

  let find k (Tbl d) = Hashtbl.find_opt d k

  let insert k v (Tbl d) = Hashtbl.add d k v ; Tbl(d)

  let to_list (Tbl d) = 
    let rec to_list_helper s l =
      match s with
      |Nil -> l
      |Cons(v, thunk) -> to_list_helper (thunk ()) (v::l)
    in
    to_list_helper ((to_seq d) ()) []
end
