build:
	ocamlbuild -use-ocamlfind main.byte

test:
	ocamlbuild -use-ocamlfind test.byte && ./test.byte

check:
	bash checkenv.sh && bash checktypes.sh

clean:
	ocamlbuild -clean

run:
	ocamlbuild -use-ocamlfind main.byte && ./main.byte