package ItemQuoteTransferProtocol;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ItemQuoteEncoderBin implements ItemQuoteEncoder, ItemQuoteBinConst{

    private String encoding;    //Character encoding

    public ItemQuoteEncoderBin() {
        encoding = DEFAULT_ENCODING;
    }

    public ItemQuoteEncoderBin(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public byte[] encode(ItemQuote item) throws Exception {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buffer);

        out.writeLong(item.getItemNumber());
        out.writeInt(item.getQuantity());
        out.writeInt(item.getUnitPrice());

        byte flags = 0;
        if (item.isDiscounted())
            flags |= DISCOUNT_FLAG;
        if (item.isInStock())
            flags |= IN_STOCK_FLAG;
        out.writeByte(flags);

        byte[] encoderDescriotion = item.getItemDescription().getBytes(encoding);
        if (encoderDescriotion.length > MAX_DESC_LEN)
            throw new IOException("Item description exceeds encoded length limit");
        out.writeByte(encoderDescriotion.length);
        out.write(encoderDescriotion);

        out.flush();

        return buffer.toByteArray();
    }
}
