package lexicalAnalyzerPackage;
/*
Lexical Analyzer
Trevor H. Crow
CSCI 4200-DB
Dr. Abi Salimi
*/
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class LexicalAnalyzer {
	char nextChar;
	tokenCodes nextToken = null, charClass = null;
	String lexeme = "", lexeme_to_print = "", output = "";
	ArrayList<String> inputs; //will be used to store the inputs from the file
	Scanner scan;
	FileOutputStream writer;
	
	public LexicalAnalyzer(Scanner in) throws IOException {
		inputs = new ArrayList<String>();
		scan = in;
		
		//grab each line from the input file
		while(scan.hasNextLine()) {
			inputs.add(scan.nextLine());
		}
		scan.close();
		output += "Trevor H. Crow, CSCI4200, Fall 2019, Lexical Analyzer\n";
		addAstericks();
		for(String str : inputs) {
			scan = new Scanner(str);
			scan.useDelimiter(""); // makes it to where the scanner will read in one character at a time when you do in.next()
			output += "Input: "+str+"\n";
			getChar();
			do {
				lex();
			}while(nextToken != tokenCodes.END_OF_LINE);
			addAstericks();
			scan.close();
		}
		//we're now at the end of the file
		nextToken = tokenCodes.END_OF_FILE;
		lexeme_to_print = "EOF";
		
		output += "Next token is: "+nextToken+"\tNext Lexeme is "+lexeme_to_print;
		output += "\nLexical analysis of the program is complete!";
		System.out.print(output);
		writer = new FileOutputStream("lexOutput.txt");
		writer.write(output.getBytes());
		writer.close();
	}
	
	//adds 80 astericks to output
	public void addAstericks() {
		for(int i = 0; i<80;i++) {
			output += "*";
		}
		output += "\n";
	}
	
	// function for looking up operators and parentheses as well as setting nextToken
	public void lookup(char ch) {
		switch(ch) {
		case '=':
			addChar();
			nextToken = tokenCodes.ASSIGN_OP;
			break;
		case '(':
			addChar();
			nextToken = tokenCodes.LEFT_PAREN;
			break;
		case ')':
			addChar();
			nextToken = tokenCodes.RIGHT_PAREN;
			break;
		case '+':
			addChar();
			nextToken = tokenCodes.ADD_OP;
			break;
		case '-':
			addChar();
			nextToken = tokenCodes.SUB_OP;
			break;
		case '*':
			addChar();
			nextToken = tokenCodes.MULT_OP;
			break;
		case '/':
			addChar();
			nextToken = tokenCodes.DIV_OP;
			break;
		default:
			addChar();
			nextToken = tokenCodes.END_OF_LINE;
			break;

		}
	}
	
	//adds the next character to the lexeme
	public void addChar() {
		lexeme += nextChar;
	}
	
	//calls getChar until it returns a non-whitespace character
	public void getNonBlank() {
		while(Character.isWhitespace(nextChar)) {
			getChar();
		}
	}
	
	// gets the next character of input and determines its character class or return that it is the end of the file
	public void getChar() {
		if(scan.hasNext()) {
			nextChar = scan.next().charAt(0); //retrieves the next character
			//we check what type the character is
			if(Character.isLetter(nextChar)) {
				if(charClass != tokenCodes.LETTER) {
					lexeme_to_print = lexeme;
					lexeme = "";	
				}
				charClass = tokenCodes.LETTER;
			}else if(Character.isDigit(nextChar)){
				if(charClass != tokenCodes.DIGIT) {
					lexeme_to_print = lexeme;
					lexeme = "";	
				}
				charClass = tokenCodes.DIGIT;
			}else {
				lexeme_to_print = lexeme;
				lexeme = "";
				charClass = tokenCodes.UNKNOWN;
			}
		}else {
			lexeme_to_print = lexeme;
			charClass = tokenCodes.END_OF_LINE;
		}
	}
	
	public void lex() {
		getNonBlank();
		switch(charClass) {
			//parse identifiers
			case LETTER:
				addChar();
				getChar();
				while(charClass == tokenCodes.LETTER || charClass == tokenCodes.DIGIT) {
					addChar();
					getChar();
				}
				nextToken = tokenCodes.IDENT;
				break;
			//parse integer literals
			case DIGIT:
				addChar();
				getChar();
				while(charClass == tokenCodes.DIGIT) {
					addChar();
					getChar();
				}
				nextToken = tokenCodes.INT_LIT;
				break;
			//parentheses and operators
			case UNKNOWN:
				lookup(nextChar);
				getChar();
				break;
			case END_OF_LINE:
				nextToken = tokenCodes.END_OF_LINE;
			default:
				break;
		}
		//we don't want to include END_OF_LINE in output
		if(nextToken != tokenCodes.END_OF_LINE) output += "Next token is: "+nextToken+"\tNext Lexeme is "+lexeme_to_print+"\n";
	}
	
//	public static void main(String[] args) throws IOException, URISyntaxException{
//		URL path = ClassLoader.getSystemResource("lexInput.txt");
//		new LexicalAnalyzer(new Scanner(new File(path.toURI())));		
//	}

}
