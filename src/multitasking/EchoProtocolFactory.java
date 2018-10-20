package multitasking;

//Implements a protocol factory for the echo protocol

import java.net.Socket;

public class EchoProtocolFactory implements ProtocolFactory{

    @Override
    public Runnable createProtocol(Socket clientSocket, Logger logger) {
        return new EchoProtocol(clientSocket, logger);
    }
}
