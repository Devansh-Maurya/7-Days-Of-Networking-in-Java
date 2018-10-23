package fileDownload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static final int BUFSIZE = 256;

    public static void main(String[] args) throws IOException {

        if (args.length != 3)
            throw new IllegalArgumentException("Parameter(s): <Server> <Port> <File>");

        String server = args[0];
        int port = Integer.parseInt(args[1]);
        String filename = args[2];

        FileOutputStream fileOut = new FileOutputStream("new" + filename);

        Socket socket = new Socket(server, port);

        byte[] filenameBytes = filename.getBytes();

        OutputStream sockOut = socket.getOutputStream();
        sockOut.write(filenameBytes);
        System.out.println("Filename sent to the server");

        InputStream sockIn = socket.getInputStream();
        int bytesRead;
        byte[] buffer = new byte[BUFSIZE];
        while ((bytesRead = sockIn.read(buffer)) != -1) {
            fileOut.write(buffer, 0, bytesRead);
            System.out.println("R");        //Reading indicator
        }
        System.out.println();

        socket.close();
        fileOut.close();
    }
}
