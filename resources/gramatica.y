%{
package Compilador.Sintactico;
import Compilador.Lexico.AnalizadorLexico;
import java.util.HashMap;
%}

%token IF THEN ELSE END_IF FUNC OUT RETURN INTEGER FLOAT FOR PROC NI VAR UP DOWN ID CTE_INT CTE_FLOAT CADENA MAYOR_IGUAL MENOR_IGUAL COMP DISTINTO CARACTER_INVALIDO END 0
%start programa
%%



programa : conjunto_sentencias {}
	 | END  {System.out.println("Sin sentencias.");}
	 | error END  {System.out.println("Sin sentencias.");}
         ;

conjunto_sentencias : sentencia {}
                    | sentencia  conjunto_sentencias {}
                    ;


sentencia : declarativa {}
          | ejecutable { }
          | error ';'{System.out.println("Error en la linea " +lex.linea + ": Sentencia mal escrita.");}
	  ;

declarativa : dec_variable ';'{System.out.println("Se encontró una sentencia declarativa de variable."); }
            | dec_procedimiento {System.out.println("Se encontró una sentencia declarativa de procedimiento."); }
            | dec_variable {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\".");}
            ;

dec_variable : tipo lista_variables  {}
	     | tipo lista_variables '=' expresion {System.out.println("Error en la linea " +lex.linea + ": Asignacion en la declaración.");}
             | lista_variables  {System.out.println("Error en la linea " +lex.linea + ": Se esperaba un tipo.");}
             | error lista_variables  {System.out.println("Error en la linea " +lex.linea + ": Tipo no valido.");}
             | tipo error {System.out.println("Error en la linea " +lex.linea + ": Se esperaba un identificador.");}
             ;
tipo : INTEGER {}
     | FLOAT {}
     ;

lista_variables : ID {}
                | ID ',' lista_variables {}
		;

dec_procedimiento : PROC ID '(' lista_parametros ')' NI '=' CTE_INT '{' conjunto_sentencias '}' {}
                  | PROC ID '(' ')' NI '=' CTE_INT '{' conjunto_sentencias '}' {}
                  | PROC ID '(' lista_parametros ')' '{' conjunto_sentencias '}' {System.out.println("Error en la linea " +lex.linea + ": Se esperaba NI=CTE_INT en la declaracion de PROC.");}
                  | PROC ID '(' lista_parametros ')' NI '=' CTE_INT conjunto_sentencias '}' {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \"{\" .");}
                  | PROC ID '(' ')' '{' conjunto_sentencias '}' {System.out.println("Error en la linea " +lex.linea + ": Se esperaba NI=CTE_INT en la declaracion de PROC.");}
                  | PROC ID '(' lista_parametros ')' NI '=' CTE_INT '{'  '}'{System.out.println("Error en la linea " +lex.linea + ": Se esperaba una sentencia.");}
		  | PROC ID '(' ')' NI '=' CTE_INT '{'  '}'{System.out.println("Error en la linea " +lex.linea + ": Se esperaba una sentencia.");}
                  ;

