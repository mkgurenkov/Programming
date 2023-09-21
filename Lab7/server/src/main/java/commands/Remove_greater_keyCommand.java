package commands;

import helperClasses.CollectionManager;
import data.City;
import helperClasses.InteractionWithClient;
import java.util.Hashtable;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "remove_greater_key" command
 */
public class Remove_greater_keyCommand extends Command {
    /** Constructor*/
    public Remove_greater_keyCommand() {this.setType("remove_greater_key");}
    /**
     * Execute method: removes elements from collection, which key is greater than entered key
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public void execute(CollectionManager collectionManager) {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        Hashtable<Integer, City> collection2 = new Hashtable<>(collection);
        int numberOfElementsBefore = collection2.size();
        collection.entrySet().stream()
                .filter(x -> x.getKey() > Integer.parseInt(super.getArgument()))
                .forEach(x -> collection2.remove(x.getKey()));
        collectionManager.setCollection(collection2);
        if (numberOfElementsBefore - collection2.size() == 1) {
            InteractionWithClient.sendMessage(numberOfElementsBefore - collection2.size() + " element has been removed");
        } else {
            InteractionWithClient.sendMessage(numberOfElementsBefore - collection2.size() + " elements has been removed");
        }
    }
}

