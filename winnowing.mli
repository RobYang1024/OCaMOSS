module type BoundedQueueWithCounter = sig
	type 'a t
	val create : int -> 'a t
	val is_empty : 'a t -> bool
	val is_full : 'a t -> bool
	val size : 'a t -> int
	val enqueue : 'a -> 'a t -> 'a t
	val dequeue : 'a t -> 'a option * 'a t
	val count : 'a t -> int
	val fold : ('b -> 'a -> 'b) -> 'b -> 'a t -> 'b
end

module Window : BoundedQueueWithCounter

val winnow: int list -> int -> (int * int) list

