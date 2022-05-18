package server;

import database.DatabaseUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * A simple TCP/IP socket server. This class includes main(), listens for
 * incoming connections and starts ServerThreads to handle those connections.
 * This server is multi-threaded.
 */
public class Server
{
    //port number
    static final int PORT = 4040;

    public static void main(String[] args) throws IOException
    {
        //initialize database properties
        DatabaseUtils.loadDatabaseProperties();
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("Server started!");
        System.out.println("Waiting for clients...");

        while (true)
        {
            //accept a new connection
            Socket clientSocket = serverSocket.accept();
            //start a new ServerThread to handle multiple client
            Thread t = new ServerThread(clientSocket);
            t.start();
        }
    }
}
