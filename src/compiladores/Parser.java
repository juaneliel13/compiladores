//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
package compiladores;
import compiladores.AnalizadorLexico.AnalizadorLexico;
import java.util.HashMap;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short THEN=258;
public final static short ELSE=259;
public final static short END_IF=260;
public final static short FUNC=261;
public final static short OUT=262;
public final static short RETURN=263;
public final static short INTEGER=264;
public final static short FLOAT=265;
public final static short FOR=266;
public final static short PROC=267;
public final static short NI=268;
public final static short VAR=269;
public final static short UP=270;
public final static short DOWN=271;
public final static short ID=272;
public final static short CTE_INT=273;
public final static short CTE_FLOAT=274;
public final static short CADENA=275;
public final static short MAYOR_IGUAL=276;
public final static short MENOR_IGUAL=277;
public final static short COMP=278;
public final static short DISTINTO=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    3,    3,    5,    5,    7,
    7,    8,    8,    6,    6,   10,   10,   10,   11,   11,
   11,    4,    4,    4,    4,    4,    4,    4,    4,   12,
   12,    9,    9,    9,   17,   17,   17,   18,   18,   18,
   18,   18,   13,   13,   13,   13,   13,   13,   19,   22,
   22,   22,   22,   22,   22,   20,   21,   23,   23,   23,
   14,   15,   15,   15,   24,   24,   24,   16,   16,   16,
   16,   25,   25,
};
final static short yylen[] = {                            2,
    1,    1,    2,    1,    1,    1,    1,    3,    5,    1,
    1,    1,    3,   11,   10,    1,    3,    5,    1,    2,
    3,    2,    1,    1,    2,    1,    2,    1,    1,    3,
    3,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    2,    2,    7,    9,    8,    6,    8,    8,    3,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    3,    4,
    4,    4,    3,    4,    1,    3,    5,   14,   13,   13,
   12,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   10,   11,    0,    0,    0,    0,    1,    0,
    4,    5,    6,    7,    0,    0,   24,    0,    0,   29,
    0,    0,    0,    0,    0,    0,    0,    3,    0,    0,
   22,   25,   27,   38,   39,   40,    0,    0,    0,   37,
    0,    0,    0,    0,    0,    0,    0,   63,    0,    0,
    8,    0,   41,   42,   53,   54,   52,   55,    0,    0,
   50,   51,    0,    0,    0,    0,    0,   61,    0,    0,
   19,    0,    0,    0,    0,   64,   62,   13,    0,    0,
    0,    0,   35,   36,    0,   58,    0,   56,    0,    0,
    0,   20,    0,    0,    0,    0,    9,    0,    0,   46,
    0,    0,    0,   21,    0,    0,    0,    0,   59,    0,
    0,   57,    0,    0,   43,    0,    0,    0,    0,    0,
    0,   67,   60,   48,   47,    0,   45,    0,    0,    0,
    0,   18,   44,   72,   73,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   15,    0,    0,    0,    0,    0,
   14,    0,   71,    0,    0,   69,    0,   70,   68,
};
final static short yydgoto[] = {                          8,
    9,   10,   11,   86,   13,   14,   15,   30,   38,   73,
   74,   16,   17,   18,   19,   20,   39,   40,   41,   87,
  111,   63,  112,   49,  137,
};
final static short yysindex[] = {                      -162,
  -22,    5,    0,    0,   56, -219,  -40,    0,    0, -162,
    0,    0,    0,    0, -213,    6,    0,   27,   42,    0,
  -37, -169, -152,   82,  -37,  -37,  -39,    0,   79,   19,
    0,    0,    0,    0,    0,    0, -191,  -36,   10,    0,
  -31,   86,   67,  -38,   46,   46,   87,    0,  -25, -213,
    0,  -37,    0,    0,    0,    0,    0,    0,  -37,  -37,
    0,    0,  -37,  -37,  -37,  -84, -128,    0, -140, -214,
    0, -134,   95,  100, -135,    0,    0,    0,  -12,   10,
   10,   46,    0,    0, -195,    0, -147,    0,  -84,  -47,
 -126,    0,   88, -114, -200,  111,    0, -106,  -84,    0,
  -95,  -56, -115,    0, -110,   97,  115, -104,    0,   44,
  -90,    0,  116,  -84,    0,  -80,  -37,  -56,   53,  -94,
 -200,    0,    0,    0,    0,  -79,    0,  -23,  -37, -162,
   60,    0,    0,    0,    0, -156,  -89,  -16,   61, -162,
  -88,  146, -156,  -83,    0,   64,  150,  -84,  -78,  151,
    0,  -84,    0,  152,  -84,    0,  -84,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   15,
    0,    0,    0,    0,    0,  107,    0,  121,  139,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   38,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    0,
    0,    0,    0,    0,   73,   93,  -11,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  153,    0,    0,    0,    0,    0,   25,
   49,  -30,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   -9,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  156,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   -5,    0,    0,   13,    0,    0,    0,  149,   12,    0,
  -81,    0,    0,    0,    0,    0,   57,   54,    0,  112,
  -60,  -67,  -10,    0,  -62,
};
final static int YYTABLESIZE=411;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         27,
   34,   48,   72,   61,   28,   62,   59,   37,   60,   67,
   49,  103,   12,  107,    2,   77,   85,   21,  109,   59,
   26,   60,   12,   61,   32,   62,   59,   85,   60,   65,
   59,   66,   60,   76,  117,  136,   45,   46,   85,  132,
  116,   34,  143,   34,   22,   34,   97,   65,   33,   66,
  129,   65,   24,  126,   91,   88,   64,   92,   29,   34,
   34,    1,   34,   79,   31,   32,    2,   32,   70,   32,
    5,   71,   31,  141,   82,  144,    7,   51,   88,   52,
  149,   53,   54,   32,   32,   32,   32,  110,   59,   33,
   60,   33,   30,   33,    1,   23,   12,   98,   12,    2,
   33,    3,    4,    5,    6,   42,   23,   33,   33,    7,
   33,   99,  100,  134,  135,   80,   81,   83,   84,   43,
   26,   44,   50,   34,  139,   34,   68,   69,  128,   89,
   75,   31,   90,   93,  146,   94,   96,  153,   28,    2,
  138,  156,   12,   95,  158,  104,  159,   32,  105,   32,
    1,   30,   12,  106,  108,    2,  118,  120,  121,    5,
  113,    1,  119,  114,  115,    7,    2,  122,  123,  124,
    5,   33,    1,   33,  125,  130,    7,    2,  131,  127,
  133,    5,  140,  142,  147,  145,  148,    7,  151,  150,
  152,  155,  157,   16,  154,   31,   17,   31,   78,    0,
  101,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   30,    0,   30,    0,   55,
   56,   57,   58,    0,  102,    0,   66,   49,    0,   23,
   70,   23,   47,   71,   34,   35,   36,   25,    0,   55,
   56,   57,   58,   26,    0,   26,  134,  135,    0,    0,
    0,    0,    0,  134,  135,    0,   34,   34,   34,   34,
   34,   28,   34,   28,   34,   34,   34,   34,    0,    0,
   34,   34,   34,    0,    0,    0,   34,   34,   34,   34,
   32,   32,   32,   32,   32,    0,   32,    0,   32,   32,
   32,   32,    0,    0,   32,   32,   32,    0,    0,    0,
   32,   32,   32,   32,   33,   33,   33,   33,   33,    0,
   33,    0,   33,   33,   33,   33,    0,    0,   33,   33,
   33,    0,    0,    0,   33,   33,   33,   33,   31,   31,
    0,   31,   31,    0,   31,    0,   31,   31,   31,   31,
    0,    0,    0,    0,   31,    0,    0,    0,   30,   30,
    0,   30,   30,    0,   30,    0,   30,   30,   30,   30,
    0,    0,   23,   23,   30,   23,   23,    0,   23,    0,
   23,   23,   23,   23,    0,    0,   26,   26,   23,   26,
   26,    0,   26,    0,   26,   26,   26,   26,    0,    0,
    0,    0,   26,    0,   28,   28,    0,   28,   28,    0,
   28,    0,   28,   28,   28,   28,    0,    0,    0,    0,
   28,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   41,   41,   60,   10,   62,   43,   45,   45,   41,
   41,   59,    0,   95,    0,   41,  123,   40,  125,   43,
   61,   45,   10,   60,    0,   62,   43,  123,   45,   41,
   43,   41,   45,   59,  102,   59,   25,   26,  123,  121,
  101,   41,   59,   43,   40,   45,   59,   59,    0,   59,
  118,   42,  272,  114,  269,   66,   47,  272,  272,   59,
   60,  257,   62,   52,   59,   41,  262,   43,  269,   45,
  266,  272,    0,  136,   63,  138,  272,   59,   89,   61,
  143,  273,  274,   59,   60,   59,   62,   98,   43,   41,
   45,   43,    0,   45,  257,   40,   59,   85,   61,  262,
   59,  264,  265,  266,  267,  275,    0,   59,   60,  272,
   62,  259,  260,  270,  271,   59,   60,   64,   65,  272,
    0,   40,   44,  123,  130,  125,   41,   61,  117,  258,
   44,   59,  273,  268,  140,   41,  272,  148,    0,  125,
  129,  152,  130,   44,  155,  272,  157,  123,   61,  125,
  257,   59,  140,  268,   44,  262,  272,   61,   44,  266,
  256,  257,  273,  259,  260,  272,  262,  272,  125,  260,
  266,  123,  257,  125,   59,  123,  272,  262,  273,  260,
  260,  266,  123,  273,  273,  125,   41,  272,  125,  273,
   41,   41,   41,   41,  273,  123,   41,  125,   50,   -1,
   89,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,   -1,  276,
  277,  278,  279,   -1,  272,   -1,  258,  258,   -1,  123,
  269,  125,  272,  272,  272,  273,  274,  278,   -1,  276,
  277,  278,  279,  123,   -1,  125,  270,  271,   -1,   -1,
   -1,   -1,   -1,  270,  271,   -1,  256,  257,  258,  259,
  260,  123,  262,  125,  264,  265,  266,  267,   -1,   -1,
  270,  271,  272,   -1,   -1,   -1,  276,  277,  278,  279,
  256,  257,  258,  259,  260,   -1,  262,   -1,  264,  265,
  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,
  276,  277,  278,  279,  256,  257,  258,  259,  260,   -1,
  262,   -1,  264,  265,  266,  267,   -1,   -1,  270,  271,
  272,   -1,   -1,   -1,  276,  277,  278,  279,  256,  257,
   -1,  259,  260,   -1,  262,   -1,  264,  265,  266,  267,
   -1,   -1,   -1,   -1,  272,   -1,   -1,   -1,  256,  257,
   -1,  259,  260,   -1,  262,   -1,  264,  265,  266,  267,
   -1,   -1,  256,  257,  272,  259,  260,   -1,  262,   -1,
  264,  265,  266,  267,   -1,   -1,  256,  257,  272,  259,
  260,   -1,  262,   -1,  264,  265,  266,  267,   -1,   -1,
   -1,   -1,  272,   -1,  256,  257,   -1,  259,  260,   -1,
  262,   -1,  264,  265,  266,  267,   -1,   -1,   -1,   -1,
  272,
};
}
final static short YYFINAL=8;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"IF","THEN","ELSE","END_IF","FUNC","OUT",
"RETURN","INTEGER","FLOAT","FOR","PROC","NI","VAR","UP","DOWN","ID","CTE_INT",
"CTE_FLOAT","CADENA","MAYOR_IGUAL","MENOR_IGUAL","COMP","DISTINTO",
};
final static String yyrule[] = {
"$accept : programa",
"programa : conjunto_sentencias",
"conjunto_sentencias : sentencia",
"conjunto_sentencias : sentencia conjunto_sentencias",
"sentencia : declarativa",
"sentencia : ejecutable",
"declarativa : dec_variable",
"declarativa : dec_procedimiento",
"dec_variable : tipo lista_variables ';'",
"dec_variable : tipo lista_variables '=' expresion ';'",
"tipo : INTEGER",
"tipo : FLOAT",
"lista_variables : ID",
"lista_variables : ID ',' lista_variables",
"dec_procedimiento : PROC ID '(' lista_parametros ')' NI '=' CTE_INT '{' conjunto_sentencias '}'",
"dec_procedimiento : PROC ID '(' ')' NI '=' CTE_INT '{' conjunto_sentencias '}'",
"lista_parametros : parametro",
"lista_parametros : parametro ',' parametro",
"lista_parametros : parametro ',' parametro ',' parametro",
"parametro : ID",
"parametro : VAR ID",
"parametro : VAR VAR ID",
"ejecutable : asignacion ';'",
"ejecutable : asignacion",
"ejecutable : seleccion",
"ejecutable : salida ';'",
"ejecutable : salida",
"ejecutable : llamada ';'",
"ejecutable : llamada",
"ejecutable : iteracion",
"asignacion : ID '=' expresion",
"asignacion : ID COMP expresion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '/' factor",
"termino : termino '*' factor",
"termino : factor",
"factor : ID",
"factor : CTE_INT",
"factor : CTE_FLOAT",
"factor : '-' CTE_INT",
"factor : '-' CTE_FLOAT",
"seleccion : IF '(' condicion_if ')' THEN bloque_ejecutables_then END_IF",
"seleccion : IF '(' condicion_if ')' THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF",
"seleccion : IF '(' condicion_if ')' THEN bloque_ejecutables_then bloque_ejecutables_else END_IF",
"seleccion : IF '(' condicion_if THEN bloque_ejecutables_then END_IF",
"seleccion : IF '(' condicion_if ')' THEN bloque_ejecutables_then error ';'",
"seleccion : IF '(' condicion_if THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF",
"condicion_if : expresion comparador expresion",
"comparador : '<'",
"comparador : '>'",
"comparador : COMP",
"comparador : MAYOR_IGUAL",
"comparador : MENOR_IGUAL",
"comparador : DISTINTO",
"bloque_ejecutables_then : bloque_ejecutables",
"bloque_ejecutables_else : bloque_ejecutables",
"bloque_ejecutables : ejecutable",
"bloque_ejecutables : '{' ejecutable '}'",
"bloque_ejecutables : '{' ejecutable bloque_ejecutables '}'",
"salida : OUT '(' CADENA ')'",
"llamada : ID '(' parametros ')'",
"llamada : ID '(' ')'",
"llamada : ID '(' parametros ';'",
"parametros : ID",
"parametros : ID ',' ID",
"parametros : ID ',' ID ',' ID",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables",
"incr_decr : UP",
"incr_decr : DOWN",
};

