open Dictionary

module StringKey : Comparable

module HashValue : Formattable

module FileDict : Dictionary

module DictValue : Formattable

module CompDict: Dictionary

val intersection : int list -> int list -> int list

val make_pair_comp : string -> (StringKey.t * HashValue.t) list -> CompDict.t -> FileDict.t

(* [compare files] returns a list of comparisons between every pair of entries
 * in a dictionary files. *)
val compare : FileDict.t -> CompDict.t

val create_sim_list : CompDict.t -> StringKey.t list
