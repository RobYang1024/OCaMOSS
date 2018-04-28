open Dictionary

(* The dictionary that stores associations between files and fingerprints. *)
type file_dict

(* A pair_comparison is a record that stores the information after comparing
 * two entries in a file_dict, which includes the names of the two files
 * compared, a list of all the matches between the two files and a similarity
 * score based on the number of matches. *)
type pair_comparison

(* [compare files] returns a list of comparisons between every pair of entries
 * in a dictionary files. *)
val compare : file_dict -> file_dict
