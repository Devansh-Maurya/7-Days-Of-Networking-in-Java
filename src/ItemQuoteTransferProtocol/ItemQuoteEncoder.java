package ItemQuoteTransferProtocol;

public interface ItemQuoteEncoder {

    byte[] encode(ItemQuote item) throws Exception;
}
