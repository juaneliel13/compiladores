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
    3,    3,    5,    5,    5,    5,    5,    7,    7,    8,
    8,    6,    6,    6,    6,    6,    6,   10,   10,   10,
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
    1,    1,    2,    1,    2,    1,    1,    1,    2,    2,
    1,    1,    2,    4,    1,    2,    2,    1,    1,    1,
    3,   11,   10,    8,   10,    7,   10,    1,    3,    5,
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
    0,    0,    0,   18,   19,    0,    0,    0,    2,    0,
    1,    0,    7,    8,    0,    0,    0,   15,    0,   42,
    0,    0,   47,    0,    3,    0,   16,    0,   96,    0,
    0,    0,    0,    0,    0,    0,    0,    5,    9,   10,
   17,    0,   40,   43,   45,   58,   59,   60,    0,    0,
    0,   57,    0,   86,   87,   85,   88,   83,   84,    0,
    0,    0,    0,   95,    0,    0,    0,   51,    0,   21,
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
    0,   26,    0,    0,    0,   33,   34,  102,    0,   93,
   68,    0,    0,    0,    0,   65,   71,    0,    0,    0,
    0,    0,    0,   24,   30,   78,   76,   75,   74,   69,
   64,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  112,  111,   23,   27,    0,   25,
    0,    0,    0,    0,  113,    0,   22,    0,  109,    0,
    0,    0,  107,  110,    0,  114,  108,  115,  106,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,  109,   15,   16,   17,   18,   50,   94,
   95,   19,   20,   21,   22,   23,   51,   52,   61,  110,
  156,   62,  111,   73,  169,
};
final static short yysindex[] = {                       133,
    3,   -1,   -3,    0,    0,   22, -255,  -40,    0,    0,
    0,  453,    0,    0,   -2,   10, -224,    0,   23,    0,
   27,   40,    0,    8,    0,  -18,    0,  177,    0,  -34,
 -196,   61,  -18,  147, -192,  -30,  -51,    0,    0,    0,
    0,   28,    0,    0,    0,    0,    0,    0, -245,   29,
   -4,    0,   62,    0,    0,    0,    0,    0,    0,  -29,
  -35,  -18,   81,    0,  368,  -36,   29,    0,   29,    0,
  -10,    0,   87,  -18,    0,    0,  -18,  -18,  -18,  -18,
 -144,    0,  -18,  439,  416,   29,    0, -137,  -18,  -88,
    0, -110, -116,  124,   79,   14, -105,    0,   29,   -4,
   -4,    0,    0,  439,   29,  109,  -38,  258,    0, -141,
    0, -103, -118,   16,   84,    0, -175,  112,  453,    0,
 -108, -134,  137,    0,  -94,   21,  -74,  419,  439,    0,
  -51,    0,  -65,  -69,  439,    0,  352, -125,  -62,    0,
    0,  -90,   64,  139,  453,  220, -134,    0,    0,    0,
  -70,  439,    0,    0,   89,  -43,    0,  376,    0,  109,
  376,    0,  -17,   34,  -18,    0,    0,  352,  -49,   37,
  189,    0,   44,  193, -134,    0,    0,    0,   71,    0,
    0,   82,   86,  101, -189,    0,    0,  156,  -18,  283,
  306,  453,  393,    0,    0,    0,    0,    0,    0,    0,
    0,  -62,  102,  -19,  439,  439,  237,  354,  251,  111,
  350,  -66,  439,  123,    0,    0,    0,    0,  257,    0,
  384,  439,  395,  165,    0,  403,    0,  439,    0,  439,
  281,  439,    0,    0,  376,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
  445,    0,    0,    0,    0,    0,    0,   93,    0,    0,
    0,   12,    0,    0,  231,  245,    0,    0,  269,    0,
  299,  323,    0,   73,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   30,    0,    0,    0,
    0,  107,    0,    0,    0,    0,    0,    0,    0,  121,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  151,    0,  175,    0,
  411,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -33,    0,    0,  -32,    0,    0,    0,    0,
    0,    0,    0,    0,  417,  418,    0,    0,  211,   25,
   49,    0,    0,    0,  -23,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  420,    0,    0,  421,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  422,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  341,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  431,    0,  -56,  506,    0,    0,   -7,   18,   17,    0,
  399,    0,    0,    0,    0,    0,  135,  140,    0,   13,
  -57,  -24,  555,    0,  -92,
};
final static int YYTABLESIZE=790;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         36,
   54,   36,   25,   35,   92,   85,   64,   82,   81,   26,
   72,    4,  119,   77,  145,   78,   32,   79,   27,  108,
   34,  213,   34,   77,   52,   78,   49,   75,   76,    6,
   58,   41,   59,   97,   42,   83,   30,   80,   28,  212,
   89,   54,   79,   54,   60,   54,  170,   24,   53,   67,
   69,   35,   70,  108,   27,  133,   39,  125,   93,   54,
   54,   31,   54,   26,  151,   52,  200,   52,   40,   52,
  201,   77,   20,   78,  138,   65,  163,  164,   86,   24,
  140,   43,  117,   52,   52,   44,   52,   93,   74,   53,
   99,   53,   20,   53,  179,  203,  141,  113,   45,  105,
   66,  182,   81,  185,  184,  115,   13,   53,   53,  210,
   53,  214,  165,  104,   93,   93,  127,  129,  130,  224,
   50,   87,  122,   54,  134,   54,   77,   98,   78,    4,
    5,   20,    9,   20,   90,  114,    4,   91,   93,   93,
  135,  136,  139,  189,  166,  167,  168,   52,   27,   52,
   49,   20,  131,    2,    6,  120,  132,  118,    3,  144,
    4,    5,    6,    7,  121,   13,  126,   93,    8,   26,
  183,   53,  142,   53,   48,    4,    5,  149,  238,   50,
  147,  188,  171,  116,  152,  153,  160,    2,  172,  161,
  162,   49,    3,  158,  159,   20,    6,   20,   77,  173,
   78,  178,  107,  166,  167,  204,  223,  166,  167,   49,
   14,  100,  101,  180,  202,   20,  181,   20,  102,  103,
   24,   49,   84,  190,   82,   81,   82,    4,    5,   13,
   11,   13,   90,   48,   79,   91,   58,   33,   59,   33,
   63,   71,  186,   50,   12,   50,   54,   55,   56,   57,
  166,  167,   29,   46,   47,   48,   54,   54,   54,   54,
   54,   96,   54,  175,   54,   54,   54,   54,   41,   14,
   54,   54,   54,   49,   24,   49,   54,   54,   54,   54,
   52,   52,   52,   52,   52,  124,   52,  137,   52,   52,
   52,   52,  150,  187,   52,   52,   52,   48,   44,   48,
   52,   52,   52,   52,   53,   53,   53,   53,   53,  191,
   53,  192,   53,   53,   53,   53,  193,  194,   53,   53,
   53,  235,   46,  205,   53,   53,   53,   53,   20,   20,
  196,   20,   20,   14,   20,   14,   20,   20,   20,   20,
   67,  197,    4,    5,   20,  198,  206,   90,   20,   20,
   91,   20,   20,   11,   20,   11,   20,   20,   20,   20,
  199,  217,   13,   13,   20,   13,   13,   12,   13,   12,
   13,   13,   13,   13,  211,  220,   50,   50,   13,   50,
   50,  227,   50,  221,   50,   50,   50,   50,    1,    2,
  222,   41,   50,   41,    3,  226,    4,    5,    6,    7,
    4,    5,   68,  108,    8,   90,   49,   49,   91,   49,
   49,   58,   49,   59,   49,   49,   49,   49,   46,   47,
   48,   44,   49,   44,  228,  166,  167,   58,   88,   59,
   48,   48,   53,   48,   48,  230,   48,  231,   48,   48,
   48,   48,   38,  232,    6,   46,   48,   46,   46,   47,
   48,   99,   54,   55,   56,   57,    0,   28,  101,    0,
   31,  100,   29,   67,    0,   67,   14,   14,    0,   14,
   14,    0,   14,    0,   14,   14,   14,   14,  218,    0,
    0,    0,   14,    4,    5,    0,   11,   11,   90,   11,
   11,   91,   11,  123,   11,   11,   11,   11,  108,    0,
   12,   12,   11,   12,   12,   14,   12,    0,   12,   12,
   12,   12,    0,  106,    2,  208,   12,   14,    0,    3,
  146,  148,    0,    6,   41,   41,    0,   41,   41,  107,
   41,    0,   41,   41,   41,   41,  106,    2,  108,    0,
   41,  108,    3,  154,  176,  177,    6,    0,    0,  143,
    0,    0,  107,    0,   44,   44,    0,   44,   44,    0,
   44,  108,   44,   44,   44,   44,    0,    0,    0,    0,
   44,    0,    0,  195,    0,  174,    0,    0,   46,   46,
    0,   46,   46,    0,   46,    0,   46,   46,   46,   46,
    0,    0,    0,    0,   46,    0,   67,   67,    0,   67,
   67,    0,   67,    0,   67,   67,   67,   67,    0,   37,
    2,    0,   67,  128,    0,    3,    0,    4,    5,    6,
    7,    0,  207,  209,   14,    8,    0,   54,   55,   56,
   57,  131,    2,    0,    0,    0,    0,    3,  219,    4,
    5,    6,    7,   54,   55,   56,   57,    8,   37,    2,
   14,    0,    0,    0,    3,    0,    4,    5,    6,    7,
    0,    0,    0,    0,    8,    0,    0,    0,    0,    0,
    0,  106,    2,  112,  106,    2,    0,    3,    0,    0,
    3,    6,  155,  157,    6,    0,    0,  107,  157,  157,
  107,    0,    0,    0,  106,    2,    0,   14,   14,    0,
    3,    0,    0,    0,    6,    0,  157,    0,   37,    2,
  107,    0,    0,   14,    3,  157,    4,    5,    6,    7,
    0,    0,    0,    0,    8,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  215,
  216,    0,    0,    0,    0,    0,    0,  225,    0,    0,
    0,    0,    0,    0,    0,    0,  229,    0,    0,    0,
    0,    0,  233,    0,  234,  236,  237,    0,    0,  239,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,    0,   44,   41,   41,   41,   41,   41,   61,
   41,    0,  123,   43,  123,   45,  272,   41,    1,  123,
   61,   41,   61,   43,    0,   45,   45,  273,  274,    0,
   60,  256,   62,   44,   17,   60,   40,   42,   40,   59,
   65,   41,   47,   43,   28,   45,  139,  272,    0,   33,
   34,   44,   35,  123,   37,  112,   59,   44,   66,   59,
   60,   40,   62,   61,   44,   41,  256,   43,   59,   45,
  260,   43,    0,   45,   59,  272,  134,  135,   62,  272,
  256,   59,   90,   59,   60,   59,   62,   95,   61,   41,
   74,   43,    0,   45,  152,  188,  272,   85,   59,   83,
   40,  158,   41,  161,  161,   89,    0,   59,   60,  202,
   62,  204,  137,  258,  122,  123,  104,  259,  260,  212,
    0,   41,   44,  123,  112,  125,   43,   41,   45,  264,
  265,   59,    0,   61,  269,  273,  125,  272,  146,  147,
  259,  260,   59,  168,  270,  271,  272,  123,  131,  125,
    0,   59,  256,  257,  125,  272,  260,  268,  262,  268,
  264,  265,  266,  267,   41,   59,  272,  175,  272,   61,
  158,  123,   61,  125,    0,  264,  265,  272,  235,   59,
   44,  165,  273,  272,  259,  260,  256,  257,  125,  259,
  260,   45,  262,  259,  260,  123,  266,  125,   43,   61,
   45,  272,  272,  270,  271,  189,  273,  270,  271,   59,
    0,   77,   78,  125,   59,  123,  260,  125,   79,   80,
  272,   45,  258,  273,  258,  258,  256,  264,  265,  123,
    0,  125,  269,   59,  258,  272,   60,  278,   62,  278,
  275,  272,  260,  123,    0,  125,  276,  277,  278,  279,
  270,  271,  256,  272,  273,  274,  256,  257,  258,  259,
  260,  272,  262,   44,  264,  265,  266,  267,    0,   59,
  270,  271,  272,  123,  272,  125,  276,  277,  278,  279,
  256,  257,  258,  259,  260,  272,  262,  272,  264,  265,
  266,  267,  272,  260,  270,  271,  272,  123,    0,  125,
  276,  277,  278,  279,  256,  257,  258,  259,  260,  273,
  262,  123,  264,  265,  266,  267,  273,  125,  270,  271,
  272,   41,    0,   41,  276,  277,  278,  279,  256,  257,
  260,  259,  260,  123,  262,  125,  264,  265,  266,  267,
    0,  260,  264,  265,  272,  260,   41,  269,  256,  257,
  272,  259,  260,  123,  262,  125,  264,  265,  266,  267,
  260,  125,  256,  257,  272,  259,  260,  123,  262,  125,
  264,  265,  266,  267,  273,  125,  256,  257,  272,  259,
  260,  125,  262,  273,  264,  265,  266,  267,  256,  257,
   41,  123,  272,  125,  262,  273,  264,  265,  266,  267,
  264,  265,  256,  123,  272,  269,  256,  257,  272,  259,
  260,   60,  262,   62,  264,  265,  266,  267,  272,  273,
  274,  123,  272,  125,   41,  270,  271,   60,   61,   62,
  256,  257,  256,  259,  260,   41,  262,  273,  264,  265,
  266,  267,   12,   41,    0,  123,  272,  125,  272,  273,
  274,   41,  276,  277,  278,  279,   -1,   41,   41,   -1,
   41,   41,   41,  123,   -1,  125,  256,  257,   -1,  259,
  260,   -1,  262,   -1,  264,  265,  266,  267,  125,   -1,
   -1,   -1,  272,  264,  265,   -1,  256,  257,  269,  259,
  260,  272,  262,   95,  264,  265,  266,  267,  123,   -1,
  256,  257,  272,  259,  260,    0,  262,   -1,  264,  265,
  266,  267,   -1,  256,  257,  123,  272,   12,   -1,  262,
  122,  123,   -1,  266,  256,  257,   -1,  259,  260,  272,
  262,   -1,  264,  265,  266,  267,  256,  257,  123,   -1,
  272,  123,  262,  125,  146,  147,  266,   -1,   -1,  119,
   -1,   -1,  272,   -1,  256,  257,   -1,  259,  260,   -1,
  262,  123,  264,  265,  266,  267,   -1,   -1,   -1,   -1,
  272,   -1,   -1,  175,   -1,  145,   -1,   -1,  256,  257,
   -1,  259,  260,   -1,  262,   -1,  264,  265,  266,  267,
   -1,   -1,   -1,   -1,  272,   -1,  256,  257,   -1,  259,
  260,   -1,  262,   -1,  264,  265,  266,  267,   -1,  256,
  257,   -1,  272,  108,   -1,  262,   -1,  264,  265,  266,
  267,   -1,  192,  193,  119,  272,   -1,  276,  277,  278,
  279,  256,  257,   -1,   -1,   -1,   -1,  262,  208,  264,
  265,  266,  267,  276,  277,  278,  279,  272,  256,  257,
  145,   -1,   -1,   -1,  262,   -1,  264,  265,  266,  267,
   -1,   -1,   -1,   -1,  272,   -1,   -1,   -1,   -1,   -1,
   -1,  256,  257,  258,  256,  257,   -1,  262,   -1,   -1,
  262,  266,  128,  129,  266,   -1,   -1,  272,  134,  135,
  272,   -1,   -1,   -1,  256,  257,   -1,  192,  193,   -1,
  262,   -1,   -1,   -1,  266,   -1,  152,   -1,  256,  257,
  272,   -1,   -1,  208,  262,  161,  264,  265,  266,  267,
   -1,   -1,   -1,   -1,  272,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  205,
  206,   -1,   -1,   -1,   -1,   -1,   -1,  213,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  222,   -1,   -1,   -1,
   -1,   -1,  228,   -1,  230,  231,  232,   -1,   -1,  235,
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
"declarativa : dec_procedimiento ';'",
"declarativa : dec_variable",
"declarativa : dec_procedimiento",
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

