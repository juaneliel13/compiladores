%{
package Compilador.Sintactico;
import Compilador.Lexico.AnalizadorLexico;
import Compilador.Utilidad.Logger;
import java.util.HashMap;
import Compilador.CodigoIntermedio.*;
import java.util.ArrayList;
import Compilador.Lexico.Tipos;


%}

%token IF THEN ELSE END_IF FUNC OUT RETURN INTEGER FLOAT FOR PROC NI VAR UP DOWN ID CTE_INT CTE_FLOAT CADENA MAYOR_IGUAL MENOR_IGUAL COMP DISTINTO CARACTER_INVALIDO END 0
%start programa
%%



programa : conjunto_sentencias {raiz = (Nodo)$1.obj;}
	 | END  {logger.addError(lex.linea,"Sin sentencias");}
	 | error END  {logger.addError(lex.linea,"Sin sentencias validas");}
         ;

conjunto_sentencias : sentencia {
					try{
						$$ = new ParserVal(new Bloque((Nodo)$1.obj,null));
					}
					catch(Exception e){
						error=true;
					}
				}
                    | sentencia  conjunto_sentencias { 	if($1.obj!=null && $2.obj!=null)
                    						$$ = new ParserVal(new Bloque((Nodo)$1.obj,(Nodo)$2.obj));
                    					else{
                    						if($2.obj==null)
                    							$$ = new ParserVal(new Bloque((Nodo)$1.obj,null));
                    						else
                    							$$ = new ParserVal(new Bloque((Nodo)$2.obj,null));
                    					}
                    				}
                    ;


sentencia : declarativa {$$=$1;}
          | ejecutable { $$ = $1; }
          | error ';'{logger.addError(lex.linea,"Sentencia mal escrita");}
	  ;

declarativa : dec_variable ';'{logger.addEvent(lex.linea,"Se encontró una sentencia declarativa de variable");
				$$=new ParserVal();}
            | dec_procedimiento {logger.addEvent(lex.linea,"Se encontró una sentencia declarativa de procedimiento");
            			$$=$1;}
            | dec_variable {logger.addError(lex.linea,"Se esperaba \";\"");}
            ;

dec_variable : tipo lista_variables  {
		for (String id : (ArrayList<String>)($2.obj)){
			if(lex.tablaDeSimbolos.containsKey(id+ambito)){
				HashMap<String, Object> var = lex.tablaDeSimbolos.get(id+ambito);
				String uso = (String)var.get("Uso");
				if(uso.equals("variable")){
					logger.addError(lex.linea,"Variable \""+ id + "\" redeclarada");
				}
				else
					logger.addError(lex.linea,"Identificador \""+ id + "\" en uso.");

 		    	}
 		    	else{
 		    		HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(id);
                                aux.put("Uso","variable");
                                aux.put("Tipo",$1.obj);
                                aux.put("Inic","?");
                                lex.tablaDeSimbolos.put(id+ambito,aux);
                        }

                  }
	     }
	     | tipo lista_variables '=' expresion {	logger.addError(lex.linea,"Asignacion en la declaración");

	     					  }
             | lista_variables  {logger.addError(lex.linea,"Se esperaba un tipo");}
             | error lista_variables  {logger.addError(lex.linea,"Tipo no valido");}
             | tipo error {logger.addError(lex.linea,"Se esperaba un identificador");}
             ;
tipo : INTEGER {
		$$=new ParserVal(Tipos.INTEGER);
		}
     | FLOAT {
     		$$=new ParserVal(Tipos.FLOAT);
     		}
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

encabezado_proc:PROC ID{if(lex.tablaDeSimbolos.containsKey($2.sval+ambito)){
                        	HashMap<String, Object> var = lex.tablaDeSimbolos.get($2.sval+ambito);
                        	String uso = (String)var.get("Uso");
                        	if(uso.equals("procedimiento")){
                        		logger.addError(lex.linea,"Procedimiento \""+ $2.sval + "\" redeclarado");
                        	}
                        	else
                                	logger.addError(lex.linea,"Identificador \""+ $2.sval + "\" en uso.");

                        }
			else{
				HashMap<String, Object> aux=lex.tablaDeSimbolos.remove($2.sval);
				aux.put("Uso","procedimiento");
				lex.tablaDeSimbolos.put($2.sval+ambito,aux);
				$$=new ParserVal($2.sval+ambito);
				ambito+="@"+$2.sval;
			}
			}

