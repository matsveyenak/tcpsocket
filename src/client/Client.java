package client;

/**
 *  Main Client class. This class includes main(), where
 *  ClientThread is running to establish connection to the server.
 */
public class Client
{
    //host name
    private static final String HOST = "127.0.0.1";
    //same port number as server
    private static final int PORT = 4040;

    public static void main(String[] args)
    {
        ClientThread thread = new ClientThread(HOST, PORT);
        thread.run();
    }
}
