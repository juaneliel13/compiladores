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
   10,    6,    6,    6,    6,    6,    6,    6,   11,   11,
   11,   11,   11,   11,   11,   11,   12,   12,   12,   12,
   12,    4,    4,    4,    4,    4,    4,    4,    4,   13,
   13,   13,   13,    9,    9,    9,   18,   18,   18,   19,
   19,   19,   19,   19,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   20,   20,   20,   20,
   23,   23,   23,   24,   24,   24,   24,   24,   24,   21,
   21,   22,   22,   26,   26,   25,   25,   15,   15,   15,
   15,   15,   15,   15,   15,   16,   16,   27,   27,   27,
   27,   27,   27,   27,   27,   17,   17,   17,   17,   17,
   17,   17,   17,   17,   17,   28,   28,
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
    3,    1,    3,    1,    3,    1,    2,    4,    4,    4,
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
    0,    0,    0,    0,    0,    0,    0,   90,    0,   78,
    0,    0,    0,    0,    0,  104,    0,    0,    0,   53,
    0,   20,    0,  107,    0,    0,    0,   40,    0,    0,
    0,    0,   77,    0,    0,    0,   57,   58,    0,   72,
    0,    0,    0,    0,    0,   70,   99,  100,  102,   98,
    0,    0,    0,    0,    0,    0,  106,    0,   39,    0,
    0,    0,   37,    0,    0,    0,    0,   73,    0,    0,
   65,    0,   92,    0,   97,   91,    0,  101,  103,    0,
    0,  112,    0,    0,   41,   38,    0,    0,    0,    0,
    0,    0,   33,    0,    0,    0,    0,    0,   67,   71,
    0,    0,    0,  114,  113,    0,    0,   26,    0,    0,
    0,   34,   35,   76,   75,   74,   69,   66,   93,    0,
  126,  127,    0,    0,    0,    0,    0,    0,   24,    0,
    0,    0,    0,    0,  115,   28,    0,    0,    0,   36,
    0,    0,    0,    0,    0,   23,   27,    0,   25,    0,
    0,    0,    0,    0,    0,   94,  122,  121,   22,    0,
    0,    0,    0,  123,    0,    0,    0,  119,    0,    0,
    0,   95,  117,  120,    0,  124,  118,  125,  116,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,  226,   15,   16,   17,   18,   41,   19,
  101,  102,   20,   21,   22,   23,   24,   42,   43,   44,
   79,  144,   45,   46,  114,  227,   95,  194,
};
final static short yysindex[] = {                       133,
    3,  -36,  -27,    0,    0,   10, -237,  -30,    0,    0,
    0, -127,    0,    0,   -6,    0, -215,    0,   16,   13,
    0,   24,   37,    0,   31,    0,    0,  -22,    0,    0,
    0,    0,    0,    0,    0,    0,  359, -158,    0,    0,
  -23,    5,    0,  427,   28,  -22,    0,   69, -159,    0,
  -22,  156, -149,  -33,  -59,    0,    0,    0,   66,  137,
    0,    0,    0,   46,   84,    0,    0,    0,  -22,  -22,
  -22,  -22,  -22,   70,  -72,  -29, -113,    0, -104,    0,
   46,  101,  117,  119,  127,    0,  -91,  -55,   46,    0,
   46,    0,  -25,    0,  129,  -22, -160,    0, -105,  -96,
  145,  220,    0,    5,    5,   46,    0,    0,  -46,    0,
  -57,  -95, -113,   72,  522,    0,    0,    0,    0,    0,
  148,  150,  -74,  -22,  -10,  -60,    0,   46,    0, -213,
  153, -127,    0,  -87, -166,  249,  399,    0,   70,  444,
    0, -113,    0,  -45,    0,    0,  -41,    0,    0,  -47,
   -5,    0,  -52,   14,    0,    0,   15,  102,  233, -127,
  274, -166,    0,   52,   62,   71, -189,  199,    0,    0,
  365, -170, -124,    0,    0,   74,  228,    0,   88,  237,
 -166,    0,    0,    0,    0,    0,    0,    0,    0,  -22,
    0,    0,  365,  109,  111,  140,  535,  461,    0,  147,
  420,  -22,  334,  350,    0,    0,  289,  547,  295,    0,
 -124,  160,   77,  524,  524,    0,    0,  301,    0,  163,
  397, -193,  524,  181, -113,    0,    0,    0,    0,  414,
  524,  416,  188,    0,  421,  339,  524,    0,  524,  335,
  524,    0,    0,    0,  482,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,   93,    0,    0,
    0,   29,    0,    0,  231,    0,    0,    0,    0,  245,
    0,  269,  299,    0,   73,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    1,    0,    0,  494,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  107,    0,
    0,    0,    0,  121,  501,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  187,    0,    0,    0,    0,    0,    0,    0,  151,    0,
  175,    0,  428,    0,    0,    0,    0,    0,    0,    0,
    0,  431,    0,   25,   49,  194,    0,    0,    0,    0,
    0,    0,  349,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  439,    0,    0,  211,    0,    0,
    0,    0,    0,    0,    0,  440,    0,    0,  323,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  441,    0,    0,    0,    0,    0,    0,
  445,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  453,    0,    0,    0,  458,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    9,    0,  -58,  537,    0,    0,  551,   64,  502,    0,
    0,  442,    0,    0,    0,    0,    0,  135,  136,    0,
  -42,  -85,  463,  -14,  -97,  593,    0, -125,
};
final static int YYTABLESIZE=838;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         27,
   56,   28,   26,   37,   39,  123,   40,   94,   38,   54,
   54,  172,   48,   53,   28,  145,  111,  132,  126,   69,
   56,   70,   38,   39,   54,   40,   71,  142,    4,  147,
   52,   52,  112,  153,   50,  160,   39,   69,   40,   70,
   58,   56,  155,   56,  168,   56,   73,  195,   55,   49,
   77,   72,   57,  173,  167,   60,   25,  176,  156,   56,
   56,   27,   56,   28,   29,   54,  187,   54,   80,   54,
  188,   61,   19,  124,   53,  212,  191,  192,  164,  232,
   59,  166,   62,   54,   54,  220,   54,  224,   69,   55,
   70,   55,   19,   55,  165,   63,  233,    4,    5,  191,
  192,  193,   97,    4,    5,   98,   12,   55,   55,   86,
   55,  129,   88,   87,   66,   67,   92,  223,   29,   69,
   52,   70,   25,   56,  103,   56,   96,  236,   55,    2,
   28,   19,    9,   19,    3,  222,    4,    5,    6,    7,
  158,  117,   74,    2,    8,  191,  192,   54,    3,   54,
   51,   19,    6,    4,  115,  116,  190,  118,   76,  119,
  139,    2,  131,  140,  141,   12,    3,  120,  180,  127,
    6,   55,   29,   55,   50,  133,   76,   99,  202,   52,
  159,  121,  122,  109,    2,  134,  248,  110,  148,    3,
  149,    4,    5,    6,    7,   19,  146,   19,  150,    8,
   38,  137,  138,  104,  105,  207,  209,  107,  108,   51,
   13,  154,   25,  157,  169,   19,  218,   19,  170,  174,
   33,   34,   35,   36,  171,   25,  178,   83,   47,   12,
   11,   12,   68,   50,   81,   30,   31,   32,   93,   33,
   34,   35,   36,   52,   43,   52,  125,   51,   51,   30,
   31,   32,   33,   34,   35,   36,   56,   56,   56,   56,
   56,  152,   56,  135,   56,   56,   56,   56,   46,   13,
   56,   56,   56,   51,   25,   51,   56,   56,   56,   56,
   54,   54,   54,   54,   54,  175,   54,  177,   54,   54,
   54,   54,  162,  179,   54,   54,   54,   50,   48,   50,
   54,   54,   54,   54,   55,   55,   55,   55,   55,   83,
   55,  184,   55,   55,   55,   55,   81,  181,   55,   55,
   55,  185,   68,  189,   55,   55,   55,   55,   19,   19,
  186,   19,   19,   13,   19,   13,   19,   19,   19,   19,
   82,   83,   84,   85,   19,  196,  191,  192,   19,   19,
  197,   19,   19,   11,   19,   11,   19,   19,   19,   19,
  198,  199,   12,   12,   19,   12,   12,   43,   12,   43,
   12,   12,   12,   12,  214,  245,   52,   52,   12,   52,
   52,  203,   52,  204,   52,   52,   52,   52,    1,    2,
  215,   46,   52,   46,    3,  205,    4,    5,    6,    7,
    4,    5,  210,   38,    8,   97,   51,   51,   98,   51,
   51,   90,   51,  216,   51,   51,   51,   51,   39,  219,
   40,   48,   51,   48,   39,  229,   40,   30,   31,   32,
   50,   50,  221,   50,   50,  230,   50,  231,   50,   50,
   50,   50,   83,   83,   83,   68,   50,   68,   83,   81,
   81,   81,   83,  235,  237,   81,  239,  225,   83,   81,
  240,  241,   69,  242,   70,   81,   13,   13,  108,   13,
   13,   29,   13,   96,   13,   13,   13,   13,  211,  110,
   32,  109,   13,    4,    5,   30,   11,   11,   97,   11,
   11,   98,   11,  111,   11,   11,   11,   11,   31,   65,
   43,   43,   11,   43,   43,    0,   43,    0,   43,   43,
   43,   43,    4,    5,    0,    0,   43,   97,    0,    0,
   98,   77,    0,    0,   46,   46,    0,   46,   46,   64,
   46,    0,   46,   46,   46,   46,   14,    4,    5,    0,
   46,    0,   97,  136,    0,   98,    0,   81,   14,   77,
    0,    0,   89,   91,   48,   48,    0,   48,   48,    0,
   48,    0,   48,   48,   48,   48,  142,    0,    0,    0,
   48,    0,  106,    0,    0,    0,  161,  163,   68,   68,
   78,   68,   68,  208,   68,    0,   68,   68,   68,   68,
   74,    2,    0,    0,   68,    0,    3,  128,    0,    0,
    6,    0,  182,  183,  225,    0,   76,    0,    0,    0,
  100,   78,    0,  113,    0,    0,   80,    0,    0,    0,
    0,    0,  200,   79,    0,  151,    0,    0,    0,    0,
   30,   31,   32,    0,   33,   34,   35,   36,    0,    0,
   33,   34,   35,   36,  142,    0,  225,  130,  143,  113,
    0,  143,  100,    0,  109,    2,    0,    0,    0,  206,
    3,    0,    4,    5,    6,    7,    0,    0,   14,    0,
    8,  217,    0,   78,    0,    0,  143,    0,  113,    0,
    0,    0,   74,    2,   75,  100,  100,    0,    3,  191,
  192,  201,    6,    0,    0,    0,   14,    0,   76,  109,
    2,    0,    0,  213,    0,    3,    0,    4,    5,    6,
    7,  100,  100,    0,    0,    8,   55,    2,    0,    0,
    0,    0,    3,    0,    4,    5,    6,    7,    0,    0,
    0,  100,    8,   14,   14,    0,    0,  109,    2,    0,
    0,    0,    0,    3,   14,    4,    5,    6,    7,   80,
   80,   80,    0,    8,    0,   80,   79,   79,   79,   80,
    0,  113,   79,    0,    0,   80,   79,    0,    0,    0,
    0,    0,   79,    0,    0,    0,    0,   74,    2,   74,
    2,    0,    0,    3,    0,    3,    0,    6,    0,    6,
   55,    2,    0,   76,    0,   76,    3,    0,    4,    5,
    6,    7,   55,    2,    0,    0,    8,  228,    3,    0,
    4,    5,    6,    7,    0,  234,    0,    0,    8,    0,
    0,    0,    0,  238,    0,    0,    0,    0,    0,  243,
    0,  244,  246,  247,    0,    0,    0,  249,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         59,
    0,   61,    0,   40,   60,   61,   62,   41,   45,   40,
   40,   59,   40,   44,   61,  113,   75,  123,   44,   43,
   12,   45,   45,   60,    0,   62,   41,  123,    0,  115,
   61,   61,   75,   44,  272,  123,   60,   43,   62,   45,
  256,   41,  256,   43,  142,   45,   42,  173,    0,   40,
  123,   47,   59,   59,  140,   40,  272,   44,  272,   59,
   60,   59,   62,   61,    1,   41,  256,   43,   41,   45,
  260,   59,    0,   88,   44,  201,  270,  271,  137,  273,
   17,  140,   59,   59,   60,  211,   62,  213,   43,   41,
   45,   43,    0,   45,  137,   59,  222,  264,  265,  270,
  271,  272,  269,  264,  265,  272,    0,   59,   60,   41,
   62,  272,  272,   45,  273,  274,   53,   41,   55,   43,
    0,   45,  272,  123,   41,  125,   61,  225,  256,  257,
   61,   59,    0,   61,  262,   59,  264,  265,  266,  267,
  132,   41,  256,  257,  272,  270,  271,  123,  262,  125,
    0,   59,  266,  125,  259,  260,  171,   41,  272,   41,
  256,  257,  268,  259,  260,   59,  262,   41,  160,   41,
  266,  123,  109,  125,    0,  272,  272,   41,  193,   59,
  268,  273,  274,  256,  257,   41,  245,  260,   41,  262,
   41,  264,  265,  266,  267,  123,  125,  125,  273,  272,
   45,  259,  260,   69,   70,  197,  198,   72,   73,   59,
    0,  272,  272,   61,  260,  123,  208,  125,  260,  272,
  276,  277,  278,  279,  272,  272,  125,   41,  256,  123,
    0,  125,  256,   59,   41,  272,  273,  274,  272,  276,
  277,  278,  279,  123,    0,  125,  272,  278,  278,  272,
  273,  274,  276,  277,  278,  279,  256,  257,  258,  259,
  260,  272,  262,   44,  264,  265,  266,  267,    0,   59,
  270,  271,  272,  123,  272,  125,  276,  277,  278,  279,
  256,  257,  258,  259,  260,  272,  262,  273,  264,  265,
  266,  267,   44,   61,  270,  271,  272,  123,    0,  125,
  276,  277,  278,  279,  256,  257,  258,  259,  260,  123,
  262,  260,  264,  265,  266,  267,  123,   44,  270,  271,
  272,  260,    0,  125,  276,  277,  278,  279,  256,  257,
  260,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  272,  273,  274,  275,  272,  272,  270,  271,  256,  257,
  123,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  273,  125,  256,  257,  272,  259,  260,  123,  262,  125,
  264,  265,  266,  267,   41,   41,  256,  257,  272,  259,
  260,  273,  262,  273,  264,  265,  266,  267,  256,  257,
   41,  123,  272,  125,  262,  256,  264,  265,  266,  267,
  264,  265,  256,   45,  272,  269,  256,  257,  272,  259,
  260,  256,  262,  125,  264,  265,  266,  267,   60,  125,
   62,  123,  272,  125,   60,  125,   62,  272,  273,  274,
  256,  257,  273,  259,  260,  273,  262,   41,  264,  265,
  266,  267,  256,  257,  258,  123,  272,  125,  262,  256,
  257,  258,  266,  273,   41,  262,   41,  123,  272,  266,
  273,   41,   43,  125,   45,  272,  256,  257,   41,  259,
  260,   41,  262,  125,  264,  265,  266,  267,   59,   41,
   41,   41,  272,  264,  265,   41,  256,  257,  269,  259,
  260,  272,  262,   41,  264,  265,  266,  267,   41,   37,
  256,  257,  272,  259,  260,   -1,  262,   -1,  264,  265,
  266,  267,  264,  265,   -1,   -1,  272,  269,   -1,   -1,
  272,  123,   -1,   -1,  256,  257,   -1,  259,  260,   28,
  262,   -1,  264,  265,  266,  267,    0,  264,  265,   -1,
  272,   -1,  269,  102,   -1,  272,   -1,   46,   12,  123,
   -1,   -1,   51,   52,  256,  257,   -1,  259,  260,   -1,
  262,   -1,  264,  265,  266,  267,  123,   -1,   -1,   -1,
  272,   -1,   71,   -1,   -1,   -1,  135,  136,  256,  257,
   44,  259,  260,  123,  262,   -1,  264,  265,  266,  267,
  256,  257,   -1,   -1,  272,   -1,  262,   96,   -1,   -1,
  266,   -1,  161,  162,  123,   -1,  272,   -1,   -1,   -1,
   60,   75,   -1,   77,   -1,   -1,  123,   -1,   -1,   -1,
   -1,   -1,  181,  123,   -1,  124,   -1,   -1,   -1,   -1,
  272,  273,  274,   -1,  276,  277,  278,  279,   -1,   -1,
  276,  277,  278,  279,  123,   -1,  123,   97,  112,  113,
   -1,  115,  102,   -1,  256,  257,   -1,   -1,   -1,  125,
  262,   -1,  264,  265,  266,  267,   -1,   -1,  132,   -1,
  272,  125,   -1,  137,   -1,   -1,  140,   -1,  142,   -1,
   -1,   -1,  256,  257,  258,  135,  136,   -1,  262,  270,
  271,  190,  266,   -1,   -1,   -1,  160,   -1,  272,  256,
  257,   -1,   -1,  202,   -1,  262,   -1,  264,  265,  266,
  267,  161,  162,   -1,   -1,  272,  256,  257,   -1,   -1,
   -1,   -1,  262,   -1,  264,  265,  266,  267,   -1,   -1,
   -1,  181,  272,  197,  198,   -1,   -1,  256,  257,   -1,
   -1,   -1,   -1,  262,  208,  264,  265,  266,  267,  256,
  257,  258,   -1,  272,   -1,  262,  256,  257,  258,  266,
   -1,  225,  262,   -1,   -1,  272,  266,   -1,   -1,   -1,
   -1,   -1,  272,   -1,   -1,   -1,   -1,  256,  257,  256,
  257,   -1,   -1,  262,   -1,  262,   -1,  266,   -1,  266,
  256,  257,   -1,  272,   -1,  272,  262,   -1,  264,  265,
  266,  267,  256,  257,   -1,   -1,  272,  215,  262,   -1,
  264,  265,  266,  267,   -1,  223,   -1,   -1,  272,   -1,
   -1,   -1,   -1,  231,   -1,   -1,   -1,   -1,   -1,  237,
   -1,  239,  240,  241,   -1,   -1,   -1,  245,
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
"bloque_ejecutables_then : ejecutable",
"bloque_ejecutables_then : '{' bloque_ejecutables '}'",
"bloque_ejecutables_else : ejecutable",
"bloque_ejecutables_else : '{' bloque_ejecutables '}'",
"bloque_ejecutables_for : ejecutable",
"bloque_ejecutables_for : '{' bloque_ejecutables '}'",
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

