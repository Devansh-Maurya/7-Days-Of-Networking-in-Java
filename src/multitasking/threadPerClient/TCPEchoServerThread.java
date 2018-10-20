package multitasking.threadPerClient;

//Creates a new thread to handle a collection

import multitasking.ConsloeLogger;
import multitasking.EchoProtocol;
import multitasking.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServerThread {

    public static void main(String[] args) throws IOException {

        if (args.length != 1)   //Test for correct number of args
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int echoServerPort = Integer.parseInt(args[0]); //Server port

        //Create a server socket to accept client connection requests
        ServerSocket serverSocket = new ServerSocket(echoServerPort);

        Logger logger = new ConsloeLogger();    //Log messages to console

        //Run forever, accepting and spawning threads to service each connection
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();    //Block waiting for connection
                EchoProtocol protocol = new EchoProtocol(clientSocket, logger);
                Thread thread = new Thread(protocol);
                thread.start();
                logger.writeEntry("Created and started Thread = " + thread.getName());
            } catch (IOException e) {
                logger.writeEntry("Exception = " + e.getMessage());
            }
        }
        //NOT REACHED
    }
}
