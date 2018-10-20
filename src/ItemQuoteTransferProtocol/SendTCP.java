package ItemQuoteTransferProtocol;

import java.net.InetAddress;
import java.net.Socket;

public class SendTCP {

    public static void main(String[] args) throws Exception{

        if (args.length != 2)
            throw new IllegalArgumentException("Parameter(s): <Destination> <Port>");

        InetAddress destAddr = InetAddress.getByName(args[0]);
        int destPort = Integer.parseInt(args[1]);

        Socket socket = new Socket(destAddr, destPort);

        ItemQuote quote = new ItemQuote(1234567890987654L, "Smm Super Widgets",
                1000, 12999, true, false);

        //Send text-encoded quote
        ItemQuoteEncoder coder = new ItemQuoteEncoderText();
        byte[] codedQuote = coder.encode(quote);
        System.out.println("Sending Text-Encoded Quote (" +
                codedQuote.length + " bytes");
        System.out.println(quote);
        socket.getOutputStream().write(codedQuote);

        //Receive binary-encoded quote
        ItemQuoteDecoder decoder = new ItemQuoteDecoderBin();
        ItemQuote receivedQuote = decoder.decode(socket.getInputStream());
        System.out.println("Received Binary-Encoded Quote: ");
        System.out.println(receivedQuote);

        socket.close();
    }
}
