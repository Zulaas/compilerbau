/*
	RightExpression.java
	
	Diese Klasse repräsentiert als Unterklasse von Semantic die
	semantische Funktion der Regeln mit dem Nonterminal
	rightExpression auf der linken Seite. 

*/

class RightExpression extends Semantic{
	//-------------------------------------------------------------------------
	// rightExpression -> '+' term rightExpression 
	// rightExpression.f(n)=n+rightExpression.f(term.f)
	//
	// rightExpression -> '-' term rightExpression 
	// rightExpression.f(n)=n-rightExpression.f(term.f)
	//
	// rightExpression -> Epsilon
	// rightExpression.f(n)=n
	//-------------------------------------------------------------------------
	int f(SyntaxTree t, int n){
		if (t.getChildNumber()==3){
			SyntaxTree symbol=t.getChild(0),
					             term=t.getChild(1), 
					             rightExpression=t.getChild(2);
			switch(symbol.getCharacter()){
				case '+' : 	return n+rightExpression.value.f(
					rightExpression,term.value.f(term,UNDEFINED));
				case '-' :	return n-rightExpression.value.f(
					rightExpression,term.value.f(term,UNDEFINED));
			default: return UNDEFINED;
			}
		}else
			return n;		
	}//f 	
}//RightExpression