//#line 341 "gramatica.y"

AnalizadorLexico lex;
public Nodo raiz = null;
String ambito;
ArrayList<String> listaVariables;
Logger logger = Logger.getInstance();

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


//#line 611 "Parser.java"
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
{logger.addError(lex.linea,"Sin sentencias");}
break;
case 3:
//#line 20 "gramatica.y"
{logger.addError(lex.linea,"Sin sentencias validas");}
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
{logger.addError(lex.linea,"Sentencia mal escrita");}
break;
case 9:
//#line 33 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia declarativa de variable");}
break;
case 10:
//#line 34 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia declarativa de procedimiento");}
break;
case 11:
//#line 35 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\"");}
break;
case 12:
//#line 38 "gramatica.y"
{
		for (String id : (ArrayList<String>)(val_peek(0).obj)){
                	HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(id);
                	aux.put("Uso","variable");
 		    	lex.tablaDeSimbolos.put(id+ambito,aux);

                  }
	     }
break;
case 13:
//#line 46 "gramatica.y"
{logger.addError(lex.linea,"Asignacion en la declaración");}
break;
case 14:
//#line 47 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba un tipo");}
break;
case 15:
//#line 48 "gramatica.y"
{logger.addError(lex.linea,"Tipo no valido");}
break;
case 16:
//#line 49 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba un identificador");}
break;
case 17:
//#line 51 "gramatica.y"
{}
break;
case 18:
//#line 52 "gramatica.y"
{}
break;
case 19:
//#line 55 "gramatica.y"
{
			ArrayList<String> aux=new ArrayList<String>();
			aux.add(val_peek(0).sval);
			yyval=new ParserVal(aux);
		}
