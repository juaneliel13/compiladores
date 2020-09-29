#!/bin/bash

yacc -J gramatica.y
mv ./Parser.java ./src/compiladores
mv ./ParserVal.java ./src/compiladores
