/**
 * Material de Aula: 
 * Classe Compilador (aplicativo)
 * @autor Prof. MSc. Giulliano P. Carnielli
 */
package br.com.compilador;

import java.io.IOException;

import br.com.compilador.analisadores.Sintatico;

public class Compilador {

	public static void main(String[] args) {

		if(args.length < 1) {
			System.out.println("Informa nome do arquivo a ser compilado");
			System.out.println("USAGE: java Compilador <nome do arquivo de entrada>");
			return;
		}
		String filename = args[0];
		Sintatico sintatico;
		try {
			sintatico = new Sintatico(filename);
			sintatico.processar();
		} catch (IOException e) {
			System.out.println("Arquivo não encontrado!");
			System.out.println("USAGE: java Compilador <nome do arquivo de entrada>");
		}
	}
}
