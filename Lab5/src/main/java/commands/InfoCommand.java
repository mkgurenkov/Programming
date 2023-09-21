package commands;

import commands.abstracts.CommandWithoutArg;
import helperClasses.CollectionManager;
import data.City;
import java.util.Hashtable;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "info" command
 */
public class InfoCommand extends CommandWithoutArg {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public InfoCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: displays information about collection*/
    @Override
    public void execute() {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type of collection: ").append(collection.getClass()).append("\n");
        stringBuilder.append("Initialization time: ").append(collectionManager.getInitializationDate()).append("\n");
        stringBuilder.append("Amount of items: ").append(collection.size());
        System.out.println(stringBuilder);
    }
    /** Overridden toString() method to convert an instance of the InfoCommand class into a string representation*/
    @Override
    public String toString() {return "info";}
}
