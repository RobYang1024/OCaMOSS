open OUnit2
open Dictionary
open Comparison

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

let tests = [

  (* The test cases for intersection has cases that test what happens with
   * one or more empty lists, single element lists, 2 lists with the order
   * reversed, lists with elements of same hash but different position,
   * and lists with different number of elements, which are all the edge
   * cases, and then a couple of general cases. *)
  "empty lists" >:: (fun _ -> assert_equal [] (intersection [] []));
  "one list empty" >:: (fun _ -> assert_equal []
                           ((intersection [(12,7);(9,13)] [])));
  "single lists uneq" >::
  (fun _ -> assert_equal [] (intersection [(1,0)] [(2,0)]));
  "single lists eq" >::
  (fun _ -> assert_equal [(1,0)] (intersection [(1,0)] [(1,0)]));
  "diff order" >:: (fun _ -> assert_equal [(1,0);(2,0)]
                       ((intersection [(1,0);(2,0)] [(2,0);(1,0)])));
  "second order" >:: (fun _ -> assert_equal [(2,0);(1,0)]
                       ((intersection [(2,0);(1,0)] [(1,0);(2,0)] )));
  "diff pos" >:: (fun _ -> assert_equal [(1,3);(2,1)]
                     ((intersection [(1,3);(2,1)] [(2,2);(1,5)])));
  "diff number of elems" >:: (fun _ -> assert_equal [(1,0);(2,1)]
                                 ((intersection [(1,0);(7,3);(2,1);(4,3)]
                                     [(2,2);(1,5)])));
  "simp case" >::
  (fun _ -> assert_equal [(1,0);(2,0)]
      ((intersection [(3,0);(1,0);(2,0)] [(2,0);(1,0)])));
  "long case" >:: (fun _ -> assert_equal [(41,0);(20,0);(7,0);(53,0)]
                      (intersection [(82,0);(23,0);(46,0);(93,0);(41,0);(20,0);
                                     (47,0);(7,0);(84,0);(53,0)]
                    [(80,0);(42,0);(41,0);(53,0);(72,0);(7,0);(20,0);(100,0)]));

  
  "empty file" >:: (fun _ -> assert_equal emp_file
                       (make_pair_comp "" [] emp_comp));
  "single entry" >:: (fun _ -> assert_equal se_dict
                         (make_pair_comp "a" [("a",[(1,0)])] emp_comp));
  "double entry first" >:: (fun _ -> assert_equal def_p_comp
  (make_pair_comp "a" (FileDict.to_list de_dict) emp_comp));
  "double entry second" >:: (fun _ -> assert_equal des_p_comp
  (make_pair_comp "b" (FileDict.to_list de_dict) emp_comp));
  "empty entry in file" >:: (fun _ -> assert_equal emp_e_s_p_comp
  (make_pair_comp "b" (FileDict.to_list emp_e_dict) emp_comp));
  "using comp_dict value" >:: (fun _ -> assert_equal des_p_comp
  (make_pair_comp "b" (FileDict.to_list de_dict) def_comp));


  "empty comp" >:: (fun _ -> assert_equal emp_comp
                       (compare emp_file));
  "single entry comp" >:: (fun _ -> assert_equal se_comp
                       (compare se_dict));
  "double entry comp" >:: (fun _ -> assert_equal de_comp
                              (compare de_dict));
  "empty entry comp" >:: (fun _ -> assert_equal emp_e_comp
                             (compare emp_e_dict));

  "empty sim" >:: (fun _ -> assert_equal []
                      (create_sim_list emp_comp));
  "single entry sim" >:: (fun _ -> assert_equal []
                              (create_sim_list se_comp));
  "double entry sim" >:: (fun _ -> assert_equal ["a";"b"]
                              (create_sim_list de_comp));
  "empty entry sim" >:: (fun _ -> assert_equal ["b"]
                              (create_sim_list emp_e_comp));

]
