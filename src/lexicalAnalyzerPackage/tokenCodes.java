package lexicalAnalyzerPackage;

//token codes including character types and EOF/EOL
public enum tokenCodes{
		INT_LIT,
		IDENT,
		ASSIGN_OP,
		ADD_OP,
		SUB_OP,
		MULT_OP,
		DIV_OP,
		LEFT_PAREN,
		RIGHT_PAREN,
		END_OF_FILE,
		END_OF_LINE,
		LETTER,
		DIGIT,
		UNKNOWN;
}