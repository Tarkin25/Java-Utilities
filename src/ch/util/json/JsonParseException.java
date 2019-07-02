package ch.util.json;

/**
 * @author Severin Weigold, Noël Monnerat
 */

public class JsonParseException extends RuntimeException {

    JsonParseException() {
        super();
    }

    JsonParseException(String s) {
        super(s);
    }

}
