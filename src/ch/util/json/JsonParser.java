package ch.util.json;

import ch.util.strings.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author Severin Weigold, Noël Monnerat
 */

class JsonParser {

    private static Set<Character> escapes = new HashSet<>();

    static {
        escapes.add(' ');
        escapes.add('\n');
        escapes.add('\t');
    }

    static JsonObject parseJsonObject(String s)  {
        if(s == null) {
            return null;
        }

        if(!(s.startsWith("{") && s.endsWith("}"))) {
            throw new JsonParseException("Json String must start with \"{\" and end with \"}\"");
        }

        s = trimJsonString(s);

        s = s.replaceFirst("\\{", "");
        s = StringUtils.replaceLast(s, "\\}", "");

        JsonObject json = new JsonObject();

        for(String fieldValuePair : splitJsonObjectString(s, ',')) {
            List<String> fields = splitJsonObjectString(fieldValuePair, ':');

            String field = fields.get(0).replace("\"", "");
            String valueString = fields.get(1);

            Object value = parseValue(valueString);

            json.put(field, value);
        }

        return json;
    }

    static JsonObject parseJsonObject(File file) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(file));

        StringBuilder sb = new StringBuilder();

        while(scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }

        return parseJsonObject(sb.toString());
    }

    private static Object parseValue(String s) {
        // check if String
        if(s.startsWith("\"") && s.endsWith("\"")) {
            return s.replace("\"", "");
        } else {
            // check if null
            if("null".equals(s)) {
                return null;
            } else {
                // check if Integer
                try {
                    return Integer.parseInt(s);
                } catch (NumberFormatException e1) {
                    // check if Double/Float
                    try {
                        return Double.parseDouble(s);
                    } catch (NumberFormatException e2) {
                        // check if Array
                        try {
                            return parseJsonArray(s);
                        } catch (JsonParseException e3) {
                            // check if JsonObject
                            try {
                                return parseJsonObject(s);
                            } catch (JsonParseException e4) {
                                // check if Boolean
                                try {
                                    return Boolean.parseBoolean(s);
                                } catch (Exception e5) {
                                    throw new JsonParseException();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static JsonArray parseJsonArray(String s) {
        if(s == null) {
            return null;
        }

        if(s.startsWith("[") && s.endsWith("]")) {
            s = s.replaceFirst("\\[", "");
            s = StringUtils.replaceLast(s, "\\]", "");

            List<String> array = splitJsonArrayString(s, ',');

            List<Object> values = new ArrayList<>();

            for(String value : array) {
                Object o = parseValue(value);

                values.add(o);
            }

            JsonArray jsonArray = new JsonArray(values.toArray());

            return jsonArray;
        } else {
            throw new JsonParseException();
        }
    }

    private static List<String> splitJsonObjectString(String s, char separator) {
        List<String> strings = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        boolean ignoreCommas = false;

        for(char c : s.toCharArray()) {
            if(!ignoreCommas && (c == '{' || c == '[')) {
                ignoreCommas = true;
            }

            if(ignoreCommas && (c == '}' || c == ']')) {
                ignoreCommas = false;
            }

            if(!ignoreCommas) {
                if(c != separator) {
                    sb.append(c);
                } else {
                    strings.add(sb.toString());
                    sb.setLength(0);
                }
            } else {
                sb.append(c);
            }
        }

        strings.add(sb.toString());

        return strings;
    }

    private static List<String> splitJsonArrayString(String s, char separator) {
        List<String> strings = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        boolean ignoreCommas = false;

        for(char c : s.toCharArray()) {
            if(!ignoreCommas && c == '[') {
                ignoreCommas = true;
            }

            if(ignoreCommas && c == ']') {
                ignoreCommas = false;
            }

            if(!ignoreCommas) {
                if(c != separator) {
                    sb.append(c);
                } else {
                    strings.add(sb.toString());
                    sb.setLength(0);
                }
            } else {
                sb.append(c);
            }
        }

        strings.add(sb.toString());

        return strings;
    }

    private static String trimJsonString(String s) {
        boolean ignoreEscapes = false;

        StringBuilder sb = new StringBuilder(s);

        for(int i=sb.length()-1;i>=0;i--) {
            char c = sb.charAt(i);

            if(c == '\"') {
                ignoreEscapes = !ignoreEscapes;
            }

            if(!ignoreEscapes) {
                if(escapes.contains(c)) {
                    sb.deleteCharAt(i);
                }
            }
        }

        return sb.toString();
    }

}
