test:
	ocamlbuild -use-ocamlfind test.byte && ./test.byte

compile:
	ocamlbuild -use-ocamlfind main.cmo

check:
	bash checkenv.sh && bash checktypes.sh

clean:
	ocamlbuild -clean
