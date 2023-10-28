package application;

import data.City;
import externalConnections.InteractionWithDatabase;

import java.time.LocalDateTime;
import java.util.*;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that manages the collection
 */
public class CollectionManager {
    /** Field: collection*/
    private Hashtable<Integer, City> collection = new Hashtable<>();
    /** Field: collection initialization date*/
    private final LocalDateTime initializationDate;
    /** Field: path to the xml file with collection*/
    public CollectionManager() {
        loadCollection();
        this.initializationDate = LocalDateTime.now();
    }

    /** Method for loading collection from xml file*/
    public void loadCollection() {
        collection = InteractionWithDatabase.loadCollection();
    }

    public Hashtable<Integer, City> getCollection() {return collection;}
    /** Method which sets new collection*/
    public void setCollection(Hashtable<Integer, City> collection) {this.collection = collection;}
    /** Method which gets the date of collection initialization
     * @return LocalDateTime initializationDate
     */
    public LocalDateTime getInitializationDate() {return initializationDate;}
    /** Method which gets path to the file with collection
     * @return String pathToFile
     */
    public Map<Integer, City> sort(Hashtable<Integer, City> collection) {
        Map<Integer, City> sortedCollection = new TreeMap<Integer, City>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        sortedCollection.putAll(collection);
        return sortedCollection;
    }
    /** Method which starts interaction with client (using in the "interactiveMode")*/
}
