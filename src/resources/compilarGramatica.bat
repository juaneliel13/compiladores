yacc.exe -J gramatica.y
copy ParserVal.java temp.txt
echo.package Compilador.Sintactico;>ParserVal.java
type temp.txt >>ParserVal.java
del temp.txt
move .\Parser.java ..\main\java\Compilador\Sintactico\
move .\ParserVal.java ..\main\java\Compilador\Sintactico\