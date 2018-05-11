type student = {
  name: string;
  mutable gpa: float;
}

let student1 = {
  name = "Alice";
  gpa = 3.7;
}

type vector = float array

let norm vec= sqrt (Array.fold_left (+.) 0. (Array.map (fun x -> x *. x) vec))

let normalize vec =
  for x = 0 to Array.length vec -1
  do vec.(x) <- vec.(x) * 2 done
