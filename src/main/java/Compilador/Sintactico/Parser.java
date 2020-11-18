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
import java.util.ArrayList;
import Compilador.Lexico.Tipos;


//#line 27 "Parser.java"




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
   10,    6,    6,    6,    6,    6,    6,    6,   11,   11,
   11,   11,   11,   11,   11,   11,   12,   12,   12,   12,
   12,    4,    4,    4,    4,    4,    4,    4,    4,   13,
   13,   13,   13,    9,    9,    9,   18,   18,   18,   19,
   19,   19,   19,   19,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   20,   20,   20,   20,
   23,   23,   23,   24,   24,   24,   24,   24,   24,   21,
   22,   26,   25,   25,   25,   27,   27,   15,   15,   15,
   15,   15,   15,   15,   15,   16,   16,   28,   28,   28,
   28,   28,   28,   28,   28,   17,   17,   17,   17,   17,
   17,   17,   17,   17,   17,   29,   29,
};
final static short yylen[] = {                            2,
    1,    1,    2,    1,    2,    1,    1,    2,    2,    1,
    1,    2,    4,    1,    2,    2,    1,    1,    1,    3,
    2,   10,    9,    7,    9,    6,    9,    8,    1,    3,
    5,    2,    3,    4,    4,    6,    2,    3,    2,    1,
    3,    2,    1,    1,    2,    1,    2,    1,    1,    3,
    3,    3,    3,    3,    3,    1,    3,    3,    1,    1,
    1,    1,    2,    2,    5,    7,    6,    5,    7,    4,
    6,    4,    5,    7,    7,    7,    3,    2,    2,    1,
    3,    2,    2,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    3,    2,    1,    1,    2,    4,    4,    4,
    5,    4,    5,    3,    2,    4,    3,    1,    3,    2,
    5,    3,    4,    4,    6,   14,   13,   13,   12,   13,
   10,   10,   11,   13,   14,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   17,   18,    0,    0,    0,    2,    0,
    1,    0,    6,    7,    0,   10,    0,   14,    0,    0,
   44,    0,    0,   49,    0,    3,    8,    0,   15,   60,
   61,   62,   87,   88,   86,   89,    0,    0,   84,   85,
    0,    0,   59,    0,    0,    0,  105,    0,    0,   21,
    0,    0,    0,    0,    0,    5,    9,   16,    0,    0,
   42,   45,   47,    0,    0,   63,   64,   82,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   95,    0,   90,
   78,    0,    0,    0,    0,    0,  104,    0,    0,    0,
   53,    0,   20,    0,  107,    0,    0,    0,   40,    0,
    0,    0,    0,   77,    0,    0,    0,   57,   58,    0,
   72,    0,    0,   94,    0,    0,    0,   70,   99,  100,
  102,   98,    0,    0,    0,    0,    0,    0,  106,    0,
   39,    0,    0,    0,   37,    0,    0,    0,    0,   73,
    0,    0,   65,    0,   91,   97,   93,    0,  101,  103,
    0,    0,  112,    0,    0,   41,   38,    0,    0,    0,
    0,    0,    0,   33,    0,    0,    0,    0,   67,   71,
    0,    0,    0,  114,  113,    0,    0,   26,    0,    0,
    0,   34,   35,   76,   75,   74,   69,   66,    0,  126,
  127,    0,    0,    0,    0,    0,    0,   24,    0,    0,
    0,    0,    0,  115,   28,    0,    0,    0,   36,    0,
    0,    0,    0,    0,   23,   27,    0,   25,    0,    0,
    0,    0,    0,   92,  122,  121,   22,    0,    0,    0,
    0,  123,    0,    0,  119,    0,    0,    0,  117,  120,
    0,  124,  118,  125,  116,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,   78,   15,   16,   17,   18,   41,   19,
  102,  103,   20,   21,   22,   23,   24,   42,   43,   44,
   79,  144,   45,   46,  224,  225,  116,   96,  193,
};
final static short yysindex[] = {                       133,
    3,  -36,  -27,    0,    0,   -5, -231,  -30,    0,    0,
    0,  505,    0,    0,  -12,    0, -218,    0,   10,   16,
    0,   19,   27,    0,   15,    0,    0,  -22,    0,    0,
    0,    0,    0,    0,    0,    0,  359, -161,    0,    0,
  -23,   57,    0,  399,   48,  -22,    0,   69, -177,    0,
  -22,  156, -171,  -33,  -59,    0,    0,    0,   45,  137,
    0,    0,    0,   80,   78,    0,    0,    0,  -22,  -22,
  -22,  -22,  -22,   76, -102,  -29,  481,    0, -132,    0,
    0,   80,   99,  103,  112,  126,    0, -117,  -55,   80,
    0,   80,    0,  -25,    0,  128,  -22, -167,    0, -107,
 -113,  138,  220,    0,   57,   57,   80,    0,    0,  -46,
    0,  -71,  392,    0, -127,   46,  444,    0,    0,    0,
    0,    0,  140,  142,  -78,  -22,  -10,  -79,    0,   80,
    0, -203,  136,  505,    0,  -95, -123,  249,  -80,    0,
   76,  -80,    0,  -53,    0,    0,    0,  -52,    0,    0,
  -47,   37,    0,  -63,   14,    0,    0,  -68,   87,  153,
  505,  274, -123,    0,  -45,  -43,  -41, -208,    0,    0,
  365, -155,  -67,    0,    0,   22,   97,    0,   39,  102,
 -123,    0,    0,    0,    0,    0,    0,    0,  -22,    0,
    0,  365,   51,   58,   32,  447,  346,    0,   66,  420,
  -22,  305,  310,    0,    0,  236,  468,  237,    0,  -67,
  109,   77,  444,  444,    0,    0,  250,    0,  111,  350,
 -199,  444,  123,    0,    0,    0,    0,  362,  444,  373,
  160,    0,  385,  444,    0,  444,  335,  444,    0,    0,
  -80,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,   93,    0,    0,
    0,   18,    0,    0,  231,    0,    0,    0,    0,  245,
    0,  269,  299,    0,   73,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    1,    0,    0,  417,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  107,    0,
    0,    0,    0,  121,  430,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  187,    0,    0,    0,    0,    0,    0,    0,  151,
    0,  175,    0,  395,    0,    0,    0,    0,    0,    0,
    0,    0,  397,    0,   25,   49,  194,    0,    0,    0,
    0,    0,    0,    0,  329,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  414,    0,    0,  211,
    0,    0,    0,    0,    0,    0,    0,  416,    0,    0,
  323,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  421,    0,    0,    0,    0,    0,
    0,  423,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  431,    0,    0,    0,  433,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  408,    0,  -39,  532,    0,    0,  617,   28,    5,    0,
    0,  413,    0,    0,    0,    0,    0,  130,  118,    0,
  -48,  -87,  424,  -24,  555,  522,  366,    0, -133,
};
final static int YYTABLESIZE=798;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         27,
   56,   28,   26,   37,   39,  125,   40,   95,   38,   54,
   54,  172,   48,   53,   28,  134,   71,    4,  128,   69,
   77,   70,   38,   39,   54,   40,  113,  161,   29,  148,
   52,   52,   64,  154,   49,  112,   39,   58,   40,  194,
   50,   56,   77,   56,   59,   56,   57,  187,   55,   60,
   82,  188,  156,   25,  168,   90,   92,  176,   53,   56,
   56,   27,   56,   28,  126,   54,  211,   54,  157,   54,
  190,  191,   19,  230,   61,  107,  219,   62,  223,   69,
   93,   70,   29,   54,   54,   63,   54,  231,   81,   55,
  166,   55,   19,   55,   89,  173,    4,    5,   73,  165,
   25,  130,  167,   72,  131,   97,   12,   55,   55,   87,
   55,   66,   67,   88,  190,  191,  192,  222,  104,   69,
   52,   70,   69,   56,   70,   56,  117,  118,   74,    2,
  152,   19,    9,   19,    3,  221,   28,   29,    6,  119,
    4,    5,    4,  120,   76,   98,  189,   54,   99,   54,
   51,   19,  121,  110,    2,  123,  124,  111,  135,    3,
  133,    4,    5,    6,    7,   12,  122,  201,  129,    8,
  147,   55,  160,   55,   50,  110,    2,  100,  136,   52,
  149,    3,  150,    4,    5,    6,    7,  139,  140,  108,
  109,    8,  155,  200,  151,   19,  158,   19,  105,  106,
   38,  244,  190,  191,  177,  212,  169,  170,  174,   51,
   13,  178,   25,  179,  184,   19,  185,   19,  186,  196,
   33,   34,   35,   36,  171,   25,  198,   83,   47,   12,
   11,   12,   68,   50,   81,   30,   31,   32,   94,   33,
   34,   35,   36,   52,   43,   52,  127,   51,   51,   30,
   31,   32,   33,   34,   35,   36,   56,   56,   56,   56,
   56,  153,   56,  137,   56,   56,   56,   56,   46,   13,
   56,   56,   56,   51,   25,   51,   56,   56,   56,   56,
   54,   54,   54,   54,   54,  175,   54,  204,   54,   54,
   54,   54,  163,  195,   54,   54,   54,   50,   48,   50,
   54,   54,   54,   54,   55,   55,   55,   55,   55,   83,
   55,  197,   55,   55,   55,   55,   81,  181,   55,   55,
   55,  209,   68,  202,   55,   55,   55,   55,   19,   19,
  203,   19,   19,   13,   19,   13,   19,   19,   19,   19,
   83,   84,   85,   86,   19,  213,  190,  191,   19,   19,
  214,   19,   19,   11,   19,   11,   19,   19,   19,   19,
  215,  218,   12,   12,   19,   12,   12,   43,   12,   43,
   12,   12,   12,   12,  227,  241,   52,   52,   12,   52,
   52,  220,   52,  228,   52,   52,   52,   52,    1,    2,
  229,   46,   52,   46,    3,  233,    4,    5,    6,    7,
    4,    5,  234,   38,    8,   98,   51,   51,   99,   51,
   51,   91,   51,  236,   51,   51,   51,   51,   39,   56,
   40,   48,   51,   48,   39,  238,   40,   30,   31,   32,
   50,   50,  237,   50,   50,  108,   50,   29,   50,   50,
   50,   50,   83,   83,   83,   68,   50,   68,   83,   81,
   81,   81,   83,   96,  110,   81,   32,   77,   83,   81,
   65,  109,   69,   30,   70,   81,   13,   13,  207,   13,
   13,  111,   13,   31,   13,   13,   13,   13,  210,    0,
  146,    0,   13,    4,    5,    0,   11,   11,   98,   11,
   11,   99,   11,    0,   11,   11,   11,   11,    0,    0,
   43,   43,   11,   43,   43,    0,   43,    0,   43,   43,
   43,   43,    4,    5,   77,  138,   43,   98,    0,    0,
   99,   77,    0,    0,   46,   46,    0,   46,   46,    0,
   46,   14,   46,   46,   46,   46,    0,    4,    5,   80,
   46,  159,   98,   14,    0,   99,    0,    0,    0,  162,
  164,    0,   79,    0,   48,   48,    0,   48,   48,    0,
   48,    0,   48,   48,   48,   48,   77,    0,  180,    0,
   48,  205,    0,    0,  182,  183,    0,    0,   68,   68,
    0,   68,   68,    0,   68,    0,   68,   68,   68,   68,
   74,    2,  216,  199,   68,    0,    3,    0,   80,    0,
    6,   55,    2,  206,  208,  114,   76,    3,  115,    4,
    5,    6,    7,    0,  217,    0,    0,    8,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   80,
   30,   31,   32,    0,   33,   34,   35,   36,    0,    0,
   33,   34,   35,   36,    0,    0,  115,  141,    2,    0,
  142,  143,    0,    3,   74,    2,   75,    6,    0,    0,
    3,    0,    0,   76,    6,   14,    0,  145,    0,    0,
   76,  145,   80,   80,   80,    0,  101,    0,   80,    0,
    0,    0,   80,    0,    0,   79,   79,   79,   80,  190,
  191,   79,   14,   80,    0,   79,  145,    0,    0,   74,
    2,   79,   55,    2,    0,    3,    0,    0,    3,    6,
    4,    5,    6,    7,  132,   76,    0,    0,    8,  101,
    0,    0,    0,   55,    2,    0,    0,   14,   14,    3,
    0,    4,    5,    6,    7,  226,   74,    2,   14,    8,
    0,    0,    3,  232,    0,    0,    6,    0,    0,    0,
  235,    0,   76,  101,  101,  239,    0,  240,  242,  243,
   55,    2,  245,    0,    0,    0,    3,    0,    4,    5,
    6,    7,    0,    0,    0,    0,    8,    0,  101,  101,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  101,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         59,
    0,   61,    0,   40,   60,   61,   62,   41,   45,   40,
   40,   59,   40,   44,   61,  123,   41,    0,   44,   43,
  123,   45,   45,   60,    0,   62,   75,  123,    1,  117,
   61,   61,   28,   44,   40,   75,   60,  256,   62,  173,
  272,   41,  123,   43,   17,   45,   59,  256,    0,   40,
   46,  260,  256,  272,  142,   51,   52,   44,   44,   59,
   60,   59,   62,   61,   89,   41,  200,   43,  272,   45,
  270,  271,    0,  273,   59,   71,  210,   59,  212,   43,
   53,   45,   55,   59,   60,   59,   62,  221,   41,   41,
  139,   43,    0,   45,  272,   59,  264,  265,   42,  139,
  272,   97,  142,   47,  272,   61,    0,   59,   60,   41,
   62,  273,  274,   45,  270,  271,  272,   41,   41,   43,
    0,   45,   43,  123,   45,  125,  259,  260,  256,  257,
  126,   59,    0,   61,  262,   59,   61,  110,  266,   41,
  264,  265,  125,   41,  272,  269,  171,  123,  272,  125,
    0,   59,   41,  256,  257,  273,  274,  260,  272,  262,
  268,  264,  265,  266,  267,   59,   41,  192,   41,  272,
  125,  123,  268,  125,    0,  256,  257,   41,   41,   59,
   41,  262,   41,  264,  265,  266,  267,  259,  260,   72,
   73,  272,  272,  189,  273,  123,   61,  125,   69,   70,
   45,  241,  270,  271,  273,  201,  260,  260,  272,   59,
    0,  125,  272,   61,  260,  123,  260,  125,  260,  123,
  276,  277,  278,  279,  272,  272,  125,   41,  256,  123,
    0,  125,  256,   59,   41,  272,  273,  274,  272,  276,
  277,  278,  279,  123,    0,  125,  272,  278,  278,  272,
  273,  274,  276,  277,  278,  279,  256,  257,  258,  259,
  260,  272,  262,   44,  264,  265,  266,  267,    0,   59,
  270,  271,  272,  123,  272,  125,  276,  277,  278,  279,
  256,  257,  258,  259,  260,  272,  262,  256,  264,  265,
  266,  267,   44,  272,  270,  271,  272,  123,    0,  125,
  276,  277,  278,  279,  256,  257,  258,  259,  260,  123,
  262,  273,  264,  265,  266,  267,  123,   44,  270,  271,
  272,  256,    0,  273,  276,  277,  278,  279,  256,  257,
  273,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  272,  273,  274,  275,  272,   41,  270,  271,  256,  257,
   41,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  125,  125,  256,  257,  272,  259,  260,  123,  262,  125,
  264,  265,  266,  267,  125,   41,  256,  257,  272,  259,
  260,  273,  262,  273,  264,  265,  266,  267,  256,  257,
   41,  123,  272,  125,  262,  273,  264,  265,  266,  267,
  264,  265,   41,   45,  272,  269,  256,  257,  272,  259,
  260,  256,  262,   41,  264,  265,  266,  267,   60,   12,
   62,  123,  272,  125,   60,   41,   62,  272,  273,  274,
  256,  257,  273,  259,  260,   41,  262,   41,  264,  265,
  266,  267,  256,  257,  258,  123,  272,  125,  262,  256,
  257,  258,  266,  125,   41,  262,   41,  123,  272,  266,
   37,   41,   43,   41,   45,  272,  256,  257,  123,  259,
  260,   41,  262,   41,  264,  265,  266,  267,   59,   -1,
  115,   -1,  272,  264,  265,   -1,  256,  257,  269,  259,
  260,  272,  262,   -1,  264,  265,  266,  267,   -1,   -1,
  256,  257,  272,  259,  260,   -1,  262,   -1,  264,  265,
  266,  267,  264,  265,  123,  103,  272,  269,   -1,   -1,
  272,  123,   -1,   -1,  256,  257,   -1,  259,  260,   -1,
  262,    0,  264,  265,  266,  267,   -1,  264,  265,  123,
  272,  134,  269,   12,   -1,  272,   -1,   -1,   -1,  137,
  138,   -1,  123,   -1,  256,  257,   -1,  259,  260,   -1,
  262,   -1,  264,  265,  266,  267,  123,   -1,  161,   -1,
  272,  125,   -1,   -1,  162,  163,   -1,   -1,  256,  257,
   -1,  259,  260,   -1,  262,   -1,  264,  265,  266,  267,
  256,  257,  125,  181,  272,   -1,  262,   -1,   44,   -1,
  266,  256,  257,  196,  197,  125,  272,  262,   77,  264,
  265,  266,  267,   -1,  207,   -1,   -1,  272,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   75,
  272,  273,  274,   -1,  276,  277,  278,  279,   -1,   -1,
  276,  277,  278,  279,   -1,   -1,  115,  256,  257,   -1,
  259,  260,   -1,  262,  256,  257,  258,  266,   -1,   -1,
  262,   -1,   -1,  272,  266,  134,   -1,  113,   -1,   -1,
  272,  117,  256,  257,  258,   -1,   60,   -1,  262,   -1,
   -1,   -1,  266,   -1,   -1,  256,  257,  258,  272,  270,
  271,  262,  161,  139,   -1,  266,  142,   -1,   -1,  256,
  257,  272,  256,  257,   -1,  262,   -1,   -1,  262,  266,
  264,  265,  266,  267,   98,  272,   -1,   -1,  272,  103,
   -1,   -1,   -1,  256,  257,   -1,   -1,  196,  197,  262,
   -1,  264,  265,  266,  267,  214,  256,  257,  207,  272,
   -1,   -1,  262,  222,   -1,   -1,  266,   -1,   -1,   -1,
  229,   -1,  272,  137,  138,  234,   -1,  236,  237,  238,
  256,  257,  241,   -1,   -1,   -1,  262,   -1,  264,  265,
  266,  267,   -1,   -1,   -1,   -1,  272,   -1,  162,  163,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  181,
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
"encabezado_proc : PROC ID",
"dec_procedimiento : encabezado_proc '(' lista_parametros ')' NI '=' CTE_INT '{' conjunto_sentencias '}'",
"dec_procedimiento : encabezado_proc '(' ')' NI '=' CTE_INT '{' conjunto_sentencias '}'",
"dec_procedimiento : encabezado_proc '(' lista_parametros ')' '{' conjunto_sentencias '}'",
"dec_procedimiento : encabezado_proc '(' lista_parametros ')' NI '=' CTE_INT conjunto_sentencias '}'",
"dec_procedimiento : encabezado_proc '(' ')' '{' conjunto_sentencias '}'",
"dec_procedimiento : encabezado_proc '(' lista_parametros ')' NI '=' CTE_INT '{' '}'",
"dec_procedimiento : encabezado_proc '(' ')' NI '=' CTE_INT '{' '}'",
"lista_parametros : parametro",
"lista_parametros : parametro ',' parametro",
"lista_parametros : parametro ',' parametro ',' parametro",
"lista_parametros : parametro parametro",
"lista_parametros : parametro parametro parametro",
"lista_parametros : parametro ',' parametro parametro",
"lista_parametros : parametro parametro ',' parametro",
"lista_parametros : parametro ',' parametro ',' parametro error",
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
"seleccion : IF condicion_if_parentesis THEN bloque_ejecutables_then END_IF",
"seleccion : IF condicion_if_parentesis THEN bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF",
"seleccion : IF condicion_if_parentesis THEN bloque_ejecutables_then bloque_ejecutables_else END_IF",
"seleccion : IF condicion_if_parentesis THEN bloque_ejecutables_then error",
"seleccion : IF condicion_if_parentesis THEN bloque_ejecutables_then ELSE bloque_ejecutables_else error",
"seleccion : IF condicion_if_parentesis bloque_ejecutables_then END_IF",
"seleccion : IF condicion_if_parentesis bloque_ejecutables_then ELSE bloque_ejecutables_else END_IF",
"seleccion : IF condicion_if_parentesis THEN END_IF",
"seleccion : IF condicion_if_parentesis THEN declarativa END_IF",
"seleccion : IF condicion_if_parentesis THEN bloque_ejecutables_then ELSE declarativa END_IF",
"seleccion : IF condicion_if_parentesis THEN declarativa ELSE bloque_ejecutables_then END_IF",
"seleccion : IF condicion_if_parentesis THEN declarativa ELSE declarativa END_IF",
"condicion_if_parentesis : '(' condicion_if ')'",
"condicion_if_parentesis : condicion_if ')'",
"condicion_if_parentesis : '(' condicion_if",
"condicion_if_parentesis : condicion_if",
"condicion_if : expresion comparador expresion",
"condicion_if : expresion error",
"condicion_if : comparador expresion",
"comparador : '<'",
"comparador : '>'",
"comparador : COMP",
"comparador : MAYOR_IGUAL",
"comparador : MENOR_IGUAL",
"comparador : DISTINTO",
"bloque_ejecutables_then : bloque_ejecutables_llaves",
"bloque_ejecutables_else : bloque_ejecutables_llaves",
"bloque_ejecutables_for : bloque_ejecutables_llaves",
"bloque_ejecutables_llaves : '{' bloque_ejecutables '}'",
"bloque_ejecutables_llaves : '{' '}'",
"bloque_ejecutables_llaves : ejecutable",
"bloque_ejecutables : ejecutable",
"bloque_ejecutables : ejecutable bloque_ejecutables",
"salida : OUT '(' CADENA ')'",
"salida : OUT '(' ID ')'",
"salida : OUT '(' CTE_INT ')'",
"salida : OUT '(' '-' CTE_INT ')'",
"salida : OUT '(' CTE_FLOAT ')'",
"salida : OUT '(' '-' CTE_FLOAT ')'",
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
"parametros : ID ',' ID ',' ID error",
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

