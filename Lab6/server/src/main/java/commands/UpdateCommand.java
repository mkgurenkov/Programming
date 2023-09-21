package commands;

import helperClasses.CollectionManager;
import data.City;
import helperClasses.InteractionWithClient;
import java.util.Hashtable;
import java.util.Map;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "update" command
 */
public class UpdateCommand extends Command {
    /** Constructor*/
    public UpdateCommand() {this.setType("update");}
    /**
     * Execute method: updates element by its id
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public void execute(CollectionManager collectionManager) {
        if (Integer.parseInt(super.getArgument()) > 0) {
            boolean idWasFound = false;
            Hashtable<Integer, City> collection = collectionManager.getCollection();
            for(Map.Entry<Integer, City> entry: collection.entrySet()) {
                if (entry.getValue().getId() == Integer.parseInt(super.getArgument())) {
                    InsertCommand insert = new InsertCommand();
                    insert.setArgument(String.valueOf(entry.getKey()));
                    if (!executingFromScript) {
                        insert.setElement(this.getElement());
                    } else {
                        insert.executingFromScript = true;
                    }
                    insert.execute(collectionManager);
                    idWasFound = true;
                    break;
                }
            }
            if (!idWasFound) {
                InteractionWithClient.sendMessage("There is no element with such id");
            }
        } else {
            InteractionWithClient.sendMessage("Id value must be positive");
        }
    }
}
