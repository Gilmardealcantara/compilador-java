package main;

import java.util.Scanner;
import java.io.IOException;

import lexico.*;

public class Main {
	public static void main(String[] args) throws IOException {
	
		
		while(true){
			Scanner entrada = new Scanner (System.in);
			System.out.println("Indique 1 teste: (example '1', s para sair)");
			String teste = entrada.nextLine();
			if(teste == "s") break;
			
			Lexer lexer = new Lexer("inputs/teste"+teste+".txt");
			Token tok = null;
			
			do{
				tok = lexer.scan();
				System.out.println(tok.toString());
			}while(tok.tag != 0);//????? funfa
			System.out.println("Analise lexica completa!!\n\n");
		}
	}
}
