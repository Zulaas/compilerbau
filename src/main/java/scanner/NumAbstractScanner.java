
class NumAbstractScanner extends AbstractScanner {


    NumAbstractScanner() {

        char[][][] transitions = {

                {{}, {','}, {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}, {'('}, {')'}, {'+'}, {'-'}, {'*'}, {'/'}, {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'}},
                {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}},
                {{}, {}, {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}, {}, {}, {}, {}, {}, {}, {}},
                {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}},
                {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}},
                {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}},
                {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}},
                {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}},
                {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}},
                {{}, {}, {}, {}, {}, {}, {}, {}, {}, {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'}}};

        byte[] states = {START, KOMMA, IDENT, OPEN_PAR, CLOSE_PAR, PLUS, MINUS, MULT, DIV, NUM};
        this.dea = new DEA(transitions, states);
    }

    @Override
    String getTokenString(byte token) {
        switch (token) {
            case 1:
                return "NUMBER";
            case 5:
                return "START";
            case 7:
                return "KOMMA";
            case 8:
                return "IDENT";
            case 9:
                return "OPEN_PAR";
            case 10:
                return "CLOSE_PAR";
            case 11:
                return "PLUS";
            case 12:
                return "MINUS";
            case 13:
                return "MULT";
            case 14:
                return "DIV";
            default:
                return "";
        }
    }


}