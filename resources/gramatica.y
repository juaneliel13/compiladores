%{
package Compilador.Sintactico;
import Compilador.Lexico.AnalizadorLexico;
import Compilador.Utilidad.Logger;
import java.util.HashMap;
%}

%token IF THEN ELSE END_IF FUNC OUT RETURN INTEGER FLOAT FOR PROC NI VAR UP DOWN ID CTE_INT CTE_FLOAT CADENA MAYOR_IGUAL MENOR_IGUAL COMP DISTINTO CARACTER_INVALIDO END 0
%start programa
%%



programa : conjunto_sentencias {}
	 | END  {Logger.getInstance().addEvent(lex.linea,"Sin sentencias");}
	 | error END  {Logger.getInstance().addEvent(lex.linea,"Sin sentencias validas");}
         ;

conjunto_sentencias : sentencia {}
                    | sentencia  conjunto_sentencias {}
                    ;


sentencia : declarativa {}
          | ejecutable { }
          | error ';'{Logger.getInstance().addError(lex.linea,"Sentencia mal escrita");}
	  ;

declarativa : dec_variable ';'{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia declarativa de variable");}
            | dec_procedimiento {Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia declarativa de procedimiento");}
            | dec_variable {Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
            ;

dec_variable : tipo lista_variables  {}
	     | tipo lista_variables '=' expresion {Logger.getInstance().addError(lex.linea,"Asignacion en la declaración");}
             | lista_variables  {Logger.getInstance().addError(lex.linea,"Se esperaba un tipo");}
             | error lista_variables  {Logger.getInstance().addError(lex.linea,"Tipo no valido");}
             | tipo error {Logger.getInstance().addError(lex.linea,"Se esperaba un identificador");}
             ;
tipo : INTEGER {}
     | FLOAT {}
     ;

lista_variables : ID {}
                | ID ',' lista_variables {}
		;

dec_procedimiento : PROC ID '(' lista_parametros ')' NI '=' CTE_INT '{' conjunto_sentencias '}' {}
                  | PROC ID '(' ')' NI '=' CTE_INT '{' conjunto_sentencias '}' {}
                  | PROC ID '(' lista_parametros ')' '{' conjunto_sentencias '}' { Logger.getInstance().addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");}
                  | PROC ID '(' lista_parametros ')' NI '=' CTE_INT conjunto_sentencias '}' { Logger.getInstance().addError(lex.linea,"Se esperaba \"{\"");}
                  | PROC ID '(' ')' '{' conjunto_sentencias '}' { Logger.getInstance().addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");}
                  | PROC ID '(' lista_parametros ')' NI '=' CTE_INT '{'  '}'{ Logger.getInstance().addError(lex.linea,"Se esperaba una sentencia");}
		  | PROC ID '(' ')' NI '=' CTE_INT '{'  '}'{Logger.getInstance().addError(lex.linea,"Se esperaba una sentencia");}
                  ;

lista_parametros :parametro {}
                 | parametro  ',' parametro  {}
                 | parametro  ',' parametro  ',' parametro  {}
                 | parametro  parametro  {Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
                 | parametro   parametro  parametro  {Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
                 | parametro ','  parametro  parametro  {Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
                 | parametro   parametro ',' parametro  {Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
                 ;

parametro: tipo ID {}
	   | VAR tipo ID {};
	   | VAR ID {Logger.getInstance().addError(lex.linea,"Se esperaba tipo");}
	   | ID {Logger.getInstance().addError(lex.linea,"Se esperaba tipo");}
	   | VAR tipo error {Logger.getInstance().addError(lex.linea,"Se esperaba identificador");}
           ;

ejecutable : asignacion ';'{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de asignación");}
           | asignacion {Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
           | seleccion {Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de seleccion"); }
           | salida ';' {Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de salida");}
           | salida {Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
           | llamada ';' {Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de llamada"); }
           | llamada {Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
           | iteracion {Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de control"); }
           ;

asignacion : ID '=' expresion  {}
	   | ID COMP expresion  {Logger.getInstance().addError(lex.linea,"Se encontró == en lugar de =");}
           | error '=' expresion {Logger.getInstance().addError(lex.linea,"Asignacion mal escrita");}
	   | ID '=' error {Logger.getInstance().addError(lex.linea,"Asignacion mal escrita");}
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
					Logger.getInstance().addError(lex.linea,"Constante entera fuera de rango");

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
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then bloque_ejecutables_else END_IF {Logger.getInstance().addError(lex.linea,"Se esperaba ELSE");}
          | IF '(' condicion_if THEN bloque_ejecutables_then END_IF {Logger.getInstance().addError(lex.linea,"Se esperaba \")\" luego de la condición"); }
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then error {Logger.getInstance().addError(lex.linea,"Se esperaba END_IF");}
          | IF '(' condicion_if THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF {Logger.getInstance().addError(lex.linea,"Se esperaba \")\" luego de la condición"); }
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then ELSE bloque_ejecutables_else error {Logger.getInstance().addError(lex.linea,"Se esperaba END_IF");}
          | IF '(' condicion_if ')' bloque_ejecutables_then END_IF {Logger.getInstance().addError(lex.linea,"Se esperaba THEN");}
          | IF '(' condicion_if ')' bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF {Logger.getInstance().addError(lex.linea,"Se esperaba THEN");}
          | IF '(' condicion_if ')' THEN  END_IF {Logger.getInstance().addError(lex.linea,"No se encontraron sentencias ejecutables");}
          | IF '(' condicion_if ')' THEN declarativa END_IF {Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del IF");}
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then ELSE declarativa END_IF {Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del ELSE");}
          | IF '(' condicion_if ')' THEN declarativa ELSE bloque_ejecutables_then END_IF {Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del IF");}
          | IF '(' condicion_if ')' THEN declarativa ELSE declarativa END_IF {Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del IF");}
          | IF '(' error ')' THEN bloque_ejecutables_then END_IF {Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
          | IF '(' error ')' THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF {Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
          ;

condicion_if:expresion comparador expresion {}
            | expresion error {Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
            | comparador expresion {Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
            | expresion comparador {Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
	    ;


comparador : '<' {}
           | '>' {}
           | COMP {}
           | MAYOR_IGUAL {}
           | MENOR_IGUAL {}
           | DISTINTO {}
           ;

bloque_ejecutables_then:ejecutable{}
		       | '{'bloque_ejecutables'}' {}
		       ;

bloque_ejecutables_else:ejecutable{}
		       | '{'bloque_ejecutables'}' {}
		       ;

bloque_ejecutables_for:ejecutable{}
		       | '{'bloque_ejecutables'}' {}
		       ;

bloque_ejecutables : ejecutable {}
                   | ejecutable bloque_ejecutables  {}
                   ;

salida : OUT '(' CADENA ')' {}
       | OUT '(' ')' {Logger.getInstance().addError(lex.linea,"Se esperaba una cadena");}
       | OUT error {Logger.getInstance().addError(lex.linea,"Sentencia OUT mal escrita");}
       ;

llamada : ID '(' parametros ')'  {}
	| ID '(' ')'  {}
	;

parametros : ID {}
           | ID ',' ID {}
           | ID  ID {Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
           | ID ',' ID ',' ID {}
           | ID  ID  ID {Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
           | ID ',' ID  ID {Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
           | ID  ID ',' ID {Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
           ;

iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables_for {
			String id_for = $3.sval;
			String id_comp = $7.sval;
			if(!id_for.equals(id_comp)) {
				Logger.getInstance().addError(lex.linea,"La variable de inicialización no es igual a la de condición");
			}
		}
	  | FOR '(' ID '=' CTE_INT ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables_for {Logger.getInstance().addError(lex.linea,"Se esperaba \";\" pero se recibio "+ $6.sval);}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables_for {Logger.getInstance().addError(lex.linea,"Se esperaba \";\" pero se recibio "+ $10.sval);}
          | FOR '(' ID '=' CTE_INT ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables_for {Logger.getInstance().addError(lex.linea,"Se esperaban \";\" en la sentencia FOR");}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' CTE_INT ')' bloque_ejecutables_for {Logger.getInstance().addError(lex.linea,"Se esperaba UP o DOWN en la sentencia FOR");}
          | FOR '(' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables_for {Logger.getInstance().addError(lex.linea,"Falta inicialización en la sentencia FOR");}
          | FOR '(' ID '=' CTE_INT ';' incr_decr CTE_INT ')' bloque_ejecutables_for {Logger.getInstance().addError(lex.linea,"Falta condicion en la sentencia FOR");}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ')' bloque_ejecutables_for {Logger.getInstance().addError(lex.linea,"Falta incremento en la sentencia FOR");}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT bloque_ejecutables_for {Logger.getInstance().addError(lex.linea,"Se esperaba \")\" en la sentencia FOR");}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' declarativa {Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del FOR");}
          ;

incr_decr : UP {}
          | DOWN {}
          ;

%%

AnalizadorLexico lex;

public Parser(AnalizadorLexico lex)
{
	this.lex = lex;
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