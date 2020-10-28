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
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   20,   20,   20,   20,   23,   23,   23,   23,   23,   23,
   21,   21,   22,   22,   25,   25,   24,   24,   15,   15,
   15,   16,   16,   26,   26,   26,   26,   26,   26,   26,
   26,   17,   17,   17,   17,   17,   17,   17,   17,   17,
   17,   27,   27,
};
final static short yylen[] = {                            2,
    1,    1,    2,    1,    2,    1,    1,    2,    2,    1,
    1,    2,    4,    1,    2,    2,    1,    1,    1,    3,
    2,   10,    9,    7,    9,    6,    9,    8,    1,    3,
    5,    2,    3,    4,    4,    6,    2,    3,    2,    1,
    3,    2,    1,    1,    2,    1,    2,    1,    1,    3,
    3,    3,    3,    3,    3,    1,    3,    3,    1,    1,
    1,    1,    2,    2,    7,    9,    8,    6,    7,    8,
    9,    6,    8,    6,    7,    9,    9,    9,    7,    9,
    3,    2,    2,    2,    1,    1,    1,    1,    1,    1,
    1,    3,    1,    3,    1,    3,    1,    2,    4,    3,
    2,    4,    3,    1,    3,    2,    5,    3,    4,    4,
    6,   14,   13,   13,   12,   13,   10,   10,   11,   13,
   14,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   17,   18,    0,    0,    0,    2,    0,
    1,    0,    6,    7,    0,   10,    0,   14,    0,    0,
   44,    0,    0,   49,    0,    3,    8,    0,   15,    0,
  101,    0,    0,   21,    0,    0,    0,    0,    0,    5,
    9,   16,    0,    0,   42,   45,   47,   60,   61,   62,
    0,    0,    0,   59,    0,   88,   89,   87,   90,   85,
   86,    0,    0,    0,    0,  100,    0,    0,   53,    0,
   20,    0,  103,    0,    0,    0,   40,    0,    0,    0,
    0,   63,   64,    0,    0,    0,    0,    0,   82,    0,
    0,    0,    0,   99,    0,    0,    0,    0,  102,    0,
   39,    0,    0,    0,   37,    0,    0,    0,    0,    0,
   57,   58,    0,    0,    0,    0,    0,   91,    0,    0,
    0,    0,    0,  108,    0,    0,   41,   38,    0,    0,
    0,    0,    0,    0,   33,    0,    0,    0,    0,   68,
    0,   74,    0,    0,    0,   72,    0,    0,    0,  110,
  109,    0,    0,   26,    0,    0,    0,   34,   35,    0,
   79,   98,   92,    0,   93,    0,    0,   75,    0,    0,
   65,    0,    0,    0,  122,  123,    0,    0,    0,    0,
    0,    0,   24,    0,    0,    0,   70,    0,    0,    0,
    0,   67,   73,    0,    0,    0,    0,  111,   28,    0,
    0,    0,   36,   80,   94,   78,   77,   76,   71,   66,
    0,    0,    0,    0,    0,   23,   27,    0,   25,    0,
    0,    0,    0,    0,    0,   95,  118,  117,   22,    0,
    0,    0,    0,  119,    0,    0,    0,  115,    0,    0,
    0,   96,  113,  116,    0,  120,  114,  121,  112,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,  226,   15,   16,   17,   18,   52,   19,
   80,   81,   20,   21,   22,   23,   24,   53,   54,   63,
  119,  166,   64,  138,  227,   74,  178,
};
final static short yysindex[] = {                       133,
    3,  -25,   -3,    0,    0,   -2, -242,  -40,    0,    0,
    0,  524,    0,    0,   13,    0, -224,    0,   11,   27,
    0,   36,   38,    0,    9,    0,    0,  -18,    0,  177,
    0,  -34, -169,    0,  -18,  147, -159,  -30,   16,    0,
    0,    0,   54,  -36,    0,    0,    0,    0,    0,    0,
  -74,   46,   -6,    0,   76,    0,    0,    0,    0,    0,
    0,  -29,  -35,  -18,   78,    0,  383,   46,    0,   46,
    0,  -10,    0,   81,  -18, -109,    0, -111, -141,   97,
   79,    0,    0,  -18,  -18,  -18,  -18, -113,    0,  -18,
  -78,  -71,   46,    0, -105,  -18,   14, -112,    0,   46,
    0, -174,  109,  524,    0, -110, -128,  137,   -6,   -6,
    0,    0,  -78,   46,  112,  -38,  380,    0, -160,  429,
  -77,   22,   24,    0,  -96,   21,    0,    0,  -80,   72,
  156,  524,  220, -128,    0,  -54,  380,   94,  346,    0,
  -51,    0,  -52,  -95,  346,    0,  352,  -68,  -58,    0,
    0,  -48,  120,    0,  -53,  185, -128,    0,    0,  346,
    0,    0,    0,  380,    0,   52,  391,    0,  112,  446,
    0,   57,   58,  -18,    0,    0,  352,   51,   88,   75,
  495,  458,    0,  106,  115,  251,    0,  122,  124,  131,
 -217,    0,    0,   71,  -18,  355,  384,    0,    0,  301,
  507,  302,    0,    0,    0,    0,    0,    0,    0,    0,
  -58,  155,  -19,  483,  483,    0,    0,  304,    0,  157,
  395, -124,  483,  165,  380,    0,    0,    0,    0,  411,
  483,  416,  186,    0,  417,  335,  483,    0,  483,  281,
  483,    0,    0,    0,  470,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,   93,    0,    0,
    0,   17,    0,    0,  231,    0,    0,    0,    0,  245,
    0,  269,  299,    0,   73,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  107,    0,    0,    0,    0,    0,    0,    0,
    0,  121,    1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  151,    0,  175,
    0,  420,    0,    0,    0,    0,    0,    0,    0,    0,
  421,    0,    0,    0,    0,    0,    0,    0,    0,  -33,
    0,    0,  -32,    0,    0,    0,   97,    0,    0,  211,
    0,    0,    0,    0,    0,    0,    0,  422,   25,   49,
    0,    0,    0,  -23,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  423,    0,    0,    0,    0,
    0,    0,  424,    0,    0,    0,  341,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  323,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  431,
    0,    0,    0,  433,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    8,    0,  -91,  506,    0,    0,  677,   18,  509,    0,
    0,  443,    0,    0,    0,    0,    0,  130,  260,    0,
  -42,  -17,   -8,  -90, -135,    0,  -93,
};
final static int YYTABLESIZE=834;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         38,
   56,   38,   26,   37,   78,   92,   66,   84,   83,   28,
   73,  104,  132,   84,   30,   85,    4,   81,   29,   40,
   36,  223,   36,   84,   54,   85,   51,  164,  143,   34,
   60,   42,   61,   98,   43,   87,   32,   33,  209,  222,
   86,   56,  210,   56,  117,   56,  162,   25,   55,  121,
   44,  117,   37,   90,   71,  179,   29,  125,   96,   56,
   56,   27,   56,   28,  152,   54,   84,   54,   85,   54,
  136,   41,   19,  186,   27,  188,   28,  144,  190,  228,
  148,  127,  149,   54,   54,   45,   54,  234,   84,   55,
   85,   55,   19,   55,   46,  238,   47,  128,  139,  140,
  212,  243,   67,  244,  246,  247,   12,   55,   55,  249,
   55,  130,   25,   84,   75,   85,   88,  220,   94,  224,
   52,   99,  107,   56,  189,   56,  172,  173,  233,  211,
  105,   19,    9,   19,  236,    4,    5,  106,  174,  156,
   76,    4,  185,   77,  113,  175,  176,   54,  232,   54,
   51,   19,  191,  248,    4,    5,  103,  131,   29,  126,
  169,    2,  101,  170,  171,   12,    3,  122,  195,  129,
    6,   55,   28,   55,   50,  150,  116,  115,    2,   52,
  134,  145,  146,    3,  115,    2,  120,    6,  200,  202,
    3,   51,  153,  116,    6,   19,  154,   19,   82,   83,
  116,  175,  176,  177,  160,  161,  167,  168,  218,   51,
   13,  175,  176,  109,  110,   19,  155,   19,  163,  182,
   25,   51,   91,  180,   84,   83,   89,    4,    5,   12,
   11,   12,   76,   50,   81,   77,   60,   35,   61,   35,
   65,   72,  181,   52,   43,   52,   56,   57,   58,   59,
  175,  176,   31,   48,   49,   50,   56,   56,   56,   56,
   56,   97,   56,  157,   56,   56,   56,   56,   46,   13,
   56,   56,   56,   51,   25,   51,   56,   56,   56,   56,
   54,   54,   54,   54,   54,  124,   54,   25,   54,   54,
   54,   54,  151,  147,   54,   54,   54,   50,   48,   50,
   54,   54,   54,   54,   55,   55,   55,   55,   55,  183,
   55,  187,   55,   55,   55,   55,  192,  193,   55,   55,
   55,  245,   69,  196,   55,   55,   55,   55,   19,   19,
  198,   19,   19,   13,   19,   13,   19,   19,   19,   19,
  175,  176,    4,    5,   19,  111,  112,   76,   19,   19,
   77,   19,   19,   11,   19,   11,   19,   19,   19,   19,
  197,  203,   12,   12,   19,   12,   12,   43,   12,   43,
   12,   12,   12,   12,  204,  205,   52,   52,   12,   52,
   52,  206,   52,  207,   52,   52,   52,   52,    1,    2,
  208,   46,   52,   46,    3,  214,    4,    5,    6,    7,
    4,    5,   69,  225,    8,   76,   51,   51,   77,   51,
   51,   60,   51,   61,   51,   51,   51,   51,   48,   49,
   50,   48,   51,   48,  215,  216,  219,  221,  229,  230,
   50,   50,   55,   50,   50,  231,   50,  235,   50,   50,
   50,   50,   60,   95,   61,   69,   50,   69,   48,   49,
   50,  237,   56,   57,   58,   59,  239,  241,  240,  242,
  104,   29,   32,  105,   30,   97,   13,   13,  164,   13,
   13,  107,   13,   31,   13,   13,   13,   13,    0,    0,
    0,    0,   13,    4,    5,    0,   11,   11,   76,   11,
   11,   77,   11,    0,   11,   11,   11,   11,    0,    0,
   43,   43,   11,   43,   43,   14,   43,    0,   43,   43,
   43,   43,    0,  117,    0,    0,   43,   14,    0,    0,
    0,    0,    0,  108,   46,   46,    0,   46,   46,    0,
   46,    0,   46,   46,   46,   46,  115,    2,   62,    0,
   46,    0,    3,   68,   70,    0,    6,    0,    0,  133,
  135,  117,  116,    0,   48,   48,    0,   48,   48,    0,
   48,    0,   48,   48,   48,   48,    0,    0,  164,    0,
   48,    0,   93,    0,    0,  158,  159,    0,   69,   69,
  201,   69,   69,  100,   69,    0,   69,   69,   69,   69,
    0,    0,  225,    0,   69,    0,  118,  118,  114,  184,
    0,  115,    2,    0,  123,  225,    0,    3,    0,   14,
    0,    6,    0,    0,    0,    0,    0,  116,  118,  199,
    0,    0,  137,    0,    0,  118,    0,   56,   57,   58,
   59,  217,    0,    0,    0,  115,    2,   14,    0,    0,
    0,    3,  137,    0,  165,    6,  141,    2,    0,  165,
  165,  116,    3,    0,    4,    5,    6,    7,   56,   57,
   58,   59,    8,    0,    0,  165,    0,    0,    0,  137,
    0,    0,  118,    0,    0,  165,    0,    0,    0,    0,
    0,    0,  194,    0,  141,    2,   14,   14,  142,    0,
    3,    0,    4,    5,    6,    7,    0,    0,    0,    0,
    8,  141,    2,  213,    0,    0,   14,    3,    0,    4,
    5,    6,    7,   39,    2,    0,    0,    8,    0,    3,
   79,    4,    5,    6,    7,  141,    2,    0,    0,    8,
  137,    3,    0,    4,    5,    6,    7,    0,  115,    2,
    0,    8,    0,    0,    3,    0,    0,    0,    6,    0,
   39,    2,  102,    0,  116,    0,    3,   79,    4,    5,
    6,    7,   39,    2,    0,    0,    8,    0,    3,    0,
    4,    5,    6,    7,    0,    0,    0,    0,    8,   39,
    2,    0,    0,   79,   79,    3,    0,    4,    5,    6,
    7,    0,    0,    0,    0,    8,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   79,
   79,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   79,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,    0,   44,   41,   41,   41,   41,   41,   61,
   41,  123,  123,   43,   40,   45,    0,   41,    1,   12,
   61,   41,   61,   43,    0,   45,   45,  123,  120,  272,
   60,  256,   62,   44,   17,   42,   40,   40,  256,   59,
   47,   41,  260,   43,  123,   45,  137,  272,    0,   92,
   40,  123,   44,   62,   37,  149,   39,   44,   67,   59,
   60,   59,   62,   61,   44,   41,   43,   43,   45,   45,
  113,   59,    0,  164,   59,  167,   61,  120,  170,  215,
   59,  256,   59,   59,   60,   59,   62,  223,   43,   41,
   45,   43,    0,   45,   59,  231,   59,  272,  259,  260,
  194,  237,  272,  239,  240,  241,    0,   59,   60,  245,
   62,  104,  272,   43,   61,   45,   41,  211,   41,  213,
    0,   41,   44,  123,  167,  125,  144,  145,  222,   59,
  272,   59,    0,   61,  225,  264,  265,   41,  147,  132,
  269,  125,  160,  272,  258,  270,  271,  123,  273,  125,
    0,   59,  170,  245,  264,  265,  268,  268,  141,  272,
  256,  257,  272,  259,  260,   59,  262,  273,  177,   61,
  266,  123,   61,  125,    0,  272,  272,  256,  257,   59,
   44,  259,  260,  262,  256,  257,  258,  266,  181,  182,
  262,   45,  273,  272,  266,  123,  125,  125,  273,  274,
  272,  270,  271,  272,  259,  260,  259,  260,  201,   59,
    0,  270,  271,   84,   85,  123,   61,  125,  125,  273,
  272,   45,  258,  272,  258,  258,  256,  264,  265,  123,
    0,  125,  269,   59,  258,  272,   60,  278,   62,  278,
  275,  272,  123,  123,    0,  125,  276,  277,  278,  279,
  270,  271,  256,  272,  273,  274,  256,  257,  258,  259,
  260,  272,  262,   44,  264,  265,  266,  267,    0,   59,
  270,  271,  272,  123,  272,  125,  276,  277,  278,  279,
  256,  257,  258,  259,  260,  272,  262,  272,  264,  265,
  266,  267,  272,  272,  270,  271,  272,  123,    0,  125,
  276,  277,  278,  279,  256,  257,  258,  259,  260,  125,
  262,  260,  264,  265,  266,  267,  260,  260,  270,  271,
  272,   41,    0,  273,  276,  277,  278,  279,  256,  257,
  256,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  270,  271,  264,  265,  272,   86,   87,  269,  256,  257,
  272,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  273,  256,  256,  257,  272,  259,  260,  123,  262,  125,
  264,  265,  266,  267,  260,  125,  256,  257,  272,  259,
  260,  260,  262,  260,  264,  265,  266,  267,  256,  257,
  260,  123,  272,  125,  262,   41,  264,  265,  266,  267,
  264,  265,  256,  123,  272,  269,  256,  257,  272,  259,
  260,   60,  262,   62,  264,  265,  266,  267,  272,  273,
  274,  123,  272,  125,   41,  125,  125,  273,  125,  273,
  256,  257,  256,  259,  260,   41,  262,  273,  264,  265,
  266,  267,   60,   61,   62,  123,  272,  125,  272,  273,
  274,   41,  276,  277,  278,  279,   41,   41,  273,  125,
   41,   41,   41,   41,   41,  125,  256,  257,  123,  259,
  260,   41,  262,   41,  264,  265,  266,  267,   -1,   -1,
   -1,   -1,  272,  264,  265,   -1,  256,  257,  269,  259,
  260,  272,  262,   -1,  264,  265,  266,  267,   -1,   -1,
  256,  257,  272,  259,  260,    0,  262,   -1,  264,  265,
  266,  267,   -1,  123,   -1,   -1,  272,   12,   -1,   -1,
   -1,   -1,   -1,   81,  256,  257,   -1,  259,  260,   -1,
  262,   -1,  264,  265,  266,  267,  256,  257,   30,   -1,
  272,   -1,  262,   35,   36,   -1,  266,   -1,   -1,  107,
  108,  123,  272,   -1,  256,  257,   -1,  259,  260,   -1,
  262,   -1,  264,  265,  266,  267,   -1,   -1,  123,   -1,
  272,   -1,   64,   -1,   -1,  133,  134,   -1,  256,  257,
  123,  259,  260,   75,  262,   -1,  264,  265,  266,  267,
   -1,   -1,  123,   -1,  272,   -1,   91,   92,   90,  157,
   -1,  256,  257,   -1,   96,  123,   -1,  262,   -1,  104,
   -1,  266,   -1,   -1,   -1,   -1,   -1,  272,  113,  125,
   -1,   -1,  117,   -1,   -1,  120,   -1,  276,  277,  278,
  279,  125,   -1,   -1,   -1,  256,  257,  132,   -1,   -1,
   -1,  262,  137,   -1,  139,  266,  256,  257,   -1,  144,
  145,  272,  262,   -1,  264,  265,  266,  267,  276,  277,
  278,  279,  272,   -1,   -1,  160,   -1,   -1,   -1,  164,
   -1,   -1,  167,   -1,   -1,  170,   -1,   -1,   -1,   -1,
   -1,   -1,  174,   -1,  256,  257,  181,  182,  260,   -1,
  262,   -1,  264,  265,  266,  267,   -1,   -1,   -1,   -1,
  272,  256,  257,  195,   -1,   -1,  201,  262,   -1,  264,
  265,  266,  267,  256,  257,   -1,   -1,  272,   -1,  262,
   44,  264,  265,  266,  267,  256,  257,   -1,   -1,  272,
  225,  262,   -1,  264,  265,  266,  267,   -1,  256,  257,
   -1,  272,   -1,   -1,  262,   -1,   -1,   -1,  266,   -1,
  256,  257,   76,   -1,  272,   -1,  262,   81,  264,  265,
  266,  267,  256,  257,   -1,   -1,  272,   -1,  262,   -1,
  264,  265,  266,  267,   -1,   -1,   -1,   -1,  272,  256,
  257,   -1,   -1,  107,  108,  262,   -1,  264,  265,  266,
  267,   -1,   -1,   -1,   -1,  272,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  133,
  134,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  157,
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

