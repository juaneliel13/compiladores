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
   10,    6,   12,   12,   12,   11,   11,   11,   11,   13,
   13,   13,   13,   13,   13,   13,   13,   14,   14,   14,
   14,   14,   14,    4,    4,    4,    4,    4,    4,    4,
    4,   15,   15,   15,   15,    9,    9,    9,   20,   20,
   20,   21,   21,   21,   21,   21,   16,   16,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   22,   22,
   22,   22,   25,   25,   25,   26,   26,   26,   26,   26,
   26,   23,   24,   28,   27,   27,   27,   29,   29,   17,
   17,   17,   17,   17,   17,   17,   17,   18,   18,   30,
   30,   30,   30,   30,   30,   30,   30,   19,   19,   19,
   19,   19,   19,   19,   19,   19,   19,   31,   31,
};
final static short yylen[] = {                            2,
    1,    1,    2,    1,    2,    1,    1,    2,    2,    1,
    1,    2,    4,    1,    2,    2,    1,    1,    1,    3,
    2,    3,    3,    2,    1,    6,    5,    3,    2,    1,
    3,    5,    2,    3,    4,    4,    6,    2,    3,    2,
    1,    3,    3,    2,    1,    1,    2,    1,    2,    1,
    1,    3,    3,    3,    3,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    2,    2,    5,    7,    6,    5,
    7,    4,    6,    4,    5,    7,    7,    7,    3,    2,
    2,    1,    3,    2,    2,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    3,    2,    1,    1,    2,    4,
    4,    4,    5,    4,    5,    3,    2,    4,    3,    1,
    3,    2,    5,    3,    4,    4,    6,   14,   13,   13,
   12,   13,   10,   10,   11,   13,   14,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   17,   18,    0,    0,    0,    2,    0,
    1,    0,    6,    7,    0,   10,    0,   14,    0,    0,
   46,    0,    0,   51,    0,    3,    8,    0,   15,   62,
   63,   64,   89,   90,   88,   91,    0,    0,   86,   87,
    0,    0,   61,    0,    0,    0,  107,    0,    0,   21,
    0,    0,    0,    0,    0,    5,    9,   16,    0,    0,
    0,   44,   47,   49,    0,    0,   65,   66,   84,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   97,    0,
   92,   80,    0,    0,    0,    0,    0,  106,    0,    0,
    0,   55,    0,   20,    0,  109,    0,    0,    0,   41,
    0,    0,    0,    0,    0,   25,   22,   79,    0,    0,
    0,   59,   60,    0,   74,    0,    0,   96,    0,    0,
    0,   72,  101,  102,  104,  100,    0,    0,    0,    0,
    0,    0,  108,    0,   40,    0,    0,    0,   38,    0,
    0,    0,   24,    0,    0,   75,    0,    0,   67,    0,
   93,   99,   95,    0,  103,  105,    0,    0,  114,    0,
    0,   42,   39,    0,   43,    0,    0,    0,   34,   23,
    0,    0,    0,    0,   69,   73,    0,    0,    0,  116,
  115,    0,   27,    0,    0,   35,   36,   78,   77,   76,
   71,   68,    0,  128,  129,    0,    0,    0,    0,   26,
    0,    0,    0,    0,    0,  117,   37,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   94,  124,  123,
    0,    0,    0,    0,  125,    0,    0,  121,    0,    0,
    0,  119,  122,    0,  126,  120,  127,  118,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,   79,   15,   16,   17,   18,   41,   19,
   61,  107,  103,  104,   20,   21,   22,   23,   24,   42,
   43,   44,   80,  150,   45,   46,  218,  219,  120,   97,
  197,
};
final static short yysindex[] = {                       133,
    3,  -36,  -27,    0,    0,  -11, -255,  -30,    0,    0,
    0,  508,    0,    0,  -19,    0, -220,    0,   34,   39,
    0,   56,   60,    0,   79,    0,    0,  -22,    0,    0,
    0,    0,    0,    0,    0,    0,  359, -131,    0,    0,
  -23,   53,    0,  -69,   84,  -22,    0,   69, -145,    0,
  -22,  156, -142,  -33,  -59,    0,    0,    0,   74,  137,
  358,    0,    0,    0,   22,  103,    0,    0,    0,  -22,
  -22,  -22,  -22,  -22,   99,  346,  -29,  -95,    0, -113,
    0,    0,   22,  122,  124,  129,  138,    0, -117,  -55,
   22,    0,   22,    0,  -25,    0,  145,  -22, -186,    0,
  -73, -166,  158,  220,  468,    0,    0,    0,   53,   53,
   22,    0,    0,  -46,    0, -101,  426,    0,  -72,   81,
  451,    0,    0,    0,    0,    0,  166,  167,  -64,  -22,
  -10,  -60,    0,   22,    0, -203,  154,  -53,    0,  -51,
 -189,  249,    0,   95,  383,    0,   99,  383,    0,   52,
    0,    0,    0,   62,    0,    0,  -47,   -2,    0,  -45,
   14,    0,    0,   15,    0,  233,  274, -189,    0,    0,
   64,   71,   86, -144,    0,    0,  365, -133, -102,    0,
    0,   89,    0,   78, -189,    0,    0,    0,    0,    0,
    0,    0,  -22,    0,    0,  365,  102,  109,  106,    0,
  128,  420,  -22,  350,  355,    0,    0, -102,  130,   77,
  451,  451,  141,  379, -182,  451,  153,    0,    0,    0,
  392,  451,  395,  165,    0,  413,  451,    0,  451,  335,
  451,    0,    0,  383,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,   93,    0,    0,
    0,   16,    0,    0,  231,    0,    0,    0,    0,  245,
    0,  269,  299,    0,   73,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    1,    0,    0,  437,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  107,    0,
    0,    0,    0,    0,  121,  444,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  187,    0,    0,    0,    0,    0,    0,    0,
  151,    0,  175,    0,  414,    0,    0,    0,    0,    0,
  396,    0,    0,  416,    0,    0,    0,    0,   25,   49,
  194,    0,    0,    0,    0,    0,    0,    0,  336,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  421,    0,    0,  211,    0,    0,    0,    0,    0,  409,
    0,  423,    0,    0,    0,    0,  323,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  431,    0,    0,    0,    0,    0,  433,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  439,    0,
  441,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   26,  425,  -43,   35,    0,    0,  627,  100,   -1,    0,
    0,    0,    0,  -86,    0,    0,    0,    0,    0,  111,
  131,    0,  -17, -100,  457,  -13,   28,  525,  380,    0,
  419,
};
final static int YYTABLESIZE=812;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         27,
   58,   28,   26,   37,   39,  129,   40,   96,   38,   54,
   54,  178,   48,   53,   28,    4,   50,  142,  132,   70,
  154,   71,   38,   39,   56,   40,   65,   72,   49,  118,
   52,   52,  116,  160,   14,   58,   39,   56,   40,   57,
   70,   58,   71,   58,   83,   58,   14,  174,   57,   91,
   93,   25,  162,   78,  167,  169,  179,  182,  117,   58,
   58,   27,   58,   28,   70,   56,   71,   56,  163,   56,
  111,   81,   19,   60,    4,    5,  130,    4,    5,   99,
  186,  187,  100,   56,   56,  135,   56,  194,  195,   57,
  223,   57,   19,   57,   74,   14,  134,   62,  201,   73,
   29,  171,  138,   81,  173,  139,   12,   57,   57,   88,
   57,  191,  119,   89,   63,  192,   59,  216,   64,   70,
   54,   71,   53,   58,   82,   58,   90,  172,  158,   25,
  144,   19,    9,   19,   98,  215,  194,  195,  196,   14,
    4,   67,   68,  108,  151,  121,  122,   56,  151,   56,
   53,   19,   94,  119,   29,  127,  128,  145,  146,   28,
   75,    2,  123,  193,  124,   12,    3,  194,  195,  125,
    6,   57,   81,   57,   52,  151,   77,  101,  126,   54,
  109,  110,  203,   75,    2,  133,   75,    2,   76,    3,
  237,  202,    3,    6,  137,   19,    6,   19,  140,   77,
   38,  210,   77,  112,  113,  153,  155,  156,  157,   53,
   13,  161,   25,   29,  164,   19,  166,   19,  165,  170,
   33,   34,   35,   36,  177,   25,  180,   85,   47,   12,
   11,   12,   69,   52,   83,   30,   31,   32,   95,   33,
   34,   35,   36,   54,   45,   54,  131,   51,   51,   30,
   31,   32,   33,   34,   35,   36,   58,   58,   58,   58,
   58,  159,   58,  141,   58,   58,   58,   58,   48,   13,
   58,   58,   58,   53,   25,   53,   58,   58,   58,   58,
   56,   56,   56,   56,   56,  181,   56,  183,   56,   56,
   56,   56,  168,  184,   56,   56,   56,   52,   50,   52,
   56,   56,   56,   56,   57,   57,   57,   57,   57,   85,
   57,  175,   57,   57,   57,   57,   83,  185,   57,   57,
   57,  176,   70,  188,   57,   57,   57,   57,   19,   19,
  189,   19,   19,   13,   19,   13,   19,   19,   19,   19,
   84,   85,   86,   87,   19,  190,  194,  195,   19,   19,
  200,   19,   19,   11,   19,   11,   19,   19,   19,   19,
  199,  206,   12,   12,   19,   12,   12,   45,   12,   45,
   12,   12,   12,   12,  204,  234,   54,   54,   12,   54,
   54,  205,   54,  207,   54,   54,   54,   54,    1,    2,
  211,   48,   54,   48,    3,  212,    4,    5,    6,    7,
    4,    5,  214,   38,    8,   99,   53,   53,  100,   53,
   53,   92,   53,  221,   53,   53,   53,   53,   39,  222,
   40,   50,   53,   50,   39,  226,   40,   30,   31,   32,
   52,   52,  227,   52,   52,  229,   52,  230,   52,   52,
   52,   52,   85,   85,   85,   70,   52,   70,   85,   83,
   83,   83,   85,  231,  110,   83,   30,   78,   85,   83,
   98,  112,   70,   33,   71,   83,   13,   13,   78,   13,
   13,  111,   13,   31,   13,   13,   13,   13,  208,  113,
  105,   32,   13,    4,    5,  106,   11,   11,   99,   11,
   11,  100,   11,   66,   11,   11,   11,   11,  152,    0,
   45,   45,   11,   45,   45,   78,   45,    0,   45,   45,
   45,   45,    4,    5,    0,    0,   45,   99,   29,    0,
  100,    0,    0,    0,   48,   48,    0,   48,   48,    0,
   48,   28,   48,   48,   48,   48,    0,    4,    5,    0,
   48,    0,   99,    0,    0,  100,    0,    0,   78,    0,
    0,    0,    0,    0,   50,   50,    0,   50,   50,   82,
   50,    0,   50,   50,   50,   50,   81,    0,    0,    0,
   50,    0,    0,   78,    0,    0,    0,    0,   70,   70,
    0,   70,   70,    0,   70,    0,   70,   70,   70,   70,
   75,    2,  143,    0,   70,    0,    3,  198,    0,    0,
    6,  114,    2,    0,    0,  115,   77,    3,    0,    4,
    5,    6,    7,   55,    2,    0,    0,    8,    0,    3,
  209,    4,    5,    6,    7,    0,  213,    0,  217,    8,
   30,   31,   32,  224,   33,   34,   35,   36,  114,    2,
   33,   34,   35,   36,    3,    0,    4,    5,    6,    7,
    0,   29,   29,    0,    8,    0,    0,   29,    0,   29,
   29,   29,   29,    0,   28,   28,    0,   29,    0,    0,
   28,    0,   28,   28,   28,   28,    0,    0,    0,    0,
   28,  147,    2,    0,  148,  149,  102,    3,    0,  194,
  195,    6,   82,   82,   82,    0,    0,   77,   82,   81,
   81,   81,   82,    0,    0,   81,   75,    2,   82,   81,
    0,    0,    3,    0,    0,   81,    6,    0,    0,    0,
    0,    0,   77,   55,    2,  136,    0,    0,    0,    3,
  102,    4,    5,    6,    7,    0,  220,    0,    0,    8,
  225,    0,    0,    0,    0,    0,  228,    0,    0,    0,
    0,  232,    0,  233,  235,  236,    0,    0,  238,    0,
    0,    0,    0,   55,    2,    0,    0,  102,  102,    3,
    0,    4,    5,    6,    7,    0,    0,    0,    0,    8,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  102,  102,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  102,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         59,
    0,   61,    0,   40,   60,   61,   62,   41,   45,   40,
   40,   59,   40,   44,   61,    0,  272,  104,   44,   43,
  121,   45,   45,   60,    0,   62,   28,   41,   40,  125,
   61,   61,   76,   44,    0,  256,   60,   12,   62,   59,
   43,   41,   45,   43,   46,   45,   12,  148,    0,   51,
   52,  272,  256,  123,  141,  142,   59,   44,   76,   59,
   60,   59,   62,   61,   43,   41,   45,   43,  272,   45,
   72,   44,    0,   40,  264,  265,   90,  264,  265,  269,
  167,  168,  272,   59,   60,  272,   62,  270,  271,   41,
  273,   43,    0,   45,   42,   61,   98,   59,  185,   47,
    1,  145,  269,   76,  148,  272,    0,   59,   60,   41,
   62,  256,   78,   45,   59,  260,   17,   41,   59,   43,
    0,   45,   44,  123,   41,  125,  272,  145,  130,  272,
  105,   59,    0,   61,   61,   59,  270,  271,  272,  105,
  125,  273,  274,   41,  117,  259,  260,  123,  121,  125,
    0,   59,   53,  119,   55,  273,  274,  259,  260,   61,
  256,  257,   41,  177,   41,   59,  262,  270,  271,   41,
  266,  123,  145,  125,    0,  148,  272,   41,   41,   59,
   70,   71,  196,  256,  257,   41,  256,  257,  258,  262,
  234,  193,  262,  266,  268,  123,  266,  125,   41,  272,
   45,  203,  272,   73,   74,  125,   41,   41,  273,   59,
    0,  272,  272,  114,   61,  123,  268,  125,  272,  125,
  276,  277,  278,  279,  272,  272,  272,   41,  256,  123,
    0,  125,  256,   59,   41,  272,  273,  274,  272,  276,
  277,  278,  279,  123,    0,  125,  272,  278,  278,  272,
  273,  274,  276,  277,  278,  279,  256,  257,  258,  259,
  260,  272,  262,   44,  264,  265,  266,  267,    0,   59,
  270,  271,  272,  123,  272,  125,  276,  277,  278,  279,
  256,  257,  258,  259,  260,  272,  262,  273,  264,  265,
  266,  267,   44,   61,  270,  271,  272,  123,    0,  125,
  276,  277,  278,  279,  256,  257,  258,  259,  260,  123,
  262,  260,  264,  265,  266,  267,  123,   44,  270,  271,
  272,  260,    0,  260,  276,  277,  278,  279,  256,  257,
  260,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  272,  273,  274,  275,  272,  260,  270,  271,  256,  257,
  273,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  272,  256,  256,  257,  272,  259,  260,  123,  262,  125,
  264,  265,  266,  267,  273,   41,  256,  257,  272,  259,
  260,  273,  262,  256,  264,  265,  266,  267,  256,  257,
   41,  123,  272,  125,  262,   41,  264,  265,  266,  267,
  264,  265,  273,   45,  272,  269,  256,  257,  272,  259,
  260,  256,  262,  273,  264,  265,  266,  267,   60,   41,
   62,  123,  272,  125,   60,  273,   62,  272,  273,  274,
  256,  257,   41,  259,  260,   41,  262,  273,  264,  265,
  266,  267,  256,  257,  258,  123,  272,  125,  262,  256,
  257,  258,  266,   41,   41,  262,   41,  123,  272,  266,
  125,   41,   43,   41,   45,  272,  256,  257,  123,  259,
  260,   41,  262,   41,  264,  265,  266,  267,   59,   41,
  123,   41,  272,  264,  265,   61,  256,  257,  269,  259,
  260,  272,  262,   37,  264,  265,  266,  267,  119,   -1,
  256,  257,  272,  259,  260,  123,  262,   -1,  264,  265,
  266,  267,  264,  265,   -1,   -1,  272,  269,  123,   -1,
  272,   -1,   -1,   -1,  256,  257,   -1,  259,  260,   -1,
  262,  123,  264,  265,  266,  267,   -1,  264,  265,   -1,
  272,   -1,  269,   -1,   -1,  272,   -1,   -1,  123,   -1,
   -1,   -1,   -1,   -1,  256,  257,   -1,  259,  260,  123,
  262,   -1,  264,  265,  266,  267,  123,   -1,   -1,   -1,
  272,   -1,   -1,  123,   -1,   -1,   -1,   -1,  256,  257,
   -1,  259,  260,   -1,  262,   -1,  264,  265,  266,  267,
  256,  257,  125,   -1,  272,   -1,  262,  179,   -1,   -1,
  266,  256,  257,   -1,   -1,  260,  272,  262,   -1,  264,
  265,  266,  267,  256,  257,   -1,   -1,  272,   -1,  262,
  202,  264,  265,  266,  267,   -1,  208,   -1,  210,  272,
  272,  273,  274,  215,  276,  277,  278,  279,  256,  257,
  276,  277,  278,  279,  262,   -1,  264,  265,  266,  267,
   -1,  256,  257,   -1,  272,   -1,   -1,  262,   -1,  264,
  265,  266,  267,   -1,  256,  257,   -1,  272,   -1,   -1,
  262,   -1,  264,  265,  266,  267,   -1,   -1,   -1,   -1,
  272,  256,  257,   -1,  259,  260,   60,  262,   -1,  270,
  271,  266,  256,  257,  258,   -1,   -1,  272,  262,  256,
  257,  258,  266,   -1,   -1,  262,  256,  257,  272,  266,
   -1,   -1,  262,   -1,   -1,  272,  266,   -1,   -1,   -1,
   -1,   -1,  272,  256,  257,   99,   -1,   -1,   -1,  262,
  104,  264,  265,  266,  267,   -1,  212,   -1,   -1,  272,
  216,   -1,   -1,   -1,   -1,   -1,  222,   -1,   -1,   -1,
   -1,  227,   -1,  229,  230,  231,   -1,   -1,  234,   -1,
   -1,   -1,   -1,  256,  257,   -1,   -1,  141,  142,  262,
   -1,  264,  265,  266,  267,   -1,   -1,   -1,   -1,  272,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  167,  168,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  185,
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
"dec_procedimiento : encabezado_proc param_ni bloque_llaves",
"bloque_llaves : '{' conjunto_sentencias '}'",
"bloque_llaves : '{' '}'",
"bloque_llaves : sentencia",
"param_ni : '(' lista_parametros ')' NI '=' CTE_INT",
"param_ni : '(' ')' NI '=' CTE_INT",
"param_ni : '(' lista_parametros ')'",
"param_ni : '(' ')'",
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
"parametro : tipo VAR ID",
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

