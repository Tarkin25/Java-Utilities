package ch.util.json;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Severin Weigold, Noël Monnerat
 */

public class JsonObject implements Map<String, Object> {

    private Map<String, JsonField> map = new HashMap<>();

    /**
     * Maps the given object into a JSON-Object
     * Possibilities of mapping an object's field to a JSON-Field:
     * <ul>
     *     <li>Marking a field with the @{@link JsonProperty} annotation</li>
     *     <li>Implementing a conventional getter-method for the field</li>
     * </ul>
     * @param object {@link Object} to be mapped into a JSON-Object
     */
    public JsonObject(Object object) {
        JsonBuilder.setJsonFields(this, object);
    }

    /**
     * Creates an empty {@link JsonObject}
     */
    public JsonObject() {}

    /**
     *
     * @param jsonString {@link String} to be parsed
     * @return {@link JsonObject}
     * @throws JsonParseException
     */
    public static JsonObject parse(String jsonString) throws JsonParseException {
        return JsonParser.parseJsonObject(jsonString);
    }

    public static JsonObject parse(File file) throws IOException, JsonParseException {
        return JsonParser.parseJsonObject(file);
    }

    /**
     *
     * @param key {@link String} to specify the field
     * @param object {@link Object} to be stored in the specified field
     */
    public Object put(String key, Object object) {
        JsonField jsonField;

        if(object != null && object.getClass().isArray()) {
            jsonField = map.put(key, new JsonField<>(new JsonArray(object)));
        } else if(isOfSimpleType(object) || object instanceof JsonObject || object instanceof JsonArray) {
            jsonField = map.put(key, new JsonField<>(object));
        } else {
            jsonField = map.put(key, new JsonField<>(new JsonObject(object)));
        }

        if(jsonField != null) {
            return jsonField.getValue();
        } else {
            return null;
        }
    }

    /**
     *
     * @param key {@link String} to specify the field
     * @return The field's value, casted to the desired type
     * @throws ClassCastException if the field's value cannot be cast to the desired type
     */
    public <T> T get(String key) {
        JsonField jsonField = map.get(key);

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

    /**
     * Possibilities of casting a {@link JsonObject} to an object of the desired class:
     * <ul>
     *  <li>Marking a field with the @{@link JsonProperty} annotation</li>
     *  <li>Implementing a conventional setter-method for the field</li>
     *</ul>
     * @param clas {@link Class} to cast to
     * @return a new instance of the given class
     */
    public <T> T cast(Class<T> clas) {
        return JsonBuilder.cast(this, clas);
    }

    /**
     * @return a {@link String} representation of the {@link JsonObject}
     */
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
