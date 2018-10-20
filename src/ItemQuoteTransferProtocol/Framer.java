package ItemQuoteTransferProtocol;

//Converts daat from input stream into bytes with delimiters

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Framer {

    public static byte[] nextToken(InputStream in, byte[] delimiter)
        throws IOException {
        int nextByte;

        // If the stream has already ended, return null
        if ((nextByte = in.read()) == -1)
            return null;

        //ByteArray classes allows a byte array to be handled as stream of bytes
        ByteArrayOutputStream tokenBuffer = new ByteArrayOutputStream();
        do {
            tokenBuffer.write(nextByte);
            byte[] currentToken = tokenBuffer.toByteArray();
            if (endsWith(currentToken, delimiter)) {
                int tokenLength = currentToken.length - delimiter.length;
                byte[] token = new byte[tokenLength];
                System.arraycopy(currentToken, 0, token, 0, tokenLength);
                return token;
            }
        } while ((nextByte = in.read()) != -1); // Stop on end-of-stream
        return tokenBuffer.toByteArray();       // Received at least 1 byte
    }

    // Returns true if value ends with the bytes in suffix
    private static boolean endsWith(byte[] value, byte[] suffix) {
        if (value.length < suffix.length)
            return false;

        for (int offset = 1; offset <= suffix.length; offset++)
            if (value[value.length - offset] != suffix[suffix.length - offset])
                return false;
            return true;
    }
}