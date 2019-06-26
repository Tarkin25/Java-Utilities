package ch.util.json;

/**
 * @author Severin Weigold, Noël Monnerat
 */

public class JsonField<T> {

    private Class type;
    private T value;

    JsonField(T value) {
        this.value = value;
        if(value != null) {
            this.type = value.getClass();
        }
    }

    T getValue() {
        return value;
    }

    Class getType() {
        return type;
    }

    public String toString() {
        return "value: "+value+", type: "+type.getName();
    }
}
