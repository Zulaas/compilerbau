/*
	Semantic.java
	
	Praktikum Algorithmen und Datenstrukturen
	Grundlage zum Versuch 2
	
	Diese Klasse stellt den Rumpf der semantsichen Funktion eines Knotens
	im Syntaxbaum für die Grammatik unseres Parsers zur Verfügung.
	
	Formal ist die Sematische Funktion zu einer Grammatik G=(V,T,P,S) hier eine
	Abbildung f:P x Z->Z, d.h. sie berechnet zu einer Regel aus P und einem 
	Eingabewert einen entsprechenden Ausgabewert.
	
	Der Eingabewert und der Ausgabewert sollen im Praktikum auf ganze Zahlen
	beschränkt sein. 
	
	Die Konkrete Semantik der einzelnen Grammatikregeln sind als 
	Subklassen der Klasse Semantik durch Überschreiben der Methode f(t,n)
	zu realisieren.	
*/

class Semantic {
	
	// Konstante, die angibt, dass die Semantische Funktion eines Knotens 
	// undefiniert ist.
	
	final int	UNDEFINED=0x10000001;
		
	//-------------------------------------------------------------------------
	// Die Semantsiche Funktion sei definiert als die Funktion f:P x Z->Z.
	// Der Parameter t repräsentiert einen Teilbaum des Syntaxbaumes, wobei
	// die Wurzel W dieses Teilbaumes die linke Seite einer Regel aus P
	// darstellt und die Kinder K1..Kn des Knotens t die rechte Seite:
	// t: W->K1..Kn.
	// Da es in einer Grammatik auch vorkommen kann, dass die Semantik nicht
	// direkt durch einen Wert der Zielmenge (hier der ganzen Zahlen Z),sondern
	// durch eine Funktion g:Z->Z  ausgedrückt werden muss, wird bei f noch
	// ein zweiter Parameter übergeben, der die Definitionsmenge von g
	// aufnimmt.
	//
	// Für eine bestimmte Regel t:A->B aus P gilt also f(t,n)=g(n).
	//
	// Im allgemeinen Fall, d.h. ohne Angabe einer Regel, ist die semantische
	// Funktion undefiniert
	//-------------------------------------------------------------------------
	
	int f(SyntaxTree t, int n){
		return UNDEFINED;
		}//f
		 
}//Semantic