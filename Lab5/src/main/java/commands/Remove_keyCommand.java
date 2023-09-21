package commands;

import commands.abstracts.CommandWithArg;
import helperClasses.CollectionManager;
import data.City;
import java.util.Hashtable;
import java.util.Map;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "remove_key" command
 */
public class Remove_keyCommand extends CommandWithArg<Integer> {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public Remove_keyCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: removes element from collection by its key
     * @param key = the key of the element you want to remove
     */
    @Override
    public void execute(Integer key) {
        boolean keyWasFound = false;
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        for(Map.Entry<Integer, City> entry: collection.entrySet()) {
            if (entry.getKey() == key) {
                collection.remove(entry.getKey());
                System.out.println("Element has been removed");
                keyWasFound = true;
                break;
            }
        }
        if (!keyWasFound) {
            System.out.println("There is no element with such key");
        }
    }
    /** Overridden toString() method to convert an instance of the Remove_keyCommand class into a string representation*/
    @Override
    public String toString() {return "remove_key";}
}