//#line 229 "gramatica.y"

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


//#line 572 "Parser.java"
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
{System.out.println("Sin sentencias.");}
break;
case 3:
//#line 15 "gramatica.y"
{System.out.println("Sin sentencias.");}
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
{}
break;
case 8:
//#line 25 "gramatica.y"
{ }
break;
case 9:
//#line 28 "gramatica.y"
{System.out.println("Se encontró una sentencia declarativa de variable."); }
break;
case 10:
//#line 29 "gramatica.y"
{System.out.println("Se encontró una sentencia declarativa de procedimiento."); }
break;
case 11:
//#line 30 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\".");}
break;
case 12:
//#line 31 "gramatica.y"
{}
break;
case 13:
//#line 34 "gramatica.y"
{}
break;
case 14:
//#line 35 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Asignacion en la declaración.");}
break;
case 15:
//#line 36 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba un tipo.");}
break;
case 16:
//#line 37 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Tipo no valido.");}
break;
case 17:
//#line 38 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba un identificador.");}
break;
case 18:
//#line 40 "gramatica.y"
{}
break;
case 19:
//#line 41 "gramatica.y"
{}
break;
case 20:
//#line 44 "gramatica.y"
{}
break;
case 21:
//#line 45 "gramatica.y"
{}
break;
case 22:
//#line 48 "gramatica.y"
{}
break;
case 23:
//#line 49 "gramatica.y"
{}
break;
case 24:
//#line 50 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba NI=CTE_INT en la declaracion de PROC.");}
break;
case 25:
//#line 51 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \"{\" .");}
break;
case 26:
//#line 52 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba NI=CTE_INT en la declaracion de PROC.");}
break;
case 27:
//#line 53 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba una sentencia.");}
break;
case 28:
//#line 56 "gramatica.y"
{}
break;
case 29:
//#line 57 "gramatica.y"
{}
break;
case 30:
//#line 58 "gramatica.y"
{}
break;
case 31:
//#line 59 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 32:
//#line 60 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 33:
//#line 61 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 34:
//#line 62 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 35:
//#line 65 "gramatica.y"
{}
break;
case 36:
//#line 66 "gramatica.y"
{}
break;
case 37:
//#line 67 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba tipo.");}
break;
case 38:
//#line 68 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba tipo.");}
break;
case 39:
//#line 69 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba identificador.");}
break;
case 40:
//#line 72 "gramatica.y"
{System.out.println("Se encontró una sentencia de asignación."); }
break;
case 41:
//#line 73 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\"' .");}
break;
case 42:
//#line 74 "gramatica.y"
{System.out.println("Se encontró una sentencia de selección."); }
break;
case 43:
//#line 75 "gramatica.y"
{System.out.println("Se encontró una sentencia de salida."); }
break;
case 44:
//#line 76 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\" .");}
break;
case 45:
//#line 77 "gramatica.y"
{System.out.println("Se encontró una sentencia de llamada."); }
break;
case 46:
//#line 78 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\"'.");}
break;
case 47:
//#line 79 "gramatica.y"
{System.out.println("Se encontró una sentencia de control."); }
break;
case 48:
//#line 82 "gramatica.y"
{}
break;
case 49:
//#line 83 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se encontró == en lugar de =.");}
break;
case 50:
//#line 84 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Asignación mal escrita.");}
break;
case 51:
//#line 85 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Asignación mal escrita.");}
break;
case 52:
//#line 88 "gramatica.y"
{}
break;
case 53:
//#line 89 "gramatica.y"
{}
break;
case 54:
//#line 90 "gramatica.y"
{}
break;
case 55:
//#line 93 "gramatica.y"
{}
break;
case 56:
//#line 94 "gramatica.y"
{}
break;
case 57:
//#line 95 "gramatica.y"
{}
break;
case 58:
//#line 98 "gramatica.y"
{}
break;
case 59:
//#line 99 "gramatica.y"
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
//#line 107 "gramatica.y"
{}
break;
case 61:
//#line 108 "gramatica.y"
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
//#line 124 "gramatica.y"
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
//#line 142 "gramatica.y"
{}
break;
case 64:
//#line 143 "gramatica.y"
{}
break;
case 65:
//#line 144 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ELSE."); }
break;
case 66:
//#line 145 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición."); }
break;
case 67:
//#line 146 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF.");}
break;
case 68:
//#line 147 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición."); }
break;
case 69:
//#line 148 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF.");}
break;
case 70:
//#line 149 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba THEN.");}
break;
case 71:
//#line 150 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba THEN.");}
break;
case 72:
//#line 151 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se encontraron sentencias ejecutables.");}
break;
case 73:
//#line 152 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del IF.");}
break;
case 74:
//#line 153 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del ELSE.");}
break;
case 75:
//#line 154 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del IF.");}
break;
case 76:
//#line 155 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del IF.");}
break;
case 77:
//#line 156 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 78:
//#line 157 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 79:
//#line 160 "gramatica.y"
{}
break;
case 80:
//#line 161 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 81:
//#line 162 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 82:
//#line 163 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Condición mal escrita.");}
break;
case 83:
//#line 167 "gramatica.y"
{}
break;
case 84:
//#line 168 "gramatica.y"
{}
break;
case 85:
//#line 169 "gramatica.y"
{}
break;
case 86:
//#line 170 "gramatica.y"
{}
break;
case 87:
//#line 171 "gramatica.y"
{}
break;
case 88:
//#line 172 "gramatica.y"
{}
break;
case 89:
//#line 175 "gramatica.y"
{}
break;
case 90:
//#line 178 "gramatica.y"
{}
break;
case 91:
//#line 181 "gramatica.y"
{}
break;
case 92:
//#line 182 "gramatica.y"
{}
break;
case 93:
//#line 183 "gramatica.y"
{}
break;
case 94:
//#line 186 "gramatica.y"
{}
break;
case 95:
//#line 187 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba una cadena.");}
break;
case 96:
//#line 188 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Sentencia OUT mal escrita.");}
break;
case 97:
//#line 191 "gramatica.y"
{}
break;
case 98:
//#line 192 "gramatica.y"
{}
break;
case 99:
//#line 195 "gramatica.y"
{}
break;
case 100:
//#line 196 "gramatica.y"
{}
break;
case 101:
//#line 197 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 102:
//#line 198 "gramatica.y"
{}
break;
case 103:
//#line 199 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 104:
//#line 200 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 105:
//#line 201 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 106:
//#line 204 "gramatica.y"
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
//#line 213 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba \";\" pero se recibio "+ val_peek(7).sval+"." );}
break;
case 108:
//#line 214 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba \";\" pero se recibio "+ val_peek(3).sval+"." );}
break;
case 109:
//#line 215 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaban \";\" en la sentencia FOR." );}
break;
case 110:
//#line 216 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaban UP o DOWN en la sentencia FOR." );}
break;
case 111:
//#line 217 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta inicialización en la sentencia FOR.");}
break;
case 112:
//#line 218 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta condicion en la sentencia FOR.");}
break;
case 113:
//#line 219 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta incremento en la sentencia FOR.");}
break;
case 114:
//#line 220 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Se esperaba \")\" en la sentencia FOR.");}
break;
case 115:
//#line 221 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se permite declaraciones dentro del FOR.");}
break;
case 116:
//#line 224 "gramatica.y"
{}
break;
case 117:
//#line 225 "gramatica.y"
{}
break;
//#line 1234 "Parser.java"
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
