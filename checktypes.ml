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
  val keywords_list : string -> string list
  val special_chars : string -> char list
  val remove_noise : (string * string * string * bool * bool)
    ->string -> string list -> char list -> bool -> string
  val k_grams : string -> int -> string list
  val hash_file : string -> int list
  val get_file_positions : Unix.dir_handle ->
    string-> string -> int list -> (string * string) list
end

module type COMPARISON = sig
  module StringKey : Dictionary.Comparable
  module HashValue : Dictionary.Formattable
  module FileDict : Dictionary.Dictionary
  module DictValue : Dictionary.Formattable
  module CompDict: Dictionary.Dictionary
  val intersection : HashValue.t -> HashValue.t -> HashValue.t
  val make_pair_comp : string -> (StringKey.t * HashValue.t) list -> FileDict.t
  val compare : FileDict.t -> CompDict.t
  val create_sim_list : CompDict.t -> float -> (StringKey.t * float) list
  val create_pair_sim_list : StringKey.t -> (StringKey.t * HashValue.t) list ->
    (StringKey.t * float) list
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
    val to_list : 'a t -> 'a list
  end
  module Window : BoundedQueueWithCounter
  val winnow: int list -> int -> (int * int) list
end

module type MAIN = sig
  type color = RED | GREEN | CYAN | TEXT
  type state = {display: (color * string) list; directory: string; results:
                  Comparison.CompDict.t option; result_files:
                  (color * string) list; threshold: float}
  type cmd = RUN of string | DIR | HELP | SETDIR of string
           | RESULTS of string | PAIR | COMPARE of (string*string)| ERROR
  val parse: string -> cmd
  val repl : state -> unit
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
    val size : t -> int
    val member : key -> t -> bool
    val find : key -> t -> value option
    val insert : key -> value -> t -> t
    val to_list : t -> (key * value) list
  end

  module type DictionaryMaker =
    functor (K : Comparable)
      -> functor (V : Formattable)
      -> Dictionary with module Key = K and module Value = V

  module HashtblDict : DictionaryMaker
end

module CheckComparison : COMPARISON = Comparison
module CheckPreprocessing : PREPROCESSING = Preprocessing
module CheckWinnowing : WINNOWING = Winnowing
module CheckDictionary : DICTIONARY = Dictionary
module CheckMain : MAIN = Main
