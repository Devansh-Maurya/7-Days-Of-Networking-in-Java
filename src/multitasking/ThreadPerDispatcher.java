package multitasking;

//Thread per client dispatcher
//Same code as the loop in TCPEchoServerThread.java
//Only change is that it uses protocol factory

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerDispatcher implements Dispatcher{

    @Override
    public void startDispatching(ServerSocket serverSocket, Logger logger,
                                 ProtocolFactory protocolFactory) {
        //Run forever, accepting and spawning threads to service each connection
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                Runnable protocol = protocolFactory.createProtocol(clientSocket, logger);
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
