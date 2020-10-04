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
public final static short CARACTER_INVALIDO=280;
public final static short END=0;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    1,    1,    1,    2,    2,    3,    3,
    3,    5,    5,    5,    5,    5,    7,    7,    8,    8,
    6,    6,    6,    6,    6,    6,   10,   10,   10,   10,
   10,   10,   10,   11,   11,   11,   11,   11,    4,    4,
    4,    4,    4,    4,    4,    4,   12,   12,   12,   12,
    9,    9,    9,   17,   17,   17,   18,   18,   18,   18,
   18,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   19,   19,
   19,   19,   22,   22,   22,   22,   22,   22,   20,   21,
   23,   23,   23,   14,   14,   14,   15,   15,   15,   24,
   24,   24,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   25,   25,
};
final static short yylen[] = {                            2,
    1,    1,    2,    1,    2,    1,    1,    1,    2,    1,
    1,    2,    4,    1,    2,    2,    1,    1,    1,    3,
   11,   10,    8,   10,    7,   10,    1,    3,    5,    2,
    3,    4,    4,    2,    3,    2,    1,    3,    2,    1,
    1,    2,    1,    2,    1,    1,    3,    3,    3,    3,
    3,    3,    1,    3,    3,    1,    1,    1,    1,    2,
    2,    7,    9,    8,    6,    7,    8,    9,    6,    8,
    6,    7,    9,    9,    9,    7,    9,    3,    3,    2,
    2,    2,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    3,    4,    4,    3,    2,    4,    3,    4,    1,
    3,    5,   14,   13,   13,   12,   13,   10,   10,   11,
   13,   14,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   17,   18,    0,    0,    0,    2,    0,
    1,    0,    7,    8,    0,   10,    0,   14,    0,   41,
    0,    0,   46,    0,    3,    0,   15,    0,   96,    0,
    0,    0,    0,    0,    0,    0,    0,    5,    9,   16,
    0,   39,   42,   44,   57,   58,   59,    0,    0,    0,
   56,    0,   86,   87,   85,   88,   78,   83,   84,    0,
    0,    0,    0,   95,    0,    0,    0,   50,    0,   20,
    0,   98,    0,    0,   60,   61,    0,    0,    0,    0,
    0,   80,    0,    0,    0,    0,   94,    0,    0,    0,
   37,    0,    0,    0,    0,    0,   99,   97,    0,    0,
    0,   54,   55,    0,    0,    0,    0,    0,   91,    0,
   89,    0,    0,    0,    0,   36,    0,    0,    0,   34,
    0,    0,    0,    0,    0,    0,    0,   65,    0,   71,
    0,    0,    0,   69,    0,    0,    0,   38,   35,    0,
    0,    0,    0,    0,    0,   31,    0,    0,   76,   92,
    0,    0,   90,    0,   72,    0,    0,   62,    0,    0,
    0,  113,  114,    0,    0,    0,    0,   25,    0,    0,
    0,   32,   33,  102,    0,   93,   67,    0,    0,    0,
    0,   64,   70,    0,    0,    0,    0,    0,    0,   23,
   29,   77,   75,   74,   73,   68,   63,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  109,  108,   22,   26,    0,   24,    0,    0,    0,    0,
  110,    0,   21,    0,  106,    0,    0,    0,  104,  107,
    0,  111,  105,  112,  103,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,  109,   15,   16,   17,   18,   49,   94,
   95,   19,   20,   21,   22,   23,   50,   51,   61,  110,
  152,   62,  111,   73,  165,
};
final static short yysindex[] = {                       133,
    3,    8,   37,    0,    0,   36, -231,  -30,    0,    0,
    0,  411,    0,    0,   43,    0, -229,    0,   58,    0,
   63,   83,    0,   45,    0,  -22,    0,  -36,    0,  -28,
 -134,  113,  -22,  147, -116,  -37,  -46,    0,    0,    0,
   98,    0,    0,    0,    0,    0,    0, -127,   82,   59,
    0,  130,    0,    0,    0,    0,    0,    0,    0,  -23,
  -39,  -22,  136,    0,  -54,   79,   82,    0,   82,    0,
  138,    0,   -5,  -22,    0,    0,  -22,  -22,  -22,  -22,
  -70,    0,  -22,  400,  281,   82,    0,  -88,  -22, -169,
    0, -111,  -75,  159,  137,  -68,    0,    0,   82,   59,
   59,    0,    0,  400,   82,  141,  -29,  200,    0,  -81,
    0, -102,  -69,  -59,   22,    0, -168,  160,  411,    0,
 -107,  -96,  161,  168,  -65,  393,  400,    0,  -46,    0,
  -53,  -73,  400,    0,  322, -135,  -62,    0,    0,  -44,
  102,  178,  411,  220,  -96,    0,   40,  400,    0,    0,
  169,   62,    0,  340,    0,  141,  340,    0,   64,   86,
  -22,    0,    0,  322,   74,   88,  208,    0,   89,  251,
  -96,    0,    0,    0,  131,    0,    0,  152,  154,  167,
 -222,    0,    0,  158,  -22,  355,  395,  411,  357,    0,
    0,    0,    0,    0,    0,    0,    0,  -62,  172,   71,
  400,  400,  326,  374,  327,  181,  414, -142,  400,  185,
    0,    0,    0,    0,  335,    0,  420,  400,  423,  192,
    0,  428,    0,  400,    0,  400,  187,  400,    0,    0,
  340,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
  474,    0,    0,    0,    0,    0,    0,   93,    0,    0,
    0,   18,    0,    0,  231,    0,    0,    0,  245,    0,
  269,  299,    0,   73,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   19,    0,    0,    0,
  107,    0,    0,    0,    0,    0,    0,    0,  121,    1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  151,    0,  175,    0,
   39,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    4,    0,    0,   28,    0,    0,    0,    0,
    0,    0,    0,    0,  440,    0,    0,    0,  211,   25,
   49,    0,    0,    0,   30,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  445,   41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  453,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  323,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  363,    0,  -82,  438,    0,    0,   -4,   16,  486,    0,
  -66,    0,    0,    0,    0,    0,  240,  135,    0,  -57,
  -74,  -25,  537,    0,  -85,
};
final static int YYTABLESIZE=768;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        136,
   53,   85,   25,   72,   57,   58,   88,   59,   48,   36,
   36,  119,   64,   35,   26,  143,   27,    4,    6,   77,
  108,   78,   48,   58,   51,   59,   40,  113,  123,  131,
   34,   34,   41,  196,   83,   98,   58,  197,   59,   89,
   32,   53,   24,   53,   82,   53,  125,   28,   52,  108,
   70,  166,   27,   97,  132,  144,  146,  159,  160,   53,
   53,   93,   53,   26,   77,   51,   78,   51,   81,   51,
   79,  178,   19,  175,  180,   31,   30,  172,  173,  100,
  137,  101,  181,   51,   51,  117,   51,  138,   35,   52,
   93,   52,   19,   52,    4,    5,  179,  100,  199,  101,
   80,   39,  116,  139,  191,   79,   12,   52,   52,  161,
   52,  209,  206,   77,  210,   78,   42,   93,   93,   92,
   49,   43,  220,   53,   77,   53,   78,  162,  163,  208,
  219,   19,    9,   19,  162,  163,  164,   65,  185,   93,
   93,   44,    4,    6,   27,   75,   76,   51,  234,   51,
   48,   19,   66,  129,    2,   24,  118,  130,   74,    3,
  142,    4,    5,    6,    7,   12,   93,    4,    5,    8,
   81,   52,   90,   52,   47,   91,   87,  127,  128,   49,
  122,   96,  156,    2,  114,  157,  158,  104,    3,  133,
  134,   48,    6,  148,  149,   19,  120,   19,  107,  121,
   77,   26,   78,  124,  145,  154,  155,  162,  163,   48,
   13,  147,  135,  102,  103,   19,  198,   19,   84,   52,
  140,   53,   54,   55,   56,   24,  168,  231,  167,   12,
   11,   12,   82,   47,   71,   45,   46,   47,  169,   53,
   54,   55,   56,   49,   40,   49,   63,   33,   33,   45,
   46,   47,   53,   54,   55,   56,   53,   53,   53,   53,
   53,   82,   53,  171,   53,   53,   53,   53,   43,   13,
   53,   53,   53,   48,   24,   48,   53,   53,   53,   53,
   51,   51,   51,   51,   51,   81,   51,   79,   51,   51,
   51,   51,   29,  176,   51,   51,   51,   47,   45,   47,
   51,   51,   51,   51,   52,   52,   52,   52,   52,  108,
   52,  174,   52,   52,   52,   52,  100,  101,   52,   52,
   52,  177,   66,  182,   52,   52,   52,   52,   19,   19,
  188,   19,   19,   13,   19,   13,   19,   19,   19,   19,
  162,  163,    4,    5,   19,  183,  186,   90,   19,   19,
   91,   19,   19,   11,   19,   11,   19,   19,   19,   19,
  187,  189,   12,   12,   19,   12,   12,   40,   12,   40,
   12,   12,   12,   12,   38,  190,   49,   49,   12,   49,
   49,   58,   49,   59,   49,   49,   49,   49,    1,    2,
  192,   43,   49,   43,    3,  201,    4,    5,    6,    7,
    4,    5,   68,  108,    8,   90,   48,   48,   91,   48,
   48,  193,   48,  194,   48,   48,   48,   48,   45,   46,
   47,   45,   48,   45,    4,    5,  195,  162,  163,   90,
   47,   47,   91,   47,   47,  202,   47,   14,   47,   47,
   47,   47,  106,    2,  207,   66,   47,   66,    3,   14,
  213,  216,    6,  217,  218,  106,    2,  222,  107,  223,
  224,    3,  108,  226,  227,    6,   13,   13,  228,   13,
   13,  107,   13,    6,   13,   13,   13,   13,    0,  204,
   27,  141,   13,    4,    5,   30,   11,   11,   90,   11,
   11,   91,   11,   28,   11,   11,   11,   11,  214,    0,
   40,   40,   11,   40,   40,  170,   40,    0,   40,   40,
   40,   40,    0,   60,    0,  108,   40,  150,   67,   69,
    0,    0,  108,    0,   43,   43,    0,   43,   43,    0,
   43,    0,   43,   43,   43,   43,  106,    2,  112,    0,
   43,    0,    3,    0,    0,  126,    6,   86,    0,    0,
  203,  205,  107,    0,   45,   45,   14,   45,   45,   99,
   45,    0,   45,   45,   45,   45,  215,    0,  105,    0,
   45,    0,    0,    0,  115,    0,    0,    0,   66,   66,
   14,   66,   66,    0,   66,    0,   66,   66,   66,   66,
    0,    0,    0,    0,   66,  129,    2,   53,   54,   55,
   56,    3,    0,    4,    5,    6,    7,    0,    0,    0,
    0,    8,   37,    2,    0,    0,    0,    0,    3,    0,
    4,    5,    6,    7,    0,   14,   14,    0,    8,   37,
    2,    0,    0,    0,    0,    3,    0,    4,    5,    6,
    7,   14,    0,    0,    0,    8,  184,    0,  106,    2,
    0,    0,    0,    0,    3,  106,    2,    0,    6,    0,
    0,    3,  151,  153,  107,    6,   37,    2,  153,  153,
  200,  107,    3,    0,    4,    5,    6,    7,    0,    0,
    0,    0,    8,    0,  153,    0,    0,    0,    0,    0,
    0,    0,    0,  153,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  211,  212,    0,
    0,    0,    0,    0,    0,  221,    0,    0,    0,    0,
    0,    0,    0,    0,  225,    0,    0,    0,    0,    0,
  229,    0,  230,  232,  233,    0,    0,  235,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         59,
    0,   41,    0,   41,   41,   60,   61,   62,   45,   40,
   40,  123,   41,   44,   61,  123,    1,    0,    0,   43,
  123,   45,   45,   60,    0,   62,  256,   85,   95,  112,
   61,   61,   17,  256,   60,   41,   60,  260,   62,   65,
  272,   41,  272,   43,   41,   45,  104,   40,    0,  123,
   35,  137,   37,   59,  112,  122,  123,  132,  133,   59,
   60,   66,   62,   61,   43,   41,   45,   43,   41,   45,
   41,  154,    0,  148,  157,   40,   40,  144,  145,   41,
   59,   41,  157,   59,   60,   90,   62,  256,   44,   41,
   95,   43,    0,   45,  264,  265,  154,   59,  184,   59,
   42,   59,  272,  272,  171,   47,    0,   59,   60,  135,
   62,   41,  198,   43,  200,   45,   59,  122,  123,   41,
    0,   59,  208,  123,   43,  125,   45,  270,  271,   59,
  273,   59,    0,   61,  270,  271,  272,  272,  164,  144,
  145,   59,  125,  125,  129,  273,  274,  123,  231,  125,
    0,   59,   40,  256,  257,  272,  268,  260,   61,  262,
  268,  264,  265,  266,  267,   59,  171,  264,  265,  272,
   41,  123,  269,  125,    0,  272,   41,  259,  260,   59,
   44,   44,  256,  257,  273,  259,  260,  258,  262,  259,
  260,   45,  266,  259,  260,  123,  272,  125,  272,   41,
   43,   61,   45,  272,   44,  259,  260,  270,  271,   59,
    0,   44,  272,   79,   80,  123,   59,  125,  258,  256,
   61,  276,  277,  278,  279,  272,  125,   41,  273,  123,
    0,  125,  256,   59,  272,  272,  273,  274,   61,  276,
  277,  278,  279,  123,    0,  125,  275,  278,  278,  272,
  273,  274,  276,  277,  278,  279,  256,  257,  258,  259,
  260,  258,  262,   44,  264,  265,  266,  267,    0,   59,
  270,  271,  272,  123,  272,  125,  276,  277,  278,  279,
  256,  257,  258,  259,  260,  258,  262,  258,  264,  265,
  266,  267,  256,  125,  270,  271,  272,  123,    0,  125,
  276,  277,  278,  279,  256,  257,  258,  259,  260,  123,
  262,  272,  264,  265,  266,  267,   77,   78,  270,  271,
  272,  260,    0,  260,  276,  277,  278,  279,  256,  257,
  123,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  270,  271,  264,  265,  272,  260,  273,  269,  256,  257,
  272,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  273,  273,  256,  257,  272,  259,  260,  123,  262,  125,
  264,  265,  266,  267,   12,  125,  256,  257,  272,  259,
  260,   60,  262,   62,  264,  265,  266,  267,  256,  257,
  260,  123,  272,  125,  262,   41,  264,  265,  266,  267,
  264,  265,  256,  123,  272,  269,  256,  257,  272,  259,
  260,  260,  262,  260,  264,  265,  266,  267,  272,  273,
  274,  123,  272,  125,  264,  265,  260,  270,  271,  269,
  256,  257,  272,  259,  260,   41,  262,    0,  264,  265,
  266,  267,  256,  257,  273,  123,  272,  125,  262,   12,
  125,  125,  266,  273,   41,  256,  257,  273,  272,  125,
   41,  262,  123,   41,  273,  266,  256,  257,   41,  259,
  260,  272,  262,    0,  264,  265,  266,  267,   -1,  123,
   41,  119,  272,  264,  265,   41,  256,  257,  269,  259,
  260,  272,  262,   41,  264,  265,  266,  267,  125,   -1,
  256,  257,  272,  259,  260,  143,  262,   -1,  264,  265,
  266,  267,   -1,   28,   -1,  123,  272,  125,   33,   34,
   -1,   -1,  123,   -1,  256,  257,   -1,  259,  260,   -1,
  262,   -1,  264,  265,  266,  267,  256,  257,  258,   -1,
  272,   -1,  262,   -1,   -1,  108,  266,   62,   -1,   -1,
  188,  189,  272,   -1,  256,  257,  119,  259,  260,   74,
  262,   -1,  264,  265,  266,  267,  204,   -1,   83,   -1,
  272,   -1,   -1,   -1,   89,   -1,   -1,   -1,  256,  257,
  143,  259,  260,   -1,  262,   -1,  264,  265,  266,  267,
   -1,   -1,   -1,   -1,  272,  256,  257,  276,  277,  278,
  279,  262,   -1,  264,  265,  266,  267,   -1,   -1,   -1,
   -1,  272,  256,  257,   -1,   -1,   -1,   -1,  262,   -1,
  264,  265,  266,  267,   -1,  188,  189,   -1,  272,  256,
  257,   -1,   -1,   -1,   -1,  262,   -1,  264,  265,  266,
  267,  204,   -1,   -1,   -1,  272,  161,   -1,  256,  257,
   -1,   -1,   -1,   -1,  262,  256,  257,   -1,  266,   -1,
   -1,  262,  126,  127,  272,  266,  256,  257,  132,  133,
  185,  272,  262,   -1,  264,  265,  266,  267,   -1,   -1,
   -1,   -1,  272,   -1,  148,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  157,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  201,  202,   -1,
   -1,   -1,   -1,   -1,   -1,  209,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  218,   -1,   -1,   -1,   -1,   -1,
  224,   -1,  226,  227,  228,   -1,   -1,  231,
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
"conjunto_sentencias : error",
"sentencia : declarativa",
"sentencia : ejecutable",
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
"seleccion : IF '(' ')'",
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
"llamada : ID '(' parametros ';'",
"parametros : ID",
"parametros : ID ',' ID",
"parametros : ID ',' ID ',' ID",
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

