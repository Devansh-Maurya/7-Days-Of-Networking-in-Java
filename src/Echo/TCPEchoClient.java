package Echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class TCPEchoClient {

    public static void main(String[] args) throws Exception {

        if ((args.length < 2) || (args.length > 3)) // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

        String server = args[0];    // Server name or IP address
        // Convert input String to bytes using the default character encoding
        byte[] byteBuffer = args[1].getBytes();

        int serverPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

        // Create socket that is connected to server on specified port
        Socket socket = new Socket(server, serverPort);
        System.out.println("Connected to server...sending echo string");

        Thread.sleep(10000);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        out.write(byteBuffer); // Send the encoded string to the server

        // Receive the same string back from the server
        int totalBytesReceived = 0; // Total bytes received so far
        int bytesReceived;          // Bytes received in last read

        while (totalBytesReceived < byteBuffer.length) {
            if ((bytesReceived = in.read(byteBuffer, totalBytesReceived,
                    byteBuffer.length - totalBytesReceived)) == -1)
                throw new SocketException("Connection closed prematurely");

            totalBytesReceived += bytesReceived;
        }

        System.out.println("Received: " + new String(byteBuffer));

        socket.close(); // Close the socket and its streams
    }
}
