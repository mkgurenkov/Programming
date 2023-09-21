package helperClasses;

import data.City;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that manages the collection
 */
public class CollectionManager {
    /** Field: instance of class CollectionParser
     * @see CollectionParser
     */
    private CollectionParser parser;
    /** Field: instance of class CommandExecutor
     * @see CommandExecutor
     */
    private final CommandExecutor commandExecutor;
    /** Field: collection*/
    private Hashtable<Integer, City> collection;
    /** Field: collection initialization date*/
    private final LocalDateTime initializationDate;
    /** Field: path to the xml file with collection*/
    private final String pathToFile;
    /** Field: flag for turning on/off the interactive mode*/
    private boolean interactiveModeIsOn = false;
    /**
     * Constructor
     * @param pathToFile - path to the xml file with collection
     */
    public CollectionManager(String pathToFile) {
        this.pathToFile = pathToFile;
        this.collection = loadCollection();
        this.initializationDate = LocalDateTime.now();
        this.commandExecutor = new CommandExecutor(this);
    }

    /** Method for loading collection from xml file
     * @return Hashtable<Integer, City> collection
     */
    private Hashtable<Integer, City> loadCollection() {
        parser = new CollectionParser(collection, pathToFile);
        Hashtable<Integer, City> loadedCollection = new Hashtable<>();
        loadedCollection = parser.unmarshalFromXml();
        if (!parser.getAnyExceptionsWithFile()) {
            printHowManyElementsWasNotUploaded();
            printProblemsWithNotUploadedElements();
        }
        return loadedCollection;
    }
    /** Method which turns on the interactive mode*/
    public void interactiveMode() {
        interactiveModeIsOn = true;
        Scanner scanner = new Scanner(System.in);
        File file = new File(pathToFile);
        if (parser.getAnyExceptionsWithFile() && !getFileExtension(file).equals("xml")) {
            System.out.println("Press \"Enter\"");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        } else {
            if (parser.getAnyExceptionsWithFile() && getFileExtension(file).equals("xml")) {
                suggestToLoadEmptyCollection();
            } else {
                while (interactiveModeIsOn && scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    EnterElement.fromConsole = true;
                    commandExecutor.execute(input);
                }
            }
        }
    }

    /** Method which gets file extension
     *
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
    private void printProblemsWithNotUploadedElements() {
        String oneOrSeveral;
        if (parser.getCreationDateDoesNotMatchHashCode() > 0) {
            if (parser.getCreationDateDoesNotMatchHashCode() == 1) {
                oneOrSeveral = "element";
            } else {oneOrSeveral = "elements";}
            System.out.println("    " + parser.getCreationDateDoesNotMatchHashCode() + " " + oneOrSeveral + ": Creation date does not match its hash field");
        }
        if (parser.getBirthdayDoesNotMatchAge() > 0) {
            if (parser.getBirthdayDoesNotMatchAge() == 1) {
                oneOrSeveral = "element";
            } else {oneOrSeveral = "elements";}
            System.out.println("    " + parser.getBirthdayDoesNotMatchAge() + " " + oneOrSeveral + ": Birthday does not match age");
        }
        if (parser.getInvalidFieldDataInFile() > 0) {
            if (parser.getInvalidFieldDataInFile() == 1) {
                oneOrSeveral = "element";
            } else {oneOrSeveral = "elements";}
            System.out.println("    " + parser.getInvalidFieldDataInFile() + " " + oneOrSeveral + ": Invalid field data in file");
        }
        if (parser.getOneKeyForSeveralElements() > 0) {
            if (parser.getOneKeyForSeveralElements() == 1) {
                oneOrSeveral = "element";
            } else {oneOrSeveral = "elements";}
            System.out.println("    " + parser.getOneKeyForSeveralElements() + " " + oneOrSeveral + ": One key matches several elements");
        }
    }
    /** Method which suggests to load empty collection if there were problems with opening xml file or if file was not found (creates file)*/
    private void suggestToLoadEmptyCollection() {
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
                while (interactiveModeIsOn && scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    commandExecutor.execute(input);
                }
                break;
            } else if (response.equals("n") | response.equals("exit")) {
                break;
            } else {
                System.out.println("Invalid input, try again");
            }
        }
    }
    /** Method which turns off the interactive mode*/
    public void interactiveModeOff(){
        interactiveModeIsOn = false;
    }

    /** Method which gets collection
     * @return Hashtable<Integer, City> collection
     */
    public Hashtable<Integer, City> getCollection() {
        return collection;
    }
    /** Method which sets new collection*/
    public void setCollection(Hashtable<Integer, City> collection) {
        this.collection = collection;
    }
    /** Method which gets the date of collection initialization
     * @return LocalDateTime initializationDate
     */
    public LocalDateTime getInitializationDate() {
        return initializationDate;
    }
    /** Method which gets path to the file with collection
     * @return String pathToFile
     */
    public String getPathToFile() {return pathToFile;}
    /** Method which gets command executor
     * @return CommandExecutor commandExecutor
     * @see CommandExecutor
     */
    public CommandExecutor getCommandExecutor() { return commandExecutor; }
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
}
