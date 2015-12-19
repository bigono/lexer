package task12;

/*
 * Returned by Lexer
 */
class Lexeme {
    public Lex type;
    public int value;
    public ErrorMessage errorMessage;

    public Lexeme(Lex aType, ErrorMessage anErrorMessage) {
        type = aType;
        errorMessage = anErrorMessage;
    }

    public Lexeme(Lex aType, int aValue) {
        type = aType;
        value = aValue;
    }

    public Lexeme(Lex aType) {
        type = aType;
    }

    public String toString() {
        return type + " \t" + value + ((errorMessage == null) ? "":" \t" + errorMessage);
    }
}
