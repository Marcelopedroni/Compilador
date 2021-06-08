/**
 * Material de Aula: 
 * Classe Token (entidade)
 * @autor Prof. MSc. Giulliano P. Carnielli
 */
package br.com.compilador.token;

public class Token {
	private TokenType tokenType; // tipo de token
	private String lexema;       // cadeia de caracteres do token
	private long linha;          // linha em que o token ocorre
	private long coluna;         // coluna do 1o caractere do token

	public Token(TokenType tokenType) {
		this(tokenType, "");
	}
	
	public Token(TokenType tokenType, String lexema) {
		this(tokenType, lexema, 0, 0);
	}

	public Token(TokenType tokenType, String lexema, long linha, long coluna) {
		this.tokenType = tokenType;
		this.lexema = lexema;
		this.linha = linha;
		this.coluna = coluna;
	}
	
	/**
	* Método responsável por imprimir os dados do token
	*/
	public void print() {
		String formatTokens = "(%3s,%3s)| %-10s || %-21s |\n";
		
		System.out.printf(formatTokens,this.coluna,this.linha,tokenType.toString(), this.lexema);	

	}
	
	/**
	* getters e setters
	*/
	public TokenType getTokenType() {
		return this.tokenType;
	}

	public String getLexema() {
		return this.lexema;
	}

	public long getLinha() {
		return this.linha;
	}

	public long getColuna() {
		return this.coluna;
	}

	public void setLinha(long linha) {
		this.linha = linha;
	}

	public void setColuna(long coluna) {
		this.coluna = coluna;
	}

}
