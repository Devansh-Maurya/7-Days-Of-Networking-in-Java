package ItemQuoteTransferProtocol;

import java.io.IOException;
import java.io.InputStream;

public class ItemQuoteDecoderText implements ItemQuoteDecoder, ItemQuoteTextConst {

    private String encoding;   //Character encoding

    public ItemQuoteDecoderText() {
        encoding = DEFAULT_ENCODING;
    }

    public ItemQuoteDecoderText(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public ItemQuote decode(InputStream source) throws IOException {

        String itemNo, description, quantity, price, flags;
        byte[] space = " ".getBytes(encoding);
        byte[] newline = "\n".getBytes(encoding);

        itemNo = new String(Framer.nextToken(source, space), encoding);
        description = new String(Framer.nextToken(source, newline), encoding);
        quantity = new String(Framer.nextToken(source, space), encoding);
        price = new String(Framer.nextToken(source, space), encoding);
        flags = new String(Framer.nextToken(source, newline), encoding);

        return new ItemQuote(Long.parseLong(itemNo), description,
                Integer.parseInt(quantity),
                Integer.parseInt(price),
                (flags.indexOf('d') != -1),
                (flags.indexOf('s') != -1));
    }
}
