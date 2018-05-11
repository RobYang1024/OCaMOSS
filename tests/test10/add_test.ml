open OUnit
open Add

let tests = "test suite for add" >::: [
    "onetwo" >:: (fun _ -> assert_equal 3 (add 1 2));
    "sevone" >:: (fun _ -> assert_equal 8 (add 7 1));
]

let _ = run_test_tt_main tests
