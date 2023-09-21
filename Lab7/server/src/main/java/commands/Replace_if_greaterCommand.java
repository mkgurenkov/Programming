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
 * Class that embodies "replace_if_greater" command
 */
public class Replace_if_greaterCommand extends Command {
    /** Constructor*/
    public Replace_if_greaterCommand() {this.setType("replace_if_greater");}
    /**
     * Execute method: replaces element by the key if entered element is larger than element in collection (comparison by area)
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
        if (collection.get(Integer.parseInt(super.getArgument())) != null) {
            if (CheckElement.checkFields(element, collection, false)) {
                if (element.getArea() > collection.get(Integer.parseInt(super.getArgument())).getArea()) {
                    int oldId = collection.get(Integer.parseInt(super.getArgument())).getId();
                    element.setId(oldId);
                    collection.put(Integer.parseInt(super.getArgument()), element);
                    InteractionWithClient.sendMessage("Element has been replaced successfully");
                } else {
                    InteractionWithClient.sendMessage("Element was not replaced");
                }
            } else {
                InteractionWithClient.sendMessage("The entry of the element was completed incorrectly, the command was not executed");
            }
        } else {
            InteractionWithClient.sendMessage("There is no element with such key");
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

