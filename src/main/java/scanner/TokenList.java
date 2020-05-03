package scanner;

public interface TokenList {

    final byte	NO_TYPE=0,
            NUM=1,
            DIGIT=2,
            INPUT_SIGN=3,
            EPSILON=4,
            START=5,
            NOT_FINAL=6,
            KOMMA=7,
            SYMBOL=8,
            OPEN_PAR=9,
            CLOSE_PAR=10,
            PLUS=11,
            MINUS=12,
            MULT=13,
            DIV=14,
            EXPRESSION=15,
            RIGHT_EXPRESSION=16,
            TERM=17,
            RIGHT_TERM=18,
            OPERATOR=20,
            PROGRAM=21,
            MACHMA=22,
            STRING=23,
            EndState=24,
            COMPARISION=25,
            WATWENN=26,
            DANN=27,
            HALTSTOPP=28,
            WHILE=29,
            GOENNDIR=30,
            GIBIHM=31,
            PARAMETER=32,
            RUFMA=33,
            HAURAUS=34,
            IST=35,
            DAT=36,
            RAUS=37;
    final int	UNDEFINED=0x10000001;
}