package parser;

import scanner.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;


public class ArithmetikParserClass implements TokenList {

    public final char EOF=(char)255;
    private int pointer;
    private int maxPointer;
    public LinkedList<SourceScanner.Token> tokenStream;
    private LinkedList<SourceScanner.Token> tokens;
    private SyntaxTree parseTree;
    public HashMap<String, LinkedList<SourceScanner.Token>> tokenLists;
    public HashMap<String, SyntaxTree> treeList;

    ArithmetikParserClass(LinkedList<SourceScanner.Token> tokenStream){
        this.tokenStream = tokenStream;
    }

    boolean parse() {
        tokenLists = new HashMap<>();
        treeList = new HashMap<>();

        String function_name = "";
        int i = 0;
        while(i < tokenStream.size()) {
            SourceScanner.Token token = tokenStream.get(i);

            if (token.token == FUNCTION) {
                function_name = tokenStream.get(++i).lexem;
                tokenLists.put(function_name, new LinkedList<>());
                i++;
            }

            if (tokenStream.get(i).token != -1){
                tokenLists.get(function_name).add(tokenStream.get(i));
            }

            i++;
        }

        Iterator it = tokenLists.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();

            /*
            System.out.println(pair.getKey());
            for(Scanner.Token t : tokenLists.get(pair.getKey())) {
                System.out.println(t.token + ": " + t.lexem);
            }

            System.out.println("--------");
            */

            this.tokens = tokenLists.get(pair.getKey());
            this.pointer = 0;
            this.parseTree = new SyntaxTree(FUNCTION);

            if(this.tokens.getLast().token == END) {
                SourceScanner.Token t = this.tokens.getLast();
                t.token = -1;
                t.lexem = "EOF";
            } else if(this.tokens.getLast().token == -1) {
                this.tokens.remove(this.tokens.size() - 1);
            }


            this.maxPointer = this.tokens.size() - 1;
            if(!this.function(this.parseTree)) {
                return false;
            }
            treeList.put(pair.getKey().toString(), this.parseTree);

            it.remove();
        }

