
import java.util.*;

/**
 * The type Syntax tree.
 * @author jan
 */
class SyntaxTree implements TokenList {


    private LinkedList<SyntaxTree> childNodes;
    private byte token;
    private char character;
    /**
     * The Value.
     */
    public Semantic value;


    /**
     * Instantiates a new Syntax tree.
     *
     * @param t the t
     */
    SyntaxTree(byte t) {
        this.childNodes = new LinkedList<>();
        character = 0;
        setToken(t);
        setSemantikFunction(t);
    }

    /**
     * Sets token.
     *
     * @param t the t
     */
    void setToken(byte t) {
        this.token = t;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    byte getToken() {
        return this.token;
    }

    /**
     * Sets character.
     *
     * @param character the character
     */
    void setCharacter(char character) {
        this.character = character;
    }

    /**
     * Gets character.
     *
     * @return the character
     */
    char getCharacter() {
        return this.character;
    }

    /**
     * Print syntax tree.
     *
     * @param t the t
     */
    void printSyntaxTree(int t) {
        for (int i = 0; i < t; i++) {
            System.out.print("  ");
        }
        System.out.print(this.getTokenString());
        if (this.character != 0) {
            System.out.println(":" + this.getCharacter());
        } else {
            System.out.println("");
        }
        for (int i = 0; i < this.childNodes.size(); i++) {
            this.childNodes.get(i).printSyntaxTree(t + 1);
        }
    }

    /**
     * Gets token string.
     *
     * @return the token string
     */
    String getTokenString() {
        switch (this.token) {
            case 0:
                return "NO_TYPE";
            case 9:
                return "OPEN_PAR";
            case 10:
                return "CLOSE_PAR";
            case 15:
                return "EXPRESSION";
            case 16:
                return "RIGHT_EXPRESSION";
            case 17:
                return "TERM";
            case 18:
                return "RIGHT_TERM";
            case 1:
                return "NUMBER";
            case 20:
                return "OPERATOR";
            case 7:
                return "KOMMA";
            case 3:
                return "INPUT_SIGN";
            case 4:
                return "EPSILON";
            case 11:
                return "PLUS";
            case 12:
                return "MINUS";
            case 13:
                return "MULT";
            case 14:
                return "DIV";
            case 8:
                return "IDENT";
            default:
                return "";
        }
    }

    /**
     * Sets semantik function.
     *
     * @param b the b
     */
    void setSemantikFunction(byte b) {
        switch (b) {
            case 1:
                value = new Expression();
                break;
            case 2:
                value = new RightExpression();
                break;
            case 3:
                value = new Term();
                break;
            case 4:
                value = new RightTerm();
                break;
            case 5:
                value = new Num();
                break;
            case 6:
                value = new Operator();
                break;
            case 7:
                value = new Digit();
                break;
            default:
                value = new Semantic();
                break;
        }
    }

    /**
     * Insert subtree syntax tree.
     *
     * @param b the b
     * @return the syntax tree
     */
    SyntaxTree insertSubtree(byte b) {
        SyntaxTree node;
        node = new SyntaxTree(b);
        this.childNodes.addLast(node);
        return node;
    }

    /**
     * Gets child.
     *
     * @param i the
     * @return the child
     */
    SyntaxTree getChild(int i) {
        if (i > this.childNodes.size()) {
            return null;
        } else {
            return this.childNodes.get(i);
        }
    }

    /**
     * Gets child nodes.
     *
     * @return the child nodes
     */
    LinkedList getChildNodes() {
        return this.childNodes;
    }

    /**
     * Gets child number.
     *
     * @return the child number
     */
    int getChildNumber() {
        return childNodes.size();
    }
}