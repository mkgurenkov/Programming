package validators;

import data.City;
import enums.DateComponent;
import enums.NumberDataType;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.Map;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class for checking the correction of City fields
 */
public class ElementFieldValidator {
    /** Method for checking the correction of name
     * @return boolean nameIsCorrect
     */
    public static boolean name(String string) {
        if (string.isBlank()) {
            return false;
        } else {return true;}
    }
    /** Method for checking the correction of cord
     * @return boolean cordIsCorrect
     */
    public static boolean cord(String string) {
        if (!string.equals("") && canBeConverted(string, NumberDataType.LONG)) {
            return true;
        } else {return false;}
    }
    /** Method for checking the correction of area
     * @return boolean areaIsCorrect
     */
    public static boolean area(String string) {
        if (!string.equals("") && canBeConverted(string, NumberDataType.FLOAT)) {
            float area = Float.parseFloat(string);
            return area > 0;
        } else {return false;}
    }
    /** Method for checking the correction of population
     * @return boolean populationIsCorrect
     */
    public static boolean population(String string) {
        if (!string.equals("") && canBeConverted(string, NumberDataType.INTEGER)) {
            int population = Integer.parseInt(string);
            return population > 0;
        } else {return false;}
    }
    /** Method for checking the correction of meters above sea level
     * @return boolean metersAboveSeaLevelIsCorrect
     */
    public static boolean metersAboveSeaLevel(String string) {
        return canBeConverted(string, NumberDataType.INTEGER);
    }
    /** Method for checking the correction of climate
     * @return boolean climateIsCorrect
     */
    public static boolean climate(String string) {
        switch (string) {
            case "MONSOON", "HUMIDCONTINENTAL", "MEDITERRANIAN", "TUNDRA":
                return true;
            default:
                return false;
        }
    }
    /** Method for checking the correction of government
     * @return boolean governmentIsCorrect
     */
    public static boolean government(String string) {
        if (string != null) {
            switch (string) {
                case "KRITARCHY", "PUPPET_STATE", "JUNTA":
                    return true;
                default:
                    return false;
            }
        } return true;
    }
    /** Method for checking the correction of standard of living
     * @return boolean standardOfLivingIsCorrect
     */
    public static boolean standardOfLiving(String string) {
        if (string != null) {
            switch (string) {
                case "VERY_HIGH", "HIGH", "LOW", "VERY_LOW":
                    return true;
                default:
                    return false;
            }
        } return true;
    }
    /** Method for checking the correction of age
     * @return boolean ageIsCorrect
     */
    public static boolean age(String string) {
        if (!string.equals("") && canBeConverted(string, NumberDataType.INTEGER)) {
            int age = Integer.parseInt(string);
            return age > 0;
        } else {return false;}
    }
    /** Method for checking the correction of range of data components (year, month, day, hour, minute)
     * @return boolean dateComponentIsCorrect
     */
    public static boolean dateComponent(String strDateComponent, DateComponent dateComponent) { //проверяет на очевидные ошибки
        if (canBeConverted(strDateComponent, NumberDataType.INTEGER)) {
            switch (dateComponent) {
                case YEAR:
                    if ((Integer.parseInt(strDateComponent) < 0) | (Integer.parseInt(strDateComponent) > LocalDate.now().getYear())) {
                        return false;
                    } else {return true;}
                case MONTH:
                    if ((Integer.parseInt(strDateComponent) < 1) | (Integer.parseInt(strDateComponent) > 12)) {
                        return false;
                    } else {return true;}
                case DAY:
                    if ((Integer.parseInt(strDateComponent) < 1) | (Integer.parseInt(strDateComponent) > 31)) {
                        return false;
                    } else {return true;}
                case HOUR:
                    if ((Integer.parseInt(strDateComponent) < 0) | (Integer.parseInt(strDateComponent) > 23)) {
                        return false;
                    } else {return true;}
                case MINUTE:
                    if ((Integer.parseInt(strDateComponent) < 0) | (Integer.parseInt(strDateComponent) > 59)) {
                        return false;
                    } else {return true;}
                default: return false;
            }
        } else {return false;}
    }
    /** Method for checking if string representation of City field can be converted to required data type
     * @return boolean canBeConverted
     */
    private static boolean canBeConverted(String string, NumberDataType numberDataType) {
        try {
            switch (numberDataType) {
                case INTEGER:
                    Integer.parseInt(string);
                    break;
                case LONG:
                    Long.parseLong(string);
                    break;
                case FLOAT:
                    Float.parseFloat(string);
                    break;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
