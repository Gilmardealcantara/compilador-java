package sintatico;

import java.io.FileNotFoundException;
import java.io.IOException;

import lexico.*;

public class Syntactic {
	public Token tok = null;
	Lexer lexer;
	
	public Syntactic() throws IOException {
		this.lexer = new Lexer("inputs/teste1.txt");
		this.tok = lexer.scan(); // traz primeiro token
	}

	private void error(){
		System.out.println("Correee negada....");
		 System.exit(0);
	}
	
	private void advance() throws IOException{
		tok = this.lexer.scan();
	}

	private void eat(Token t) throws IOException{
		if(tok.tag == t.tag) this.advance();
		else this.error();
	}
	
	public void exec(){
		//chama program function
	}
}
