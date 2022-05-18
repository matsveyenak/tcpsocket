package server;

import database.DatabaseUtils;
import model.InsuranceOffer;
import model.Insurer;
import model.Vehicle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Every ServerThread waits until a client sends a command, then
 * runs a certain command and sends the output of that command to the client
 */

public class ServerThread extends Thread
{
    private Socket clientSocket;	// Client socket object
    private Connection conn;		// Database connection object
    private ObjectInputStream ois;	// Input stream
    private ObjectOutputStream oos;	// Output stream

    public ServerThread(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
        System.out.println("New client connected: " + clientSocket.getRemoteSocketAddress());
    }

    // Thread execution method
    public void run()
    {
        try
        {
            //open a new ObjectInputStream and ObjectOutputStream on the socket
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos = new ObjectOutputStream(clientSocket.getOutputStream());

            //infinite loop while client sending commands
            while (readCommand()) {}

        }
        catch (IOException | SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get a command that client sends.
     * There are 3 valid commands that can be sent by the client:
     *  a) auth
     *  b) data
     *  c) exit
     */
    public boolean readCommand() throws SQLException, ClassNotFoundException, IOException
    {
        String command;
        try
        {
            //read a client command
            command = (String)ois.readObject();
        }
        catch (Exception e)
        {
            command = null;
        }

        if (command == null)
        {
            //finish execution
            commandExit();
            return false;
        }
        else
        {
            switch (command)
            {
                case "auth":
                {
                    commandAuth();
                    break;
                }
                case "data":
                {
                    commandData();
                    break;
                }
                case "exit":
                {
                    commandExit();
                    break;
                }
                default:
                {
                    break;
                }

            }
        }

        return true;
    }

    /**
     * Handling "auth" command.
     * Verify login and password sent by a client and
     * return a user_id to the client
     */
    public void commandAuth() throws IOException, ClassNotFoundException, SQLException
    {
        System.out.println("Command auth from client " + clientSocket.getRemoteSocketAddress());
        String login =  (String)ois.readObject();
        String password =  (String)ois.readObject();

        if (conn == null || conn.isClosed())
            conn = DatabaseUtils.createConnection();

        long userId = userAuthentication(conn, login, password);
        oos.writeObject(userId);
    }

    /**
     * Handling "data" command.
     * Send a map of insurance offers for all cars
     * that belong to a client
     */
    public void commandData() throws IOException, ClassNotFoundException, SQLException
    {
        System.out.println("Command data from client " + clientSocket.getRemoteSocketAddress());
        long userId = (long) ois.readObject();

        if (conn == null || conn.isClosed())
            conn = DatabaseUtils.createConnection();

        oos.writeObject(getVehicleInsuranceOffers(conn, userId));

    }

    /**
     * Handling "exit" command
     * Close the database connection and client socket
     */
    public void commandExit()
    {
        System.out.println("Command exit from client " + clientSocket.getRemoteSocketAddress());
        DatabaseUtils.close(conn);
        closeSocket();
    }

    /**
     * Retrieve a user id by checking login and password
     * in a "users" table
     */
    public long userAuthentication(Connection conn, String login, String password) throws SQLException
    {
        long userId = 0L;
        String query = "SELECT id FROM users WHERE login = ? AND password = ?";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, login);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        if (rs.next())
            userId = rs.getLong(1);

        DatabaseUtils.close(stmt);
        DatabaseUtils.close(rs);

        return userId;
    }

    /**
     * Form and return a map that contains all insurance offers related
     * to every car a user possesses
     */
    public Map<Vehicle, List<InsuranceOffer>> getVehicleInsuranceOffers
            (Connection conn, long userId) throws SQLException
    {
        Map<Vehicle, List<InsuranceOffer>> map = new HashMap<>();

        String query = "SELECT vehicles.id, vehicles.brand, vehicles.model, " +
                "insurers.name, insurers.phone_number, insurers.email, " +
                "insurance_offers.price, insurance_offers.start_date, insurance_offers.end_date " +
                "FROM insurance_offers " +
                "JOIN vehicles " +
                "ON insurance_offers.vehicle_id = vehicles.id " +
                "JOIN insurers " +
                "ON insurance_offers.insurer_id = insurers.id " +
                "WHERE vehicles.user_id = ?;";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setLong(1, userId);

        ResultSet rs = stmt.executeQuery();
        while (rs.next())
        {
            //create and fill new Vehicle object
            Vehicle vehicle = new Vehicle();
            vehicle.setId(rs.getLong(1));
            vehicle.setBrand(rs.getString(2));
            vehicle.setModel(rs.getString(3));

            //create and fill new Insurer object
            Insurer insurer = new Insurer();
            insurer.setName(rs.getString(4));
            insurer.setPhoneNumber(rs.getString(5));
            insurer.setEmail(rs.getString(6));

            //create and fill new InsuranceOffer object
            InsuranceOffer insuranceOffer = new InsuranceOffer();
            insuranceOffer.setPrice(rs.getDouble(7));
            insuranceOffer.setStartDate(rs.getDate(8));
            insuranceOffer.setEndDate(rs.getDate(9));
            insuranceOffer.setInsurer(insurer);

            //update a HashMap with new value
            map.computeIfAbsent(vehicle, value -> new ArrayList<>()).add(insuranceOffer);
        }

        DatabaseUtils.close(stmt);
        DatabaseUtils.close(rs);

        return map;
    }

    // Close the client socket
    public void closeSocket()
    {
        try
        {
            oos.close();
            ois.close();
            clientSocket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
