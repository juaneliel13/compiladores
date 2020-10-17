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
package Compilador.Sintactico;
import Compilador.Lexico.AnalizadorLexico;
import Compilador.Utilidad.Logger;
import java.util.HashMap;
import Compilador.CodigoIntermedio.*;



//#line 26 "Parser.java"




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
   19,   19,   22,   22,   22,   22,   22,   22,   20,   20,
   21,   21,   24,   24,   23,   23,   14,   14,   14,   15,
   15,   25,   25,   25,   25,   25,   25,   25,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   26,   26,
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
    2,    2,    1,    1,    1,    1,    1,    1,    1,    3,
    1,    3,    1,    3,    1,    2,    4,    3,    2,    4,
    3,    1,    3,    2,    5,    3,    4,    4,   14,   13,
   13,   12,   13,   10,   10,   11,   13,   14,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   17,   18,    0,    0,    0,    2,    0,
    1,    0,    6,    7,    0,   10,    0,   14,    0,   42,
    0,    0,   47,    0,    3,    8,    0,   15,    0,   99,
    0,    0,    0,    0,    0,    0,    0,    0,    5,    9,
   16,    0,   40,   43,   45,   58,   59,   60,    0,    0,
    0,   57,    0,   86,   87,   85,   88,   83,   84,    0,
    0,    0,    0,   98,    0,    0,    0,   51,    0,   20,
    0,  101,    0,    0,   61,   62,    0,    0,    0,    0,
    0,   80,    0,    0,    0,    0,   97,    0,    0,    0,
   38,    0,    0,    0,    0,    0,    0,  100,    0,    0,
    0,   55,   56,    0,    0,    0,    0,    0,   89,    0,
    0,    0,    0,    0,   37,    0,    0,    0,   35,    0,
    0,    0,  106,    0,    0,    0,    0,    0,    0,   66,
    0,   72,    0,    0,    0,   70,    0,    0,    0,   39,
   36,    0,    0,    0,    0,    0,    0,   32,  108,  107,
    0,    0,   77,   96,   90,    0,   91,    0,    0,   73,
    0,    0,   63,    0,    0,    0,  119,  120,    0,    0,
    0,    0,   25,    0,    0,    0,   33,   34,  105,    0,
    0,   68,    0,    0,    0,    0,   65,   71,    0,    0,
    0,    0,    0,    0,   23,   30,   78,   92,   76,   75,
   74,   69,   64,    0,    0,    0,    0,    0,   27,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   93,  115,
  114,   22,   26,    0,   24,    0,    0,    0,    0,  116,
    0,    0,   21,    0,  112,    0,    0,    0,   94,  110,
  113,    0,  117,  111,  118,  109,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,  219,   15,   16,   17,   18,   50,   94,
   95,   19,   20,   21,   22,   23,   51,   52,   61,  110,
  158,   62,  128,  220,   73,  170,
};
final static short yysindex[] = {                       133,
    3,   -8,   -3,    0,    0,   10, -255,  -40,    0,    0,
    0,  507,    0,    0,   -6,    0, -213,    0,   23,    0,
   30,   32,    0,   27,    0,    0,  -18,    0,  177,    0,
  -34, -174,   61,  -18,  147, -157,  -30,   16,    0,    0,
    0,   51,    0,    0,    0,    0,    0,    0, -235,   31,
    5,    0,   76,    0,    0,    0,    0,    0,    0,  -29,
  -35,  -18,   81,    0,  365,  -36,   31,    0,   31,    0,
  -10,    0,   84,  -18,    0,    0,  -18,  -18,  -18,  -18,
 -138,    0,  -18,  447,  434,   31,    0, -142,  -18, -186,
    0, -110, -137,  124,   79,   14,  -89,    0,   31,    5,
    5,    0,    0,  447,   31,  132,  -38,  -57,    0, -155,
 -103, -132,   22,   24,    0, -184,  136,  507,    0, -108,
  -52,  137,    0,  -69,   21, -113,  -57,   82,  458,    0,
  -51,    0, -104,  409,  458,    0,  322, -129,  -94,    0,
    0,  -67,   83,  143,  507,  220,  -52,    0,    0,    0,
  -58,  458,    0,    0,    0,  -57,    0,  -41,  -78,    0,
  132,  349,    0,  -17,   50,  -18,    0,    0,  322,  -49,
   39,  194,    0,   45,  199,  -52,    0,    0,    0,   86,
  206,    0,   87,  101,  102, -160,    0,    0,   71,  -18,
  334,  335,  482,  392,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -94,  118,  -19,  461,  461,    0,  271,
  494,  287,  141,  387, -100,  461,  156,  -57,    0,    0,
    0,    0,    0,  305,    0,  397,  461,  402,  171,    0,
  404,  327,    0,  461,    0,  461,  281,  461,    0,    0,
    0,  422,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,   93,    0,    0,
    0,   12,    0,    0,  231,    0,    0,    0,  245,    0,
  269,  299,    0,   73,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  107,    0,    0,    0,    0,    0,    0,    0,  121,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  151,    0,  175,    0,
  416,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -33,    0,    0,  -32,    0,    0,    0,    0,
    0,    0,    0,    0,  417,  418,    0,    0,  211,   25,
   49,    0,    0,    0,  -23,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  419,    0,    0,  420,    0,  337,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  423,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  323,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  424,    0,  -60,  518,    0,    0,  631,   18,  465,    0,
  428,    0,    0,    0,    0,    0,  113,  122,    0,  -56,
   33,  -24,  -99,  -98,    0, -109,
};
final static int YYTABLESIZE=807;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         37,
   54,   37,   25,   36,   92,   85,   64,   82,   81,   27,
   72,    4,  118,   77,  145,   78,   33,   79,   28,  108,
   35,  216,   35,   77,   52,   78,   49,  154,  112,  171,
   58,   29,   59,   97,   42,   83,   31,   75,   76,  215,
   89,   54,   41,   54,  108,   54,   80,  126,   53,   32,
  133,   79,   40,   70,  134,   28,  181,  124,   24,   54,
   54,   26,   54,   27,  151,   52,   77,   52,   78,   52,
   36,  140,   19,   77,   26,   78,   27,    4,    5,  205,
  138,   43,  139,   52,   52,  115,   52,  141,   44,   53,
   45,   53,   19,   53,  213,  202,  217,   65,  183,  203,
   66,  185,  184,  129,  130,  229,   12,   53,   53,  221,
   53,   74,  166,   77,   24,   78,   81,  230,  232,  104,
   50,   87,  121,   54,   98,   54,  135,  136,  235,  204,
  113,   19,    9,   19,  119,  240,    4,  241,  243,  244,
  167,  168,  169,  246,  190,  152,  153,   52,   28,   52,
   49,   19,  131,    2,  159,  160,  132,  117,    3,  144,
    4,    5,    6,    7,  120,   12,  164,  165,    8,  167,
  168,   53,  228,   53,   48,  167,  168,  131,    2,   50,
  147,  245,  125,    3,  180,    4,    5,    6,    7,  100,
  101,   49,   27,    8,  186,   19,  142,   19,  106,    2,
  102,  103,  149,  174,    3,  172,  155,  173,    6,   49,
   13,    4,    5,  179,  107,   19,   90,   19,  182,   91,
   24,   49,   84,  191,   82,   81,   82,    4,    5,   12,
   11,   12,   90,   48,   79,   91,   58,   34,   59,   34,
   63,   71,  187,   50,   41,   50,   54,   55,   56,   57,
  167,  168,   30,   46,   47,   48,   54,   54,   54,   54,
   54,   96,   54,  176,   54,   54,   54,   54,   44,   13,
   54,   54,   54,   49,   24,   49,   54,   54,   54,   54,
   52,   52,   52,   52,   52,  123,   52,   24,   52,   52,
   52,   52,  150,  137,   52,   52,   52,   48,   46,   48,
   52,   52,   52,   52,   53,   53,   53,   53,   53,  188,
   53,  192,   53,   53,   53,   53,  193,  194,   53,   53,
   53,  242,   67,  195,   53,   53,   53,   53,   19,   19,
  198,   19,   19,   13,   19,   13,   19,   19,   19,   19,
  167,  168,    4,    5,   19,  197,  199,   90,   19,   19,
   91,   19,   19,   11,   19,   11,   19,   19,   19,   19,
  200,  201,   12,   12,   19,   12,   12,   41,   12,   41,
   12,   12,   12,   12,  207,  208,   50,   50,   12,   50,
   50,   58,   50,   59,   50,   50,   50,   50,    1,    2,
  214,   44,   50,   44,    3,  222,    4,    5,    6,    7,
    4,    5,   68,  218,    8,   90,   49,   49,   91,   49,
   49,  225,   49,  226,   49,   49,   49,   49,   46,   47,
   48,   46,   49,   46,   58,   88,   59,  227,  231,  233,
   48,   48,   53,   48,   48,   39,   48,  234,   48,   48,
   48,   48,  236,  237,  238,   67,   48,   67,   46,   47,
   48,  239,   54,   55,   56,   57,  102,   28,  104,   31,
  103,   95,    0,   29,    0,    0,   13,   13,    0,   13,
   13,  156,   13,    0,   13,   13,   13,   13,    0,    0,
    0,    0,   13,    4,    5,    0,   11,   11,   90,   11,
   11,   91,   11,   60,   11,   11,   11,   11,   67,   69,
   41,   41,   11,   41,   41,    0,   41,    0,   41,   41,
   41,   41,    0,    0,  211,    0,   41,   14,    0,    0,
    0,    0,  122,    0,   44,   44,   86,   44,   44,   14,
   44,  156,   44,   44,   44,   44,  106,    2,   99,    0,
   44,  143,    3,    0,  218,    0,    6,  105,  146,  148,
    0,    0,  107,  114,   46,   46,  108,   46,   46,    0,
   46,    0,   46,   46,   46,   46,    0,    0,  175,  108,
   46,    0,    0,  177,  178,    0,    0,    0,   67,   67,
  156,   67,   67,  218,   67,    0,   67,   67,   67,   67,
    0,    0,    0,    0,   67,    0,    0,   54,   55,   56,
   57,  109,  109,  196,  131,    2,  209,    0,    0,    0,
    3,    0,    4,    5,    6,    7,  210,  212,  223,    0,
    8,  109,    0,    0,    0,  127,    0,    0,  109,    0,
  189,    0,    0,    0,  224,   14,    0,    0,    0,    0,
   54,   55,   56,   57,  127,    0,  157,   38,    2,    0,
    0,  157,  157,    3,  206,    4,    5,    6,    7,    0,
    0,    0,   14,    8,  161,    2,    0,  162,  163,  157,
    3,    0,    0,  127,    6,    0,  109,  131,    2,  157,
  107,    0,    0,    3,    0,    4,    5,    6,    7,  106,
    2,  111,    0,    8,    0,    3,   93,    0,    0,    6,
    0,    0,  106,    2,    0,  107,    0,    0,    3,    0,
   14,   14,    6,  106,    2,    0,  106,    2,  107,    3,
  116,    0,    3,    6,    0,   93,    6,    0,   14,  107,
    0,    0,  107,    0,    0,  127,    0,   38,    2,    0,
    0,    0,    0,    3,    0,    4,    5,    6,    7,   38,
    2,   93,   93,    8,    0,    3,    0,    4,    5,    6,
    7,    0,   38,    2,    0,    8,    0,    0,    3,    0,
    4,    5,    6,    7,    0,    0,   93,   93,    8,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   93,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,    0,   44,   41,   41,   41,   41,   41,   61,
   41,    0,  123,   43,  123,   45,  272,   41,    1,  123,
   61,   41,   61,   43,    0,   45,   45,  127,   85,  139,
   60,   40,   62,   44,   17,   60,   40,  273,  274,   59,
   65,   41,  256,   43,  123,   45,   42,  104,    0,   40,
  111,   47,   59,   36,  111,   38,  156,   44,  272,   59,
   60,   59,   62,   61,   44,   41,   43,   43,   45,   45,
   44,  256,    0,   43,   59,   45,   61,  264,  265,  189,
   59,   59,   59,   59,   60,  272,   62,  272,   59,   41,
   59,   43,    0,   45,  204,  256,  206,  272,  159,  260,
   40,  162,  159,  259,  260,  215,    0,   59,   60,  208,
   62,   61,  137,   43,  272,   45,   41,  216,  218,  258,
    0,   41,   44,  123,   41,  125,  259,  260,  227,   59,
  273,   59,    0,   61,  272,  234,  125,  236,  237,  238,
  270,  271,  272,  242,  169,  259,  260,  123,  131,  125,
    0,   59,  256,  257,  259,  260,  260,  268,  262,  268,
  264,  265,  266,  267,   41,   59,  134,  135,  272,  270,
  271,  123,  273,  125,    0,  270,  271,  256,  257,   59,
   44,  242,  272,  262,  152,  264,  265,  266,  267,   77,
   78,   45,   61,  272,  162,  123,   61,  125,  256,  257,
   79,   80,  272,   61,  262,  273,  125,  125,  266,   59,
    0,  264,  265,  272,  272,  123,  269,  125,  260,  272,
  272,   45,  258,  273,  258,  258,  256,  264,  265,  123,
    0,  125,  269,   59,  258,  272,   60,  278,   62,  278,
  275,  272,  260,  123,    0,  125,  276,  277,  278,  279,
  270,  271,  256,  272,  273,  274,  256,  257,  258,  259,
  260,  272,  262,   44,  264,  265,  266,  267,    0,   59,
  270,  271,  272,  123,  272,  125,  276,  277,  278,  279,
  256,  257,  258,  259,  260,  272,  262,  272,  264,  265,
  266,  267,  272,  272,  270,  271,  272,  123,    0,  125,
  276,  277,  278,  279,  256,  257,  258,  259,  260,  260,
  262,  273,  264,  265,  266,  267,  123,  273,  270,  271,
  272,   41,    0,  125,  276,  277,  278,  279,  256,  257,
  125,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  270,  271,  264,  265,  272,  260,  260,  269,  256,  257,
  272,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  260,  260,  256,  257,  272,  259,  260,  123,  262,  125,
  264,  265,  266,  267,   41,   41,  256,  257,  272,  259,
  260,   60,  262,   62,  264,  265,  266,  267,  256,  257,
  273,  123,  272,  125,  262,  125,  264,  265,  266,  267,
  264,  265,  256,  123,  272,  269,  256,  257,  272,  259,
  260,  125,  262,  273,  264,  265,  266,  267,  272,  273,
  274,  123,  272,  125,   60,   61,   62,   41,  273,  125,
  256,  257,  256,  259,  260,   12,  262,   41,  264,  265,
  266,  267,   41,  273,   41,  123,  272,  125,  272,  273,
  274,  125,  276,  277,  278,  279,   41,   41,   41,   41,
   41,  125,   -1,   41,   -1,   -1,  256,  257,   -1,  259,
  260,  123,  262,   -1,  264,  265,  266,  267,   -1,   -1,
   -1,   -1,  272,  264,  265,   -1,  256,  257,  269,  259,
  260,  272,  262,   29,  264,  265,  266,  267,   34,   35,
  256,  257,  272,  259,  260,   -1,  262,   -1,  264,  265,
  266,  267,   -1,   -1,  123,   -1,  272,    0,   -1,   -1,
   -1,   -1,   95,   -1,  256,  257,   62,  259,  260,   12,
  262,  123,  264,  265,  266,  267,  256,  257,   74,   -1,
  272,  118,  262,   -1,  123,   -1,  266,   83,  121,  122,
   -1,   -1,  272,   89,  256,  257,  123,  259,  260,   -1,
  262,   -1,  264,  265,  266,  267,   -1,   -1,  145,  123,
  272,   -1,   -1,  146,  147,   -1,   -1,   -1,  256,  257,
  123,  259,  260,  123,  262,   -1,  264,  265,  266,  267,
   -1,   -1,   -1,   -1,  272,   -1,   -1,  276,  277,  278,
  279,   84,   85,  176,  256,  257,  125,   -1,   -1,   -1,
  262,   -1,  264,  265,  266,  267,  193,  194,  125,   -1,
  272,  104,   -1,   -1,   -1,  108,   -1,   -1,  111,   -1,
  166,   -1,   -1,   -1,  211,  118,   -1,   -1,   -1,   -1,
  276,  277,  278,  279,  127,   -1,  129,  256,  257,   -1,
   -1,  134,  135,  262,  190,  264,  265,  266,  267,   -1,
   -1,   -1,  145,  272,  256,  257,   -1,  259,  260,  152,
  262,   -1,   -1,  156,  266,   -1,  159,  256,  257,  162,
  272,   -1,   -1,  262,   -1,  264,  265,  266,  267,  256,
  257,  258,   -1,  272,   -1,  262,   66,   -1,   -1,  266,
   -1,   -1,  256,  257,   -1,  272,   -1,   -1,  262,   -1,
  193,  194,  266,  256,  257,   -1,  256,  257,  272,  262,
   90,   -1,  262,  266,   -1,   95,  266,   -1,  211,  272,
   -1,   -1,  272,   -1,   -1,  218,   -1,  256,  257,   -1,
   -1,   -1,   -1,  262,   -1,  264,  265,  266,  267,  256,
  257,  121,  122,  272,   -1,  262,   -1,  264,  265,  266,
  267,   -1,  256,  257,   -1,  272,   -1,   -1,  262,   -1,
  264,  265,  266,  267,   -1,   -1,  146,  147,  272,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  176,
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
"bloque_ejecutables_then : ejecutable",
"bloque_ejecutables_then : '{' bloque_ejecutables '}'",
"bloque_ejecutables_else : ejecutable",
"bloque_ejecutables_else : '{' bloque_ejecutables '}'",
"bloque_ejecutables_for : ejecutable",
"bloque_ejecutables_for : '{' bloque_ejecutables '}'",
"bloque_ejecutables : ejecutable",
"bloque_ejecutables : ejecutable bloque_ejecutables",
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
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables_for",
"iteracion : FOR '(' ID '=' CTE_INT ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables_for",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables_for",
"iteracion : FOR '(' ID '=' CTE_INT ID comparador expresion incr_decr CTE_INT ')' bloque_ejecutables_for",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' CTE_INT ')' bloque_ejecutables_for",
"iteracion : FOR '(' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables_for",
"iteracion : FOR '(' ID '=' CTE_INT ';' incr_decr CTE_INT ')' bloque_ejecutables_for",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ')' bloque_ejecutables_for",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT bloque_ejecutables_for",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT ')' declarativa",
"incr_decr : UP",
"incr_decr : DOWN",
};

