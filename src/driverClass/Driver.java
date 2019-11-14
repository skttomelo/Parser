package driverClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import lexicalAnalyzerPackage.LexicalAnalyzer;
import parserPackage.Parse;

public class Driver {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		File input_file = new File("src\\driverClass\\sourceStatements.txt");
		Scanner scan = new Scanner(input_file);
		LexicalAnalyzer LA = new LexicalAnalyzer(scan);
		Parse parser = new Parse(LA);
	}
}