//#line 222 "gramatica.y"

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


//#line 565 "Parser.java"
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
//#line 14 "gramatica.y"
{System.out.println("Sin sentencias");}
break;
case 3:
//#line 15 "gramatica.y"
{System.out.println("Sin sentencias");}
break;
case 4:
//#line 18 "gramatica.y"
{}
break;
case 5:
//#line 19 "gramatica.y"
{}
break;
case 6:
//#line 20 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Sentencia mal escrita.");}
break;
case 7:
//#line 24 "gramatica.y"
{System.out.println("Se encontró una sentencia declarativa"); }
break;
case 8:
//#line 25 "gramatica.y"
{System.out.println("Se encontró una sentencia ejecutable"); }
break;
case 9:
//#line 28 "gramatica.y"
{}
break;
case 10:
//#line 29 "gramatica.y"
{}
break;
case 11:
//#line 30 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\" .");}
break;
case 12:
//#line 34 "gramatica.y"
{}
break;
case 13:
//#line 35 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Asignacion en la declaración.");}
break;
case 14:
//#line 36 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba un tipo.");}
break;
case 15:
//#line 37 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Tipo no valido.");}
break;
case 16:
//#line 38 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba un identificador.");}
break;
case 17:
//#line 40 "gramatica.y"
{}
break;
case 18:
//#line 41 "gramatica.y"
{}
break;
case 19:
//#line 44 "gramatica.y"
{}
break;
case 20:
//#line 45 "gramatica.y"
{}
break;
case 21:
//#line 48 "gramatica.y"
{System.out.println("procedimiento " + val_peek(9).sval);}
break;
case 22:
//#line 49 "gramatica.y"
{}
break;
case 23:
//#line 50 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba NI=CTE_INT en la declaracion de PROC .");}
break;
case 24:
//#line 51 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \"{\" .");}
break;
case 25:
//#line 52 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba NI=CTE_INT en la declaracion de PROC .");}
break;
case 26:
//#line 53 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba una sentencia.");}
break;
case 27:
//#line 56 "gramatica.y"
{}
break;
case 28:
//#line 57 "gramatica.y"
{}
break;
case 29:
//#line 58 "gramatica.y"
{}
break;
case 30:
//#line 59 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 31:
//#line 60 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 32:
//#line 61 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 33:
//#line 62 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 34:
//#line 65 "gramatica.y"
{}
break;
case 35:
//#line 66 "gramatica.y"
{}
break;
case 36:
//#line 67 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba tipo.");}
break;
case 37:
//#line 68 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba tipo.");}
break;
case 38:
//#line 69 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba identificador.");}
break;
case 39:
//#line 72 "gramatica.y"
{}
break;
case 40:
//#line 73 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\"' .");}
break;
case 41:
//#line 74 "gramatica.y"
{}
break;
case 42:
//#line 75 "gramatica.y"
{}
break;
case 43:
//#line 76 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\" .");}
break;
case 44:
//#line 77 "gramatica.y"
{}
break;
case 45:
//#line 78 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\"'.");}
break;
case 46:
//#line 79 "gramatica.y"
{}
break;
case 47:
//#line 82 "gramatica.y"
{}
break;
case 48:
//#line 83 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se encontró == en lugar de =.");}
break;
case 49:
//#line 84 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Asignación mal escrita.");}
break;
case 50:
//#line 85 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Asignación mal escrita.");}
break;
case 51:
//#line 88 "gramatica.y"
{}
break;
case 52:
//#line 89 "gramatica.y"
{}
break;
case 53:
//#line 90 "gramatica.y"
{System.out.println("una wea termino");}
break;
case 54:
//#line 93 "gramatica.y"
{System.out.println("una wea divisoria entre "  + val_peek(2).sval + " / " + val_peek(0).sval);}
break;
case 55:
//#line 94 "gramatica.y"
{System.out.println("una wea multiplicatoria");}
break;
case 56:
//#line 95 "gramatica.y"
{System.out.println("una wea factor");}
break;
case 57:
//#line 98 "gramatica.y"
{System.out.println("una wea identificatoria");}
break;
case 58:
//#line 99 "gramatica.y"
{
	   		int i = (int) Integer.parseInt(val_peek(0).sval);
	   		if ( i > (int) Math.pow(2, 15) - 1) {
	   			System.out.println("Error en la linea " +lex.linea + ": Constante entera fuera de rango.");
	   		}
		 }
