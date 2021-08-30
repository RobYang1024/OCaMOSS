open OUnit2
open Dictionary
open Comparison

let assoc_compare l1 l2 =
  if List.length l1 <> List.length l2 then false
  else 
    List.fold_left (fun acc (k,v) -> acc && List.assoc k l2 = v) true l1

let unordered_list_compare l1 l2 =
  if List.length l1 <> List.length l2 then false
  else 
    List.fold_left (fun acc x -> acc && List.mem x l2) true l1

let filedict_equal d1 d2 = 
  assert_equal ~cmp:assoc_compare (FileDict.to_list d1) (FileDict.to_list d2)

let compdict_equal d1 d2 = 
  assert_equal ~cmp:assoc_compare (CompDict.to_list d1) (CompDict.to_list d2)

let emp_file = FileDict.empty

let se_dict = FileDict.insert "a" [(1,0)] emp_file
let de_dict = FileDict.(empty |> insert "a" [(1,0);(2,0)]
                        |> insert "b" [(1,0);(3,0)])
let emp_e_dict = FileDict.(empty |> insert "a" [] |> insert "b" [(7,0);(4,0)]
                           |> insert "c" [(4,0);(8,0);(13,0)])

let def_p_comp = FileDict.(empty |> insert "a" [(1,0);(2,0)]
                           |> insert "b" [(1,0)])
let des_p_comp = FileDict.(empty |> insert "a" [(1,0)]
                           |> insert "b" [(1,0);(3,0)])
let emp_e_f_p_comp = FileDict.(empty |> insert "a" [] |> insert "b" []
                               |> insert "c" [])
let emp_e_s_p_comp = FileDict.(empty |> insert "a" []
                               |> insert "b" [(7,0);(4,0)]
                               |> insert "c" [(4,0)])
let emp_e_t_p_comp = FileDict.(empty |> insert "a" [] |> insert "b" [(4,0)]
                               |> insert "c" [(4,0);(8,0);(13,0)])


let emp_comp = CompDict.empty
let se_comp = CompDict.(insert "a" se_dict empty)
let def_comp = CompDict.(insert "a" def_p_comp empty)
let de_comp = CompDict.insert "b" des_p_comp def_comp
let emp_e_comp = CompDict.(empty |> insert "a" emp_e_f_p_comp
                           |> insert "b" emp_e_s_p_comp
                           |> insert "c" emp_e_t_p_comp)

let get_files = List.map (fun (k,s) -> k)

