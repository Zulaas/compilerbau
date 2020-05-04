/*
	Term.java
	
	Diese Klasse repräsentiert als Unterklasse von Semantic die
	semantische Funktion der Regeln mit dem Nonterminal
	term auf der linken Seite. 

*/

class Term extends Semantic{
	// term->term rightTerm
	// term.f=rightTerm.f(term.f)
	int f(SyntaxTree t, int n){
		SyntaxTree term=t.getChild(0), 
				   rightTerm=t.getChild(1);
		return rightTerm.value.f(rightTerm,term.value.f(term,UNDEFINED));
		} 	
}//Term