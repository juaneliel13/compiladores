yacc.exe -J gramatica.y
copy ParserVal.java temp.txt
echo.package compiladores;>ParserVal.java
type temp.txt >>ParserVal.java
del temp.txt
move .\Parser.java .\src\compiladores\
move .\ParserVal.java .\src\compiladores\