//#line 621 "gramatica.y"

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


//#line 618 "Parser.java"
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
//#line 19 "gramatica.y"
{raiz = (Nodo)val_peek(0).obj;}
break;
case 2:
//#line 20 "gramatica.y"
{logger.addError(lex.linea,"Sin sentencias");}
break;
case 3:
//#line 21 "gramatica.y"
{logger.addError(lex.linea,"Sin sentencias validas");}
break;
case 4:
//#line 24 "gramatica.y"
{
					try{
						yyval = new ParserVal(new Bloque((Nodo)val_peek(0).obj,null));
					}
					catch(Exception e){
						error=true;
					}
				}
break;
case 5:
//#line 32 "gramatica.y"
{ 	if(val_peek(1).obj!=null && val_peek(0).obj!=null)
                    						yyval = new ParserVal(new Bloque((Nodo)val_peek(1).obj,(Nodo)val_peek(0).obj));
                    					else{
                    						if(val_peek(0).obj==null)
                    							yyval = new ParserVal(new Bloque((Nodo)val_peek(1).obj,null));
                    						else
                    							yyval = new ParserVal(new Bloque((Nodo)val_peek(0).obj,null));
                    					}
                    				}
break;
case 6:
//#line 44 "gramatica.y"
{yyval=val_peek(0);}
break;
case 7:
//#line 45 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 8:
//#line 46 "gramatica.y"
{logger.addError(lex.linea,"Sentencia mal escrita");}
break;
case 9:
//#line 49 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia declarativa de variable");
				yyval=new ParserVal();}
