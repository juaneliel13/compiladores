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
    6,    6,   10,   10,   10,   10,   10,   10,   10,   11,
   11,   11,   11,   11,    4,    4,    4,    4,    4,    4,
    4,    4,   12,   12,    9,    9,    9,   17,   17,   17,
   18,   18,   18,   18,   18,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   19,   19,   19,   19,   22,
   22,   22,   22,   22,   22,   20,   21,   23,   23,   23,
   14,   15,   15,   15,   24,   24,   24,   16,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   25,   25,
};
final static short yylen[] = {                            2,
    1,    1,    2,    1,    2,    1,    1,    1,    2,    1,
    1,    2,    4,    1,    2,    2,    1,    1,    1,    3,
   11,   10,    1,    3,    5,    2,    3,    4,    4,    2,
    3,    2,    1,    3,    2,    1,    1,    2,    1,    2,
    1,    1,    3,    3,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    2,    2,    7,    9,    8,    6,    7,
    8,    9,    6,    8,    6,    3,    3,    3,    3,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    3,    4,
    4,    4,    3,    4,    1,    3,    5,   14,   13,   13,
   12,   13,   10,   10,   11,   13,    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   17,   18,    0,    0,    0,    2,    0,
    1,    0,    7,    8,    0,   10,    0,   14,    0,   37,
    0,    0,   42,    0,    3,   15,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    5,    9,   16,    0,
   35,   38,   40,    0,   51,   52,   53,    0,    0,    0,
   50,    0,    0,    0,    0,   78,   97,    0,    0,    0,
    0,   20,    0,   83,    0,    0,   73,   74,   72,   75,
   70,   71,    0,   54,   55,    0,    0,    0,    0,    0,
    0,    0,    0,   81,    0,    0,    0,    0,   33,    0,
    0,    0,    0,    0,   84,   82,    0,    0,   67,    0,
    0,   69,    0,   48,   49,    0,   76,    0,    0,   79,
    0,    0,    0,   32,    0,    0,   30,    0,    0,    0,
    0,    0,   59,   65,    0,    0,   63,   80,    0,    0,
    0,   34,   31,    0,    0,    0,    0,   27,    0,    0,
   77,   60,    0,   56,    0,    0,    0,   98,   99,    0,
    0,    0,    0,    0,    0,   28,   29,   87,   61,    0,
   58,   64,    0,    0,    0,    0,    0,    0,   25,   62,
   57,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   94,   93,   22,    0,    0,    0,    0,
    0,   95,    0,   21,    0,   91,    0,    0,    0,   89,
   92,    0,   96,   90,   88,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,   56,   15,   16,   91,   18,   49,   92,
   93,   19,   20,   21,   22,   23,   50,   51,   52,  106,
  140,   73,  141,   65,  151,
};
final static short yysindex[] = {                       219,
    2,  -12,   -2,    0,    0,  -25, -225,  -40,    0,    0,
    0,  298,    0,    0,   -8,    0, -213,    0,    5,    0,
   18,   42,    0,   28,    0,    0,  178, -184,  -67, -166,
   70,  -19,  -19, -159,  -35, -159,    0,    0,    0,   54,
    0,    0,    0,  250,    0,    0,    0, -192,  -29,   32,
    0,  -34,   78,  -37, -186,    0,    0,  262,  -36,   59,
   59,    0,   87,    0,  -24,  -19,    0,    0,    0,    0,
    0,    0,  -19,    0,    0,   95,  -19,  -19,  224,  -19,
  -19,  -67, -104,    0,  -86, -124,  -19, -167,    0, -121,
 -113,  116,   79, -112,    0,    0,   59,   59,    0,   32,
   32,    0,   59,    0,    0, -132,    0,  -75, -126,    0,
   41,  -30,   24,    0, -194,  106,    0,  -98, -169,  125,
  135,  -67,    0,    0,  287,  -67,    0,    0,  250, -130,
 -115,    0,    0,  -92,  122,  249, -169,    0,  -78,  -60,
    0,    0,  -67,    0,  -59,  -53,  -19,    0,    0,  250,
  -65,  -64,   90,  -58, -169,    0,    0,    0,    0, -226,
    0,    0,  311,  -19,  173,  176,  298,   98,    0,    0,
    0, -115,  -38,   71,  -67,  -67,  114,  298,  -28,  199,
 -153,  -67,  -21,    0,    0,    0,  118,  215,  -67,  229,
   21,    0,  234,    0,  -67,    0,  -67,  245,  -67,    0,
    0,  -67,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
  299,    0,    0,    0,    0,    0,    0,  163,    0,    0,
    0,   12,    0,    0,  206,    0,    0,    0,  107,    0,
  121,  139,    0,  151,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   13,    0,    0,    0,  175,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   73,
   93,    0,   -9,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  257,    0,    0,    0,  192,  -33,    0,   25,
   49,    0,  -32,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  277,
   16,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  293,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    6,    0,    0,   10,    0,    0,   11,   19,   56,    0,
  368,    0,    0,    0,    0,    0,   67,   84,    0,  -56,
  -85,   -4,  437,    0,   30,
};
final static int YYTABLESIZE=639;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         35,
   47,   25,   35,   34,   90,   64,   83,   68,   66,   14,
   17,    4,    6,   77,   30,   78,   96,   37,   55,   26,
   33,   14,   17,   33,   45,   48,  109,   27,  130,  170,
   71,   85,   72,  171,   95,   40,   55,   28,  110,  145,
  146,   47,   39,   47,   79,   47,   31,   55,   46,   85,
   38,  125,   62,   87,   26,   55,   86,  160,   24,   47,
   47,  132,   47,   41,   85,   45,   77,   45,   78,   45,
    2,   34,   44,   81,   86,    3,   42,  133,   80,    6,
   74,   75,  131,   45,   45,   54,   45,   60,   61,   46,
   53,   46,   43,   46,    4,    5,    4,    5,  115,   88,
   43,   77,   89,   78,  114,   58,   36,   46,   46,   59,
   46,  182,   24,   77,   66,   78,  148,  149,   84,  190,
   39,   97,  119,   47,  147,   47,  122,  123,   98,  181,
   94,   44,  126,  127,  103,   99,    4,    6,   41,  148,
  149,  150,  113,  100,  101,  164,  116,   45,  112,   45,
   19,   43,    2,  108,  148,  149,  118,    3,  117,  121,
  152,    6,   19,  104,  105,  128,  134,   54,  137,  135,
    2,   46,  177,   46,   12,    3,   14,   17,  139,    6,
  153,    2,  154,  187,  124,   54,    3,   14,   17,    2,
    6,   13,  173,  158,    3,   44,   54,   44,    6,  159,
  161,  179,  163,  183,   54,   11,  162,  165,  166,   19,
  191,   19,  167,  175,  168,   43,  176,   43,    9,  174,
  178,   19,   48,   82,   68,   66,   76,    4,    5,   36,
   29,   36,   88,   12,  180,   89,   63,   32,  186,  189,
   32,  129,  194,   39,  188,   39,   67,   68,   69,   70,
   13,  193,   45,   46,   47,  195,   47,   47,   47,   47,
   47,   41,   47,   41,   47,   47,   47,   47,   48,  197,
   47,   47,   47,   24,  199,   19,   47,   47,   47,   47,
   45,   45,   45,   45,   45,  202,   45,   19,   45,   45,
   45,   45,  155,  198,   45,   45,   45,   23,    6,   12,
   45,   45,   45,   45,   46,   46,   46,   46,   46,   71,
   46,   72,   46,   46,   46,   46,   13,   26,   46,   46,
   46,   71,   86,   72,   46,   46,   46,   46,   44,   44,
   11,   44,   44,   24,   44,    0,   44,   44,   44,   44,
  148,  149,    4,    5,   44,    0,    0,   88,   43,   43,
   89,   43,   43,   77,   43,   78,   43,   43,   43,   43,
    0,    0,   36,   36,   43,   36,   36,   55,   36,  172,
   36,   36,   36,   36,    0,    0,   39,   39,   36,   39,
   39,    0,   39,    0,   39,   39,   39,   39,    4,    5,
    0,    0,   39,   88,   41,   41,   89,   41,   41,    0,
   41,    0,   41,   41,   41,   41,   19,   19,    0,   55,
   41,    0,   19,    0,   19,   19,   19,   19,   19,   19,
    0,    0,   19,    0,   19,    0,   19,   19,   19,   19,
   12,   12,    0,   44,   19,    0,   12,    0,   12,   12,
   12,   12,    0,    0,    0,    0,   12,   13,   13,   45,
   46,   47,    0,   13,    0,   13,   13,   13,   13,    0,
  120,   11,   11,   13,    0,   57,    0,   11,    0,   11,
   11,   11,   11,    0,    1,    2,    0,   11,    0,  102,
    3,    0,    4,    5,    6,    7,  136,  138,    0,    0,
    8,    0,    0,    0,    0,   45,   46,   47,    0,    0,
    0,    2,    0,  156,  157,    0,    3,    0,    0,    0,
    6,    0,    4,    5,    0,    0,   54,   88,  107,  107,
   89,  111,  169,    0,    0,   67,   68,   69,   70,    0,
    0,    0,    0,    0,    0,    0,    0,   67,   68,   69,
   70,    0,  142,    2,  107,  143,  144,    0,    3,    0,
    0,    0,    6,   36,    2,    0,    0,    0,   54,    3,
    0,    4,    5,    6,    7,    0,    0,    0,    0,    8,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  148,  149,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  184,  185,    0,    0,    0,    0,    0,  192,    0,
    0,    0,    0,    0,    0,  196,    0,    0,    0,    0,
    0,  200,    0,  201,  203,  204,    0,    0,  205,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,    0,   40,   44,   41,   41,   41,   41,   41,    0,
    0,    0,    0,   43,   40,   45,   41,   12,  123,    1,
   61,   12,   12,   61,    0,   45,   83,   40,   59,  256,
   60,   41,   62,  260,   59,   17,  123,   40,  125,  125,
  126,   41,  256,   43,   49,   45,  272,  123,    0,   59,
   59,  108,   34,   58,   36,  123,   41,  143,  272,   59,
   60,  256,   62,   59,   55,   41,   43,   43,   45,   45,
  257,   44,    0,   42,   59,  262,   59,  272,   47,  266,
  273,  274,   59,   59,   60,  272,   62,   32,   33,   41,
  275,   43,    0,   45,  264,  265,  264,  265,   88,  269,
   59,   43,  272,   45,  272,  272,    0,   59,   60,   40,
   62,   41,  272,   43,   61,   45,  270,  271,   41,  273,
    0,   66,   44,  123,  129,  125,  259,  260,   73,   59,
   44,   59,  259,  260,   79,   41,  125,  125,    0,  270,
  271,  272,   87,   77,   78,  150,  268,  123,  273,  125,
    0,   59,  257,  258,  270,  271,   41,  262,  272,  272,
  131,  266,    0,   80,   81,  125,   61,  272,   44,  268,
  257,  123,  167,  125,    0,  262,  167,  167,   44,  266,
  273,  257,   61,  178,  260,  272,  262,  178,  178,  257,
  266,    0,  163,  272,  262,  123,  272,  125,  266,  260,
  260,  172,  147,  174,  272,    0,  260,  273,  273,   59,
  181,   61,  123,   41,  273,  123,   41,  125,    0,  164,
  123,   59,   45,  258,  258,  258,  256,  264,  265,  123,
  256,  125,  269,   59,  273,  272,  272,  278,  125,   41,
  278,  272,  125,  123,  273,  125,  276,  277,  278,  279,
   59,  273,  272,  273,  274,   41,  256,  257,  258,  259,
  260,  123,  262,  125,  264,  265,  266,  267,   45,   41,
  270,  271,  272,  272,   41,  125,  276,  277,  278,  279,
  256,  257,  258,  259,  260,   41,  262,  125,  264,  265,
  266,  267,   44,  273,  270,  271,  272,   41,    0,  125,
  276,  277,  278,  279,  256,  257,  258,  259,  260,   60,
  262,   62,  264,  265,  266,  267,  125,   41,  270,  271,
  272,   60,   61,   62,  276,  277,  278,  279,  256,  257,
  125,  259,  260,   41,  262,   -1,  264,  265,  266,  267,
  270,  271,  264,  265,  272,   -1,   -1,  269,  256,  257,
  272,  259,  260,   43,  262,   45,  264,  265,  266,  267,
   -1,   -1,  256,  257,  272,  259,  260,  123,  262,   59,
  264,  265,  266,  267,   -1,   -1,  256,  257,  272,  259,
  260,   -1,  262,   -1,  264,  265,  266,  267,  264,  265,
   -1,   -1,  272,  269,  256,  257,  272,  259,  260,   -1,
  262,   -1,  264,  265,  266,  267,  256,  257,   -1,  123,
  272,   -1,  262,   -1,  264,  265,  266,  267,  256,  257,
   -1,   -1,  272,   -1,  262,   -1,  264,  265,  266,  267,
  256,  257,   -1,  256,  272,   -1,  262,   -1,  264,  265,
  266,  267,   -1,   -1,   -1,   -1,  272,  256,  257,  272,
  273,  274,   -1,  262,   -1,  264,  265,  266,  267,   -1,
   93,  256,  257,  272,   -1,   29,   -1,  262,   -1,  264,
  265,  266,  267,   -1,  256,  257,   -1,  272,   -1,  256,
  262,   -1,  264,  265,  266,  267,  119,  120,   -1,   -1,
  272,   -1,   -1,   -1,   -1,  272,  273,  274,   -1,   -1,
   -1,  257,   -1,  136,  137,   -1,  262,   -1,   -1,   -1,
  266,   -1,  264,  265,   -1,   -1,  272,  269,   82,   83,
  272,   85,  155,   -1,   -1,  276,  277,  278,  279,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  279,   -1,  256,  257,  108,  259,  260,   -1,  262,   -1,
   -1,   -1,  266,  256,  257,   -1,   -1,   -1,  272,  262,
   -1,  264,  265,  266,  267,   -1,   -1,   -1,   -1,  272,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  270,  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  175,  176,   -1,   -1,   -1,   -1,   -1,  182,   -1,
   -1,   -1,   -1,   -1,   -1,  189,   -1,   -1,   -1,   -1,
   -1,  195,   -1,  197,  198,  199,   -1,   -1,  202,
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
"condicion_if : expresion comparador expresion",
"condicion_if : expresion error ')'",
"condicion_if : error comparador expresion",
"condicion_if : expresion comparador error",
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
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' CTE_INT ')' bloque_ejecutables",
"iteracion : FOR '(' ID comparador expresion ';' incr_decr CTE_INT ')' bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ';' incr_decr CTE_INT ')' bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ')' bloque_ejecutables",
"iteracion : FOR '(' ID '=' CTE_INT ';' ID comparador expresion ';' incr_decr CTE_INT bloque_ejecutables",
"iteracion : FOR error bloque_ejecutables",
"incr_decr : UP",
"incr_decr : DOWN",
};

