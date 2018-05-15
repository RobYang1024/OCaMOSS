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
let t2 = DictString.(insert "L" "l" t1)
let t3 = DictString.(insert "G" "g" t2)
let t4 = DictString.(insert "O" "o" t3)
let t5 = DictString.(insert "R" "r" t4)
let t6 = DictString.(insert "I" "i" t5)
let t7 = DictString.(insert "T" "t" t6)
let t8 = DictString.(insert "H" "h" t7)
let t9 = DictString.(insert "M" "m" t8)
let t = DictString.(insert "S" "s" t9)

let tests = [
  "insert first" >::(fun _ -> assert_equal [("A","a")] (DictString.to_list t1));
  "insert 2" >:: (fun _ -> assert_equal [("A","a");("L","l")]
   (DictString.to_list t2));
  "insert 2" >:: (fun _ -> assert_equal [("A","a"); ("G", "g");("L","l")]
  (DictString.to_list t3));
  "insert 3" >:: (fun _ -> assert_equal [("A","a"); ("G", "g");("L","l");
                                         ("O","o")] (DictString.to_list t4));
  "insert 4" >:: (fun _ -> assert_equal
   [("A","a"); ("G","g");("L","l"); ("O","o");("R","r")]
   (DictString.to_list t5));
  "insert 5" >:: (fun _ -> assert_equal
  [("A","a"); ("G","g"); ("I", "i");("L","l"); ("O","o");("R","r")]
  (DictString.to_list t6));
  "insert 6" >:: (fun _ -> assert_equal
    [("A","a"); ("G","g");("I","i");("L","l"); ("O","o");("R","r"); ("T","t")]
    (DictString.to_list t7));
  "insert 7" >:: (fun _ -> assert_equal
    [("A","a"); ("G","g");("H","h");("I","i");("L","l"); ("O","o");("R","r");
                      ("T","t")] (DictString.to_list t8));
  "insert 8" >:: (fun _ -> assert_equal
    [("A","a"); ("G","g");("H","h");("I","i");("L","l");
    ("M","m");("O","o");("R","r"); ("T","t")]
    (DictString.to_list t9));
  "Inserted Correct" >:: (fun _ ->
      assert_equal [("A", "a"); ("G", "g"); ("H", "h"); ("I", "i"); ("L", "l");
                    ("M", "m"); ("O", "o"); ("R", "r"); ("S", "s"); ("T", "t")]
        (DictString.to_list t));


  "size_empty" >:: (fun _ -> assert_equal 0 DictString.(size empty));
  "size_1" >:: (fun _ -> assert_equal 1 DictString.(size t1));
  "size_mid" >:: (fun _ -> assert_equal 5 DictString.(size t5));
  "size_full" >:: (fun _ -> assert_equal 10 DictString.(size t));

  "string_member" >:: (fun _ -> assert_equal true
                                          (DictString.member "A" t1));
  "member_empty" >:: (fun _ -> assert_equal false
                         (DictString.(member "empty" empty)));
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
