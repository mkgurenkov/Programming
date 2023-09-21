package application;

import helperClasses.CollectionManager;
import java.util.Scanner;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Main class for starting a program
 */
public class Main {
    /**
     * Main method for starting a program
     * @param args - argument for program - path to file with xml collection
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            String pathToFIle = args[0];
            System.out.println("Press ctrl+D or type \"exit\" to exit program");
            CollectionManager collectionManager = new CollectionManager(pathToFIle);
            collectionManager.interactiveMode();
        } else {
            System.out.println("Missing argument");
            System.out.println("Press \"Enter\"");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        }
    }
}