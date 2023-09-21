package commands;

import helperClasses.CollectionManager;
import data.City;
import helperClasses.ElementReceiver;
import helperClasses.InteractionWithClient;
import helperClasses.checkers.CheckElement;
import helperClasses.checkers.CheckField;
import java.util.Hashtable;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "remove_lower" command
 */
public class Remove_lowerCommand extends Command {
    /** Constructor*/
    public Remove_lowerCommand() {this.setType("remove_lower");}
    /**
     * Execute method: removes elements, lower than entered element (comparison by area)
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public void execute(CollectionManager collectionManager) {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        Hashtable<Integer, City> collection2 = new Hashtable<>(collection);
        int numberOfElementsBefore = collection2.size();
        City element;
        if (!executingFromScript) {
            element = super.getElement();
            element.setId(generateId(collectionManager));
        } else {
            ElementReceiver elementReceiver = new ElementReceiver(collectionManager);
            element = elementReceiver.receive();
        }
        if (CheckElement.checkFields(element, collection, false)) {
            collection.entrySet().stream()
                    .filter(x -> element.getArea() > x.getValue().getArea())
                    .forEach(x -> collection2.remove(x.getKey()));
            collectionManager.setCollection(collection2);
            if (numberOfElementsBefore - collection2.size() == 1) {
                InteractionWithClient.sendMessage(numberOfElementsBefore - collection2.size() + " element has been removed");
            } else {
                InteractionWithClient.sendMessage(numberOfElementsBefore - collection2.size() + " elements has been removed");
            }
        } else {
            InteractionWithClient.sendMessage("The entry of the element was completed incorrectly, the command was not executed");
        }
    }
    /**
     * Method which generates id for the element
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     * @return id
     */
    private int generateId(CollectionManager collectionManager) {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        int id = 1;
        while (!CheckField.id(id, collection, false)) {
            id++;
        }
        return id;
    }
}

