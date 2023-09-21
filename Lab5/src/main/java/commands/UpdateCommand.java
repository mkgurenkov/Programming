package commands;

import commands.abstracts.CommandWithArg;
import helperClasses.CollectionManager;
import data.City;
import java.util.Hashtable;
import java.util.Map;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "update" command
 */
public class UpdateCommand extends CommandWithArg<Integer> {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public UpdateCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: updates element by its id
     * @param id - the id of the element you want to update
     */
    @Override
    public void execute(Integer id) {
        if (id > 0) {
            boolean idWasFound = false;
            Hashtable<Integer, City> collection = collectionManager.getCollection();
            for(Map.Entry<Integer, City> entry: collection.entrySet()) {
                if (entry.getValue().getId() == id) {
                    InsertCommand insert = new InsertCommand(collectionManager);
                    insert.execute(entry.getKey());
                    idWasFound = true;
                    break;
                }
            }
            if (!idWasFound) {
                System.out.println("There is no element with such id");
            }
        } else {
            System.out.println("Id value must be positive");
        }
    }
    /** Overridden toString() method to convert an instance of the UpdateCommand class into a string representation*/
    @Override
    public String toString() {return "update";}
}