dec_procedimiento : encabezado_proc param_ni '{' conjunto_sentencias '}' {
		    	ambito=ambito.substring(0,ambito.lastIndexOf("@"));
			DecProc proc = new DecProc((Nodo)$4.obj,null,$1.sval);
			$$ = new ParserVal(proc);


		   }

                  | encabezado_proc param_ni conjunto_sentencias '}' { logger.addError(lex.linea,"Se esperaba \"{\"");
                  							ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  							}
                  | encabezado_proc param_ni '{'  '}'{ logger.addError(lex.linea,"Se esperaba una sentencia");
                  					ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  					}

                  ;
param_ni: '(' lista_parametros ')' NI '=' CTE_INT{
		String nombre = ambito.substring(ambito.lastIndexOf("@")+1,ambito.length());
		nombre=getIdentificador(nombre);
		HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(nombre);
		aux.put("NI",Integer.parseInt($6.sval));
		aux.put("Parametros", $2.obj);
		lex.tablaDeSimbolos.put(nombre,aux);
		HashMap<String, Object> aux2=lex.tablaDeSimbolos.remove($6.sval);
		Integer NI = (Integer)aux2.remove("NI");
		aux2.put("NI",++NI);
		lex.tablaDeSimbolos.put($6.sval,aux2);
		}
	| '(' ')' NI '=' CTE_INT {
		String nombre = ambito.substring(ambito.lastIndexOf("@")+1,ambito.length());
		nombre=getIdentificador(nombre);
		HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(nombre);
		aux.put("NI",Integer.parseInt($5.sval));
		lex.tablaDeSimbolos.put(nombre,aux);
	}
	| '(' lista_parametros ')' {    String nombre = ambito.substring(ambito.lastIndexOf("@")+1,ambito.length());
					nombre=getIdentificador(nombre);
					HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(nombre);
					aux.put("NI",0);
					aux.put("Parametros", $2.obj);
					lex.tablaDeSimbolos.put(nombre,aux);
					logger.addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");
	}
	| '(' ')'  {
        		String nombre = ambito.substring(ambito.lastIndexOf("@")+1,ambito.length());
        		System.out.println(nombre);
        		nombre=getIdentificador(nombre);
        		HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(nombre);
        		aux.put("NI",0);
        		lex.tablaDeSimbolos.put(nombre,aux);
        }



lista_parametros :parametro {
				ArrayList<Parametro> parametros = new ArrayList<>();
				parametros.add((Parametro)$1.obj);
				$$ = new ParserVal(parametros);
			    }
                 | parametro  ',' parametro  {
                 				ArrayList<Parametro> parametros = new ArrayList<>();
						parametros.add((Parametro)$1.obj);
						parametros.add((Parametro)$3.obj);
						$$ = new ParserVal(parametros);
                			     }
                 | parametro  ',' parametro  ',' parametro  {
                 						ArrayList<Parametro> parametros = new ArrayList<>();
                 						parametros.add((Parametro)$1.obj);
                 						parametros.add((Parametro)$3.obj);
                 						parametros.add((Parametro)$5.obj);
                 						$$ = new ParserVal(parametros);
                  					    }
                 | parametro  parametro  {logger.addError(lex.linea,"Se esperaba \",\"");}
                 | parametro   parametro  parametro  {logger.addError(lex.linea,"Se esperaba \",\"");}
                 | parametro ','  parametro  parametro  {logger.addError(lex.linea,"Se esperaba \",\"");}
                 | parametro   parametro ',' parametro  {logger.addError(lex.linea,"Se esperaba \",\"");}
                 | parametro ',' parametro ',' parametro error {logger.addError(lex.linea,"Se esperaban como maximo 3 parametros");}
                 ;

