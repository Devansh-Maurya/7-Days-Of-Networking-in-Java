package ItemQuoteTransferProtocol;

import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveTCP {

    public static void main(String[] args) throws Exception{

        if (args.length != 1)   // Test for corect number of args
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int port = Integer.parseInt(args[0]);   //Receiving Port

        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();

        //Receive text-encoded quote
        ItemQuoteDecoder decoder = new ItemQuoteDecoderText();
        ItemQuote quote = decoder.decode(clientSocket.getInputStream());
        System.out.println("Received Text-Encoded Quote: ");
        System.out.println(quote);

        //Repeat quote with binary encoding
        ItemQuoteEncoder encoder = new ItemQuoteEncoderBin();
        System.out.println("Sending (binary)...");
        clientSocket.getOutputStream().write(encoder.encode(quote));

        clientSocket.close();
        serverSocket.close();
    }
}
