package main;

import java.io.IOException;

import lexico.*;

public class Main {
	public static void main(String[] args) throws IOException {
		
		Lexer lexer = new Lexer("inputs/teste2.txt");
		Token tok = null;
		
		do{
			tok = lexer.scan();
			System.out.println(tok.toString());

		}while(tok.tag != 0);//????? funfa
		System.out.println("Alnalise lexica completa!!");
	}
}
