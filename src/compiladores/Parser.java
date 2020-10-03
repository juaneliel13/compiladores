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
public final static short FIN_PROGRAMA=280;
public final static short END=0;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    1,    1,    1,    2,    2,    3,    3,
    3,    5,    5,    5,    5,    5,    7,    7,    8,    8,
    6,    6,   10,   10,   10,   10,   10,   10,   10,   11,
   11,   11,   11,   11,    4,    4,    4,    4,    4,    4,
    4,    4,   12,   12,    9,    9,    9,   17,   17,   17,
   18,   18,   18,   18,   18,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   19,   19,   19,   19,   22,   22,
   22,   22,   22,   22,   20,   21,   23,   23,   23,   14,
   15,   15,   15,   24,   24,   24,   16,   16,   16,   16,
   16,   16,   16,   16,   16,   25,   25,
};
final static short yylen[] = {                            2,
    1,    1,    2,    1,    2,    1,    1,    1,    2,    1,
    1,    2,    4,    1,    2,    2,    1,    1,    1,    3,
   11,   10,    1,    3,    5,    2,    3,    4,    4,    2,
    3,    2,    1,    3,    2,    1,    1,    2,    1,    2,
    1,    1,    3,    3,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    2,    2,    7,    9,    8,    6,    7,
    8,    9,    6,    8,    3,    3,    3,    3,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    3,    4,    4,
    4,    3,    4,    1,    3,    5,   14,   13,   13,   12,
   13,   10,   10,   11,   13,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   17,   18,    0,    0,    0,    2,    0,
    1,    0,    7,    8,    0,   10,    0,   14,    0,   37,
    0,    0,   42,    0,    3,   15,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    5,    9,   16,    0,   35,
   38,   40,    0,   51,   52,   53,    0,    0,    0,   50,
    0,    0,    0,    0,    0,    0,   20,    0,   82,    0,
    0,   72,   73,   71,   74,   69,   70,    0,   54,   55,
    0,    0,    0,    0,    0,    0,    0,    0,   80,    0,
    0,    0,   33,    0,    0,    0,    0,    0,   83,   81,
    0,    0,    0,    0,    0,   68,    0,   48,   49,    0,
    0,   77,    0,   75,    0,    0,    0,    0,   32,    0,
    0,   30,    0,    0,    0,    0,    0,    0,   59,    0,
    0,   63,    0,    0,    0,   34,   31,    0,    0,    0,
    0,   27,    0,   78,    0,    0,   76,   60,    0,   56,
    0,    0,    0,   96,   97,    0,    0,    0,    0,    0,
    0,   28,   29,   86,   79,   61,    0,   58,   64,    0,
    0,    0,    0,    0,    0,   25,   62,   57,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   93,   92,   22,    0,    0,    0,    0,    0,   94,    0,
   21,    0,   90,    0,    0,    0,   88,   91,    0,   95,
   89,   87,
};
final static short yydgoto[] = {                         10,
   11,   12,   13,  102,   15,   16,   85,   18,   48,   86,
   87,   19,   20,   21,   22,   23,   49,   50,   51,  103,
  136,   68,  137,   60,  147,
};
final static short yysindex[] = {                       219,
    2,  -25,    5,    0,    0,    7, -234,  -40,    0,    0,
    0,  288,    0,    0,   -1,    0, -215,    0,    3,    0,
    8,   13,    0,   30,    0,    0,  178, -179, -174,   60,
  -19,  -19, -157,  -35, -157,    0,    0,    0,   66,    0,
    0,    0,  250,    0,    0,    0, -139,  -29,   41,    0,
  -34,   88,  262,  -36,  -15,  -15,    0,   87,    0,  -22,
  -19,    0,    0,    0,    0,    0,    0,  -19,    0,    0,
  -19,  -19,  -19,  224,  -19,  -19,  -58,  -72,    0, -135,
  -19, -159,    0, -119, -115,  102,   79, -112,    0,    0,
  -15,  -15,  -15,   41,   41,    0,  -15,    0,    0,  -37,
 -171,    0, -106,    0,  -58,  -93,  -30,   10,    0, -208,
  103,    0, -100, -147,  125,  126,  -69,  -58,    0, -101,
  -58,    0,  250, -125,  -94,    0,    0,  -91,  122,  249,
 -147,    0,  -85,    0,   64,  -59,    0,    0,  -58,    0,
  -53,  -51,  -19,    0,    0,  250,  -68,  -62,   90,  -56,
 -147,    0,    0,    0,    0,    0, -243,    0,    0,  311,
  -19,  174,  179,  288,   98,    0,    0,    0,  -94,  -28,
   71,  -58,  -58,  114,  288,  -21,  190, -129,  -58,  -17,
    0,    0,    0,  115,  202,  -58,  229,   21,    0,  234,
    0,  -58,    0,  -58,  245,  -58,    0,    0,  -58,    0,
    0,    0,
};
final static short yyrindex[] = {                         0,
  298,    0,    0,    0,    0,    0,    0,  163,    0,    0,
    0,   11,    0,    0,  206,    0,    0,    0,  107,    0,
  121,  139,    0,  151,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   12,    0,    0,    0,  175,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    0,
    0,    0,    0,    0,   73,   93,    0,   -7,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  258,    0,    0,    0,
  192,  -33,  -32,   25,   49,    0,  -23,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  277,   40,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  293,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
   -2,    0,    0,   27,    0,    0,   20,   42,  462,    0,
  -11,    0,    0,    0,    0,    0,  106,  105,    0,  -55,
  -42,  -13,  442,    0,  -89,
};
final static int YYTABLESIZE=641;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         34,
   47,   25,   34,   33,   84,   59,   78,   67,   66,   36,
    4,    6,  167,   72,   27,   73,  168,   65,   90,   17,
   32,  101,  106,   32,   45,   47,   14,   72,  124,   73,
   66,   17,   67,   84,   74,  148,   89,   30,   14,   81,
   38,   47,   26,   47,   28,   47,   29,  126,   46,  120,
  101,   84,   72,  101,   73,  134,   24,   37,   39,   47,
   47,   40,   47,  127,  101,   45,   41,   45,  125,   45,
  170,   42,   44,   33,   57,  115,   26,  141,  142,  176,
   85,  180,   76,   45,   45,    2,   45,   75,  188,   46,
    3,   46,   43,   46,    6,   52,  157,   53,   85,   54,
  100,  110,  130,  132,    4,    5,   36,   46,   46,  143,
   46,  179,  109,   72,   24,   73,    4,    5,  152,  153,
   39,   82,  114,   47,   83,   47,   61,  117,   79,  178,
   88,   44,  161,   69,   70,    4,    6,  107,   41,  166,
  144,  145,  113,  187,  144,  145,  146,   45,  111,   45,
   19,   43,  118,  119,  138,    2,  112,  139,  140,  116,
    3,  174,   19,  128,    6,  121,  122,  129,  131,  133,
  100,   46,  184,   46,   12,  144,  145,   94,   95,   98,
   99,  149,  150,   17,    2,  105,  154,    2,  155,    3,
   14,   13,    3,    6,   17,   44,    6,   44,    2,  100,
  156,   14,  100,    3,  162,   11,  158,    6,  159,   19,
  163,   19,  164,  100,  172,   43,  165,   43,    9,  173,
  175,   19,   47,   77,   67,   66,   71,    4,    5,   36,
  186,   36,   82,   12,   65,   83,   58,   31,  183,  191,
   31,  123,  192,   39,  177,   39,   62,   63,   64,   65,
   13,  185,   44,   45,   46,  190,   47,   47,   47,   47,
   47,   41,   47,   41,   47,   47,   47,   47,   47,  194,
   47,   47,   47,   24,  196,   19,   47,   47,   47,   47,
   45,   45,   45,   45,   45,  199,   45,   19,   45,   45,
   45,   45,  151,  195,   45,   45,   45,    6,   23,   12,
   45,   45,   45,   45,   46,   46,   46,   46,   46,   66,
   46,   67,   46,   46,   46,   46,   13,   26,   46,   46,
   46,   66,   80,   67,   46,   46,   46,   46,   44,   44,
   11,   44,   44,   24,   44,    0,   44,   44,   44,   44,
  144,  145,    4,    5,   44,    0,    0,   82,   43,   43,
   83,   43,   43,   72,   43,   73,   43,   43,   43,   43,
    0,    0,   36,   36,   43,   36,   36,  101,   36,  169,
   36,   36,   36,   36,    0,    0,   39,   39,   36,   39,
   39,    0,   39,    0,   39,   39,   39,   39,    4,    5,
    0,    0,   39,   82,   41,   41,   83,   41,   41,    0,
   41,    0,   41,   41,   41,   41,   19,   19,    0,    0,
   41,    0,   19,    0,   19,   19,   19,   19,   19,   19,
    0,    0,   19,    0,   19,    0,   19,   19,   19,   19,
   12,   12,    0,   43,   19,    0,   12,    0,   12,   12,
   12,   12,    0,    0,    0,    0,   12,   13,   13,   44,
   45,   46,    0,   13,    0,   13,   13,   13,   13,    0,
    0,   11,   11,   13,    0,    0,    0,   11,    0,   11,
   11,   11,   11,    0,    1,    2,    0,   11,    0,   96,
    3,    0,    4,    5,    6,    7,    0,    0,    0,    0,
    8,    0,   55,   56,    0,   44,   45,   46,    0,    0,
    0,    2,    0,    0,    0,    0,    3,    0,    0,    0,
    6,    0,    4,    5,    0,    0,  100,   82,  104,  104,
   83,    0,   91,    0,    0,   62,   63,   64,   65,   92,
    0,    0,   93,    0,    0,   97,    0,   62,   63,   64,
   65,    0,  108,   35,    2,    0,  104,    0,    0,    3,
    0,    4,    5,    6,    7,    0,    0,    0,  135,    8,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  144,  145,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  160,    0,    0,    0,    0,    0,
    0,    0,    0,  181,  182,    0,    0,    0,    0,    0,
  189,    0,  171,    0,    0,    0,    0,  193,    0,    0,
    0,    0,    0,  197,    0,  198,  200,  201,    0,    0,
  202,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,    0,   40,   44,   41,   41,   41,   41,   41,   12,
    0,    0,  256,   43,   40,   45,  260,   41,   41,    0,
   61,  123,   78,   61,    0,   45,    0,   43,   59,   45,
   60,   12,   62,   41,   48,  125,   59,  272,   12,   53,
  256,   41,    1,   43,   40,   45,   40,  256,    0,  105,
  123,   59,   43,  123,   45,  125,  272,   59,   17,   59,
   60,   59,   62,  272,  123,   41,   59,   43,   59,   45,
  160,   59,    0,   44,   33,   87,   35,  120,  121,  169,
   41,  171,   42,   59,   60,  257,   62,   47,  178,   41,
  262,   43,    0,   45,  266,  275,  139,  272,   59,   40,
  272,   82,  114,  115,  264,  265,    0,   59,   60,  123,
   62,   41,  272,   43,  272,   45,  264,  265,  130,  131,
    0,  269,   44,  123,  272,  125,   61,  101,   41,   59,
   44,   59,  146,  273,  274,  125,  125,  273,    0,  151,
  270,  271,   41,  273,  270,  271,  272,  123,  268,  125,
    0,   59,  259,  260,  256,  257,  272,  259,  260,  272,
  262,  164,    0,   61,  266,  259,  260,  268,   44,   44,
  272,  123,  175,  125,    0,  270,  271,   72,   73,   75,
   76,  273,   61,  164,  257,  258,  272,  257,  125,  262,
  164,    0,  262,  266,  175,  123,  266,  125,  257,  272,
  260,  175,  272,  262,  273,    0,  260,  266,  260,   59,
  273,   61,  123,  272,   41,  123,  273,  125,    0,   41,
  123,   59,   45,  258,  258,  258,  256,  264,  265,  123,
   41,  125,  269,   59,  258,  272,  272,  278,  125,  125,
  278,  272,   41,  123,  273,  125,  276,  277,  278,  279,
   59,  273,  272,  273,  274,  273,  256,  257,  258,  259,
  260,  123,  262,  125,  264,  265,  266,  267,   45,   41,
  270,  271,  272,  272,   41,  125,  276,  277,  278,  279,
  256,  257,  258,  259,  260,   41,  262,  125,  264,  265,
  266,  267,   44,  273,  270,  271,  272,    0,   41,  125,
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
  262,   -1,  264,  265,  266,  267,  256,  257,   -1,   -1,
  272,   -1,  262,   -1,  264,  265,  266,  267,  256,  257,
   -1,   -1,  272,   -1,  262,   -1,  264,  265,  266,  267,
  256,  257,   -1,  256,  272,   -1,  262,   -1,  264,  265,
  266,  267,   -1,   -1,   -1,   -1,  272,  256,  257,  272,
  273,  274,   -1,  262,   -1,  264,  265,  266,  267,   -1,
   -1,  256,  257,  272,   -1,   -1,   -1,  262,   -1,  264,
  265,  266,  267,   -1,  256,  257,   -1,  272,   -1,  256,
  262,   -1,  264,  265,  266,  267,   -1,   -1,   -1,   -1,
  272,   -1,   31,   32,   -1,  272,  273,  274,   -1,   -1,
   -1,  257,   -1,   -1,   -1,   -1,  262,   -1,   -1,   -1,
  266,   -1,  264,  265,   -1,   -1,  272,  269,   77,   78,
  272,   -1,   61,   -1,   -1,  276,  277,  278,  279,   68,
   -1,   -1,   71,   -1,   -1,   74,   -1,  276,  277,  278,
  279,   -1,   81,  256,  257,   -1,  105,   -1,   -1,  262,
   -1,  264,  265,  266,  267,   -1,   -1,   -1,  117,  272,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  270,  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  143,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  172,  173,   -1,   -1,   -1,   -1,   -1,
  179,   -1,  161,   -1,   -1,   -1,   -1,  186,   -1,   -1,
   -1,   -1,   -1,  192,   -1,  194,  195,  196,   -1,   -1,
  199,
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
"FIN_PROGRAMA",
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
"condicion_if : expresion comparador expresion",
"condicion_if : expresion error expresion",
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
"incr_decr : UP",
"incr_decr : DOWN",
};

