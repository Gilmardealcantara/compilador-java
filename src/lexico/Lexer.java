package lexico;

import java.io.*;
import java.util.*;

public class Lexer {
	public static int line = 1; //contador de linhas
	private char ch = ' '; //caractere lido do arquivo
	private FileReader file;
	public Hashtable words = new Hashtable();
	
	//metodo construtor
	public Lexer(String fileName) throws FileNotFoundException{
		try{
			file = new FileReader (fileName);
		}
		catch(FileNotFoundException e){
			System.out.println("Arquivo nao encontrado");
			throw e;
		}
		//Insere palavras reservadas na HashTable
		reserve(new Word ("if", Tag.IF));
		reserve(new Word ("else", Tag.ELSE));
		reserve(new Word ("program", Tag.PRG));
		reserve(new Word ("int", Tag.INT));
		reserve(new Word ("float", Tag.FLOAT));
		reserve(new Word ("repeat", Tag.REPEAT));
		reserve(new Word ("until", Tag.UNTIL));
		reserve(new Word ("scan", Tag.SCAN));
		reserve(new Word ("print", Tag.PRINT));
	}
	
	/* Metodo para inserir palavras reservadas na HashTable*/
	private void reserve(Word w){
		words.put(w.getLexeme(), w); // lexema e a chave para entrada na HashTable
	}
	
	
	/*Le o proximo caractere do arquivo*/
	private void readch() throws IOException{
		ch = (char) file.read();
		
	}
	
	/* Le o proximo caractere do arquivo e verifica se e igual a c*/
	private boolean readch(char c) throws IOException{
		readch();
		if (ch != c) return false;
		ch = ' ';
		return true;
	}
	
	public Token scan() throws IOException{
		
		//Desconsidera delimitadores na entrada
		for ( ; ; readch()) {
			if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') continue;
			//para desconsiderar os comentarios de uma linha (%comentatio)
			else if (ch == '%'){
				for(; ; readch()){
					if(ch == '\n'){
						line++;
						break;
					}
				}
			}
			else if (ch == '\n') line++; //conta linhas
			else break;
		}
		switch(ch){
			//Operadores
			case '&':
				if (readch('&')) return Word.and;
				else return new Token('&');
			
			case '|':
				if (readch('|')) return Word.or;
				else return new Token('|');
			
			case '=':
				if (readch('=')) return Word.eq;
				else return new Token('=');
			
			case '<':
				if (readch('=')) return Word.le;
				else return new Token('<');
			
			case '>':
				if (readch('=')) return Word.ge;
				else return new Token('>');
		}
		
		//Desconsidera os comentarios de bloco
		if(ch == '/'){
				if(readch('*')){
					for( ; ; readch()){
						//reconhece fim de arquivo se não encontrar */
						if(ch == 65535){
							ch=0;
							Token t = new Token(ch);
							ch = ' ';
							return t;
						}
						
						if(ch == '\n') line++;
						
						else if (ch == '*'){
							if(readch('/')) break;
						}
					}
				}
		}
				
		//Literais
		if(ch == '"'){
			StringBuffer sb = new StringBuffer();
			
			do{
				sb.append(ch);
				readch();
			}while(ch != '"');
			sb.append(ch);
			readch();
			String s = sb.toString();
			return new Literal(s);
		}
		
		//Numeros
		if (Character.isDigit(ch)){
			StringBuffer sb = new StringBuffer();
			sb.append(ch);
			
			if(ch == '0'){
				readch();
				//se o proximo caracter não for um digito (pode ser outro ou .)
				if(!Character.isDigit(ch)){
					//float do tipo 0.{digit}
					if(ch == '.'){
						sb.append(ch);
						readch();
						//tem que vir um digito depois do ponto
						if(Character.isDigit(ch)){
							do{
								sb.append(ch);
								readch();
							}while(Character.isDigit(ch));
							String s = sb.toString();
							float f = Float.parseFloat(s);
							return new Numfloat(f);
						}else{
							System.out.println("ERRO: Token mal formado");
							System.exit(1);
						}
					}else{
					// se depois do zero não vem ponto e não tem digito, e inteiro
						String s = sb.toString();
						int n = Integer.parseInt(s);
						return new Num(n);
					}
				}else{ //se o proximo caracter for um digito so pode ser float
					do{
						sb.append(ch);
						readch();
					}while(Character.isDigit(ch));
					//int não pode começar com 0 e ser seguido de digitos
					if(ch != '.'){
						System.out.println("ERRO: Token mal formado");
						System.exit(1);
					}else{
						sb.append(ch);
						readch();
						//tem que vir um digito depois do ponto
						if(Character.isDigit(ch)){
							do{
								sb.append(ch);
								readch();
							}while(Character.isDigit(ch));
							String s = sb.toString();
							float f = Float.parseFloat(s);
							return new Numfloat(f);
						}else{
							System.out.println("ERRO: Token mal formado");
							System.exit(1);
						}
					}
				}
			// se não começar com 0	
			}else{
				do{
					sb.append(ch);
					readch();
				}while(Character.isDigit(ch));
				
				//se vier . 
				if(ch == '.'){
					sb.append(ch);
					readch();
					if(Character.isDigit(ch)){
						do{
							sb.append(ch);
							readch();
						}while(Character.isDigit(ch));
						
						String s = sb.toString();
						float f = Float.parseFloat(s);
						return new Numfloat(f);
					}else{
						System.out.println("ERRO: Token mal formado");
						System.exit(1);
					}
				}else{
					String s = sb.toString();
					int n = Integer.parseInt(s);
					return new Num(n);
				}
			}
		}
		
		
		//Identificadores
		if (Character.isLetter(ch)){
			StringBuffer sb = new StringBuffer();
			do{
				sb.append(ch);
				readch();
			}while(Character.isLetterOrDigit(ch));
		
			String s = sb.toString();
			Word w = (Word)words.get(s);
			
			if (w != null) return w; //palavra ja existe na w; //palavra ja existe na HashTable HashTable
			
			w = new Word (s, Tag.ID);
			words.put(s, w);
			return w;
		}
		///EOF
		if(ch == 65535){
			ch = 0;
		}
		
		//Caracteres nao especificados
		Token t = new Token(ch);
		ch = ' ';
		return t;
	}

}
	
	