/**
 * The type Token.
 *
 * @author jan on 21.03.2020
 * @project compilerbau
 */
class Token {
    /**
     * The Token.
     */
    byte token;
    /**
     * The Lexem.
     */
    String lexem;
    /**
     * The Line.
     */
    int line;

    /**
     * Instantiates a new Token.
     *
     * @param token the token
     * @param line  the line
     * @param lexem the lexem
     */
    Token(byte token, int line, String lexem) {
        this.token = token;
        this.lexem = lexem;
        this.line = line;
    }
}