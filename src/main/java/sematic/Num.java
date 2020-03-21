/**
 * The type Num.
 * @author jan
 */
class Num extends Semantic {

    private int potenz(int v) {
        int p = 10;
        while (v / p != 0) {
            p = p * 10;
        }
        return p;
    }

    @Override
    int f(SyntaxTree t, int n) {
        if (t.getChildNumber() == 2) {
            SyntaxTree digit = t.getChild(0),
                    num = t.getChild(1);
            int v = num.value.f(num, UNDEFINED);
            return digit.value.f(digit, UNDEFINED) * potenz(v) + v;
        } else {
            SyntaxTree digit = t.getChild(0);
            return digit.value.f(digit, UNDEFINED);
        }
    }
}