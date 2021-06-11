/**
 * Material de Aula: 
 * Classe Lexico (analisador)
 * @autor Prof. MSc. Giulliano P. Carnielli
 */
package br.com.compilador.analisadores;

import java.io.EOFException;
import java.io.IOException;

import br.com.compilador.TabSimbolos;
import br.com.compilador.token.Token;
import br.com.compilador.token.TokenType;
import br.com.compilador.utils.ErrorHandler;
import br.com.compilador.utils.ErrorType;
import br.com.compilador.utils.FileLoader;

public class Lexico {
	private FileLoader fileLoader;
	private StringBuilder lexema = null;
	private char caracter;
	private long tk_col;
	private long tk_lin;

	public Lexico(String filename) throws IOException {
		fileLoader = new FileLoader(filename);
	}

	public Token nextToken() {
		Token token = null;

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
		try {
			char c = getNextChar();
			//Tratando o caso $df
			if (c == 'd') {
				c = getNextChar();
				if (c != 'f') {
					ErrorHandler.getInstance().addCompilerError(ErrorType.LEXICO, 
																lexema.toString(),
																"Operador relacional inválido",
																tk_lin, tk_col);	
					resetLastChar();
					return null;
				}
			}
			//Tratando o caso $eq
			else if (c == 'e') {
				c = getNextChar();
				if (c != 'q') {
					ErrorHandler.getInstance().addCompilerError(ErrorType.LEXICO, 
																lexema.toString(),
																"Operador relacional inválido",
																tk_lin, tk_col);	
					resetLastChar();
					return null;
				}
			}
			//Tratando casos $gt, $ge, $lt e $le
			else if (c == 'g' || c == 'l') {
				c = getNextChar();
				if (c != 'e' && c != 't') {
					ErrorHandler.getInstance().addCompilerError(ErrorType.LEXICO, 
																lexema.toString(),
																"Operador relacional inválido",
																tk_lin, tk_col);
					resetLastChar();
					return null;
				}
			}
			//Tratando casos onde há erros de digitação.
			else {	
					ErrorHandler.getInstance().addCompilerError(ErrorType.LEXICO, 
																lexema.toString(),
																"Operador relacional inválido",
																tk_lin, tk_col);
					resetLastChar();
					return null;
			}
		}
		catch (EOFException e){
			fileLoader.resetLastChar();	
		}	
		return new Token(TokenType.RELOP, lexema.toString(), tk_lin, tk_col);
	}
	
	private Token processaAssign() throws IOException {
		char c = getNextChar();
		try {
			if (c != '-') {
				// Registra Erro Léxico
				ErrorHandler.getInstance().addCompilerError(ErrorType.LEXICO, 
															lexema.toString(),
															"Operador inválido",
															tk_lin, tk_col);
				resetLastChar();
				return null;
			}
		}
		catch (EOFException e) {
			fileLoader.resetLastChar();
		}
		return new Token(TokenType.ASSIGN, lexema.toString(), tk_lin, tk_col);
	}

	private Token processaNum() throws IOException {
        Token token = null;
        try
		{
        	char c = getNextChar();
        	while (Character.isDigit(c)) {
        		 c = getNextChar();
            }
        	// Números Float
        	if (c == '.') {
				c = getNextChar();
				// Após o '.' é obrigatório haver pelo menos um número, senão quebra.
				if (!Character.isDigit(c)) {
					ErrorHandler.getInstance().addCompilerError(ErrorType.LEXICO, 
																lexema.toString(),
																"Número inválido",
																tk_lin, tk_col);
					resetLastChar();
					return null;
				}
				// Após o '.' leu um número.
				else {
					while (Character.isDigit(c)) {
						c = getNextChar();
					}
					// Saiu do while, ou leu 'E' ('e') ou o número acabou.
					if (c == 'E' || c == 'e') {
						c = getNextChar();
						// Se leu um 'E' ou 'e' é obrigatório que o próximo char seja um número, senão quebra.
						if (!Character.isDigit(c)) {
							ErrorHandler.getInstance().addCompilerError(ErrorType.LEXICO, 
																		lexema.toString(),
																		"Número inválido",
																		tk_lin, tk_col);
							resetLastChar();
							return null;
						}
						while (Character.isDigit(c)) {
							c = getNextChar();
						}
						// Saiu do while, o número acabou, retorna Token Num_Float.
						resetLastChar();
						token = TabSimbolos.getInstance().addTokenNum_Float(lexema.toString(), tk_lin, tk_col);
					}
					
					// Não leu 'E' ou 'e', ou seja o número acabou, retornando Token Num_Float. Ex: 1.12.
					else {
						resetLastChar();
						token = TabSimbolos.getInstance().addTokenNum_Float(lexema.toString(), tk_lin, tk_col);
					}
				}
			}
        	// Números Inteiros
        	// Saiu do 1º while, pode ter lido 'E', 'e' ou o número acabou.
        	// Se leu 'E' ou 'e'.
        	else if (c == 'E' || c == 'e') {
        		c = getNextChar();
        		// Após ler 'E' ou 'e' é obrigatório ler número, senão quebra.
				if (!Character.isDigit(c)) {
					ErrorHandler.getInstance().addCompilerError(ErrorType.LEXICO, 
																lexema.toString(),
																"Número inválido",
																tk_lin, tk_col);
					resetLastChar();
					return null;
				}
				// Lendo todos números subsequentes ao 'E' ou 'e'.
				while (Character.isDigit(c)) {
					c = getNextChar();
				}
				// Leu qualquer coisa que não números, indicando fim do número, retornando o Token Num_Int
				resetLastChar();
				token = TabSimbolos.getInstance().addTokenNum_Int(lexema.toString(), tk_lin, tk_col);
        	}
        	// Não leu 'E', 'e' ou '.', indicando fim do número inteiro, retornando Token Num_Int.
        	else {
        		resetLastChar();
				token = TabSimbolos.getInstance().addTokenNum_Int(lexema.toString(), tk_lin, tk_col);
        	}
		}
        catch (EOFException e)
		{
        	// Quebra de padrão provocado pelo fim do arquivo
        	// Ainda retornaremos o token
        	fileLoader.resetLastChar();
		}
        
        return token;
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
}
