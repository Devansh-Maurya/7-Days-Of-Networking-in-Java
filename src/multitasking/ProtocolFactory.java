package multitasking;

//Returns an instance implementing the desired protocol
//A factory object supplies instances of a particular class

import java.net.Socket;

public interface ProtocolFactory {

    public Runnable createProtocol(Socket clientSocket, Logger logger);
}
