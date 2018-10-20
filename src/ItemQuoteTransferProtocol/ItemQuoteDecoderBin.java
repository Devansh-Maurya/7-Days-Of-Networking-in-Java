package ItemQuoteTransferProtocol;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class ItemQuoteDecoderBin implements ItemQuoteDecoder, ItemQuoteBinConst{

    private String encoing;     //Character encoding

    public ItemQuoteDecoderBin() {
        encoing = DEFAULT_ENCODING;
    }

    public ItemQuoteDecoderBin(String encoing) {
        this.encoing = encoing;
    }

    @Override
    public ItemQuote decode(InputStream source) throws IOException {
        boolean discounted, inStock;

        //Using the given InputStream, construct a DataInputStream so we can make use of the
        //methods readLong() and readInt() for reading binary data types from the input.
        DataInputStream src = new DataInputStream(source);

        long itemNumber = src.readLong();
        int quantity = src.readInt();
        int unitPrice = src.readInt();
        byte flags = src.readByte();
        int stringLength = src.read(); //Returns an unsigned byte as an int
        if (stringLength == -1)
            throw new EOFException();
        byte[] stringBuf = new byte[stringLength];
        src.readFully(stringBuf);
        String itemDescription = new String(stringBuf, encoing);

        return new ItemQuote(itemNumber, itemDescription, quantity, unitPrice,
                ((flags & DISCOUNT_FLAG) == DISCOUNT_FLAG),
                ((flags & IN_STOCK_FLAG) == IN_STOCK_FLAG));
    }
}
