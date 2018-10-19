package Echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServer {

    private static final int BUFFER_SIZE = 32;  // Size of receive buffer

    public static void main(String[] args) throws IOException {

        if (args.length != 1)   // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int serverPort = Integer.parseInt(args[0]);

        // Create a server socket to accept client connection requests
        ServerSocket serverSocket = new ServerSocket(serverPort);

        int receiveMessageSize;    // Size of received message
        byte[] byteBuffer = new byte[BUFFER_SIZE];  // Receive buffer

        while (true) {  // Run forever, accepting and servicing connections
            Socket clientSocket = serverSocket.accept();    // Get client connection

            System.out.println("Handling client at " +
                    clientSocket.getInetAddress().getHostAddress() + " on port " +
                    clientSocket.getPort());

            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();

            // Receive until client closes connection, indicated b y - i return
            while ((receiveMessageSize = in.read(byteBuffer)) != -1)
                out.write(byteBuffer, 0, receiveMessageSize);

            clientSocket.close();   // Close the socket. We are done with this client!
        }
        /* NOT REACHED */
    }
}
