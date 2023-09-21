package commands;

import helperClasses.CollectionManager;
import data.City;
import helperClasses.InteractionWithClient;
import java.util.Hashtable;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "info" command
 */
public class InfoCommand extends Command {
    /** Constructor*/
    public InfoCommand() {this.setType("info");}
    /**
     * Execute method: displays information about collection
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public void execute(CollectionManager collectionManager) {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type of collection: ").append(collection.getClass()).append("\n");
        stringBuilder.append("Initialization time: ").append(collectionManager.getInitializationDate()).append("\n");
        stringBuilder.append("Amount of items: ").append(collection.size());
        InteractionWithClient.sendMessage(String.valueOf(stringBuilder));
    }
}