//#line 336 "./resources/gramatica.y"

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


//#line 606 "Parser.java"
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
//#line 18 "./resources/gramatica.y"
{ raiz = (Nodo)val_peek(0).obj; }
break;
case 2:
//#line 19 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Sin sentencias");}
break;
case 3:
//#line 20 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Sin sentencias validas");}
break;
case 4:
//#line 23 "./resources/gramatica.y"
{ yyval = new ParserVal(new Bloque((Nodo)val_peek(0).obj,null));}
break;
case 5:
//#line 24 "./resources/gramatica.y"
{ yyval = new ParserVal(new Bloque((Nodo)val_peek(1).obj,(Nodo)val_peek(0).obj));}
break;
case 6:
//#line 28 "./resources/gramatica.y"
{ }
break;
case 7:
//#line 29 "./resources/gramatica.y"
{ yyval = val_peek(0); }
break;
case 8:
//#line 30 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Sentencia mal escrita");}
break;
case 9:
//#line 33 "./resources/gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia declarativa de variable");}
break;
case 10:
//#line 34 "./resources/gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia declarativa de procedimiento");}
break;
case 11:
//#line 35 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
break;
case 12:
//#line 38 "./resources/gramatica.y"
{
		for (String id : (ArrayList<String>)(val_peek(0).obj)){
                	HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(id);
                	aux.put("Uso","variable");
 		    	lex.tablaDeSimbolos.put(id+ambito,aux);

                  }
	     }