//#line 686 "gramatica.y"

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


//#line 623 "Parser.java"
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
                                aux.put("Inic","?");
                                lex.tablaDeSimbolos.put(id+ambito,aux);
                        }

                  }
	     }
break;
case 13:
//#line 78 "gramatica.y"
{	logger.addError(lex.linea,"Asignacion en la declaración");

	     					  }
break;
case 14:
//#line 81 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba un tipo");}
break;
case 15:
//#line 82 "gramatica.y"
{logger.addError(lex.linea,"Tipo no valido");}
break;
case 16:
//#line 83 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba un identificador");}
break;
case 17:
//#line 85 "gramatica.y"
{
		yyval=new ParserVal(Tipos.INTEGER);
		}
break;
case 18:
//#line 88 "gramatica.y"
{
     		yyval=new ParserVal(Tipos.FLOAT);
     		}
break;
case 19:
//#line 93 "gramatica.y"
{
			ArrayList<String> aux=new ArrayList<String>();
			aux.add(val_peek(0).sval);
			yyval=new ParserVal(aux);
		}
break;
case 20:
//#line 98 "gramatica.y"
{
                	ArrayList<String> aux = (ArrayList<String>)(val_peek(0).obj);
                	aux.add(val_peek(2).sval);
                	yyval=val_peek(0);
                }
