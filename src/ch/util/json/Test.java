package ch.util.json;

public class Test {

    public static void main(String[] args) {

        User user1 = new User("Max Muster", "max.muster@example.com", true);
        user1.setCar(new Car(123, "Hansli", "ZH 4859"));
        User user2 = new User("Hans Heiri", "hans.heiri@example.com", false);

        JsonObject json = new JsonObject();
        json.put("name", "Max Muster");

        JsonObject car = new JsonObject();
        car.put("owner", "Hansli");
        car.put("number", 123);
        car.put("licence", "ZH 784");

        json.put("car", car);

        System.out.println(json);

        System.out.println(JsonParser.parseJsonObject("{\"name\":\"Max Muster\",\"car\":{\"licence\":\"ZH 12345\",\"number\":123}}"));


    }

}
