open OUnit2
open Preprocessing

let ocaml_keywords =
  ["and"; "as"; "assert"; "asr"; "begin"; "class"; "constraint";
   "do"; "done"; "downto"; "else"; "end"; "exception"; "external";
   "false"; "for"; "fun"; "function"; "functor"; "if"; "in"; "include";
   "inherit"; "initializer"; "land"; "lazy"; "let"; "lor"; "lsl"; "lsr";
   "lxor"; "match"; "method"; "mod"; "module"; "mutable"; "new"; "nonrec";
   "object"; "of"; "open"; "or"; "private"; "rec"; "sig"; "struct"; "then";
   "to"; "true"; "try"; "type"; "val"; "virtual"; "when"; "while"; "with";
   "Arg"; "Array"; "ArrayLabels"; "Buffer"; "Bytes"; "BytesLabels"; "Callback";
   "Char"; "Complex"; "Digest"; "Ephemeron"; "Filename"; "Format"; "Gc";
   "Genlex"; "Hashtbl"; "Int32"; "Int64"; "Lazy"; "Lexing"; "List";
   "ListLabels"; "Map"; "Marshal"; "MoreLabels"; "Nativeint"; "Oo"; "Parsing";
   "Printexc"; "Printf"; "Queue"; "Random"; "Scanf"; "Set"; "Sort";
   "Spacetime"; "Stack"; "StdLabels"; "Stream"; "String"; "StringLabels";
   "Sys"; "Uchar"; "Weak"; "failwith"]

let ocaml_spec_chars =
  ['!'; '$'; '%'; '&'; '*'; '+'; '-'; '.'; '/'; ':'; ';'; '<';
   '='; '>'; '?'; '@'; '^'; '|'; '~'; '#'; '\"'; '('; ')'; ',';
   '['; ']'; '{'; '}']

let ocaml_comments_info = 
  {
    single_comment = "";
    multi_comment_start = "(*";
    multi_comment_end = "*)";
    nest= true;
    strings= true;
  }

let java_keywords =
  ["abstract"; "continue"; "for"; "new"; "switch"; "assert"; "default";
   "package"; "synchronized"; "boolean"; "do"; "if"; "private"; "this"; "break";
   "double"; "implements"; "protected"; "throw"; "byte"; "else"; "import";
   "public"; "throws"; "case"; "enum"; "instanceof"; "return"; "transient";
   "catch"; "extends"; "int"; "short"; "try"; "char"; "final"; "interface";
   "static"; "void"; "class"; "finally"; "long"; "strictfp"; "volatile";
   "float"; "native"; "super"; "while"; "Boolean"; "Byte"; "Character"; "Class";
   "ClassLoader"; "ClassValue"; "Compiler"; "Double"; "Enum"; "Float";
   "InheritableThreadLocal"; "Integer "; "Long"; "Math"; "Number"; "Object";
   "Package"; "Process"; "ProcessBuilder"; "Runtime"; "RuntimePermission";
   "SecurityManager"; "Short"; "StackTraceElement"; "StrictMath"; "String";
   "StringBuffer"; "StringBuilder"; "System"; "Thread"; "ThreadGroup";
   "ThreadLocal"; "Throwable"; "Void"; "AbstractCollection"; "AbstractList";
   "AbstractMap"; "AbstractQueue"; "AbstractSequentialList"; "AbstractSet";
   "ArrayDeque"; "ArrayList"; "Arrays"; "BitSet"; "Calendar"; "Collections";
   "Currency"; "Date"; "Dictionary"; "EnumMap"; "EnumSet"; "EventListenerProxy";
   "EventObject"; "FormattableFlags"; "Formatter"; "GregorianCalendar";
   "HashMap";"HashSet"; "Hashtable"; "IdentityHashMap"; "LinkedHashMap";
   "LinkedHashSet"; "LinkedList"; "ListResourceBundle"; "Locale"; "Objects";
   "Observable"; "PriorityQueue"; "Properties"; "PropertyPermission";
   "PropertyResourceBundle"; "Random"; "ResourceBundle";
   "ResourceBundle.Control"; "Scanner"; "ServiceLoader"; "SimpleTimeZone";
   "Stack"; "StringTokenizer"; "Timer"; "TimerTask"; "TimeZone"; "TreeMap";
   "TreeSet"; "UUID"; "Vector"; "WeakHashMap"]

let java_spec_chars =
  ['!'; '$'; '%'; '&'; '*'; '+'; '-'; '.'; '/'; ':'; ';';
   '<'; '='; '>'; '?'; '^'; '|';  '\"'; '\''; '('; ')'; ','; '['; ']';
   '{'; '}'; '~'; '@']

let java_comments_info = 
  {
    single_comment = "//";
    multi_comment_start = "/*";
    multi_comment_end = "*/";
    nest= false;
    strings= true;
  }

