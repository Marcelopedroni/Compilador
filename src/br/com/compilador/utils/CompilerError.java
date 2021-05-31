/**
 * Material de Aula: 
 * Classe Error (entidade)
 * @autor Prof. MSc. Giulliano P. Carnielli
 */
package br.com.compilador.utils;

public class CompilerError
{
	private ErrorType errorType;
	private String lexema;
	private String msg;
	private long linha;
	private long coluna;
	
	public CompilerError(ErrorType errorType, String lexema, String msg, long linha, long coluna)
	{
		this.errorType = errorType;
		this.lexema = lexema;
		this.linha = linha;
		this.coluna = coluna;
		this.msg = msg;
	}

	@Override
	public String toString()
	{
		return "Error [type=" + errorType + ", lex=" + lexema + ", (" + linha + ", " + coluna + ")]: " + msg;
	}

	public ErrorType getErrorType() 				{ return errorType; }
	public void setErrorType(ErrorType errorType) 	{ this.errorType = errorType; }

	public String getMsg() 						    { return msg; }

	public String getLexema() 						{ return lexema; }

	public long getLinha() 							{ return linha; }

	public long getColuna() 						{ return coluna; }
}
