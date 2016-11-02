package main;

import java.util.Scanner;
import java.io.IOException;

import lexico.*;
import sintatico.*;

public class Main {
	public static void main(String[] args) throws IOException {
		while(true){
			Scanner entrada = new Scanner (System.in);
			System.out.println("Indique 1 teste: (examplo '1', s para sair)");
			String teste = entrada.nextLine();
			
			if(teste.equals("s")) System.exit(0);
			
			Syntactic sync = new Syntactic(teste);
			sync.exec();
		}
		
	}
}