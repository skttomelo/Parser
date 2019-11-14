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
		File input_file = new File("src/driverClass/sourceStatements.txt");
		Scanner scan = new Scanner(input_file);
		System.out.println("****************************************************");
		System.out.println("Trevor H. Crow, CSCI4200, Fall 2019, Parser");
		System.out.println("****************************************************");
		
		Parse parser = new Parse(new LexicalAnalyzer(scan));
		
		parser.assign();
	}
}
