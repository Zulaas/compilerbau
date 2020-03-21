/**
 * The type Right expression.
 * @author jan
 */
class RightExpression extends Semantic {

    @Override
    int f(SyntaxTree t, int n) {
        if (t.getChildNumber() == 3) {
            SyntaxTree symbol = t.getChild(0),
                    term = t.getChild(1),
                    rightExpression = t.getChild(2);
            switch (symbol.getCharacter()) {
                case '+':
                    return n + rightExpression.value.f(
                            rightExpression, term.value.f(term, UNDEFINED));
                case '-':
                    return n - rightExpression.value.f(
                            rightExpression, term.value.f(term, UNDEFINED));
                default:
                    return UNDEFINED;
            }
        } else {
            return n;
        }
    }
}