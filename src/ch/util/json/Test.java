package ch.util.json;

public class Test {

    public static void main(String[] args) {

        User user1 = new User("Max Muster", "max.muster@example.com", true);
        user1.setCar(new Car(123, "Hansli", "ZH 4859"));
        User user2 = new User("Hans Heiri", "hans.heiri@example.com", false);

        String unformatedJson = "{\n" +
                "  \"array\": [\n" +
                "    \"Hello\",\n" +
                "    \"World\",\n" +
                "    {\n" +
                "      \"name\": \"Test\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"name\": \"Max Muster\",\n" +
                "  \"car\": {\n" +
                "    \"licence\": \"ZH 12345\",\n" +
                "    \"number\": 123\n" +
                "  },\n" +
                "  \"test\": {\n" +
                "    \"name\": \"test\",\n" +
                "    \"age\": 12,\n" +
                "    \"anothertest\": {\n" +
                "      \"name\": \"anothertest\",\n" +
                "      \"number\": 123\n" +
                "    }\n" +
                "  }\n" +
                "}";

        String json = "{\"array\":[\"Hello Blyat!\",\"World\",{\"name\":\"Test\"}],\"name\":\"Max Muster\",\"car\":{\"licence\":\"ZH 12345\",\"number\": 123},\"test\":{\"name\":\"test\",\"age\":12,\"anothertest\": {\"name\":\"anothertest\",\"number\":123}}}";

        System.out.println(JsonParser.parseJsonObject(unformatedJson));
    }

}
