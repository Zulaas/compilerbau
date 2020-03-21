/**
 * The interface Token list.
 * @author jan
 */
interface TokenList {

    /**
     * The constant NO_TYPE.
     */
    final byte NO_TYPE = 0,
    /**
     * The Num.
     */
    NUM = 1,
    /**
     * The Digit.
     */
    DIGIT = 2,
    /**
     * The Input sign.
     */
    INPUT_SIGN = 3,
    /**
     * The Epsilon.
     */
    EPSILON = 4,
    /**
     * The Start.
     */
    START = 5,
    /**
     * The Not final.
     */
    NOT_FINAL = 6,
    /**
     * The Komma.
     */
    KOMMA = 7,
    /**
     * The Ident.
     */
    IDENT = 8,
    /**
     * The Open par.
     */
    OPEN_PAR = 9,
    /**
     * The Close par.
     */
    CLOSE_PAR = 10,
    /**
     * The Plus.
     */
    PLUS = 11,
    /**
     * The Minus.
     */
    MINUS = 12,
    /**
     * The Mult.
     */
    MULT = 13,
    /**
     * The Div.
     */
    DIV = 14,
    /**
     * The Expression.
     */
    EXPRESSION = 15,
    /**
     * The Right expression.
     */
    RIGHT_EXPRESSION = 16,
    /**
     * The Term.
     */
    TERM = 17,
    /**
     * The Right term.
     */
    RIGHT_TERM = 18,
    /**
     * The Operator.
     */
    OPERATOR = 20,
    /**
     * The Program.
     */
    PROGRAM = 21,
    /**
     * The Function.
     */
    FUNCTION = 22,

    /**
     * The String.
     */
    STRING = 23,
    /**
     * The Endstate.
     */
    ENDSTATE = 24,
    /**
     * The Comparision.
     */
    COMPARISION = 25,
    /**
     * The If.
     */
    IF = 26,
    /**
     * The Do.
     */
    DO = 27,
    /**
     * The End.
     */
    END = 28,
    /**
     * The While.
     */
    WHILE = 29,
    /**
     * The Define.
     */
    DEFINE = 30,
    /**
     * The Assign.
     */
    ASSIGN = 31,
    /**
     * The Parameter.
     */
    PARAMETER = 32,
    /**
     * The Call.
     */
    CALL = 33,
    /**
     * The Return.
     */
    RETURN = 34;

    /**
     * The constant UNDEFINED.
     */
    final int UNDEFINED = 0x10000001;
}