break;
case 20:
//#line 60 "gramatica.y"
{
                	ArrayList<String> aux = (ArrayList<String>)(val_peek(0).obj);
                	aux.add(val_peek(2).sval);
                	yyval=val_peek(0);
                }
break;
case 21:
//#line 67 "gramatica.y"
{HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(val_peek(0).sval);
			aux.put("Uso","procedimiento");
                        lex.tablaDeSimbolos.put(val_peek(0).sval+ambito,aux);
			ambito+="@"+val_peek(0).sval;
			}
break;
case 22:
//#line 73 "gramatica.y"
{
		    	ambito=ambito.substring(0,ambito.lastIndexOf("@"));
		   }
break;
case 23:
//#line 76 "gramatica.y"
{ambito=ambito.substring(0,ambito.lastIndexOf("@"));}
break;
case 24:
//#line 77 "gramatica.y"
{ logger.addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");
                  									   ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  									 }
break;
case 25:
//#line 80 "gramatica.y"
{ logger.addError(lex.linea,"Se esperaba \"{\"");
                  										      ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  										    }
break;
case 26:
//#line 83 "gramatica.y"
{ logger.addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");
                  							 ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  							}
break;
case 27:
//#line 86 "gramatica.y"
{ logger.addError(lex.linea,"Se esperaba una sentencia");
                  								      ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  								    }
