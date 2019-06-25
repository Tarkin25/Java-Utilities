package ch.util.json;

public class Test {

    public static void main(String[] args) {

        User user1 = new User("Max Muster", "max.muster@example.com", true);
        User user2 = new User("Hans Heiri", "hans.heiri@example.com", false);

        JSONObject json = new JSONObject();
        json.put("name", "Qendrim");
        json.put("email", "qendrim@zoo.ch");
        json.put("gay", true);

        Car car = new Car(1, "Hansli", "ZH 2345");

        json.put("car", car);

        User user = json.cast(User.class);

        System.out.println(user);
    }

}
