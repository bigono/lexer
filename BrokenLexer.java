package task12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * For "a := 12 + (b - a*11);"
 * it shall produce
ID_OR_KW 	7   //переменная  ID  = 7
ASSIGN 	0       //присваивать
INT 	0       //число integer
PLUS 	0       // плюс
LPAREN 	0       //скобка
ID_OR_KW 	8   //переменная  ID = 8
MINUS 	0       //минус
ID_OR_KW 	7   //переменная  ID  = 7
ASTERISK 	0   //звездочка
INT 	1       //число integer
RPAREN 	0       //скобка
SEMICOL 	0   //ТОЧКА С ЗАПЯТОЙ
Lexer finished!

лексер -  В информатике лексический анализ
процесс аналитического разбора входной последовательности символов
с целью получения на выходе последовательности символов, называемых «токенами».
 */



public class BrokenLexer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Context c = new Context();
        Lexer l = new Lexer("a := 12 + (b - a*11);", c);
        do {
            Lexeme m = l.next();
            System.out.println(m);
        } while (! l.hasFinished());
        System.out.println("Lexer finished!");
    }
}

