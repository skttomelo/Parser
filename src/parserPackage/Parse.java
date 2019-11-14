package parserPackage;

import lexicalAnalyzerPackage.*;

public class Parse {
	
	LexicalAnalyzer LA = null; // we need a lexical analyzer so we can grab tokens
	
	public Parse(LexicalAnalyzer lexical_analyzer) {
		LA = lexical_analyzer;
	}
	
	/*
	 * expr
	 * Parses strings in the language generated by the rule:
	 * <expr> -> <term> {(+ | -) <term>}
	 */
	private void expr() {
		System.out.println("Enter <expr>");
		
		// parse the first term
		term();
		
		// as long as the next token is + or -, get the next token and parse the next term
		while (nextToken == ADD_OP || nextToken == SUB_OP) {
			LA.lex();
			term();
		}
		System.out.println("Exit <expr>");
	}
	
	/*
	 * term
	 * Parses strings in the language generated by the rule:
	 * <term> -> <factor> {(* | /) <factor>}
	 */
	private void term() {
		System.out.println("Enter <term>");
		
		// parse the first factor
		factor();
		
		while(nextToken == MULT_OP || nextToken == DIV_OP) {
			LA.lex();
			factor();
		}
		
		System.out.println("Exit <term>");
	}
	
	/*
	 * factor
	 * Parses strings in the language generated by the rule:
	 * <factor> -> id | int_constant | (<expr>)
	 */
	private void factor() {
		System.out.println("Enter <factor>");
		
		// determione whice RHS
		if(nextToken == IDENT || nextToken == INT_LIT) LA.lex(); // get the next token
		
		// if the RHS is (<expr>), call lex to pass over the left parenthesis, call expr, and check for the right parenthesis
		else {
			if(nextToken == LEF_PAREN) {
				LA.lex();
				expr();
				if(nextToken == RIGHT_PAREN) LA.lex();
				else error();
			}else error(); // it was not an id, an integer literal, or a left parenthesis
		}
		
		System.out.println("Exit <factor>");
	}
	
	// print out error message saying which token the error occured at
	private void error() {
		System.out.println("Error occured on token: "+nextToken);
	}
}
