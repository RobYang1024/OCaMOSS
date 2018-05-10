

let usage () =
  print_string
   "Usage: strstr2 <pat> <str>\n\
    returns the index of the first occurrence of <pat> in string <str>\n";
  exit 2;;

let main () =
  let args = Sys.argv in
  if Array.length args < 2 then usage () else
   let p = Sys.argv.(1)
   and s = Sys.argv.(2) in
  find_pat p s;;

if !Sys.interactive then () else main ();;

let find_pat p s =
  try
    let i = find_sub_string p s in
    print_string "Match found at character ";
    print_int i;
    print_newline ();
    exit 0
  with Not_found ->
    print_string "Pattern ";
    print_string p;
    print_string " does not match string ";
    print_string s;
    print_newline ();
    exit 2;;





let find_sub_string p s =
  let plen = String.length p in
  if plen = 0 then 0 else
  let slen = String.length s in
  if slen = 0 then raise Not_found else
  let rec find c i =
    if i >= slen then raise Not_found else
    if s.[i] = c then find_rest c 1 (i + 1) else find c (i + 1)
   and find_rest c j i =
    if j >= plen then (i - plen) else
    if i >= slen then raise Not_found else
    if p.[j] = s.[i] then find_rest c (j + 1) (i + 1) else
    find c (i + 1 - j) in
  find p.[0] 0;;