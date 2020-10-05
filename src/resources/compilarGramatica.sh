#!/bin/bash

yacc -J gramatica.y
sed -i '1s/^/package Compilador.Sintactico;\n/' ParserVal.java
mv ./Parser.java ..\main\java\Compilador\Sintactico\
mv ./ParserVal.java ..\main\java\Compilador\Sintactico\
