package helperClasses.wrappers;

import data.City;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Hashtable;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Wrapper class for collection
 */
@XmlRootElement(name = "collection")
public class CollectionWrapper {
    /** Field: collection */
    private Hashtable<Integer, City> cities;

    /**
     * Default constructor
     */
    public CollectionWrapper(){}
    /**
     * Constructor
     * @param cities - collection
     */
    public CollectionWrapper(Hashtable<Integer, City> cities){
        this.cities = cities;
    }
    /** Method for getting collection
     * @return Hashtable<Integer, City> collection
     */
    @XmlElement(name = "city")
    public Hashtable<Integer, City> getCities() {
        return cities;
    }
    /** Method for setting collection*/
    public void setCities(Hashtable<Integer, City> cities) {
        this.cities = cities;
    }
}
