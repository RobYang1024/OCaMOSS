open OUnit2
open Winnowing

let test1 = [599;-127;491;-432;-802;-856;-762;251;-547;365;943;695;148;658;-270;-166;652;-42;-651;798;-151;510;-825;-820;0;101;-585;-264;770;-272;-570;694;-636;497;-803;-210;135;-268;390;-713;739;657;-189;702;889;728;-136;566;788;179;878;-829;-107;-378;-305;385;719;-28;985;194;-39;212;-728;402;290;-275;723;780;344;-837;995;161;-775;259;104;862;596;-409;-826;-588;-218;-327;894;-354;109;-852;-465;-333;628;342;415;-562;-118;-73;-27;22;-787;-623;-236;-858]
let output1 = [(-858, 109);(-856, 15);(-852, 95);(-837, 79);(-829, 61);(-826, 88);(-825, 32);(-820, 33);(-803, 44);(-802, 14);(-787, 106);(-762, 16);(-728, 72);(-713, 49);(-651, 28);(-636, 42);(-562, 101);(-547, 18);(-432, 13);(-378, 63);(-189, 52);(-127, 11);(599, 10)]
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
"winnow1" >:: (fun _ -> assert_equal (List.length output1) (winnow test1 10 |> List.length));
"winnow2" >:: (fun _ -> assert_equal 5 (winnow [1;2;3;4;5] 1 |> List.length));
"winnow3" >:: (fun _ -> assert_equal 5 (winnow [5;4;3;2;1] 1 |> List.length));
"winnow4" >:: (fun _ -> assert_equal 5 (winnow [5;4;3;2;1] 2 |> List.length));
"winnow5" >:: (fun _ -> assert_equal 4 (winnow [1;2;3;4;5] 2 |> List.length));
]

