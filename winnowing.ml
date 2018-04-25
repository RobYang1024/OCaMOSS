module type BoundedQueueWithCounter = sig
	type 'a t
	val create : int -> 'a t
	val is_empty : 'a t -> bool
	val size : 'a t -> int
	val enqueue : 'a -> 'a t -> 'a t
	val dequeue : 'a t -> 'a t
	val count : 'a t -> int
	val fold : ('b -> 'a -> 'b) -> 'b -> 'a t -> 'b
end

module Window : BoundedQueueWithCounter = struct
	type 'a t = ('a list) * int * int
	
	let create n = 
		if n = 0 then failwith "Cannot create queue of size 0!" else ([], n, 0)

	let is_empty q = match q with (data, _, _) -> List.length data = 0

	let size q = match q with (data, _, _) -> List.length data

	let dequeue q = match q with 
		|(data, x, y) -> begin
			match data with
			|[] -> q
			|h::t -> (t, x, y)
		end

	let rec enqueue item q = 
		let s = size q in
		match q with
		|(data, maxsize, trans) -> 
			if s = maxsize then dequeue q |> enqueue item
			else (item::data, maxsize, trans + 1)

	let count q = match q with (_, _, trans) -> trans

	let fold f init q = match q with
		|(data, _, _) -> List.fold_left f init data

end

(* h = hashes list, w = window size *)
let winnow h w = 
	(* calculates the global position of the i-th hash in the window
	   example: if [global_pos 5 W] = 100, then the 5th hash in W is the 100th hash 
	   that was processed by the winnowing algorithm. *)
	let global_pos i w = 
		let c = Window.count w in
		let s = Window.size w in
		c - (s - 1 - i)
	in
	(* helper function *)
	let mincheck ((minval,minpos),c) x = 
		if x <= minval then ((x, c), c + 1)
		else ((minval, minpos), c + 1)
	in
	(*  At the end of each iteration, min is a tuple of the (value,position)
		of the rightmost minimal hash in the
		current window. hash x is only added to the fingerprint [res] the
		first time an instance of x is selected as the
		rightmost minimal hash of a window. *)
	let rec winnowhelper hashes window res n (v,p) =
		if n = List.length hashes then res
		else begin
			let nexthash = List.nth hashes n in
			let updateW = Window.enqueue nexthash window in
			if p = 0 && Window.size window = w then 
				let newmin = fst (Window.fold mincheck ((max_int,0),0) window) in
				let newres = (fst newmin)::res in
				winnowhelper hashes updateW newres (n+1) newmin
			else if nexthash < v then 
				let newres = nexthash::res in
				winnowhelper hashes updateW newres (n+1) (nexthash, Window.size updateW)
			else 
				winnowhelper hashes updateW res (n+1) (v,p - 1)
		end
	in
	let window = Window.create w in
	winnowhelper h window [] 0 (max_int, 0)





