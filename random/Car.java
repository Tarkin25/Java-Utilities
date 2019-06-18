package random;

public class Car {

    public int factoryNumber;
    public String owner;
    public String licence;

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
