package commands;

import application.CollectionManager;
import data.City;

import java.util.Hashtable;
import java.util.Map;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "show" command
 */
public class Show extends Command {
    /** Constructor*/
    public Show(CollectionManager collectionManager) {
        super(collectionManager);
        this.setType("show");
    }
    public void execute() {
        Hashtable<Integer, City> collection = getCollectionManager().getCollection();
        StringBuilder stringBuilder = new StringBuilder();
        boolean isFirstElement = true;
        for(Map.Entry<Integer, City> entry: getCollectionManager().sort(collection).entrySet()) {
            if (isFirstElement) {
                stringBuilder.append("  ").append(entry.getKey()).append(" ").append(entry.getValue().toString());
                isFirstElement = false;
            } else {
                stringBuilder.append("\n").append("  ").append(entry.getKey()).append(" ").append(entry.getValue().toString());
            }
        }
        getClientInteractor().sendString(String.valueOf(stringBuilder));
    }
}
