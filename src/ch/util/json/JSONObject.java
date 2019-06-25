package ch.util.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Severin Weigold
 */

public class JSONObject implements Map<String, Object> {

    private Map<String, JSONField> map = new HashMap<>();

    public JSONObject(Object object) {
        JSONBuilder.setJsonFields(this, object);
    }

    public JSONObject(String json) {
        //TODO parse json String
    }

    public JSONObject(File file) throws IOException {
        StringBuilder sb = new StringBuilder();

        FileInputStream inputStream = new FileInputStream(file);

        Scanner scanner = new Scanner(inputStream);

        while(scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }

        //TODO parse json String
    }

    public JSONObject() {}

    public Object put(String field, Object o) {
        JSONField jsonField;

        if(o != null && o.getClass().isArray()) {
            jsonField = map.put(field, new JSONField<>(new JSONArray(o)));
        } else if(isOfSimpleType(o)) {
            jsonField = map.put(field, new JSONField<>(o));
        } else {
            jsonField = map.put(field, new JSONField<>(new JSONObject(o)));
        }

        if(jsonField != null) {
            return jsonField.getValue();
        } else {
            return null;
        }
    }

    public <T> T get(String field) {
        JSONField jsonField = map.get(field);

        Class<T> clazz = jsonField.getType();

        Object value = jsonField.getValue();

        T result = clazz.cast(value);

        return result;
    }

    public Map<String, Class> getFields() {
        Map<String, Class> fields = new HashMap<>();

        for(String field : map.keySet()) {
            fields.put(field, map.get(field).getType());
        }

        return fields;
    }

    static boolean isOfSimpleType(Object o) {
        return o instanceof String || o instanceof Boolean || o instanceof Character || o instanceof Byte || o instanceof Short || o instanceof Integer || o instanceof Long || o instanceof Float || o instanceof Double || o == null;
    }

    public <T> T cast(Class<T> clas) {
        return JSONBuilder.cast(this, clas);
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

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for(JSONField field : map.values()) {
            if(field.getValue().equals(value)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object get(Object key) {
        return get(key.toString());
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        //TODO putAll

        for(String field : m.keySet()) {
            put(field, m.get(field));
        }
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Object> values() {
        Collection<Object> values = new ArrayList<>();

        for(String field : keySet()) {
            values.add(get(field));
        }

        return values;
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        Set<Entry<String, Object>> entrySet = new HashSet<>();

        for(String field : keySet()) {
            entrySet.add(new AbstractMap.SimpleEntry<>(field, get(field)));
        }

        return entrySet;
    }

}
