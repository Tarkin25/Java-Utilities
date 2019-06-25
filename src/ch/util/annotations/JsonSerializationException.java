package ch.util.annotations;

public class JsonSerializationException extends NullPointerException {

    public JsonSerializationException(String s) {
        super(s);
    }

    public JsonSerializationException() {
        super();
    }

}
