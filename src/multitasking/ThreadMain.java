package multitasking;

import java.net.ServerSocket;

public class ThreadMain {

    public static void main(String[] args) throws Exception{

        if (args.length != 3)   //Test for correct number of args
            throw new IllegalArgumentException("Parameter(s): <Port> " +
                    "<Protocol> <Dispatcher");

        int serverPort = Integer.parseInt(args[0]); //Server Port
        String protocolName = args[1];              //Protocol name
        String dispatcherName = args[2];            //Dispatcher name

        ServerSocket serverSocket = new ServerSocket(serverPort);
        Logger logger = new ConsloeLogger();                //Log messages to console

        //The newInstance() method of Class creates
        //a new instance of the class using the parameterless constructor

        //Get protocol factory
        ProtocolFactory protocolFactory = (ProtocolFactory) Class.forName("multitasking." +
                protocolName + "ProtocolFactory").newInstance();

        //Get dispatcher
        Dispatcher dispatcher = (Dispatcher)
                Class.forName("multitasking." + dispatcherName + "Dispatcher").newInstance();

        dispatcher.startDispatching(serverSocket, logger, protocolFactory);
        //NOT REACHED
    }
}
