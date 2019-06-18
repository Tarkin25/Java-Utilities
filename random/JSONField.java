package random;

public class JSONField<T> {

    private Class type;
    private T value;

    public JSONField(T value) {
        this.value = value;
        if(value != null) {
            this.type = value.getClass();
        }
    }

    public T getValue() {
        return value;
    }

    public Class getType() {
        return type;
    }

    public String toString() {
        return "value: "+value+", type: "+type.getName();
    }
}
