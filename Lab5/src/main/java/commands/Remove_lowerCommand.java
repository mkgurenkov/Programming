package commands;

import commands.abstracts.CommandWithoutArg;
import helperClasses.CollectionManager;
import data.City;
import helperClasses.EnterElement;
import helperClasses.checkers.CheckElement;
import java.util.Hashtable;
import java.util.Map;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "remove_lower" command
 */
public class Remove_lowerCommand extends CommandWithoutArg {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public Remove_lowerCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: removes elements, lower than entered element (comparison by area)*/
    @Override
    public void execute() {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        EnterElement enterElement = new EnterElement(collectionManager);
        City element = enterElement.enter();
        if (enterElement.getExit_program() == 1) {
            ExitCommand exit = new ExitCommand(collectionManager);
            exit.execute();
        } else if (CheckElement.checkFields(element, collection, false)) { //нужно чтобы это условие проверяло не равны ли null те поля элемента которые не должны быть равны null (то есть не был ли ввод элемента завершен раньше)
            Hashtable<Integer, City> collection2 = new Hashtable<>(collection);
            int numberOfElementsBefore = collection2.size();
            for(Map.Entry<Integer, City> entry: collection.entrySet()) {
                if (element.getArea() > entry.getValue().getArea()) {
                    collection2.remove(entry.getKey());
                }
            }
            collectionManager.setCollection(collection2);
            if (numberOfElementsBefore - collection2.size() == 1) {
                System.out.println(numberOfElementsBefore - collection2.size() + " element has been removed");
            } else {
                System.out.println(numberOfElementsBefore - collection2.size() + " elements has been removed");
            }
        } else {
            System.out.println("0 elements has been removed");
        }
    }
    /** Overridden toString() method to convert an instance of the Remove_lowerCommand class into a string representation*/
    @Override
    public String toString() {return "remove_lower";}
}

