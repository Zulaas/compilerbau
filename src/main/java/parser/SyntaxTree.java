package parser;

import scanner.TokenList;

import java.util.*;

class SyntaxTree implements TokenList {

    private LinkedList<SyntaxTree> childNodes;
    private byte token;
    private String character;

    SyntaxTree(byte t) {
        this.childNodes = new LinkedList<SyntaxTree>();
        character = "";
        setToken(t);
    }

    void setToken(byte t) {
        this.token = t;
    }

    byte getToken() {
        return this.token;
    }

    void setCharacter(String character) {
        this.character = character;
    }

    String getCharacter() {
        return this.character;
    }

    void printSyntaxTree(String indent, boolean last) {
        System.out.print(indent);
        if (last) {
            System.out.print("\\-");
            indent += " ";
        } else {
            System.out.print("|-");
            indent += "| ";
        }

        System.out.print(this.getTokenString());
        if (!this.character.equals(""))
            System.out.print(":" + this.getCharacter());
        System.out.println();


        for (int i = 0; i < this.childNodes.size(); i++) {
            this.childNodes.get(i).printSyntaxTree(indent, i == this.childNodes.size() - 1);
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
                return "SYMBOL";
            case 2:
                return "DIGIT";
            case 25:
                return "COMPARISION";
            case 30:
                return "DEFINE";
            case 31:
                return "ASSIGN";
            case 22:
                return "FUNCTION";
            case 32:
                return "PARAMETER";
            case 26:
                return "IF";
            case 29:
                return "WHILE";
            case 33:
                return "CALL";
            case 34:
                return "RETURN";
            default:
                return "";
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

    LinkedList<SyntaxTree> getChildNodes() {
        return this.childNodes;
    }

    int getChildNumber() {
        return childNodes.size();
    }


}