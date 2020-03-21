import java.util.*;

class SyntaxTree implements TokenList{

	// linker bzw. rechter Teilbaum (null bei Bl�ttern), rightNode=null,
	// wenn Operator nur einen Operanden hat
	private LinkedList <SyntaxTree> childNodes; 
	
	// Art des Knotens gem�� der Beschreibung in der Schnittstelle Arithmetic
	private byte token;
	
	// Zeichen des Knotens, falls es sich um einen Bl�tterknoten, der ein
	// Eingabezeichen repr�sentiert, handelt, d.h. einen Knoten mit dem Token  
	// DIGIT oder MATH_SIGN.
	private char character;
	
	
	// value enth�lt die semantsiche Funktion des Teilbaums
	// mit Wurzelknoten this
	public Semantic value;
	
		
	//-------------------------------------------------------------------------
	// Konstruktor des Syntaxbaumes 
	//-------------------------------------------------------------------------
	
	// Der Konstruktor bekommt den TokenTyp t des Knotens �bergeben
	SyntaxTree(byte t){
		this.childNodes= new LinkedList<SyntaxTree>();
		character=0;
		setToken(t);
		setSemantikFunction(t);
		}	
	//-------------------------------------------------------------------------
	// get und set Methoden des Syntaxbaumes
	//-------------------------------------------------------------------------
	
	// Setzt den Typ des Tokens auf den �bergabeparameter t
	// Zu den m�glichen TokenTypen siehe Interface TokenList.java
	void setToken(byte t){
		this.token=t;
		}
	
	// Gibt den aktuellen Konten des Syntaxbaumes zur�ck
	byte getToken(){
		return this.token;
	}
	
	// Bei einem Knoten, der ein Eingabezeichen repr�sentiert (INPUT_SIGN)
	// wird mit dieser Methode das Zeichen im Knoten gespeichert
	void setCharacter(char character){
		this.character=character;
	}
	
	// Gibt das zum Knoten geh�rende Eingabezeichen zur�ck
	char getCharacter(){
		return this.character;
	}
	

	// Gibt den Syntaxbaum mit entsprechenden Einr�ckungen auf der Konsole
	// aus.
	void printSyntaxTree(int t){
		for(int i=0;i<t;i++)
		  System.out.print("  ");
		System.out.print(this.getTokenString());  		
		if(this.character!=0)
			System.out.println(":"+this.getCharacter());
		else
			System.out.println("");	
		for(int i=0;i<this.childNodes.size();i++){
			this.childNodes.get(i).printSyntaxTree(t+1);
		}
	}
	
	// Gibt den zum Zahlenwert passenden String des Tokentyps zur�ck
	String getTokenString(){
		switch(this.token){
			case 0: return "NO_TYPE";
			case 9: return "OPEN_PAR";
			case 10: return "CLOSE_PAR";
			case 15: return "EXPRESSION";
			case 16: return "RIGHT_EXPRESSION";
			case 17: return "TERM";
			case 18: return "RIGHT_TERM";
			case 1: return "NUMBER";
			case 20: return "OPERATOR";
			case 7: return "KOMMA";
			case 3: return "INPUT_SIGN";
			case 4: return "EPSILON";
			case 11: return "PLUS";
			case 12: return "MINUS";
			case 13: return "MULT";
			case 14: return "DIV";
			case 8: return "IDENT";
			default: return "";
		}
	}
	
	
	
	// Bestimmt und speichert die semantsiche Funktion des Kontens in
	// Abh�ngigkeit vom Knotentyp
	void setSemantikFunction(byte b){
		switch(b){/*
			case 1: value=new Expression();
					break;
			case 2: value=new RightExpression();
					break;
			case 3: value=new Term();
					break;
			case 4: value=new RightTerm();
					break;
			case 5: value=new Num();
					break;
			case 6: value=new Operator();
					break;
			case 7: value=new Digit();
					break; */
		default: value=new Semantic();
				 break;
		}
	}
	
	
	
	// Legt einen neuen Teilbaum als Kind des aktuellen Knotens an und gibt die
	// Referenz auf seine Wurzel zur�ck
	SyntaxTree insertSubtree(byte b){
		SyntaxTree node;
		node=new SyntaxTree(b); 
		this.childNodes.addLast(node);
		return node;
		}
	
	// Gibt die Refernz der Wurzel des i-ten Kindes des aktuellen 
	// Knotens zur�ck
	SyntaxTree getChild(int i){
		if (i>this.childNodes.size())
			return null;
		else
			return this.childNodes.get(i);
		}
		
	// Gibt die Referenz auf die Liste der Kinder des aktuellen Knotens zur�ck
	LinkedList getChildNodes(){
		return this.childNodes;
		}	
	
	// Gibt die Zahl der Kinder des aktuellen Konotens zur�ck
	int getChildNumber(){
		return childNodes.size();
	}
	

}