break;
case 13:
//#line 46 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Asignacion en la declaración");}
break;
case 14:
//#line 47 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba un tipo");}
break;
case 15:
//#line 48 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Tipo no valido");}
break;
case 16:
//#line 49 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba un identificador");}
break;
case 17:
//#line 51 "./resources/gramatica.y"
{}
break;
case 18:
//#line 52 "./resources/gramatica.y"
{}
break;
case 19:
//#line 55 "./resources/gramatica.y"
{
			ArrayList<String> aux=new ArrayList<String>();
			aux.add(val_peek(0).sval);
			yyval=new ParserVal(aux);
		}
break;
case 20:
//#line 60 "./resources/gramatica.y"
{
                	ArrayList<String> aux = (ArrayList<String>)(val_peek(0).obj);
                	aux.add(val_peek(2).sval);
                	yyval=val_peek(0);
                }
break;
case 21:
//#line 67 "./resources/gramatica.y"
{HashMap<String, Object> aux=lex.tablaDeSimbolos.remove(val_peek(0).sval);
			aux.put("Uso","procedimiento");
                        lex.tablaDeSimbolos.put(val_peek(0).sval+ambito,aux);
			ambito+="@"+val_peek(0).sval;
			}
break;
case 22:
//#line 73 "./resources/gramatica.y"
{
		    	ambito=ambito.substring(0,ambito.lastIndexOf("@"));
		   }
