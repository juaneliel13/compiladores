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






//#line 2 "./resources/gramatica.y"
package Compilador.Sintactico;
import Compilador.Lexico.AnalizadorLexico;
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
public final static short CARACTER_INVALIDO=280;
public final static short END=0;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    1,    1,    2,    2,    2,    3,    3,
    3,    5,    5,    5,    5,    5,    7,    7,    8,    8,
    6,    6,    6,    6,    6,    6,    6,   10,   10,   10,
   10,   10,   10,   10,   11,   11,   11,   11,   11,    4,
    4,    4,    4,    4,    4,    4,    4,   12,   12,   12,
   12,    9,    9,    9,   17,   17,   17,   18,   18,   18,
   18,   18,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   19,   19,
   19,   19,   22,   22,   22,   22,   22,   22,   20,   21,
   23,   23,   23,   14,   14,   14,   15,   15,   24,   24,
   24,   24,   24,   24,   24,   16,   16,   16,   16,   16,
   16,   16,   16,   16,   16,   25,   25,
};
final static short yylen[] = {                            2,
    1,    1,    2,    1,    2,    1,    1,    2,    2,    1,
    1,    2,    4,    1,    2,    2,    1,    1,    1,    3,
   11,   10,    8,   10,    7,   10,    9,    1,    3,    5,
    2,    3,    4,    4,    2,    3,    2,    1,    3,    2,
    1,    1,    2,    1,    2,    1,    1,    3,    3,    3,
    3,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    2,    2,    7,    9,    8,    6,    7,    8,    9,    6,
    8,    6,    7,    9,    9,    9,    7,    9,    3,    2,
    2,    2,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    3,    4,    4,    3,    2,    4,    3,    1,    3,
    2,    5,    3,    4,    4,   14,   13,   13,   12,   13,
   10,   10,   11,   13,   14,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   17,   18,    0,    0,    0,    2,    0,
    1,    0,    6,    7,    0,   10,    0,   14,    0,   42,
    0,    0,   47,    0,    3,    8,    0,   15,    0,   96,
    0,    0,    0,    0,    0,    0,    0,    0,    5,    9,
   16,    0,   40,   43,   45,   58,   59,   60,    0,    0,
    0,   57,    0,   86,   87,   85,   88,   83,   84,    0,
    0,    0,    0,   95,    0,    0,    0,   51,    0,   20,
    0,   98,    0,    0,   61,   62,    0,    0,    0,    0,
    0,   80,    0,    0,    0,    0,   94,    0,    0,    0,
   38,    0,    0,    0,    0,    0,    0,   97,    0,    0,
    0,   55,   56,    0,    0,    0,    0,    0,   91,    0,
   89,    0,    0,    0,    0,   37,    0,    0,    0,   35,
    0,    0,    0,  103,    0,    0,    0,    0,    0,   66,
    0,   72,    0,    0,    0,   70,    0,    0,    0,   39,
   36,    0,    0,    0,    0,    0,    0,   32,  105,  104,
    0,    0,   77,   92,    0,    0,   90,    0,   73,    0,
    0,   63,    0,    0,    0,  116,  117,    0,    0,    0,
    0,   25,    0,    0,    0,   33,   34,  102,    0,   93,
   68,    0,    0,    0,    0,   65,   71,    0,    0,    0,
    0,    0,    0,   23,   30,   78,   76,   75,   74,   69,
   64,    0,    0,    0,    0,    0,   27,    0,    0,    0,
    0,    0,    0,    0,    0,  112,  111,   22,   26,    0,
   24,    0,    0,    0,    0,  113,    0,   21,    0,  109,
    0,    0,    0,  107,  110,    0,  114,  108,  115,  106,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,  109,   15,   16,   17,   18,   50,   94,
   95,   19,   20,   21,   22,   23,   51,   52,   61,  110,
  156,   62,  111,   73,  169,
};
final static short yysindex[] = {                       133,
    3,  -11,   -3,    0,    0,   -1, -255,  -40,    0,    0,
    0,  467,    0,    0,  -12,    0, -157,    0,   -9,    0,
   -6,   -2,    0,   30,    0,    0,  -18,    0,  177,    0,
  -34, -190,   46,  -18,  147, -184,  -30,   16,    0,    0,
    0,   34,    0,    0,    0,    0,    0,    0, -118,   35,
   -4,    0,   50,    0,    0,    0,    0,    0,    0,  -29,
  -35,  -18,   59,    0,  365,  -36,   35,    0,   35,    0,
  -10,    0,   62,  -18,    0,    0,  -18,  -18,  -18,  -18,
 -148,    0,  -18,  404,  390,   35,    0, -154,  -18, -129,
    0, -111, -150,   84,   79,   14, -144,    0,   35,   -4,
   -4,    0,    0,  404,   35,   70,  -38, -160,    0, -113,
    0,  -78, -100,   22,   24,    0, -155,   78,  467,    0,
 -110, -127,  137,    0, -131,   21,  -52,  407,  404,    0,
  -51,    0,  -47,  -95,  404,    0,  322, -102,   47,    0,
    0,  -97,   48,  122,  467,  220, -127,    0,    0,    0,
  -87,  404,    0,    0,   72,  -67,    0,  346,    0,   70,
  346,    0,  -65,  -61,  -18,    0,    0,  322,  -69,  -64,
   83,    0,  -49,  185, -127,    0,    0,    0,   52,    0,
    0,   64,  101,  102, -208,    0,    0,   71,  -18,  290,
  334,  421,  358,    0,    0,    0,    0,    0,    0,    0,
    0,   47,  103,  -19,  404,  404,    0,  266,  442,  271,
  139,  373,  -68,  404,  155,    0,    0,    0,    0,  304,
    0,  389,  404,  395,  165,    0,  402,    0,  404,    0,
  404,  281,  404,    0,    0,  346,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,   93,    0,    0,
    0,   15,    0,    0,  231,    0,    0,    0,  245,    0,
  269,  299,    0,   73,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  107,    0,    0,    0,    0,    0,    0,    0,  121,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  151,    0,  175,    0,
  403,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -33,    0,    0,  -32,    0,    0,    0,    0,
    0,    0,    0,    0,  411,  416,    0,    0,  211,   25,
   49,    0,    0,    0,  -23,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  417,    0,    0,  418,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  419,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  323,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    8,    0,  -82,  462,    0,    0,  599,   18,  486,    0,
   68,    0,    0,    0,    0,    0,  142,  267,    0,  -53,
  -63,  -24,  567,    0,  -84,
};
final static int YYTABLESIZE=803;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         37,
   54,   37,   25,   36,   92,   85,   64,   82,   81,   27,
   72,  119,  145,   77,    4,   78,   33,   79,   28,   39,
   35,  214,   35,   77,   52,   78,   49,  108,   29,  133,
   58,  113,   59,   97,   42,   83,   31,   80,   32,  213,
   89,   54,   79,   54,  108,   54,   40,  200,   53,   43,
  127,  201,   44,   70,  170,   28,   45,  125,  134,   54,
   54,   26,   54,   27,  151,   52,   77,   52,   78,   52,
  163,  164,   19,   36,   26,  182,   27,   77,  184,   78,
  138,   65,  139,   52,   52,   66,   52,   24,  179,   53,
   81,   53,   19,   53,   74,  106,    2,  185,   41,   87,
  140,    3,   98,  203,  183,    6,   12,   53,   53,  104,
   53,  107,  165,   77,   24,   78,  141,  211,  114,  215,
   50,  120,  122,   54,  121,   54,  143,  126,  225,  202,
   27,   19,    9,   19,    4,    5,    4,    5,  142,    4,
  149,   90,  116,  189,   91,  129,  130,   52,   28,   52,
   49,   19,  174,  239,   75,   76,  118,  144,  135,  136,
  160,    2,  123,  161,  162,   12,    3,  166,  167,  168,
    6,   53,  172,   53,   48,  171,  107,  131,    2,   50,
  147,  132,  173,    3,  178,    4,    5,    6,    7,  146,
  148,   49,  181,    8,  186,   19,  180,   19,  187,  208,
  210,  166,  167,  190,  224,  192,  152,  153,  191,   49,
   13,  158,  159,  176,  177,   19,  220,   19,  100,  101,
   24,   49,   84,  193,   82,   81,   82,    4,    5,   12,
   11,   12,   90,   48,   79,   91,   58,   34,   59,   34,
   63,   71,  195,   50,   41,   50,   54,   55,   56,   57,
  166,  167,   30,   46,   47,   48,   54,   54,   54,   54,
   54,   96,   54,  175,   54,   54,   54,   54,   44,   13,
   54,   54,   54,   49,   24,   49,   54,   54,   54,   54,
   52,   52,   52,   52,   52,  124,   52,   24,   52,   52,
   52,   52,  150,  137,   52,   52,   52,   48,   46,   48,
   52,   52,   52,   52,   53,   53,   53,   53,   53,  194,
   53,  196,   53,   53,   53,   53,  166,  167,   53,   53,
   53,  236,   67,  197,   53,   53,   53,   53,   19,   19,
  205,   19,   19,   13,   19,   13,   19,   19,   19,   19,
  166,  167,    4,    5,   19,  102,  103,   90,   19,   19,
   91,   19,   19,   11,   19,   11,   19,   19,   19,   19,
  198,  199,   12,   12,   19,   12,   12,   41,   12,   41,
   12,   12,   12,   12,  206,  212,   50,   50,   12,   50,
   50,   58,   50,   59,   50,   50,   50,   50,    1,    2,
  218,   44,   50,   44,    3,  221,    4,    5,    6,    7,
    4,    5,   68,  108,    8,   90,   49,   49,   91,   49,
   49,  222,   49,  223,   49,   49,   49,   49,   46,   47,
   48,   46,   49,   46,   58,   88,   59,  227,  228,  229,
   48,   48,   53,   48,   48,  231,   48,  232,   48,   48,
   48,   48,  233,   99,    0,   67,   48,   67,   46,   47,
   48,   28,   54,   55,   56,   57,  101,   31,  100,   29,
    0,   14,    0,    0,    0,    0,   13,   13,  108,   13,
   13,    0,   13,   14,   13,   13,   13,   13,    0,    0,
  209,    0,   13,    4,    5,    0,   11,   11,   90,   11,
   11,   91,   11,    0,   11,   11,   11,   11,    0,    0,
   41,   41,   11,   41,   41,    0,   41,    0,   41,   41,
   41,   41,  108,    0,   60,    0,   41,    0,    0,   67,
   69,    0,    0,    0,   44,   44,  108,   44,   44,  108,
   44,  154,   44,   44,   44,   44,  106,    2,    0,    0,
   44,    0,    3,    0,    0,  207,    6,   86,    0,    0,
    0,    0,  107,    0,   46,   46,    0,   46,   46,   99,
   46,    0,   46,   46,   46,   46,  219,    0,  105,  128,
   46,    0,    0,    0,  115,    0,    0,    0,   67,   67,
   14,   67,   67,    0,   67,    0,   67,   67,   67,   67,
    0,    0,    0,    0,   67,    0,    0,   54,   55,   56,
   57,  131,    2,    0,    0,    0,   14,    3,    0,    4,
    5,    6,    7,   38,    2,    0,    0,    8,    0,    3,
    0,    4,    5,    6,    7,    0,    0,    0,    0,    8,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   54,   55,   56,   57,    0,  106,    2,  112,    0,    0,
  188,    3,    0,   14,   14,    6,    0,    0,    0,  106,
    2,  107,  106,    2,   93,    3,    0,    0,    3,    6,
   14,    0,    6,    0,  204,  107,   38,    2,  107,    0,
    0,    0,    3,    0,    4,    5,    6,    7,  117,    0,
    0,    0,    8,   93,  155,  157,    0,   38,    2,    0,
  157,  157,    0,    3,    0,    4,    5,    6,    7,    0,
    0,    0,    0,    8,    0,    0,    0,    0,  157,    0,
   93,   93,   38,    2,    0,    0,    0,  157,    3,    0,
    4,    5,    6,    7,    0,    0,    0,    0,    8,    0,
    0,    0,    0,    0,   93,   93,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  216,  217,   93,    0,    0,    0,    0,    0,    0,
  226,    0,    0,    0,    0,    0,    0,    0,    0,  230,
    0,    0,    0,    0,    0,  234,    0,  235,  237,  238,
    0,    0,  240,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,    0,   44,   41,   41,   41,   41,   41,   61,
   41,  123,  123,   43,    0,   45,  272,   41,    1,   12,
   61,   41,   61,   43,    0,   45,   45,  123,   40,  112,
   60,   85,   62,   44,   17,   60,   40,   42,   40,   59,
   65,   41,   47,   43,  123,   45,   59,  256,    0,   59,
  104,  260,   59,   36,  139,   38,   59,   44,  112,   59,
   60,   59,   62,   61,   44,   41,   43,   43,   45,   45,
  134,  135,    0,   44,   59,  158,   61,   43,  161,   45,
   59,  272,   59,   59,   60,   40,   62,  272,  152,   41,
   41,   43,    0,   45,   61,  256,  257,  161,  256,   41,
  256,  262,   41,  188,  158,  266,    0,   59,   60,  258,
   62,  272,  137,   43,  272,   45,  272,  202,  273,  204,
    0,  272,   44,  123,   41,  125,  119,  272,  213,   59,
   61,   59,    0,   61,  264,  265,  264,  265,   61,  125,
  272,  269,  272,  168,  272,  259,  260,  123,  131,  125,
    0,   59,  145,  236,  273,  274,  268,  268,  259,  260,
  256,  257,   95,  259,  260,   59,  262,  270,  271,  272,
  266,  123,  125,  125,    0,  273,  272,  256,  257,   59,
   44,  260,   61,  262,  272,  264,  265,  266,  267,  122,
  123,   45,  260,  272,  260,  123,  125,  125,  260,  192,
  193,  270,  271,  273,  273,  123,  259,  260,  273,   59,
    0,  259,  260,  146,  147,  123,  209,  125,   77,   78,
  272,   45,  258,  273,  258,  258,  256,  264,  265,  123,
    0,  125,  269,   59,  258,  272,   60,  278,   62,  278,
  275,  272,  175,  123,    0,  125,  276,  277,  278,  279,
  270,  271,  256,  272,  273,  274,  256,  257,  258,  259,
  260,  272,  262,   44,  264,  265,  266,  267,    0,   59,
  270,  271,  272,  123,  272,  125,  276,  277,  278,  279,
  256,  257,  258,  259,  260,  272,  262,  272,  264,  265,
  266,  267,  272,  272,  270,  271,  272,  123,    0,  125,
  276,  277,  278,  279,  256,  257,  258,  259,  260,  125,
  262,  260,  264,  265,  266,  267,  270,  271,  270,  271,
  272,   41,    0,  260,  276,  277,  278,  279,  256,  257,
   41,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  270,  271,  264,  265,  272,   79,   80,  269,  256,  257,
  272,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  260,  260,  256,  257,  272,  259,  260,  123,  262,  125,
  264,  265,  266,  267,   41,  273,  256,  257,  272,  259,
  260,   60,  262,   62,  264,  265,  266,  267,  256,  257,
  125,  123,  272,  125,  262,  125,  264,  265,  266,  267,
  264,  265,  256,  123,  272,  269,  256,  257,  272,  259,
  260,  273,  262,   41,  264,  265,  266,  267,  272,  273,
  274,  123,  272,  125,   60,   61,   62,  273,  125,   41,
  256,  257,  256,  259,  260,   41,  262,  273,  264,  265,
  266,  267,   41,   41,   -1,  123,  272,  125,  272,  273,
  274,   41,  276,  277,  278,  279,   41,   41,   41,   41,
   -1,    0,   -1,   -1,   -1,   -1,  256,  257,  123,  259,
  260,   -1,  262,   12,  264,  265,  266,  267,   -1,   -1,
  123,   -1,  272,  264,  265,   -1,  256,  257,  269,  259,
  260,  272,  262,   -1,  264,  265,  266,  267,   -1,   -1,
  256,  257,  272,  259,  260,   -1,  262,   -1,  264,  265,
  266,  267,  123,   -1,   29,   -1,  272,   -1,   -1,   34,
   35,   -1,   -1,   -1,  256,  257,  123,  259,  260,  123,
  262,  125,  264,  265,  266,  267,  256,  257,   -1,   -1,
  272,   -1,  262,   -1,   -1,  125,  266,   62,   -1,   -1,
   -1,   -1,  272,   -1,  256,  257,   -1,  259,  260,   74,
  262,   -1,  264,  265,  266,  267,  125,   -1,   83,  108,
  272,   -1,   -1,   -1,   89,   -1,   -1,   -1,  256,  257,
  119,  259,  260,   -1,  262,   -1,  264,  265,  266,  267,
   -1,   -1,   -1,   -1,  272,   -1,   -1,  276,  277,  278,
  279,  256,  257,   -1,   -1,   -1,  145,  262,   -1,  264,
  265,  266,  267,  256,  257,   -1,   -1,  272,   -1,  262,
   -1,  264,  265,  266,  267,   -1,   -1,   -1,   -1,  272,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  279,   -1,  256,  257,  258,   -1,   -1,
  165,  262,   -1,  192,  193,  266,   -1,   -1,   -1,  256,
  257,  272,  256,  257,   66,  262,   -1,   -1,  262,  266,
  209,   -1,  266,   -1,  189,  272,  256,  257,  272,   -1,
   -1,   -1,  262,   -1,  264,  265,  266,  267,   90,   -1,
   -1,   -1,  272,   95,  128,  129,   -1,  256,  257,   -1,
  134,  135,   -1,  262,   -1,  264,  265,  266,  267,   -1,
   -1,   -1,   -1,  272,   -1,   -1,   -1,   -1,  152,   -1,
  122,  123,  256,  257,   -1,   -1,   -1,  161,  262,   -1,
  264,  265,  266,  267,   -1,   -1,   -1,   -1,  272,   -1,
   -1,   -1,   -1,   -1,  146,  147,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  205,  206,  175,   -1,   -1,   -1,   -1,   -1,   -1,
  214,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  223,
   -1,   -1,   -1,   -1,   -1,  229,   -1,  231,  232,  233,
   -1,   -1,  236,
};
}
final static short YYFINAL=10;
final static short YYMAXTOKEN=280;
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
"CARACTER_INVALIDO",
};
final static String yyrule[] = {
"$accept : programa",
"programa : conjunto_sentencias",
"programa : END",
"programa : error END",
"conjunto_sentencias : sentencia",
"conjunto_sentencias : sentencia conjunto_sentencias",
"sentencia : declarativa",
"sentencia : ejecutable",
"sentencia : error ';'",
"declarativa : dec_variable ';'",
"declarativa : dec_procedimiento",
"declarativa : dec_variable",
"dec_variable : tipo lista_variables",
"dec_variable : tipo lista_variables '=' expresion",
"dec_variable : lista_variables",
"dec_variable : error lista_variables",
"dec_variable : tipo error",
"tipo : INTEGER",
"tipo : FLOAT",
"lista_variables : ID",
"lista_variables : ID ',' lista_variables",
"dec_procedimiento : PROC ID '(' lista_parametros ')' NI '=' CTE_INT '{' conjunto_sentencias '}'",
"dec_procedimiento : PROC ID '(' ')' NI '=' CTE_INT '{' conjunto_sentencias '}'",
"dec_procedimiento : PROC ID '(' lista_parametros ')' '{' conjunto_sentencias '}'",
"dec_procedimiento : PROC ID '(' lista_parametros ')' NI '=' CTE_INT conjunto_sentencias '}'",
"dec_procedimiento : PROC ID '(' ')' '{' conjunto_sentencias '}'",
"dec_procedimiento : PROC ID '(' lista_parametros ')' NI '=' CTE_INT '{' '}'",
"dec_procedimiento : PROC ID '(' ')' NI '=' CTE_INT '{' '}'",
"lista_parametros : parametro",
"lista_parametros : parametro ',' parametro",
"lista_parametros : parametro ',' parametro ',' parametro",
"lista_parametros : parametro parametro",
"lista_parametros : parametro parametro parametro",
"lista_parametros : parametro ',' parametro parametro",
"lista_parametros : parametro parametro ',' parametro",
"parametro : tipo ID",
"parametro : VAR tipo ID",
"parametro : VAR ID",
"parametro : ID",
"parametro : VAR tipo error",
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
"asignacion : error '=' expresion",
"asignacion : ID '=' error",
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
"seleccion : IF '(' condicion_if ')' THEN bloque_ejecutables_then error",
"seleccion : IF '(' condicion_if THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF",
"seleccion : IF '(' condicion_if ')' THEN bloque_ejecutables_then ELSE bloque_ejecutables_else error",
"seleccion : IF '(' condicion_if ')' bloque_ejecutables_then END_IF",
"seleccion : IF '(' condicion_if ')' bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF",
"seleccion : IF '(' condicion_if ')' THEN END_IF",
"seleccion : IF '(' condicion_if ')' THEN declarativa END_IF",
"seleccion : IF '(' condicion_if ')' THEN bloque_ejecutables_then ELSE declarativa END_IF",
"seleccion : IF '(' condicion_if ')' THEN declarativa ELSE bloque_ejecutables_then END_IF",
"seleccion : IF '(' condicion_if ')' THEN declarativa ELSE declarativa END_IF",
"seleccion : IF '(' error ')' THEN bloque_ejecutables_then END_IF",
"seleccion : IF '(' error ')' THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF",
"condicion_if : expresion comparador expresion",
"condicion_if : expresion error",
"condicion_if : comparador expresion",
"condicion_if : expresion comparador",
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
"salida : OUT '(' ')'",
"salida : OUT error",
"llamada : ID '(' parametros ')'",
"llamada : ID '(' ')'",
"parametros : ID",
"parametros : ID ',' ID",
"parametros : ID ID",
"parametros : ID ',' ID ',' ID",
"parametros : ID ID ID",
"parametros : ID ',' ID ID",
"parametros : ID ID ',' ID",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' CTE_INT ')' bloque_ejecutables",
"iteracion : FOR '(' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ';' incr_decr CTE_INT ')' bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ')' bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' declarativa",
"incr_decr : UP",
"incr_decr : DOWN",
};

