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
public final static short END_INF=260;
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
    0,    1,    1,    2,    2,    3,    3,    5,    7,    7,
    8,    8,    6,    6,    9,    9,    9,   10,   10,    4,
    4,    4,    4,    4,   11,   16,   16,   16,   17,   17,
   17,   18,   18,   18,   18,   18,   12,   12,   19,   19,
   19,   19,   19,   19,   20,   20,   13,   14,   14,   21,
   21,   15,   22,   22,
};
final static short yylen[] = {                            2,
    1,    1,    2,    1,    1,    1,    1,    3,    1,    1,
    1,    3,   11,   10,    1,    3,    5,    1,    2,    1,
    1,    1,    1,    1,    4,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    2,    2,    9,   11,    1,    1,
    1,    1,    1,    1,    1,    4,    5,    5,    4,    1,
    3,   14,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    9,   10,    0,    0,    0,    0,    1,    0,
    4,    5,    6,    7,    0,   20,   21,   22,   23,   24,
    0,    0,    0,    0,    0,    0,    3,    0,    0,   32,
   33,   34,    0,    0,    0,   31,    0,    0,    0,    0,
    0,    0,    0,    0,    8,   35,   36,   42,   43,   41,
   44,    0,    0,   39,   40,    0,    0,    0,    0,    0,
    0,   18,    0,    0,    0,    0,   49,    0,   25,   12,
    0,    0,    0,   29,   30,   47,    0,   19,    0,    0,
    0,   51,   48,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   45,    0,    0,    0,    0,   17,
    0,    0,   37,    0,    0,    0,    0,    0,    0,   14,
    0,   46,   38,   53,   54,    0,   13,    0,    0,   52,
};
final static short yydgoto[] = {                          8,
    9,   10,   11,   12,   13,   14,   15,   29,   64,   65,
   16,   17,   18,   19,   20,   34,   35,   36,   56,   96,
   42,  116,
};
final static short yysindex[] = {                      -205,
  -13,   30,    0,    0,   43, -255,   -8,    0,    0, -205,
    0,    0,    0,    0, -247,    0,    0,    0,    0,    0,
  -15, -232, -188,   45,  -38,  -15,    0,   42,   29,    0,
    0,    0, -218,  -23,    3,    0,   48,   31,  -40,   46,
   32,   52,   -5, -247,    0,    0,    0,    0,    0,    0,
    0,  -15,  -15,    0,    0,  -15,  -15,  -15,   35, -178,
 -176,    0, -171,   57,   55, -172,    0,   47,    0,    0,
    3,    3,   28,    0,    0,    0,   49,    0,   40, -166,
 -195,    0,    0, -154, -167, -164,   51,   63, -112,  -54,
   -9, -163, -195, -194,    0, -184,  -15, -205,   -7,    0,
 -112, -112,    0,   -1,  -12, -205,   -6, -145, -206,    0,
   -4,    0,    0,    0,    0, -156,    0,   77, -112,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   13,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   61,    0,    0,
    0,    0,    0,    0,  -41,    0,    0,    0,    0,   81,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   82,    0,    0,    0,    0,    0,
  -36,  -31,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   83,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    5,    0,    0,  -53,    0,    0,    0,   84,    0,  -46,
    0,    0,    0,    0,    0,  -10,   27,   24,   36,  -68,
   59,    0,
};
final static int YYTABLESIZE=259;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         28,
   63,   28,   41,   28,   26,   54,   26,   55,   26,   27,
   94,   27,    2,   27,   27,   43,   24,   28,   28,   52,
   28,   53,   26,   26,   28,   26,   21,   27,   27,   33,
   27,   25,  107,  108,   88,   95,   54,   52,   55,   53,
  101,   52,   37,   53,   58,   73,  100,   95,   95,   57,
  120,    1,   26,   69,   46,   47,    2,  109,    3,    4,
    5,    6,    1,  114,  115,   95,    7,    2,   84,   22,
   52,    5,   53,   61,  102,  103,   62,    7,   71,   72,
   74,   75,   23,   38,   39,   44,  104,   45,   59,   66,
   67,   60,   68,   76,   77,   78,   79,   80,   81,   40,
   86,   87,  105,   89,   90,   83,   93,   85,   91,   99,
  111,   92,  110,   98,  113,  106,  118,  119,  112,   11,
  117,   50,   15,   16,   82,   97,    0,   70,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    2,    0,    0,
    0,    0,    0,    0,    1,    0,    0,    0,    0,    2,
    0,    0,    0,    5,    0,    0,    0,    0,    0,    7,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   48,   49,   50,   51,    0,    0,    0,   61,    0,
    0,   62,    0,   40,   28,   28,   28,   28,    0,   26,
   26,   26,   26,    0,   27,   27,   27,   27,    0,    0,
    0,    0,   48,   49,   50,   51,   30,   31,   32,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   41,   43,   41,   45,   41,   60,   43,   62,   45,   41,
  123,   43,    0,   45,   10,   26,  272,   59,   60,   43,
   62,   45,   59,   60,  272,   62,   40,   59,   60,   45,
   62,   40,  101,  102,   81,   89,   60,   43,   62,   45,
   94,   43,  275,   45,   42,   56,   93,  101,  102,   47,
  119,  257,   61,   59,  273,  274,  262,   59,  264,  265,
  266,  267,  257,  270,  271,  119,  272,  262,   41,   40,
   43,  266,   45,  269,  259,  260,  272,  272,   52,   53,
   57,   58,   40,  272,   40,   44,   97,   59,   41,   44,
   59,   61,   41,   59,  273,  272,  268,   41,   44,  272,
   61,  268,   98,  258,  272,   59,   44,   59,  273,  273,
  106,   61,  125,  123,  260,  123,  273,   41,  125,   59,
  125,   41,   41,   41,   66,   90,   -1,   44,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,
   -1,   -1,   -1,   -1,  257,   -1,   -1,   -1,   -1,  262,
   -1,   -1,   -1,  266,   -1,   -1,   -1,   -1,   -1,  272,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,  279,   -1,   -1,   -1,  269,   -1,
   -1,  272,   -1,  272,  276,  277,  278,  279,   -1,  276,
  277,  278,  279,   -1,  276,  277,  278,  279,   -1,   -1,
   -1,   -1,  276,  277,  278,  279,  272,  273,  274,
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
null,null,null,null,null,null,null,"IF","THEN","ELSE","END_INF","FUNC","OUT",
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
"ejecutable : asignacion",
"ejecutable : seleccion",
"ejecutable : salida",
"ejecutable : llamada",
"ejecutable : iteracion",
"asignacion : ID '=' expresion ';'",
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
"seleccion : IF '(' expresion comparador expresion ')' THEN bloque_ejecutables END_INF",
"seleccion : IF '(' expresion comparador expresion ')' THEN bloque_ejecutables ELSE bloque_ejecutables END_INF",
"comparador : '<'",
"comparador : '>'",
"comparador : COMP",
"comparador : MAYOR_IGUAL",
"comparador : MENOR_IGUAL",
"comparador : DISTINTO",
"bloque_ejecutables : ejecutable",
"bloque_ejecutables : '{' ejecutable bloque_ejecutables '}'",
"salida : OUT '(' CADENA ')' ';'",
"llamada : ID '(' parametros ')' ';'",
"llamada : ID '(' ')' ';'",
"parametros : ID",
"parametros : ID ',' parametros",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables",
"incr_decr : UP",
"incr_decr : DOWN",
};

