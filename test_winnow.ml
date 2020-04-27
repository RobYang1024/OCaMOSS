open OUnit2
open Winnowing

(* helper function for sorting winnowing results (int*int) list representing
 * hash * position tuples sorts from least to greatest, by hash value first,
 * then by position if hashes are equal
*)
let sort_results r =
  let cmp x y =
    let res = Stdlib.compare (fst x) (fst y) in
    if res = 0 then Stdlib.compare (snd x) (snd y) else res
  in
  List.sort (cmp) r

(* helper function for converting results to strings
 * inputs: (int * int) list representing winnowing results
 * returns: a string representation of the hashes contained in the input
 * side effects: prints the string that is created *)
let res_to_string r =
  List.rev r |> List.map (fun x -> fst x) |> List.map (string_of_int) |>
  List.fold_left (fun a x -> a ^ x ^ ",") ""

(* Non-trivial test cases generated with a Python implementation of the same
 * algorithm *)
(* 10 hashes, w = 5 *)
let t1 = [407;345;-163;-695;54;898;-182;759;172;-345]
let r1 = "407,345,-163,-695,-182,-345,"

(* 100 hashes, w = 10 *)
let t2 = [-525;-765;990;457;-841;-446;-407;386;-433;-145;370;-133;-170;348;-532;
          328;306;-325;-856;-131;971;266;-88;-608;-274;539;10;-367;389;68;147;
          81;-745;51;750;640;148;416;-983;645;-692;-169;-162;-76;562;894;881;
          -883;244;121;-501;-281;311;-903;20;-285;-533;-818;-273;241;103;318;
          161;763;-790;-348;961;655;431;301;217;-680;834;385;-92;76;-354;-683;
          23;-898;-16;734;-806;-474;-728;-349;-900;107;-118;-160;808;273;351;
          117;53;695;261;-803;-571;-564]
let r2 = "-525,-765,-841,-532,-856,-608,-745,-983,-883,-903,-818,-790,-680,"^
         "-683,-898,-900,-160,-803,"

(* 100 hashes, w = 5 *)
let t3 = [-688;288;-953;-376;-521;480;-314;723;428;260;-804;-915;471;990;-243;
          -230;-684;-694;682;948;404;-474;-704;746;886;634;92;-87;778;-424;120;
          -883;-293;-463;594;-408;58;518;-300;-938;-641;413;873;102;-35;-978;
          -921;-909;-745;-693;-318;-612;-475;366;-173;749;181;215;-487;-264;
          -33;730;613;-43;-464;-779;435;123;-313;-252;116;-175;-291;21;-932;
          -105;-816;-697;312;-332;-140;702;949;864;-134;217;-579;-601;-616;59;
          -478;-381;107;-88;-696;872;-431;533;-397;-918]
let r3 = "-688,-953,-521,-314,-804,-915,-684,-694,-704,-87,-424,-883,-463,"^
         "-408,-938,-641,-978,-921,-909,-745,-693,-612,-475,-173,-487,-264,"^
         "-464,-779,-313,-291,-932,-816,-697,-332,-140,-134,-579,-601,-616,"^
         "-478,-696,-918,"

(* 100 hashes, w = 20 *)
let t4 = [-776;271;540;-580;-710;803;690;980;611;-305;252;-404;-377;544;-982;
          -577;-792;139;574;-107;752;-108;-326;354;-339;-323;-317;-571;-995;
          797;-517;-594;-130;-95;-343;-989;-979;-623;299;-941;534;-542;-328;
          -23;632;280;640;-74;-3;471;-770;356;-204;-354;-273;124;743;143;575;
          -75;-391;329;724;-253;680;-63;-891;-564;92;-100;-892;263;-671;100;
          496;205;468;-936;408;-195;422;600;382;726;991;-397;172;164;373;559;
          185;495;584;-311;-257;-869;-136;968;-12;932]
let r4 = "-776,-982,-995,-989,-979,-941,-770,-891,-892,-936,-869,"

(* tests to check Winnowing functionality *)
let tests = [
  "winnow0" >:: (fun _ -> assert_equal "" (winnow 1 [] |> res_to_string));
  "winnow1" >:: (fun _ -> assert_equal "1," (winnow 1 [1] |> res_to_string));
  "winnow2" >:: (fun _ -> assert_equal "1,2,3,4,5," (winnow 1 [1;2;3;4;5]
                                                     |> res_to_string));
  "winnow3" >:: (fun _ -> assert_equal "5,4,3,2,1," (winnow 1 [5;4;3;2;1]
                                                     |> res_to_string));
  "winnow4" >:: (fun _ -> assert_equal "5,4,3,2,1," (winnow 1 [5;4;3;2;1]
                                                     |> res_to_string));
  "winnow5" >:: (fun _ -> assert_equal "1,2,3,4," (winnow 1 [1;2;3;4;5]
                                                   |> res_to_string));
  "winnow6" >:: (fun _ -> assert_equal r1 (winnow 5 t1 |> res_to_string));
  "winnow7" >:: (fun _ -> assert_equal r2 (winnow 10 t2 |> res_to_string));
  "winnow8" >:: (fun _ -> assert_equal r3 (winnow 5 t3 |> res_to_string));
  "winnow9" >:: (fun _ -> assert_equal r4 (winnow 20 t4 |> res_to_string));
]
