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

	private void eat(int t) throws IOException{
		if(tok.tag == t) this.advance();
		else this.error();
	}
	
	public void exec(){
		//chama program function
	}
	
	void program() throws IOException{
		eat(Tag.PRG); 
		
		// decl_list e opcional
		switch(tok.tag){
			case '{' : eat('{'); stmt_list(); eat('}'); break;
			default : decl_list(); eat('{'); stmt_list(); eat('}'); 
		}
		
	}
	
	void decl_list() throws IOException{
		//decl-list		::= decl ";" { decl ";"}
		/* repete decl; ate que encontra um {. Quando encontra, volta
		 * para program e le o resto dos tokens*/
		
		switch(tok.tag){
			case '{': return ;
			default: decl(); eat(';');  decl_list();
		}
	}
	
	void decl() throws IOException{
		ident_list(); type();
	}
	
	void ident_list() throws IOException{
		// ident-list		::= identifier {"," identifier}
		// Repete identifier(); ate que encontra um type
		
		if(tok.tag == Tag.INT || tok.tag == Tag.FLOAT){
			return;
		}else{
			identifier(); eat(','); ident_list();
		}
	}
	
	void type() throws IOException{
		
		switch(tok.tag){
			case Tag.INT: eat(Tag.INT); break;
			case Tag.FLOAT: eat(Tag.FLOAT); break;
			default: error();
		}
		
	}
	
	void identifier() throws IOException{
		
	}
	
	void stmt_list() throws IOException{
		
		
	}
	
	
}
