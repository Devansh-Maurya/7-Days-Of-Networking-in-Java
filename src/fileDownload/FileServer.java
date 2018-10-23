package fileDownload;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

    public static final int BUFSIZE = 256;

    public static void main(String[] args) throws IOException {

        if (args.length != 1)
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int port = Integer.parseInt(args[0]);

        ServerSocket serverSocket = new ServerSocket(port);

        int bytesRead;
        byte[] buffer = new byte[BUFSIZE];

        Socket clientSocket = serverSocket.accept();

        InputStream in = clientSocket.getInputStream();
        OutputStream out = clientSocket.getOutputStream();

        bytesRead = in.read(buffer);

        String receivedFileName = new String(buffer);
        System.out.println("Bytes read: " + bytesRead + "\nFilename: " + receivedFileName);

        //Trim is important, otherwise FileNotFoundException is thrown
        FileInputStream fileIn = new FileInputStream(receivedFileName.trim());

        while ((bytesRead = fileIn.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
            System.out.println("W");        //Writing indicator
        }

        serverSocket.close();
        fileIn.close();
}
}
