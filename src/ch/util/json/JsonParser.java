package ch.util.json;

import ch.util.strings.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    static JsonObject parseJsonObject(String s)  {
        if(s == null) {
            return null;
        }

        if(!(s.startsWith("{") && s.endsWith("}"))) {
            throw new JsonParseException("Json String must start with \"{\" and end with \"}\"");
        }

        s = s.replaceFirst("\\{", "");
        s = StringUtils.replaceLast(s, "\\}", "");

        JsonObject json = new JsonObject();

        String[] fieldValuePairs = s.split(",");

        for(String fieldValuePair : fieldValuePairs) {
            System.out.println(fieldValuePair);

            String[] fields = fieldValuePair.split(":");

            String field = fields[0].replace("\"", "");
            String valueString = fields[1];

            Object value = parseValue(valueString);

            json.put(field, value);
        }

        return json;
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
            return null;
        } else {
            throw new JsonParseException();
        }
    }

    private static List<String> splitJsonString(String s) {
        List<String> strings = new ArrayList<>();



        return strings;
    }

}
