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
 * Class that embodies "replace_if_greater" command
 */
public class Replace_if_greaterCommand extends CommandWithArg<Integer> {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public Replace_if_greaterCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: replaces element by the key if entered element is larger than element in collection (comparison by area)
     * @param key - the key of the element you want to replace
     */
    @Override
    public void execute(Integer key) {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        if (collection.get(key) != null) {
            EnterElement enterElement = new EnterElement(collectionManager);
            City element = enterElement.enter();
            if (enterElement.getExit_program() == 1) {
                ExitCommand exit = new ExitCommand(collectionManager);
                exit.execute();
            } else if (CheckElement.checkFields(element, collection, false)) { //нужно чтобы это условие проверяло не равны ли null те поля элемента которые не должны быть равны null
                if (element.getArea() > collection.get(key).getArea()) {
                    int oldId = collection.get(key).getId();
                    collection.put(key, element);
                    collection.get(key).setId(oldId); //по умолчанию EnterElement генерирует новый id, но в данном случае мы заменяем элемент поэтому наверное лучше оставить старый
                    System.out.println("Element has been replaced successfully");
                } else {
                    System.out.println("Element was not replaced");
                }
            } else {
                System.out.println("Element was not replaced");
            }
        } else {
            System.out.println("There is no element with such key");
        }
    }
    /** Overridden toString() method to convert an instance of the Replace_if_greaterCommand class into a string representation*/
    @Override
    public String toString() {return "replace_if_greater";}
}

