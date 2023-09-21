package commands;

import commands.abstracts.CommandWithArg;
import helperClasses.CollectionManager;
import data.City;
import data.Climate;
import java.util.Hashtable;
import java.util.Map;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "filter_by_climate" command
 */
public class Filter_by_climateCommand extends CommandWithArg<String> {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public Filter_by_climateCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: prints collection elements, which have the same type of climate as the argument
     * @param strClimate - climate type
     */
    @Override
    public void execute(String strClimate) {
        try {
            Climate.valueOf(strClimate);
            int howManyElements = 0;
            Hashtable<Integer, City> collection = collectionManager.getCollection();
            for(Map.Entry<Integer, City> entry: collectionManager.sort(collection).entrySet()) {
                if (entry.getValue().getClimate() == Climate.valueOf(strClimate)) {
                    System.out.println("  " + entry.getKey() + " " + entry.getValue().toString());
                    howManyElements ++;
                }
            }
            if (howManyElements == 0) {
                System.out.println("There are no elements with such climate");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("There is no such type of climate");
            System.out.println("filter_by_climate climate (\"MONSOON\", \"HUMIDCONTINENTAL\", \"MEDITERRANIAN\", \"TUNDRA\") - вывести элементы, значение поля climate которых равно заданному");
        }
    }
    /** Overridden toString() method to convert an instance of the Filter_by_climateCommand class into a string representation*/
    @Override
    public String toString() {return "filter_by_climate";}
}