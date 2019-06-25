package ch.util.json;

/**
 * @author Severin Weigold
 */

public class JSONField<T> {

    private Class type;
    private T value;

    JSONField(T value) {
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
