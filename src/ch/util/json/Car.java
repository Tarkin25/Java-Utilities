package ch.util.json;

public class Car {

    @JsonProperty
    private int factoryNumber;

    @JsonProperty
    private String owner;

    @JsonProperty
    private String licence;

    public Car() {}

    public Car(int factoryNumber, String owner, String licence) {
        this.factoryNumber = factoryNumber;
        this.owner = owner;
        this.licence = licence;
    }

    public int getFactoryNumber() {
        return factoryNumber;
    }

    public String getOwner() {
        return owner;
    }

    public String getLicence() {
        return licence;
    }
}
