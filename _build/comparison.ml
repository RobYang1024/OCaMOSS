open Dictionary

type file_dict = Dictionary

let compare d =

  let module StringKey = struct
    type t = string
    let compare k1 k2 = Pervasives.compare k1 k2
  end in

  let module DictValue = struct
    type t = file_dict
    let format d = ()
  end in

  let module ComparisonDict = TreeDictionary(StringKey)(DictValue) in

  let rec make_compare_dict compare_dict =
    d in

  make_compare_dict ComparisonDict.empty
