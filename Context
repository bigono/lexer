package task12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To be shared amongst various compiler parts.
 * Holds all the identifiers and constants we've encountered.
 * Lexer pushes them here, then other parts enhance them with more information
 */
class Context {
    public final Map<String, Integer> idMap = new HashMap<String, Integer>();
    public final List<Constant> constList= new ArrayList<Constant>();

    @Override
    public String toString() {
        return "Context [idMap=" + idMap + ", constList=" + constList + "]";
    }

    public Context() {
        for (String s : Lexer.reservedKeywords) {
            enumerateId(s);
        }
    }

    public int enumerateId(String s) {
        int n = idMap.size();
        idMap.put(s, n);
        return n;
    }

    /*
     * TODO
     * The method detecting repeating constants would save some data segment space.
     * The present implementation doesn't do it.
     */
    public int enumerateConstant(Constant c) {
        int n = constList.size();
        constList.add(c);
        return n;
    }

}
