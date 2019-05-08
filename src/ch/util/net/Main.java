package ch.util.net;

import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) {

        try {
            new ClientConnection("10.10.100.109", 22222);
        } catch (TimeoutException e) {
            System.out.println("Unable to connect after 5 seconds");
        }

    }

}
