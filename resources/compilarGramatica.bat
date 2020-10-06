yacc.exe -J gramatica.y
copy ParserVal.java temp.txt
echo.package Compilador.Sintactico;>ParserVal.java
type temp.txt >>ParserVal.java
del temp.txt
move .\Parser.java ..\src\main\java\Compilador\Sintactico\
move .\ParserVal.java ..\src\main\java\Compilador\Sintactico\