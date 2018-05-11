module type BoundedQueueWithCounter = sig
	type 'a t
	val empty : int -> 'a t
	val create : int -> 'a -> 'a t
	val is_empty : 'a t -> bool
	val is_full : 'a t -> bool
	val size : 'a t -> int
	val enqueue : 'a -> 'a t -> 'a t
	val dequeue : 'a t -> 'a option * 'a t
	val count : 'a t -> int
	val fold : ('b -> 'a -> 'b) -> 'b -> 'a t -> 'b
end

module Window : BoundedQueueWithCounter = struct

	type 'a t = { data: 'a list ; maxsize: int ; size: int ; count: int}

	let empty n =
		if n = 0 then failwith "Cannot create queue of size 0!"
		else
			{ data = []; maxsize = n; size = n; count = 0}

	let create n i =
		let rec gen l acc i =
			if l = 0 then acc else gen (l - 1) (i::acc) i
		in
		if n = 0 then failwith "Cannot create queue of size 0!"
		else
			let initdata = gen n [] i in
			{ data = initdata; maxsize = n; size = n; count = 0}

	let is_empty q = (q.size = 0)

	let is_full q = (q.size = q.maxsize)

	let size q = q.size

	let dequeue q =
		match q.data with
		|[] -> (None, q)
		|h::t -> (Some h, {q with data = t ; size = q.size - 1})

	let rec enqueue item q =
		if is_full q then dequeue q |> snd |> enqueue item
		else {q with data = q.data@[item] ;size = q.size + 1 ;count = q.count + 1}

	let count q = q.count

	let fold f init q = List.fold_left f init q.data

end

(* h = hashes list, w = window size *)
let winnow h w =
	(* calculates the global position of the i-th hash in the window
    example: if [global_pos 5 W] = 100, then the 5th hash in W is the 100th
    hash that was processed by the winnowing algorithm. *)
	let global_pos i w =
		let c = Window.count w in
		let s = Window.size w in
		c - (s - 1 - i)
	in
	(* helper function *)
	let mincheck ((minval,minpos),count) x =
		if x <= minval then ((x, count), count + 1)
		else ((minval, minpos), count + 1)
	in
	(*  At the end of each iteration, min is a tuple of the (value,position)
		of the rightmost minimal hash in the
		current window. hash x is only added to the fingerprint [res] the
		first time an instance of x is selected as the
		rightmost minimal hash of a window. *)
	(*
	hashes - int list - the complete list of hashes
	window - BoundedQueueWithCounter - window object
	acc - (hash, position) list of selected hashes
	n - int - counter for position of hashes
	(v,p) - int * int - value and position of minimum hash
	*)
	let rec winnowhelper hashes window acc n (v,p) =
		if n = List.length hashes then acc
		else begin
			let nexthash = List.nth hashes n in
			let new_window = Window.enqueue nexthash window in
			if nexthash <= v then
     let new_acc = (nexthash, global_pos (Window.size new_window - 1)
                      new_window)::acc in
     winnowhelper hashes new_window new_acc (n+1) (nexthash,
                                                   Window.size new_window - 1)
			else begin
				let p = p - 1 in
				if p < 0 then
      let new_min = fst (Window.fold mincheck ((max_int,0),0) new_window) in
      let new_acc = (fst new_min, global_pos (snd new_min) new_window)::acc in
					winnowhelper hashes new_window new_acc (n+1) new_min
				else
					winnowhelper hashes new_window acc (n+1) (v,p)
			end
		end
	in
	let window = Window.create w max_int in
	let res = winnowhelper h window [] 0 (max_int, 0) in res
