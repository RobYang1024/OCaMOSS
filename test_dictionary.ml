open Dictionary
open OUnit2

module ValString = struct
  type t = string
  let format s =
    print_string s;
end

module KeyString = struct
  type t = string
  let compare x y = Pervasives.compare x y
end

module DictString  = TreeDictionary (KeyString) (ValString)

let t = DictString.(insert "A" "" empty)
let t = DictString.(insert "L" "" t)
let t = DictString.(insert "G" "" t)
let t = DictString.(insert "O" "" t)
let t = DictString.(insert "R" "" t)
let t = DictString.(insert "I" "" t)
let t = DictString.(insert "T" "" t)
let t = DictString.(insert "H" "" t)
let t = DictString.(insert "M" "" t)
let t = DictString.(insert "S" "" t)
    (*Should be listed in a DFS fashion*)

let tests = [

  "Inserted Correct" >:: (fun _ -> assert_equal [("I", ""); ("G", "");
  ("A", ""); ("H", ""); ("O", ""); ("S", ""); ("L", ""); ("M", ""); ("R", "");
  ("T", "")] (DictString.to_list t));
]
