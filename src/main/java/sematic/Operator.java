/**
 * The type Operator.
 * @author jan
 */
class Operator extends Semantic {

    @Override
    int f(SyntaxTree t, int n) {
        if (t.getChildNumber() == 3) {
            SyntaxTree expression = t.getChild(1);
            return expression.value.f(expression, UNDEFINED);
        } else {
            SyntaxTree num = t.getChild(0);
            return num.value.f(num, UNDEFINED);
        }
    }
}