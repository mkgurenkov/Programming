package application;

/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Main class for starting a program
 */
public class Main {
    /**
     * Main method for starting a program
     * @param args - argument of the command line
     */
    public static void main(String[] args) {
        ClientManager clientManager = new ClientManager();
        clientManager.interactiveMode();
    }
}