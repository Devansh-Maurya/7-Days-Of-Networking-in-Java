package multitasking;

//Sends log messages to the console

import java.util.Collection;
import java.util.Iterator;

public class ConsloeLogger implements Logger{

    @Override
    public synchronized void writeEntry(Collection entry) {
        for (Iterator line = entry.iterator(); line.hasNext();)
            System.out.println(line.next());
        System.out.println();
    }

    @Override
    public synchronized void writeEntry(String entry) {
        System.out.println(entry);
        System.out.println();
    }
}
