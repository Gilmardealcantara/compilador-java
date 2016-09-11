package main;

import java.io.IOException;

import lexico.*;

public class Main {
	public static void main(String[] args) throws IOException {
		
		System.out.println("ola");
		Lexer lexer = new Lexer("inputs/teste2.txt");
		System.out.println("sai");
		Token tok = null;
		
		do{
			tok = lexer.scan();
			System.out.println(tok.toString());

		}while(tok.tag != 65535);//????? funfa
	}
}