break;
case 10:
//#line 51 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia declarativa de procedimiento");
            			yyval=val_peek(0);}
break;
case 11:
//#line 53 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\"");}
break;
case 12:
//#line 56 "gramatica.y"
{
		for (String id : (ArrayList<String>)(val_peek(0).obj)){
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
                                aux.put("Tipo",val_peek(1).obj);
                                lex.tablaDeSimbolos.put(id+ambito,aux);
                        }

                  }
	     }
break;
case 13:
//#line 77 "gramatica.y"
{	logger.addError(lex.linea,"Asignacion en la declaración");

	     					  }
break;
case 14:
//#line 80 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba un tipo");}
break;
case 15:
//#line 81 "gramatica.y"
{logger.addError(lex.linea,"Tipo no valido");}
break;
case 16:
//#line 82 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba un identificador");}
break;
case 17:
//#line 84 "gramatica.y"
{
		yyval=new ParserVal(Tipos.INTEGER);
		}
break;
case 18:
//#line 87 "gramatica.y"
{
     		yyval=new ParserVal(Tipos.FLOAT);
     		}
break;
case 19:
//#line 92 "gramatica.y"
{
			ArrayList<String> aux=new ArrayList<String>();
			aux.add(val_peek(0).sval);
			yyval=new ParserVal(aux);
		}