//#line 184 "gramatica.y"

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


//#line 419 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 13 "gramatica.y"
{}
break;
case 3:
//#line 17 "gramatica.y"
{}
break;
case 4:
//#line 22 "gramatica.y"
{System.out.println("Se encontró una sentencia declarativa"); }
break;
case 5:
//#line 23 "gramatica.y"
{System.out.println("Se encontró una sentencia ejecutable"); }
break;
case 6:
//#line 28 "gramatica.y"
{}
break;
case 7:
//#line 29 "gramatica.y"
{}
break;
case 8:
//#line 32 "gramatica.y"
{}
break;
case 9:
//#line 33 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Asignacion en la declaración.");}
break;
case 10:
//#line 36 "gramatica.y"
{}
break;
case 11:
//#line 37 "gramatica.y"
{}
break;
case 12:
//#line 40 "gramatica.y"
{}
break;
case 13:
//#line 41 "gramatica.y"
{}
break;
case 14:
//#line 44 "gramatica.y"
{System.out.println("procedimiento " + val_peek(9).sval);}
break;
case 15:
//#line 45 "gramatica.y"
{}
break;
case 16:
//#line 48 "gramatica.y"
{}
break;
case 17:
//#line 49 "gramatica.y"
{}
break;
case 18:
//#line 50 "gramatica.y"
{}
break;
case 19:
//#line 53 "gramatica.y"
{}
break;
case 20:
//#line 54 "gramatica.y"
{}
break;
case 21:
//#line 55 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se encontró VAR VAR en lugar de VAR.");}
break;
case 22:
//#line 58 "gramatica.y"
{}
break;
case 23:
//#line 59 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba ;.");}
break;
case 24:
//#line 60 "gramatica.y"
{}
break;
case 25:
//#line 61 "gramatica.y"
{}
break;
case 26:
//#line 62 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba ;.");}
break;
case 27:
//#line 63 "gramatica.y"
{}
break;
case 28:
//#line 64 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba ;.");}
break;
case 29:
//#line 65 "gramatica.y"
{}
break;
case 30:
//#line 68 "gramatica.y"
{}
break;
case 31:
//#line 69 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se encontró == en lugar de =.");}
break;
case 32:
//#line 72 "gramatica.y"
{}
break;
case 33:
//#line 73 "gramatica.y"
{}
break;
case 34:
//#line 74 "gramatica.y"
{System.out.println("una wea termino");}
break;
case 35:
//#line 77 "gramatica.y"
{System.out.println("una wea divisoria entre "  + val_peek(2).sval + " / " + val_peek(0).sval);}
break;
case 36:
//#line 78 "gramatica.y"
{System.out.println("una wea multiplicatoria");}
break;
case 37:
//#line 79 "gramatica.y"
{System.out.println("una wea factor");}
break;
case 38:
//#line 82 "gramatica.y"
{System.out.println("una wea identificatoria");}
break;
case 39:
//#line 83 "gramatica.y"
{
	   		int i = (int) Integer.parseInt(val_peek(0).sval);
	   		if ( i > (int) Math.pow(2, 15) - 1) {
	   			System.out.println("Error en la linea " +lex.linea + ": Constante entera fuera de rango.");
	   		}
		 }
