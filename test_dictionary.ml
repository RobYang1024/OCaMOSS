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

let t1 = DictString.(insert "A" "a" empty)
let t = DictString.(insert "L" "l" t1)
let t = DictString.(insert "G" "g" t)
let t = DictString.(insert "O" "o" t)
let t = DictString.(insert "R" "r" t)
let t = DictString.(insert "I" "i" t)
let t = DictString.(insert "T" "t" t)
let t = DictString.(insert "H" "h" t)
let t = DictString.(insert "M" "m" t)
let t = DictString.(insert "S" "s" t)

let tests = [
  "Inserted Correct" >:: (fun _ -> assert_equal [("A", "a"); ("G", "g");
  ("H", "h"); ("I", "i"); ("L", "l"); ("M", "m"); ("O", "o"); ("R", "r"); ("S", "s");
                                       ("T", "t")] (DictString.to_list t));
  "string_member" >:: (fun _ -> assert_equal true
                                          (DictString.member "A" t1));
  "member_empty" >:: (fun _ -> assert_equal false (DictString.(member "empty" empty)));
  "member_fake" >:: (fun _ -> assert_equal false (DictString.member "B" t1));
  "member_two" >:: (fun _ -> assert_equal true (DictString.member "R" t));
  "member_three" >:: (fun _ -> assert_equal true (DictString.member "S" t));

  "find_fake" >:: (fun _ -> assert_equal (None) (DictString.find "fake" t));
  "find_none" >:: (fun _ -> assert_equal (None) DictString.(find "K" empty));
  "find_first" >:: (fun _ -> assert_equal (Some("a"))
                       (DictString.find "A" t1));

  "find_two" >:: (fun _ -> assert_equal (Some "g") DictString.(find "G" t));
  "find_three" >:: (fun _ -> assert_equal (Some "m") DictString.(find "M" t));


]
