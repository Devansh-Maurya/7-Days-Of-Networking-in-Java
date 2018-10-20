package multitasking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class EchoProtocol implements Runnable {

    public static final int BUFFER_SIZE = 32;   //Size (in bytes) of I/O buffer

    private Socket clientSocket;    //Connection socket
    private Logger logger;          //Logging facility

    public EchoProtocol(Socket clientSocket, Logger logger) {
        this.clientSocket = clientSocket;
        this.logger = logger;
    }

    @Override
    public void run() {
        ArrayList<String> entry = new ArrayList<>();
        entry.add("Client address and port = " +
                clientSocket.getInetAddress().getHostAddress() + ":" +
                clientSocket.getPort());
        entry.add("Thread = " + Thread.currentThread().getName());

        try {
            //Get the input and output I/O streams from socket
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();

            int receiveMessageSize;                     //Size of received message
            int totalBytesEchoed = 0;                   //Bytes received from client
            byte[] echoBuffer = new byte[BUFFER_SIZE];  //Receive buffer

            //Receive while client closes connection, indicated by -1
            while ((receiveMessageSize = in.read(echoBuffer)) != -1) {
                out.write(echoBuffer, 0, receiveMessageSize);
                totalBytesEchoed += receiveMessageSize;
            }

            entry.add("Client finished; echoed " + totalBytesEchoed + " bytes.");

        } catch (IOException e) {
            entry.add("Exception = " + e.getMessage());
        }

        try {   //Close socket
            clientSocket.close();
        } catch (IOException e) {
            entry.add("Exception = " + e.getMessage());
        }

        logger.writeEntry(entry);
    }
}
