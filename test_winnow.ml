open OUnit2
open Winnowing

let test_queue = Window.create 1
let test_queue2 = Window.create 5

(* helper function for sorting winnowing results (int*int) list representing hash * position tuples *)
let sort_results r = 
	let cmp x y = 
		let res = Pervasives.compare (fst x) (fst y) in
		if res = 0 then Pervasives.compare (snd x) (snd y) else res
	in
	List.sort (cmp) r

(* tests to check Winnowing functionality *)
let tests = [
(* testing the bounded queue *)
"is_empty" >:: (fun _ -> assert_equal true (Window.is_empty test_queue));
"is_empty2" >:: (fun _ -> assert_equal false (Window.enqueue 0 test_queue |> Window.dequeue |> snd |> Window.is_empty));
"is_empty3" >:: (fun _ -> assert_equal 0 (Window.size test_queue));
"is_not_empty" >:: (fun _ -> assert_equal false (Window.enqueue 0 test_queue |> Window.is_empty));
"size" >:: (fun _ -> assert_equal 2 (Window.enqueue 0 test_queue |> Window.enqueue 1 |> Window.size));
(* testing Winnowing *)

]