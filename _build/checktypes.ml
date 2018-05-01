(******************************************************************)
(* TODO                                        *)
(******************************************************************)

(*
module type TEST_DATA = sig
  module type Tests = sig
    val tests : OUnit2.test list
  end
  module DictTester (M:Data.DictionaryMaker) : Tests
  val tests : OUnit2.test list
end

module type TEST_ENGINE = sig
  val tests : OUnit2.test list
end *)

module type PREPROCESSING =  sig
  val keywords_list : string-> string list
  val remove_noise : string -> string list -> string
  val k_grams : string -> int -> string list
  val hash : string -> int
  val winnow : int list -> int -> (int * int) list
end

module type COMPARISON = sig
  module StringKey : Dictionary.Comparable
  module HashValue : Dictionary.Formattable
  module FileDict : Dictionary.Dictionary
  module DictValue : Dictionary.Formattable
  module CompDict: Dictionary.Dictionary
  val intersection : int list -> int list -> int list
  val make_pair_comp : string ->
    (StringKey.t * HashValue.t) list -> CompDict.t -> FileDict.t
  val compare : FileDict.t -> CompDict.t
  val create_sim_list : CompDict.t -> StringKey.t list
end

module type WINNOWING = sig
  module type BoundedQueueWithCounter = sig
    type 'a t
    val empty : int -> 'a t
    val create : int -> 'a -> 'a t
    val is_empty : 'a t -> bool
    val is_full : 'a t -> bool
    val size : 'a t -> int
    val enqueue : 'a -> 'a t -> 'a t
    val dequeue : 'a t -> 'a option * 'a t
    val count : 'a t -> int
    val fold : ('b -> 'a -> 'b) -> 'b -> 'a t -> 'b
  end
  module Window : BoundedQueueWithCounter
  val winnow: int list -> int -> (int * int) list
end

module type DICTIONARY = sig
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
    type value = Value.t
    type t
    val empty : t
    val member : key -> t -> bool
    val find : key -> t -> value option
    val insert : key -> value -> t -> t
    val to_list : t -> (key * value) list
  end

  module type DictionaryMaker =
    functor (K : Comparable)
      -> functor (V : Formattable)
      -> Dictionary with module Key = K and module Value = V

  module TreeDictionary : DictionaryMaker
end

module CheckComparison : COMPARISON = Comparison
module CheckPreprocessing : PREPROCESSING = Preprocessing
module CheckWinnowing : WINNOWING = Winnowing
module CheckDictionary : DICTIONARY = Dictionary
