package scanner;

import java.util.*;
import java.io.*;

abstract class AbstractScanner implements TokenList {

    public final char EOF = (char) 255;
    private LinkedList<InputCharacter> inputStream;
    private int pointer;
    private String lexem;
    public LinkedList<Token> tokenStream;
    DEA dea;

    boolean match(char[] matchSet) {
        for (char c : matchSet)
            if (inputStream.get(pointer).character == c) {
                char currentChar = inputStream.get(pointer).character;
                if (!(currentChar == '"' || currentChar == ' '))
                    lexem = lexem + inputStream.get(pointer).character;
                pointer++;
                return true;
            }
        return false;
    }

    void lexicalError(String s) {
        char z;
        System.out.println("lexikalischer Fehler in Zeile " +
                inputStream.get(pointer).line + ".\nZeichen: " +
                (char) inputStream.get(pointer).character);
    }

    abstract String getTokenString(byte token);

    void printTokenStream() {
        for (Token token : tokenStream)
            System.out.println(getTokenString(token.token) + ": " +
                    token.lexem);
    }

    void printInputStream() {
        for (InputCharacter inputCharacter : inputStream) System.out.print(inputCharacter.character);
        System.out.println();
    }

    public boolean readInput(String name) {
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
                    //}else if(((char)c)==' '){
                } else if (((char) c) == '\n') {
                    inputStream.addLast(new InputCharacter('\n', l));// carriage return ueberlesen und Zeilennummer hochzaehlen
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
        return true;
    }

    public boolean lexicalAnalysis() {
        char[] EOFSet = {EOF};
        byte token = NO_TYPE;
        while (!match(EOFSet)) {
            token = getNextToken();
            if (token == NO_TYPE) {
                return false;
            } else if (token == EndState) {

            } else if (token == SYMBOL) {
                matchLexem();
            } else {
                tokenStream.addLast(new Token(token, inputStream.get(pointer - 1).line, lexem));
            }
        }
        tokenStream.addLast(new Token((byte) EOF, inputStream.get(pointer - 1).line, "EOF"));
        return true;
    }

    private void matchLexem() {
        switch (lexem.trim()) {
            case "watwenn":
                tokenStream.addLast(new Token(WATWENN, inputStream.get(pointer - 1).line, lexem));
                break;
            case "dann":
                tokenStream.addLast(new Token(DANN, inputStream.get(pointer - 1).line, lexem));
                break;
            case "haltstopp":
                tokenStream.addLast(new Token(HALTSTOPP, inputStream.get(pointer - 1).line, lexem));
                break;
            case "goenndir":
                tokenStream.addLast(new Token(GOENNDIR, inputStream.get(pointer - 1).line, lexem));
                break;
            case "machma":
                tokenStream.add(new Token(MACHMA, inputStream.get(pointer - 1).line, lexem));
                break;
            case "rufma":
                tokenStream.add(new Token(RUFMA, inputStream.get(pointer - 1).line, lexem));
                break;
            case "gibihm":
                tokenStream.add(new Token(GIBIHM, inputStream.get(pointer - 1).line, lexem));
                break;
            case "hauraus":
                tokenStream.add(new Token(HAURAUS, inputStream.get(pointer - 1).line, lexem));
                break;
            case "dat":
                tokenStream.add(new Token(DAT, inputStream.get(pointer - 1).line, lexem));
                break;
            case "ist":
                tokenStream.add(new Token(IST, inputStream.get(pointer - 1).line, lexem));
                break;
            case "raus":
                tokenStream.add(new Token(RAUS, inputStream.get(pointer - 1).line, lexem));
                break;
            default:
                tokenStream.addLast(new Token(SYMBOL, inputStream.get(pointer - 1).line, lexem));
                break;
        }
    }

    byte getNextToken() {
        boolean transitionFound = false;
        int actualState = 0;
        int bufferState = 0;

        lexem = "";
        do {
            transitionFound = false;

            for (int j = 0; j < dea.transitions[actualState].length; j++) {
                if (match(dea.transitions[actualState][j])) {
                    if ((dea.states[j] == EndState) && bufferState != 0) {
                        actualState = bufferState;
                        transitionFound = false;
                        break;
                    } else {

                        actualState = j;
                        bufferState = actualState;
                        transitionFound = true;
                        break;
                    }

                }
            }
        } while (transitionFound);

        if ((dea.states[actualState] != NOT_FINAL) && (dea.states[actualState] != START)) {
            return dea.states[actualState];
        } else {
            lexicalError("");
            return NO_TYPE;
        }
    }

    static class InputCharacter {
        char character;
        int line;

        InputCharacter(char c, int l) {
            this.character = c;
            this.line = l;
        }
    }

    static class DEA {
        char[][][] transitions;
        byte[] states;

        DEA(char[][][] transitions, byte[] states) {
            this.transitions = transitions;
            this.states = states;
        }
    }

    public static class Token {
        public byte token;
        public String lexem;
        int line;

        Token(byte token, int line, String lexem) {
            this.token = token;
            this.lexem = lexem;
            this.line = line;
        }
    }
}