break;
case 20:
//#line 97 "gramatica.y"
{
                	ArrayList<String> aux = (ArrayList<String>)(val_peek(0).obj);
                	aux.add(val_peek(2).sval);
                	yyval=val_peek(0);
                }
break;
case 21:
//#line 104 "gramatica.y"
{if(lex.tablaDeSimbolos.containsKey(val_peek(0).sval+ambito)){
                        	HashMap<String, Object> var = lex.tablaDeSimbolos.get(val_peek(0).sval+ambito);
                        	String uso = (String)var.get("Uso");
                        	if(uso.equals("procedimiento")){
                        		logger.addError(lex.linea,"Procedimiento \""+ val_peek(0).sval + "\" redeclarado");
                        	}
                        	else
                                	logger.addError(lex.linea,"Identificador \""+ val_peek(0).sval + "\" en uso.");

                        }
			else{
				HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(val_peek(0).sval);
				aux.put("Uso","procedimiento");
				lex.tablaDeSimbolos.put(val_peek(0).sval+ambito,aux);
				yyval=new ParserVal(val_peek(0).sval+ambito);
				ambito+="@"+val_peek(0).sval;
			}
			}
break;
case 22:
//#line 123 "gramatica.y"
{
		    	ambito=ambito.substring(0,ambito.lastIndexOf("@"));
		    	HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(val_peek(9).sval);
		    	aux.put("NI",Integer.parseInt(val_peek(3).sval));
		    	aux.put("Parametros", val_peek(7).obj);
		    	lex.tablaDeSimbolos.put(val_peek(9).sval,aux);
			DecProc proc = new DecProc((Nodo)val_peek(1).obj,null,val_peek(9).sval);
			yyval = new ParserVal(proc);


		   }
