
(* A [Comparable] is a value that can be compared. *)
module type Comparable = sig

  (* Type t gives the type of the value that is stored in the comparable. *)
  type t

(* [compare t1 t2] returns a positive number if t1 is greater than t2,
 * a negative number if it is less than t2 and 0 if they are equal.
 *)
  val compare: t -> t -> int

end

(* A [Dictionary] maps keys to values. The keys
 * must be comparable, but there are no restrictions
 * on the values.
 *)
module type Dictionary = sig

  module Key : Comparable

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

  (* [remove k dict] returns dict with binding k removed from it if it is
   * a binding in dict, and dict as it is otherwise.
   *)
  val remove : key -> t -> t

  (* [to_list dict] returns an association list where each element of the list
   * is a tuple of a key and it's corresponding value in dict. *)
  val to_list : t -> (key * value) list

end

(* An implementation of dictionary using avl trees. *)
module TreeDictionary: Dictionary

(* [keywords_list keyword_file] is a list of keywords pertaining to a
 * particular language, which are stored in a plaintext file with the name [keyword_file].
 * If a file with that name does not exist, then this function returns []
 *)
val keywords_list : string-> string list

(* [remove_noise str keywords] removes/replaces all of the noise, for example,
 * whitespace, variable names, function names, and language key words found in
 * [keywords] with more general names so that hashing can catch similarities
 * that it otherwise would not. *)
val remove_noise : string -> string list -> string

(* [k_grams str n] creates a list of strings of length n, starting at each
 * character in [str] up to and including ([str] length - [n])th character.
 *)
val k_grams : string -> int -> string list

(* [hash str] produces a hash of str *)
val hash : string -> int

(* [to_windows hashes win_size] creates a list of windows of size [win_size]
 * starting at each hash in [hashes] up to and including the
 * ([hashes] length - win_size)th hash. *)
val to_windows : int list -> int -> int list list

(* [winnow windows] Selects fingerprints from [windows] via winnowing *)
val winnow : int list list -> int list
