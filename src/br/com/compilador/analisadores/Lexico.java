/**
 * Material de Aula: 
 * Classe Lexico (analisador)
 * @autor Prof. MSc. Giulliano P. Carnielli
 */
package br.com.compilador.analisadores;

import java.io.EOFException;
import java.io.IOException;
import java.util.List;

import br.com.compilador.TabSimbolos;
import br.com.compilador.symbols.FirstFollowSets;
import br.com.compilador.symbols.NonTerminalType;
import br.com.compilador.symbols.Token;
import br.com.compilador.symbols.TokenType;
import br.com.compilador.utils.ErrorHandler;
import br.com.compilador.utils.ErrorType;
import br.com.compilador.utils.FileLoader;

public class Lexico {
	private Token buffer;
	private FileLoader fileLoader;
	private StringBuilder lexema;
	private char caracter;
	private long tk_col;
	private long tk_lin;

	public Lexico(String filename) throws IOException {
		fileLoader = new FileLoader(filename);
	}

	public void storeToken(Token t) {
		this.buffer = t;
	}
	
	public Token nextToken() {
		Token token = null;

		if (buffer != null) {
			token = buffer;
			buffer = null;
			return token;
		}
		
		try {
			// Trata entrada até encontrar um token
			while (token == null) {
				// Remove espaços em branco no início do reconhecimento
				do {
					caracter = fileLoader.getNextChar();
				} while (Character.isWhitespace(caracter));

				lexema = new StringBuilder();
				// Posição inicial do possível token
				tk_lin = fileLoader.getLine();
				tk_col = fileLoader.getColumn();
				
				// Apenda 1o caracter do lexema
				lexema.append(caracter);
				
				switch(caracter) {
				case '+':
				case '-':
					token = new Token(TokenType.ARIT_AS, lexema.toString(), tk_lin, tk_col);
					break;
				case '*':
				case '/':
					token = new Token(TokenType.ARIT_MD, lexema.toString(), tk_lin, tk_col);
					break;
				case ';':
					token = new Token(TokenType.TERM, lexema.toString(), tk_lin, tk_col);
					break;
				case '(':
					token = new Token(TokenType.L_PAR, lexema.toString(), tk_lin, tk_col);
					break;
				case ')':
					token = new Token(TokenType.R_PAR, lexema.toString(), tk_lin, tk_col);
					break;
				case '{': 
					processaComentario();
					break;
				case '"': 
					token = processaLiteral();
					break;
				case '$': 
					token = processaRelop();
					break;
				case '<':
					token = processaAssign();
					break;
				default:
					if (Character.isLetter(caracter) || caracter == '_') { 
						token = processaID(); 
					} else if (Character.isDigit(caracter)) { 
						token = processaNum(); 
					} else {
						// Registra erro (Léxico)
						ErrorHandler.getInstance().addCompilerError(ErrorType.LEXICO, 
                                lexema.toString(),
                                "Caracter inválido",
                                tk_lin, tk_col);
					}
				}
			}
			
		} catch (EOFException eof) {
			// Cada método deve tratar a possibilidade de um fim de arquivo apropriadamente
			// Se o fim de arquivo ocorre no início do processamento do token, então, isso significa
			// que devemos encerrar retornando um 'Token EOF'
			token = new Token(TokenType.EOF);
		} catch (IOException io) {
			// Registra erro (Processamento)
			ErrorHandler.getInstance().addCompilerError(ErrorType.PROCESSAMENTO, 
                                                        "",
                                                        "Erro ao acessar o arquivo",
                                                        tk_lin, tk_col);
			token = new Token(TokenType.EOF, "Erro de processamento");
		}
		return token;
	}
	
	private char getNextChar () throws IOException {
		char c = fileLoader.getNextChar();
		lexema.append(c);
		return c;
	}
	
	private void resetLastChar() throws IOException {
		fileLoader.resetLastChar();
    	lexema.deleteCharAt(lexema.length() - 1);		
	}
	
	/**
	* metodo responsavel por ignorar o comentario no codigo
	*/
	private void processaComentario() throws IOException {
		try {
			char c = getNextChar();
			if (c != '#') {
				// Registra erro, reseta lexema e reinicia
				ErrorHandler.getInstance().addCompilerError(ErrorType.LEXICO, 
	                                                        lexema.toString(),
	                                                        "Comentário mal formatado",
	                                                        tk_lin, tk_col);			
			} else {
				do {
					do {
						c = getNextChar();
					} while (c != '#');
					c = getNextChar();
				} while (c != '}');
			}
		} catch (EOFException e) {
			// Gera Erro, pois se um EOF ocorre, significa que o comentário não foi fechado
			ErrorHandler.getInstance().addCompilerError(ErrorType.LEXICO, 
                                                        lexema.toString(),
                                                        "Comentário não finalizado.",
                                                        tk_lin, tk_col);
			fileLoader.resetLastChar();
		}
	}
	
	private Token processaLiteral() throws IOException {
		char c = getNextChar();
		try {
			while (c != '"' && c != '\n') {
				c = getNextChar();
			}
		} catch (EOFException eof) {
			// Adiciona espaço para tornar o tratamento homogêneo
			lexema.append(" ");
		}
		
		if (c != '"') {
			resetLastChar();
			// Registra Erro Léxico
			ErrorHandler.getInstance().addCompilerError(ErrorType.LEXICO, 
                                                        lexema.toString(),
                                                        "Literal não finalizado",
                                                        tk_lin, tk_col);
			return null;
		}
		
		return new Token(TokenType.LITERAL, lexema.toString(), tk_lin, tk_col);
	}	

	private Token processaRelop() throws IOException {
		return new Token(TokenType.RELOP, "RELOP STUB");
	}
	
	private Token processaAssign() throws IOException {
		char c = getNextChar();
		if (c != '-') {
			// Registra Erro Léxico
			ErrorHandler.getInstance().addCompilerError(ErrorType.LEXICO, 
                                                        lexema.toString(),
                                                        "Operador inválido",
                                                        tk_lin, tk_col);
			return null;
		}
		return new Token(TokenType.ASSIGN, lexema.toString(), tk_lin, tk_col);
	}
	
	private Token processaNum() throws IOException {
		return new Token(TokenType.NUM_INT, "NUM_INT STUB");
	}
	
	private Token processaID() throws IOException {
        Token token = null;
        try
		{
        	char c = getNextChar();
        	while (Character.isLetter(c) || c == '_' || Character.isDigit(c)) {
        		 c = getNextChar();
            }
			resetLastChar();
		}
        catch (EOFException e)
		{
        	// Quebra de padrão provocado pelo fim do arquivo
        	// Ainda retornaremos o token
        	fileLoader.resetLastChar();
		}
        
        token = TabSimbolos.getInstance().addToken(lexema.toString(), tk_lin, tk_col);
        return token;
	}
	
	public Token sincronizaEmTokens(List<TokenType> tokens) {
		Token t = null;
		if (tokens == null) return t;
		do {
			t = nextToken();
		} while(!tokens.contains(t.getTokenType()));
		return t;
	}
	
	public Token sincronizaEmFollow(NonTerminalType nt) {
		List<TokenType> l = FirstFollowSets.getInstance().getFollowFromNT(nt);
		return sincronizaEmTokens(l);
	}
}
