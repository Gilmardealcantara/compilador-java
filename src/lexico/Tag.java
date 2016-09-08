package lexico;

public class Tag {

	public final static int
		//Palavras reservadas
		PRG = 256,
		INT = 257,
		FLOAT = 258,
		IF	= 259,
		ELSE = 260,
		REPEAT = 261,
		UNTIL = 262,
		SCAN = 263,
		PRINT = 264,
		
		//Operadores e pontuação
		EQ = 270,
		GE = 271,
		LE = 272,
		NE = 273,
		AND = 276,
		OR = 277,
		
		
		
		//Outros tokens
		NUM = 280,
		ID = 281,
		LITERAL = 282,
		NUM_FLOAT = 283,
		EOF = -1;
		

}
