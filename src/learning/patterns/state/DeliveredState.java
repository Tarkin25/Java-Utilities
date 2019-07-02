package learning.patterns.state;

public class DeliveredState implements PackageState {

    @Override
    public void next(Package pack) {
        pack.setState(new ReceivedState());
    }

    @Override
    public void prev(Package pack) {
        pack.setState(new OrderedState());
    }

    @Override
    public String getStatus() {
        return "Delivered";
    }
}
