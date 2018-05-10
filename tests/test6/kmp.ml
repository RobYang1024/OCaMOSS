let init_next p =
  let m = String.length p in
  let next = Array.create m 0 in
  let i = ref 1 and j = ref 0 in
  while !i < m - 1 do
    if p.[!i] = p.[!j] then begin incr i; incr j; next.(!i) <- !j end else
    if !j = 0 then begin incr i; next.(!i) <- 0 end else j := next.(!j)
  done;
  next;;

let kmp p =
  let next = init_next p
  and m = String.length p in
  function s ->
    let n = String.length s
    and i = ref 0
    and j = ref 0 in
    while !j < m && !i < n do
      if s.[!i] = p.[!j] then begin incr i; incr j end else
      if !j = 0 then incr i else j := next.(!j)
    done;
    if !j >= m then !i - m else raise Not_found;;

let find_pat p s =
  try
    let i = kmp p s in
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

let usage () =
  print_string
   "Usage: kmp <pat> <str>\n\
    returns the index of the first occurrence of <pat> in string <str>\n";
  exit 2;;

let main () =
  let args = Sys.argv in
  if Array.length args < 2 then usage () else
   let p = Sys.argv.(1)
   and s = Sys.argv.(2) in
  find_pat p s;;

if !Sys.interactive then () else main ();;