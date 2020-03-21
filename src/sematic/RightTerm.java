/**
 * The type Right term.
 */
class RightTerm extends Semantic{

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
			default: return UNDEFINED;
			}
		}else
			return n;		
	}
}