break;
case 23:
//#line 76 "./resources/gramatica.y"
{ambito=ambito.substring(0,ambito.lastIndexOf("@"));}
break;
case 24:
//#line 77 "./resources/gramatica.y"
{ Logger.getInstance().addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");
                  									   ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  									 }
break;
case 25:
//#line 80 "./resources/gramatica.y"
{ Logger.getInstance().addError(lex.linea,"Se esperaba \"{\"");
                  										      ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  										    }
break;
case 26:
//#line 83 "./resources/gramatica.y"
{ Logger.getInstance().addError(lex.linea,"Se esperaba NI=CTE_INT en la declaracion de PROC");
                  							 ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  							}
break;
case 27:
//#line 86 "./resources/gramatica.y"
{ Logger.getInstance().addError(lex.linea,"Se esperaba una sentencia");
                  								      ambito=ambito.substring(0,ambito.lastIndexOf("@"));
                  								    }
break;
case 28:
//#line 89 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba una sentencia");
		  						    ambito=ambito.substring(0,ambito.lastIndexOf("@"));
		  						   }
break;
case 29:
//#line 94 "./resources/gramatica.y"
{}
break;
case 30:
//#line 95 "./resources/gramatica.y"
{}
break;
case 31:
//#line 96 "./resources/gramatica.y"
{}
break;
case 32:
//#line 97 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 33:
//#line 98 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 34:
//#line 99 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 35:
//#line 100 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 36:
//#line 101 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaban como maximo 3 parametros");}
break;
case 37:
//#line 104 "./resources/gramatica.y"
{}
break;
case 38:
//#line 105 "./resources/gramatica.y"
{}
break;
case 39:
//#line 106 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba tipo");}
break;
case 40:
//#line 107 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba tipo");}
break;
case 41:
//#line 108 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba identificador");}
break;
case 42:
//#line 111 "./resources/gramatica.y"
{ Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de asignación");
			     yyval = val_peek(1);
			   }
