package lexico;

import java.io.*;
import java.util.*;

public class Lexer {
	public static int line = 1; //contador de linhas
	private char ch = ' '; //caractere lido do arquivo
	private FileReader file;
	private Hashtable words = new Hashtable();
	
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
				System.out.println("comentario de linha");
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
					System.out.println("comtario de bloco");
					for( ; ; readch())
						if(ch == '\n') line++;
					
						else if (ch == '*'){
							if(readch('/')) break;
						}
				}
		}
		
		if(ch == '"'){
			System.out.println("string");
			StringBuffer sb = new StringBuffer();
			
			do{
				sb.append(ch);
				readch();
			}while(ch != '"');

			String s = sb.toString();
			return new Literal(s);
		}
		
		//Numeros
		if (Character.isDigit(ch)){
			int value=0;
			do{
				value = 10*value + Character.digit(ch,10);
				readch();
			}while(Character.isDigit(ch));
				return new Num(value);
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
		
		//Caracteres nao especificados
		Token t = new Token(ch);
		ch = ' ';
		return t;
	}
	
	public char scan2() throws IOException{
		return (char) file.read();
	}

}
	
	