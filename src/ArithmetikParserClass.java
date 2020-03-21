import java.io.*;

/*
	ArithmetikParserClass.java
	
	Praktikum Algorithmen und Datenstrukturen
	Grundlage zum Versuch 2
	
	Diese Java Klasse implementiert einen
	einfachen Parser zum Erkennen arithmetischer
	Ausdrücke der folgenden Grammatik:
	
	expression -> term rightExpression
	rightExpression -> '+' term rightExpression 
	rightExpression -> '-' term rightExpression 
	rightExpression -> Epsilon
	term -> operator rightTerm
	rightTerm -> '*' operator rightTerm 
	rightTerm -> '/' operator rightTerm 
	rightTerm -> Epsilon
	operator -> '(' expression ')' | num
	num -> digit num | digit
	digit -> '1' | '2' | '3' | '4' | '5' |'6' | '7' | '8' | '9' | '0'
	
	Epsilon steht hier für das "leere Wort"
	
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

public class ArithmetikParserClass implements TokenList{
	// Konstante für Ende der Eingabe
	public final char EOF=(char)255;
	// Zeiger auf das aktuelle Eingabezeichen
	private int pointer;
	// Zeiger auf das Ende der Eingabe
	private int maxPointer;
	// Eingabe zeichenweise abgelegt
	private char input[];
	// Syntaxbaum
	private SyntaxTree parseTree;
	
	//-------------------------------------------------------------------------
	//------------Konstruktor der Klasse ArithmetikParserClass-----------------
	//-------------------------------------------------------------------------
	
	ArithmetikParserClass(SyntaxTree parseTree){
		this.parseTree=parseTree;
		this.input = new char[256];
		this.pointer=0;
		this.maxPointer=0;
	}
	
	//-------------------------------------------------------------------------
	//-------------------Methoden der Grammatik--------------------------------
	//-------------------------------------------------------------------------
	
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
		char [] addSet = {'+'};
		char [] subSet = {'-'};
		SyntaxTree epsilonTree;
		// Falls aktuelles Eingabezeichen '+'
		if (match(addSet,sT))
			//rightExpression -> '+' term rightExpression
    		return term(sT.insertSubtree(TERM))&& 
    		rightExpression(sT.insertSubtree(RIGHT_EXPRESSION)); 
   		// Falls aktuelles Eingabezeichen '-'
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
		char [] multDivSet = {'*','/'};
		char [] divSet = {'/'};
		SyntaxTree epsilonTree;

		// Falls aktuelles Eingabezeichen '*' oder '/'
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
		char [] openParSet= {'('};
		char [] closeParSet= {')'};
		char [] digitSet= {'1','2','3','4','5','6','7','8','9','0'};

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
    	// sonst versuchen nach digit abzuleiten 
   		else if (num(sT.insertSubtree(NUM)))
			//operator -> num   		
     		return true;
     	// wenn das nicht möglich ...				
  		else{ //Syntaxfehler
			syntaxError("Ziffer oder Klammer auf erwartet"); 			
 			return false;
  		}
	}//operator

	
	//-------------------------------------------------------------------------			
	// num -> digit num | digit
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean num(SyntaxTree sT){
		char [] digitSet = {'1','2','3','4','5','6','7','8','9','0'};

   		if (lookAhead(digitSet))
   			//num->digit num
    		return digit(sT.insertSubtree(DIGIT))&& num(sT.insertSubtree(NUM));		 
   		else 
   			//num->digit
     		return digit(sT.insertSubtree(DIGIT));					   
	}//num

	
	//-------------------------------------------------------------------------	
	// digit -> '1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'|'0'
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean digit(SyntaxTree sT){
		char [] matchSet = {'1','2','3','4','5','6','7','8','9','0'};

		if (match(matchSet,sT)){	//digit->'1'|'2'...|'9'|'0'
      		return true;            // korrekte Ableitung der Regel möglich
   		}else{
      		syntaxError("Ziffer erwartet"); // korrekte Ableitung der Regel  
      		return false;                   // nicht möglich
   		}
	}//digit
	
	//-------------------------------------------------------------------------
	//-------------------Hilfsmethoden-----------------------------------------
	//-------------------------------------------------------------------------

	//-------------------------------------------------------------------------		
	// Methode, die testet, ob das aktuele Eingabezeichen unter den Zeichen
	// ist, die als Parameter (matchSet) übergeben wurden.
	// Ist das der Fall, so gibt match() true zurück und setzt den Eingabe-
	// zeiger auf das nächste Zeichen, sonst wird false zurückgegeben.
	//-------------------------------------------------------------------------
	boolean match(char [] matchSet, SyntaxTree sT){
		SyntaxTree node;
		for (int i=0;i<matchSet.length;i++)
			if (input[pointer]==matchSet[i]){
				//Eingabezeichen als entsprechendem Knoten des Syntaxbaumes
				//eintragen
				node=sT.insertSubtree(INPUT_SIGN);
				node.setCharacter(input[pointer]);

				pointer++;	//Eingabepointer auf das nächste Zeichen setzen 
				return true;
			}
		return false;
	}//match
	
	//-------------------------------------------------------------------------
	//Methode, die testet, ob das auf das aktuelle Zeichen folgende Zeichen
	//unter den Zeichen ist, die als Parameter (aheadSet) übergeben wurden.
	//Der Eingabepointer wird nicht verändert!
	//-------------------------------------------------------------------------
	boolean lookAhead(char [] aheadSet){
		for (int i=0;i<aheadSet.length;i++)
			if (input[pointer+1]==aheadSet[i])
				return true;
		return false;
	}//lookAhead
	

	//-------------------------------------------------------------------------
	// Methode zum zeichenweise Einlesen der Eingabes aus
	// einer Eingabedatei mit dem Namen "testdatei.txt".
	// Die Metode berücksichtigt beim Einlesen schon die maximale Grösse
	// des Arrays input von 256 Zeichen.
	// Das Ende der Eingabe wird mit EOF markiert
	//-------------------------------------------------------------------------
	boolean readInput(String name){
		int c=0;
		try{
			FileReader f=new FileReader(name);
			for(int i=0;i<256;i++){
				c = f.read();
				if (c== -1){
					maxPointer=i;
					input[i]=EOF;
					break;
				}else
					input[i]=(char)c;
			} 
		}
		catch(Exception e){
			System.out.println("Fehler beim Dateizugriff: "+name);
			return false;
		}
		return true;	
	}//readInput


	//-------------------------------------------------------------------------	
	// Methode, die testet, ob das Ende der Eingabe erreicht ist
	// (pointer == maxPointer)
	//-------------------------------------------------------------------------
	boolean inputEmpty(){
		if (pointer==maxPointer){
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
		if (input[pointer]==EOF)
			System.out.println("Syntax Fehler beim "+(pointer+1)+". Zeichen: "
							+"EOF");
		else
			System.out.println("Syntax Fehler beim "+(pointer+1)+". Zeichen: "
							+input[pointer]);
		System.out.println(s);	
	}//syntaxError

}//ArithmetikParserClass