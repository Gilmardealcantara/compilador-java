package main;

import java.io.IOException;

import lexico.*;

public class Main {
	public static void main(String[] args) throws IOException {
		Lexer lexer = new Lexer("imputs/teste1.txt");
		Token tok = null;
		
		do{
			tok = lexer.scan();
			System.out.println(tok.toString());

		}while(tok.tag != 65535);//????? funfa
		
	}
}