break;
case 21:
//#line 105 "gramatica.y"
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
//#line 124 "gramatica.y"
{
		    	ambito=ambito.substring(0,ambito.lastIndexOf("@"));
			DecProc proc = new DecProc((Nodo)val_peek(0).obj,null,val_peek(2).sval);
			yyval = new ParserVal(proc);
			}
break;
case 23:
//#line 131 "gramatica.y"
{ yyval = new ParserVal(val_peek(1).obj);}
break;
case 24:
//#line 132 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba una sentencia");}
break;
case 25:
//#line 133 "gramatica.y"
{ logger.addError(lex.linea,"Se esperaban llaves en la declaracion del procedimiento");}
break;
case 26:
//#line 137 "gramatica.y"
{
		String nombre = ambito.substring(ambito.lastIndexOf("@")+1,ambito.length());
		nombre=getIdentificador(nombre);
		HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(nombre);
		aux.put("NI",Integer.parseInt(val_peek(0).sval));
		aux.put("Parametros", val_peek(4).obj);
		lex.tablaDeSimbolos.put(nombre,aux);
		HashMap<String, Object> aux2=lex.tablaDeSimbolos.remove(val_peek(0).sval);
		Integer NI = (Integer)aux2.remove("NI");
		aux2.put("NI",++NI);
		lex.tablaDeSimbolos.put(val_peek(0).sval,aux2);
		}
