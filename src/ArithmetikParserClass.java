import java.io.*;


public class ArithmetikParserClass implements TokenList {
    public final char EOF = (char) 255;
    private int pointer;
    private int maxPointer;
    private char input[];
    private SyntaxTree parseTree;



    ArithmetikParserClass(SyntaxTree parseTree) {
        this.parseTree = parseTree;
        this.input = new char[256];
        this.pointer = 0;
        this.maxPointer = 0;
    }

    boolean expression(SyntaxTree sT) {
        return (term(sT.insertSubtree(TERM)) &&
                rightExpression(sT.insertSubtree(RIGHT_EXPRESSION)));
    }

    boolean rightExpression(SyntaxTree sT) {
        char[] addSet = {'+'};
        char[] subSet = {'-'};
        SyntaxTree epsilonTree;
        if (match(addSet, sT))
            return term(sT.insertSubtree(TERM)) &&
                    rightExpression(sT.insertSubtree(RIGHT_EXPRESSION));
        else if (match(subSet, sT))
            return term(sT.insertSubtree(TERM)) &&
                    rightExpression(sT.insertSubtree(RIGHT_EXPRESSION));
        else {
            epsilonTree = sT.insertSubtree(EPSILON);
            return true;
        }
    }

    boolean term(SyntaxTree sT) {
        return (operator(sT.insertSubtree(OPERATOR))
                && rightTerm(sT.insertSubtree(RIGHT_TERM)));
    }



    boolean rightTerm(SyntaxTree sT) {
        char[] multDivSet = {'*', '/'};
        char[] divSet = {'/'};
        SyntaxTree epsilonTree;

        if (match(multDivSet, sT))
            return operator(sT.insertSubtree(OPERATOR)) &&
                    rightTerm(sT.insertSubtree(RIGHT_TERM));
        else {
            epsilonTree = sT.insertSubtree(EPSILON);
            return true;
        }
    }

    boolean operator(SyntaxTree sT) {
        char[] openParSet = {'('};
        char[] closeParSet = {')'};
        char[] digitSet = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

        if (match(openParSet, sT))
            if (expression(sT.insertSubtree(EXPRESSION))) {

                if (match(closeParSet, sT))
                    return true;
                else {
                    syntaxError("Geschlossene Klammer erwartet");
                    return false;
                }
            } else {
                syntaxError("Fehler in geschachtelter sematic.Expression");
                return false;
            }
        else if (num(sT.insertSubtree(NUM)))
            return true;
        else {
            syntaxError("Ziffer oder Klammer auf erwartet");
            return false;
        }
    }

    boolean num(SyntaxTree sT) {
        char[] digitSet = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

        if (lookAhead(digitSet))
            return digit(sT.insertSubtree(DIGIT)) && num(sT.insertSubtree(NUM));
        else
            return digit(sT.insertSubtree(DIGIT));
    }

    boolean digit(SyntaxTree sT) {
        char[] matchSet = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

        if (match(matchSet, sT)) {
            return true;
        } else {
            syntaxError("Ziffer erwartet");
            return false;
        }
    }

    boolean match(char[] matchSet, SyntaxTree sT) {
        SyntaxTree node;
        for (int i = 0; i < matchSet.length; i++)
            if (input[pointer] == matchSet[i]) {

                node = sT.insertSubtree(INPUT_SIGN);
                node.setCharacter(input[pointer]);

                pointer++;
                return true;
            }
        return false;
    }

    boolean lookAhead(char[] aheadSet) {
        for (int i = 0; i < aheadSet.length; i++)
            if (input[pointer + 1] == aheadSet[i])
                return true;
        return false;
    }

    boolean readInput(String name) {
        int c = 0;
        try {
            FileReader f = new FileReader(name);
            for (int i = 0; i < 256; i++) {
                c = f.read();
                if (c == -1) {
                    maxPointer = i;
                    input[i] = EOF;
                    break;
                } else
                    input[i] = (char) c;
            }
        } catch (Exception e) {
            System.out.println("Fehler beim Dateizugriff: " + name);
            return false;
        }
        return true;
    }

    boolean inputEmpty() {
        if (pointer == maxPointer) {
            ausgabe("Eingabe leer!", 0);
            return true;
        } else {
            syntaxError("Eingabe bei Ende des Parserdurchlaufs nicht leer");
            return false;
        }

    }


    void ausgabe(String s, int t) {
        for (int i = 0; i < t; i++)
            System.out.print("  ");
        System.out.println(s);
    }

    void syntaxError(String s) {
        char z;
        if (input[pointer] == EOF)
            System.out.println("Syntax Fehler beim " + (pointer + 1) + ". Zeichen: "
                    + "EOF");
        else
            System.out.println("Syntax Fehler beim " + (pointer + 1) + ". Zeichen: "
                    + input[pointer]);
        System.out.println(s);
    }
}