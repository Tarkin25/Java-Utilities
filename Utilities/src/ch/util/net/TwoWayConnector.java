package ch.util.net;

import ch.util.threads.ThreadUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class TwoWayConnector {

    private static final int PORT = 8048;

    private float connectionTimeout = 5;

    private ServerSocket serverSocket;
    private Socket socket;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private volatile boolean listenForConnection = true;

    private volatile boolean connectManually = false;

    private volatile boolean connected = false;

    private Handler handler;

    private Object received;

    public TwoWayConnector(Handler handler) {
        this.handler = handler;

        setupServerSocket();

        setupConnectionListener();
    }

    public void startConnecting(String hostname) {
        if(socket != null) {
            disconnect();
        }

        listenForConnection = false;
        connectManually = true;

        displayMessage("Connecting to "+hostname);

        new Thread(() -> {
            while(connectManually) {
                try {
                    ThreadUtil.runTimeoutThread(() ->{
                        try {
                            socket = new Socket(hostname, PORT);

                            stopConnecting();

                            if(setupStreams()) {
                                connected = true;

                                setupIOHandler();

                                displayMessage("Connected to "+socket.getInetAddress().getHostName());
                            } else {
                                displayMessage("Error while setting up IO streams to "+socket.getInetAddress().getHostName());
                            }
                        } catch (IOException e) {
                            displayErrorMessage(e);
                        }
                    }, connectionTimeout);
                } catch(TimeoutException e) {
                    displayMessage("Connection to "+hostname+" timed out after "+ connectionTimeout +" seconds");
                }
            }
        }).start();
    }

    public void stopConnecting() {
        connectManually = false;
        listenForConnection = true;
    }

    public void disconnect() {
        connected = false;

        stopConnecting();

        try {
            inputStream.close();
            outputStream.close();

            displayMessage("Closed connection to "+socket.getInetAddress().getHostName());
            socket.close();
            socket = null;
        } catch(IOException e) {
            displayErrorMessage(e);
        } catch(NullPointerException e) {

        }
    }

    public void sendObject(Object object) {
        try {
            outputStream.writeObject(object);
            outputStream.flush();
        } catch (IOException e) {
            disconnect();
        }
    }

    private void setupServerSocket() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            displayErrorMessage(e);
        }
    }

    private boolean setupStreams() {
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            return true;
        } catch(IOException e) {
            displayErrorMessage(e);
            return false;
        }
    }

    private void setupIOHandler() {
        Thread t = new Thread() {
            public void run() {
                while(true) {
                    try {
                        received = inputStream.readObject();

                        handler.handle(received);

                        received = null;
                    } catch(IOException | ClassNotFoundException e) {
                        if(socket != null) {
                            disconnect();
                        }
                        try {
                            join();
                        } catch (InterruptedException ex) {
                            displayErrorMessage(ex);
                        }
                    }
                }
            }
        };

        t.start();
    }

    private void setupConnectionListener() {
        new Thread(() -> {
            while(true) {
                if(listenForConnection) {

                    try {
                        ThreadUtil.runTimeoutThread(() ->{
                            try {
                                if(serverSocket != null) {
                                    socket = serverSocket.accept();

                                    if(setupStreams()) {
                                        connected = true;

                                        setupIOHandler();

                                        listenForConnection = false;
                                        displayMessage("Connected to "+socket.getInetAddress().getHostName());
                                    } else {
                                        displayMessage("Error while setting up IO streams to "+socket.getInetAddress().getHostName());
                                    }
                                }
                            } catch(IOException e) {
                                displayErrorMessage(e);
                            }
                        }, connectionTimeout);
                    } catch(TimeoutException e) {
                    }

                }
            }
        }).start();
    }

    private void displayMessage(String message) {
        handler.displayMessage(message);
    }

    private void displayErrorMessage(Exception e) {
        handler.displayErrorMessage(e);
    }

    public void setConnectionTimeout(float seconds) {
        connectionTimeout = seconds;
    }

    public float getConnectionTimeout() {
        return connectionTimeout;
    }

    public boolean isConnected() {
        return connected;
    }

}
