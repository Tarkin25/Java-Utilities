package ch.util.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static ch.util.json.JsonObject.isOfSimpleType;

/**
 * @author Severin Weigold, Noël Monnerat
 */

public class JsonBuilder {

    static <T> T cast(JsonObject object, Class<T> clazz) {
        try {
            T result = clazz.newInstance();

            for(Field field : clazz.getFields()) {
                String fieldName;

                Object value;

                if(field.isAnnotationPresent(JsonProperty.class)) {
                    fieldName = field.getAnnotation(JsonProperty.class).value();

                    if("".equals(fieldName)) {
                        fieldName = field.getName();
                    }

                    field.setAccessible(true);

                    try {
                        value = object.get(fieldName);

                        if(!isOfSimpleType(value)) {
                            value = new JsonObject(value).cast(field.getType());
                        }

                        field.set(result, value);
                    } catch (NullPointerException e) {}
                } else {
                    fieldName = field.getName();

                    if(field.isAccessible()) {
                        System.out.println("Field "+fieldName+" is public");

                        value = object.get(fieldName);

                        if(!isOfSimpleType(value)) {
                            value = new JsonObject(value).cast(field.getType());
                        }

                        field.set(result, value);
                    } else {
                        try {
                            String methodName = getSetterMethodName(field);

                            Method setterMethod = clazz.getMethod(methodName, field.getType());

                            value = object.get(fieldName);

                            if(!isOfSimpleType(value)) {
                                value = new JsonObject(value).cast(field.getType());
                            }

                            setterMethod.invoke(result, value);
                        } catch (NoSuchMethodException | InvocationTargetException e) {
                        }
                    }
                }
            }

            for(Field field : clazz.getDeclaredFields()) {
                String fieldName;

                Object value;

                if(field.isAnnotationPresent(JsonProperty.class)) {
                    fieldName = field.getAnnotation(JsonProperty.class).value();

                    if("".equals(fieldName)) {
                        fieldName = field.getName();
                    }

                    field.setAccessible(true);

                    try {
                        value = object.get(fieldName);

                        if(!isOfSimpleType(value)) {
                            value = new JsonObject(value).cast(field.getType());
                        }

                        field.set(result, value);
                    } catch (NullPointerException e) {}
                } else {
                    fieldName = field.getName();

                    if(field.isAccessible()) {
                        System.out.println("Field "+fieldName+" is public");

                        value = object.get(fieldName);

                        if(!isOfSimpleType(value)) {
                            value = new JsonObject(value).cast(field.getType());
                        }

                        field.set(result, value);
                    } else {
                        try {
                            String methodName = getSetterMethodName(field);

                            Method setterMethod = clazz.getMethod(methodName, field.getType());

                            value = object.get(fieldName);

                            if(!isOfSimpleType(value)) {
                                value = new JsonObject(value).cast(field.getType());
                            }

                            setterMethod.invoke(result, value);
                        } catch (NoSuchMethodException | InvocationTargetException e) {
                        }
                    }
                }
            }

            return result;
        } catch (IllegalAccessException |InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

    static void setJsonFields(JsonObject json, Object object) {
        for(Field field : object.getClass().getFields()) {
            if(field.isAnnotationPresent(JsonProperty.class)) {
                String fieldName = field.getAnnotation(JsonProperty.class).value();

                if("".equals(fieldName)) {
                    fieldName = field.getName();
                }

                field.setAccessible(true);

                try {
                    json.put(fieldName, getValue(field, object));
                } catch (IllegalAccessException e) {}
            } else {
                String fieldName = field.getName();

                if(field.isAccessible()) {
                    try {
                        json.put(fieldName, getValue(field, object));
                    } catch (IllegalAccessException e) {}
                } else  {
                    try {
                        String methodName = getGetterMethodName(field);

                        Method getterMethod = object.getClass().getMethod(methodName);

                        json.put(fieldName, getValue(getterMethod, object));
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {}
                }
            }
        }

        for(Field field : object.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(JsonProperty.class)) {
                String fieldName = field.getAnnotation(JsonProperty.class).value();

                if("".equals(fieldName)) {
                    fieldName = field.getName();
                }

                field.setAccessible(true);

                try {
                    json.put(fieldName, getValue(field, object));
                } catch (IllegalAccessException e) {}
            } else {
                String fieldName = field.getName();

                if(field.isAccessible()) {
                    try {
                        json.put(fieldName, getValue(field, object));
                    } catch (IllegalAccessException e) {}
                } else  {
                    try {
                        String methodName = getGetterMethodName(field);

                        Method getterMethod = object.getClass().getMethod(methodName);

                        json.put(fieldName, getValue(getterMethod, object));
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {}
                }
            }
        }
    }

    private static Object getValue(Field field, Object object) throws IllegalAccessException {
        return field.get(object);
    }

    private static Object getValue(Method method, Object object) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(object);
    }

    private static String getSetterMethodName(Field field) {
        String methodName;

        String fieldName = field.getName();

        Class type = field.getType();

        String methodPrefix = "set";

        String firstLetter = fieldName.substring(0, 1).toUpperCase();

        methodName = methodPrefix + firstLetter + fieldName.substring(1);

        return methodName;
    }

    private static String getGetterMethodName(Field field) {
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

}
