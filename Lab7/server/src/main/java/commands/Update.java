package commands;

import application.CollectionManager;
import data.City;

import java.util.Hashtable;
import java.util.Map;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "update" command
 */
public class Update extends Command {
    /** Constructor*/
    public Update(CollectionManager collectionManager) {
        super(collectionManager);
        this.setType("update");
    }
    public void execute() {
        if (Integer.parseInt(super.getArgument()) > 0) {
            boolean idWasFound = false;
            Hashtable<Integer, City> collection = getCollectionManager().getCollection();
            for(Map.Entry<Integer, City> entry: collection.entrySet()) {
                if (entry.getValue().getId() == Integer.parseInt(super.getArgument())) {
                    Insert insert = new Insert(getCollectionManager());
                    insert.setArgument(String.valueOf(entry.getKey()));
                    insert.setUsername(getUsername());
                    if (!executingFromScript) {
                        insert.setElement(this.getElement());
                    } else {
                        insert.executingFromScript = true;
                    }
                    insert.execute();
                    idWasFound = true;
                    break;
                }
            }
            if (!idWasFound) {
                getClientInteractor().sendString("There is no element with such id");
            }
        } else {
            getClientInteractor().sendString("Id value must be positive");
        }
    }
}
