open Dictionary

module StringKey = struct
  type t = string
  let compare k1 k2 = Pervasives.compare k1 k2
end

module HashValue = struct
  type t = (int*int) list
  let format d = ()
end

module FileDict = TreeDictionary(StringKey)(HashValue)

module DictValue = struct
  type t = FileDict.t
  let format d = ()
end

module CompDict = TreeDictionary(StringKey)(DictValue)

let intersection v1 v2 =
  let rec intersection_helper lst1 lst2 common =
    match lst1 with
    | [] -> common
    | (h,p)::t -> intersection_helper t lst2
                    (if List.mem_assoc h lst2 then (h,p)::common else common)
  in
  List.rev(intersection_helper v1 v2 [])

let make_pair_comp k0 file_list comp_dict =
  List.fold_left (fun x (k,v) -> (*match CompDict.find k comp_dict with
      | None -> *)FileDict.insert k (intersection v (List.assoc k0 file_list)) x
        (*| Some dict -> match FileDict.find k0 dict with
        | None -> failwith "Invalid comparison dictionary"
          | Some v2 -> FileDict.insert k v2 x*))
    FileDict.empty file_list

let compare d =
  let file_list = FileDict.to_list d in
  List.fold_left (fun x (k,v) ->
      CompDict.insert k (make_pair_comp k file_list x) x)
      CompDict.empty file_list

let create_sim_list comp_dict =

  let create_sim_list_helper comp_dict =
    List.fold_left (fun x (k,d) -> match FileDict.find k d with
        | None -> failwith "Unimplemented"
        | Some v -> let file_length = float_of_int (List.length v) in
          let sim_score =
            List.fold_left (fun a (k1,v1) -> if StringKey.compare k k1 = 0
              then a
              else a +. (if file_length = 0.0 then 0.0 else
                           ((float_of_int (List.length v1))/.file_length))) 0.0
              (FileDict.to_list d) in
          if sim_score >= 0.5
          then k::x else x) [] (CompDict.to_list comp_dict) in
  List.sort (Pervasives.compare) (create_sim_list_helper comp_dict)