lista_parametros :parametro {}
                 | parametro  ',' parametro  {}
                 | parametro  ',' parametro  ',' parametro  {}
                 | parametro  parametro  {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
                 | parametro   parametro  parametro  {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
                 | parametro ','  parametro  parametro  {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
                 | parametro   parametro ',' parametro  {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
                 ;

parametro: tipo ID {}
	   | VAR tipo ID {};
	   | VAR ID {System.out.println("Error en la linea " +lex.linea + ": Se esperaba tipo.");}
	   | ID {System.out.println("Error en la linea " +lex.linea + ": Se esperaba tipo.");}
	   | VAR tipo error {System.out.println("Error en la linea " +lex.linea + ": Se esperaba identificador.");}
           ;

ejecutable : asignacion ';'{System.out.println("Se encontró una sentencia de asignación."); }
           | asignacion {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\"' .");}
           | seleccion {System.out.println("Se encontró una sentencia de selección."); }
           | salida ';' {System.out.println("Se encontró una sentencia de salida."); }
           | salida {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\" .");}
           | llamada ';' {System.out.println("Se encontró una sentencia de llamada."); }
           | llamada {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\"'.");}
           | iteracion {System.out.println("Se encontró una sentencia de control."); }
           ;

asignacion : ID '=' expresion  {}
	   | ID COMP expresion  {System.out.println("Error en la linea " +lex.linea + ": Se encontró == en lugar de =.");}
           | error '=' expresion {System.out.println("Error en la linea " +lex.linea + ": Asignación mal escrita.");}
	   | ID '=' error {System.out.println("Error en la linea " +lex.linea + ": Asignación mal escrita.");}
           ;

expresion : expresion '+' termino {}
          | expresion '-' termino {}
          | termino {}
          ;

termino : termino '/' factor {}
        | termino '*' factor {}
        | factor {}
        ;

factor : ID {}
       | CTE_INT {
       			if ($1.sval!=null){
				int i = (int) Integer.parseInt($1.sval);
				if ( i > (int) Math.pow(2, 15) - 1) {
					System.out.println("Error en la linea " +lex.linea + ": Constante entera fuera de rango.");
				}
			}
		 }
       | CTE_FLOAT {}
       | '-' CTE_INT {
       			if($2.sval!=null){
				int i = -(int) Integer.parseInt($2.sval);
				if (!lex.tablaDeSimbolos.containsKey(String.valueOf(i))) {
					    HashMap<String, Object> aux = new HashMap<String, Object>();
					    aux.put("Tipo", "INT");
					    lex.tablaDeSimbolos.put(String.valueOf(i), aux);
					}
				int aux = (int) lex.tablaDeSimbolos.get(String.valueOf(-i)).get("Contador");
				aux--;
				if(aux != 0)
					lex.tablaDeSimbolos.get(String.valueOf(-i)).put("Contador",aux);
				else
					lex.tablaDeSimbolos.remove(String.valueOf(-i));
			}
       		     }
      | '-' CTE_FLOAT {
      			if($2.sval!=null){
				float f = -(float) Float.parseFloat($2.sval);
				if (!lex.tablaDeSimbolos.containsKey(String.valueOf(f))) {
					    HashMap<String, Object> aux = new HashMap<String, Object>();
					    aux.put("Tipo", "FLOAT");
					    lex.tablaDeSimbolos.put(String.valueOf(f), aux);
				}
				int aux = (int) lex.tablaDeSimbolos.get(String.valueOf(-f)).get("Contador");
				aux--;
				if(aux != 0)
					lex.tablaDeSimbolos.get(String.valueOf(-f)).put("Contador",aux);
				else
					lex.tablaDeSimbolos.remove(String.valueOf(-f));
			}
       		       }
       		       ;

seleccion : IF '(' condicion_if ')' THEN bloque_ejecutables_then END_IF {}
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF {}
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then bloque_ejecutables_else END_IF {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ELSE."); }
          | IF '(' condicion_if THEN bloque_ejecutables_then END_IF {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición."); }
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then error {System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF.");}
          | IF '(' condicion_if THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición."); }
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then ELSE bloque_ejecutables_else error  {System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF.");}
          | IF '(' condicion_if ')' bloque_ejecutables_then END_IF {System.out.println("Error en la linea " + lex.linea + ": Se esperaba THEN.");}
          | IF '(' condicion_if ')' bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF {System.out.println("Error en la linea " + lex.linea + ": Se esperaba THEN.");}
          | IF '(' condicion_if ')' THEN  END_IF {System.out.println("Error en la linea " + lex.linea + ": No se encontraron sentencias ejecutables.");}
          | IF '(' condicion_if ')' THEN declarativa END_IF {System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del IF.");}
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then ELSE declarativa END_IF {System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del ELSE.");}
          | IF '(' condicion_if ')' THEN declarativa ELSE bloque_ejecutables_then END_IF {System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del IF.");}
          | IF '(' condicion_if ')' THEN declarativa ELSE declarativa END_IF {System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del IF.");}
          | IF '(' error ')' THEN bloque_ejecutables_then END_IF {System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
          | IF '(' error ')' THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF {System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
          ;

condicion_if:expresion comparador expresion {}
            | expresion error  {System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
            | comparador expresion {System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
            | expresion comparador  {System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
	    ;


comparador : '<' {}
           | '>' {}
           | COMP {}
           | MAYOR_IGUAL {}
           | MENOR_IGUAL {}
           | DISTINTO {}
           ;

bloque_ejecutables_then:bloque_ejecutables {}
		       ;

bloque_ejecutables_else:bloque_ejecutables{}
		     ;

bloque_ejecutables : ejecutable  {}
		   | '{' ejecutable '}' {}
                   | '{' ejecutable  bloque_ejecutables '}' {}
                   ;

salida : OUT '(' CADENA ')' {}
       | OUT '(' ')' {System.out.println("Error en la linea " + lex.linea + ": Se esperaba una cadena.");}
       | OUT error {System.out.println("Error en la linea " + lex.linea + ": Sentencia OUT mal escrita.");}
       ;

llamada : ID '(' parametros ')'  {}
	| ID '(' ')'  {}
	;

parametros : ID {}
           | ID ',' ID {}
           | ID  ID {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
           | ID ',' ID ',' ID {}
           | ID  ID  ID {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
           | ID ',' ID  ID {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
           | ID  ID ',' ID {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
           ;

iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables {
			String id_for = $3.sval;
			String id_comp = $7.sval;
			System.out.println("$3: " + $3.sval);
			System.out.println("$7: " + $7.sval);
			if(!id_for.equals(id_comp)) {
				System.out.println("Error en la linea " + lex.linea + ": La variable de inicialización no es igual a la de condición.");
			}
		}
	  | FOR '(' ID '=' CTE_INT ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ": Se esperaba \";\" pero se recibio "+ $6.sval+"." );}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ": Se esperaba \";\" pero se recibio "+ $10.sval+"." );}
          | FOR '(' ID '=' CTE_INT ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ": Se esperaban \";\" en la sentencia FOR." );}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ": Se esperaban UP o DOWN en la sentencia FOR." );}
          | FOR '(' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ":  Falta inicialización en la sentencia FOR.");}
          | FOR '(' ID '=' CTE_INT ';' incr_decr CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ":  Falta condicion en la sentencia FOR.");}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ":  Falta incremento en la sentencia FOR.");}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ":  Se esperaba \")\" en la sentencia FOR.");}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' declarativa {System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del FOR.");}
          ;

incr_decr : UP {}
          | DOWN {}
          ;

%%

AnalizadorLexico lex;

public Parser(AnalizadorLexico lex)
{
	this.lex = lex;
	//yydebug=true;
}

int yylex()
{
	int aux= lex.getToken();
	this.yylval = new ParserVal(lex.yylval);
	return aux;
}

void yyerror(String a)
{


}

public int yyparse_publico() {
	return yyparse();
}