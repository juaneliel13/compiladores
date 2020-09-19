%{

%}

%token IF THEN ELSE END_INF FUNC OUT RETURN INTEGER FLOAT FOR PROC NI VAR UP DOWN ID CTE_INT CTE_FLOAT CADENA MAYOR_IGUAL MENOR_IGUAL COMP DISTINTO 

%%


programa : conjunto_sentencias {}
         ;

conjunto_sentencias : sentencia {}
                    | sentencia conjunto_sentencias {}
                    ;

sentencia : declarativa {} 
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

dec_procedimiento : PROC ID '(' lista_parametros? ')' NI '=' CTE_INT '{' conjunto_sentencias '}' {}
                  ;

lista_parametros : VAR? tipo ID',' lista_parametros {}
                 | VAR? tipo ID {}
                 ;

ejecutable : asignacion {}
           | seleccion {}
           | salida {}
           | llamada {}
           | iteracion {}
           ;

asignacion : ID = expresion';' {}
           ;

expresion : expresion '+' termino {}
          | expresion '-' termino {}
          | termino {}
          ;

termino : termino '*' factor {}
        | termino '/' factor {}
        | factor {}
        ;

factor : ID {}
       | CTE_INT {}
       | CTE_FLOAT {}
       ;
       
seleccion : IF'(' expresion comparador expresion ')' bloque_ejecutables END_INF {}
          | IF'(' expresion comparador expresion ')' bloque_ejecutables ELSE bloque_ejecutables END_INF {}
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
        
salida : OUT'(' CADENA ')' ';' {} 
       ;

llamada : ID'(' parametros? ')' ';' {} 
        ;

parametros : ID {}
           | ID ',' parametros {}
           ;

iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT )' bloque_ejecutables {}
          ;

incr_decr : UP {}
          | DOWN {}
          ;

%%

