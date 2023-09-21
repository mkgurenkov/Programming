package commands;

import helperClasses.CollectionManager;
import data.City;
import data.Climate;
import helperClasses.InteractionWithClient;
import java.util.*;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "filter_by_climate" command
 */
public class Filter_by_climateCommand extends Command {
    /** Constructor*/
    public Filter_by_climateCommand() {this.setType("filter_by_climate");}
    /**
     * Execute method: prints collection elements, which have the same type of climate as the argument
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public void execute(CollectionManager collectionManager) {
        try {
            Climate.valueOf(super.getArgument());
            int howManyElements = 0;
            Hashtable<Integer, City> collection = collectionManager.getCollection();
            for(Map.Entry<Integer, City> entry: collectionManager.sort(collection).entrySet()) {
                if (entry.getValue().getClimate() == Climate.valueOf(super.getArgument())) {
                    InteractionWithClient.sendMessage("  " + entry.getKey() + " " + entry.getValue().toString());
                    howManyElements ++;
                }
            }
            if (howManyElements == 0) {
                InteractionWithClient.sendMessage("There are no elements with such climate");
            }
        } catch (IllegalArgumentException e) {
            InteractionWithClient.sendMessage("There is no such type of climate");
            InteractionWithClient.sendMessage("filter_by_climate climate (\"MONSOON\", \"HUMIDCONTINENTAL\", \"MEDITERRANIAN\", \"TUNDRA\") - вывести элементы, значение поля climate которых равно заданному");
        }
    }
}