break;
case 28:
//#line 89 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba una sentencia");
		  						    ambito=ambito.substring(0,ambito.lastIndexOf("@"));
		  						   }
break;
case 29:
//#line 94 "gramatica.y"
{}
break;
case 30:
//#line 95 "gramatica.y"
{}
break;
case 31:
//#line 96 "gramatica.y"
{}
break;
case 32:
//#line 97 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 33:
//#line 98 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 34:
//#line 99 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 35:
//#line 100 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 36:
//#line 101 "gramatica.y"
{logger.addError(lex.linea,"Se esperaban como maximo 3 parametros");}
break;
case 37:
//#line 104 "gramatica.y"
{}
break;
case 38:
//#line 105 "gramatica.y"
{}
break;
case 39:
//#line 106 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba tipo");}
break;
case 40:
//#line 107 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba tipo");}
break;
case 41:
//#line 108 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba identificador");}
break;
case 42:
//#line 111 "gramatica.y"
{ logger.addEvent(lex.linea,"Se encontró una sentencia de asignación");
			     yyval = val_peek(1);
			   }
break;
case 43:
//#line 114 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\"");}
break;
case 44:
//#line 115 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia de seleccion"); }
break;
case 45:
//#line 116 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia de salida");}
break;
case 46:
//#line 117 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\"");}
break;
case 47:
//#line 118 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia de llamada"); }
break;
case 48:
//#line 119 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\"");}
break;
case 49:
//#line 120 "gramatica.y"
{logger.addEvent(lex.linea,"Se encontró una sentencia de control"); }
break;
case 50:
//#line 123 "gramatica.y"
{
				 val_peek(2).obj = new Hoja(val_peek(2).sval);
				 yyval = new ParserVal(new Asignacion((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));

			       }
break;
case 51:
//#line 128 "gramatica.y"
{logger.addError(lex.linea,"Se encontró == en lugar de =");}
break;
case 52:
//#line 129 "gramatica.y"
{logger.addError(lex.linea,"Asignacion mal escrita");}
break;
case 53:
//#line 130 "gramatica.y"
{logger.addError(lex.linea,"Asignacion mal escrita");}
break;
case 54:
//#line 133 "gramatica.y"
{
    				    yyval = new ParserVal(new Suma((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));
    				  }
break;
case 55:
//#line 136 "gramatica.y"
{
          			    yyval = new ParserVal(new Resta((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));
          			  }
break;
case 56:
//#line 139 "gramatica.y"
{
          	      yyval = val_peek(0);
          	    }
break;
case 57:
//#line 144 "gramatica.y"
{
			       yyval = new ParserVal(new Division((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));
			     }
break;
case 58:
//#line 147 "gramatica.y"
{
        		       yyval = new ParserVal(new Multiplicacion((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));
        		     }
break;
case 59:
//#line 150 "gramatica.y"
{
                   yyval = val_peek(0);
                 }
break;
case 60:
//#line 155 "gramatica.y"
{ yyval = new ParserVal(new Hoja(val_peek(0).sval));}
break;
case 61:
//#line 156 "gramatica.y"
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
//#line 167 "gramatica.y"
{ yyval = new ParserVal(new Hoja(val_peek(0).sval)); }
break;
case 63:
//#line 168 "gramatica.y"
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
case 64:
//#line 181 "gramatica.y"
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
case 65:
//#line 198 "gramatica.y"
{
									  CuerpoIf aux = new CuerpoIf((Nodo)val_peek(1).obj,null);
									  yyval = new ParserVal(new If((Nodo)val_peek(3).obj, aux));
 									}
break;
case 66:
//#line 202 "gramatica.y"
{
          											       CuerpoIf aux = new CuerpoIf((Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);
												       yyval = new ParserVal(new If((Nodo)val_peek(5).obj, aux));
          											     }
break;
case 67:
//#line 206 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba ELSE");}
break;
case 68:
//#line 207 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba END_IF");}
break;
case 69:
//#line 208 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba END_IF");}
break;
case 70:
//#line 209 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba THEN");}
break;
case 71:
//#line 210 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba THEN");}
break;
case 72:
//#line 211 "gramatica.y"
{logger.addError(lex.linea,"No se encontraron sentencias ejecutables");}
break;
case 73:
//#line 212 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 74:
//#line 213 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del ELSE");}
break;
case 75:
//#line 214 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 76:
//#line 215 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 77:
//#line 218 "gramatica.y"
{  }
break;
case 78:
//#line 219 "gramatica.y"
{ logger.addError(lex.linea, "Falta parentesis de abertura en la condicion"); }
break;
case 79:
//#line 220 "gramatica.y"
{ logger.addError(lex.linea, "Falta parentesis de cierre en la condicion"); }
break;
case 80:
//#line 221 "gramatica.y"
{ logger.addError(lex.linea, "Faltan ambos parentesis en la condicion"); }
break;
case 81:
//#line 224 "gramatica.y"
{
					      Nodo aux = (Nodo)val_peek(1).obj;
					      aux.izquierdo = (Nodo)val_peek(2).obj;
					      aux.derecho = (Nodo)val_peek(0).obj;
					      yyval = val_peek(1);
					    }
break;
case 82:
//#line 230 "gramatica.y"
{logger.addError(lex.linea,"Condicion mal escrita");}
break;
case 83:
//#line 231 "gramatica.y"
{}
break;
case 84:
//#line 234 "gramatica.y"
{ yyval = new ParserVal(new Menor(null,null));}
break;
case 85:
//#line 235 "gramatica.y"
{ yyval = new ParserVal(new Mayor(null,null));}
break;
case 86:
//#line 236 "gramatica.y"
{ yyval = new ParserVal(new Igual(null,null));}
break;
case 87:
//#line 237 "gramatica.y"
{ yyval = new ParserVal(new MayorIgual(null,null));}
break;
case 88:
//#line 238 "gramatica.y"
{ yyval = new ParserVal(new MenorIgual(null,null));}
break;
case 89:
//#line 239 "gramatica.y"
{ yyval = new ParserVal(new Distinto(null,null));}
break;
case 90:
//#line 242 "gramatica.y"
{
				    yyval = new ParserVal(new Then((Nodo)val_peek(0).obj));
				  }
break;
case 91:
//#line 245 "gramatica.y"
{
		       				    yyval = new ParserVal(new Then((Nodo)val_peek(2).obj));
		       				  }
break;
case 92:
//#line 250 "gramatica.y"
{
				    yyval = new ParserVal(new Else((Nodo)val_peek(0).obj));
				  }
break;
case 93:
//#line 253 "gramatica.y"
{
		       				    yyval = new ParserVal(new Else((Nodo)val_peek(2).obj));
		       				  }
break;
case 94:
//#line 258 "gramatica.y"
{
				   yyval = new ParserVal(new Bloque((Nodo)val_peek(0).obj,null));
				 }
break;
case 95:
//#line 261 "gramatica.y"
{
		       				    yyval = new ParserVal(new Bloque((Nodo)val_peek(2).obj,null));
		       				  }
break;
case 96:
//#line 266 "gramatica.y"
{}
break;
case 97:
//#line 267 "gramatica.y"
{}
break;
case 98:
//#line 270 "gramatica.y"
{}
break;
case 99:
//#line 271 "gramatica.y"
{}
break;
case 100:
//#line 272 "gramatica.y"
{}
break;
case 101:
//#line 273 "gramatica.y"
{}
break;
case 102:
//#line 274 "gramatica.y"
{}
break;
case 103:
//#line 275 "gramatica.y"
{}
break;
case 104:
//#line 276 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba una cadena");}
break;
case 105:
//#line 277 "gramatica.y"
{logger.addError(lex.linea,"Sentencia OUT mal escrita");}
break;
case 106:
//#line 280 "gramatica.y"
{}
break;
case 107:
//#line 281 "gramatica.y"
{}
break;
case 108:
//#line 284 "gramatica.y"
{}
break;
case 109:
//#line 285 "gramatica.y"
{}
break;
case 110:
//#line 286 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 111:
//#line 287 "gramatica.y"
{}
break;
case 112:
//#line 288 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 113:
//#line 289 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 114:
//#line 290 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \",\"");}
break;
case 115:
//#line 291 "gramatica.y"
{logger.addError(lex.linea,"Se esperaban como maximo 3 parametros");}
break;
case 116:
//#line 294 "gramatica.y"
{
			String id_for = val_peek(11).sval;
			String id_comp = val_peek(7).sval;
			if(!id_for.equals(id_comp)) {
				logger.addError(lex.linea,"La variable de inicialización no es igual a la de condición");
			}
			/*Creando la parte de la inicializacion de codigo*/
			Asignacion inicializacion = new Asignacion(new Hoja(val_peek(11).sval),new Hoja(val_peek(9).sval));

			/*Creando la parte del incremento*/
			Nodo incremento = (Nodo)val_peek(3).obj;
			incremento.izquierdo = new Hoja(val_peek(11).sval);
			incremento.derecho = new Hoja(val_peek(2).sval);
			Asignacion asig = new Asignacion(new Hoja(val_peek(11).sval),incremento);

			/*Creando la parte de la condicion*/
			Nodo comp = (Nodo) val_peek(6).obj;
			comp.izquierdo = new Hoja(val_peek(7).sval);
			comp.derecho = (Nodo) val_peek(5).obj;

			/*Agregandolo*/
			CuerpoFor cuerpoFor = new CuerpoFor((Nodo)val_peek(0).obj,asig);
			For forsito = new For(comp,cuerpoFor);
			Bloque bloque = new Bloque(inicializacion,forsito);
			yyval = new ParserVal(bloque);

		}
break;
case 117:
//#line 321 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\" pero se recibio "+ val_peek(7).sval);}
break;
case 118:
//#line 322 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \";\" pero se recibio "+ val_peek(3).sval);}
break;
case 119:
//#line 323 "gramatica.y"
{logger.addError(lex.linea,"Se esperaban \";\" en la sentencia FOR");}
break;
case 120:
//#line 324 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba UP o DOWN en la sentencia FOR");}
break;
case 121:
//#line 325 "gramatica.y"
{logger.addError(lex.linea,"Falta inicialización en la sentencia FOR");}
break;
case 122:
//#line 326 "gramatica.y"
{logger.addError(lex.linea,"Falta condicion en la sentencia FOR");}
break;
case 123:
//#line 327 "gramatica.y"
{logger.addError(lex.linea,"Falta incremento en la sentencia FOR");}
break;
case 124:
//#line 328 "gramatica.y"
{logger.addError(lex.linea,"Se esperaba \")\" en la sentencia FOR");}
break;
case 125:
//#line 329 "gramatica.y"
{logger.addError(lex.linea,"No se permite declaraciones dentro del FOR");}
break;
case 126:
//#line 332 "gramatica.y"
{
		yyval = new ParserVal(new Suma(null,null));
	       }
break;
case 127:
//#line 335 "gramatica.y"
{
          	   yyval = new ParserVal(new Resta(null,null));
	 	 }
break;
//#line 1406 "Parser.java"
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
