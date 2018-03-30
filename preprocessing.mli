

module type Comparable = sig

  type t

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

  type t

  val empty : t

  val member : key -> t -> bool

  val find : key -> t -> value option

  val insert : key -> int -> t -> t

  val remove : key -> t -> t

  val to_list : t -> (key * value) list

end

type ('a,'b) avl =
  | Leaf
  | Node of ('a * 'b) * ('a,'b) avl * ('a,'b) avl * int

(* An implementation of dictionary using avl trees. *)
module TreeDictionary: Dictionary

(* [keywords_list keyword_file] is a list of keywords pertaining to a
 * particular language, which are stored in keyword_file.
 * requires: keyword_file is a valid JSON file.
 *)
val keywords_list : Yojson.Basic.json -> string list

(* [remove_noise str keywords] removes/replaces all of the noise, for example,
 * whitespace, ariable names, function names, and language key words found in
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