break;
case 40:
//#line 89 "gramatica.y"
{}
break;
case 41:
//#line 90 "gramatica.y"
{
       			int i = -(int) Integer.parseInt(val_peek(0).sval);
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
break;
case 42:
//#line 105 "gramatica.y"
{
			float f = -(float) Float.parseFloat(val_peek(1).sval);
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
break;
case 43:
//#line 121 "gramatica.y"
{System.out.println(val_peek(4).sval+" "+val_peek(2).sval); }
break;
case 44:
//#line 122 "gramatica.y"
{System.out.println("una wea if");}
break;
case 45:
//#line 123 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ELSE"); }
break;
case 46:
//#line 124 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición"); }
break;
case 47:
//#line 125 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF");}
break;
case 48:
//#line 126 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición"); }
break;
case 49:
//#line 130 "gramatica.y"
{}
break;
case 50:
//#line 134 "gramatica.y"
{}
break;
case 51:
//#line 135 "gramatica.y"
{}
break;
case 52:
//#line 136 "gramatica.y"
{}
break;
case 53:
//#line 137 "gramatica.y"
{}
break;
case 54:
//#line 138 "gramatica.y"
{}
break;
case 55:
//#line 139 "gramatica.y"
{}
break;
case 56:
//#line 142 "gramatica.y"
{}
break;
case 57:
//#line 145 "gramatica.y"
{}
break;
case 58:
//#line 148 "gramatica.y"
{}
break;
case 59:
//#line 149 "gramatica.y"
{}
break;
case 60:
//#line 150 "gramatica.y"
{}
break;
case 61:
//#line 153 "gramatica.y"
{}
break;
case 62:
//#line 156 "gramatica.y"
{}
break;
case 63:
//#line 157 "gramatica.y"
{}
break;
case 64:
//#line 158 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de los parametros");}
break;
case 65:
//#line 161 "gramatica.y"
{}
break;
case 66:
//#line 162 "gramatica.y"
{}
break;
case 67:
//#line 163 "gramatica.y"
{}
break;
case 68:
//#line 165 "gramatica.y"
{
			String id_for = val_peek(11).sval;
			String id_comp = val_peek(7).sval;
			System.out.println("$3: " + val_peek(11).sval);
			System.out.println("$7: " + val_peek(7).sval);
			if(!id_for.equals(id_comp)) {
				System.out.println("Error en la linea " + lex.linea + ": Error for(detallar mas adelante).");
			}
		}
break;
case 69:
//#line 174 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ; pero se recibio "+ val_peek(7).sval );}
break;
case 70:
//#line 175 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ; pero se recibio "+ val_peek(3).sval );}
break;
case 71:
//#line 176 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaban ; en la sentencia FOR" );}
break;
case 72:
//#line 179 "gramatica.y"
{}
break;
case 73:
//#line 180 "gramatica.y"
{}
break;
//#line 896 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