        return true;
    }


    boolean function(SyntaxTree sT) {
        return (
                parameter(sT.insertSubtree(PARAMETER))
                        &&
                        expression(sT.insertSubtree(EXPRESSION))
        );
    }

    boolean expression(SyntaxTree sT){
        if (compareToken(TokenList.DEFINE, sT)) {
            return (
                    define(sT.insertSubtree(DEFINE))
                            &&
                            rightExpression(sT.insertSubtree(RIGHT_EXPRESSION))
            );
        } else if (compareToken(TokenList.ASSIGN, sT)) {
            return (
                    assign(sT.insertSubtree(ASSIGN))
                            &&
                            rightExpression(sT.insertSubtree(RIGHT_EXPRESSION))
            );
        } else if (compareToken(TokenList.CALL, sT)) {
            return (
                    call(sT.insertSubtree(CALL))
                            &&
                            rightExpression(sT.insertSubtree(RIGHT_EXPRESSION))
            );
        } else if (compareToken(TokenList.IF, sT)) {
            return (
                    comparator(sT.insertSubtree(IF))
                            &&
                            rightExpression(sT.insertSubtree(RIGHT_EXPRESSION))
            );
        } else if (compareToken(TokenList.WHILE, sT)) {
            return (
                    comparator(sT.insertSubtree(WHILE))
                            &&
                            rightExpression(sT.insertSubtree(RIGHT_EXPRESSION))
            );
        } else if (compareToken(CALL, sT)) {
            return (
                    call(sT.insertSubtree(CALL))
                            &&
                            rightExpression(sT.insertSubtree(RIGHT_EXPRESSION))
            );
        } else if (compareToken(RETURN, sT)) {
            return returnStatement(sT.insertSubtree(RETURN));
        }
        else {
            sT.insertSubtree(EPSILON);
            return true;
        }
    }

    boolean define(SyntaxTree sT) {
        if (symbol(sT.insertSubtree(SYMBOL))) {
            if (inPlaceCompareToken(TokenList.SYMBOL,sT)) {
                return symbol(sT.insertSubtree(SYMBOL));
            } else if (inPlaceCompareToken(TokenList.STRING, sT)) {
                return string(sT.insertSubtree(STRING));
            }
            else if (term(sT.insertSubtree(TERM)) /*&& rightExpression(sT.insertSubtree(RIGHT_EXPRESSION))*/) {
                return true;
            }
        }

        return false;
    }

    boolean assign(SyntaxTree sT) {
        if (symbol(sT.insertSubtree(SYMBOL))) {
            if (inPlaceCompareToken(TokenList.SYMBOL,sT)) {
                return symbol(sT.insertSubtree(SYMBOL));
            } else if (inPlaceCompareToken(TokenList.STRING, sT)) {
                return string(sT.insertSubtree(STRING));
            }
            else if (term(sT.insertSubtree(TERM)) /*&& rightExpression(sT.insertSubtree(RIGHT_EXPRESSION))*/) {
                return true;
            }
        }

        return false;
    }

    boolean call(SyntaxTree sT) {
        if (match(TokenList.SYMBOL, sT)) {
            if (match(TokenList.NUM, sT)) {
                if (match(TokenList.SYMBOL, sT)) {
                    return true;
                }
            } else if (match(TokenList.SYMBOL, sT)) {
                if (match(TokenList.SYMBOL, sT)) {
                    return true;
                }
            } else if (match(TokenList.STRING, sT)) {
                if (match(TokenList.SYMBOL, sT)) {
                    return true;
                }
            }
        }

        return false;
    }

    boolean comparator(SyntaxTree sT) {
        return (
                comparision(sT.insertSubtree(COMPARISION))
                        &&
                        conditionBranch(sT.insertSubtree(EXPRESSION))
        );
    }

    boolean comparision(SyntaxTree sT) {
        boolean first = false;
        boolean second = false;
        boolean third = false;

        if (inPlaceCompareToken(TokenList.SYMBOL,sT)) {
            first = symbol(sT.insertSubtree(SYMBOL));
        } else if (num(sT.insertSubtree(NUM))) {
            first = true;
        }

        if (match(TokenList.COMPARISION, sT)) {
            second = true;
        }

        if (inPlaceCompareToken(TokenList.SYMBOL,sT)) {
            third = symbol(sT.insertSubtree(SYMBOL));
        } else if (num(sT.insertSubtree(NUM))) {
            third = true;
        }

        return first && second && third;
    }

    boolean conditionBranch(SyntaxTree sT) {
        if (match(TokenList.DO, sT)) {
            if (expression(sT.insertSubtree(EXPRESSION)) && rightExpression(sT.insertSubtree(RIGHT_EXPRESSION))) {
                if (match(TokenList.END, sT)) {
                    return true;
                }
            }
        }

        return false;
    }

    boolean rightExpression(SyntaxTree sT){
        if (match(TokenList.PLUS,sT)) {
            return (
                    term(sT.insertSubtree(TERM))
                            &&
                            rightExpression(sT.insertSubtree(RIGHT_EXPRESSION))
            );
        } else if (match(TokenList.MINUS,sT)) {
            return (
                    term(sT.insertSubtree(TERM))
                            &&
                            rightExpression(sT.insertSubtree(RIGHT_EXPRESSION))
            );
        } else if (match(TokenList.COMPARISION, sT)) { // Keine Ahnung obs drin sein muss
            return (
                    term(sT.insertSubtree(TERM))
                            &&
                            rightExpression(sT.insertSubtree(RIGHT_TERM))
            );
        } else {
            return (expression(sT));
        }
    }

    boolean term(SyntaxTree sT){
        return (
                operator(sT.insertSubtree(OPERATOR))
                        &&
                        rightTerm(sT.insertSubtree(RIGHT_TERM))
        );
    }

    boolean rightTerm(SyntaxTree sT){
        if (match(TokenList.MULT,sT) || match(TokenList.DIV, sT)) {
            return (
                    operator(sT.insertSubtree(OPERATOR))
                            &&
                            rightTerm(sT.insertSubtree(RIGHT_TERM))
            );
        } else {
            sT.insertSubtree(EPSILON);
            return true;
        }
    }

    boolean operator(SyntaxTree sT){
        if (match(TokenList.OPEN_PAR,sT)) {
            if (term(sT.insertSubtree(TERM)) && rightExpression(sT.insertSubtree(RIGHT_EXPRESSION))){

                if(match(TokenList.CLOSE_PAR,sT)) {
                    return true;
                } else {
                    syntaxError("Geschlossene 📎 erwartet");
                    return false;
                }

            }else{
                syntaxError("Fehler in geschachtelter Expression");
                return false;
            }
        } else if (inPlaceCompareToken(NUM, sT)) {
            return num(sT.insertSubtree(NUM));
        } else if (inPlaceCompareToken(SYMBOL, sT)) {
            return symbol(sT.insertSubtree(SYMBOL));
        }
        else {
            syntaxError("🔢 oder 🖇 auf erwartet");
            return false;
        }
    }

    boolean num(SyntaxTree sT){
        if (match(TokenList.NUM, sT)) {
            return true;
        } else {
            syntaxError("Ziffer erwartet");
            return false;
        }
    }

    boolean symbol(SyntaxTree sT) {
        if (match(TokenList.SYMBOL, sT)) {
            return true;
        } else {
            syntaxError("Symbol erwartet");
            return false;
        }
    }

    boolean string(SyntaxTree sT) {
        if (match(TokenList.STRING, sT)) {
            return true;
        } else {
            syntaxError("String erwartet");
            return false;
        }
    }

    boolean parameter(SyntaxTree sT) {
        if (match(TokenList.NUM, sT)) {
            return true;
        } else if (match(TokenList.SYMBOL, sT)) {
            return true;
        }
        else {
            syntaxError("Ziffer erwartet");
            return false;
        }
    }

    boolean returnStatement(SyntaxTree sT) {
        if (inPlaceCompareToken(NUM, sT)) {
            return num(sT.insertSubtree(NUM));
        } else if (inPlaceCompareToken(SYMBOL, sT)) {
            return symbol(sT.insertSubtree(SYMBOL));
        } else {
            return false;
        }
    }

    boolean match(byte token, SyntaxTree sT){
        SyntaxTree node;

        if (tokens.get(pointer).token==token){
            node=sT.insertSubtree(INPUT_SIGN);
            node.setCharacter(tokens.get(pointer).lexem);

            pointer++;
            return true;
        }

        return false;
    }

    boolean compareToken(byte token, SyntaxTree sT){
        if (tokens.get(pointer).token==token){
            pointer++;
            return true;
        }

        return false;
    }

    boolean inPlaceCompareToken(byte token, SyntaxTree sT) {
        return tokens.get(pointer).token==token;
    }

    boolean inputEmpty(){
        if (pointer==maxPointer){
            ausgabe("Eingabe 📭! bzw zu am Ende",0);
            return true;
        }else{
            syntaxError("Eingabe bei Ende des Parserdurchlaufs nicht leer");
            return false;
        }

    }

    void ausgabe(String s, int t){
        for(int i=0;i<t;i++)
            System.out.print("  ");
        System.out.println(s);
    }

    void syntaxError(String s){
        char z;
        if (pointer >= tokens.size()) {
            //System.out.println("Syntax Fehler beim " + (pointer + 1) + ". Zeichen: EOF");
            System.out.println("Syntax 💥 beim " + (pointer + 1) + ". Zeichen: 🔚");
        } else {
            System.out.println("Syntax 💥 beim " + (pointer + 1) + ". Zeichen: " + tokens.get(pointer).token);
            System.out.println(tokens.get(pointer).lexem);
        }
        System.out.println(s);
    }
}