//#line 252 "gramatica.y"

AnalizadorLexico lex;
public Nodo raiz = null;


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

//#line 591 "Parser.java"
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
//#line 18 "gramatica.y"
{ raiz = (Nodo)val_peek(0).obj; }
break;
case 2:
//#line 19 "gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Sin sentencias");}
break;
case 3:
//#line 20 "gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Sin sentencias validas");}
break;
case 4:
//#line 23 "gramatica.y"
{ yyval = new ParserVal(new Bloque((Nodo)val_peek(0).obj,null));}
break;
case 5:
//#line 24 "gramatica.y"
{ yyval = new ParserVal(new Bloque((Nodo)val_peek(1).obj,(Nodo)val_peek(0).obj));}
break;
case 6:
//#line 28 "gramatica.y"
{ }
break;
case 7:
//#line 29 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 8:
//#line 30 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Sentencia mal escrita");}
break;
case 9:
//#line 33 "gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia declarativa de variable");}
break;
case 10:
//#line 34 "gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia declarativa de procedimiento");}
break;
case 11:
//#line 35 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
break;
case 12:
//#line 38 "gramatica.y"
{}
break;
case 13:
//#line 39 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Asignacion en la declaración");}
break;
case 14:
//#line 40 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba un tipo");}
break;
case 15:
//#line 41 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Tipo no valido");}
break;
case 16:
//#line 42 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba un identificador");}
break;
case 17:
//#line 44 "gramatica.y"
{}
break;
case 18:
//#line 45 "gramatica.y"
{}
break;
case 19:
//#line 48 "gramatica.y"
{}
break;
case 20:
//#line 49 "gramatica.y"
{}
break;
case 21:
//#line 52 "gramatica.y"
{}
break;
case 22:
//#line 53 "gramatica.y"
{}
break;
case 23:
//#line 54 "gramatica.y"
{ Logger.getInstance().addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");}
break;
case 24:
//#line 55 "gramatica.y"
{ Logger.getInstance().addError(lex.linea,"Se esperaba \"{\"");}
break;
case 25:
//#line 56 "gramatica.y"
{ Logger.getInstance().addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");}
break;
case 26:
//#line 57 "gramatica.y"
{ Logger.getInstance().addError(lex.linea,"Se esperaba una sentencia");}
break;
case 27:
//#line 58 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba una sentencia");}
break;
case 28:
//#line 61 "gramatica.y"
{}
break;
case 29:
//#line 62 "gramatica.y"
{}
break;
case 30:
//#line 63 "gramatica.y"
{}
break;
case 31:
//#line 64 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 32:
//#line 65 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 33:
//#line 66 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 34:
//#line 67 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 35:
//#line 70 "gramatica.y"
{}
break;
case 36:
//#line 71 "gramatica.y"
{}
break;
case 37:
//#line 72 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba tipo");}
break;
case 38:
//#line 73 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba tipo");}
break;
case 39:
//#line 74 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba identificador");}
break;
case 40:
//#line 77 "gramatica.y"
{ Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de asignación");
			     yyval = val_peek(1);
			   }
break;
case 41:
//#line 80 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
break;
case 42:
//#line 81 "gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de seleccion"); }
break;
case 43:
//#line 82 "gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de salida");}
break;
case 44:
//#line 83 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
break;
case 45:
//#line 84 "gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de llamada"); }
break;
case 46:
//#line 85 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
break;
case 47:
//#line 86 "gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de control"); }
break;
case 48:
//#line 89 "gramatica.y"
{
				 System.out.println("" + val_peek(2).obj);
				 val_peek(2).obj = new Hoja(val_peek(2).sval);
				 yyval = new ParserVal(new Asignacion((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));

			       }
break;
case 49:
//#line 95 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se encontró == en lugar de =");}
break;
case 50:
//#line 96 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Asignacion mal escrita");}
break;
case 51:
//#line 97 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Asignacion mal escrita");}
break;
case 52:
//#line 100 "gramatica.y"
{
    				    yyval = new ParserVal(new Suma((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));
    				  }
break;
case 53:
//#line 103 "gramatica.y"
{
          			    yyval = new ParserVal(new Resta((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));
          			  }
break;
case 54:
//#line 106 "gramatica.y"
{
          	      yyval = val_peek(0);
          	    }
break;
case 55:
//#line 111 "gramatica.y"
{
			       yyval = new ParserVal(new Division((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));
			     }
break;
case 56:
//#line 114 "gramatica.y"
{
        		       yyval = new ParserVal(new Multiplicacion((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));
        		     }
break;
case 57:
//#line 117 "gramatica.y"
{
                   yyval = val_peek(0);
                 }
break;
case 58:
//#line 122 "gramatica.y"
{ yyval = new ParserVal(new Hoja(val_peek(0).sval));}
break;
case 59:
//#line 123 "gramatica.y"
{
       			if (val_peek(0).sval!=null){
				int i = (int) Integer.parseInt(val_peek(0).sval);
				if ( i > (int) Math.pow(2, 15) - 1) {
					Logger.getInstance().addError(lex.linea,"Constante entera fuera de rango");
				} else {
					yyval = new ParserVal(new Hoja(val_peek(0).sval));
				}
			}
		 }
break;
case 60:
//#line 133 "gramatica.y"
{ yyval = new ParserVal(new Hoja(val_peek(0).sval)); }
break;
case 61:
//#line 134 "gramatica.y"
{
       			if(val_peek(0).sval!=null){
				int i = -(int) Integer.parseInt(val_peek(0).sval);
				if (!lex.tablaDeSimbolos.containsKey(String.valueOf(i))) {
					    HashMap<String, Object> aux = new HashMap<String, Object>();
					    aux.put("Tipo", "INT");
					    lex.tablaDeSimbolos.put(String.valueOf(i), aux);
					}
				int aux = (int) lex.tablaDeSimbolos.get(String.valueOf(-i)).get("Contador");
				lex.tablaDeSimbolos.get(String.valueOf(-i)).put("Contador",aux-1);
				yyval = new ParserVal(new Hoja(val_peek(1).sval));
			}
       		     }
break;
case 62:
//#line 147 "gramatica.y"
{
      			if(val_peek(0).sval!=null){
				float f = -(float) Float.parseFloat(val_peek(0).sval);
				if (!lex.tablaDeSimbolos.containsKey(String.valueOf(f))) {
					    HashMap<String, Object> aux = new HashMap<String, Object>();
					    aux.put("Tipo", "FLOAT");
					    lex.tablaDeSimbolos.put(String.valueOf(f), aux);
				}
				int aux = (int) lex.tablaDeSimbolos.get(String.valueOf(-f)).get("Contador");
				lex.tablaDeSimbolos.get(String.valueOf(-f)).put("Contador",aux-1);
				yyval = new ParserVal(new Hoja(val_peek(1).sval));
			}
       		       }
break;
case 63:
//#line 162 "gramatica.y"
{}
break;
case 64:
//#line 163 "gramatica.y"
{}
break;
case 65:
//#line 164 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba ELSE");}
break;
case 66:
//#line 165 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \")\" luego de la condición"); }
break;
case 67:
//#line 166 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba END_IF");}
break;
case 68:
//#line 167 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \")\" luego de la condición"); }
break;
case 69:
//#line 168 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba END_IF");}
break;
case 70:
//#line 169 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba THEN");}
break;
case 71:
//#line 170 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba THEN");}
break;
case 72:
//#line 171 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"No se encontraron sentencias ejecutables");}
break;
case 73:
//#line 172 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 74:
//#line 173 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del ELSE");}
break;
case 75:
//#line 174 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 76:
//#line 175 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 77:
//#line 176 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
break;
case 78:
//#line 177 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
break;
case 79:
//#line 180 "gramatica.y"
{}
break;
case 80:
//#line 181 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
break;
case 81:
//#line 182 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
break;
case 82:
//#line 183 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
break;
case 83:
//#line 187 "gramatica.y"
{}
break;
case 84:
//#line 188 "gramatica.y"
{}
break;
case 85:
//#line 189 "gramatica.y"
{}
break;
case 86:
//#line 190 "gramatica.y"
{}
break;
case 87:
//#line 191 "gramatica.y"
{}
break;
case 88:
//#line 192 "gramatica.y"
{}
break;
case 89:
//#line 195 "gramatica.y"
{}
break;
case 90:
//#line 196 "gramatica.y"
{}
break;
case 91:
//#line 199 "gramatica.y"
{}
break;
case 92:
//#line 200 "gramatica.y"
{}
break;
case 93:
//#line 203 "gramatica.y"
{}
break;
case 94:
//#line 204 "gramatica.y"
{}
break;
case 95:
//#line 207 "gramatica.y"
{}
break;
case 96:
//#line 208 "gramatica.y"
{}
break;
case 97:
//#line 211 "gramatica.y"
{}
break;
case 98:
//#line 212 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba una cadena");}
break;
case 99:
//#line 213 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Sentencia OUT mal escrita");}
break;
case 100:
//#line 216 "gramatica.y"
{}
break;
case 101:
//#line 217 "gramatica.y"
{}
break;
case 102:
//#line 220 "gramatica.y"
{}
break;
case 103:
//#line 221 "gramatica.y"
{}
break;
case 104:
//#line 222 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 105:
//#line 223 "gramatica.y"
{}
break;
case 106:
//#line 224 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 107:
//#line 225 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 108:
//#line 226 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 109:
//#line 229 "gramatica.y"
{
			String id_for = val_peek(11).sval;
			String id_comp = val_peek(7).sval;
			if(!id_for.equals(id_comp)) {
				Logger.getInstance().addError(lex.linea,"La variable de inicialización no es igual a la de condición");
			}
		}
break;
case 110:
//#line 236 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \";\" pero se recibio "+ val_peek(7).sval);}
break;
case 111:
//#line 237 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \";\" pero se recibio "+ val_peek(3).sval);}
break;
case 112:
//#line 238 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaban \";\" en la sentencia FOR");}
break;
case 113:
//#line 239 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba UP o DOWN en la sentencia FOR");}
break;
case 114:
//#line 240 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Falta inicialización en la sentencia FOR");}
break;
case 115:
//#line 241 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Falta condicion en la sentencia FOR");}
break;
case 116:
//#line 242 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Falta incremento en la sentencia FOR");}
break;
case 117:
//#line 243 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \")\" en la sentencia FOR");}
break;
case 118:
//#line 244 "gramatica.y"
{Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del FOR");}
break;
case 119:
//#line 247 "gramatica.y"
{}
break;
case 120:
//#line 248 "gramatica.y"
{}
break;
//#line 1278 "Parser.java"
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