break;
case 59:
//#line 105 "gramatica.y"
{}
break;
case 60:
//#line 106 "gramatica.y"
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
case 61:
//#line 121 "gramatica.y"
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
case 62:
//#line 137 "gramatica.y"
{System.out.println(val_peek(4).sval+" "+val_peek(2).sval); }
break;
case 63:
//#line 138 "gramatica.y"
{System.out.println("una weaaaa if");}
break;
case 64:
//#line 139 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ELSE"); }
break;
case 65:
//#line 140 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición"); }
break;
case 66:
//#line 141 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF");}
break;
case 67:
//#line 142 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición"); }
break;
case 68:
//#line 143 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF");}
break;
case 69:
//#line 144 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba THEN");}
break;
case 70:
//#line 145 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba THEN");}
break;
case 71:
//#line 146 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se encontraron sentencias ejecutables.");}
break;
case 72:
//#line 147 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del IF.");}
break;
case 73:
//#line 148 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del ELSE.");}
break;
case 74:
//#line 149 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del IF.");}
break;
case 75:
//#line 150 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del IF.");}
break;
case 76:
//#line 151 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 77:
//#line 152 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 78:
//#line 153 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Falta condicion.");}
break;
case 79:
//#line 156 "gramatica.y"
{}
break;
case 80:
//#line 157 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 81:
//#line 158 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 82:
//#line 159 "gramatica.y"
{{System.out.println("Error en la linea " + lex.linea + ":ERReR2.");}}
break;
case 83:
//#line 163 "gramatica.y"
{}
break;
case 84:
//#line 164 "gramatica.y"
{}
break;
case 85:
//#line 165 "gramatica.y"
{}
break;
case 86:
//#line 166 "gramatica.y"
{}
break;
case 87:
//#line 167 "gramatica.y"
{}
break;
case 88:
//#line 168 "gramatica.y"
{}
break;
case 89:
//#line 171 "gramatica.y"
{}
break;
case 90:
//#line 174 "gramatica.y"
{}
break;
case 91:
//#line 177 "gramatica.y"
{}
break;
case 92:
//#line 178 "gramatica.y"
{}
break;
case 93:
//#line 179 "gramatica.y"
{}
break;
case 94:
//#line 182 "gramatica.y"
{}
break;
case 95:
//#line 183 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba una cadena");}
break;
case 96:
//#line 184 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Sentencia OUT mal escrita");}
break;
case 97:
//#line 187 "gramatica.y"
{}
break;
case 98:
//#line 188 "gramatica.y"
{}
break;
case 99:
//#line 189 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de los parametros");}
break;
case 100:
//#line 192 "gramatica.y"
{}
break;
case 101:
//#line 193 "gramatica.y"
{}
break;
case 102:
//#line 194 "gramatica.y"
{}
break;
case 103:
//#line 197 "gramatica.y"
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
case 104:
//#line 206 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ; pero se recibio "+ val_peek(7).sval+"." );}
break;
case 105:
//#line 207 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ; pero se recibio "+ val_peek(3).sval+"." );}
break;
case 106:
//#line 208 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaban ; en la sentencia FOR." );}
break;
case 107:
//#line 209 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaban UP o DOWN en la sentencia FOR." );}
break;
case 108:
//#line 210 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta inicialización en la sentencia FOR.");}
break;
case 109:
//#line 211 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta condicion en la sentencia FOR.");}
break;
case 110:
//#line 212 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta incremento en la sentencia FOR.");}
break;
case 111:
//#line 213 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Se esperaba \")\" en la sentencia FOR.");}
break;
case 112:
//#line 214 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del FOR.");}
break;
case 113:
//#line 217 "gramatica.y"
{}
break;
case 114:
//#line 218 "gramatica.y"
{}
break;
//#line 1210 "Parser.java"
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
