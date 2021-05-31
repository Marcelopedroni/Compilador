/**
 * Material de Aula: 
 * Classe ErrorType (enumerado com códigos de erro)
 * @autor Prof. MSc. Giulliano P. Carnielli
 */
package br.com.compilador.utils;

/**
* Enumerado de tipos de erros
*/
public enum ErrorType {
    LEXICO(1),
    SINTATICO(2),
    SEMANTICO(3),
    PROCESSAMENTO(4);

    private int cod;

    private ErrorType(int codErro) {
        this.cod = codErro;
    }

    public int getCod(){
        return cod;
    }

    public static ErrorType toEnum(int codToken) {

        for (ErrorType tokenType : ErrorType.values()){
            if(codToken == tokenType.getCod()) { return tokenType; }
        }

        throw new IllegalArgumentException("codigo invalido "+codToken);
    }
}
