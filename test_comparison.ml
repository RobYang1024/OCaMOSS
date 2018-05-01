open OUnit2
open Dictionary
open Comparison

let emp_file = FileDict.empty

(*let single_entry = FileDict.insert "a" [1] emp_file*)

let emp_comp = CompDict.empty

let tests = [
  "empty_int" >:: (fun _ -> assert_equal [] (intersection [] []));
  "single_lists_uneq" >:: (fun _ -> assert_equal [] (intersection [1] [2]));
  "single_lists_eq" >:: (fun _ -> assert_equal [1] (intersection [1] [1]));
  "diff_order" >:: (fun _ -> assert_equal [1;2] ((intersection [1;2] [2;1]) |>
                                                 List.sort Pervasives.compare));
  "simp_case" >:: (fun _ -> assert_equal [1;2] ((intersection [3;1;2] [2;1]) |>
                                                List.sort Pervasives.compare));
  "long_case" >:: (fun _ -> assert_equal [7;20;41;53]
                      ((intersection [82;23;46;93;41;20;47;7;84;53]
                          [80;42;41;53;72;7;20;100])
                       |> List.sort Pervasives.compare));

  "empty_file" >:: (fun _ -> assert_equal emp_file
                       (make_pair_comp "" [] emp_comp));
  (*"single_entry" >:: (fun _ -> assert_equal single_entry
                         (make_pair_comp "a" [("a",[1])] emp_comp));*)

  "empty_comp" >:: (fun _ -> assert_equal emp_comp
                       (compare emp_file));

  
]