break;
case 27:
//#line 149 "gramatica.y"
{
		String nombre = ambito.substring(ambito.lastIndexOf("@")+1,ambito.length());
		nombre=getIdentificador(nombre);
		HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(nombre);
		aux.put("NI",Integer.parseInt(val_peek(0).sval));
		lex.tablaDeSimbolos.put(nombre,aux);
		HashMap<String, Object> aux2=lex.tablaDeSimbolos.remove(val_peek(0).sval);
		Integer NI = (Integer)aux2.remove("NI");
		aux2.put("NI",++NI);
		lex.tablaDeSimbolos.put(val_peek(0).sval,aux2);
	}
break;
case 28:
//#line 160 "gramatica.y"
{    String nombre = ambito.substring(ambito.lastIndexOf("@")+1,ambito.length());
					nombre=getIdentificador(nombre);
					HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(nombre);
					aux.put("NI",0);
					aux.put("Parametros", val_peek(1).obj);
					lex.tablaDeSimbolos.put(nombre,aux);
					logger.addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");
	}
break;
case 29:
//#line 168 "gramatica.y"
{
        		String nombre = ambito.substring(ambito.lastIndexOf("@")+1,ambito.length());
        		nombre=getIdentificador(nombre);
        		HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(nombre);
        		aux.put("NI",0);
        		lex.tablaDeSimbolos.put(nombre,aux);
        		logger.addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");
        }
