package helperClasses;

import commands.Command;
import commands.SaveCommand;
import data.City;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that manages the collection
 */
public class CollectionManager {
    /**
     * Field: commandExecutor
     * @see helperClasses.CommandExecutor
     */
    private final CommandExecutor commandExecutor = new CommandExecutor(this);
    /**
     * Field: collectonParser
     * @see helperClasses.CollectionParser
     */
    private CollectionParser parser;
    /** Field: collection*/
    private Hashtable<Integer, City> collection = new Hashtable<>();
    /** Field: collection initialization date*/
    private final LocalDateTime initializationDate;
    /** Field: path to the xml file with collection*/
    private final String pathToFile;
    /**
     * Constructor
     * @param pathToFile - path to the xml file with collection
     */
    public CollectionManager(String pathToFile) {
        this.pathToFile = pathToFile;
        loadCollection();
        this.initializationDate = LocalDateTime.now();
    }

    /** Method for loading collection from xml file*/
    private void loadCollection() {
        parser = new CollectionParser(collection, pathToFile);
        parser.unmarshalFromXml();
        if (!parser.getExceptionsFound()) {
            printHowManyElementsWasNotUploaded();
            printWhyElementsWasNotUploaded();
        }
    }
    /** Method which turns on the interactive mode*/
    public void interactiveMode() {
        Scanner scanner = new Scanner(System.in);
        File file = new File(pathToFile);
        if (parser.getExceptionsFound() && !getFileExtension(file).equals("xml")) {
            System.out.println("Press \"Enter\"");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        } else if (parser.getExceptionsFound() && getFileExtension(file).equals("xml")) {
            boolean emptyCollectionWasLoaded = suggestToLoadEmptyCollection();
            if (emptyCollectionWasLoaded) {
                startInteractionWithClient();
            }
        } else {
            startInteractionWithClient();
        }
    }

    /**
     * Method which gets file extension
     * @param file - file
     * @return String extension
     */
    private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    /** Method which prints quantity of not uploaded elements*/
    private void printHowManyElementsWasNotUploaded() {
        if (parser.getNotLoadedElements() == 0) {
            System.out.println("Collection was successfully uploaded");
        } else if (parser.getNotLoadedElements() == 1) {
            System.out.println("1 element was not uploaded:");
        } else {
            System.out.println(parser.getNotLoadedElements() + " elements were not uploaded:");
        }
    }
    /** Method which describes problems with not loaded elements*/
    private void printWhyElementsWasNotUploaded() {
        String oneOrSeveral;
        if (parser.getCreationDateDoesNotMatchHashCode() > 0) {
            if (parser.getCreationDateDoesNotMatchHashCode() == 1) {
                oneOrSeveral = "element";
            } else {oneOrSeveral = "elements";}
            InteractionWithClient.sendMessage("    " + parser.getCreationDateDoesNotMatchHashCode() + " " + oneOrSeveral + ": Creation date does not match its hash field");
        }
        if (parser.getBirthdayDoesNotMatchAge() > 0) {
            if (parser.getBirthdayDoesNotMatchAge() == 1) {
                oneOrSeveral = "element";
            } else {oneOrSeveral = "elements";}
            InteractionWithClient.sendMessage("    " + parser.getBirthdayDoesNotMatchAge() + " " + oneOrSeveral + ": Birthday does not match age");
        }
        if (parser.getInvalidFieldDataInFile() > 0) {
            if (parser.getInvalidFieldDataInFile() == 1) {
                oneOrSeveral = "element";
            } else {oneOrSeveral = "elements";}
            InteractionWithClient.sendMessage("    " + parser.getInvalidFieldDataInFile() + " " + oneOrSeveral + ": Invalid field data in file");
        }
        if (parser.getOneKeyForSeveralElements() > 0) {
            if (parser.getOneKeyForSeveralElements() == 1) {
                oneOrSeveral = "element";
            } else {oneOrSeveral = "elements";}
            InteractionWithClient.sendMessage("    " + parser.getOneKeyForSeveralElements() + " " + oneOrSeveral + ": One key matches several elements");
        }
    }
    /** Method which suggests to load empty collection if there were problems with opening xml file or if file was not found (creates file)*/
    private boolean suggestToLoadEmptyCollection() {
        Scanner scanner = new Scanner(System.in);
        if (parser.getFileNotFound()) {
            System.out.println("Create new empty collection [y/n]");
        } else {
            System.out.println("Upload empty collection [y/n]");
        }
        while (scanner.hasNextLine()) {
            String response = scanner.nextLine();
            response = DeleteSpaces.delete(response);
            if (response.equals("y")) {
                System.out.println("Empty collection was created");
                Hashtable<Integer, City> emptyCollection = new Hashtable<>();
                setCollection(emptyCollection);
                return true;
            } else if (response.equals("n") | response.equals("exit")) {
                return false;
            } else {
                System.out.println("Invalid input, try again");
            }
        }
        return false;
    }

    /** Method which gets collection
     * @return Hashtable<Integer, City> collection
     */
    public Hashtable<Integer, City> getCollection() {return collection;}
    /** Method which sets new collection*/
    public void setCollection(Hashtable<Integer, City> collection) {this.collection = collection;}
    /** Method which gets the date of collection initialization
     * @return LocalDateTime initializationDate
     */
    public LocalDateTime getInitializationDate() {return initializationDate;}
    /** Method which gets path to the file with collection
     * @return String pathToFile
     */
    public String getPathToFile() {return pathToFile;}
    /** Method which sorts collection by key
     * @return Map<Integer, City> sortedCollection
     */
    public Map<Integer, City> sort(Hashtable<Integer, City> collection) {
        Map<Integer, City> sortedCollection = new TreeMap<Integer, City>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        sortedCollection.putAll(collection);
        return sortedCollection;
    }
    /** Method which starts interaction with client (using in the "interactiveMode")*/
     private void startInteractionWithClient() {
         while (true) {
             String serializedCommand = InteractionWithClient.receiveCommand();
             try {
                 Command command = CommandUnmarshaller.fromXml(serializedCommand);
                 Hashtable<Integer, City> collection2 = new Hashtable<>(collection);
                 commandExecutor.execute(command);
                 if (!collection2.equals(collection)) {
                     SaveCommand saveCommand = new SaveCommand();
                     saveCommand.execute(this);
                 }
                 InteractionWithClient.sendMessage("endOfResponse");
                 InteractionWithClient.closeChannel();
             } catch (JAXBException e) {
                 throw new RuntimeException(e);
             }
         }
     }
     /**
      * Getter for the commandExecutor
      * @return commandExecutor
      * @see helperClasses.CommandExecutor
      */
     public CommandExecutor getCommandExecutor() {
        return commandExecutor;
     }
}
