/**
 * Material de Aula: 
 * Classe ErrorHandler (estrutura auxiliar - Singleton)
 * @autor Prof. MSc. Giulliano P. Carnielli
 */
package br.com.compilador.utils;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler
{

	private static ErrorHandler instance = new ErrorHandler();
	private List<CompilerError> erros = new ArrayList<CompilerError>();

	/**
	 * Método responsável por registrar erros
	 */
	public void addCompilerError(CompilerError error)
	{
		erros.add(error);
	}
	
	public void addCompilerError(ErrorType errorType, String lexema, String msg, long linha, long coluna) {
		CompilerError e =  new CompilerError(errorType, lexema, msg, linha, coluna);
		addCompilerError(e);
	}

	public void gerarRelatorio()
	{
		cabecalhoRelatorioLexico();

		for (CompilerError erro : erros)
		{
			System.out.println(erro.toString());
		}
	}

	/**
	 * Método responsável por imprimir o cabeçalho de erros
	 */
	private void cabecalhoRelatorioLexico()
	{
		System.out.println("\n---------------------------------------");
		System.out.printf("|%25s %12s", "Erros", "|");
		System.out.println("\n---------------------------------------");
	}

	/**
	 * Método responsável por retornar a instância única da classe
	 */
	public static ErrorHandler getInstance()
	{
		return instance;
	}
}
