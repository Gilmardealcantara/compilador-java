package sintatico;

import java.io.FileNotFoundException;
import java.io.IOException;

import lexico.*;

public class Syntactic {

	public Syntactic() {
		// TODO Auto-generated constructor stub
	}
	
	public void exec() throws IOException{
		Lexer lexer = new Lexer("inputs/teste1.txt");
		Token tok = null;
		
		do{
	
			tok = lexer.scan();

		}while(tok.tag != 0);
	
	}
}
