package commands;

import commands.abstracts.CommandWithArg;
import helperClasses.CollectionManager;
import data.City;
import data.Government;
import java.util.Hashtable;
import java.util.Map;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "remove_any_by_government" command
 */
public class Remove_any_by_governmentCommand extends CommandWithArg<String> {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public Remove_any_by_governmentCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: removes element from collection by government
     * @param strGovernment - government type
     */
    @Override
    public void execute(String strGovernment) {
        try {
            Government.valueOf(strGovernment);
            Hashtable<Integer, City> collection = collectionManager.getCollection();
            boolean isRemoved = false;
            for(Map.Entry<Integer, City> entry: collectionManager.sort(collection).entrySet()) {
                if (entry.getValue().getGovernment() == Government.valueOf(strGovernment)) {
                    collection.remove(entry.getKey());
                    System.out.println("Element with key " + entry.getKey() + " has been removed");
                    isRemoved = true;
                    break;
                }
            }
            if (!isRemoved) {
                System.out.println("There are no elements with such government");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("There is no such type of government");
            System.out.println("remove_any_by_government government (\"KRITARCHY\", \"PUPPET_STATE\", \"JUNTA\") - удалить из коллекции один элемент, значение поля government которого эквивалентно заданному");
        }
    }
    /** Overridden toString() method to convert an instance of the Remove_any_by_governmentCommand class into a string representation*/
    @Override
    public String toString() {return "remove_any_by_government";}
}