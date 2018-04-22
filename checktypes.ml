(* (******************************************************************)
(* TODO                                        *)
(******************************************************************)

module type DATA = sig
  module type Formattable = sig
    type t
    val format : Format.formatter -> t -> unit

  end
  module type Comparable = sig
    type t
    val compare : t -> t -> [`LT | `EQ | `GT]
    include Formattable with type t := t

  end
  type ('k,'v) avl =
    | Leaf
    | Node of ('k * 'v) * ('k,'v) avl * ('k,'v) avl * int

  module type Dictionary = sig
    module Key : Comparable
    module Value : Formattable
    type key = Key.t
    type value = Value.t
    type t
    val rep_ok : t  -> t
    val empty : t
    val is_empty : t -> bool
    val size : t -> int
    val insert : key -> value -> t -> t
    val member : key -> t -> bool
    val find : key -> t -> value option
    val remove : key -> t -> t
    val choose : t -> (key * value) option
    val fold : (key -> value -> 'acc -> 'acc) -> 'acc -> t -> 'acc
    val to_list : t -> (key * value) list
    val expose_tree : t -> (key, value) avl
    val format : Format.formatter -> t -> unit
  end
  module type DictionaryMaker =
    functor (K : Comparable)
    -> functor (V : Formattable)
       -> Dictionary with module Key = K and module Value = V
  module MakeListDictionary : DictionaryMaker
  module MakeTreeDictionary : DictionaryMaker
  module type Set = sig
    module Elt : Comparable
    type elt = Elt.t
    type t
    val rep_ok : t  -> t
    val empty : t
    val is_empty : t -> bool
    val size : t -> int
    val insert : elt -> t -> t
    val member : elt -> t -> bool
    val remove : elt -> t -> t
    val union : t -> t -> t
    val intersect : t -> t -> t
    val difference : t -> t -> t
    val choose : t -> elt option
    val fold : (elt -> 'acc -> 'acc) -> 'acc -> t -> 'acc
    val to_list : t -> elt list
    val format : Format.formatter -> t -> unit

  end
  module MakeSetOfDictionary
         : functor (C : Comparable)
           -> functor (DM : DictionaryMaker)
              -> Set with module Elt = C

end (* DATA *)

module type ENGINE = sig

  module type Engine = sig
    type idx
    val index_of_dir : string -> idx
    val to_list : idx -> (string * string list) list
    val or_not  : idx -> string list -> string list -> string list
    val and_not : idx -> string list -> string list -> string list
    val format : Format.formatter -> idx -> unit
  end

  module ListEngine : Engine
  module TreeEngine : Engine

end (* ENGINE *)

module type TEST_DATA = sig
  module type Tests = sig
    val tests : OUnit2.test list
  end
  module DictTester (M:Data.DictionaryMaker) : Tests
  val tests : OUnit2.test list
end

module type TEST_ENGINE = sig
  val tests : OUnit2.test list
end

module CheckData : DATA = Data
module CheckEngine : ENGINE = Engine
module CheckTest_data : TEST_DATA = Test_data
module CheckTest_engine : TEST_ENGINE = Test_engine *)
