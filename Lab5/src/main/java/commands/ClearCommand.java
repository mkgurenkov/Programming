package commands;

import commands.abstracts.CommandWithoutArg;
import helperClasses.CollectionManager;
import data.City;
import java.util.Hashtable;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "clear" command
 */
public class ClearCommand extends CommandWithoutArg {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public ClearCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: clears the collection*/
    @Override
    public void execute() {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        collection.clear();
        System.out.println("Collection has been cleared");
    }
    /** Overridden toString() method to convert an instance of the ClearCommand class into a string representation*/
    @Override
    public String toString() {return "clear";}
}