parametro: tipo ID {
		    HashMap<String, Object> aux=lex.tablaDeSimbolos.remove($2.sval);
		    aux.put("Uso","variable");
		    aux.put("Tipo",(Tipos)$1.obj);
		    aux.put("Inic","?");
                    lex.tablaDeSimbolos.put($2.sval+ambito,aux);
                    $$ = new ParserVal(new Parametro($2.sval+ambito,(Tipos)$1.obj,"COPIA"));
	   }
	   | VAR tipo ID {
	   			HashMap<String, Object> aux=lex.tablaDeSimbolos.remove($3.sval);
                                aux.put("Uso","variable");
                                aux.put("Tipo",(Tipos)$2.obj);
                                aux.put("Inic","?");
                                lex.tablaDeSimbolos.put($3.sval+ambito,aux);
	   			$$ = new ParserVal(new Parametro($3.sval+ambito,(Tipos)$2.obj,"VAR"));
	   		 }
	   | VAR ID {logger.addError(lex.linea,"Se esperaba tipo");}
	   | ID {logger.addError(lex.linea,"Se esperaba tipo");}
	   | VAR tipo error {logger.addError(lex.linea,"Se esperaba identificador");}
	   | tipo VAR ID {logger.addError(lex.linea,"Se esperaba VAR TIPO ID");
	   		 HashMap<String, Object> aux=lex.tablaDeSimbolos.remove($3.sval);
			 aux.put("Uso","variable");
			 aux.put("Tipo",(Tipos)$1.obj);
			 aux.put("Inic","?");
			 lex.tablaDeSimbolos.put($3.sval+ambito,aux);
	   		 $$ = new ParserVal(new Parametro($3.sval+ambito,(Tipos)$1.obj,"VAR"));
	   		 }
           ;

ejecutable : asignacion ';'{ logger.addEvent(lex.linea,"Se encontró una sentencia de asignación");
			     $$ = $1;
			   }
           | asignacion {logger.addError(lex.linea,"Se esperaba \";\"");}
           | seleccion {logger.addEvent(lex.linea,"Se encontró una sentencia de seleccion"); }
           | salida ';' {logger.addEvent(lex.linea,"Se encontró una sentencia de salida");}
           | salida {logger.addError(lex.linea,"Se esperaba \";\"");}
           | llamada ';' {logger.addEvent(lex.linea,"Se encontró una sentencia de llamada"); }
           | llamada {logger.addError(lex.linea,"Se esperaba \";\"");}
           | iteracion {logger.addEvent(lex.linea,"Se encontró una sentencia de control"); }
           ;

asignacion : ID '=' expresion  { String var =getIdentificador($1.sval);
				if(var==null){
                                	logger.addError(lex.linea,"Variable \""+ $1.sval+ "\" no declarada" );
                                }
				else{
					$1.obj = new Hoja(var);
					Asignacion asignacion = new Asignacion((ConTipo)$1.obj,(ConTipo)$3.obj);
					if(asignacion.getTipo()==null)
                                        	logger.addError(lex.linea,"Incompatibilidad de tipos en la asignacion");
                                        $$ = new ParserVal(asignacion);
				 }

			       }
	   | ID COMP expresion  {logger.addError(lex.linea,"Se encontró == en lugar de =");}
           | error '=' expresion {logger.addError(lex.linea,"Asignacion mal escrita");}
	   | ID '=' error {logger.addError(lex.linea,"Asignacion mal escrita");}
           ;

expresion : expresion '+' termino {
					Suma suma = new Suma((ConTipo)$1.obj,(ConTipo)$3.obj);
					suma.updateTipo();
					if(suma.getTipo()==null)
						logger.addError(lex.linea,"Incompatibilidad de tipos en la suma");
    				   	$$ = new ParserVal(suma);

    				  }
          | expresion '-' termino {
          				Resta resta = new Resta((ConTipo)$1.obj,(ConTipo)$3.obj);
          				resta.updateTipo();
          				if(resta.getTipo()==null)
                                        	logger.addError(lex.linea,"Incompatibilidad de tipos en la resta");
          			   	$$ = new ParserVal(resta);
          			  }
          | termino {
          	      $$ = $1;
          	    }
          ;

termino : termino '/' factor {
				Division division = new Division((ConTipo)$1.obj,(ConTipo)$3.obj);
				division.updateTipo();
				if(division.getTipo()==null)
                                        logger.addError(lex.linea,"Incompatibilidad de tipos en la division");
                                $$ = new ParserVal(division);
			     }
        | termino '*' factor {
        			Multiplicacion multiplicacion = new Multiplicacion((ConTipo)$1.obj,(ConTipo)$3.obj);
        			multiplicacion.updateTipo();
        		      	if(multiplicacion.getTipo()==null)
                                       logger.addError(lex.linea,"Incompatibilidad de tipos en la multiplicacion");
                                $$ = new ParserVal(multiplicacion);
        		     }
        | factor {
                   $$ = $1;
                 }
        ;