break;
case 30:
//#line 179 "gramatica.y"
{
				ArrayList<Parametro> parametros = new ArrayList<>();
				parametros.add((Parametro)val_peek(0).obj);
				yyval = new ParserVal(parametros);
			    }
break;
case 31:
//#line 184 "gramatica.y"
{
                 				ArrayList<Parametro> parametros = new ArrayList<>();
						parametros.add((Parametro)val_peek(2).obj);
						parametros.add((Parametro)val_peek(0).obj);
						yyval = new ParserVal(parametros);
                			     }
break;
case 32:
//#line 190 "gramatica.y"
{
                 						ArrayList<Parametro> parametros = new ArrayList<>();
                 						parametros.add((Parametro)val_peek(4).obj);
                 						parametros.add((Parametro)val_peek(2).obj);
                 						parametros.add((Parametro)val_peek(0).obj);
                 						yyval = new ParserVal(parametros);
                  					    }
break;
case 33:
//#line 197 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 34:
//#line 198 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 35:
//#line 199 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 36:
//#line 200 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 37:
//#line 201 "gramatica.y"
{logger.addError(lex.linea,"Se esperaban como maximo 3 parametros");}
break;
case 38:
//#line 204 "gramatica.y"
{
		    HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(val_peek(0).sval);
		    aux.put("Uso","variable");
		    aux.put("Tipo",(Tipos)val_peek(1).obj);
		    aux.put("Inic","?");
                    lex.tablaDeSimbolos.put(val_peek(0).sval+ambito,aux);
                    yyval = new ParserVal(new Parametro(val_peek(0).sval+ambito,(Tipos)val_peek(1).obj,"COPIA"));
	   }
break;
case 39:
//#line 212 "gramatica.y"
{
	   			HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(val_peek(0).sval);
                                aux.put("Uso","variable");
                                aux.put("Tipo",(Tipos)val_peek(1).obj);
                                aux.put("Inic","?");
                                lex.tablaDeSimbolos.put(val_peek(0).sval+ambito,aux);
	   			yyval = new ParserVal(new Parametro(val_peek(0).sval+ambito,(Tipos)val_peek(1).obj,"VAR"));
	   		 }
