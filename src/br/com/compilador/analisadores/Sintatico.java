/**
 * Material de Aula: 
 * Classe Sintático (analisador)
 * @autor Prof. MSc. Giulliano P. Carnielli
 */
package br.com.compilador.analisadores;

import java.io.IOException;

import br.com.compilador.TabSimbolos;
import br.com.compilador.symbols.Token;
import br.com.compilador.symbols.TokenType;
import br.com.compilador.utils.ErrorHandler;

public class Sintatico
{

	private Lexico lexico;
	private Token token = null;

	public Sintatico(String filename) throws IOException
	{
		lexico = new Lexico(filename);
	}

	public void processar()
	{  
		derivaS();
		
		// TODO: Código de teste para exercitar o Analisador Léxico
		/*
		System.out.println("------------------------------------------------");
		System.out.println("( X , Y )|   Token    ||          Lexema       |");
		System.out.println("------------------------------------------------"); 
		do  { 
			token = lexico.nextToken(); 
			token.print(); 
		} while (token.getTokenType() != TokenType.EOF);
		System.out.println("------------------------------------------------");

		TabSimbolos.getInstance().printReport(); 
		*/
		
		ErrorHandler.getInstance().gerarRelatorio();
	}
	
	private void derivaS() {
		Token t = lexico.nextToken();
/*
  if (t != program) {
     geraErro()
     while (t != term) {
         t = next()
     }
  } else {
     t = next()
     if (t != id) {
        geraErro()
        while (t != term) {
           t = next()
        } 
     } else {
        t = next()
        if (t != term) geraErro()
     }
  }
  derivaBloco()
  t = next()
  if (t != end_prog) geraErro()
  t = next()
  if (t != term) geraErro()
 */
	}
	
	private void derivaBLOCO() {
		
	}

	private void derivaCMDS() {
		
	}
	
	private void derivaCMD() {
		
	}
	
}
