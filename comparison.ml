open Dictionary

type file_dict = Dictionary

type pair_comparison = unit

let compare d = (*let list_of_dict = file_dict.to_list d in*)
  let module StringKey = struct
    type t = string
    let compare k1 k2 = Pervasives.compare k1 k2
  end in

  let module DictValue = struct
    type t = file_dict
    let format d = ()
  end in

  let module ComparisonDict = TreeDictionary (StringKey) (DictValue) in

  let make_compare_dict d = d in

  (*make_compare_dict ComparisonDict*) failwith "Unimplemented"
