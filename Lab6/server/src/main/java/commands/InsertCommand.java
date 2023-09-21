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
 * Class that embodies "insert" command
 */
public class InsertCommand extends Command {
    /** Constructor*/
    public InsertCommand() {this.setType("insert");}
    /** Execute method: starts the element's input method and adds  it to the collection
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public void execute(CollectionManager collectionManager) {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        City element;
        if (!executingFromScript) {
            element = super.getElement();
            element.setId(generateId(collectionManager));
        } else {
            ElementReceiver elementReceiver = new ElementReceiver(collectionManager);
            element = elementReceiver.receive();
        }
        if (CheckElement.checkFields(element, collection, false)) {
            if (collection.containsKey(Integer.parseInt(super.getArgument()))) {
                InteractionWithClient.sendMessage("Element has been updated successfully");
                int oldId = collection.get(Integer.parseInt(super.getArgument())).getId();
                element.setId(oldId);
                collection.put(Integer.parseInt(super.getArgument()), element);
            } else {
                InteractionWithClient.sendMessage("Element has been added successfully");
                collection.put(Integer.parseInt(super.getArgument()), element);
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