//#line 229 "./resources/gramatica.y"

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
//#line 578 "Parser.java"
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
//#line 13 "./resources/gramatica.y"
{}
break;
case 2:
//#line 14 "./resources/gramatica.y"
{System.out.println("Sin sentencias.");}
break;
case 3:
//#line 15 "./resources/gramatica.y"
{System.out.println("Sin sentencias.");}
break;
case 4:
//#line 18 "./resources/gramatica.y"
{}
break;
case 5:
//#line 19 "./resources/gramatica.y"
{}
break;
case 6:
//#line 23 "./resources/gramatica.y"
{}
break;
case 7:
//#line 24 "./resources/gramatica.y"
{ }
break;
case 8:
//#line 25 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Sentencia mal escrita.");}
break;
case 9:
//#line 28 "./resources/gramatica.y"
{System.out.println("Se encontró una sentencia declarativa de variable."); }
break;
case 10:
//#line 29 "./resources/gramatica.y"
{System.out.println("Se encontró una sentencia declarativa de procedimiento."); }
break;
case 11:
//#line 30 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\".");}
break;
case 12:
//#line 33 "./resources/gramatica.y"
{}
break;
case 13:
//#line 34 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Asignacion en la declaración.");}
break;
case 14:
//#line 35 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba un tipo.");}
break;
case 15:
//#line 36 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Tipo no valido.");}
break;
case 16:
//#line 37 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba un identificador.");}
break;
case 17:
//#line 39 "./resources/gramatica.y"
{}
break;
case 18:
//#line 40 "./resources/gramatica.y"
{}
break;
case 19:
//#line 43 "./resources/gramatica.y"
{}
break;
case 20:
//#line 44 "./resources/gramatica.y"
{}
break;
case 21:
//#line 47 "./resources/gramatica.y"
{}
break;
case 22:
//#line 48 "./resources/gramatica.y"
{}
break;
case 23:
//#line 49 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba NI=CTE_INT en la declaracion de PROC.");}
break;
case 24:
//#line 50 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \"{\" .");}
break;
case 25:
//#line 51 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba NI=CTE_INT en la declaracion de PROC.");}
break;
case 26:
//#line 52 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba una sentencia.");}
break;
case 27:
//#line 53 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba una sentencia.");}
break;
case 28:
//#line 56 "./resources/gramatica.y"
{}
break;
case 29:
//#line 57 "./resources/gramatica.y"
{}
break;
case 30:
//#line 58 "./resources/gramatica.y"
{}
break;
case 31:
//#line 59 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 32:
//#line 60 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 33:
//#line 61 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 34:
//#line 62 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 35:
//#line 65 "./resources/gramatica.y"
{}
break;
case 36:
//#line 66 "./resources/gramatica.y"
{}
break;
case 37:
//#line 67 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba tipo.");}
break;
case 38:
//#line 68 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba tipo.");}
break;
case 39:
//#line 69 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba identificador.");}
break;
case 40:
//#line 72 "./resources/gramatica.y"
{System.out.println("Se encontró una sentencia de asignación."); }
break;
case 41:
//#line 73 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\" .");}
break;
case 42:
//#line 74 "./resources/gramatica.y"
{System.out.println("Se encontró una sentencia de selección."); }
break;
case 43:
//#line 75 "./resources/gramatica.y"
{System.out.println("Se encontró una sentencia de salida."); }
break;
case 44:
//#line 76 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\" .");}
break;
case 45:
//#line 77 "./resources/gramatica.y"
{System.out.println("Se encontró una sentencia de llamada."); }
break;
case 46:
//#line 78 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\".");}
break;
case 47:
//#line 79 "./resources/gramatica.y"
{System.out.println("Se encontró una sentencia de control."); }
break;
case 48:
//#line 82 "./resources/gramatica.y"
{}
break;
case 49:
//#line 83 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se encontró == en lugar de =.");}
break;
case 50:
//#line 84 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Asignación mal escrita.");}
break;
case 51:
//#line 85 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Asignación mal escrita.");}
break;
case 52:
//#line 88 "./resources/gramatica.y"
{}
break;
case 53:
//#line 89 "./resources/gramatica.y"
{}
break;
case 54:
//#line 90 "./resources/gramatica.y"
{}
break;
case 55:
//#line 93 "./resources/gramatica.y"
{}
break;
case 56:
//#line 94 "./resources/gramatica.y"
{}
break;
case 57:
//#line 95 "./resources/gramatica.y"
{}
break;
case 58:
//#line 98 "./resources/gramatica.y"
{}
break;
case 59:
//#line 99 "./resources/gramatica.y"
{
       			if (val_peek(0).sval!=null){
				int i = (int) Integer.parseInt(val_peek(0).sval);
				if ( i > (int) Math.pow(2, 15) - 1) {
					System.out.println("Error en la linea " +lex.linea + ": Constante entera fuera de rango.");
				}
			}
		 }
