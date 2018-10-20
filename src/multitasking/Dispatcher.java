package multitasking;

//Used to create a dispatching model to handle clients

import java.net.ServerSocket;

public interface Dispatcher {

    void startDispatching(ServerSocket serverSocket, Logger logger,
                          ProtocolFactory protocolFactory);
}
