package ch.util.json;

public class Test {

    public static void main(String[] args) {

        Sean sean = new Sean(500, 69, "Sean");

        JsonObject jsonObject = new JsonObject(sean);

        System.out.println(jsonObject);

    }

}
