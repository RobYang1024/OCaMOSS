open Winnowing
open Unix
(* [keywords_list keyword_file] is a list of keywords pertaining to a
 * particular language, which are stored in a plaintext file with the name
 * [keyword_file].
 * If a file with that name does not exist, then this function returns []
 *)
val keywords_list : string -> string list

val special_chars : string -> char list

val comment_info : string -> (string * string * string * bool * bool)

(* [remove_noise str keywords] removes/replaces all of the noise, for example,
 * whitespace, variable names, function names, and language key words found in
 * [keywords] with more general names so that hashing can catch similarities
 * that it otherwise would not. *)
val remove_noise : (string * string * string * bool * bool) ->
  string -> string list -> char list -> bool -> string

(* [k_grams str n] creates a list of strings of length n, starting at each
 * character in [str] up to and including ([str] length - [n])th character.
 *)
val k_grams : string -> int -> string list

val hash_file : string -> int list

val get_file_positions : Unix.dir_handle ->
  string-> string -> int list -> (string * string) list