//#line 146 "gramatica.y"

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


//#line 350 "Parser.java"
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
case 2:
//#line 16 "gramatica.y"
{}
break;
case 3:
//#line 17 "gramatica.y"
{}
break;
case 4:
//#line 20 "gramatica.y"
{ System.out.println("una wea");}
break;
case 5:
//#line 21 "gramatica.y"
{}
break;
case 6:
//#line 24 "gramatica.y"
{}
break;
case 7:
//#line 25 "gramatica.y"
{}
break;
case 8:
//#line 28 "gramatica.y"
{}
break;
case 9:
//#line 31 "gramatica.y"
{}
break;
case 10:
//#line 32 "gramatica.y"
{}
break;
case 11:
//#line 35 "gramatica.y"
{}
break;
case 12:
//#line 36 "gramatica.y"
{}
break;
case 13:
//#line 39 "gramatica.y"
{}
break;
case 14:
//#line 40 "gramatica.y"
{}
break;
case 15:
//#line 43 "gramatica.y"
{}
break;
case 16:
//#line 44 "gramatica.y"
{}
break;
case 17:
//#line 45 "gramatica.y"
{}
break;
case 18:
//#line 48 "gramatica.y"
{}
break;
case 19:
//#line 49 "gramatica.y"
{}
break;
case 20:
//#line 52 "gramatica.y"
{}
break;
case 21:
//#line 53 "gramatica.y"
{}
break;
case 22:
//#line 54 "gramatica.y"
{}
break;
case 23:
//#line 55 "gramatica.y"
{}
break;
case 24:
//#line 56 "gramatica.y"
{}
break;
case 25:
//#line 59 "gramatica.y"
{}
break;
case 26:
//#line 62 "gramatica.y"
{}
break;
case 27:
//#line 63 "gramatica.y"
{}
break;
case 28:
//#line 64 "gramatica.y"
{System.out.println("una wea termino");}
break;
case 29:
//#line 67 "gramatica.y"
{System.out.println("una wea divisoria entre "  + val_peek(2).sval + " / " + val_peek(1).sval);}
break;
case 30:
//#line 68 "gramatica.y"
{System.out.println("una wea multiplicatoria");}
break;
case 31:
//#line 69 "gramatica.y"
{System.out.println("una wea factor");}
break;
case 32:
//#line 72 "gramatica.y"
{System.out.println("una wea identificatoria");}
break;
case 33:
//#line 73 "gramatica.y"
{
	   		int i = (int) Integer.parseInt(val_peek(0).sval);
	   		if ( i > (int) Math.pow(2, 15) - 1) {
	   			System.out.println("Error en la linea " +lex.linea + ": Constante entera fuera de rango.");
	   		}
		 }
break;
case 34:
//#line 79 "gramatica.y"
{}
break;
case 35:
//#line 80 "gramatica.y"
{
       			int i = -(int) Integer.parseInt(val_peek(1).sval);
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
case 36:
//#line 95 "gramatica.y"
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
case 37:
//#line 111 "gramatica.y"
{}
break;
case 38:
//#line 112 "gramatica.y"
{System.out.println("una wea if");}
break;
case 39:
//#line 115 "gramatica.y"
{}
break;
case 40:
//#line 116 "gramatica.y"
{}
break;
case 41:
//#line 117 "gramatica.y"
{}
break;
case 42:
//#line 118 "gramatica.y"
{}
break;
case 43:
//#line 119 "gramatica.y"
{}
break;
case 44:
//#line 120 "gramatica.y"
{}
break;
case 45:
//#line 123 "gramatica.y"
{}
break;
case 47:
//#line 127 "gramatica.y"
{}
break;
case 48:
//#line 130 "gramatica.y"
{}
break;
case 49:
//#line 131 "gramatica.y"
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
//#line 138 "gramatica.y"
{}
break;
case 53:
//#line 141 "gramatica.y"
{}
break;
case 54:
//#line 142 "gramatica.y"
{}
break;
//#line 743 "Parser.java"
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
