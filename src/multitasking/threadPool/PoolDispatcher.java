package multitasking.threadPool;

//Implements the thread pool dispatcher model

import multitasking.Dispatcher;
import multitasking.Logger;
import multitasking.ProtocolFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PoolDispatcher implements Dispatcher {

    static final String NUMTHREADS = "8";           //Default thread pool size
    static final String THREADPROP = "Threads";     //name of thread property

    private int numThreads;             //Number of threads in the pool

    public PoolDispatcher() {
        //Get the number of threads from the System properties or take the default
        numThreads = Integer.parseInt(System.getProperty(THREADPROP, NUMTHREADS));
    }

    @Override
    public void startDispatching(ServerSocket serverSocket, Logger logger,
                                 ProtocolFactory protocolFactory) {
        //Create N-1 threads, each running an iterative server
        for (int i = 0; i < (numThreads - 1); i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dispatchLoop(serverSocket, logger, protocolFactory);
                }
            });
            thread.start();
            logger.writeEntry("Iterative server starting in main thread " +
                    Thread.currentThread().getName());
            //Use main thread as Nth iterative server
            dispatchLoop(serverSocket, logger, protocolFactory);
            //NOT REACHED
        }
    }

    private void dispatchLoop(ServerSocket serverSocket, Logger logger,
                              ProtocolFactory protocolFactory) {
        //Run forever, accepting and handling each connection
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();    //Block waiting for connection
                Runnable protocol = protocolFactory.createProtocol(clientSocket, logger);
                protocol.run();
            } catch (IOException e) {
                logger.writeEntry("Exception = " + e.getMessage());
            }
        }
    }
}