break;
case 40:
//#line 220 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba tipo");}
break;
case 41:
//#line 221 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba tipo");}
break;
case 42:
//#line 222 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba identificador");}
break;
case 43:
//#line 223 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba VAR TIPO ID");
	   		 HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(val_peek(0).sval);
			 aux.put("Uso","variable");
			 aux.put("Tipo",(Tipos)val_peek(2).obj);
			 aux.put("Inic","?");
			 lex.tablaDeSimbolos.put(val_peek(0).sval+ambito,aux);
	   		 yyval = new ParserVal(new Parametro(val_peek(0).sval+ambito,(Tipos)val_peek(2).obj,"VAR"));
	   		 }
break;
case 44:
//#line 234 "gramatica.y"
{ logger.addEvent(lex.linea,"Se encontró una sentencia de asignación");
			     yyval = val_peek(1);
			   }
break;
case 45:
//#line 237 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\"");}
break;
case 46:
//#line 238 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia de seleccion"); }
break;
case 47:
//#line 239 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia de salida");}
break;
case 48:
//#line 240 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\"");}
break;
case 49:
//#line 241 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia de llamada"); }
break;
case 50:
//#line 242 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\"");}
break;
case 51:
//#line 243 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia de control"); }
break;
case 52:
//#line 246 "gramatica.y"
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
case 53:
//#line 259 "gramatica.y"
{logger.addError(lex.linea,"Se encontró == en lugar de =");}
break;
case 54:
//#line 260 "gramatica.y"
{logger.addError(lex.linea,"Asignacion mal escrita");}
break;
case 55:
//#line 261 "gramatica.y"
{logger.addError(lex.linea,"Asignacion mal escrita");}
break;
case 56:
//#line 264 "gramatica.y"
{
					Suma suma = new Suma((ConTipo)val_peek(2).obj,(ConTipo)val_peek(0).obj);
					suma.updateTipo();
					if(suma.getTipo()==null)
						logger.addError(lex.linea,"Incompatibilidad de tipos en la suma");
    				   	yyval = new ParserVal(suma);

    				  }
break;
case 57:
//#line 272 "gramatica.y"
{
          				Resta resta = new Resta((ConTipo)val_peek(2).obj,(ConTipo)val_peek(0).obj);
          				resta.updateTipo();
          				if(resta.getTipo()==null)
                                        	logger.addError(lex.linea,"Incompatibilidad de tipos en la resta");
          			   	yyval = new ParserVal(resta);
          			  }
break;
case 58:
//#line 279 "gramatica.y"
{
          	      yyval = val_peek(0);
          	    }
break;
case 59:
//#line 284 "gramatica.y"
{
				Division division = new Division((ConTipo)val_peek(2).obj,(ConTipo)val_peek(0).obj);
				division.updateTipo();
				if(division.getTipo()==null)
                                        logger.addError(lex.linea,"Incompatibilidad de tipos en la division");
                                yyval = new ParserVal(division);
			     }
break;
case 60:
//#line 291 "gramatica.y"
{
        			Multiplicacion multiplicacion = new Multiplicacion((ConTipo)val_peek(2).obj,(ConTipo)val_peek(0).obj);
        			multiplicacion.updateTipo();
        		      	if(multiplicacion.getTipo()==null)
                                       logger.addError(lex.linea,"Incompatibilidad de tipos en la multiplicacion");
                                yyval = new ParserVal(multiplicacion);
        		     }
break;
case 61:
//#line 298 "gramatica.y"
{
                   yyval = val_peek(0);
                 }
break;
case 62:
//#line 303 "gramatica.y"
{	String var = getIdentificador(val_peek(0).sval);
		if(var==null){
			logger.addError(lex.linea,"Variable \""+ val_peek(0).sval+ "\" no declarada" );
		}
		else
			yyval = new ParserVal(new Hoja(var));
	}
break;
case 63:
//#line 310 "gramatica.y"
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
case 64:
//#line 321 "gramatica.y"
{ 	if(val_peek(0).sval!=null){
       				yyval = new ParserVal(new Hoja(val_peek(0).sval));
       			}
       			}
break;
case 65:
//#line 325 "gramatica.y"
{
       			if(val_peek(0).sval!=null){
				int i = -(int) Integer.parseInt(val_peek(0).sval);
				if (!lex.tablaDeSimbolos.containsKey(String.valueOf(i))) {
					    HashMap<String, Object> aux = new HashMap<String, Object>();
					    aux.put("Tipo", Tipos.INTEGER);
                                            aux.put("Contador", 1);
                                            aux.put("NI",0);
					    lex.tablaDeSimbolos.put(String.valueOf(i), aux);
				}
				int aux = (int) lex.tablaDeSimbolos.get(String.valueOf(-i)).get("Contador");
				lex.tablaDeSimbolos.get(String.valueOf(-i)).put("Contador",aux-1);
				yyval = new ParserVal(new Hoja(String.valueOf(i)));
			}
       		     }
break;
case 66:
//#line 340 "gramatica.y"
{

      			if(val_peek(0).sval!=null){

				float f = -(float) Float.parseFloat(val_peek(0).sval);
				if (!lex.tablaDeSimbolos.containsKey(String.valueOf(f))) {
					    HashMap<String, Object> aux = new HashMap<String, Object>();
					    aux.put("Tipo", Tipos.FLOAT);
                                            aux.put("Contador", 1);
                                            aux.put("NI",0);
                                            lex.tablaDeSimbolos.put(String.valueOf(f), aux);
				}
				int aux = (int) lex.tablaDeSimbolos.get(String.valueOf(-f)).get("Contador");
				lex.tablaDeSimbolos.get(String.valueOf(-f)).put("Contador",aux-1);
				yyval = new ParserVal(new Hoja(String.valueOf(f)));
			}
       		       }
