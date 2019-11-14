package lexicalAnalyzerPackage;
/*
Lexical Analyzer
Trevor H. Crow
CSCI 4200-DB
Dr. Abi Salimi
*/

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class LexicalAnalyzer {
	char nextChar;
	tokenCodes nextToken = null, charClass = null;
	String lexeme = "", lexeme_to_print = "", output = "";
	ArrayList<String> inputs; //will be used to store the inputs from the file
	Queue<tokenCodes> token_list; // will be used to queue up nextTokens
	Scanner scan;
	FileOutputStream writer;
	
	public LexicalAnalyzer(Scanner in) throws IOException {
		inputs = new ArrayList<String>();
		token_list = new LinkedList<tokenCodes>();
		scan = in;
		
		//grab each line from the input file
		while(scan.hasNextLine()) {
			inputs.add(scan.nextLine());
		}
		scan.close();
//		output += "Trevor H. Crow, CSCI4200, Fall 2019, Lexical Analyzer\n";
//		addAstericks();
		for(String str : inputs) {
			scan = new Scanner(str);
			scan.useDelimiter(""); // makes it to where the scanner will read in one character at a time when you do in.next()
			output += "Input: "+str+"\n";
			getChar();
			do {
				lex();
			}while(nextToken != tokenCodes.END_OF_LINE);
//			addAstericks();
			scan.close();
		}
		//we're now at the end of the file
		nextToken = tokenCodes.END_OF_FILE;
		token_list.add(nextToken);
//		lexeme_to_print = "EOF";
//		
//		output += "Next token is: "+nextToken+"\tNext Lexeme is "+lexeme_to_print;
//		output += "\nLexical analysis of the program is complete!";
////		System.out.print(output);
//		writer = new FileOutputStream("lexOutput.txt");
//		writer.write(output.getBytes());
//		writer.close();
	}
	
	//adds 80 astericks to output
//	private void addAstericks() {
//		for(int i = 0; i<80;i++) {
//			output += "*";
//		}
//		output += "\n";
//	}
	
	// function for looking up operators and parentheses as well as setting nextToken
	private void lookup(char ch) {
		switch(ch) {
		case '=':
			addChar();
			nextToken = tokenCodes.ASSIGN_OP;
			token_list.add(nextToken);
			break;
		case '(':
			addChar();
			nextToken = tokenCodes.LEFT_PAREN;
			token_list.add(nextToken);
			break;
		case ')':
			addChar();
			nextToken = tokenCodes.RIGHT_PAREN;
			token_list.add(nextToken);
			break;
		case '+':
			addChar();
			nextToken = tokenCodes.ADD_OP;
			token_list.add(nextToken);
			break;
		case '-':
			addChar();
			nextToken = tokenCodes.SUB_OP;
			token_list.add(nextToken);
			break;
		case '*':
			addChar();
			nextToken = tokenCodes.MULT_OP;
			token_list.add(nextToken);
			break;
		case '/':
			addChar();
			nextToken = tokenCodes.DIV_OP;
			token_list.add(nextToken);
			break;
		default:
			addChar();
			nextToken = tokenCodes.END_OF_LINE;
			token_list.add(nextToken);
			break;

		}
	}
	
	//adds the next character to the lexeme
	private void addChar() {
		lexeme += nextChar;
	}
	
	//calls getChar until it returns a non-whitespace character
	private void getNonBlank() {
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
				token_list.add(nextToken);
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
				token_list.add(nextToken);
				break;
			//parentheses and operators
			case UNKNOWN:
				lookup(nextChar);
				getChar();
				break;
			case END_OF_LINE:
				nextToken = tokenCodes.END_OF_LINE;
				token_list.add(nextToken);
			default:
				break;
		}
		//we don't want to include END_OF_LINE in output
		if(nextToken != tokenCodes.END_OF_LINE) output += "Next token is: "+nextToken+"\tNext Lexeme is "+lexeme_to_print+"\n";
	}
	
	// returns the queue
	public Queue<tokenCodes> getTokenList(){
		return token_list;
	}
	
	// return the list of inputs
	public ArrayList<String> getInputs(){
		return inputs;
	}
	
//	public static void main(String[] args) throws IOException, URISyntaxException{
//		URL path = ClassLoader.getSystemResource("lexInput.txt");
//		new LexicalAnalyzer(new Scanner(new File(path.toURI())));		
//	}

}
