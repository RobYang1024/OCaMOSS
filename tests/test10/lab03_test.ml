open OUnit
open Lab03

let tests = "testing lab03" >::: [
    "prod1" >:: (fun _ -> assert_equal 1 (mult []));
    "prod2" >:: (fun _ -> assert_equal 24 (mult [1;2;3;4]));
    "prod3" >:: (fun _ -> assert_equal 60 (mult [3;4;5]));

    "bigred" >:: (fun _ -> assert_equal true (bigred ["bigred"; "i"]));
    "bigred1" >:: (fun _ -> assert_equal false (bigred []));
    "bigred2" >:: (fun _ -> assert_equal false (bigred ["hello"]));

    "twoorfour3" >:: (fun _ -> assert_equal true (twoOrFour [1;2;3;4]));
    "twoorfour" >:: (fun _ -> assert_equal true (twoOrFour ["bigred"; "i"]));
    "twoorfour1" >:: (fun _ -> assert_equal false (twoOrFour []));
    "twoorfour2" >:: (fun _ -> assert_equal false (twoOrFour ["hello"]));
    "twoorfour4" >:: (fun _ -> assert_equal false (twoOrFour [1;2;3;4;5;5]));

    "1" >:: (fun _ -> assert_equal true (twoEl [1;1;3]));
    "2" >:: (fun _ -> assert_equal false (twoEl [1;2;3]));
    "3" >:: (fun _ -> assert_equal false (twoEl [1;3]));
    "4" >:: (fun _ -> assert_equal false (twoEl [1]));
    "5" >:: (fun _ -> assert_equal true (twoEl [1;1]));






  ]

let _ = run_test_tt_main tests
