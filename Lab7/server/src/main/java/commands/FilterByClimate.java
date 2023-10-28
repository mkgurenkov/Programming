package commands;

import application.CollectionManager;
import data.City;
import data.Climate;

import java.util.*;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "filter_by_climate" command
 */
public class FilterByClimate extends Command {
    /** Constructor*/
    public FilterByClimate(CollectionManager collectionManager) {
        super(collectionManager);
        this.setType("filter_by_climate");
    }
    public void execute() {
        Hashtable<Integer, City> collection = getCollectionManager().getCollection();
        try {
            Climate.valueOf(super.getArgument());
            int howManyElements = 0;
            for(Map.Entry<Integer, City> entry: getCollectionManager().sort(collection).entrySet()) {
                if (entry.getValue().getClimate() == Climate.valueOf(super.getArgument())) {
                    getClientInteractor().sendString("  " + entry.getKey() + " " + entry.getValue().toString());
                    howManyElements ++;
                }
            }
            if (howManyElements == 0) {
                getClientInteractor().sendString("There are no elements with such climate");
            }
        } catch (IllegalArgumentException e) {
            getClientInteractor().sendString("There is no such type of climate");
            getClientInteractor().sendString("filter_by_climate climate (\"MONSOON\", \"HUMIDCONTINENTAL\", \"MEDITERRANIAN\", \"TUNDRA\") - вывести элементы, значение поля climate которых равно заданному");
        }
    }
}