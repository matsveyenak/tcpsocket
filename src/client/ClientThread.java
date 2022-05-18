package client;

import model.InsuranceOffer;
import model.Insurer;
import model.Vehicle;

import java.io.*;
import java.net.Socket;
import java.util.*;

/*
 * ClientThread helps to determine which client's command to send to the server,
 * handles a response from the server and form a customized console output
 */
public class ClientThread extends Thread
{
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Scanner scan;

    //current user id
    private long userId = 0l;
    //current console menu (can be INIT_MENU or AUTH_MENU)
    private String menu = ConsoleUtils.INIT_MENU;

    public ClientThread(String host, int port)
    {
        System.out.println("Dear client, welcome to AutoInsurance center!");
        try
        {
            //open a new socket connection
            socket = new Socket(host, port);
            //open a new ObjectInputStream and ObjectOutputStream on the socket
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            //initialize scanner for client input
            scan = new Scanner(System.in);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // Thread execution method
    public void run()
    {
        while (true)
        {
            //if user has been authenticated
            if (userId != 0)
                menu = ConsoleUtils.AUTH_MENU; //change console menu

            System.out.print(menu);


            /**
             * Scan a command that client types.
             * There are 3 valid commands that can be sent by the client:
             *  a) auth
             *  b) data
             *  c) exit
             */
            String choice = scan.nextLine();
            switch (choice)
            {
                case "auth":
                    try
                    {
                        commandAuth();
                    }
                    catch (IOException | ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case "data":
                    try
                    {
                        commandData();
                    }
                    catch (IOException | ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case "exit":
                    try
                    {
                        commandExit();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Invalid operation! Try again.");
                    break;
            };

            System.out.println(ConsoleUtils.enterInput);
            scan.nextLine();
        }
    }

    /**
     * Perform "auth" command.
     * Send a login and password to a server
     * and get a user id that indicates a successful authentication
     */
    private void commandAuth() throws IOException, ClassNotFoundException
    {
        oos.writeObject("auth");

        System.out.print("Enter user login: ");
        String login = scan.nextLine();

        System.out.print("Enter user password: ");
        String password = scan.nextLine();

        oos.writeObject(login);
        oos.writeObject(password);

        userId = (long)ois.readObject();
        System.out.println("Retrieved id from server: " + userId);
    }

    /**
     * Perform "data" command.
     * Output a map of insurance offers for all cars
     * that belong to a client
     */
    private void commandData() throws IOException, ClassNotFoundException
    {
        //check if user has passed authentication process
        if (userId != 0)
        {
            oos.writeObject("data");
            oos.writeObject(userId);

            Map<Vehicle, List<InsuranceOffer>> map = (HashMap) ois.readObject();

            for(Map.Entry<Vehicle, List<InsuranceOffer>> entry : map.entrySet())
            {
                System.out.println(ConsoleUtils.NEW_LINE + entry.getKey() + ConsoleUtils.NEW_LINE
                        + "All insurance offers for this vehicle" + ConsoleUtils.NEW_LINE
                        + ConsoleUtils.DELIMITER);

                List<InsuranceOffer> offers = entry.getValue();
                for (int i = 0; i < offers.size(); i++)
                {
                    InsuranceOffer offer = offers.get(i);
                    Insurer insurer = offer.getInsurer();

                    System.out.format("%d) offer by %s | insurance period: from %s to %s | $%s%n", i, insurer.getName(),
                            offer.getStartDate(), offer.getEndDate(), offer.getPrice());

                    System.out.format("Contact insurer: %s | %s%n", insurer.getPhoneNumber(), insurer.getEmail());

                    if (i != offers.size() - 1)
                        System.out.println(ConsoleUtils.LIST_DELIMITER);
                }
                System.out.println(ConsoleUtils.DELIMITER);
            }
        }
        else
        {
            System.out.println("Please properly authenticate to do this operation.");
        }

    }

    /**
     * Perform "exit" command.
     * Close the socket connection
     */
    private void commandExit() throws IOException
    {
        System.out.println("It was nice to meet you :)");
        oos.writeObject("command");

        oos.close();
        ois.close();
        socket.close();

        System.exit(0);
    }

}