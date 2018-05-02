open OUnit2
open Preprocessing

let keywords =
  ["let"; "try"; "with"; "Failure"; "fun"; "failwith"; "if"; "then"; "else"; "match"; "with"; "in"]

(*Tests to check for preprocessing functionality*)
let tests = [
  "k_grams_2" >:: (fun _ -> assert_equal (k_grams "test" 3) ["tes"; "est"]);
  "k_grams_1" >:: (fun _ -> assert_equal (k_grams "Hello World" 5)
            ["Hello"; "ello "; "llo W"; "lo Wo"; "o Wor"; " Worl"; "World"]);
  "remove_noise_1" >::
    (fun _ ->
      assert_equal
        (remove_noise
           "let x = try some_expr 58 with Failure -> (fun a -> a>12)"
           keywords)
        "letv=tryv58withFailure->(funv->v>12)");
  "remove_noise_2" >::
  (fun _ ->
    assert_equal
      (remove_noise
         "let split_and_keep_on_spec_chars str =
  let char_array = str_to_chr_arr str in
  (List.fold_left
    (fun acc_arr chr ->
       let str_of_chr = String.make 1 chr in
       if List.mem chr special_chars then
         List.cons \"\" (List.cons str_of_chr acc_arr)
       else
         match acc_arr with
         | h::t -> (String.concat \"\" [h;str_of_chr])::t
         | [] -> failwith \"Array should never be empty\"
    )
    [\"\"]
    char_array) |> List.filter (fun str -> str <> \"\") |> List.rev"
         keywords)
    "letvv=letv=vvin(v.v(funvv->letv=v.v1vinifv.vvvthenv.v\"\"(v.vvv)elsematchvwith|v::v->(v.v\"\"[v;v])::v|[]->failwith\"vvvvv\")[\"\"]v)|>v.v(funv->v<>\"\")|>v.v");
]
