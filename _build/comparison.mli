open Dictionary

(* The dictionary that stores associations between files and fingerprints. *)
type file_dict = Dictionary

(* [compare files] returns a list of comparisons between every pair of entries
 * in a dictionary files. *)
val compare : file_dict -> file_dict
