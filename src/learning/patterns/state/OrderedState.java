package learning.patterns.state;

public class OrderedState implements PackageState {

    @Override
    public void next(Package pack) {
        pack.setState(new DeliveredState());
    }

    @Override
    public void prev(Package pack) {
    System.out.println("The package is in it's root state.");
    }

    @Override
    public String getStatus() {
        return "Ordered";
    }
}
