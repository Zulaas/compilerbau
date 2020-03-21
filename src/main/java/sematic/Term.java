/**
 * The type Term.
 * @author jan
 */
class Term extends Semantic {
    // term->term rightTerm
    // term.f=rightTerm.f(term.f)
    @Override
    int f(SyntaxTree t, int n) {
        SyntaxTree term = t.getChild(0),
                rightTerm = t.getChild(1);
        return rightTerm.value.f(rightTerm, term.value.f(term, UNDEFINED));
    }
}