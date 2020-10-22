%{
package Compilador.Sintactico;
import Compilador.Lexico.AnalizadorLexico;
import Compilador.Utilidad.Logger;
import java.util.HashMap;
import Compilador.CodigoIntermedio.*;
import java.util.ArrayList;


%}

%token IF THEN ELSE END_IF FUNC OUT RETURN INTEGER FLOAT FOR PROC NI VAR UP DOWN ID CTE_INT CTE_FLOAT CADENA MAYOR_IGUAL MENOR_IGUAL COMP DISTINTO CARACTER_INVALIDO END 0
%start programa
%%



programa : conjunto_sentencias { raiz = (Nodo)$1.obj; }
	 | END  {Logger.getInstance().addError(lex.linea,"Sin sentencias");}
	 | error END  {Logger.getInstance().addError(lex.linea,"Sin sentencias validas");}
         ;

conjunto_sentencias : sentencia { $$ = new ParserVal(new Bloque((Nodo)$1.obj,null));}
                    | sentencia  conjunto_sentencias { $$ = new ParserVal(new Bloque((Nodo)$1.obj,(Nodo)$2.obj));}
                    ;


sentencia : declarativa { }
          | ejecutable { $$ = $1; }
          | error ';'{Logger.getInstance().addError(lex.linea,"Sentencia mal escrita");}
	  ;

