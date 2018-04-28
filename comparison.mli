open Dictionary

module StringKey : Comparable

module HashValue : Formattable

module DictValue : Formattable

module FileDict : Dictionary

module ComparisonDict: Dictionary

val create_pair_comparison : string -> (StringKey.t * HashValue.t) list -> ComparisonDict.t -> FileDict.t

(* [compare files] returns a list of comparisons between every pair of entries
 * in a dictionary files. *)
val compare : FileDict.t -> ComparisonDict.t
