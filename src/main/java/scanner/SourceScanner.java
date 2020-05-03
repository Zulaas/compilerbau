package scanner;

public class SourceScanner extends AbstractScanner{

	
	SourceScanner(){

		char[] az = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q', 'r','s','t','u','v','w','x','y','z'};
		char[] n09 = {'0','1','2','3','4','5','6','7','8','9'};
		char[] az09 = {'a','b','c','d','e','f','g','h','i','j','k', 'l','m','n','o','p','q', 'r','s','t','u','v','w','x','y','z', '0','1','2','3','4','5','6','7','8','9'};
		char[] all = {'a','b','c','d','e','f','g','h','i','j','k', 'l','m','n','o','p','q', 'r','s','t','u','v','w','x','y','z', '0','1','2','3','4','5','6','7','8','9', ' ', ':'};

		char[][][] transitions ={
				//              START   KOMMA   SYMBOL  OPEN_PAR    CLOSE_PAR  PLUS     MINUS     MULT     DIV     NUM   Comp0              Comp1   ASSIGN   String  Enstate
				//              -------------------------------------------------------------------------------------------------------------------------------------------------
				/* 0 START*/       {{},    {},     az,     {'('},      {')'},     {'+'},   {'-'},    {'*'},   {'/'},  n09,  {'<', '>', '='},   {},     {'~'},  {'"'},  {' ', '\n', '\r'} },
				/* 1 KOMMA*/       {{},    {},     {},     {},         {},        {},      {},       {},      {},     n09,  {},                {},     {},     {},     {}          },
				/* 2 SYMBOL*/      {{},    {},     az09,   {},         {},        {},      {},       {},      {},     {},   {},                {},     {},     {},     {' '} },
				/* 3 OPEN_PAR*/    {{},    {},     {},     {},         {},        {},      {},       {},      {},     {},   {},                {},     {},     {},     {' '} },
				/* 4 CLOSE_PAR*/   {{},    {},     {},     {},         {},        {},      {},       {},      {},     {},   {},                {},     {},     {},     {' '} },
				/* 5 PLUS*/        {{},    {},     {},     {},         {},        {},      {},       {},      {},     {},   {},                {},     {},     {},     {' '} },
				/* 6 MINUS*/       {{},    {},     {},     {},         {},        {},      {},       {},      {},     {},   {},                {},     {},     {},     {' '} },
				/* 7 MULT*/        {{},    {},     {},     {},         {},        {},      {},       {},      {},     {},   {},                {},     {},     {},     {' '} },
				/* 8 DIV*/         {{},    {},     {},     {},         {},        {},      {},       {},      {},     {},   {},                {},     {},     {},     {' '} },
				/* 9 NUM */        {{},    {'.'},  {},     {},         {},        {},      {},       {},      {},     n09,  {},                {},     {},     {},     {' '} },
				/*10 Comp0*/       {{},    {},     {},     {},         {},        {},      {},       {},      {},     {},   {},                {'='},  {},     {},     {' '} },
				/*11 Comp1*/       {{},    {},     {},     {},         {},        {},      {},       {},      {},     {},   {},                {},     {},     {},     {' '} },
				/*12 ASSIGN*/      {{},    {},     {},     {},         {},        {},      {},       {},      {},     {},   {},                {},     {},     {},     {' '} },
				/*13 String*/      {{},    {},     {},     {},         {},        {},      {},       {},      {},     {},   {},                {},     {},     all,    {'"'}        },
				/*14 Endstate*/    {{},    {},     {},     {},         {},        {},      {},       {},      {},     {},   {},                {},     {},     {},     {}          }
		};

		byte[] states ={START, KOMMA, SYMBOL, OPEN_PAR, CLOSE_PAR, PLUS, MINUS, MULT, DIV, NUM, COMPARISION, COMPARISION, ASSIGN, STRING, EndState};
		this.dea=new DEA(transitions, states);
	}

	String getTokenString(byte token){
		switch(token){
			case  1: return "NUMBER";
			case  5: return "START";
			case  7: return "KOMMA";
			case  8: return "SYMBOL";
			case  9: return "OPEN_PAR";
			case 10: return "CLOSE_PAR";
			case 11: return "PLUS";
			case 12: return "MINUS";		
			case 13: return "MULT";
			case 14: return "DIV";
			case 22: return "FUNCTION";
			case 23: return "String";
			case 24: return "Enstate";
			case 25: return "Comparision";
            case 26: return "IF";
            case 27: return "DO";
            case 28: return "END";
			case 29: return "WHILE";
			case 30: return "DEFINE";
			case 31: return "ASSIGN";
            case 33: return "CALL";
		default: return "";
		}
	}

}