break;
case 43:
//#line 114 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
break;
case 44:
//#line 115 "./resources/gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de seleccion"); }
break;
case 45:
//#line 116 "./resources/gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de salida");}
break;
case 46:
//#line 117 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
break;
case 47:
//#line 118 "./resources/gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de llamada"); }
break;
case 48:
//#line 119 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \";\"");}
break;
case 49:
//#line 120 "./resources/gramatica.y"
{Logger.getInstance().addEvent(lex.linea,"Se encontró una sentencia de control"); }
break;
case 50:
//#line 123 "./resources/gramatica.y"
{
				 val_peek(2).obj = new Hoja(val_peek(2).sval);
				 yyval = new ParserVal(new Asignacion((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));

			       }
break;
case 51:
//#line 128 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se encontró == en lugar de =");}
break;
case 52:
//#line 129 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Asignacion mal escrita");}
break;
case 53:
//#line 130 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Asignacion mal escrita");}
break;
case 54:
//#line 133 "./resources/gramatica.y"
{
    				    yyval = new ParserVal(new Suma((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));
    				  }
break;
case 55:
//#line 136 "./resources/gramatica.y"
{
          			    yyval = new ParserVal(new Resta((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));
          			  }
break;
case 56:
//#line 139 "./resources/gramatica.y"
{
          	      yyval = val_peek(0);
          	    }
break;
case 57:
//#line 144 "./resources/gramatica.y"
{
			       yyval = new ParserVal(new Division((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));
			     }
break;
case 58:
//#line 147 "./resources/gramatica.y"
{
        		       yyval = new ParserVal(new Multiplicacion((Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj));
        		     }
break;
case 59:
//#line 150 "./resources/gramatica.y"
{
                   yyval = val_peek(0);
                 }
break;
case 60:
//#line 155 "./resources/gramatica.y"
{ yyval = new ParserVal(new Hoja(val_peek(0).sval));}
break;
case 61:
//#line 156 "./resources/gramatica.y"
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
case 62:
//#line 167 "./resources/gramatica.y"
{ yyval = new ParserVal(new Hoja(val_peek(0).sval)); }
break;
case 63:
//#line 168 "./resources/gramatica.y"
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
//#line 181 "./resources/gramatica.y"
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
//#line 198 "./resources/gramatica.y"
{
									  CuerpoIf aux = new CuerpoIf((Nodo)val_peek(1).obj,null);
									  yyval = new ParserVal(new If((Nodo)val_peek(4).obj, aux));
 									}
break;
case 66:
//#line 202 "./resources/gramatica.y"
{
          											       CuerpoIf aux = new CuerpoIf((Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);
												       yyval = new ParserVal(new If((Nodo)val_peek(6).obj, aux));
          											     }
break;
case 67:
//#line 206 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba ELSE");}
break;
case 68:
//#line 207 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \")\" luego de la condición"); }
break;
case 69:
//#line 208 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba END_IF");}
break;
case 70:
//#line 209 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \")\" luego de la condición"); }
break;
case 71:
//#line 210 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba END_IF");}
break;
case 72:
//#line 211 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba THEN");}
break;
case 73:
//#line 212 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba THEN");}
break;
case 74:
//#line 213 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"No se encontraron sentencias ejecutables");}
break;
case 75:
//#line 214 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 76:
//#line 215 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del ELSE");}
break;
case 77:
//#line 216 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 78:
//#line 217 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del IF");}
break;
case 79:
//#line 218 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
break;
case 80:
//#line 219 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
break;
case 81:
//#line 222 "./resources/gramatica.y"
{
					      Nodo aux = (Nodo)val_peek(1).obj;
					      aux.izquierdo = (Nodo)val_peek(2).obj;
					      aux.derecho = (Nodo)val_peek(0).obj;
					      yyval = val_peek(1);
					    }
break;
case 82:
//#line 228 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
break;
case 83:
//#line 229 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
break;
case 84:
//#line 230 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Condicion mal escrita");}
break;
case 85:
//#line 234 "./resources/gramatica.y"
{ yyval = new ParserVal(new Menor(null,null));}
break;
case 86:
//#line 235 "./resources/gramatica.y"
{ yyval = new ParserVal(new Mayor(null,null));}
break;
case 87:
//#line 236 "./resources/gramatica.y"
{ yyval = new ParserVal(new Igual(null,null));}
break;
case 88:
//#line 237 "./resources/gramatica.y"
{ yyval = new ParserVal(new MayorIgual(null,null));}
break;
case 89:
//#line 238 "./resources/gramatica.y"
{ yyval = new ParserVal(new MenorIgual(null,null));}
break;
case 90:
//#line 239 "./resources/gramatica.y"
{ yyval = new ParserVal(new Distinto(null,null));}
break;
case 91:
//#line 242 "./resources/gramatica.y"
{
				    yyval = new ParserVal(new Then((Nodo)val_peek(0).obj));
				  }
break;
case 92:
//#line 245 "./resources/gramatica.y"
{
		       				    yyval = new ParserVal(new Then((Nodo)val_peek(2).obj));
		       				  }
break;
case 93:
//#line 250 "./resources/gramatica.y"
{
				    yyval = new ParserVal(new Else((Nodo)val_peek(0).obj));
				  }
break;
case 94:
//#line 253 "./resources/gramatica.y"
{
		       				    yyval = new ParserVal(new Else((Nodo)val_peek(2).obj));
		       				  }
break;
case 95:
//#line 258 "./resources/gramatica.y"
{
				   yyval = new ParserVal(new Bloque((Nodo)val_peek(0).obj,null));
				 }
break;
case 96:
//#line 261 "./resources/gramatica.y"
{
		       				    yyval = new ParserVal(new Bloque((Nodo)val_peek(2).obj,null));
		       				  }
break;
case 97:
//#line 266 "./resources/gramatica.y"
{}
break;
case 98:
//#line 267 "./resources/gramatica.y"
{}
break;
case 99:
//#line 270 "./resources/gramatica.y"
{}
break;
case 100:
//#line 271 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba una cadena");}
break;
case 101:
//#line 272 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Sentencia OUT mal escrita");}
break;
case 102:
//#line 275 "./resources/gramatica.y"
{}
break;
case 103:
//#line 276 "./resources/gramatica.y"
{}
break;
case 104:
//#line 279 "./resources/gramatica.y"
{}
break;
case 105:
//#line 280 "./resources/gramatica.y"
{}
break;
case 106:
//#line 281 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 107:
//#line 282 "./resources/gramatica.y"
{}
break;
case 108:
//#line 283 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 109:
//#line 284 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 110:
//#line 285 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \",\"");}
break;
case 111:
//#line 286 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaban como maximo 3 parametros");}
break;
case 112:
//#line 289 "./resources/gramatica.y"
{
			String id_for = val_peek(11).sval;
			String id_comp = val_peek(7).sval;
			if(!id_for.equals(id_comp)) {
				Logger.getInstance().addError(lex.linea,"La variable de inicialización no es igual a la de condición");
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
case 113:
//#line 316 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \";\" pero se recibio "+ val_peek(7).sval);}
break;
case 114:
//#line 317 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \";\" pero se recibio "+ val_peek(3).sval);}
break;
case 115:
//#line 318 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaban \";\" en la sentencia FOR");}
break;
case 116:
//#line 319 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba UP o DOWN en la sentencia FOR");}
break;
case 117:
//#line 320 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Falta inicialización en la sentencia FOR");}
break;
case 118:
//#line 321 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Falta condicion en la sentencia FOR");}
break;
case 119:
//#line 322 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Falta incremento en la sentencia FOR");}
break;
case 120:
//#line 323 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"Se esperaba \")\" en la sentencia FOR");}
break;
case 121:
//#line 324 "./resources/gramatica.y"
{Logger.getInstance().addError(lex.linea,"No se permite declaraciones dentro del FOR");}
break;
case 122:
//#line 327 "./resources/gramatica.y"
{
		yyval = new ParserVal(new Suma(null,null));
	       }
break;
case 123:
//#line 330 "./resources/gramatica.y"
{
          	   yyval = new ParserVal(new Resta(null,null));
	 	 }
break;
//#line 1385 "Parser.java"
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
