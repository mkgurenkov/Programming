package commands;

import application.CollectionManager;
import data.City;
import data.Climate;

import java.util.*;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "print_field_descending_climate" command
 */
public class PrintFieldDescendingClimate extends Command {
    /** Constructor*/
    public PrintFieldDescendingClimate(CollectionManager collectionManager) {
        super(collectionManager);
        this.setType("print_field_descending_climate");
    }
    public void execute() {
        int mon = 0;
        int tun = 0;
        int hum = 0;
        int med = 0;
        for(Map.Entry<Integer, City> entry: getCollectionManager().getCollection().entrySet()) {
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
            getClientInteractor().sendString(String.valueOf(vals.get(i)));
        }
    }
}
