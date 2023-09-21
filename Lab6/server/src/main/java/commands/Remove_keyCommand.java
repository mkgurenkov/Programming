package commands;

import helperClasses.CollectionManager;
import data.City;
import helperClasses.InteractionWithClient;
import java.util.Hashtable;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "remove_key" command
 */
public class Remove_keyCommand extends Command {
    /** Constructor*/
    public Remove_keyCommand() {this.setType("remove_key");}
    /**
     * Execute method: removes element from collection by its key
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public void execute(CollectionManager collectionManager) {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        boolean keyWasFound = collection.entrySet().stream().anyMatch(x -> x.getKey() == Integer.parseInt(super.getArgument()));
        collection.entrySet().stream()
                .filter(x -> x.getKey() == Integer.parseInt(super.getArgument()))
                .forEach(x -> collection.remove(x.getKey()));
        if (keyWasFound) {
            InteractionWithClient.sendMessage("Element has been removed");
        } else {
            InteractionWithClient.sendMessage("There is no element with such key");
        }
    }
}
