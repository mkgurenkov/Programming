package validators;

import data.*;
import validators.ElementFieldValidator;

import java.time.LocalDate;
import java.util.Hashtable;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class for checking the correction of element's fields
 */
public class ElementValidator {
    /** Method for checking the correction of element's fields
     * @return boolean elementIsCorrect
     */
    public static boolean checkFields(City element, Hashtable<Integer, City> collection, boolean elementInCollection) {
        if (element.isNull()) {
            return false;
        } else {
            String name = element.getName();
            String x = element.getCoordinates().getX().toString();
            String y = element.getCoordinates().getY().toString();
            String area = Float.toString(element.getArea());
            String population = element.getPopulation().toString();
            String climate = element.getClimate().toString();
            String government = null;
            String standardOfLiving = null;
            if (element.getGovernment() != null) {
                government = element.getGovernment().toString();
            }
            if (element.getStandardOfLiving() != null) {
                standardOfLiving = element.getStandardOfLiving().toString();
            }
            String governorsName = element.getGovernor().getName();
            String governorsAge = Integer.toString(element.getGovernor().getAge());
            return ElementFieldValidator.name(name) && ElementFieldValidator.cord(x) && ElementFieldValidator.cord(y) && ElementFieldValidator.area(area) &&
                    ElementFieldValidator.population(population) && ElementFieldValidator.climate(climate) && ElementFieldValidator.government(government) &&
                    ElementFieldValidator.standardOfLiving(standardOfLiving) && ElementFieldValidator.name(governorsName) && ElementFieldValidator.age(governorsAge);
        }
    }
    /** Method for checking if birthday matches age
     * @return boolean birthdayMatchesAge
     */
    public static boolean checkIfBirthdayMatchesAge(City element) {
        if (element.getGovernor().getBirthday() != null) {
            long days = LocalDate.now().toEpochDay() - element.getGovernor().getBirthday().toLocalDate().toEpochDay(); //сколько дней прожил человек по факту
            long days2 = LocalDate.now().toEpochDay() - LocalDate.of(LocalDate.now().getYear() - element.getGovernor().getAge(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()).toEpochDay();//ровно age губернатора в днях
            if (days >= days2) {
                return true;
            } else {
                return false;
            }
        } else {return true;}
    }
    /** Method for checking if creationDate field in xml file is authentic by comparing its hashcode with its primary hashcode (creationDateHash)
     * @return boolean creationDateMatchesHashCode
     */
    public static boolean checkIfCreationDateMatchesHashCode(City element) {
        if (element.getCreationDate().hashCode() == element.getCreationDateHash()) {
            return true;
        } else {return false;}
    }
}
