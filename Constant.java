package task12;

/*
 * @see Context.constList
 * Holds one constant of any type
 */

class Constant {
    public Double doubleValue;
    public Integer intValue;
    public String stringValue;

    @Override
    public String toString() {
        return  (doubleValue != null ? "double=" + doubleValue	: "")
                + (intValue != null ? "int=" + intValue : "")
                + (stringValue != null ? "string=" + stringValue : "")
                ;
    }

    public Constant (double aValue) {
        doubleValue = aValue;
    }

    public Constant (String aValue) {
        stringValue = aValue;
    }

    public Constant (int aValue) {
        intValue = aValue;
    }

}
