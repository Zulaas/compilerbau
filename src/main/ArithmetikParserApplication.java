/**
 * The type Arithmetik parser application.
 *
 * @author jan
 */
class ArithmetikParserApplication implements TokenList {
    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String args[]) {
        SyntaxTree parseTree = new SyntaxTree(TokenList.EXPRESSION);

        ArithmetikParserClass parser = new ArithmetikParserClass(parseTree);
        if (parser.readInput("testdatei_arithmetik.txt"))
            if (parser.lexicalAnalysis())
                if (parser.expression(parseTree) && parser.inputEmpty()) {
                    parseTree.printSyntaxTree(0);
//					System.out.println("Korrekter Ausdruck mit Wert:"
//					+parseTree.value.f(parseTree,UNDEFINED));
                } else
                    System.out.println("Fehler im Ausdruck");
            else
                System.out.println("Fehler in lexikalischer Analyse");
    }
}