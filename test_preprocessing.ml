open OUnit2
open Preprocessing

let keywords =
  ["and"; "as"; "assert"; "asr"; "begin"; "class"; "constraint";
   "do"; "done"; "downto"; "else"; "end"; "exception"; "external";
   "false"; "for"; "fun"; "function"; "functor"; "if"; "in"; "include";
   "inherit"; "initializer"; "land"; "lazy"; "let"; "lor"; "lsl"; "lsr";
   "lxor"; "match"; "method"; "mod"; "module"; "mutable"; "new"; "nonrec";
   "object"; "of"; "open"; "or"; "private"; "rec"; "sig"; "struct"; "then";
   "to"; "true"; "try"; "type"; "val"; "virtual"; "when"; "while"; "with"]

let spec_chars = ['!'; '$'; '%'; '&'; '*'; '+'; '-'; '.'; '/'; ':'; ';'; '<';
                  '='; '>'; '?'; '@'; '^'; '|'; '~'; '#'; '\"'; '('; ')'; ',';
                  '['; ']'; '{'; '}']

let test_fun_str =
  "let split_and_keep_on_spec_chars spec_chars str =
  let char_array = str_to_chr_arr str in
  (List.fold_left
    (fun acc_arr chr ->
       let str_of_chr = String.make 1 chr in
       if List.mem chr spec_chars then
         List.cons \"\" (List.cons str_of_chr acc_arr)
       else
         match acc_arr with
         | h::t -> (String.concat \"\" [h;str_of_chr])::t
         | [] -> failwith \"Array should never be empty\"
    )
    [\"\"]
    char_array) |> List.filter (fun str -> str <> \"\") |> List.rev"

let expected_res_str = String.concat ""
    ["letvvv=letv=vvin(v.v(funvv->letv=v.v1vinifv.vvvthenv.v\"\"(v.vvv)else";
     "matchvwith|v::v->(v.v\"\"[v;v])::v|[]->v\"vvvvv\")[\"\"]v)|>v.v(funv->v";
     "<>\"\")|>v.v"]

(*Tests to check for preprocessing functionality*)
let tests = [
  "k_grams_2" >:: (fun _ -> assert_equal (k_grams "test" 3) ["tes"; "est"]);
  "k_grams_1" >:: (fun _ -> assert_equal (k_grams "Hello World" 5)
            ["Hello"; "ello "; "llo W"; "lo Wo"; "o Wor"; " Worl"; "World"]);
  "keywords" >:: (fun _ -> assert_equal
                            (keywords_list "ocaml_keywords.json") keywords);
  "spec_chars" >:: (fun _ -> assert_equal
                              (special_chars "ocaml_keywords.json") spec_chars);
  "remove_noise" >:: (fun _ -> assert_equal
                                 (remove_noise
                                    test_fun_str
                                    keywords spec_chars)
                                 expected_res_str);

]
