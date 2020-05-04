import java.io.*;

/*
	ArithmetikParserClass.java
	
	Praktikum Algorithmen und Datenstrukturen
	Grundlage zum Versuch 2
	
	Diese Java Klasse implementiert einen
	einfachen Parser zum Erkennen arithmetischer
	Ausdrücke der folgenden Grammatik:
	
	program -> function program | function
	function -> ident openPar parameterList closePar expression
	parameterList -> ident komma parameterList | ident | Epsilon 
	expression -> term rightExpression
	rightExpression -> plus term rightExpression 
	rightExpression -> minus term rightExpression 
	rightExpression -> Epsilon
	term -> operator rightTerm
	rightTerm -> mult operator rightTerm 
	rightTerm -> div operator rightTerm 
	rightTerm -> Epsilon
	operator -> openPar expression closePar | num | ident | functionCall
	functionCall -> ident openPar expressionList closePar
	expressionList -> expression komma expressionList | expression | Epsilon

	
	Epsilon steht hier für das "leere Wort"
	
	Der Parser verarbeitet den Token Strom, der durch den Scanner bei der
	lexikalischen Analyse der Eingabe erzeugt wurde.
	
	Der Parser ist nach dem Prinzip des rekursiven Abstiegs programmiert,
	d.h. jedes nicht terminale Symbol der Grammatik wird durch eine 
	Methode in Java repräsentiert, die die jeweils anderen nicht terminalen
	Symbole auf der rechten Seite der Grammatik Regeln ggf. auch rekursiv
	aufruft.
	
	Der zu parsende Ausdruck wird aus einer Datei gelesen und in einem
	Array of Char abgespeichert. Pointer zeigt beim Parsen auf den aktuellen
	Eingabewert.
	
	Ist der zu parsende Ausdruck syntaktisch nicht korrekt, so werden 
	über die Methode syntaxError() entsprechende Fehlermeldungen ausgegeben.
	
	Zusätzlich werden den Methoden der Klasse neben der Rekursionstiefe auch
	eine Referenz auf eine Instanz der Klasse SyntaxTree übergeben.
	
	Über die Instanzen der Klasse SyntaxTree wird beim rekursiven Abstieg
	eine konkreter Syntaxbaum des geparsten Ausdrucks aufgebaut.

*/

public class ArithmetikParserClass extends NumScanner implements TokenList{
	// Konstante für Ende der Eingabe
	public final char EOF=(char)255;
	// Zeiger auf das aktuelle Eingabezeichen
	private int pointer;
	// Zeiger auf das Ende der Eingabe
	private int maxPointer;
	// Eingabe zeichenweise abgelegt
	// private char input[];
	// Syntaxbaum
	private SyntaxTree parseTree;
	
	//-------------------------------------------------------------------------
	//------------Konstruktor der Klasse ArithmetikParserClass-----------------
	//-------------------------------------------------------------------------
	
	ArithmetikParserClass(SyntaxTree parseTree){
		this.parseTree=parseTree;
		//this.input = new char[256];
		this.pointer=0;
		this.maxPointer=0;
	}
	
	//-------------------------------------------------------------------------
	//-------------------Methoden der Grammatik--------------------------------
	//-------------------------------------------------------------------------
	
	//-------------------------------------------------------------------------
	// program -> function program | function
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean program(SyntaxTree sT){
		byte [] identSet = {IDENT};
		// program -> function ...
		if (function(sT.insertSubtree(FUNCTION)))
			// program -> function program
			if (lookAhead(identSet))
				return program(sT.insertSubtree(PROGRAM));
			else
			// program -> function (analog zum Epsilon Fall)
				return true;
	}//program
	
	
	//-------------------------------------------------------------------------
	// expression -> term rightExpression
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean expression(SyntaxTree sT){
		return (term(sT.insertSubtree(TERM))&&
				rightExpression(sT.insertSubtree(RIGHT_EXPRESSION)));
	}//expression	
	
	
	//-------------------------------------------------------------------------
	// rightExpression -> '+' term rightExpression | 
	//                    '-' term rightExpression | Epsilon
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean rightExpression(SyntaxTree sT){
		byte [] addSet = {PLUS};
		byte [] subSet = {MINUS};
		SyntaxTree epsilonTree;
		// Falls aktuelles Token PLUS
		if (match(addSet,sT))
			//rightExpression -> '+' term rightExpression
    		return term(sT.insertSubtree(TERM))&& 
    		rightExpression(sT.insertSubtree(RIGHT_EXPRESSION)); 
   		// Falls aktuelles Token MINUS
  		else if (match(subSet,sT))
			//rightExpression -> '-' term rightExpression   		
     		return term(sT.insertSubtree(TERM))&& 
     		rightExpression(sT.insertSubtree(RIGHT_EXPRESSION));
     	// sonst				
  		else{
  			//rightExpression ->Epsilon
  			epsilonTree = sT.insertSubtree(EPSILON);
  			return true;
  			}				
	}//rightExpression
	
	
	//-------------------------------------------------------------------------	
	// term -> operator rightTerm
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	
	boolean term(SyntaxTree sT){

		//term -> operator rightTerm
		return (operator(sT.insertSubtree(OPERATOR))
		&&rightTerm(sT.insertSubtree(RIGHT_TERM)));		
	}//term
	
