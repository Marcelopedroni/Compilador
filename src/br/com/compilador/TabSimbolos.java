/**
 * Material de Aula: 
 * Classe TabSimbolos (estrutura auxiliar)
 * @autor Prof. MSc. Giulliano P. Carnielli
 */
package br.com.compilador;

import java.util.HashMap;
import java.util.Map;

import br.com.compilador.token.Token;
import br.com.compilador.token.TokenType;

public class TabSimbolos {

	private static TabSimbolos instance = new TabSimbolos();
	private Map<String, Token> tab;
	
	private TabSimbolos() {
		tab = new HashMap<String, Token>();

		tab.put("true", new Token(TokenType.LOGIC_VAL, "true"));
		tab.put("false", new Token(TokenType.LOGIC_VAL, "false"));
		tab.put("not", new Token(TokenType.LOGIC_OP, "not"));
		tab.put("and", new Token(TokenType.LOGIC_OP, "and"));
		tab.put("or", new Token(TokenType.LOGIC_OP, "or"));
		tab.put("bool", new Token(TokenType.TYPE, "bool"));
		tab.put("text", new Token(TokenType.TYPE, "text"));
		tab.put("int", new Token(TokenType.TYPE, "int"));
		tab.put("float", new Token(TokenType.TYPE, "float"));
		tab.put("program", new Token(TokenType.PROGRAM, "program"));
		tab.put("end_prog", new Token(TokenType.END_PROG, "end_prog"));
		tab.put("begin", new Token(TokenType.BEGIN, "begin"));
		tab.put("end", new Token(TokenType.END, "end"));
		tab.put("if", new Token(TokenType.IF, "if"));
		tab.put("then", new Token(TokenType.THEN, "then"));
		tab.put("else", new Token(TokenType.ELSE, "else"));
		tab.put("for", new Token(TokenType.FOR, "for"));
		tab.put("while", new Token(TokenType.WHILE, "while"));
		tab.put("declare", new Token(TokenType.DECLARE, "declare"));
		tab.put("to", new Token(TokenType.TO, "to"));
	}
	
	public static TabSimbolos getInstance() {
		return instance;
	}
	
	public Token addToken(String lexema, long linha, long coluna) {
		Token token = null;

		if (tab.containsKey(lexema)) {
			token = tab.get(lexema);
			token.setLinha(linha);
			token.setColuna(coluna);
		} else {
			token = new Token(TokenType.ID, lexema, linha, coluna);
			tab.put(lexema, token);
		}

		return token;
	}
	
	public void printReport()
	{
		cabecalhoTabela();
		String formatTab = "| %-15s || %-16s |\n";
		
		tab.forEach((Lexema, token) -> {
			   System.out.printf(formatTab, token.getTokenType(), Lexema );
		});
		System.out.println("---------------------------------------");
	}

	private void cabecalhoTabela()
	{
		System.out.println("\n---------------------------------------");
		System.out.printf("|%27s %10s","Tabela de Simbolos","|");
		System.out.println("\n---------------------------------------");
		System.out.println("|      Token      ||      Lexema      |");
		System.out.println("---------------------------------------");
	}

}
