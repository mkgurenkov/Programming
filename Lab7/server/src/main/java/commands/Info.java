package commands;

import application.CollectionManager;
import data.City;

import java.util.Hashtable;

/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "info" command
 */
public class Info extends Command {
    /** Constructor*/
    public Info(CollectionManager collectionManager) {
        super(collectionManager);
        this.setType("info");
    }
    public void execute() {
        Hashtable<Integer, City> collection = getCollectionManager().getCollection();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type of collection: ").append(collection.getClass()).append("\n");
        stringBuilder.append("Initialization time: ").append(getCollectionManager().getInitializationDate()).append("\n");
        stringBuilder.append("Amount of items: ").append(collection.size());
        getClientInteractor().sendString(String.valueOf(stringBuilder));
    }
}
