(* OCaml Winnowing Algorithm *)


let next_hash k = Some (String.length k) (* placeholder, see below *)

let global_pos min r w = r + w + min (* placeholder, see below *)

let record k v d = (k,v)::d (* placeholder, see below *)

(*
What the helpers do:
record is essentially Dict.insert - insert hash with global position pos into dictionary
(int -> int -> dict -> dict) - order of arguments may be modified

global_pos min r w - calculates global position of hash somehow, min = position of min hash, r = position of current, w = window size
(int -> int -> int -> int)

next_hash k - process the next hash for file k
(string -> int option)
*)

let find_min_pos r w min arr =
(* Scan h leftward starting from r
	// for the rightmost minimal hash. Note min
	// starts with the index of the rightmost
	// hash.
	*)
	let rec checkmin pos m = 
		if pos = r then m
		else
			if arr.(pos) < arr.(m) then 
				let newmin = pos in
				checkmin ((pos - 1 + w) mod w) newmin
			else
				checkmin ((pos - 1 + w) mod w) m
	in
	checkmin ((r - 1) mod w) (min)

let rec winnowhelper arr r min w k d = 
(* 	// At the end of each iteration, min holds the
	// position of the rightmost minimal hash in the
	// current window. record(x) is called only the
	// first time an instance of x is selected as the
	// rightmost minimal hash of a window. *)
	let r = (r + 1) mod w in
	let next = next_hash k in
	match next with
	|None -> d
	|Some hash -> begin
		arr.(r) <- hash ;
		if min = r then
		(* 	// The previous minimum is no longer in this
			// window.  *)
			let newmin = find_min_pos r w min arr in
			let newdict = record arr.(newmin) (global_pos newmin r w) d in
			winnowhelper arr r newmin w k newdict
		else
		(* 	// The previous minimum is still in
			// this window. Compare against the new value
			// and update min if necessary. *)
			if (arr.(r) <= arr.(min)) then
				let newdict = record arr.(r) (global_pos r r w) d in
				winnowhelper arr r r w k newdict
			else 
				winnowhelper arr r min w k d
	end

(* k = file, d = dictionary with file as a key, w = window size *)
let winnow w k d = 
	let arr = Array.init w (fun x -> max_int) in
	winnowhelper arr 0 0 w k d

(* C++ implementation from the paper
void winnow(int w /*window size*/) {
	// circular buffer implementing window of size w
	hash_t h[w];

	for (int i=0; i<w; ++i) h[i] = INT_MAX;
	int r = 0; // window right end
	int min = 0; // index of minimum hash
	// At the end of each iteration, min holds the
	// position of the rightmost minimal hash in the
	// current window. record(x) is called only the
	// first time an instance of x is selected as the
	// rightmost minimal hash of a window.

	while (true) {
		r = (r + 1) % w; // shift the window by one
		h[r] = next_hash(); // and add one new hash
		if (min == r) {
			// The previous minimum is no longer in this
			// window. Scan h leftward starting from r
			// for the rightmost minimal hash. Note min
			// starts with the index of the rightmost
			// hash.
			for(int i=(r-1)%w; i!=r; i=(i-1+w)%w) if (h[i] < h[min]) min = i;
			record(h[min], global_pos(min, r, w));
		} else {
			// Otherwise, the previous minimum is still in
			// this window. Compare against the new value
			// and update min if necessary.
			if (h[r] <= h[min]) { // (*)
				min = r;
				record(h[min], global_pos(min, r, w));
			}
		}
	}
} 
*)





