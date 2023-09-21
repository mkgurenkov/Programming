package commands;

import commands.abstracts.CommandWithArg;
import helperClasses.CollectionManager;
import data.City;
import java.util.Hashtable;
import java.util.Map;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "remove_greater_key" command
 */
public class Remove_greater_keyCommand extends CommandWithArg<Integer> {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public Remove_greater_keyCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: removes elements from collection, which key is greater than entered key
     * @param key - number with which would be compared element keys
     */
    @Override
    public void execute(Integer key) {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        Hashtable<Integer, City> collection2 = new Hashtable<>(collection);
        int numberOfElementsBefore = collection2.size();
        for(Map.Entry<Integer, City> entry: collection.entrySet()) {
            if (entry.getKey() > key) {
                collection2.remove(entry.getKey());
            }
        }
        collectionManager.setCollection(collection2);
        if (numberOfElementsBefore - collection2.size() == 1) {
            System.out.println(numberOfElementsBefore - collection2.size() + " element has been removed");
        } else {
            System.out.println(numberOfElementsBefore - collection2.size() + " elements has been removed");
        }
    }
    /** Overridden toString() method to convert an instance of the Remove_greater_keyCommand class into a string representation*/
    @Override
    public String toString() {return "remove_greater_key";}
}

