package ch.util.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author Severin Weigold
 */

public class JsonObject implements Map<String, Object> {

    private Map<String, JsonField> map = new HashMap<>();

    public JsonObject(Object object) {
        JsonBuilder.setJsonFields(this, object);
    }

    public JsonObject(String json) {
        //TODO parseJsonObject json String
    }

    public JsonObject(File file) throws IOException {
        StringBuilder sb = new StringBuilder();

        FileInputStream inputStream = new FileInputStream(file);

        Scanner scanner = new Scanner(inputStream);

        while(scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }

        //TODO parseJsonObject json String
    }

    public JsonObject() {}

    public Object put(String field, Object o) {
        JsonField jsonField;

        if(o != null && o.getClass().isArray()) {
            jsonField = map.put(field, new JsonField<>(new JsonArray(o)));
        } else if(isOfSimpleType(o) || o instanceof JsonObject || o instanceof JsonArray) {
            jsonField = map.put(field, new JsonField<>(o));
        } else {
            jsonField = map.put(field, new JsonField<>(new JsonObject(o)));
        }

        if(jsonField != null) {
            return jsonField.getValue();
        } else {
            return null;
        }
    }

    public <T> T get(String field) {
        JsonField jsonField = map.get(field);

        if(jsonField != null) {
            Class<T> clazz = jsonField.getType();

            Object value = jsonField.getValue();

            T result = clazz.cast(value);

            return result;
        } else {
            return null;
        }
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
        return JsonBuilder.cast(this, clas);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for(String field : map.keySet()) {
            sb.append("\"").append(field).append("\":");

            Object value = map.get(field).getValue();

            if(isOfSimpleType(value) && !(value instanceof String) || value instanceof JsonObject || value instanceof JsonArray) {
                sb.append(value.toString());
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
        for(JsonField field : map.values()) {
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
