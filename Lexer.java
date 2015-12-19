package task12;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * Lexical analyzer - a beginning of a compiler pipeline.
 * Contains hardcoded grammar for Pascal-like programming language.
 * Sample use:
 *     Lexer l = new Lexer("a :=12 :b - a;");
 *     do {
 *          Lexeme m = l.next();
 *          System.out.println(m);
 *      } while (! l.hasFinished());
 *
 * Not designed for extending through inheritance
 */
class Lexer {
    public final static String[] reservedKeywords = {
            "IF", "THEN", "FOR", "WHILE", "NEXT", "UNTIL", "REPEAT"
    };
    private Context context;
    private String source;
    private int cur; // position of the next char after cc in the input stream
    private StringBuilder buf = new StringBuilder();
    private boolean finished = false;
    private boolean reachedEof = false;
    private char cc; // current character we are going to process.
    // step() fetches the next one into cc.

    private final static Map<Character, Lex> singleCharLexemeMap =
            Collections.unmodifiableMap(new HashMap<Character, Lex>() {
                static final long serialVersionUID = 1L;

                {
                    put('-', Lex.MINUS);
                    put('*', Lex.ASTERISK);
                    put('/', Lex.SLASH);
                    put(';', Lex.SEMICOL);
                    put('(', Lex.LPAREN);
                    put(')', Lex.RPAREN);
                    put('[', Lex.LBRACKET);
                    put(']', Lex.RBRACKET);
                    put('{', Lex.LBRACE);
                    put('}', Lex.RBRACE);
                    put('"', Lex.QUOTE);
                    put('\'', Lex.APOSTROPHE);
                }
            });

    public boolean hasFinished() {
        return finished;
    }

    public Lexer(String aSource, Context aContext) {
        source = aSource;
        context = aContext;
        cur = 1;
        cc = source.charAt(0);
    }


    private enum S { // State of the Lexer. Lexer.next() implements a state machine on these states.
        START, ID_OR_KW, INT, FLOAT
        ;
    }


    /**
     * @returns next lexeme or LEX.EOF
     */
    public Lexeme next() {
        if (finished) throw new RuntimeException("Lexer has already finished");

        S s = S.START;
        clear();
        do {
            switch (s) {
                case START: {
                    if (Character.isJavaIdentifierStart(cc)) {
                        add(); step(); s = S.ID_OR_KW; continue;
                    }

                    if (Character.isSpaceChar(cc)) { step(); continue; }

                    if (cc == ':') {
                        step();
                        if (cc == '=') { return new Lexeme(Lex.ASSIGN); }
                        return new Lexeme(Lex.COLON);
                    }

                    if (Character.isDigit(cc)) {
                        s = S.INT; add(); step(); continue;
                    }

                    Lex l = singleCharLexemeMap.get(cc);
                    if (l != null) { step(); return new Lexeme(l); }

                    return finish(Lex.ILLEGAL);
                }

                case ID_OR_KW: {
                    if (Character.isJavaIdentifierPart(cc)) {
                        add(); step(); continue;
                    }
                    return id_or_kw();
                }

                case INT: {
                    if (Character.isDigit(cc)) {
                        add(); step(); continue;
                    }
                    if (cc != '.') {
                        Constant c = new Constant(Integer.parseInt(buf.toString()));
                        int n = context.enumerateConstant(c);
                        return new Lexeme(Lex.INT, n);
                    }
                    s = S.FLOAT;
                    add(); step(); continue;
                }

                case FLOAT: {
                    if (Character.isDigit(cc)) {
                        add(); step(); continue;
                    }

                    // TODO implement exponential form
                    Constant c = new Constant(Double.parseDouble(buf.toString()));
                    int n = context.enumerateConstant(c);
                    return new Lexeme(Lex.FLOAT, n);
                }

            }
        } while (! reachedEof);

        if (reachedEof) {

            if (s == S.START) return finish(Lex.EOF);

            String sA[] = { s.toString() };
            return finish(Lex.UNEXPECTED_EOF, ErrorMessage.Codes.EOF_WHILE_SMTH, sA );
        }

        return finish(Lex.LEXER_ERROR);
    }

    /*
     * The buffer contains a word - either identifier or keyword.
     */
    private Lexeme id_or_kw() {
        return new Lexeme(Lex.ID_OR_KW, context.enumerateId(buf.toString()));
    }

    private Lexeme finish(Lex lexType, ErrorMessage.Codes code, String[] args) {
        finished = true;
        return new Lexeme(lexType, new ErrorMessage(code, args));
    }

    private Lexeme finish(Lex lexType) {
        finished = true;
        return new Lexeme(lexType);
    }

    private void clear() {
        buf.setLength(0);
    }

    private void add() {
        buf.append(cc);
    }

    /*
     * Assumes somebody checks reachedEof value after calling step().
     * Just in case we'll set cc to an impossible value after encountering EOF.
     */
    private void step() {
        if (cur >= source.length()) {
            cc = 0;
            reachedEof = true;
            finished = true;
            return;
        }
        cc = source.charAt(cur++);
    }


}
