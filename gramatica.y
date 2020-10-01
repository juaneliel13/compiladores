%{
package compiladores;
import compiladores.AnalizadorLexico.AnalizadorLexico;
import java.util.HashMap;
%}

%token IF THEN ELSE END_INF FUNC OUT RETURN INTEGER FLOAT FOR PROC NI VAR UP DOWN ID CTE_INT CTE_FLOAT CADENA MAYOR_IGUAL MENOR_IGUAL COMP DISTINTO
%start programa

%%


programa : conjunto_sentencias {}
         ;

conjunto_sentencias : sentencia {}
                    | sentencia conjunto_sentencias {}
                    ;

sentencia : declarativa {System.out.println("Se encontró una sentencia declarativa"); }
          | ejecutable {System.out.println("Se encontró una sentencia ejecutable"); }
          | error ';' {System.out.println("Error en la linea " +lex.linea + ": Sentencia mal escrita.");}

          ;

declarativa : dec_variable {}
            | dec_procedimiento {}
            ;

dec_variable : tipo lista_variables ';' {}
	     | tipo lista_variables '=' expresion ';' {System.out.println("Error en la linea " +lex.linea + ": Asignacion en la declaración.");}
             ;

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
                 ;

parametro: ID {}
	   | VAR ID {}
	   | VAR VAR ID {System.out.println("Error en la linea " +lex.linea + ": Se encontró VAR VAR en lugar de VAR.");}
	   ;

ejecutable : asignacion {}
           | seleccion {}
           | salida {}
           | llamada {}
           | iteracion {}
           ;

asignacion : ID '=' expresion ';' {}
	   | ID COMP expresion ';' {System.out.println("Error en la linea " +lex.linea + ": Se encontró == en lugar de =.");}
           ;

expresion : expresion '+' termino {}
          | expresion '-' termino {}
          | termino {System.out.println("una wea termino");}
          | expresion '+' '+' termino {System.out.println("Error en la linea " +lex.linea + ": Dos operadores + juntos.");}
         // | expresion '-' '-' termino {System.out.println("Error en la linea " +lex.linea + ": Dos operadores - juntos.");}
	 // | expresion termino {System.out.println("Error en la linea " +lex.linea + ": Se esperaba un operador.");}
	  ;

termino : termino '/' factor {System.out.println("una wea divisoria entre "  + $1.sval + " / " + $3.sval);}
        | termino '*' factor {System.out.println("una wea multiplicatoria");}
        | factor {System.out.println("una wea factor");}
        | termino '/' '/' factor {System.out.println("Error en la linea " +lex.linea + ": Dos operadores / juntos.");}
        | termino '*' '*' factor {System.out.println("Error en la linea " +lex.linea + ": Dos operadores * juntos.");}
       // | termino factor {System.out.println("Error en la linea " +lex.linea + ": Se esperaba un operador.");}
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

seleccion : IF '(' expresion comparador expresion ')' THEN bloque_ejecutables END_INF {System.out.println($3.sval+" "+$5.sval); }
          | IF '(' expresion comparador expresion ')' THEN bloque_ejecutables ELSE bloque_ejecutables END_INF {System.out.println("una wea if");}
          | IF '(' expresion comparador expresion THEN bloque_ejecutables END_INF {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición"); }
          //| IF '(' expresion comparador expresion ')' THEN bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF");}
          | IF '(' expresion comparador expresion THEN bloque_ejecutables ELSE bloque_ejecutables END_INF {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición"); }
          //| IF '(' expresion comparador expresion ')' THEN bloque_ejecutables ELSE bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF");}
          ;

comparador : '<' {}
           | '>' {}
           | COMP {}
           | MAYOR_IGUAL {}
           | MENOR_IGUAL {}
           | DISTINTO {}
           ;

bloque_ejecutables : ejecutable {}
		   | '{' ejecutable '}' {}
                   | '{' ejecutable bloque_ejecutables '}' {}
                   ;

salida : OUT '(' CADENA ')' ';' {}
       ;

llamada : ID '(' parametros ')' ';' {}
	| ID '(' ')' ';' {}
	| ID '(' parametros ';' {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de los parametros");}
        ;

parametros : ID {}
           | ID ',' ID {}
           | ID ',' ID ',' ID {}

iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables {
			String id_for = $3.sval;
			String id_comp = $7.sval;
			System.out.println("$3: " + $3.sval);
			System.out.println("$7: " + $7.sval);
			if(!id_for.equals(id_comp)) {
				System.out.println("Error en la linea " + lex.linea + ": Error for(detallar mas adelante).");
			}
		}
	  | FOR '(' ID '=' CTE_INT ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ; pero se recibio "+ $6.sval );}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ": Se esperaba ; pero se recibio "+ $10.sval );}
          | FOR '(' ID '=' CTE_INT ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables {System.out.println("Error en la linea " + lex.linea + ": Se esperaban ; en la sentencia FOR" );}
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