factor : ID {	String var = getIdentificador($1.sval);
		if(var==null){
			logger.addError(lex.linea,"Variable \""+ $1.sval+ "\" no declarada" );
		}
		else
			$$ = new ParserVal(new Hoja(var));
	}
       | CTE_INT {

       			if ($1.sval!=null){
				int i = (int) Integer.parseInt($1.sval);
				if ( i > (int) Math.pow(2, 15) - 1) {
					logger.addError(lex.linea,"Constante entera fuera de rango");
				} else {
					$$ = new ParserVal(new Hoja($1.sval));
				}
			}
		 }
       | CTE_FLOAT { 	if($1.sval!=null){
       				$$ = new ParserVal(new Hoja($1.sval));
       			}
       			}
       | '-' CTE_INT {
       			if($2.sval!=null){
				int i = -(int) Integer.parseInt($2.sval);
				if (!lex.tablaDeSimbolos.containsKey(String.valueOf(i))) {
					    HashMap<String, Object> aux = new HashMap<String, Object>();
					    aux.put("Tipo", Tipos.INTEGER);
					    lex.tablaDeSimbolos.put(String.valueOf(i), aux);
					}
				int aux = (int) lex.tablaDeSimbolos.get(String.valueOf(-i)).get("Contador");
				lex.tablaDeSimbolos.get(String.valueOf(-i)).put("Contador",aux-1);
				$$ = new ParserVal(new Hoja(String.valueOf(i)));
			}
       		     }
      | '-' CTE_FLOAT {

      			if($2.sval!=null){

				float f = -(float) Float.parseFloat($2.sval);
				if (!lex.tablaDeSimbolos.containsKey(String.valueOf(f))) {
					    HashMap<String, Object> aux = new HashMap<String, Object>();
					    aux.put("Tipo", Tipos.FLOAT);
					    lex.tablaDeSimbolos.put(String.valueOf(f), aux);
				}
				int aux = (int) lex.tablaDeSimbolos.get(String.valueOf(-f)).get("Contador");
				lex.tablaDeSimbolos.get(String.valueOf(-f)).put("Contador",aux-1);
				$$ = new ParserVal(new Hoja(String.valueOf(f)));
			}
       		       }
       		       ;

seleccion : IF condicion_if_parentesis THEN bloque_ejecutables_then END_IF {
									  CuerpoIf aux = new CuerpoIf((Nodo)$4.obj,null);
									  $$ = new ParserVal(new If((Nodo)$2.obj, aux));
 									}
          | IF condicion_if_parentesis THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF {
          											       CuerpoIf aux = new CuerpoIf((Nodo)$4.obj,(Nodo)$6.obj);
												       $$ = new ParserVal(new If((Nodo)$2.obj, aux));
          											     }
          | IF condicion_if_parentesis THEN bloque_ejecutables_then bloque_ejecutables_else END_IF {logger.addError(lex.linea,"Se esperaba ELSE");}
          | IF condicion_if_parentesis THEN bloque_ejecutables_then error {logger.addError(lex.linea,"Se esperaba END_IF");}
          | IF condicion_if_parentesis THEN bloque_ejecutables_then ELSE bloque_ejecutables_else error {logger.addError(lex.linea,"Se esperaba END_IF");}
          | IF condicion_if_parentesis bloque_ejecutables_then END_IF {logger.addError(lex.linea,"Se esperaba THEN");}
          | IF condicion_if_parentesis bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF {logger.addError(lex.linea,"Se esperaba THEN");}
          | IF condicion_if_parentesis THEN  END_IF {logger.addError(lex.linea,"No se encontraron sentencias ejecutables");}
          | IF condicion_if_parentesis THEN declarativa END_IF {logger.addError(lex.linea,"No se permite declaraciones dentro del IF");}
          | IF condicion_if_parentesis THEN bloque_ejecutables_then ELSE declarativa END_IF {logger.addError(lex.linea,"No se permite declaraciones dentro del ELSE");}
          | IF condicion_if_parentesis THEN declarativa ELSE bloque_ejecutables_then END_IF {logger.addError(lex.linea,"No se permite declaraciones dentro del IF");}
          | IF condicion_if_parentesis THEN declarativa ELSE declarativa END_IF {logger.addError(lex.linea,"No se permite declaraciones dentro del IF");}
          ;

