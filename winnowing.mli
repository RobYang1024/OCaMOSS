(* custom data structure representing a bounded queue which also keeps track of its current size
 * and the total number of times enqueue has been called *)
module type BoundedQueueWithCounter = sig
	type 'a t

	(* [empty n] returns an empty queue with capacity n *)
	val empty : int -> 'a t

	(* [create n i] returns a full queue with capacity n, with every element initialized to i *)
	val create : int -> 'a -> 'a t

	(* [is_empty q] checks if q is empty and returns a boolean *)
	val is_empty : 'a t -> bool

	(* [is_full q] checks if q is full and returns a boolean *)
	val is_full : 'a t -> bool

	(* [size q] returns the number of elements currently in q *)
	val size : 'a t -> int

	(* [enqueue v q] returns a queue that has the contents of q with the 
	 * value v enqueued at the tail
	 * if q is full, then the first element will be dequeued before v is enqueued *)
	val enqueue : 'a -> 'a t -> 'a t

	(* [dequeue q] dequeues the first element (head) of q and returns a tuple 
	 * of the element and the updated queue the dequeued element is stored as an option
	 * if q is empty then the first value of the tuple will be None*)
	val dequeue : 'a t -> 'a option * 'a t

	(* [count q] returns the number of elements that have been enqueued (including ones that are no
	 * longer in the queue) *)
	val count : 'a t -> int

	(* [fold f init q] folds [f] over a list of the elements in [q] from 
	 * the head to the tail with initial value [init] *)
	val fold : ('b -> 'a -> 'b) -> 'b -> 'a t -> 'b
end

(* an implementation of BoundedQueueWithCounter using a record type and using a list to store data
 *
 * AF: the elements of the queue is stored in the [data] field, the current size stored in [size], 
 * the maximum size stored in [maxsize], and number of times an element has been enqueued stored in [count]
 *
 * RI: [size] <= [maxsize], [size],[maxsize],[count] cannot be negative, [size] is the length of the list in [data],
 * the first element of [data] is the head of the queue, the last element is the tail
 *)
module Window : BoundedQueueWithCounter

(* [winnow h w] applies the winnowing algorithm for a list of hashes [h]  with window size [w] 
 * returns: a list of (v,p) tuples where v is a hash value and p is the position in the original input
 * the (v,p) list is not guaranteed to be in any particular order
 * requires: [w] is a positive integer *)
val winnow: int list -> int -> (int * int) list

