package client;

/**
 * Handy utilities for customized console output
 */
public class ConsoleUtils
{
    //a platform independent new line separator
    static final String NEW_LINE = System.lineSeparator();

    //console line delimiter
    public static final String DELIMITER = "------------------------------------------------";

    //console line delimiter (is used while printing a map)
    public static final String LIST_DELIMITER = "\t\t------------------------\t\t";

    //client's initial menu
    public static final String INIT_MENU =
        "------------------------------------------------" + NEW_LINE +
                "----------List of available commands------------" + NEW_LINE +
                "------------------------------------------------" + NEW_LINE +
                "auth (enter your login and password for further actions)" + NEW_LINE +
                "exit (finish execution of the program)" + NEW_LINE +
                "------------------------------------------------" + NEW_LINE +
                "Enter your next command: ";

    //client's menu after successful authentication
    public static final String AUTH_MENU =
                DELIMITER + NEW_LINE +
                "----------List of available commands------------" + NEW_LINE +
                DELIMITER + NEW_LINE +
                "auth (reenter your login and password to change account)" + NEW_LINE +
                "data (get information about your cars and insurance offers related to them)" + NEW_LINE +
                "exit (finish execution of the program)" + NEW_LINE +
                DELIMITER + NEW_LINE +
                "Enter your next command: ";

    //reminder to continue the work with a program
    public static final String enterInput = NEW_LINE +
            "Press Enter to continue work";

}