let tests = [

  (* The test cases for intersection has cases that test what happens with
   * one or more empty lists, single element lists, 2 lists with the order
   * reversed, lists with elements of same hash but different position,
   * and lists with different number of elements, which are all the edge
   * cases, and then a couple of general cases. *)
  "empty lists" >:: (fun _ -> assert_equal ~cmp:assoc_compare [] (intersection [] []));
  "one list empty" >:: (fun _ -> assert_equal ~cmp:assoc_compare []
                           ((intersection [(12,7);(9,13)] [])));
  "single lists uneq" >::
  (fun _ -> assert_equal ~cmp:assoc_compare [] (intersection [(1,0)] [(2,0)]));
  "single lists eq" >::
  (fun _ -> assert_equal ~cmp:assoc_compare [(1,0)] (intersection [(1,0)] [(1,0)]));
  "diff order" >:: (fun _ -> assert_equal ~cmp:assoc_compare [(1,0);(2,0)]
                       ((intersection [(1,0);(2,0)] [(2,0);(1,0)])));
  "second order" >:: (fun _ -> assert_equal ~cmp:assoc_compare [(2,0);(1,0)]
                         ((intersection [(2,0);(1,0)] [(1,0);(2,0)] )));
  "diff pos" >:: (fun _ -> assert_equal ~cmp:assoc_compare [(1,3);(2,1)]
                     ((intersection [(1,3);(2,1)] [(2,2);(1,5)])));
  "diff number of elems" >:: (fun _ -> assert_equal ~cmp:assoc_compare [(1,0);(2,1)]
                                 ((intersection [(1,0);(7,3);(2,1);(4,3)]
                                     [(2,2);(1,5)])));
  "simp case" >::
  (fun _ -> assert_equal ~cmp:assoc_compare [(1,0);(2,0)]
      ((intersection [(3,0);(1,0);(2,0)] [(2,0);(1,0)])));
  "long case" >:: (fun _ -> assert_equal ~cmp:assoc_compare [(41,0);(20,0);(7,0);(53,0)]
                      (intersection [(82,0);(23,0);(46,0);(93,0);(41,0);(20,0);
                                     (47,0);(7,0);(84,0);(53,0)]
                         [(80,0);(42,0);(41,0);(53,0);(72,0);(7,0);(20,0);(100,0)]));


  (* The test cases for make_pair_comp test if the function works on an
   * empty file, on a single entry file dictionary, if it works correctly for
   * either part of a double entry file dictionary with the corresponding
   * changes, and if it works if one of the entries in a file dictionary
   * is empty, which are all the edge cases. *)
  "empty file" >:: (fun _ -> filedict_equal emp_file
                       (make_pair_comp "" []));
  "single entry" >:: (fun _ -> filedict_equal se_dict
                         (make_pair_comp "a" [("a",[(1,0)])]));
  "double entry first" >:: (fun _ -> filedict_equal def_p_comp
                               (make_pair_comp "a" (FileDict.to_list de_dict)));
  "double entry second" >:: (fun _ -> filedict_equal des_p_comp
                                (make_pair_comp "b" (FileDict.to_list de_dict)));
  "empty entry in file" >:: (fun _ -> filedict_equal emp_e_f_p_comp
                                (make_pair_comp "a" (FileDict.to_list emp_e_dict)));
  "empty entry second" >:: (fun _ -> filedict_equal emp_e_s_p_comp
                               (make_pair_comp "b" (FileDict.to_list emp_e_dict)));
  "empty entry third" >:: (fun _ -> filedict_equal emp_e_t_p_comp
                              (make_pair_comp "c" (FileDict.to_list emp_e_dict)));


  (* The test for compare test if it works on an empty file dictionary,
   * a single entry file dictionary, a double entry file dictionary,
   * and a file dictionary with an empty entry, which are all the edge
   * cases. *)
  "empty comp" >:: (fun _ -> compdict_equal emp_comp
                       (compare emp_file));
  "single entry comp" >:: (fun _ -> compdict_equal se_comp
                              (compare se_dict));
  "double entry comp" >:: (fun _ -> compdict_equal de_comp
                              (compare de_dict));
  "empty entry comp" >:: (fun _ -> compdict_equal emp_e_comp
                             (compare emp_e_dict));


  (* The test cases for create_sim_list test if the function works for
   * an empty comparison dictionary, one with a single entry, one with two
   * entries, and only with an empty entry, which are all the edge cases. *)
  "empty sim" >:: (fun _ -> assert_equal ~cmp:assoc_compare []
                      (create_sim_list emp_comp 0.5));
  "single entry sim" >:: (fun _ -> assert_equal ~cmp:assoc_compare []
                             (create_sim_list se_comp 0.5));
  "double entry sim" >:: (fun _ -> assert_equal ~cmp:unordered_list_compare ["a";"b"]
                             (create_sim_list de_comp 0.5 |> get_files));
  "empty entry sim" >:: (fun _ -> assert_equal ~cmp:unordered_list_compare ["b"]
                            (create_sim_list emp_e_comp 0.5 |> get_files));


  (* The tests for create_pair_sim_list test if it works on an empty file
   * dictionary list, a single entry list, a double entry list for both entries
   * and for a list with an empty entry with every other filename than that of
   * of the one with the empty entry, since a precondition is that that case
   * will not work, which are all of the edge cases. *)
  "empty pair" >:: (fun _ -> assert_equal ~cmp:assoc_compare []
                       (create_pair_sim_list "" []));
  "single entry" >:: (fun _ -> assert_equal ~cmp:assoc_compare []
                         (create_pair_sim_list "a" (FileDict.to_list se_dict)));
  "double entry first" >:: (fun _ -> assert_equal ~cmp:assoc_compare [("b",0.5)]
                               (create_pair_sim_list "a" (FileDict.to_list def_p_comp)));
  "double entry second" >:: (fun _ -> assert_equal ~cmp:assoc_compare [("a",0.5)]
                                (create_pair_sim_list "b" (FileDict.to_list des_p_comp)));
  "empty entry second" >:: (fun _ -> assert_equal ~cmp:assoc_compare [("c",0.5);("a",0.0)]
                               (create_pair_sim_list "b" (FileDict.to_list emp_e_s_p_comp)));
  "empty entry third" >:: (fun _ -> assert_equal ~cmp:unordered_list_compare ["b";"a"]
                              ((create_pair_sim_list "c" (FileDict.to_list emp_e_t_p_comp)) |> get_files));

]