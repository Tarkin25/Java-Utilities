package learning.patterns.state;

public class Package {

    private PackageState state;

    public void setState(PackageState state) {
        this.state = state;
    }

    public PackageState getState() {
        return this.state;
    }

    public void nextState() {
        state.next(this);
    }

    public void previousState() {
        state.next(this);
    }

    public void printInfo() {
        System.out.println(state.getStatus());
    }

}
