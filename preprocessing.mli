open Winnowing
open Unix

(* [keywords_list language_file] is a list of keywords pertaining to a
 * particular language, which are stored in a json file with the name
 * [language_file].
 * requires: language_file is a json with a top level field/member named
 * "keywords" that contains a string list.
 *
 * example: [keywords_list "some_language_info.json" = ["let"; "if"; "else"]]
 *)
val keywords_list : string -> string list

(* [special_chars language_file] is a list of special characters pertaining to a
 * particular language, which are stored in a json file with the name
 * [language_file].
 * requires: language_file is a json with a top level field/member named
 * "special characters" that contains a string list, where each string has a
 * length of 1.
 *
 * example: [special_chars "some_language_info.json" = [':'; '!'; '&']]
 *)
val special_chars : string -> char list

(* [comment_info language_file] returns a 5-tuple of information pertaining to
 * comments in the corresponding language,
 * [(single_begin, multi_begin, multi_end, comments_nest)], where
 * - [single_begin] represents the string that begins a single line comment,
 *   or an empty string if the language does not have a special string to
 *   indicate the beginning of a single line comment specifically.
 * - [multi_begin] represents the string that begins a multi-line comment, or
 *   an empty string if the language does not have a special string to indicate
 *   the beginning of a multi line comment.
 * - [multi_end] represents the string that ends a multi-line comment, or an
 *   empty string if the language does not have a special string to indicate
 *   the end of a multi line comment.
 * - [comments_nest] is a bool that indicates whether or not multi-line
 *   comments nest in the language.
 *
 * example: [comment_info "java_info.json" = ("//", "/*", "*/", false, true)]
 *)
val comment_info : string -> (string * string * string * bool * bool)

(* [remove_noise c_i_q code_str keywords spec_chars comments_nest remove_strings],
 * where
 * - [c_i_q], an abbreviation for "comments_info_quint", is a 5-tuple containing
 *   all of the commenting information regarding a particular language in the
 *   same format as found in [comment_info] function specification.
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
val remove_noise : (string * string * string * bool * bool) ->
  string -> string list -> char list -> bool -> string

(* [k_grams str n] creates a list of strings of length n, starting at each
 * character in [str] up to and including ([str] length - [n])th character.
 *
 * example:
 *  [k_grams "Hello World" 3 =
 *  ["Hel"; "ell"; "llo"; "lo "; "o W"; " Wo"; "Wor"; "orl"; "rld"]]
 *)
val k_grams : string -> int -> string list

val hash_file : string -> int list

val get_file_positions : Unix.dir_handle ->
  string -> string -> int list -> (string * string) list
