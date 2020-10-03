%{
package compiladores;
import compiladores.AnalizadorLexico.AnalizadorLexico;
import java.util.HashMap;
%}

%token IF THEN ELSE END_IF FUNC OUT RETURN INTEGER FLOAT FOR PROC NI VAR UP DOWN ID CTE_INT CTE_FLOAT CADENA MAYOR_IGUAL MENOR_IGUAL COMP DISTINTO FIN_PROGRAMA END 0
%start programa

%%


programa : conjunto_sentencias {}
	 | END  {System.out.println("Sin sentencias");}
	 | error END  {System.out.println("Sin sentencias");}
         ;

conjunto_sentencias : sentencia {}
                    | sentencia  conjunto_sentencias {}
                    | error {System.out.println("Error en la linea " +lex.linea + ": Sentencia mal escrita.");}
                    ;//TODO: REVISAR ERROR


sentencia : declarativa {System.out.println("Se encontró una sentencia declarativa"); }
          | ejecutable {System.out.println("Se encontró una sentencia ejecutable"); }
	  ;

declarativa : dec_variable ';'{}
            | dec_procedimiento {}
            |dec_variable {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\" .");}
            ;

dec_variable : tipo lista_variables  {}
	     | tipo lista_variables '=' expresion {System.out.println("Error en la linea " +lex.linea + ": Asignacion en la declaración.");}
             | lista_variables  {System.out.println("Error en la linea " +lex.linea + ": Se esperaba un tipo.");}
             | error lista_variables  {System.out.println("Error en la linea " +lex.linea + ": Tipo no valido.");}
             | tipo error {System.out.println("Error en la linea " +lex.linea + ": Se esperaba un identificador.");}
             ;//TODO: VER EL NUMERO DE LINEA
tipo : INTEGER {}
     | FLOAT {}
     ;

lista_variables : ID {}
                | ID ',' lista_variables {}
		;

dec_procedimiento : PROC ID '(' lista_parametros ')' NI '=' CTE_INT '{' conjunto_sentencias '}' {System.out.println("procedimiento " + $2.sval);}
                  | PROC ID '(' ')' NI '=' CTE_INT '{' conjunto_sentencias '}' {}
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

ejecutable : asignacion ';'{}
           | asignacion {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\"' .");}
           | seleccion {}
           | salida ';' {System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\" .");}
           | salida {}
           | llamada ';'{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\"'.");}
           | llamada {}
           | iteracion {}
           ;

asignacion : ID '=' expresion  {}
	   | ID COMP expresion  {System.out.println("Error en la linea " +lex.linea + ": Se encontró == en lugar de =.");}
           ;

expresion : expresion '+' termino {}
          | expresion '-' termino {}
          | termino {System.out.println("una wea termino");}
          ;

termino : termino '/' factor {System.out.println("una wea divisoria entre "  + $1.sval + " / " + $3.sval);}
        | termino '*' factor {System.out.println("una wea multiplicatoria");}
        | factor {System.out.println("una wea factor");}
        ;

factor : ID {System.out.println("una wea identificatoria");}
       | CTE_INT {
	   		int i = (int) Integer.parseInt($1.sval);
	   		if ( i > (int) Math.pow(2, 15) - 1) {
	   			System.out.println("Error en la linea " +lex.linea + ": Constante entera fuera de rango.");
	   		}
		 }
       | CTE_FLOAT {}
       | '-' CTE_INT {
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
      | '-' CTE_FLOAT {
			float f = -(float) Float.parseFloat($1.sval);
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
       ;

seleccion : IF '(' condicion_if ')' THEN bloque_ejecutables_then END_IF {System.out.println($3.sval+" "+$5.sval); }
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF {System.out.println("una weaaaa if");}
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then bloque_ejecutables_else END_IF {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ELSE"); }
          | IF '(' condicion_if THEN bloque_ejecutables_then END_IF {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición"); }
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then error {System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF");}
          | IF '(' condicion_if THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición"); }
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then ELSE bloque_ejecutables_else error  {System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF");}
          | IF '(' condicion_if ')' bloque_ejecutables_then END_IF {System.out.println("Error en la linea " + lex.linea + ": Se esperaba THEN");}
          | IF '(' condicion_if ')' bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF {System.out.println("Error en la linea " + lex.linea + ": Se esperaba THEN");}
          ;

condicion_if:expresion comparador expresion {}
            | expresion error expresion{}
            | error comparador expresion{}
            | expresion comparador error {} //TODO:MOSTRAR MENSAJE DE ERROR
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
		   | '{' ejecutable  '}' {}
                   | '{' ejecutable  bloque_ejecutables '}' {}
                   ;

salida : OUT '(' CADENA ')' {}
       ;

llamada : ID '(' parametros ')'  {}
	| ID '(' ')'  {}
	| ID '(' parametros ';' {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de los parametros");}
        ;

parametros : ID {}
           | ID ',' ID {}
           | ID ',' ID ',' ID {}
           ;

iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables {
			String id_for = $3.sval;
			String id_comp = $7.sval;
			System.out.println("$3: " + $3.sval);
			System.out.println("$7: " + $7.sval);
			if(!id_for.equals(id_comp)) {
				System.out.println("Error en la linea " + lex.linea + ": Error for(detallar mas adelante).");
			}
		}
	  | FOR '(' ID '=' CTE_INT ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ; pero se recibio "+ $6.sval+"." );}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ; pero se recibio "+ $10.sval+"." );}
          | FOR '(' ID '=' CTE_INT ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ": Se esperaban ; en la sentencia FOR." );}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ": Se esperaban UP o DOWN en la sentencia FOR." );}
          | FOR '(' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ":  Falta inicialización en la sentencia FOR.");}
          | FOR '(' ID '=' CTE_INT ';' incr_decr CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ":  Falta condicion en la sentencia FOR.");}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ":  Falta incremento en la sentencia FOR.");}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ":  Se esperaba \")\" en la sentencia FOR.");}
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


