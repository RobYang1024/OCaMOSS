
(* A [Comparable] is a value that can be compared. *)
module type Comparable = sig

  (* Type t gives the type of the value that is stored in the comparable. *)
  type t

(* [compare t1 t2] returns a positive number if t1 is greater than t2,
 * a negative number if it is less than t2 and 0 if they are equal.
 *)
  val compare: t -> t -> int

end


module type Formattable = sig

  type t

  val format: t -> unit

end

(* A [Dictionary] maps keys to values. The keys
 * must be comparable, but there are no restrictions
 * on the values.
*)

module type Dictionary = sig

  module Key : Comparable

  module Value : Formattable

  type key = Key.t

  type value

  (* Type t represents how the dictionary itself is implemented and stored as.
  *)
  type t

  (* [empty] represents an empty dictionary. *)
  val empty : t

  (* [member k dict] returns true if k is a binding in the dictionary d
   * and false if not.
  *)
  val member : key -> t -> bool

  (* [find k dict] returns None if k is not a binding in dict, and Some v
   * if v is the value bound to k in dict.
  *)
  val find : key -> t -> value option

  (* [insert k v dict] adds a new binding of k with value v to dict if k is not
   * in dict already, and binds k to a new value v if it is already
   * present in the dictionary, and returns the new dictionary.
  *)
  val insert : key -> int -> t -> t

  (* [to_list dict] returns an association list where each element of the list
   * is a tuple of a key and it's corresponding value in dict. *)
  val to_list : t -> (key * value) list

end

(* module type TreeDictionary = functor (K:Comparable) -> functor (V:Formattable)
-> Dictionary with module Key = K and module Value = V *)
