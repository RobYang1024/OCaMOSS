open OUnit2
open Preprocessing

let keywords =
  ["and"; "as"; "assert"; "asr"; "begin"; "class"; "constraint";
   "do"; "done"; "downto"; "else"; "end"; "exception"; "external";
   "false"; "for"; "fun"; "function"; "functor"; "if"; "in"; "include";
   "inherit"; "initializer"; "land"; "lazy"; "let"; "lor"; "lsl"; "lsr";
   "lxor"; "match"; "method"; "mod"; "module"; "mutable"; "new"; "nonrec";
   "object"; "of"; "open"; "or"; "private"; "rec"; "sig"; "struct"; "then";
   "to"; "true"; "try"; "type"; "val"; "virtual"; "when"; "while"; "with";
   "Arg"; "Array"; "ArrayLabels"; "Buffer"; "Bytes"; "BytesLabels"; "Callback";
   "Char"; "Complex"; "Digest"; "Ephemeron"; "Filename"; "Format"; "Gc";
   "Genlex"; "Hashtbl"; "Int32"; "Int64"; "Lazy"; "Lexing"; "List";
   "ListLabels"; "Map"; "Marshal"; "MoreLabels"; "Nativeint"; "Oo"; "Parsing";
   "Printexc"; "Printf"; "Queue"; "Random"; "Scanf"; "Set"; "Sort";
   "Spacetime"; "Stack"; "StdLabels"; "Stream"; "String"; "StringLabels";
   "Sys"; "Uchar"; "Weak"]

let spec_chars = ['!'; '$'; '%'; '&'; '*'; '+'; '-'; '.'; '/'; ':'; ';'; '<';
                  '='; '>'; '?'; '@'; '^'; '|'; '~'; '#'; '\"'; '('; ')'; ',';
                  '['; ']'; '{'; '}']

let comments_info_quad = ("", "(*", "*)", true)

let test_fun_str =
  "(* Hello World this is a comment *)
   (* (* This is a nested comment *) *)
   (* This is a multi line comment
      This is a multi line comment *)
   (* This is a multi line (* nested comment
      Wowie this is *) quite the comment *)
  let split_and_keep_on_spec_chars spec_chars str =
  let char_array = str_to_chr_arr str in
  (List.fold_left
    (fun acc_arr chr ->
       let str_of_chr = String.make 1 chr in
       if List.mem chr spec_chars then
         List.cons \"\" (List.cons str_of_chr acc_arr)
       else
         (* Hello I am yet another comment *)
         match acc_arr with
         | h::t -> (String.concat \"\" [h;str_of_chr])::t
         | [] -> failwith \"Array should never be empty\"
    )
    [\"\"]
    char_array) |> List.filter (fun str -> str <> \"\") |> List.rev"

let expected_res_str = String.concat ""
    ["letvvv=letv=vvin(List.v(funvv->letv=String.v1vinifList.vvvthenList.v\"\"";
     "(List.vvv)elsematchvwith|v::v->(String.v\"\"[v;v])::v|[]->v\"Arrayvvvv\"";
     ")[\"\"]v)|>List.v(funv->v<>\"\")|>List.v"]

    (*)
let test_fun_str2 =
  "let split_and_keep_on_spec_chars spec_chars str =
  let char_array = str_to_chr_arr str in
  (* asdf lasjhdkjasd List let char str in *)
  (List.fold_left
    (fun acc_arr chr ->
       let str_of_chr = String.make 1 chr in
       if List.mem chr spec_chars then
         List.cons \"\" (List.cons str_of_chr acc_arr)
       else
         match acc_arr with (* hello my name is potato *)
         | h::t -> (String.concat \"\" [h;str_of_chr])::t
         | [] -> failwith \"Array should never be empty\"
    )
    [\"\"]
    (* me too thanks *)
    char_array) |> List.filter (fun str -> str <> \"\") |> List.rev"

let expected_res_str2 = String.concat ""
    ["letvvv=letv=vvin(List.v(funvv->letv=String.v1vinifList.vvvthenList.v\"\"";
     "(List.vvv)elsematchvwith|v::v->(String.v\"\"[v;v])::v|[]->v\"Arrayvvvv\"";
      ")[\"\"]v)|>List.v(funv->v<>\"\")|>List.v"] *)

let test_fun_str3 = "wow(*wow wow wow*)wow"

let expected_res_str3 = "vv"

let test_fun_str4 = "wow (* wow wow wow"

let expected_res_str4 = "v"

let some_java_keywords = ["public"; "class"; "extends"]

let java_comments_quad = ("//", "/*", "*/", false)

let test_fun_str5 =
  "/**
     * A DumbAI is a Controller that always chooses the blank space with the
     * smallest column number from the row with the smallest row number.
     */
    public class DumbAI extends Controller"

let expected_res_str5 = "publicclassvextendsv"


(*Tests to check for preprocessing functionality*)
let tests = [
  "k_grams_2" >:: (fun _ -> assert_equal (k_grams "test" 3) ["tes"; "est"]);
  "k_grams_1" >:: (fun _ -> assert_equal (k_grams "Hello World" 5)
            ["Hello"; "ello "; "llo W"; "lo Wo"; "o Wor"; " Worl"; "World"]);
  "keywords" >:: (fun _ -> assert_equal
                            (keywords_list "ocaml_keywords.json") keywords);
  "spec_chars" >:: (fun _ -> assert_equal
                        (special_chars "ocaml_keywords.json") spec_chars);
  "comments_info" >:: (fun _ -> assert_equal
                      (comment_info "ocaml_keywords.json") comments_info_quad);
  "remove_noise" >::
    (fun _ -> assert_equal
      (remove_noise
        comments_info_quad
        test_fun_str
        keywords spec_chars
        false)
      expected_res_str);

  "remove_noise_3" >::
    (fun _ -> assert_equal
      (remove_noise
        comments_info_quad
        test_fun_str3
        keywords spec_chars
        false)
      expected_res_str3);

  "remove_noise_4" >::
    (fun _ -> assert_equal
      (remove_noise
        comments_info_quad
        test_fun_str4
        keywords spec_chars
        false)
      expected_res_str4);


  "remove_noise_5" >::
    (fun _ -> assert_equal
      (remove_noise
        java_comments_quad
        test_fun_str5
        some_java_keywords spec_chars
        false)
      expected_res_str5);
]
