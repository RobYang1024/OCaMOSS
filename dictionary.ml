module type Comparable = sig
  type t
  val compare: t -> t -> int
end

module type Dictionary = sig
  module Key : Comparable
  type key = Key.t
  type value
  type t
  val empty : t
  val member : key -> t -> bool
  val find : key -> t -> value option
  val insert : key -> int -> t -> t
  val remove : key -> t -> t
  val to_list : t -> (key * value) list
end

(*  module TreeDictionary *)