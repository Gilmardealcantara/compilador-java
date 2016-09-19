package main;

import java.util.Scanner;
import java.io.IOException;

import lexico.*;

public class Main {
	public static void main(String[] args) throws IOException {
	
		// cont é string para evitar atribuição por referência entre ints do Java
		String cont;
		while(true){
			Scanner entrada = new Scanner (System.in);
			System.out.println("Indique 1 teste: (example '1', s para sair)");
			String teste = entrada.nextLine();
			
			if(teste.equals("s")) System.exit(0);
			
			Lexer lexer = new Lexer("inputs/teste"+teste+".txt");
			lexer.line=1;
			Token tok = null;

			System.out.print("\nLinha " + lexer.line + ":");
			do{

				cont = lexer.line+ "";
				tok = lexer.scan();
				if(lexer.line == Integer.parseInt(cont))
					System.out.print(tok.toString()+" ");
				else
					System.out.print("\nLinha " + lexer.line+ ": " + tok.toString());
			}while(tok.tag != 0);//????? funfa
			System.out.println("Analise lexica completa!!\n\n");
		}
	}
}
