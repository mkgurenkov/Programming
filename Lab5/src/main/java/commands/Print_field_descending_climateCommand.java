package commands;

import commands.abstracts.CommandWithoutArg;
import helperClasses.CollectionManager;
import data.City;
import data.Climate;
import java.util.*;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "print_field_descending_climate" command
 */
public class Print_field_descending_climateCommand extends CommandWithoutArg {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public Print_field_descending_climateCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: groups and arranges in descending order the climate field of all elements of the collection*/
    @Override
    public void execute() {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        int mon = 0;
        int tun = 0;
        int hum = 0;
        int med = 0;
        for(Map.Entry<Integer, City> entry: collection.entrySet()) {
            switch (entry.getValue().getClimate()) {
                case MONSOON:
                    mon ++;
                    break;
                case TUNDRA:
                    tun++;
                    break;
                case HUMIDCONTINENTAL:
                    hum++;
                    break;
                case MEDITERRANIAN:
                    med++;
                    break;
                default: break;
            }
        }
        Hashtable<Climate, Integer> clims = new Hashtable<>();
        clims.put(Climate.MONSOON, mon);
        clims.put(Climate.TUNDRA, tun);
        clims.put(Climate.HUMIDCONTINENTAL, hum);
        clims.put(Climate.MEDITERRANIAN, med);
        List vals = new ArrayList(clims.entrySet());
        Collections.sort(vals, new Comparator<Map.Entry<Climate, Integer>>() {
            @Override
            public int compare(Map.Entry<Climate, Integer> a, Map.Entry<Climate, Integer> b) {
                return a.getValue() - b.getValue();
            }
        });
        for (int i = vals.size() - 1; i > -1; i--) {
            System.out.println(vals.get(i));
        }
    }
    /** Overridden toString() method to convert an instance of the Print_field_descending_climateCommand class into a string representation*/
    @Override
    public String toString() {return "print_field_descending_climate";}
}
