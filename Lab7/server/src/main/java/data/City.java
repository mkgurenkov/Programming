package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class for describing a city - element of collection
 */
public class City implements Serializable {
    private String username;
    /** Field: city id*/
    private Integer id;
    /** Field: city name*/
    private String name;
    /** Field: city coordinates*/
    private Coordinates coordinates;
    /** Field: element creation date*/
    private LocalDate creationDate;
    /** Field: creation date hashcode (to compare with creation date field after loading collection from file)*/
    private int creationDateHash;
    /** Field: city area*/
    private float area;
    /** Field: city population*/
    private Integer population;
    /** Field: meters above the sea level of the city*/
    private int metersAboveSeaLevel;
    /** Field: city climate*/
    private Climate climate;
    /** Field: city government*/
    private Government government;
    /** Field: standard of living in the city*/
    private StandardOfLiving standardOfLiving;
    /** Field: city governor*/
    private Human governor;
    /** Default constructor to create an empty city*/
    public City() {}
    /** Constructor to create a city*/
    public City(Integer id, String name, Coordinates coordinates, LocalDate creationDate, float area, Integer population,
                int metersAboveSeaLevel, Climate climate, Government government, StandardOfLiving standardOfLiving,
                Human governor) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        creationDateHash = creationDate.hashCode();
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.climate = climate;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }
    /** Method for getting id
     * @return Integer id
     */
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    /** Method for getting name
     * @return String name
     */
    public String getName() {
        return name;
    }
    /** Method for setting name*/
    public void setName(String name) {
        this.name = name;
    }
    /** Method for getting coordinates
     * @return Coordinates coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    /** Method for setting coordinates*/
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    /** Method for getting creation date
     * @return LocalDate creationDate
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }
    /** Method for setting creation date*/
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    /** Method for getting area
     * @return float area
     */
    public float getArea() {
        return area;
    }
    /** Method for setting area*/
    public void setArea(float area) {
        this.area = area;
    }
    /** Method for getting population
     * @return Long population
     */
    public Integer getPopulation() {
        return population;
    }
    /** Method for setting population*/
    public void setPopulation(Integer population) {
        this.population = population;
    }
    /** Method for getting meters above sea level
     * @return int metersAboveSeaLevel
     */
    public int getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }
    /** Method for setting meters above sea level*/
    public void setMetersAboveSeaLevel(int metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }
    /** Method for getting climate
     * @return Climate climate
     */
    public Climate getClimate() {
        return climate;
    }
    /** Method for setting climate*/
    public void setClimate(Climate climate) {
        this.climate = climate;
    }
    /** Method for getting government
     * @return Government government
     */
    public Government getGovernment() {
        return government;
    }
    /** Method for setting government*/
    public void setGovernment(Government government) {
        this.government = government;
    }
    /** Method for getting standard of living
     * @return StandardOfLiving standardOfLiving
     */
    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }
    /** Method for setting standard of living*/
    public void setStandardOfLiving(StandardOfLiving standardOfLiving) {
        this.standardOfLiving = standardOfLiving;
    }
    /** Method for getting governor
     * @return Human governor
     */
    public Human getGovernor() {
        return governor;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    /** Method for setting governor*/
    public void setGovernor(Human governor) {
        this.governor = governor;
    }
    /** Method for getting creationDateHash
     * @return int creationDateHash
     */
    public int getCreationDateHash() {
        return creationDateHash;
    }
    /** Method for setting creationDateHash*/
    public void setCreationDateHash(int creationDateHash) {
        this.creationDateHash = creationDateHash;
    }

    /** Overridden equals() method to check for equality of instances of the City class*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Float.compare(city.area, area) == 0 && metersAboveSeaLevel == city.metersAboveSeaLevel &&
                Objects.equals(id, city.id) && Objects.equals(name, city.name) && Objects.equals(coordinates, city.coordinates) &&
                Objects.equals(creationDate, city.creationDate) && Objects.equals(population, city.population) && climate == city.climate &&
                government == city.government && standardOfLiving == city.standardOfLiving && Objects.equals(governor, city.governor);
    }

    /** Overridden hashCode() method to check for equality of instances of the City class*/
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate,
                area, population, metersAboveSeaLevel, climate, government, standardOfLiving, governor);
    }
    /** Overridden toString() method to convert an instance of the City class into a string representation*/
    @Override
    public String toString() {
        return getUsername() + "'s City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", area=" + area +
                ", population=" + population +
                ", metersAboveSeaLevel=" + metersAboveSeaLevel +
                ", climate=" + climate +
                ", government=" + government +
                ", standardOfLiving=" + standardOfLiving +
                ", governor=" + governor +
                '}';
    }
    /** Method for checking if fields, which must not be null, are null
     * @return boolean isNull
     */
    public boolean isNull() {
        if ((id == null) | (name == null) | (coordinates == null) | (creationDate == null) | (population == null) |
                (climate == null) | (governor == null)) {
            return true;
        } else if ((getCoordinates().isNull()) | (getGovernor().isNull())) {
            return true;
        } else {return false;}
    }
}