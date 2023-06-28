package lexico;

import static lexico.Tokens.*;



%%
%class Lexer
%type Tokens
%public


L=[a-zA-z_]+
M=[A-Z]+
D=[0-9]+
LD={L}|{D}


NUMERO =({D})+ | ({D})+.({D})+
ATRIBUTO ={L}({LD} | [_])*
SALTO =\n|\r|\r\n
ESPACIO =[ \t\n]+
CADENA = \" ({NUMERO} | {LD}| _|-|%)* \"
IDENTIFICADOR={L}({LD} | [_])*
TAB={M}
CAMPO={L}*

%{
    public String lexeme;
%}


%%

while {lexeme=yytext();  return Reservada;}

{ESPACIO} {/*Ignore*/}

	"Π"		{lexeme=yytext(); return PROY;}
	"σ"		{lexeme=yytext(); return SELECT;}
	"∩"		{lexeme=yytext(); return INTERSECCION;}
	"∪"		{lexeme=yytext(); return UNION;}
        "∪B"            {lexeme=yytext(); return UNIONBAG;}
	"*"		{lexeme=yytext(); return CRUZ;}
	"-"		{lexeme=yytext(); return DIFERENCIA;}
	"JOIN"	        {lexeme=yytext(); return JOIN;}
	","		{lexeme=yytext(); return COMA;}
	"."		{lexeme=yytext(); return PUNTO;}
	"("		{lexeme=yytext(); return PAR_I;}
        "(("            {lexeme=yytext(); return PAR_II;}
	")"		{lexeme=yytext(); return PAR_D;}
        "))"            {lexeme=yytext(); return PAR_DD;}
	"&"		{lexeme=yytext(); return AND;}
	"|"		{lexeme=yytext(); return OR;}
	"~"		{lexeme=yytext(); return NOT;}
	">"		{lexeme=yytext(); return MAYOR;}
	">="	        {lexeme=yytext(); return MAYORIGUAL;}
	"<"		{lexeme=yytext(); return MENOR;}
	"<="	        {lexeme=yytext(); return MENORIGUAL;}
	"="		{lexeme=yytext(); return IGUAL;}
	"<>"	        {lexeme=yytext(); return DIFERENTE;}
	"LIKE"	        {lexeme=yytext(); return COMO;}
	{TAB}	        {lexeme=yytext(); return TABLA;}
	{IDENTIFICADOR}	{lexeme=yytext(); return IDENTIFICADOR;}
	{SALTO}         {}
	{NUMERO}        {lexeme=yytext(); return NUMERO;}
	{CADENA}	{lexeme=yytext(); return CADENA;}
        {CAMPO}         {lexeme=yytext(); return CAMPO;}
	{ESPACIO}	{}
        .  {return ERROR;}