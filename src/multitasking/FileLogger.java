package multitasking;

//Writes the log messages to a file specified in the constructor

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

public class FileLogger implements Logger{

    PrintWriter out;    //Log file

    public FileLogger(String fileName) throws IOException {
        out = new PrintWriter(new FileWriter(fileName), true);  //Create log file
    }

    @Override
    public synchronized void writeEntry(Collection entry) {
        for (Iterator line = entry.iterator(); line.hasNext();)
            out.println(line.hasNext());
        out.println();
    }

    @Override
    public void writeEntry(String entry) {
        out.println(entry);
        out.println();
    }
}
