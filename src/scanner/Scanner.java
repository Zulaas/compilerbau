import java.util.*;
import java.io.*;

abstract class Scanner implements TokenList {


    class InputCharacter {

        char character;
        int line;

        InputCharacter(char c, int l) {
            this.character = c;
            this.line = l;
        }
    }


    class DEA {
        char transitions[][][];
        byte states[];

        DEA(char transitions[][][], byte states[]) {
            this.transitions = transitions;
            this.states = states;
        }
    }


    class Token {
        byte token;
        String lexem;
        int line;

        Token(byte token, int line, String lexem) {
            this.token = token;
            this.lexem = lexem;
            this.line = line;
        }
    }

    public final char EOF = (char) 255;
    private LinkedList<InputCharacter> inputStream;
    private int pointer;
    private String lexem;
    LinkedList<Token> tokenStream;
    DEA dea;


    boolean match(char[] matchSet) {
        for (int i = 0; i < matchSet.length; i++)
            if (inputStream.get(pointer).character == matchSet[i]) {
                System.out.println("match:" + inputStream.get(pointer).character);
                lexem = lexem + inputStream.get(pointer).character;
                pointer++;    //Eingabepointer auf das nächste Zeichen setzen
                return true;
            }
        return false;
    }

    void lexicalError(String s) {
        char z;
        System.out.println("lexikalischer Fehler in Zeile " +
                inputStream.get(pointer).line + ". Zeichen: " +
                inputStream.get(pointer).character);
        System.out.println((byte) inputStream.get(pointer).character);
    }


    abstract String getTokenString(byte token);


    void printTokenStream() {
        for (int i = 0; i < tokenStream.size(); i++)
            System.out.println(getTokenString(tokenStream.get(i).token) + ": " +
                    tokenStream.get(i).lexem);
    }


    void printInputStream() {
        for (int i = 0; i < inputStream.size(); i++)
            System.out.print(inputStream.get(i).character);
        System.out.println();

    }

    boolean readInput(String name) {
        int c = 0;
        int l = 1;
        inputStream = new LinkedList<InputCharacter>();
        tokenStream = new LinkedList<Token>();
        try {
            FileReader f = new FileReader(name);
            while (true) {
                c = f.read();
                if (c == -1) {
                    inputStream.addLast(new InputCharacter(EOF, l));
                    break;
                } else if (((char) c) == ' ') {
                } else if (((char) c) == '\n') {

                    l++;
                } else if (c == 13) {

                } else {

                    inputStream.addLast(new InputCharacter((char) c, l));
                }
            }
        } catch (Exception e) {
            System.out.println("Fehler beim Dateizugriff: " + name);
            return false;
        }
        System.out.println(inputStream.size());
        return true;
    }

    boolean lexicalAnalysis() {
        char[] EOFSet = {EOF};
        byte token = NO_TYPE;

        while (!match(EOFSet)) {
            token = getNextToken();
            System.out.println(getTokenString(token));

            if (token == NO_TYPE)
                return false;
            else
                tokenStream.
                        addLast(new Token(token, inputStream.get(pointer - 1).line, lexem));
        }
        tokenStream.addLast(new Token((byte) EOF, inputStream.get(pointer - 1).line, "EOF"));
        return true;
    }

    byte getNextToken() {

        boolean transitionFound = false;
        int actualState = 0;
        lexem = "";

        do {

            transitionFound = false;
            for (int j = 0; j < dea.transitions[actualState].length; j++)
                if (match(dea.transitions[actualState][j])) {
                    actualState = j;
                    System.out.println(actualState + "->" + j);
                    transitionFound = true;
                    break;
                }
        } while (transitionFound);

        if ((dea.states[actualState] != NOT_FINAL) && (dea.states[actualState] != START))
            return dea.states[actualState];
        else {
            lexicalError("");
            System.out.println(pointer);
            return NO_TYPE;
        }
    }

}