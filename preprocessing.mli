open Winnowing
open Unix

type comment_info = {
  single_comment: string;
  multi_comment_start: string;
  multi_comment_end: string;
  nest: bool;
  strings: bool;
}

type language_info = {
  keywords: string list;
  special_chars: char list;
  comment_info: comment_info;
}

(* [get_language_info language_file] gets the language info from a particular language JSON file and returns it as a record
 * requires: language_file is well-formatted JSON with the necessary fields
 *)
val get_language_info : string -> language_info

(* [remove_noise c_i_q code_str keywords spec_chars comments_nest remove_strings],
 * where
 * - [c_i_q] represents a comment_info record
 * - [code_str] is the string representation of the code whose noise is to be
 *   removed.
 * - [keywords] represents a list of keywords in the language that [code_str] is
 *   written in that are to be kept upon noise removal, and not replaced by a
 *   generic character.
 * - [spec_chars] represents a list of special characters in the language that
 *   [code_str] is written in that are to be kept upon noise removal, and not
 *   replaced by a generic character.
 * - [comments_nest] is a bool that indicates whether or not multi-line
 *   comments nest in the language.
 * - and [remove_strings] is a bool that indicates whether or not strings are to
 *   be removed from files of this language
 * is a function that takes outputs a "noise removed" version of [code_str],
 * where noise removal in this case indicates removing all instances of white
 * space, strings (if set to be removed), and comments, and replacing all
 * programmer defined variable/class/interface/module/type/function/method etc.,
 * names with a generic character 'v'.
 *)
val remove_noise : comment_info -> string -> string list -> char list -> bool -> string

(* [k_grams str n] creates a list of strings of length n, starting at each
 * character in [str] up to and including ([str] length - [n])th character.
 *
 * example:
 *  [k_grams "Hello World" 3 =
 *  ["Hel"; "ell"; "llo"; "lo "; "o W"; " Wo"; "Wor"; "orl"; "rld"]]
 *)
val k_grams : string -> int -> string list

(* [hash_file f] returns a list of hashes for n-grams of a file f with default
 * n = 35, where the file has been preprocessed by removing whitespace,
 * removing comments, replacing all variable names and strings with a generic
 * tag, while making sure that keywords and module names remain intact.
 *)
val hash_file : string -> int list

(* [get_file_positions dir dir_name filename positions] rehashes file filename
 * from directory dir, preprocessing it similar to how it would be in hash_file,
 * and returns a list of parts of the files that start at the values in
 * positions once the files have been preprocessed.
 *)
val get_file_positions : Unix.dir_handle ->
  string -> string -> int list -> (string * string) list
