package compressor;

import java.io.*;
import java.net.Socket;

public class CompressClient {

    public static final int BUFSIZE = 256;  //Size of read buffer

    public static void main(String[] args) throws IOException {

        if (args.length != 3)   //Test for correct number of args
            throw new IllegalArgumentException("Parameter(s): <Server> <Port> <File> ");

        String server = args[0];                //Server name or IP address
        int port = Integer.parseInt(args[1]);   //Server port
        String filename = args[2];              //File to read data from

        //Open input and output file (named input.gz)
        FileInputStream fileIn = new FileInputStream(filename);
        FileOutputStream fileOut = new FileOutputStream(filename + ".gz");

        //Create socket connected to a server on specified port
        Socket socket = new Socket(server, port);

        //Send uncompressed byte stream to server
        sendBytes(socket, fileIn);

        //Receive compressed byte stream from server
        InputStream sockIn = socket.getInputStream();
        int bytesRead;                      //Number of bytes read
        byte[] buffer = new byte[BUFSIZE];  //Byte buffer
        while ((bytesRead = sockIn.read(buffer)) != -1) {
            fileOut.write(buffer, 0, bytesRead);
            System.out.println("R");        //Reading process indicator
        }
        System.out.println();               //End progress indicator line

        socket.close();         //Close the socket and its streams
        fileIn.close();         //Close file streams
        fileOut.close();
    }

    private static void sendBytes(Socket socket, InputStream fileIn)
        throws IOException {
        OutputStream sockOut = socket.getOutputStream();
        int bytesRead;                      //Number of bytes read
        byte[] buffer = new byte[BUFSIZE];  //Byte buffer

        while ((bytesRead = fileIn.read(buffer)) != -1) {
            sockOut.write(buffer, 0, bytesRead);
            System.out.println("W");        //Writing progress indicator
        }
        socket.shutdownInput();             //Finished sending
    }
}