//#line 205 "gramatica.y"

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


//#line 511 "Parser.java"
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
//#line 33 "gramatica.y"
{}
break;
case 13:
//#line 34 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Asignacion en la declaración.");}
break;
case 14:
//#line 35 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba un tipo.");}
break;
case 15:
//#line 36 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Tipo no valido.");}
break;
case 16:
//#line 37 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba un identificador.");}
break;
case 17:
//#line 39 "gramatica.y"
{}
break;
case 18:
//#line 40 "gramatica.y"
{}
break;
case 19:
//#line 43 "gramatica.y"
{}
break;
case 20:
//#line 44 "gramatica.y"
{}
break;
case 21:
//#line 47 "gramatica.y"
{System.out.println("procedimiento " + val_peek(9).sval);}
break;
case 22:
//#line 48 "gramatica.y"
{}
break;
case 23:
//#line 51 "gramatica.y"
{}
break;
case 24:
//#line 52 "gramatica.y"
{}
break;
case 25:
//#line 53 "gramatica.y"
{}
break;
case 26:
//#line 54 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 27:
//#line 55 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 28:
//#line 56 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 29:
//#line 57 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \",\" .");}
break;
case 30:
//#line 60 "gramatica.y"
{}
break;
case 31:
//#line 61 "gramatica.y"
{}
break;
case 32:
//#line 62 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba tipo.");}
break;
case 33:
//#line 63 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba tipo.");}
break;
case 34:
//#line 64 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba identificador.");}
break;
case 35:
//#line 67 "gramatica.y"
{}
break;
case 36:
//#line 68 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\"' .");}
break;
case 37:
//#line 69 "gramatica.y"
{}
break;
case 38:
//#line 70 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\" .");}
break;
case 39:
//#line 71 "gramatica.y"
{}
break;
case 40:
//#line 72 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se esperaba \";\"'.");}
break;
case 41:
//#line 73 "gramatica.y"
{}
break;
case 42:
//#line 74 "gramatica.y"
{}
break;
case 43:
//#line 77 "gramatica.y"
{}
break;
case 44:
//#line 78 "gramatica.y"
{System.out.println("Error en la linea " +lex.linea + ": Se encontró == en lugar de =.");}
break;
case 45:
//#line 81 "gramatica.y"
{}
break;
case 46:
//#line 82 "gramatica.y"
{}
break;
case 47:
//#line 83 "gramatica.y"
{System.out.println("una wea termino");}
break;
case 48:
//#line 86 "gramatica.y"
{System.out.println("una wea divisoria entre "  + val_peek(2).sval + " / " + val_peek(0).sval);}
break;
case 49:
//#line 87 "gramatica.y"
{System.out.println("una wea multiplicatoria");}
break;
case 50:
//#line 88 "gramatica.y"
{System.out.println("una wea factor");}
break;
case 51:
//#line 91 "gramatica.y"
{System.out.println("una wea identificatoria");}
break;
case 52:
//#line 92 "gramatica.y"
{
	   		int i = (int) Integer.parseInt(val_peek(0).sval);
	   		if ( i > (int) Math.pow(2, 15) - 1) {
	   			System.out.println("Error en la linea " +lex.linea + ": Constante entera fuera de rango.");
	   		}
		 }
