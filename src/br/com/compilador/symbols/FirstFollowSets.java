package br.com.compilador.symbols;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.compilador.TabSimbolos;

public class FirstFollowSets {

	private static FirstFollowSets instance = new FirstFollowSets();
	private Map<NonTerminalType, List<TokenType> > firstSet = new HashMap<>();
	private Map<NonTerminalType, List<TokenType> > followSet = new HashMap<>();
	
    private FirstFollowSets() {
    	List<TokenType> l;
    	
    	// Cria conjuntos FIRST
    	l = Arrays.asList(TokenType.PROGRAM);
    	firstSet.put(NonTerminalType.S, l);
    	l = Arrays.asList(TokenType.DECLARE,
    	                  TokenType.IF,
    	                  TokenType.FOR,
    	                  TokenType.WHILE,
    	                  TokenType.ID);
    	firstSet.put(NonTerminalType.CMDS, l);
    	firstSet.put(NonTerminalType.CMD, l);
    	
    	// Cria conjuntos FOLLOW
    	l = Arrays.asList(TokenType.EOF);
    	followSet.put(NonTerminalType.S, l);

    	l = Arrays.asList(TokenType.END);
    	followSet.put(NonTerminalType.CMDS, l);
    	l = Arrays.asList(TokenType.END,
					      TokenType.DECLARE,
					      TokenType.IF,
					      TokenType.FOR,
					      TokenType.WHILE,
					      TokenType.ID,
					      TokenType.END_PROG,
					      TokenType.ELSE);
    	followSet.put(NonTerminalType.CMD, l);
    }

    public static FirstFollowSets getInstance() {
		return instance;
	}
    
    public List<TokenType> getFirstFromNT(NonTerminalType nt) {
    	return firstSet.get(nt);
    }
    
    public List<TokenType> getFollowFromNT(NonTerminalType nt) {
    	return followSet.get(nt);
    }
}
