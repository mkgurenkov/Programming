package commands;

import commands.abstracts.CommandWithoutArg;
import helperClasses.CollectionManager;
import data.City;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "show" command
 */
public class ShowCommand extends CommandWithoutArg {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public ShowCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: displays all collection elements*/
    @Override
    public void execute() {
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
        System.out.println(stringBuilder);
    }
    /** Overridden toString() method to convert an instance of the ShowCommand class into a string representation*/
    @Override
    public String toString() {return "show";}
}
