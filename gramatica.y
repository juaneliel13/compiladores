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

sentencia : declarativa { System.out.println("una wea");}
          | ejecutable {}
          ;

declarativa : dec_variable {}
            | dec_procedimiento {}
            ;

dec_variable : tipo lista_variables ';' {}
             ;

tipo : INTEGER {}
     | FLOAT {}
     ;

lista_variables : ID {}
                | ID ',' lista_variables {}
                ;

dec_procedimiento : PROC ID '(' lista_parametros ')' NI '=' CTE_INT '{' conjunto_sentencias '}' {}
                  | PROC ID '(' ')' NI '=' CTE_INT '{' conjunto_sentencias '}' {}
                  ;

lista_parametros :parametro {}
                 | parametro  ',' parametro  {}
                 | parametro  ',' parametro  ',' parametro  {}
                 ;

parametro: ID {}
	   | VAR ID {}
	   ;

ejecutable : asignacion {}
           | seleccion {}
           | salida {}
           | llamada {}
           | iteracion {}
           ;

asignacion : ID '=' expresion ';' {}
           ;

expresion : expresion '+' termino {}
          | expresion '-' termino {}
          | termino {System.out.println("una wea termino");}
          ;

termino : termino '/' factor {System.out.println("una wea divisoria entre "  + $1.sval + " / " + $2.sval);}
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
       			int i = -(int) Integer.parseInt($1.sval);
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

seleccion : IF'(' expresion comparador expresion ')' THEN bloque_ejecutables END_INF {}
          | IF'(' expresion comparador expresion ')' THEN bloque_ejecutables ELSE bloque_ejecutables END_INF {System.out.println("una wea if");}
          ;

comparador : '<' {}
           | '>' {}
           | COMP {}
           | MAYOR_IGUAL {}
           | MENOR_IGUAL {}
           | DISTINTO {}
           ;

bloque_ejecutables : ejecutable {}
                   | '{' ejecutable bloque_ejecutables '}'
                   ;

salida : OUT '(' CADENA ')' ';' {}
       ;

llamada : ID '(' parametros ')' ';' {}
	| ID '(' ')' ';' {}
        ;

parametros : ID {}
           | ID ',' parametros {}
           ;

iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables {
			String id_for = $2.sval;
			String id_comp = $4.sval;
			System.out.println("$2: " + $2.sval);
			System.out.println("$4: " + $4.sval);
			if(!id_for.equals(id_comp)) {
				System.out.println("Error en la linea " + lex.linea + ": Error for(detallar mas adelante).");
			}
		}
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
	this.yylval = lex.yylval;
	return lex.getToken();
}

void yyerror(String a)
{


}


