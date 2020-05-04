/*
	ArithmetikParserApplikation.java
	
	Praktikum Algorithmen und Datenstrukturen
	Grundlage zum Versuch 3
	
	Diese Java Klasse implementiert die Applikation eines
	einfachen Parsers zum Erkennen arithmetischer
	Ausdr�cke.
	
	Der eigentliche Parser wird in der Klasse ArithmeticParserClasse
	defifiert.
	
*/


class ArithmetikParserApplication implements TokenList{
	public static void main(String args[]){

		// Anlegen des Wurzelknotens f�r den Syntaxbaum. Dem Konstruktor
		// wid als Token das Startsymbol der Grammatik �bergeben
		SyntaxTree parseTree = new SyntaxTree(EXPRESSION);

		// Anlegen des Parsers als Instanz der Klasse ArithmetikParserClass
		ArithmetikParserClass parser = new ArithmetikParserClass(parseTree);

		// Einlesen der Datei
		if (parser.readInput("testdatei_arithmetik.txt"))
			// lexikalische Analyse durchf�hren
			if (parser.lexicalAnalysis())
				//Aufruf des Parsers und Test, ob gesamte Eingabe gelesen
				if (parser.expression(parseTree)&& parser.inputEmpty()){
					//Ausgabe des Syntaxbaumes und des sematischen Wertes
					parseTree.printSyntaxTree(0);
					/*
					System.out.println("Korrekter Ausdruck mit Wert:"
					+parseTree.value.f(parseTree,UNDEFINED));
	*/
				}else
					//Fehlermeldung, falls Ausdruck nicht zu parsen war
					System.out.println("Fehler im Ausdruck");
			else
				//Fehlermeldung, falls lexikalische Analyse fehlgeschlagen
				System.out.println("Fehler in lexikalischer Analyse");
	}//main
}//ArithmetikParserApplikation