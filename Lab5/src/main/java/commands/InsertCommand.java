package commands;

import commands.abstracts.CommandWithArg;
import helperClasses.CollectionManager;
import data.City;
import helperClasses.EnterElement;
import helperClasses.checkers.CheckElement;
import java.util.Hashtable;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "insert" command
 */
public class InsertCommand extends CommandWithArg<Integer> {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public InsertCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: starts the element's input method and adds  it to the collection
     * @param key - key for the new element
     */
    @Override
    public void execute(Integer key) {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        EnterElement enterElement = new EnterElement(collectionManager);
        City element = enterElement.enter();
        if (enterElement.getExit_program() == 1) {
            ExitCommand exit = new ExitCommand(collectionManager);
            exit.execute();
        } else if (CheckElement.checkFields(element, collection, false)) { //нужно чтобы это условие проверяло не равны ли null те поля элемента которые не должны быть равны null
            if (collection.containsKey(key)) {
                System.out.println("Element has been updated successfully");
                int oldId = collection.get(key).getId();
                collection.put(key, element);
                collection.get(key).setId(oldId); //по умолчанию EnterElement генерирует новый id, но в данном случае мы заменяем элемент поэтому наверное лучше оставить старый
            } else {
                System.out.println("Element has been added successfully");
                collection.put(key, element);
            }
        } else {
            if (collection.containsKey(key)) {
                System.out.println("Element was not updated");
            } else {
                System.out.println("Element was not added");
            }
        }
    }
    /** Overridden toString() method to convert an instance of the InsertCommand class into a string representation*/
    @Override
    public String toString() {return "insert";}
}
