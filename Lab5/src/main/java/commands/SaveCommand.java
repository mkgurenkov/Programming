package commands;

import commands.abstracts.CommandWithoutArg;
import helperClasses.CollectionManager;
import helperClasses.CollectionParser;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "save" command
 */
public class SaveCommand extends CommandWithoutArg {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public SaveCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: saves collection in the xml file*/
    @Override
    public void execute() {
        CollectionParser parser = new CollectionParser(collectionManager.getCollection(), collectionManager.getPathToFile());
        parser.marshalToXml();
        System.out.println("Collection has been saved");
    }
    /** Overridden toString() method to convert an instance of the SaveCommand class into a string representation*/
    @Override
    public String toString() {return "save";}
}
