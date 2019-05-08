package ch.util.net;

public interface Handler {
    void handle(Object received);

    void displayMessage(String message);

    void displayErrorMessage(Exception e);
}
