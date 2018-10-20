package ItemQuoteTransferProtocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ItemQuoteEncoderText implements ItemQuoteEncoder, ItemQuoteTextConst {

    private String encoding;    //Character encoding

    public ItemQuoteEncoderText() {
        encoding = DEFAULT_ENCODING;
    }

    public ItemQuoteEncoderText(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public byte[] encode(ItemQuote item) throws Exception {

        //Encoding of the form: 12345 AAA Battery\n23 1445 ds\n

        //A ByteArray0utputStream collects the bytes to be returned. Wrapping it in an Out-
        //putWriter allows us to take advantage of the latter's methods for converting strings
        //to bytes.

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        OutputStreamWriter out = new OutputStreamWriter(buffer, encoding);
        out.write(item.getItemNumber() + " ");

        if (item.getItemDescription().indexOf('\n') != -1)
            throw new IOException("Invalid description (contains newline)");
        out.write(item.getItemDescription() + "\n" + item.getQuantity() + " " +
                item.getUnitPrice() + " ");

        if (item.isDiscounted())
            out.write('d');     //Only include 'd' if discounted
        if (item.isInStock())
            out.write('s');
        out.write('\n');        //Only include 's' if in stock

        out.flush();

        if (buffer.size() > MAX_WIRE_LENGTH)
            throw new IOException("Encoded length too long");
        return buffer.toByteArray();

    }
}
