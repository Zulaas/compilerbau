import java.util.*;

class SyntaxTree implements TokenList {


    private LinkedList<SyntaxTree> childNodes;
    private byte token;
    private char character;
    public Semantic value;


    SyntaxTree(byte t) {
        this.childNodes = new LinkedList<>();
        character = 0;
        setToken(t);
        setSemantikFunction(t);
    }

    void setToken(byte t) {
        this.token = t;
    }

    byte getToken() {
        return this.token;
    }

    void setCharacter(char character) {
        this.character = character;
    }

    char getCharacter() {
        return this.character;
    }

    void printSyntaxTree(int t) {
        for (int i = 0; i < t; i++)
            System.out.print("  ");
        System.out.print(this.getTokenString());
        if (this.character != 0)
            System.out.println(":" + this.getCharacter());
        else
            System.out.println("");
        for (int i = 0; i < this.childNodes.size(); i++) {
            this.childNodes.get(i).printSyntaxTree(t + 1);
        }
    }

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

    void setSemantikFunction(byte b) {
        switch (b) {/*
			case 1: value=new Expression();
					break;
			case 2: value=new RightExpression();
					break;
			case 3: value=new Term();
					break;
			case 4: value=new RightTerm();
					break;
			case 5: value=new Num();
					break;
			case 6: value=new Operator();
					break;
			case 7: value=new Digit();
					break; */
            default:
                value = new Semantic();
                break;
        }
    }


    SyntaxTree insertSubtree(byte b) {
        SyntaxTree node;
        node = new SyntaxTree(b);
        this.childNodes.addLast(node);
        return node;
    }

    SyntaxTree getChild(int i) {
        if (i > this.childNodes.size())
            return null;
        else
            return this.childNodes.get(i);
    }

    LinkedList getChildNodes() {
        return this.childNodes;
    }

    int getChildNumber() {
        return childNodes.size();
    }


}