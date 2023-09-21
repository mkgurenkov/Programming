package commands;

import helperClasses.CollectionManager;
import data.City;
import data.Government;
import helperClasses.InteractionWithClient;
import java.util.Hashtable;
import java.util.Map;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "remove_any_by_government" command
 */
public class Remove_any_by_governmentCommand extends Command {
    /** Constructor*/
    public Remove_any_by_governmentCommand() {this.setType("remove_any_by_government");}
    /**
     * Execute method: removes element from collection by government
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public void execute(CollectionManager collectionManager) {
        try {
            Government.valueOf(super.getArgument());
            Hashtable<Integer, City> collection = collectionManager.getCollection();
            boolean isRemoved = false;
            for(Map.Entry<Integer, City> entry: collectionManager.sort(collection).entrySet()) {
                if (entry.getValue().getGovernment() == Government.valueOf(super.getArgument())) {
                    collection.remove(entry.getKey());
                    InteractionWithClient.sendMessage("Element with key " + entry.getKey() + " has been removed");
                    isRemoved = true;
                    break;
                }
            }
            if (!isRemoved) {
                InteractionWithClient.sendMessage("There are no elements with such government");
            }
        } catch (IllegalArgumentException e) {
            InteractionWithClient.sendMessage("There is no such type of government");
            InteractionWithClient.sendMessage("remove_any_by_government government (\"KRITARCHY\", \"PUPPET_STATE\", \"JUNTA\") - удалить из коллекции один элемент, значение поля government которого эквивалентно заданному");
        }
    }
}