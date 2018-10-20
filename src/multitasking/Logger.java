package multitasking;

//The logger allows for synchronized reporting of thread creation and client completion,
//so that entries from different threads are not interleaved.

import java.util.Collection;

public interface Logger {

    public void writeEntry(Collection entry);   //Write list of lines
    public void writeEntry(String entry);       //Write single line
}
