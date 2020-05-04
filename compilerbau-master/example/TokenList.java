/*
	interface TokenList

	Praktikum Algorithmen und Datenstrukturen
	Beispiel zum Versuch 2

	Die Schnittstelle TokenList stellt die Konstanten für die
	Knotentypen eines Syntaxbaumes (Token) für Ziffernfolgen
	zur Verfügung.
*/

interface TokenList {
	// Konstanten zur Bezeichnung der Knoten des Syntaxbaumes
	
	final byte	NO_TYPE=0,
				NUM=1,
				DIGIT=2,
				INPUT_SIGN=3,
				EPSILON=4,
				START=5,
				NOT_FINAL=6,
				KOMMA=7,
				IDENT=8,
				OPEN_PAR=9,
				CLOSE_PAR=10,
				PLUS=11,
				MINUS=12,
				MULT=13,
				DIV=14,
				EXPRESSION=15,
				RIGHT_EXPRESSION=16,
				TERM=17,
				RIGHT_TERM=18,
				OPERATOR=20,
				PROGRAM=21,
				FUNCTION=22;
				
	// Konstante, die angibt, dass die Semantische Funktion eines Knotens 
	// undefiniert ist
	final int	UNDEFINED=0x10000001;	

				
}