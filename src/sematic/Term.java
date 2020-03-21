
class Term extends Semantic {
    // term->term rightTerm
    // term.f=rightTerm.f(term.f)
    int f(SyntaxTree t, int n) {
        SyntaxTree term = t.getChild(0),
                rightTerm = t.getChild(1);
        return rightTerm.value.f(rightTerm, term.value.f(term, UNDEFINED));
    }
}