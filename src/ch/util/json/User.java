package ch.util.json;

public class User {

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

    @JsonProperty
    private boolean gay;

    @JsonProperty
    private Car car;

    private Integer[] numbers = new Integer[]{1,2,3,4,5};

    public User(String name, String email, boolean gay) {
        this.name = name;
        this.email = email;
        this.gay = gay;
    }

    public User() {}

    public String toString() {
        return "Name: "+name+" Email: "+email+" Gay: "+gay+" Car licence: "+car.getLicence();
    }
}
