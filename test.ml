open OUnit2

let suite = "Final Project Test Suite" >:::
  Test_winnow.tests @ Test_comparison.tests @ Test_dictionary.tests @ Test_preprocessing.tests 

(* The following line must be the one and only place
 * in your entire source code that calls [OUnit2.run_test_tt_main]. *)
let _ = run_test_tt_main suite
