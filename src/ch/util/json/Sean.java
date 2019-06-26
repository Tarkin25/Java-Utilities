package ch.util.json;

public class Sean {

    private int dickSize;

    private int iq;

    private String name;

    public Sean() {}

    public Sean(int dickSize, int iq, String name) {
        this.dickSize = dickSize;
        this.iq = iq;
        this.name = name;
    }

    public int getDickSize() {
        return dickSize;
    }

    public void setDickSize(int dickSize) {
        this.dickSize = dickSize;
    }

    public int getIq() {
        return iq;
    }

    public void setIq(int iq) {
        this.iq = iq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
