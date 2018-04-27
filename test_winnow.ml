open OUnit2
open Winnowing

(* helper function for sorting winnowing results (int*int) list representing hash * position tuples *)
let sort_results r = 
	let cmp x y = 
		let res = Pervasives.compare (fst x) (fst y) in
		if res = 0 then Pervasives.compare (snd x) (snd y) else res
	in
	List.sort (cmp) r

(* tests to check Winnowing functionality *)
let tests = [
(* testing Winnowing *)
"winnow2" >:: (fun _ -> assert_equal 5 (winnow [1;2;3;4;5] 1 |> List.length));
"winnow3" >:: (fun _ -> assert_equal 5 (winnow [5;4;3;2;1] 1 |> List.length));
"winnow4" >:: (fun _ -> assert_equal 5 (winnow [5;4;3;2;1] 2 |> List.length));
"winnow5" >:: (fun _ -> assert_equal 4 (winnow [1;2;3;4;5] 2 |> List.length));
]

