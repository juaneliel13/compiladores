#!/bin/bash

yacc -J gramatica.y
sed -i '1s/^/package compiladores;\n/' ParserVal.java
mv ./Parser.java ./src/compiladores
mv ./ParserVal.java ./src/compiladores