break;
case 67:
//#line 359 "gramatica.y"
{
									  CuerpoIf aux = new CuerpoIf((Nodo)val_peek(1).obj,null);
									  yyval = new ParserVal(new If((Nodo)val_peek(3).obj, aux));
 									}
break;
case 68:
//#line 363 "gramatica.y"
{
          											       CuerpoIf aux = new CuerpoIf((Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);
												       yyval = new ParserVal(new If((Nodo)val_peek(5).obj, aux));
          											     }
break;
case 69:
//#line 367 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba ELSE");}
break;
case 70:
//#line 368 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba END_IF");}
break;
case 71:
//#line 369 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba END_IF");}
break;
case 72:
//#line 370 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba THEN");}
break;
case 73:
//#line 371 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba THEN");}
break;
case 74:
//#line 372 "gramatica.y"
{logger.addError(lex.linea,"No se encontraron sentencias ejecutables");}
break;
case 75:
//#line 373 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 76:
//#line 374 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del ELSE");}
break;
case 77:
//#line 375 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 78:
//#line 376 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 79:
//#line 379 "gramatica.y"
{ yyval=val_peek(1); }
break;
case 80:
//#line 380 "gramatica.y"
{ logger.addError(lex.linea, "Falta parentesis de abertura en la condicion"); }
break;
case 81:
//#line 381 "gramatica.y"
{ logger.addError(lex.linea, "Falta parentesis de cierre en la condicion"); }
break;
case 82:
//#line 382 "gramatica.y"
{ logger.addError(lex.linea, "Faltan ambos parentesis en la condicion"); }
break;
case 83:
//#line 385 "gramatica.y"
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
case 84:
//#line 394 "gramatica.y"
{logger.addError(lex.linea,"Condicion mal escrita");}
break;
case 85:
//#line 395 "gramatica.y"
{}
break;
case 86:
//#line 398 "gramatica.y"
{ yyval = new ParserVal(new Menor(null,null));}
break;
case 87:
//#line 399 "gramatica.y"
{ yyval = new ParserVal(new Mayor(null,null));}
break;
case 88:
//#line 400 "gramatica.y"
{ yyval = new ParserVal(new Igual(null,null));}
break;
case 89:
//#line 401 "gramatica.y"
{ yyval = new ParserVal(new MayorIgual(null,null));}
break;
case 90:
//#line 402 "gramatica.y"
{ yyval = new ParserVal(new MenorIgual(null,null));}
break;
case 91:
//#line 403 "gramatica.y"
{ yyval = new ParserVal(new Distinto(null,null));}
break;
case 92:
//#line 406 "gramatica.y"
{
		       				    yyval = new ParserVal(new Then((Nodo)val_peek(0).obj));
		       				}
break;
case 93:
//#line 411 "gramatica.y"
{
		       				    yyval = new ParserVal(new Else((Nodo)val_peek(0).obj));
		       				  }
break;
case 94:
//#line 416 "gramatica.y"
{
		       				    yyval = new ParserVal(new Bloque((Nodo)val_peek(0).obj,null));
		       				  }
break;
case 95:
//#line 421 "gramatica.y"
{ yyval = new ParserVal(val_peek(1).obj);}
break;
case 96:
//#line 422 "gramatica.y"
{}
break;
case 97:
//#line 423 "gramatica.y"
{}
break;
case 98:
//#line 427 "gramatica.y"
{
					try{
						yyval = new ParserVal(new Bloque((Nodo)val_peek(0).obj,null));
					    }
					    catch(Exception e){
						error=true;
					    }
        			}
break;
case 99:
//#line 435 "gramatica.y"
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
case 100:
//#line 447 "gramatica.y"
{ yyval=new ParserVal(new Salida(val_peek(1).sval));}
break;
case 101:
//#line 448 "gramatica.y"
{
       			String var =getIdentificador(val_peek(1).sval);
                        if(var==null){
                        	logger.addError(lex.linea,"Variable \""+ val_peek(1).sval+ "\" no declarada" );
                        }else
       				yyval=new ParserVal(new Salida(var)); }
break;
case 102:
//#line 454 "gramatica.y"
{yyval=new ParserVal(new Salida(val_peek(1).sval));}
break;
case 103:
//#line 455 "gramatica.y"
{
					int i = -(int) Integer.parseInt(val_peek(1).sval);
					if (!lex.tablaDeSimbolos.containsKey(String.valueOf(i))) {
						HashMap<String, Object> aux = new HashMap<String, Object>();
                                                aux.put("Tipo", Tipos.INTEGER);
						aux.put("Contador", 1);
						aux.put("NI",0);
                                                lex.tablaDeSimbolos.put(String.valueOf(i), aux);
					}
					int aux = (int) lex.tablaDeSimbolos.get(String.valueOf(-i)).get("Contador");
					lex.tablaDeSimbolos.get(String.valueOf(-i)).put("Contador",aux-1);
					yyval=new ParserVal(new Salida("-"+val_peek(1).sval));

       					}
break;
case 104:
//#line 469 "gramatica.y"
{yyval=new ParserVal(new Salida(val_peek(1).sval));}
break;
case 105:
//#line 470 "gramatica.y"
{
       					float f = -(float) Float.parseFloat(val_peek(1).sval);
					if (!lex.tablaDeSimbolos.containsKey(String.valueOf(f))) {
						HashMap<String, Object> aux = new HashMap<String, Object>();
						aux.put("Tipo", Tipos.FLOAT);
						aux.put("Contador", 1);
                                                aux.put("NI",0);
						lex.tablaDeSimbolos.put(String.valueOf(f), aux);
					}
					int aux = (int) lex.tablaDeSimbolos.get(String.valueOf(-f)).get("Contador");
					lex.tablaDeSimbolos.get(String.valueOf(-f)).put("Contador",aux-1);
       					yyval=new ParserVal(new Salida("-"+val_peek(1).sval));}
break;
case 106:
//#line 482 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba una cadena");}
break;
case 107:
//#line 483 "gramatica.y"
{logger.addError(lex.linea,"Sentencia OUT mal escrita");}
break;
case 108:
//#line 486 "gramatica.y"
{
				String proc = getIdentificador(val_peek(3).sval);
				if(proc==null){
					logger.addError(lex.linea,"Procedimiento \""+ val_peek(3).sval+ "\" no declarado" );
				}
				else{
					String a;
					HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(proc);
					if(aux.get("Uso").equals("procedimiento")){
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
							yyval=new ParserVal(new Llamada(proc,parametros_llamada));
						} else {
							lex.tablaDeSimbolos.put(proc,aux);
							logger.addError(lex.linea,"Invocacion invalida, \"" + val_peek(3).sval + "\" no lleva parametros" );
						}
					}
					else {
						lex.tablaDeSimbolos.put(proc,aux);
						logger.addError(lex.linea,"Invocacion invalida, \"" + val_peek(3).sval + "\" no es un procedimiento" );
					}

				}
	}
