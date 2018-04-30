open OUnit2
open Winnowing

(* helper function for sorting winnowing results (int*int) list representing hash * position tuples
 * sorts from least to greatest, by hash value first, then by position if hashes are equal *)
let sort_results r = 
	let cmp x y = 
		let res = Pervasives.compare (fst x) (fst y) in
		if res = 0 then Pervasives.compare (snd x) (snd y) else res
	in
	List.sort (cmp) r

(* helper function for converting results to strings
 * inputs: (int * int) list representing winnowing results
 * returns: a string representation of the hashes contained in the input
 * side effects: prints the string that is created *)
let res_to_string r = 
	let s = List.rev r |> List.map (fun x -> fst x) |> List.map (string_of_int) |> List.fold_left (fun a x -> a ^ x ^ ",") "" in
	let () = print_endline s in s

(* Non-trivial test cases generated with a Python implementation of the same algorithm *)
(* 10 hashes, w = 5 *)
let t1 = [407;345;-163;-695;54;898;-182;759;172;-345]
let r1 = "407,345,-163,-695,-182,-345,"

(* 100 hashes, w = 10 *)
let t2 = [-525;-765;990;457;-841;-446;-407;386;-433;-145;370;-133;-170;348;-532;328;306;-325;-856;-131;971;266;-88;-608;-274;539;10;-367;389;68;147;81;-745;51;750;640;148;416;-983;645;-692;-169;-162;-76;562;894;881;-883;244;121;-501;-281;311;-903;20;-285;-533;-818;-273;241;103;318;161;763;-790;-348;961;655;431;301;217;-680;834;385;-92;76;-354;-683;23;-898;-16;734;-806;-474;-728;-349;-900;107;-118;-160;808;273;351;117;53;695;261;-803;-571;-564]
let r2 = "-525,-765,-841,-532,-856,-608,-745,-983,-883,-903,-818,-790,-680,-683,-898,-900,-160,-803,"

(* tests to check Winnowing functionality *)
let tests = [
"winnow0" >:: (fun _ -> assert_equal "" (winnow [] 1 |> res_to_string));
"winnow1" >:: (fun _ -> assert_equal "1," (winnow [1] 1 |> res_to_string));
"winnow2" >:: (fun _ -> assert_equal "1,2,3,4,5," (winnow [1;2;3;4;5] 1 |> res_to_string));
"winnow3" >:: (fun _ -> assert_equal "5,4,3,2,1," (winnow [5;4;3;2;1] 1 |> res_to_string));
"winnow4" >:: (fun _ -> assert_equal "5,4,3,2,1," (winnow [5;4;3;2;1] 2 |> res_to_string));
"winnow5" >:: (fun _ -> assert_equal "1,2,3,4," (winnow [1;2;3;4;5] 2 |> res_to_string));
"winnow6" >:: (fun _ -> assert_equal r1 (winnow t1 5 |> res_to_string));
"winnow7" >:: (fun _ -> assert_equal r2 (winnow t2 10 |> res_to_string));
]

