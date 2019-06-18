package random;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JSONObject {

    private Map<String, JSONField> map = new HashMap<>();

    private Class clas;

    public JSONObject(Object object) {
        this.clas = object.getClass();

        for(Field field : clas.getFields()) {
            try {
                String fieldName = field.getName();

                Object value = getValue(field, object);

                put(fieldName, value);
            } catch (IllegalAccessException e) {}
        }

        for(Field field : clas.getDeclaredFields()) {
            try {
                String fieldName = field.getName();

                try {
                    String methodName = getMethodName(field);

                    Method getterMethod = clas.getMethod(methodName);

                    Object value = getValue(getterMethod, object);

                    put(fieldName, value);
                } catch (NoSuchMethodException | InvocationTargetException e) {}
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject() {}

    public void put(String field, Object value) {
        if((value != null && value.getClass().isArray()) || isOfSimpleType(value)) {
            map.put(field, new JSONField<>(value));
        } else {
            map.put(field, new JSONField<>(new JSONObject(value)));
        }

        if(value != null && value.getClass().isArray()) {
            Object[] newArray = (Object[]) value;

            map.put(field, new JSONField<>(new JSONArray(newArray)));
        } else if(isOfSimpleType(value)) {
            map.put(field, new JSONField<>(value));
        } else {
            map.put(field, new JSONField<>(new JSONObject(value)));
        }
    }

    public <T> T get(String field) {
        JSONField jsonField = map.get(field);

        Class<T> clas = jsonField.getType();

        Object value = jsonField.getValue();

        if(value.getClass().isArray()) {
            T[] result;
        } else {
            T result = clas.cast(value);

            return result;
        }

        return null;
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

            if(isOfSimpleType(value) && !(value instanceof String) || value instanceof JSONObject || value instanceof JSONArray) {
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
        User user = new User("Max Muster", "max.muster@example.com", true);

        JSONObject json = new JSONObject(user);
        json.put("user", new User("Hans Heiri", null, false));
        json.put("car", new Car(12345, "Max Muster", "ZH 5678"));
        json.put("array", new Integer[]{1,2,3,4,5});

        System.out.println(json.get("array").toString());

        System.out.println(json);
    }

}