break;
case 60:
//#line 107 "./resources/gramatica.y"
{}
break;
case 61:
//#line 108 "./resources/gramatica.y"
{
       			if(val_peek(0).sval!=null){
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
       		     }
break;
case 62:
//#line 124 "./resources/gramatica.y"
{
      			if(val_peek(0).sval!=null){
				float f = -(float) Float.parseFloat(val_peek(0).sval);
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
break;
case 63:
//#line 142 "./resources/gramatica.y"
{}
break;
case 64:
//#line 143 "./resources/gramatica.y"
{}
break;
case 65:
//#line 144 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ELSE."); }
break;
case 66:
//#line 145 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición."); }
break;
case 67:
//#line 146 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF.");}
break;
case 68:
//#line 147 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición."); }
break;
case 69:
//#line 148 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF.");}
break;
case 70:
//#line 149 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba THEN.");}
break;
case 71:
//#line 150 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba THEN.");}
break;
case 72:
//#line 151 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se encontraron sentencias ejecutables.");}
break;
case 73:
//#line 152 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del IF.");}
break;
case 74:
//#line 153 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del ELSE.");}
break;
case 75:
//#line 154 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del IF.");}
break;
case 76:
//#line 155 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del IF.");}
break;
case 77:
//#line 156 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 78:
//#line 157 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 79:
//#line 160 "./resources/gramatica.y"
{}
break;
case 80:
//#line 161 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 81:
//#line 162 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 82:
//#line 163 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 83:
//#line 167 "./resources/gramatica.y"
{}
break;
case 84:
//#line 168 "./resources/gramatica.y"
{}
break;
case 85:
//#line 169 "./resources/gramatica.y"
{}
break;
case 86:
//#line 170 "./resources/gramatica.y"
{}
break;
case 87:
//#line 171 "./resources/gramatica.y"
{}
break;
case 88:
//#line 172 "./resources/gramatica.y"
{}
break;
case 89:
//#line 175 "./resources/gramatica.y"
{}
break;
case 90:
//#line 178 "./resources/gramatica.y"
{}
break;
case 91:
//#line 181 "./resources/gramatica.y"
{}
break;
case 92:
//#line 182 "./resources/gramatica.y"
{}
break;
case 93:
//#line 183 "./resources/gramatica.y"
{}
break;
case 94:
//#line 186 "./resources/gramatica.y"
{}
break;
case 95:
//#line 187 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba una cadena.");}
break;
case 96:
//#line 188 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Sentencia OUT mal escrita.");}
break;
case 97:
//#line 191 "./resources/gramatica.y"
{}
break;
case 98:
//#line 192 "./resources/gramatica.y"
{}
break;
case 99:
//#line 195 "./resources/gramatica.y"
{}
break;
case 100:
//#line 196 "./resources/gramatica.y"
{}
break;
case 101:
//#line 197 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 102:
//#line 198 "./resources/gramatica.y"
{}
break;
case 103:
//#line 199 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 104:
//#line 200 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 105:
//#line 201 "./resources/gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 106:
//#line 204 "./resources/gramatica.y"
{
			String id_for = val_peek(11).sval;
			String id_comp = val_peek(7).sval;
			System.out.println("$3: " + val_peek(11).sval);
			System.out.println("$7: " + val_peek(7).sval);
			if(!id_for.equals(id_comp)) {
				System.out.println("Error en la linea " + lex.linea + ": La variable de inicialización no es igual a la de condición.");
			}
		}
break;
case 107:
//#line 213 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba \";\" pero se recibio "+ val_peek(7).sval+"." );}
break;
case 108:
//#line 214 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba \";\" pero se recibio "+ val_peek(3).sval+"." );}
break;
case 109:
//#line 215 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaban \";\" en la sentencia FOR." );}
break;
case 110:
//#line 216 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaban UP o DOWN en la sentencia FOR." );}
break;
case 111:
//#line 217 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta inicialización en la sentencia FOR.");}
break;
case 112:
//#line 218 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta condicion en la sentencia FOR.");}
break;
case 113:
//#line 219 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta incremento en la sentencia FOR.");}
break;
case 114:
//#line 220 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Se esperaba \")\" en la sentencia FOR.");}
break;
case 115:
//#line 221 "./resources/gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del FOR.");}
break;
case 116:
//#line 224 "./resources/gramatica.y"
{}
break;
case 117:
//#line 225 "./resources/gramatica.y"
{}
break;
//#line 1240 "Parser.java"
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