let test_fun_str =
  "(* Hello World this is a comment *)
   (* (* This is a nested comment *) *)
   (* This is a multi line comment
      This is a multi line comment *)
   (* This is a multi line (* nested comment
      Wowie this is *) quite the comment *)
  let split_and_keep_on_spec_chars spec_chars str =
  let char_array = str_to_chr_arr str in
  (List.fold_left
    (fun acc_arr chr ->
       let str_of_chr = String.make 1 chr in
       if List.mem chr spec_chars then
         List.cons \"\" (List.cons str_of_chr acc_arr)
       else
         (* Hello I am yet another comment *)
         match acc_arr with
         | h::t -> (String.concat \"\" [h;str_of_chr])::t
         | [] -> failwith \"Array should never be empty\"
    )
    [\"\"]
    char_array) |> List.filter (fun str -> str <> \"\") |> List.rev"

let expected_res_str = String.concat ""
    ["letvvv=letv=vvin(List.v(funvv->letv=String.v1vinifList.vvvthenList.v";
     "(List.vvv)elsematchvwith|v::v->(String.v[v;v])::v|[]->failwithv";
     ")[]v)|>List.v(funv->v<>)|>List.v"]

let test_fun_str3 = "wow(*wow wow wow*)wow"

let expected_res_str3 = "vv"

let test_fun_str4 = "wow (* wow wow wow"

let expected_res_str4 = "v"

let test_fun_str5 =
  "/**
     * A DumbAI is a Controller that always chooses the blank space with the
     * smallest column number from the row with the smallest row number.
     */
    public class DumbAI extends Controller"

let expected_res_str5 = "publicclassvextendsv"

let test_fun_str6 =
  "// Note: Calling delay here will make the CLUI work a little more
    Hello World
    I am the World
    hahaha"

let expected_res_str6 = "vvvvvvv"

let test_fun_str7 =
  "package controller;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Game;
import model.Location;
import model.NotImplementedException;
import model.Player;

/**
 * A DumbAI is a Controller that always chooses the blank space with the
 * smallest column number from the row with the smallest row number.
 */
public class DumbAI extends Controller {

  public DumbAI(Player me) {
    super(me);
    // TODO Auto-generated constructor stub
    //throw new NotImplementedException();
  }

  protected @Override Location nextMove(Game g) {
    // Note: Calling delay here will make the CLUI work a little more
    // nicely when competing different AIs against each other.

    // TODO Auto-generated method stub
    //throw new NotImplementedException();

    Board b = g.getBoard();
    // find available moves
    for (int row = 0;row<Board.NUM_ROWS;row++) {
      for(int col = 0;col<Board.NUM_COLS;col++) {
        Location loc = new Location(row,col);
        if (b.get(loc) == null) {
          delay();
          return loc;
        }

      }
    }
    // wait a bit
    delay();

    return null;
  }
}"

let ocaml_info = get_language_info "ocaml_info.json"
let java_info = get_language_info "java_info.json"

let tests = [
  "k_grams_2" >:: (fun _ -> assert_equal (k_grams "test" 3) ["tes"; "est"]);
  "k_grams_1" >:: (fun _ -> assert_equal (k_grams "Hello World" 5)
                      ["Hello"; "ello "; "llo W"; "lo Wo"; "o Wor"; " Worl"; "World"]);
  "ocaml_keywords" >:: (fun _ -> assert_equal
                           ocaml_info.keywords
                           ocaml_keywords);
  "ocaml_spec_chars" >:: (fun _ -> assert_equal
                             ocaml_info.special_chars ocaml_spec_chars);
  "ocaml_comments_info" >:: (fun _ -> assert_equal
                                ocaml_info.comment_info
                                ocaml_comments_info);

  "java_keywords" >:: (fun _ -> assert_equal java_info.keywords java_keywords);
  "java_spec_chars" >:: (fun _ -> assert_equal java_info.special_chars java_spec_chars);
  "java_comments_info" >:: (fun _ -> assert_equal
                               java_info.comment_info
                               java_comments_info);


  "remove_noise" >::
  (fun _ -> assert_equal
      (remove_noise
         ocaml_comments_info
         test_fun_str
         ocaml_keywords ocaml_spec_chars
         false)
      expected_res_str);

  "remove_noise_3" >::
  (fun _ -> assert_equal
      (remove_noise
         ocaml_comments_info
         test_fun_str3
         ocaml_keywords ocaml_spec_chars
         false)
      expected_res_str3);

  "remove_noise_4" >::
  (fun _ -> assert_equal
      (remove_noise
         ocaml_comments_info
         test_fun_str4
         ocaml_keywords ocaml_spec_chars
         false)
      expected_res_str4);

  "remove_noise_5" >::
  (fun _ -> assert_equal
      (remove_noise
         java_comments_info
         test_fun_str5
         java_keywords java_spec_chars
         false)
      expected_res_str5);

  "remove_noise_6" >::
  (fun _ -> assert_equal
      (remove_noise
         java_comments_info
         test_fun_str6
         java_keywords java_spec_chars
         false)
      expected_res_str6);
]