break;
case 23:
//#line 134 "gramatica.y"
{
                  										ambito=ambito.substring(0,ambito.lastIndexOf("@"));
												HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(val_peek(8).sval);
												aux.put("NI",Integer.parseInt(val_peek(3).sval));
												lex.tablaDeSimbolos.put(val_peek(8).sval,aux);
												DecProc proc = new DecProc((Nodo)val_peek(1).obj,null,val_peek(8).sval);
                                                                                                yyval = new ParserVal(proc);
                  								       }
break;
case 24:
//#line 142 "gramatica.y"
{ logger.addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");
                  									   ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  									 }
break;
case 25:
//#line 145 "gramatica.y"
{ logger.addError(lex.linea,"Se esperaba \"{\"");
                  										      ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  										    }
break;
case 26:
//#line 148 "gramatica.y"
{ logger.addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");
                  							 ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  							}
break;
case 27:
//#line 151 "gramatica.y"
{ logger.addError(lex.linea,"Se esperaba una sentencia");
                  								      ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  								    }
break;
case 28:
//#line 154 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba una sentencia");
		  						    ambito=ambito.substring(0,ambito.lastIndexOf("@"));
		  						   }
break;
case 29:
//#line 159 "gramatica.y"
{
				ArrayList<Parametro> parametros = new ArrayList<>();
				parametros.add((Parametro)val_peek(0).obj);
				yyval = new ParserVal(parametros);
			    }
break;
case 30:
//#line 164 "gramatica.y"
{
                 				ArrayList<Parametro> parametros = new ArrayList<>();
						parametros.add((Parametro)val_peek(2).obj);
						parametros.add((Parametro)val_peek(0).obj);
						yyval = new ParserVal(parametros);
                			     }
break;
case 31:
//#line 170 "gramatica.y"
{
                 						ArrayList<Parametro> parametros = new ArrayList<>();
                 						parametros.add((Parametro)val_peek(4).obj);
                 						parametros.add((Parametro)val_peek(2).obj);
                 						parametros.add((Parametro)val_peek(0).obj);
                 						yyval = new ParserVal(parametros);
                  					    }
break;
case 32:
//#line 177 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 33:
//#line 178 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 34:
//#line 179 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 35:
//#line 180 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 36:
//#line 181 "gramatica.y"
{logger.addError(lex.linea,"Se esperaban como maximo 3 parametros");}
break;
case 37:
//#line 184 "gramatica.y"
{
		    HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(val_peek(0).sval);
		    aux.put("Uso","variable");
		    aux.put("Tipo",(Tipos)val_peek(1).obj);
                    lex.tablaDeSimbolos.put(val_peek(0).sval+ambito,aux);
                    yyval = new ParserVal(new Parametro(val_peek(0).sval+ambito,(Tipos)val_peek(1).obj,"COPIA"));
	   }
break;
case 38:
//#line 191 "gramatica.y"
{
	   			yyval = new ParserVal(new Parametro(val_peek(0).sval+ambito,(Tipos)val_peek(1).obj,"VAR"));
	   		 }
break;
case 39:
//#line 194 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba tipo");}
break;
case 40:
//#line 195 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba tipo");}
break;
case 41:
//#line 196 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba identificador");}
break;
case 42:
//#line 199 "gramatica.y"
{ logger.addEvent(lex.linea,"Se encontró una sentencia de asignación");
			     yyval = val_peek(1);
			   }
break;
case 43:
//#line 202 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\"");}
break;
case 44:
//#line 203 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia de seleccion"); }
break;
case 45:
//#line 204 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia de salida");}
break;
case 46:
//#line 205 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\"");}
break;
case 47:
//#line 206 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia de llamada"); }
break;
case 48:
//#line 207 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\"");}
break;
case 49:
//#line 208 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia de control"); }
break;
case 50:
//#line 211 "gramatica.y"
{ String var =getIdentificador(val_peek(2).sval);
				if(var==null){
                                	logger.addError(lex.linea,"Variable \""+ val_peek(2).sval+ "\" no declarada" );
                                }
				else{
					val_peek(2).obj = new Hoja(var);
					Asignacion asignacion = new Asignacion((ConTipo)val_peek(2).obj,(ConTipo)val_peek(0).obj);
					if(asignacion.getTipo()==null)
                                        	logger.addError(lex.linea,"Incompatibilidad de tipos en la asignacion");
                                        yyval = new ParserVal(asignacion);
				 }

			       }