condicion_if_parentesis : '(' condicion_if ')' { $$=$2; }
			| condicion_if ')' { logger.addError(lex.linea, "Falta parentesis de abertura en la condicion"); }
			| '(' condicion_if { logger.addError(lex.linea, "Falta parentesis de cierre en la condicion"); }
			| condicion_if { logger.addError(lex.linea, "Faltan ambos parentesis en la condicion"); }
			;

condicion_if: expresion comparador expresion{
					      Operador aux = (Operador)$2.obj;
					      aux.izquierdo = (Nodo)$1.obj;
					      aux.derecho = (Nodo)$3.obj;
					      aux.updateTipo();
					      if(aux.getTipo()==null)
					      	logger.addError(lex.linea,"Incompatibilidad de tipos en la comparacion");
					      $$ = $2;
					    }
            | expresion error {logger.addError(lex.linea,"Condicion mal escrita");}
            | comparador expresion {}
	    ;

comparador : '<' { $$ = new ParserVal(new Menor(null,null));}
           | '>' { $$ = new ParserVal(new Mayor(null,null));}
           | COMP { $$ = new ParserVal(new Igual(null,null));}
           | MAYOR_IGUAL { $$ = new ParserVal(new MayorIgual(null,null));}
           | MENOR_IGUAL { $$ = new ParserVal(new MenorIgual(null,null));}
           | DISTINTO { $$ = new ParserVal(new Distinto(null,null));}
           ;

bloque_ejecutables_then:bloque_ejecutables_llaves {
		       				    $$ = new ParserVal(new Then((Nodo)$1.obj));
		       				}
		       ;

bloque_ejecutables_else:bloque_ejecutables_llaves {
		       				    $$ = new ParserVal(new Else((Nodo)$1.obj));
		       				  }
		       ;

bloque_ejecutables_for:bloque_ejecutables_llaves {
		       				    $$ = new ParserVal(new Bloque((Nodo)$1.obj,null));
		       				  }
		       ;

bloque_ejecutables_llaves: '{' bloque_ejecutables '}' { $$ = new ParserVal($2.obj);}
			 | '{' '}' {}
			 //| bloque_ejecutables '}' {}
			 | ejecutable {}
			 ;


bloque_ejecutables : ejecutable {
					try{
						$$ = new ParserVal(new Bloque((Nodo)$1.obj,null));
					    }
					    catch(Exception e){
						error=true;
					    }
        			}
                   | ejecutable bloque_ejecutables  {
                   					if($1.obj!=null && $2.obj!=null)
                                                            $$ = new ParserVal(new Bloque((Nodo)$1.obj,(Nodo)$2.obj));
                                                        else{
                                                            if($2.obj==null)
                                                                $$ = new ParserVal(new Bloque((Nodo)$1.obj,null));
                                                            else
                                                                $$ = new ParserVal(new Bloque((Nodo)$2.obj,null));
							}
                   				    }
                   ;

salida : OUT '(' CADENA ')' { $$=new ParserVal(new Salida($3.sval));}
       | OUT '(' ID ')' {
       			String var =getIdentificador($3.sval);
                        if(var==null){
                        	logger.addError(lex.linea,"Variable \""+ $3.sval+ "\" no declarada" );
                        }else
       				$$=new ParserVal(new Salida(var)); }
       | OUT '(' CTE_INT ')' {$$=new ParserVal(new Salida($3.sval));}
       | OUT '(' '-' CTE_INT ')' {$$=new ParserVal(new Salida("-"+$4.sval));}
       | OUT '(' CTE_FLOAT ')' {$$=new ParserVal(new Salida($3.sval));}
       | OUT '(' '-' CTE_FLOAT ')' {$$=new ParserVal(new Salida("-"+$4.sval));}
       | OUT '(' ')' {logger.addError(lex.linea,"Se esperaba una cadena");}
       | OUT error {logger.addError(lex.linea,"Sentencia OUT mal escrita");}
       ;

