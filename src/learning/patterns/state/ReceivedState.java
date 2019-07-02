package learning.patterns.state;

public class ReceivedState implements PackageState {

    @Override
    public void next(Package pack) {
    System.out.println("The package is in it's final state.");
    }

    @Override
    public void prev(Package pack) {
        pack.setState(new DeliveredState());
    }

    @Override
    public String getStatus() {
        return "Received.";
    }

}
