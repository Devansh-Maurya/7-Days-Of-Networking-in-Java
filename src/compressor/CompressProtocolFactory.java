package compressor;

import multitasking.Logger;
import multitasking.ProtocolFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

public class CompressProtocolFactory implements ProtocolFactory {

    public static final int BUFSIZE = 1024;     //Size of receive buffer

    @Override
    public Runnable createProtocol(Socket clientSocket, Logger logger) {
        return new Runnable() {
            @Override
            public void run() {
                CompressProtocolFactory.handleClient(clientSocket, logger);
            }
        };
    }

    public static void handleClient(Socket clientSocket, Logger logger) {
        ArrayList<String> entry = new ArrayList<>();
        entry.add("Client address and port = " +
                clientSocket.getInetAddress().getHostAddress() + ":" +
                clientSocket.getPort());
        entry.add("Thread = " + Thread.currentThread().getName());

        try {
            //Get the input and output streams from socket
            InputStream in = clientSocket.getInputStream();
            GZIPOutputStream out = new GZIPOutputStream(clientSocket.getOutputStream());

            byte[] buffer = new byte[BUFSIZE];      //Allocate read/write buffer
            int bytesRead;                          //Number of bytes read
            //Receive until client closes connection, indicated by -1 return
            while ((bytesRead = in.read(buffer)) != -1)
                out.write(buffer, 0, bytesRead);

            out.finish();       //Flush bytes from GZIPOutputStream
        } catch (IOException e) {
            logger.writeEntry("Exception = " + e.getMessage());
        }

        try {   //Close socket
            clientSocket.close();
        } catch (IOException e) {
            entry.add("Exception = " + e.getMessage());
        }

        logger.writeEntry(entry);
    }
}