	//-------------------------------------------------------------------------	
	// rightTerm -> '*' operator rightTerm | 
	//              '/' operator rightTerm | Epsilon
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------

	boolean rightTerm(SyntaxTree sT){
		byte [] multDivSet = {MULT,DIV};
		byte [] divSet = {DIV};
		SyntaxTree epsilonTree;

		// Falls aktuelles Token MULT oder DIV
		if (match(multDivSet,sT))
			//rightTerm -> '*' operator rightTerm bzw.
			//rightTerm -> '/' operator rightTerm   		
    		return operator(sT.insertSubtree(OPERATOR))&& 
    		rightTerm(sT.insertSubtree(RIGHT_TERM));
  		else{
  			//rightTerm ->Epsilon
  			epsilonTree = sT.insertSubtree(EPSILON);
  			return true;
  			}				
	}//rightTerm


	//-------------------------------------------------------------------------	
	// operator -> '(' expression ')' | num	
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean operator(SyntaxTree sT){
		byte [] openParSet= {OPEN_PAR};
		byte [] closeParSet= {CLOSE_PAR};
		byte [] numSet={NUM};
		byte [] identSet={IDENT};


		// Falls aktuelle Eingabe '('
		if (match(openParSet,sT))
			//operator -> '(' expression ')' 
    		if (expression(sT.insertSubtree(EXPRESSION))){
    			// Fallunterscheidung ermöglicht, den wichtigen Fehler einer
    			// fehlenden geschlossenen Klammer gesondert auszugeben
    			if(match(closeParSet,sT))
    				return true;
    			else{//Syntaxfehler
					syntaxError("Geschlossene Klammer erwartet"); 			
 					return false;
    				}
    		}else{
    			syntaxError("Fehler in geschachtelter Expression"); 			
 				return false;
    		}
    	// sonst versuchen nach num abzuleiten 
   		else if (match(numSet,sT))
			//operator -> num   		
     		return true;
   		else if (match(identSet,sT))
			//operator -> ident   		
     		return true;
     	// wenn das nicht möglich ...				
  		else{ //Syntaxfehler
			syntaxError("Ziffer, Identifier oder Klammer auf erwartet"); 			
 			return false;
  		}
	}//operator
	
	//-------------------------------------------------------------------------
	//-------------------Hilfsmethoden-----------------------------------------
	//-------------------------------------------------------------------------

	//-------------------------------------------------------------------------		
	// Methode, die testet, ob das aktuele Token unter den Token
	// ist, die als Parameter (matchSet) übergeben wurden.
	// Ist das der Fall, so gibt match() true zurück und setzt den Eingabe-
	// zeiger auf das nächste Zeichen, sonst wird false zurückgegeben.
	//-------------------------------------------------------------------------
	boolean match(byte [] matchSet, SyntaxTree sT){
		SyntaxTree node;
		for (int i=0;i<matchSet.length;i++)
			if (tokenStream.get(pointer).token==matchSet[i]){
				// gefundenes Token in den Syntaxbaum eintragen
				sT.insertSubtree(tokenStream.get(pointer).token);
				pointer++;	//Eingabepointer auf das nächste Zeichen setzen 
				return true;
			}
		return false;
	}//match
	
	//-------------------------------------------------------------------------
	//Methode, die testet, ob das auf das aktuelle Token folgende Token
	//unter den Token ist, die als Parameter (aheadSet) übergeben wurden.
	//Der Eingabepointer wird nicht verändert!
	//-------------------------------------------------------------------------
	boolean lookAhead(byte [] aheadSet){
		for (int i=0;i<aheadSet.length;i++)
			if (tokenStream.get(pointer+1).token==aheadSet[i])
				return true;
		return false;
	}//lookAhead
	



	//-------------------------------------------------------------------------	
	// Methode, die testet, ob das Ende der Eingabe erreicht ist
	// (pointer == maxPointer)
	//-------------------------------------------------------------------------
	boolean inputEmpty(){
		if (pointer==(tokenStream.size()-1)){
			ausgabe("Eingabe leer!",0);
			return true;
		}else{
			syntaxError("Eingabe bei Ende des Parserdurchlaufs nicht leer");
			return false;
		}
		
	}//inputEmpty


	//-------------------------------------------------------------------------	
	// Methode zum korrekt eingerückten Ausgeben des Syntaxbaumes auf der 
	// Konsole 
	//-------------------------------------------------------------------------
	void ausgabe(String s, int t){
		for(int i=0;i<t;i++)
		  System.out.print("  ");
		System.out.println(s);
	}//ausgabe

	//-------------------------------------------------------------------------
	// Methode zum Ausgeben eines Syntaxfehlers mit Angabe des vermuteten
	// Zeichens, bei dem der Fehler gefunden wurde 
	//-------------------------------------------------------------------------
	void syntaxError(String s){
		char z;
		if (tokenStream.get(pointer).token==EOF)
			System.out.println("Syntax Fehler in Zeile "+tokenStream.get(pointer).line+": "+"EOF");
		else
			System.out.println("Syntax Fehler in Zeile "+tokenStream.get(pointer).line+": "+tokenStream.get(pointer).token);
		System.out.println(s);	
	}//syntaxError

}//ArithmetikParserClass