break;
case 51:
//#line 224 "gramatica.y"
{logger.addError(lex.linea,"Se encontró == en lugar de =");}
break;
case 52:
//#line 225 "gramatica.y"
{logger.addError(lex.linea,"Asignacion mal escrita");}
break;
case 53:
//#line 226 "gramatica.y"
{logger.addError(lex.linea,"Asignacion mal escrita");}
break;
case 54:
//#line 229 "gramatica.y"
{
					Suma suma = new Suma((ConTipo)val_peek(2).obj,(ConTipo)val_peek(0).obj);
					suma.updateTipo();
					if(suma.getTipo()==null)
						logger.addError(lex.linea,"Incompatibilidad de tipos en la suma");
    				   	yyval = new ParserVal(suma);

    				  }
break;
case 55:
//#line 237 "gramatica.y"
{
          				Resta resta = new Resta((ConTipo)val_peek(2).obj,(ConTipo)val_peek(0).obj);
          				resta.updateTipo();
          				if(resta.getTipo()==null)
                                        	logger.addError(lex.linea,"Incompatibilidad de tipos en la resta");
          			   	yyval = new ParserVal(resta);
          			  }
break;
case 56:
//#line 244 "gramatica.y"
{
          	      yyval = val_peek(0);
          	    }
break;
case 57:
//#line 249 "gramatica.y"
{
				Division division = new Division((ConTipo)val_peek(2).obj,(ConTipo)val_peek(0).obj);
				division.updateTipo();
				if(division.getTipo()==null)
                                        logger.addError(lex.linea,"Incompatibilidad de tipos en la division");
                                yyval = new ParserVal(division);
			     }
break;
case 58:
//#line 256 "gramatica.y"
{
        			Multiplicacion multiplicacion = new Multiplicacion((ConTipo)val_peek(2).obj,(ConTipo)val_peek(0).obj);
        			multiplicacion.updateTipo();
        		      	if(multiplicacion.getTipo()==null)
                                       logger.addError(lex.linea,"Incompatibilidad de tipos en la multiplicacion");
                                yyval = new ParserVal(multiplicacion);
        		     }
break;
case 59:
//#line 263 "gramatica.y"
{
                   yyval = val_peek(0);
                 }
break;
case 60:
//#line 268 "gramatica.y"
{	String var = getIdentificador(val_peek(0).sval);
		if(var==null){
			logger.addError(lex.linea,"Variable \""+ val_peek(0).sval+ "\" no declarada" );
		}
		else
			yyval = new ParserVal(new Hoja(var));
	}
break;
case 61:
//#line 275 "gramatica.y"
{

       			if (val_peek(0).sval!=null){
				int i = (int) Integer.parseInt(val_peek(0).sval);
				if ( i > (int) Math.pow(2, 15) - 1) {
					logger.addError(lex.linea,"Constante entera fuera de rango");
				} else {
					yyval = new ParserVal(new Hoja(val_peek(0).sval));
				}
			}
		 }
break;
case 62:
//#line 286 "gramatica.y"
{ yyval = new ParserVal(new Hoja(val_peek(0).sval)); }
break;
case 63:
//#line 287 "gramatica.y"
{
       			if(val_peek(0).sval!=null){
				int i = -(int) Integer.parseInt(val_peek(0).sval);
				if (!lex.tablaDeSimbolos.containsKey(String.valueOf(i))) {
					    HashMap<String, Object> aux = new HashMap<String, Object>();
					    aux.put("Tipo", Tipos.INTEGER);
					    lex.tablaDeSimbolos.put(String.valueOf(i), aux);
					}
				int aux = (int) lex.tablaDeSimbolos.get(String.valueOf(-i)).get("Contador");
				lex.tablaDeSimbolos.get(String.valueOf(-i)).put("Contador",aux-1);
				yyval = new ParserVal(new Hoja(String.valueOf(i)));
			}
       		     }
break;
case 64:
//#line 300 "gramatica.y"
{

      			if(val_peek(0).sval!=null){

				float f = -(float) Float.parseFloat(val_peek(0).sval);
				if (!lex.tablaDeSimbolos.containsKey(String.valueOf(f))) {
					    HashMap<String, Object> aux = new HashMap<String, Object>();
					    aux.put("Tipo", Tipos.FLOAT);
					    lex.tablaDeSimbolos.put(String.valueOf(f), aux);
				}
				int aux = (int) lex.tablaDeSimbolos.get(String.valueOf(-f)).get("Contador");
				lex.tablaDeSimbolos.get(String.valueOf(-f)).put("Contador",aux-1);
				yyval = new ParserVal(new Hoja(String.valueOf(f)));
			}
       		       }
break;
case 65:
//#line 317 "gramatica.y"
{
									  CuerpoIf aux = new CuerpoIf((Nodo)val_peek(1).obj,null);
									  yyval = new ParserVal(new If((Nodo)val_peek(3).obj, aux));
 									}
