package driverClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import lexicalAnalyzerPackage.LexicalAnalyzer;
import lexicalAnalyzerPackage.tokenCodes;
import parserPackage.Parse;

public class Driver {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		File input_file = new File("sourceStatements.txt");
		Scanner scan = new Scanner(input_file);
		
		Parse parser = new Parse(new LexicalAnalyzer(scan));
		
		parser.assign();
		parser.output();
	}
}
