open OUnit2
open Winnowing

(* helper function for sorting winnowing results (int*int) list representing hash * position tuples *)
let sort_results r = 
	let cmp x y = 
		let res = Pervasives.compare (fst x) (fst y) in
		if res = 0 then Pervasives.compare (snd x) (snd y) else res
	in
	List.sort (cmp) r

(* helper function for converting results to strings *)
let res_to_string r = 
	let s = List.rev r |> List.map (fun x -> fst x) |> List.map (string_of_int) |> List.fold_left (fun a x -> a ^ x ^ ",") "" in
	let () = print_endline s in s

(* tests to check Winnowing functionality *)
let tests = [
(* testing Winnowing *)
"winnow0" >:: (fun _ -> assert_equal "" (winnow [] 1 |> res_to_string));
"winnow1" >:: (fun _ -> assert_equal "1," (winnow [1] 1 |> res_to_string));
"winnow2" >:: (fun _ -> assert_equal "1,2,3,4,5," (winnow [1;2;3;4;5] 1 |> res_to_string));
"winnow3" >:: (fun _ -> assert_equal "5,4,3,2,1," (winnow [5;4;3;2;1] 1 |> res_to_string));
"winnow4" >:: (fun _ -> assert_equal "5,4,3,2,1," (winnow [5;4;3;2;1] 2 |> res_to_string));
"winnow5" >:: (fun _ -> assert_equal "1,2,3,4," (winnow [1;2;3;4;5] 2 |> res_to_string));
]

