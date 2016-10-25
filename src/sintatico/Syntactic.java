package sintatico;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.omg.CORBA.portable.IndirectionException;

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
			eat(Tag.ID); eat(','); ident_list();
		}
	}
	
	void type() throws IOException{
		
		switch(tok.tag){
			case Tag.INT: eat(Tag.INT); break;
			case Tag.FLOAT: eat(Tag.FLOAT); break;
			default: error();
		}
		
	}
	
	void stmt_list() throws IOException{
		//stmt-list		::= stmt ";" { stmt ";"}
		// regra acima tem repetição ate encontrar um } ou until (repeat_suffix)
		//program		::= program [decl-list] "{" stmt-list "}"
		//if-stmt			::= if condition "{" stmt-list "}" if-stmt’
		//repeat-stmt		::= repeat stmt-list repeat-suffix
		while(tok.tag != '}' || tok.tag != Tag.UNTIL){
			stmt(); eat(';');
		}
	}
	
	void stmt() throws IOException{
		switch(tok.tag){
			case Tag.IF: if_stmt(); break;
			case Tag.REPEAT: repeat_stmt(); break;
			case Tag.SCAN: read_stmt(); break;
			case Tag.PRINT: write_stmt(); break;
			default: assign_stmt(); 
		}
	}
	
	void assign_stmt() throws IOException{
		eat(Tag.ID); eat('='); simple_expr();
	}
	
	void if_stmt() throws IOException{
		eat(Tag.IF); condition(); eat('{'); 
		stmt_list(); eat('}'); if_stmt_quote();
	}

	void repeat_stmt() throws IOException{
		eat(Tag.REPEAT); stmt_list(); repeat_suffix();
	}
	
	void read_stmt() throws IOException{
		eat(Tag.SCAN); eat('('); eat(Tag.ID); eat(')');
	}
	
	void write_stmt() throws IOException{
		eat(Tag.PRINT); eat('('); writable(); eat(')');
	}
	
	void simple_expr() throws IOException{
		term(); simple_expr_quote();
	}
	
	void simple_expr_quote() throws IOException{
		
	}
		
	void condition() throws IOException{
		expression();
	}
	
	void if_stmt_quote() throws IOException{
		
	}
	
	void expression() throws IOException{
		simple_expr(); expression_quote();
	}
	
	void repeat_suffix() throws IOException{
		eat(Tag.UNTIL); condition();
	}
	
	void writable() throws IOException{
		simple_expr();
	}
		
	void expression_quote() throws IOException{
		
	}
	
	void term() throws IOException{
		factor_a(); term_quote();
	}
	
	void factor_a() throws IOException{
		switch(tok.tag){
			case '!': eat('!'); factor(); break;
			case '-': eat('-'); factor(); break;
			default: factor();
		}
	}
	
	void term_quote() throws IOException{
		
	}
	
	void factor() throws IOException{
		switch(tok.tag){
			case Tag.ID: eat(Tag.ID); break;
			case Tag.NUM: constant(); break;
			case Tag.NUM_FLOAT: constant(); break;
			case Tag.LITERAL: constant(); break;
			case '(': eat('('); expression(); eat(')'); break;
			default: error();
		}
	}
	
	void relop() throws IOException{
		switch(tok.tag){
			case '>': eat('>'); break;
			case '<': eat('<'); break;
			case Tag.EQ: eat(Tag.EQ); break;
			case Tag.GE: eat(Tag.GE); break;
			case Tag.LE: eat(Tag.LE); break;
			case Tag.NE: eat(Tag.NE); break;
			default: error();
		}
	}
	
	void addop() throws IOException{
		switch(tok.tag){
			case '+': eat('+'); break;
			case '-': eat('-'); break;
			case Tag.OR: eat(Tag.OR); break;
			default: error();
		}
	}
	
	void mulop() throws IOException{
		switch(tok.tag){
			case '*': eat('*'); break;
			case '/': eat('/'); break;
			case Tag.AND: eat(Tag.AND); break;
			default: error();
		}
	}
	
	void constant() throws IOException{
		switch(tok.tag){
			case Tag.NUM: eat(Tag.NUM); break;
			case Tag.NUM_FLOAT: eat(Tag.NUM_FLOAT); break;
			case Tag.LITERAL: eat(Tag.LITERAL); break;
			default: error();
		}
	}
	
	
}