break;
case 66:
//#line 321 "gramatica.y"
{
          											       CuerpoIf aux = new CuerpoIf((Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);
												       yyval = new ParserVal(new If((Nodo)val_peek(5).obj, aux));
          											     }
break;
case 67:
//#line 325 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba ELSE");}
break;
case 68:
//#line 326 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba END_IF");}
break;
case 69:
//#line 327 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba END_IF");}
break;
case 70:
//#line 328 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba THEN");}
break;
case 71:
//#line 329 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba THEN");}
break;
case 72:
//#line 330 "gramatica.y"
{logger.addError(lex.linea,"No se encontraron sentencias ejecutables");}
break;
case 73:
//#line 331 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 74:
//#line 332 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del ELSE");}
break;
case 75:
//#line 333 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 76:
//#line 334 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 77:
//#line 337 "gramatica.y"
{ yyval=val_peek(1); }
break;
case 78:
//#line 338 "gramatica.y"
{ logger.addError(lex.linea, "Falta parentesis de abertura en la condicion"); }
break;
case 79:
//#line 339 "gramatica.y"
{ logger.addError(lex.linea, "Falta parentesis de cierre en la condicion"); }
break;
case 80:
//#line 340 "gramatica.y"
{ logger.addError(lex.linea, "Faltan ambos parentesis en la condicion"); }
break;
case 81:
//#line 343 "gramatica.y"
{
					      Operador aux = (Operador)val_peek(1).obj;
					      aux.izquierdo = (Nodo)val_peek(2).obj;
					      aux.derecho = (Nodo)val_peek(0).obj;
					      aux.updateTipo();
					      if(aux.getTipo()==null)
					      	logger.addError(lex.linea,"Incompatibilidad de tipos en la comparacion");
					      yyval = val_peek(1);
					    }
break;
case 82:
//#line 352 "gramatica.y"
{logger.addError(lex.linea,"Condicion mal escrita");}
break;
case 83:
//#line 353 "gramatica.y"
{}
break;
case 84:
//#line 356 "gramatica.y"
{ yyval = new ParserVal(new Menor(null,null));}
break;
case 85:
//#line 357 "gramatica.y"
{ yyval = new ParserVal(new Mayor(null,null));}
break;
case 86:
//#line 358 "gramatica.y"
{ yyval = new ParserVal(new Igual(null,null));}
break;
case 87:
//#line 359 "gramatica.y"
{ yyval = new ParserVal(new MayorIgual(null,null));}
break;
case 88:
//#line 360 "gramatica.y"
{ yyval = new ParserVal(new MenorIgual(null,null));}
break;
case 89:
//#line 361 "gramatica.y"
{ yyval = new ParserVal(new Distinto(null,null));}
break;
case 90:
//#line 364 "gramatica.y"
{
		       				    yyval = new ParserVal(new Then((Nodo)val_peek(0).obj));
		       				}
break;
case 91:
//#line 369 "gramatica.y"
{
		       				    yyval = new ParserVal(new Else((Nodo)val_peek(0).obj));
		       				  }
break;
case 92:
//#line 374 "gramatica.y"
{
		       				    yyval = new ParserVal(new Bloque((Nodo)val_peek(0).obj,null));
		       				  }
break;
case 93:
//#line 379 "gramatica.y"
{ yyval = new ParserVal(val_peek(1).obj);}
break;
case 94:
//#line 380 "gramatica.y"
{}
break;
case 95:
//#line 382 "gramatica.y"
{}
break;
case 96:
//#line 386 "gramatica.y"
{
					try{
						yyval = new ParserVal(new Bloque((Nodo)val_peek(0).obj,null));
					    }
					    catch(Exception e){
						error=true;
					    }
        			}
break;
case 97:
//#line 394 "gramatica.y"
{
                   					if(val_peek(1).obj!=null && val_peek(0).obj!=null)
                                                            yyval = new ParserVal(new Bloque((Nodo)val_peek(1).obj,(Nodo)val_peek(0).obj));
                                                        else{
                                                            if(val_peek(0).obj==null)
                                                                yyval = new ParserVal(new Bloque((Nodo)val_peek(1).obj,null));
                                                            else
                                                                yyval = new ParserVal(new Bloque((Nodo)val_peek(0).obj,null));
							}
                   				    }
break;
case 98:
//#line 406 "gramatica.y"
{}
break;
case 99:
//#line 407 "gramatica.y"
{}
break;
case 100:
//#line 408 "gramatica.y"
{}
break;
case 101:
//#line 409 "gramatica.y"
{}
break;
case 102:
//#line 410 "gramatica.y"
{}
break;
case 103:
//#line 411 "gramatica.y"
{}
break;
case 104:
//#line 412 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba una cadena");}
break;
case 105:
//#line 413 "gramatica.y"
{logger.addError(lex.linea,"Sentencia OUT mal escrita");}
break;
case 106:
//#line 416 "gramatica.y"
{
				String proc = getIdentificador(val_peek(3).sval);
				if(proc==null){
					logger.addError(lex.linea,"Procedimiento \""+ val_peek(3).sval+ "\" no declarado" );
				}
				else{
					String a;
					HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(proc);;
					if(aux.get("Uso").equals("procedimiento")){
						int ni = (Integer) aux.get("NI");
						if ( ni <= 0 ) {
							logger.addError(lex.linea,"Se agotaron los llamados para el procedimiento \""+ val_peek(3).sval+ "\"" );
						} else {
							aux.put("NI",(Integer)ni-1);
						}
						if(aux.containsKey("Parametros")) {
							/*estaria todo ok*/
							ArrayList<Parametro> parametros_func = (ArrayList) aux.get("Parametros");
							ArrayList<String> parametros_llamada = (ArrayList) val_peek(1).obj;
							int i;
							for ( i = 0; i < parametros_func.size() && i < parametros_llamada.size(); i++) {
								String parametro_func = getIdentificador(parametros_llamada.get(i));
								if (parametro_func != null) {
									/*checkeo si el tipo es igual*/
									Tipos tipo_func = (Tipos) lex.tablaDeSimbolos.get(parametro_func).get("Tipo");
									if(tipo_func != parametros_func.get(i).tipo){
										logger.addError(lex.linea,"Parametro \"" + parametros_llamada.get(i) + "\" de tipo \""
                                                                                                                        + tipo_func + "\" no coincide con el parametro formal de tipo \"" + parametros_func.get(i).tipo +  "\" en el llamado a \"" + val_peek(3).sval + "\"" );
									}
								} else {
									logger.addError(lex.linea,"Parametro en el procedimiento \"" + parametros_llamada.get(i) + "\" fuera de alcance" );
								}
							}
							if (i >= parametros_func.size() ^ i >= parametros_llamada.size()) {
								logger.addError(lex.linea, "Llamada a procedimiento con cantidad de parametros erronea" );
							}
						} else {
							logger.addError(lex.linea,"Invocacion invalida, \"" + val_peek(3).sval + "\" no lleva parametros" );
						}
					}
					else {
						logger.addError(lex.linea,"Invocacion invalida, \"" + val_peek(3).sval + "\" no es un procedimiento" );
					}
					lex.tablaDeSimbolos.put(proc,aux);
				}
	}
break;
case 107:
//#line 462 "gramatica.y"
{
			String proc = getIdentificador(val_peek(2).sval);
			if(proc==null){
				logger.addError(lex.linea,"Procedimiento \""+ val_peek(2).sval+ "\" no declarado" );
			}
			else{
				String a;
				HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(proc);
				if(aux.get("Uso").equals("procedimiento")){
					int ni = (Integer) aux.get("NI");
					if ( ni <= 0 ) {
						logger.addError(lex.linea,"Se agotaron los llamados para el procedimiento \""+ val_peek(2).sval+ "\"" );
					} else {
						aux.put("NI",(Integer)ni-1);
					}
					if(!aux.containsKey("Parametros")) {
						/*estaria todo ok*/

					} else {
						logger.addError(lex.linea,"Invocacion invalida, \"" + val_peek(2).sval + "\" lleva parametros" );
					}
				}
				else {
					logger.addError(lex.linea,"Invocacion invalida, \"" + val_peek(2).sval + "\" no es un procedimiento" );
				}
				lex.tablaDeSimbolos.put(proc,aux);
			}
	}
break;
case 108:
//#line 492 "gramatica.y"
{
		ArrayList<String> ids=new ArrayList<String>();
		String var = getIdentificador(val_peek(0).sval);
		if(var==null){
			logger.addError(lex.linea,"Variable \""+ val_peek(0).sval + "\" no declarada" );
			yyval = new ParserVal(new ArrayList());
		}
		else{
			ids.add(val_peek(0).sval);
			yyval = new ParserVal(ids);
		}
		}
break;
case 109:
//#line 504 "gramatica.y"
{
           		ArrayList<String> ids=new ArrayList<String>();
           		ids.add(val_peek(2).sval);
           		ids.add(val_peek(0).sval);
           		boolean e = false;
           		for (int i=0; i<ids.size();i++){
           			String var = getIdentificador(ids.get(i));
           			if(var==null){
					logger.addError(lex.linea,"Variable \"" + ids.get(i) + "\" no declarada" );
					e = true;
					break;/*ver si se quiere que solo diga el primero que este mal o si dice todo*/
				}
           		}
           		if(e != true) {
           			yyval = new ParserVal(ids);
           		} else {
           			yyval = new ParserVal(new ArrayList());
           		}


           }
break;
case 110:
//#line 525 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 111:
//#line 526 "gramatica.y"
{
				ArrayList<String> ids=new ArrayList<String>();
				ids.add(val_peek(4).sval);
				ids.add(val_peek(2).sval);
				ids.add(val_peek(0).sval);
				boolean e = false;
				for (int i=0; i<ids.size();i++){
					String var = getIdentificador(ids.get(i));
					if(var==null){
						logger.addError(lex.linea,"Variable \"" + ids.get(i) + "\" no declarada" );
						e = true;
						break;/*ver si se quiere que solo diga el primero que este mal o si dice todo*/
					}
				}
				if(e != true) {
					yyval = new ParserVal(ids);
				} else {
					yyval = new ParserVal(new ArrayList());
				}
           }
break;
case 112:
//#line 546 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 113:
//#line 547 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 114:
//#line 548 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 115:
//#line 549 "gramatica.y"
{logger.addError(lex.linea,"Se esperaban como maximo 3 parametros");
           				ArrayList<String> aux = new ArrayList<>();
           				aux.add(val_peek(5).sval);
           				aux.add(val_peek(3).sval);
           				aux.add(val_peek(1).sval);
           				yyval = new ParserVal(aux);
           }
break;
case 116:
//#line 558 "gramatica.y"
{
			String id_for = val_peek(11).sval;
			String id_comp = val_peek(7).sval;
			String var = getIdentificador(id_for);
			String var_comp = getIdentificador(id_comp);
			if(var==null){
				logger.addError(lex.linea,"Variable \""+ id_for+ "\" no declarada" );
			}
			else{
                        	if(!id_for.equals(id_comp)) {
					logger.addError(lex.linea,"La variable de inicialización no es igual a la de condición");
				}
				/*Creando la parte de la inicializacion de codigo*/
				Asignacion inicializacion = new Asignacion(new Hoja(var),new Hoja(val_peek(9).sval));

				/*Creando la parte del incremento*/
				Operador incremento = (Operador)val_peek(3).obj;
				incremento.izquierdo = new Hoja(var);
				incremento.derecho = new Hoja(val_peek(2).sval);
				incremento.updateTipo();
				Asignacion asig = new Asignacion(new Hoja(var),incremento);

				/*Creando la parte de la condicion*/
				Comparador comp = (Comparador) val_peek(6).obj;
				comp.izquierdo = new Hoja(var_comp);
				System.out.println(val_peek(5).sval);
				System.out.println(val_peek(5).obj);
				System.out.println(val_peek(5).ival);
				System.out.println(val_peek(5).dval);
				comp.derecho = (Nodo) val_peek(5).obj;
				comp.updateTipo();
                                if(comp.getTipo()==null)
                                	logger.addError(lex.linea,"Incompatibilidad de tipos en la comparacion");


				/*Agregandolo*/
				CuerpoFor cuerpoFor = new CuerpoFor((Nodo)val_peek(0).obj,asig);
				For forsito = new For(comp,cuerpoFor);
				Bloque bloque = new Bloque(inicializacion,forsito);
				yyval = new ParserVal(bloque);
			}

		}
break;
case 117:
//#line 601 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\" pero se recibio "+ val_peek(7).sval);}
break;
case 118:
//#line 602 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\" pero se recibio "+ val_peek(3).sval);}
break;
case 119:
//#line 603 "gramatica.y"
{logger.addError(lex.linea,"Se esperaban \";\" en la sentencia FOR");}
break;
case 120:
//#line 604 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba UP o DOWN en la sentencia FOR");}
break;
case 121:
//#line 605 "gramatica.y"
{logger.addError(lex.linea,"Falta inicialización en la sentencia FOR");}
break;
case 122:
//#line 606 "gramatica.y"
{logger.addError(lex.linea,"Falta condicion en la sentencia FOR");}
break;
case 123:
//#line 607 "gramatica.y"
{logger.addError(lex.linea,"Falta incremento en la sentencia FOR");}
break;
case 124:
//#line 608 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \")\" en la sentencia FOR");}
break;
case 125:
//#line 609 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del FOR");}
break;
case 126:
//#line 612 "gramatica.y"
{
		yyval = new ParserVal(new Suma(null,null));
	       }
break;
case 127:
//#line 615 "gramatica.y"
{
          	   yyval = new ParserVal(new Resta(null,null));
	 	 }
break;
//#line 1688 "Parser.java"
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
