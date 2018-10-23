package fileDownload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMultiple {

    public static final int BUFSIZE = 256;

    public static void main(String[] args) throws IOException {

        if (args.length != 2)
            throw new IllegalArgumentException("Parameter(s): <Server> <Port>");

        String server = args[0];
        int port = Integer.parseInt(args[1]);
        String filename;

        Socket socket = new Socket(server, port);

        OutputStream sockOut;
        InputStream sockIn;

        while (true) {
            sockOut = socket.getOutputStream();
            sockIn = socket.getInputStream();

            System.out.println("Enter the filename: ");

            Scanner scanner = new Scanner(System.in);
            filename = scanner.nextLine();

            FileOutputStream fileOut = new FileOutputStream("new" + filename);

            byte[] filenameBytes = filename.getBytes();

            sockOut.write(filenameBytes);
            System.out.println("Filename sent to the server: " + filename);

            int bytesRead;
            byte[] buffer = new byte[BUFSIZE];
            while ((bytesRead = sockIn.read(buffer)) != -1) {
                fileOut.write(buffer, 0, bytesRead);
                System.out.println("R");
                System.out.println("Client while loop end: " + bytesRead);//Reading indicator
                for (int i = 0; i < 10; i++)
                    System.out.println(i);
            }
            //System.out.println("Client while loop end: " + bytesRead);//Reading indicator

            //System.out.println("Client while loop end");
            System.out.println();
            fileOut.close();
            sockOut.flush();
        }

        //socket.close();
    }
}