//#line 203 "gramatica.y"

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
//#line 141 "gramatica.y"
{}
break;
case 66:
//#line 142 "gramatica.y"
{}
break;
case 67:
//#line 143 "gramatica.y"
{}
break;
case 68:
//#line 144 "gramatica.y"
{}
break;
case 69:
//#line 148 "gramatica.y"
{}
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
//#line 156 "gramatica.y"
{}
break;
case 76:
//#line 159 "gramatica.y"
{}
break;
case 77:
//#line 162 "gramatica.y"
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
//#line 167 "gramatica.y"
{}
break;
case 81:
//#line 170 "gramatica.y"
{}
break;
case 82:
//#line 171 "gramatica.y"
{}
break;
case 83:
//#line 172 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ) luego de los parametros");}
break;
case 84:
//#line 175 "gramatica.y"
{}
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
//#line 180 "gramatica.y"
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
case 88:
//#line 189 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ; pero se recibio "+ val_peek(7).sval+"." );}
break;
case 89:
//#line 190 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaba ; pero se recibio "+ val_peek(3).sval+"." );}
break;
case 90:
//#line 191 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaban ; en la sentencia FOR." );}
break;
case 91:
//#line 192 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ": Se esperaban UP o DOWN en la sentencia FOR." );}
break;
case 92:
//#line 193 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta inicialización en la sentencia FOR.");}
break;
case 93:
//#line 194 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta condicion en la sentencia FOR.");}
break;
case 94:
//#line 195 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Falta incremento en la sentencia FOR.");}
break;
case 95:
//#line 196 "gramatica.y"
{System.out.println("Error en la linea " + lex.linea + ":  Se esperaba \")\" en la sentencia FOR.");}
break;
case 96:
//#line 198 "gramatica.y"
{}
break;
case 97:
//#line 199 "gramatica.y"
{}
break;
//#line 1088 "Parser.java"
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
