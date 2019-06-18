package ch.util.json;

import static ch.util.json.JSONObject.isOfSimpleType;

public class JSONField<T> {

    private Class type;
    private T value;

    public JSONField(T value) {
        this.value = value;
        if(value != null) {
            this.type = value.getClass();

            if(type.isArray()) {
                Object[] array = (Object[]) value;

                for(int i=0;i<array.length;i++) {
                    Object o = array[i];

                    if((o != null && o.getClass().isArray()) || isOfSimpleType(o)) {
                    } else {
                        array[i] = new JSONObject(o);
                    }
                }
            }
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