declarativa : dec_variable ';'{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia declarativa de variable");}
            | dec_procedimiento {Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia declarativa de procedimiento");}
            | dec_variable {Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
            ;

dec_variable : tipo lista_variables  {
		for (String id : (ArrayList<String>)($2.obj)){
                	HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(id);
                	aux.put("Uso","variable");
 		    	lex.tablaDeSimbolos.put(id+ambito,aux);

                  }
	     }
	     | tipo lista_variables '=' expresion {Logger.getInstance().addError(lex.linea,"Asignacion en la declaración");}
             | lista_variables  {Logger.getInstance().addError(lex.linea,"Se esperaba un tipo");}
             | error lista_variables  {Logger.getInstance().addError(lex.linea,"Tipo no valido");}
             | tipo error {Logger.getInstance().addError(lex.linea,"Se esperaba un identificador");}
             ;
tipo : INTEGER {}
     | FLOAT {}
     ;

lista_variables : ID {
			ArrayList<String> aux=new ArrayList<String>();
			aux.add($1.sval);
			$$=new ParserVal(aux);
		}
                | ID ',' lista_variables {
                	ArrayList<String> aux = (ArrayList<String>)($3.obj);
                	aux.add($1.sval);
                	$$=$3;
                }
		;

encabezado_proc:PROC ID{HashMap<String, Object> aux=lex.tablaDeSimbolos.remove($2.sval);
			aux.put("Uso","procedimiento");
                        lex.tablaDeSimbolos.put($2.sval+ambito,aux);
			ambito+="@"+$2.sval;
			}

dec_procedimiento : encabezado_proc '(' lista_parametros ')' NI '=' CTE_INT '{' conjunto_sentencias '}' {
		    	ambito=ambito.substring(0,ambito.lastIndexOf("@"));
		   }
                  | encabezado_proc '(' ')' NI '=' CTE_INT '{' conjunto_sentencias '}' {ambito=ambito.substring(0,ambito.lastIndexOf("@"));}
                  | encabezado_proc '(' lista_parametros ')' '{' conjunto_sentencias '}' { Logger.getInstance().addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");
                  									   ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  									 }
                  | encabezado_proc '(' lista_parametros ')' NI '=' CTE_INT conjunto_sentencias '}' { Logger.getInstance().addError(lex.linea,"Se esperaba \"{\"");
                  										      ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  										    }
                  | encabezado_proc '(' ')' '{' conjunto_sentencias '}' { Logger.getInstance().addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");
                  							 ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  							}
                  | encabezado_proc '(' lista_parametros ')' NI '=' CTE_INT '{'  '}'{ Logger.getInstance().addError(lex.linea,"Se esperaba una sentencia");
                  								      ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  								    }
		  | encabezado_proc '(' ')' NI '=' CTE_INT '{'  '}'{Logger.getInstance().addError(lex.linea,"Se esperaba una sentencia");
		  						    ambito=ambito.substring(0,ambito.lastIndexOf("@"));
		  						   }
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

ejecutable : asignacion ';'{ Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de asignación");
			     $$ = $1;
			   }
           | asignacion {Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
           | seleccion {Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de seleccion"); }
           | salida ';' {Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de salida");}
           | salida {Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
           | llamada ';' {Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de llamada"); }
           | llamada {Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
           | iteracion {Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de control"); }
           ;

asignacion : ID '=' expresion  {
				 System.out.println("" + $1.obj);
				 $1.obj = new Hoja($1.sval);
				 $$ = new ParserVal(new Asignacion((Nodo)$1.obj,(Nodo)$3.obj));

			       }
	   | ID COMP expresion  {Logger.getInstance().addError(lex.linea,"Se encontró == en lugar de =");}
           | error '=' expresion {Logger.getInstance().addError(lex.linea,"Asignacion mal escrita");}
	   | ID '=' error {Logger.getInstance().addError(lex.linea,"Asignacion mal escrita");}
           ;

expresion : expresion '+' termino {
    				    $$ = new ParserVal(new Suma((Nodo)$1.obj,(Nodo)$3.obj));
    				  }
          | expresion '-' termino {
          			    $$ = new ParserVal(new Resta((Nodo)$1.obj,(Nodo)$3.obj));
          			  }
          | termino {
          	      $$ = $1;
          	    }
          ;

termino : termino '/' factor {
			       $$ = new ParserVal(new Division((Nodo)$1.obj,(Nodo)$3.obj));
			     }
        | termino '*' factor {
        		       $$ = new ParserVal(new Multiplicacion((Nodo)$1.obj,(Nodo)$3.obj));
        		     }
        | factor {
                   $$ = $1;
                 }
        ;

factor : ID { $$ = new ParserVal(new Hoja($1.sval));}
       | CTE_INT {
       			if ($1.sval!=null){
				int i = (int) Integer.parseInt($1.sval);
				if ( i > (int) Math.pow(2, 15) - 1) {
					Logger.getInstance().addError(lex.linea,"Constante entera fuera de rango");
				} else {
					$$ = new ParserVal(new Hoja($1.sval));
				}
			}
		 }
       | CTE_FLOAT { $$ = new ParserVal(new Hoja($1.sval)); }
       | '-' CTE_INT {
       			if($2.sval!=null){
				int i = -(int) Integer.parseInt($2.sval);
				if (!lex.tablaDeSimbolos.containsKey(String.valueOf(i))) {
					    HashMap<String, Object> aux = new HashMap<String, Object>();
					    aux.put("Tipo", "INT");
					    lex.tablaDeSimbolos.put(String.valueOf(i), aux);
					}
				int aux = (int) lex.tablaDeSimbolos.get(String.valueOf(-i)).get("Contador");
				lex.tablaDeSimbolos.get(String.valueOf(-i)).put("Contador",aux-1);
				$$ = new ParserVal(new Hoja($1.sval));
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
				lex.tablaDeSimbolos.get(String.valueOf(-f)).put("Contador",aux-1);
				$$ = new ParserVal(new Hoja($1.sval));
			}
       		       }
       		       ;

seleccion : IF '(' condicion_if ')' THEN bloque_ejecutables_then END_IF {
									  CuerpoIf aux = new CuerpoIf((Nodo)$6.obj,null);
									  $$ = new ParserVal(new If((Nodo)$3.obj, aux));
 									}
          | IF '(' condicion_if ')' THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF {
          											       CuerpoIf aux = new CuerpoIf((Nodo)$6.obj,(Nodo)$8.obj);
												       $$ = new ParserVal(new If((Nodo)$3.obj, aux));
          											     }
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

condicion_if:expresion comparador expresion {
					      Nodo aux = (Nodo)$2.obj;
					      aux.izquierdo = (Nodo)$1.obj;
					      aux.derecho = (Nodo)$3.obj;
					      $$ = $2;
					    }
            | expresion error {Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
            | comparador expresion {Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
            | expresion comparador {Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
	    ;


comparador : '<' { $$ = new ParserVal(new Menor(null,null));}
           | '>' { $$ = new ParserVal(new Mayor(null,null));}
           | COMP { $$ = new ParserVal(new Igual(null,null));}
           | MAYOR_IGUAL { $$ = new ParserVal(new MayorIgual(null,null));}
           | MENOR_IGUAL { $$ = new ParserVal(new MenorIgual(null,null));}
           | DISTINTO { $$ = new ParserVal(new Distinto(null,null));}
           ;

bloque_ejecutables_then:ejecutable{
				    $$ = new ParserVal(new Then((Nodo)$1.obj));
				  }
		       | '{'bloque_ejecutables'}' {
		       				    $$ = new ParserVal(new Then((Nodo)$1.obj));
		       				  }
		       ;

bloque_ejecutables_else:ejecutable{
				    $$ = new ParserVal(new Else((Nodo)$1.obj));
				  }
		       | '{'bloque_ejecutables'}' {
		       				    $$ = new ParserVal(new Else((Nodo)$1.obj));
		       				  }
		       ;

bloque_ejecutables_for:ejecutable{
				   $$ = new ParserVal(new Bloque((Nodo)$1.obj,null));
				 }
		       | '{'bloque_ejecutables'}' {
		       				    $$ = new ParserVal(new Bloque((Nodo)$1.obj,null));
		       				  }
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
			//Creando la parte de la inicializacion de codigo
			Asignacion inicializacion = new Asignacion(new Hoja($3.sval),new Hoja($5.sval));

			//Creando la parte del incremento
			Nodo incremento = (Nodo)$11.obj;
			incremento.izquierdo = new Hoja($3.sval);
			incremento.derecho = new Hoja($12.sval);
			Asignacion asig = new Asignacion(new Hoja($3.sval),incremento);

			//Creando la parte de la condicion
			Nodo comp = (Nodo) $8.obj;
			comp.izquierdo = new Hoja($7.sval);
			comp.derecho = (Nodo) $9.obj;

			//Agregandolo
			CuerpoFor cuerpoFor = new CuerpoFor((Nodo)$14.obj,asig);
			For forsito = new For(comp,cuerpoFor);
			Bloque bloque = new Bloque(inicializacion,forsito);
			$$ = new ParserVal(bloque);

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

incr_decr : UP {
		$$ = new ParserVal(new Suma(null,null));
	       }
          | DOWN {
          	   $$ = new ParserVal(new Resta(null,null));
	 	 }
          ;

%%

AnalizadorLexico lex;
public Nodo raiz = null;
String ambito;
ArrayList<String> listaVariables;

public Parser(AnalizadorLexico lex)
{
	this.lex = lex;
	ambito="@main";
	listaVariables = new ArrayList<String>();
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


