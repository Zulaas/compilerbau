/*
	Num.java
	
	Praktikum Algorithmen und Datenstrukturen
	Beispiel zum Versuch 2
	
	Diese Klasse repräsentiert als Unterklasse von Semantic die
	semantische Funktion der Regeln mit dem Nonterminal
	num auf der linken Seite. 

*/

class Num extends Semantic{
	
	//-------------------------------------------------------------------------
	// Berechnet die nächst größere 10er Potenz von v
	// Hilfsmethode für num.f
	//-------------------------------------------------------------------------
	private int potenz(int v){
		int p=10;
		while(v/p!=0)
			p=p*10;
		return p;
	}//potenz
	
	
	//-------------------------------------------------------------------------
	// num -> digit num | digit
	// num.f = digit.f*potenz(num.f)+num.f, mit potenz(z)= 10 hoch n, falls
	//												z div (10 hoch n-1)=0
	// num -> digit
	// num.f=digit.f
	//-------------------------------------------------------------------------
	int f(SyntaxTree t, int n){
		if (t.getChildNumber()==2){
			SyntaxTree digit=t.getChild(0),
					             num=t.getChild(1);
			int v=num.value.f(num,UNDEFINED);
			return digit.value.f(digit,UNDEFINED)*potenz(v)+v;
		}else{
			SyntaxTree digit=t.getChild(0);
			return digit.value.f(digit,UNDEFINED);
		}
	}//f 	
}//Num