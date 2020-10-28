#!/bin/bash

yacc -J ./resources/gramatica.y
sed -i '1s/^/package Compilador.Sintactico;\n/' ParserVal.java
mv ./Parser.java ./src/main/java/Compilador/Sintactico/
mv ./ParserVal.java ./src/main/java/Compilador/Sintactico/