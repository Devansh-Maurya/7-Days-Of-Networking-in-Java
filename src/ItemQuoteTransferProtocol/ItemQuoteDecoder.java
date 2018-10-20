package ItemQuoteTransferProtocol;

import java.io.IOException;
import java.io.InputStream;

public interface ItemQuoteDecoder {

    ItemQuote decode(InputStream source) throws IOException;
}
