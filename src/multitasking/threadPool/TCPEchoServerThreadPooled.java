package multitasking.threadPool;


import multitasking.ConsloeLogger;
import multitasking.EchoProtocolFactory;
import multitasking.Logger;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPEchoServerThreadPooled {

    public static void main(String[] args) throws IOException {

        if (args.length != 1)   //Test for correct number of args
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int echoServerPort = Integer.parseInt(args[0]); //Server port

        //Create a server socket to accept client connection requests
        ServerSocket serverSocket = new ServerSocket(echoServerPort);
        Logger logger = new ConsloeLogger();    //Log messages to console

        EchoProtocolFactory echoProtocol = new EchoProtocolFactory();
        PoolDispatcher poolDispatcher = new PoolDispatcher();
        poolDispatcher.startDispatching(serverSocket, logger, echoProtocol);

        //NOT REACHED
    }
}
