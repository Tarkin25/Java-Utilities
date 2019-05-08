package ch.util.net;

import ch.util.threads.ThreadUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class ClientConnection {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public ClientConnection(String hostname, int port) throws TimeoutException {
        this(hostname, port, 5);
    }

    public ClientConnection(final String hostname, final int port, float timeout) throws TimeoutException {
        ThreadUtil.runTimeoutThread(()->{
            try {
                socket = new Socket(hostname, port);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {

            }
        }, timeout);
    }

    public void close() {

    }

}
