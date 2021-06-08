/**
 * Material de Aula: 
 * Classe TokenType (enumerado com códigos de erro)
 * @autor Prof. MSc. Giulliano P. Carnielli
 */
package br.com.compilador.token;

/**
* Enumerado de tipos de tokens
*/
public enum TokenType {
	EOF(0), 		// Fim do arquivo
	NUM_INT(1), 	// 123,48, 3E+10
	NUM_FLOAT(2), 	// 4.8, 3.10E+10
	LITERAL(3), 	// “Carlos”, “Batata”
	ID(4), 			// val, _salario, i__
	RELOP(5), 		// $df, $gt, $le
	ARIT_AS(6), 	// +, -
	ARIT_MD(7), 	// *, /
	ASSIGN(8), 		// <-
	TERM(9), 		// ;
	L_PAR(10), 		// (
	R_PAR(11), 		// )
	LOGIC_VAL(12), 	// true, false
	LOGIC_OP(13), 	// and, not, or
	TYPE(14), 		// bool, text, int
	PROGRAM(15), 	// program
	END_PROG(16), 	// end_prog
	BEGIN(17), 		// begin
	END(18), 		// end
	IF(19), 		// if
	THEN(20), 		// then
	ELSE(21), 		// else
	FOR(22), 		// for
	WHILE(23), 		// while
	DECLARE(24), 	// declare 
	TO(25); 		// to
	
	private int codToken;

	private TokenType(int codToken){
		this.codToken = codToken;
	}

	public int getCodToken(){
		return codToken;
	}
	
	public static TokenType toEnum(int codToken){
		
		for (TokenType tokenType : TokenType.values()){
			if(codToken == tokenType.getCodToken()) { return tokenType; }
		}
		
		throw new IllegalArgumentException("codigo invalido "+codToken);
	}
}
