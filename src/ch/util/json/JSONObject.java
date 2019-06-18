package ch.util.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class JSONObject {

    private Map<String, JSONField> map = new HashMap<>();

    private Class clas;

    public JSONObject(Object object) {
        this.clas = object.getClass();

        if(clas.isArray()) {

        }

        for(Field field : clas.getFields()) {
            try {
                String fieldName = field.getName();

                Object value = getValue(field, object);

                set(fieldName, value);
            } catch (IllegalAccessException e) {}
        }

        for(Field field : clas.getDeclaredFields()) {
            try {
                String fieldName = field.getName();

                try {
                    String methodName = getMethodName(field);

                    Method getterMethod = clas.getMethod(methodName);

                    Object value = getValue(getterMethod, object);

                    set(fieldName, value);
                } catch (NoSuchMethodException | InvocationTargetException e) {}
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject() {}

    public void set(String field, Object o) {
        if((o != null && o.getClass().isArray()) || isOfSimpleType(o)) {
            map.put(field, new JSONField<>(o));
        } else {
            map.put(field, new JSONField<>(new JSONObject(o)));
        }
    }

    public <T> T get(String field) {
        JSONField jsonField = map.get(field);

        Class<T> clas = jsonField.getType();

        Object value = jsonField.getValue();

        T result = clas.cast(value);

        return result;
    }

    public Map<String, Class> getFields() {
        Map<String, Class> fields = new HashMap<>();

        for(String field : map.keySet()) {
            fields.put(field, map.get(field).getType());
        }

        return fields;
    }

    private static String getMethodName(Field field) {
        String methodName;

        String fieldName = field.getName();

        Class type = field.getType();

        String methodPrefix;

        if(type != Boolean.TYPE) {
            methodPrefix = "get";
        } else {
            methodPrefix = "is";
        }

        String firstLetter = fieldName.substring(0, 1).toUpperCase();

        methodName = methodPrefix + firstLetter + fieldName.substring(1);

        return methodName;
    }

    private static Object getValue(Field field, Object object) throws IllegalAccessException {
        return field.get(object);
    }

    private static Object getValue(Method method, Object object) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(object);
    }

    static boolean isOfSimpleType(Object o) {
        return o instanceof String || o instanceof Boolean || o instanceof Character || o instanceof Byte || o instanceof Short || o instanceof Integer || o instanceof Long || o instanceof Float || o instanceof Double || o == null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for(String field : map.keySet()) {
            sb.append("\"").append(field).append("\":");

            Object value = map.get(field).getValue();

            if(value != null && value.getClass().isArray()) {
                Object[] array = (Object[])value;

                sb.append("[");

                for(Object object : array) {
                    sb.append(object).append(",");
                }

                sb.setLength(sb.length()-1);

                sb.append("]");
            } else if(isOfSimpleType(value) && !(value instanceof String) || value instanceof JSONObject) {
                sb.append(value);
            } else {
                sb.append("\"").append(value).append("\"");
            }

            sb.append(",");
        }

        sb.setLength(sb.length()-1);

        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        User user1 = new User("Max Muster", "max.muster@example.com", true);
        User user2 = new User("Hans Heiri", "hans.heiri@example.com", false);

        JSONObject json = new JSONObject();
        json.set("users", new User[]{user1, user2});
        json.set("car", new Car(12345, "Max Muster", "ZH 5678"));
        json.set("array", new Integer[]{1,2,3,4,5});

        System.out.println(json);
    }

}
