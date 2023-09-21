package commands;

import helperClasses.CollectionManager;
import data.City;
import helperClasses.InteractionWithClient;
import java.util.Hashtable;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "clear" command
 */
public class ClearCommand extends Command {
    /** Constructor*/
    public ClearCommand() {this.setType("clear");}
    /**
     * Execute method: clears the collection
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public void execute(CollectionManager collectionManager) {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        collection.clear();
        InteractionWithClient.sendMessage("Collection has been cleared");
    }
}
