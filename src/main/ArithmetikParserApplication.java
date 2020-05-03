
class ArithmetikParserApplication implements TokenList {

    public static void main(String args[]) {
        SyntaxTree parseTree = new SyntaxTree(EXPRESSION);

        SourceScanner sourceScanner = new SourceScanner();
        if (sourceScanner.readInput("tmp.txt")) {
            if (sourceScanner.lexicalAnalysis()) {
                ArithmetikParserClass parser = new ArithmetikParserClass(sourceScanner.tokenStream);
                if (parser.parse() && parser.inputEmpty()) {
                    for (String key : parser.treeList.keySet()) {
                        SyntaxTree tree = parser.treeList.get(key);
                        // tree.printSyntaxTree("", true);
                    }
                } else {
                    System.out.println("Fehler im Ausdruck");
                }
            } else {
                System.out.println("Fehler in lexikalischer Analyse");
            }
        }
    }
}