llamada : ID '(' parametros ')'  {
				String proc = getIdentificador($1.sval);
				if(proc==null){
					logger.addError(lex.linea,"Procedimiento \""+ $1.sval+ "\" no declarado" );
				}
				else{
					String a;
					HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(proc);
					if(aux.get("Uso").equals("procedimiento")){
						int ni = (Integer) aux.get("NI");
						if ( ni <= 0 ) {
							logger.addError(lex.linea,"Se agotaron los llamados para el procedimiento \""+ $1.sval+ "\"" );
						} else {
							aux.put("NI",(Integer)ni-1);
						}
						if(aux.containsKey("Parametros")) {
							//estaria todo ok
							ArrayList<Parametro> parametros_func = (ArrayList) aux.get("Parametros");
							ArrayList<String> parametros_llamada = (ArrayList) $3.obj;
							int i;
							for ( i = 0; i < parametros_func.size() && i < parametros_llamada.size(); i++) {
								String parametro_func = getIdentificador(parametros_llamada.get(i));
								if (parametro_func != null) {
									//checkeo si el tipo es igual
									Tipos tipo_func = (Tipos) lex.tablaDeSimbolos.get(parametro_func).get("Tipo");
									if(tipo_func != parametros_func.get(i).tipo){
										logger.addError(lex.linea,"Parametro \"" + parametros_llamada.get(i) + "\" de tipo \""
                                                                                                                        + tipo_func + "\" no coincide con el parametro formal de tipo \"" + parametros_func.get(i).tipo +  "\" en el llamado a \"" + $1.sval + "\"" );
									}
									else{
										parametros_llamada.set(i,parametro_func);
									}

								} else {
									logger.addError(lex.linea,"Parametro en el procedimiento \"" + parametros_llamada.get(i) + "\" fuera de alcance" );
								}
							}
							if (i >= parametros_func.size() ^ i >= parametros_llamada.size()) {
								logger.addError(lex.linea, "Llamada a procedimiento con cantidad de parametros erronea" );
							}
							lex.tablaDeSimbolos.put(proc,aux);
							$$=new ParserVal(new Llamada(proc,parametros_llamada));
						} else {
							lex.tablaDeSimbolos.put(proc,aux);
							logger.addError(lex.linea,"Invocacion invalida, \"" + $1.sval + "\" no lleva parametros" );
						}
					}
					else {
						lex.tablaDeSimbolos.put(proc,aux);
						logger.addError(lex.linea,"Invocacion invalida, \"" + $1.sval + "\" no es un procedimiento" );
					}

				}
	}
	| ID '(' ')'  {
			String proc = getIdentificador($1.sval);
			if(proc==null){
				logger.addError(lex.linea,"Procedimiento \""+ $1.sval+ "\" no declarado" );
			}
			else{
				String a;
				HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(proc);
				if(aux.get("Uso").equals("procedimiento")){
					int ni = (Integer) aux.get("NI");
					if ( ni <= 0 ) {
						logger.addError(lex.linea,"Se agotaron los llamados para el procedimiento \""+ $1.sval+ "\"" );
					} else {
						aux.put("NI",(Integer)ni-1);
					}
					if(!aux.containsKey("Parametros")) {
						lex.tablaDeSimbolos.put(proc,aux);
						$$=new ParserVal(new Llamada(proc,null));

					} else {
						lex.tablaDeSimbolos.put(proc,aux);
						logger.addError(lex.linea,"Invocacion invalida, \"" + $1.sval + "\" lleva parametros" );
					}
				}
				else {
					lex.tablaDeSimbolos.put(proc,aux);
					logger.addError(lex.linea,"Invocacion invalida, \"" + $1.sval + "\" no es un procedimiento" );
				}

			}
	}
	;

parametros : ID {
		ArrayList<String> ids=new ArrayList<String>();
		String var = getIdentificador($1.sval);
		if(var==null){
			logger.addError(lex.linea,"Variable \""+ $1.sval + "\" no declarada" );
			$$ = new ParserVal(new ArrayList());
		}
		else{
			ids.add($1.sval);
			$$ = new ParserVal(ids);
		}
		}
           | ID ',' ID {
           		ArrayList<String> ids=new ArrayList<String>();
           		ids.add($1.sval);
           		ids.add($3.sval);
           		boolean e = false;
           		for (int i=0; i<ids.size();i++){
           			String var = getIdentificador(ids.get(i));
           			if(var==null){
					logger.addError(lex.linea,"Variable \"" + ids.get(i) + "\" no declarada" );
					e = true;
					break;//ver si se quiere que solo diga el primero que este mal o si dice todo
				}
           		}
           		if(e != true) {
           			$$ = new ParserVal(ids);
           		} else {
           			$$ = new ParserVal(new ArrayList());
           		}


           }
           | ID  ID {logger.addError(lex.linea,"Se esperaba \",\"");}
           | ID ',' ID ',' ID {
				ArrayList<String> ids=new ArrayList<String>();
				ids.add($1.sval);
				ids.add($3.sval);
				ids.add($5.sval);
				boolean e = false;
				for (int i=0; i<ids.size();i++){
					String var = getIdentificador(ids.get(i));
					if(var==null){
						logger.addError(lex.linea,"Variable \"" + ids.get(i) + "\" no declarada" );
						e = true;
						break;//ver si se quiere que solo diga el primero que este mal o si dice todo
					}
				}
				if(e != true) {
					$$ = new ParserVal(ids);
				} else {
					$$ = new ParserVal(new ArrayList());
				}
           }
           | ID  ID  ID {logger.addError(lex.linea,"Se esperaba \",\"");}
           | ID ',' ID  ID {logger.addError(lex.linea,"Se esperaba \",\"");}
           | ID  ID ',' ID {logger.addError(lex.linea,"Se esperaba \",\"");}
           | ID ',' ID ',' ID error {logger.addError(lex.linea,"Se esperaban como maximo 3 parametros");
           				ArrayList<String> aux = new ArrayList<>();
           				aux.add($1.sval);
           				aux.add($3.sval);
           				aux.add($5.sval);
           				$$ = new ParserVal(aux);
           }
           ;

iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables_for {
			String id_for = $3.sval;
			String id_comp = $7.sval;
			String var = getIdentificador(id_for);
			String var_comp = getIdentificador(id_comp);
			if(var==null){
				logger.addError(lex.linea,"Variable \""+ id_for+ "\" no declarada" );
			}
			else{
                        	if(!id_for.equals(id_comp)) {
					logger.addError(lex.linea,"La variable de inicialización no es igual a la de condición");
				}
				//Creando la parte de la inicializacion de codigo
				Asignacion inicializacion = new Asignacion(new Hoja(var),new Hoja($5.sval));

				//Creando la parte del incremento
				Operador incremento = (Operador)$11.obj;
				incremento.izquierdo = new Hoja(var);
				incremento.derecho = new Hoja($12.sval);
				incremento.updateTipo();
				Asignacion asig = new Asignacion(new Hoja(var),incremento);

				//Creando la parte de la condicion
				Comparador comp = (Comparador) $8.obj;
				comp.izquierdo = new Hoja(var_comp);
				comp.derecho = (Nodo) $9.obj;
				comp.updateTipo();
                                if(comp.getTipo()==null)
                                	logger.addError(lex.linea,"Incompatibilidad de tipos en la comparacion");


				//Agregandolo
				CuerpoFor cuerpoFor = new CuerpoFor((Nodo)$14.obj,asig);
				For forsito = new For(comp,cuerpoFor);
				Bloque bloque = new Bloque(inicializacion,forsito);
				$$ = new ParserVal(bloque);
			}

		}
	  | FOR '(' ID '=' CTE_INT ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables_for {logger.addError(lex.linea,"Se esperaba \";\" pero se recibio "+ $6.sval);}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables_for {logger.addError(lex.linea,"Se esperaba \";\" pero se recibio "+ $10.sval);}
          | FOR '(' ID '=' CTE_INT ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables_for {logger.addError(lex.linea,"Se esperaban \";\" en la sentencia FOR");}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' CTE_INT ')' bloque_ejecutables_for {logger.addError(lex.linea,"Se esperaba UP o DOWN en la sentencia FOR");}
          | FOR '(' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables_for {logger.addError(lex.linea,"Falta inicialización en la sentencia FOR");}
          | FOR '(' ID '=' CTE_INT ';' incr_decr CTE_INT ')' bloque_ejecutables_for {logger.addError(lex.linea,"Falta condicion en la sentencia FOR");}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ')' bloque_ejecutables_for {logger.addError(lex.linea,"Falta incremento en la sentencia FOR");}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT bloque_ejecutables_for {logger.addError(lex.linea,"Se esperaba \")\" en la sentencia FOR");}
          | FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' declarativa {logger.addError(lex.linea,"No se permite declaraciones dentro del FOR");}
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
public static String ambito;
ArrayList<String> listaVariables;
Logger logger = Logger.getInstance();
public boolean error = false;
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

public String getIdentificador(String sval){

	String id=sval+ambito;
       	while(!id.equals(sval)){
	       	if(!lex.tablaDeSimbolos.containsKey(id))
	       		id=id.substring(0,id.lastIndexOf("@"));
		else
	       		break;
	}
	if(id.equals(sval))
		return null;
	return id;
}


