package ch.util.json;

/**
 * @author Severin Weigold, Noël Monnerat
 */

public class JsonParseException extends IllegalArgumentException {

    public JsonParseException() {
        super();
    }

    public JsonParseException(String s) {
        super(s);
    }

}
