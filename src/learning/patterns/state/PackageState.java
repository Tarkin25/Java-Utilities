package learning.patterns.state;

public interface PackageState {

    void next(Package pack);
    void prev(Package pack);
    String getStatus();

}
