package commands;

import helperClasses.CollectionManager;
import data.City;
import helperClasses.InteractionWithClient;
import java.util.Hashtable;
import java.util.Map;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "show" command
 */
public class ShowCommand extends Command {
    /** Constructor*/
    public ShowCommand() {this.setType("show");}
    /**
     * Execute method: displays all collection elements
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public void execute(CollectionManager collectionManager) {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        StringBuilder stringBuilder = new StringBuilder();
        boolean isFirstElement = true;
        for(Map.Entry<Integer, City> entry: collectionManager.sort(collection).entrySet()) {
            if (isFirstElement) {
                stringBuilder.append("  ").append(entry.getKey()).append(" ").append(entry.getValue().toString());
                isFirstElement = false;
            } else {
                stringBuilder.append("\n").append("  ").append(entry.getKey()).append(" ").append(entry.getValue().toString());
            }
        }
        InteractionWithClient.sendMessage(String.valueOf(stringBuilder));
    }
}
