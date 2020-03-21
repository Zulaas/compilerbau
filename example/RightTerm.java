/*
	RightTerm.java
	
	Diese Klasse repräsentiert als Unterklasse von Semantic die
	semantische Funktion der Regeln mit dem Nonterminal
	rightTerm auf der linken Seite. 

*/

class RightTerm extends Semantic{
	//-------------------------------------------------------------------------
	// rightTerm -> '+' operator rightTerm 
	// rightTerm.f(n)=n+rightTerm.f(operator.f)
	//
	// rightTerm -> '-' operator rightTerm 
	// rightTerm.f(n)=n-rightTerm.f(operator.f)
	//
	// rightTerm -> Epsilon
	// rightTerm.f(n)=n
	//-------------------------------------------------------------------------
	int f(SyntaxTree t, int n){
		if (t.getChildNumber()==3){
			SyntaxTree symbol=t.getChild(0),
					             operator=t.getChild(1), 
					             rightTerm=t.getChild(2);
			switch(symbol.getCharacter()){
				case '*' : 	return n*rightTerm.value.f(
					rightTerm,operator.value.f(operator,UNDEFINED));
				case '/' :	return n/rightTerm.value.f(
					rightTerm,operator.value.f(operator,UNDEFINED));
			default: return UNDEFINED; //Fehler Fall
			}
		}else //Epsilon Fall
			return n;		
	}//f 	
}//RightTerm