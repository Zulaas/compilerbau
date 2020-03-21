/*
	Expression.java
	
	Diese Klasse repräsentiert als Unterklasse von Semantic die
	semantische Funktion der Regeln mit dem Nonterminal
	Expression auf der linken Seite. 

*/

class Expression extends Semantic{
	// expression->term rightExpression
	// expression.f=rightExpression.f(term.f)
	int f(SyntaxTree t, int n){
		SyntaxTree term=t.getChild(0), 
				             rightExpression=t.getChild(1);
		return rightExpression.value.f(rightExpression,term.value.f(term,UNDEFINED));
		} 	
}//Expression