break;
case 53:
//#line 98 "gramatica.y"
{}
break;
case 54:
//#line 99 "gramatica.y"
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
case 55:
//#line 114 "gramatica.y"
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
case 56:
//#line 130 "gramatica.y"
{System.out.println(val_peek(4).sval+" "+val_peek(2).sval); }
break;
case 57:
//#line 131 "gramatica.y"
{System.out.println("una weaaaa if");}
break;
case 58:
//#line 132 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ELSE"); }
break;
case 59:
//#line 133 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición"); }
break;
case 60:
//#line 134 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF");}
break;
case 61:
//#line 135 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de la condición"); }
break;
case 62:
//#line 136 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba END_IF");}
break;
case 63:
//#line 137 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba THEN");}
break;
case 64:
//#line 138 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba THEN");}
break;
case 65:
//#line 139 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": No se encontraron sentencias ejecutables.");}
break;
case 66:
//#line 142 "gramatica.y"
{}
break;
case 67:
//#line 143 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":ERRR.");}
break;
case 68:
//#line 144 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":ERReR.");}
break;
case 69:
//#line 145 "gramatica.y"
{{System.out.println("Error en la linea " + lex.linea + ":ERReR2.");}}
break;
case 70:
//#line 149 "gramatica.y"
{}
break;
case 71:
//#line 150 "gramatica.y"
{}
break;
case 72:
//#line 151 "gramatica.y"
{}
break;
case 73:
//#line 152 "gramatica.y"
{}
break;
case 74:
//#line 153 "gramatica.y"
{}
break;
case 75:
//#line 154 "gramatica.y"
{}
break;
case 76:
//#line 157 "gramatica.y"
{}
break;
case 77:
//#line 160 "gramatica.y"
{}
break;
case 78:
//#line 163 "gramatica.y"
{}
break;
case 79:
//#line 164 "gramatica.y"
{}
break;
case 80:
//#line 165 "gramatica.y"
{}
break;
case 81:
//#line 168 "gramatica.y"
{}
break;
case 82:
//#line 171 "gramatica.y"
{}
break;
case 83:
//#line 172 "gramatica.y"
{}
break;
case 84:
//#line 173 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de los parametros");}
break;
case 85:
//#line 176 "gramatica.y"
{}
break;
case 86:
//#line 177 "gramatica.y"
{}
break;
case 87:
//#line 178 "gramatica.y"
{}
break;
case 88:
//#line 181 "gramatica.y"
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
case 89:
//#line 190 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ; pero se recibio "+ val_peek(7).sval+"." );}
break;
case 90:
//#line 191 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ; pero se recibio "+ val_peek(3).sval+"." );}
break;
case 91:
//#line 192 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaban ; en la sentencia FOR." );}
break;
case 92:
//#line 193 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaban UP o DOWN en la sentencia FOR." );}
break;
case 93:
//#line 194 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta inicialización en la sentencia FOR.");}
break;
case 94:
//#line 195 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta condicion en la sentencia FOR.");}
break;
case 95:
//#line 196 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta incremento en la sentencia FOR.");}
break;
case 96:
//#line 197 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Se esperaba \")\" en la sentencia FOR.");}
break;
case 97:
//#line 198 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Sentencia FOR mal escrita.");}
break;
case 98:
//#line 200 "gramatica.y"
{}
break;
case 99:
//#line 201 "gramatica.y"
{}
break;
//#line 1096 "Parser.java"
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
