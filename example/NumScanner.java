/*
	NunScanner.java
	
	Diese Klasse implementiert die Zustnde und Transitionstabelle eines DEA für 
	Ziffernfolgen nach dem folgenden regulären Ausdruck:
	
													+
	NUM := {'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'|'0'}

*/
class NumScanner extends Scanner{
	
	//-------------------------------------------------------------------------
	// Konstruktor (Legt die Zustände und Transitionstabelle des DEA an)
	//-------------------------------------------------------------------------
	
	NumScanner(){
		// Transitionstabelle zum regulären Ausdruck
		//	    											+
		// NUM := {'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'|'0'}
		
		char transitions[][][]={
		//				START	KOMMA			IDENT														OPEN_PAR	  CLOSE_PAR		PLUS	MINUS	MULT	DIV									NUM
		//				-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		/*START*/		{{}, {','},	{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q',
									'r','s','t','u','v','w','x','y','z'},									{'('},		{')'}, 		{'+'},	{'-'},	{'*'},	{'/'},			{'1','2','3','4','5','6','7','8','9','0'}},
		/*KOMMA*/		{{}, {}, 					{},														{},			{},			{},		{},		{},		{},							{}   						 },
		/*IDENT*/		{{}, {},	{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q',
									'r','s','t','u','v','w','x','y','z'},									{},			{},			{},		{},		{},		{},							{}	 						 },
		/*OPEN_PAR*/	{{}, {},					{},														{},			{},			{},		{},		{},		{},							{}	 						 },
		/*CLOSE_PAR*/	{{}, {},					{},														{},			{},			{},		{},		{},		{},							{}	 						 },
		/*PLUS*/		{{}, {},					{},														{},			{},			{},		{},		{},		{},							{}	 						 },
		/*MINUS*/		{{}, {},					{},														{},			{},			{},		{},		{},		{},							{}	 						 },
		/*MULT*/		{{}, {},					{},														{},			{},			{},		{},		{},		{},							{}	 						 },
		/*DIV*/			{{}, {},					{},														{},			{},			{},		{},		{},		{},							{}	 						 },
		/*NUM  */		{{}, {},					{},														{}, 		{},			{},		{},		{},		{},				{'1','2','3','4','5','6','7','8','9','0'}}};
		//				-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		// Zustände zum DEA 
		byte states[]={START, KOMMA, IDENT, OPEN_PAR, CLOSE_PAR, PLUS, MINUS, MULT, DIV, NUM};
		// Instanz des DEA anlegen
		this.dea=new DEA(transitions, states);
	}
	
	// Gibt den zum Zahlenwert passenden String des Tokentyps zurück
	// Implementierung der abstrakten Methode aus der Klasse Scanner
	String getTokenString(byte token){
		switch(token){
			case  1: return "NUMBER";
			case  5: return "START";
			case  7: return "KOMMA";
			case  8: return "IDENT";
			case  9: return "OPEN_PAR";
			case 10: return "CLOSE_PAR";
			case 11: return "PLUS";
			case 12: return "MINUS";		
			case 13: return "MULT";
			case 14: return "DIV";		
		default: return "";
		}
	}
	
	

}