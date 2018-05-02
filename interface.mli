(* [interface] functions as an REPL that will allow users to input a
 * directory of files that they want to check for similarities in and then
 * display the results of that comparison in a GUI highlighting portions of
 * files that look alike. *)


 (* [comparison] will be of type Comparison.pair_comparison list, but cannot be
  * represented yet without MakeFile.
  * A comparison is a record that stores the information after comparing
  * two entries in a file_dict, which includes the names of the two files
  * compared, a list of all the matches between the two files and a similarity
  * score based on the number of matches. *)
type comparison

(* [gui] will represent the graphical interface of the application. (This is a
 * hand-wavy way of trying to describe the function of the GUI in the [interface]
 * signature. We do not currently know too much about the implementation of GUIs
 * in OCaml, but plan to look into the CamlImages and Gtktop Libraries)
 *
 * [gui] will update throughout application from opening screen to display
 * screen for the comparisons *)
type gui

(* [open_page] will display a welcome screen to the program and ask the user
 * to input a directory of files they would like to compare
 * effects: gui output *)
val open_page: gui

(* [input] will read the user's input in the program and pass the directory's
 * name to begin the comparison process
 * requires: a valid directory with files of the same language
 * example: a directory [python_1110] that contains only [.py] files*)
val input: string

(* [output lst] will return a gui representation of similarities in files being
 * highlighted and display a similarity score between files as well
 * requires: a valid comparison list
 * effects: gui outputs*)
val output: comparison list -> gui