break;
case 109:
//#line 534 "gramatica.y"
{
			String proc = getIdentificador(val_peek(2).sval);
			if(proc==null){
				logger.addError(lex.linea,"Procedimiento \""+ val_peek(2).sval+ "\" no declarado" );
			}
			else{
				String a;
				HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(proc);
				if(aux.get("Uso").equals("procedimiento")){
					if(!aux.containsKey("Parametros")) {
						lex.tablaDeSimbolos.put(proc,aux);
						yyval=new ParserVal(new Llamada(proc,null));

					} else {
						lex.tablaDeSimbolos.put(proc,aux);
						logger.addError(lex.linea,"Invocacion invalida, \"" + val_peek(2).sval + "\" lleva parametros" );
					}
				}
				else {
					lex.tablaDeSimbolos.put(proc,aux);
					logger.addError(lex.linea,"Invocacion invalida, \"" + val_peek(2).sval + "\" no es un procedimiento" );
				}

			}
	}
break;
case 110:
//#line 561 "gramatica.y"
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
case 111:
//#line 573 "gramatica.y"
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
case 112:
//#line 594 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 113:
//#line 595 "gramatica.y"
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
case 114:
//#line 615 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 115:
//#line 616 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 116:
//#line 617 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 117:
//#line 618 "gramatica.y"
{logger.addError(lex.linea,"Se esperaban como maximo 3 parametros");
           				ArrayList<String> aux = new ArrayList<>();
           				aux.add(val_peek(5).sval);
           				aux.add(val_peek(3).sval);
           				aux.add(val_peek(1).sval);
           				yyval = new ParserVal(aux);
           }
break;
case 118:
//#line 627 "gramatica.y"
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
case 119:
//#line 666 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\" pero se recibio "+ val_peek(7).sval);}
break;
case 120:
//#line 667 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\" pero se recibio "+ val_peek(3).sval);}
break;
case 121:
//#line 668 "gramatica.y"
{logger.addError(lex.linea,"Se esperaban \";\" en la sentencia FOR");}
break;
case 122:
//#line 669 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba UP o DOWN en la sentencia FOR");}
break;
case 123:
//#line 670 "gramatica.y"
{logger.addError(lex.linea,"Falta inicialización en la sentencia FOR");}
break;
case 124:
//#line 671 "gramatica.y"
{logger.addError(lex.linea,"Falta condicion en la sentencia FOR");}
break;
case 125:
//#line 672 "gramatica.y"
{logger.addError(lex.linea,"Falta incremento en la sentencia FOR");}
break;
case 126:
//#line 673 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \")\" en la sentencia FOR");}
break;
case 127:
//#line 674 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del FOR");}
break;
case 128:
//#line 677 "gramatica.y"
{
		yyval = new ParserVal(new Suma(null,null));
	       }
break;
case 129:
//#line 680 "gramatica.y"
{
          	   yyval = new ParserVal(new Resta(null,null));
	 	 }
break;
//#line 1758 "Parser.java"
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
