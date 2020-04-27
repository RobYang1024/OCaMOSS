build:
	ocamlbuild -use-ocamlfind main.native && mv main.native ocamoss

test:
	ocamlbuild -use-ocamlfind test.native && ./test.native

check:
	bash checkenv.sh && bash checktypes.sh

clean:
	ocamlbuild -clean

run:
	ocamlbuild -use-ocamlfind main.native && mv main.native ocamoss && ./ocamoss