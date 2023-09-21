package helperClasses;

import data.*;
import helperClasses.checkers.CheckField;
import helperClasses.enums.Coordinate;
import helperClasses.enums.DateComponent;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class, responsible for receiving the City type command argument
 */
public class ElementReceiver {
    /** Field: City element*/
    private final City element;
    /** Field: collectionManager*/
    private final CollectionManager collectionManager;
    /** Field: exit - flag to interrupt execution of the main method*/
    private int exit;
    /** Field: keeps path to the script file*/
    public static String pathToFile;
    /** Field: fromWhichLine - field, which keeps an index of a line in file, from which input should be read*/
    public static int fromWhichLine;

    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     */
    public ElementReceiver(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.element = new City();
        this.exit = 0;
    }

    /** Main ReceiveElement class method: gets all the fields of the class from the user and assigns them to the returned element
     * @return City element - received element
     */
    public City receive() {
        InteractionWithClient.sendMessage("Type \"exit\" to exit from element input");
        InteractionWithClient.sendMessage("To enter \"null\"(0) values press \"Enter\"");
        while (true) {
            element.setCreationDate(LocalDate.now());
            element.setCreationDateHash(element.getCreationDate().hashCode());
            element.setId(generateId());
            receiveName();
            if (exit == 1) {break;}
            receiveCords();
            if (exit == 1) {break;}
            receiveArea();
            if (exit == 1) {break;}
            receivePopulation();
            if (exit == 1) {break;}
            receiveMetersAboveSeaLevel();
            if (exit == 1) {break;}
            receiveClimate();
            if (exit == 1) {break;}
            receiveGovernment();
            if (exit == 1) {break;}
            receiveStandardOfLiving();
            if (exit == 1) {break;}
            receiveGovernor();
            break;
        }
        return element;
    }

    /** Method which generates id
     * @return int id
     */
    private int generateId() {
        Hashtable<Integer, City> collection = collectionManager.getCollection();
        int id = 1;
        while (!CheckField.id(id, collection, false)) {
            id++;
        }
        return id;
    }
    /** Method which receives name*/
    private void receiveName() {
        while (true) {
            InteractionWithClient.sendMessage("Enter name: ");
            String name = scan();
            if (name.equals("exit")) {
                exit = 1;
                break;
            } else if (CheckField.name(name)) {
                element.setName(name);
                break;
            } else {
                InteractionWithClient.sendMessage("Invalid name input, please try again");
            }
        }
    }
    /** Method which receives coordinates*/
    private void receiveCords() {
        Coordinates coordinates = new Coordinates();
        while (true) {
            InteractionWithClient.sendMessage("Enter coordinates: ");
            receiveCord(coordinates, Coordinate.X);
            if(exit == 1) {break;}
            receiveCord(coordinates, Coordinate.Y);
            if(exit == 1) {break;}
            element.setCoordinates(coordinates);
            break;
        }
    }
    /** Method which receives coordinate*/
    private void receiveCord(Coordinates coordinates, Coordinate cord) {
        while (true) {
            InteractionWithClient.sendMessage("Enter " + cord.toString().toLowerCase() + ": ");
            String strCord = scan();
            if (strCord.equals("exit")) {
                exit = 1;
                break;
            } else if (CheckField.cord(strCord)) {
                if (cord == Coordinate.X) {
                    coordinates.setX(Long.parseLong(strCord));
                } else if (cord == Coordinate.Y) {
                    coordinates.setY(Long.parseLong(strCord));
                }
                break;
            } else {
                InteractionWithClient.sendMessage("Invalid coordinates input, please try again");
            }
        }
    }
    /** Method which receives area*/
    private void receiveArea() {
        while (true) {
            InteractionWithClient.sendMessage("Enter area: ");
            String area = scan();
            if (area.equals("exit")) {
                exit = 1;
                break;
            } else if (CheckField.area(area)) {
                element.setArea(Float.parseFloat(area));
                break;
            } else {
                InteractionWithClient.sendMessage("Invalid area input, please try again");
            }

        }
    }
    /** Method which receives population*/
    private void receivePopulation() {
        while (true) {
            InteractionWithClient.sendMessage("Enter population: ");
            String population = scan();
            if (population.equals("exit")) {
                exit = 1;
                break;
            } else if (CheckField.population(population)) {
                element.setPopulation(Integer.parseInt(population));
                break;
            } else {
                InteractionWithClient.sendMessage("Invalid population input, please try again");
            }

        }
    }
    /** Method which receives metersAboveSeaLevel*/
    private void receiveMetersAboveSeaLevel() {
        while (true) {
            InteractionWithClient.sendMessage("Enter meters above sea level: ");
            String metAbSeaLvl = scan();
            if (metAbSeaLvl.equals("exit")) {
                exit = 1;
                break;
            } else if (metAbSeaLvl.equals("")) {
                break;
            } else if (CheckField.metersAboveSeaLevel(metAbSeaLvl)) {
                element.setMetersAboveSeaLevel(Integer.parseInt(metAbSeaLvl));
                break;
            } else {
                InteractionWithClient.sendMessage("Invalid meters above sea level input, please try again");
            }
        }
    }
    /** Method which receives climate*/
    private void receiveClimate() {
        while (true) {
            InteractionWithClient.sendMessage("Enter climate (MONSOON | HUMIDCONTINENTAL | MEDITERRANIAN | TUNDRA): ");
            String climate = scan();
            if (climate.equals("exit")) {
                exit = 1;
                break;
            } else if (CheckField.climate(climate)) {
                element.setClimate(Climate.valueOf(climate));
                break;
            } else {
                InteractionWithClient.sendMessage("Invalid climate input, please try again");
            }
        }
    }
    /** Method which receives government*/
    private void receiveGovernment() {
        while (true) {
            InteractionWithClient.sendMessage("Enter government (KRITARCHY | PUPPET_STATE | JUNTA): ");
            String government = scan();
            if (government.equals("exit")) {
                exit = 1;
                break;
            } else if (government.equals("")) {
                break;
            } else if (CheckField.government(government)) {
                element.setGovernment(Government.valueOf(government));
                break;
            } else {
                InteractionWithClient.sendMessage("Invalid government input, please try again");
            }
        }
    }
    /** Method which receives standardOfLiving*/
    private void receiveStandardOfLiving() {
        while (true) {
            InteractionWithClient.sendMessage("Enter standard of living (VERY_HIGH | HIGH | LOW | VERY_LOW): ");
            String stanOfLvng = scan();
            if (stanOfLvng.equals("exit")) {
                exit = 1;
                break;
            } else if (stanOfLvng.equals("")) {
                break;
            } else if (CheckField.standardOfLiving(stanOfLvng)) {
                element.setStandardOfLiving(StandardOfLiving.valueOf(stanOfLvng));
                break;
            } else {
                InteractionWithClient.sendMessage("Invalid standard of leaving input, please try again");
            }
        }
    }
    /** Method which receives governor*/
    private void receiveGovernor() {
        Human governor = new Human();
        while (true) {
            InteractionWithClient.sendMessage("Enter governor: ");
            receiveGovernorsName(governor);
            if (exit == 1) {break;}
            receiveGovernorsAge(governor);
            if (exit == 1) {break;}
            while (true) {
                InteractionWithClient.sendMessage("Would you like to enter a date of birth? [y/n]");
                String response = scan();
                if (response.equals("y")) {
                    receiveGovernorsBirthday(governor);
                    if (governor.getBirthday() != null) {
                        element.setGovernor(governor);
                    }
                    break;
                } else if (response.equals("n")) {
                    element.setGovernor(governor);
                    exit = 1;
                    break;
                } else if (response.equals("exit")) {
                    exit = 1;
                    break;
                } else {
                    InteractionWithClient.sendMessage("Invalid input, try again");
                }
            }
            break;
        }
    }
    /** Method which receives governor's name*/
    private void receiveGovernorsName(Human governor) {
        while (true) {
            InteractionWithClient.sendMessage("Enter name: ");
            String name = scan();
            if (name.equals("exit")) {
                exit = 1;
                break;
            } else if (CheckField.name(name)) {
                governor.setName(name);
                break;
            } else {
                InteractionWithClient.sendMessage("Invalid name input, please try again");
            }
        }
    }
    /** Method which receives governor's age*/
    private void receiveGovernorsAge(Human governor) {
        while (true) {
            InteractionWithClient.sendMessage("Enter age: ");
            String age = scan();
            if (age.equals("exit")) {
                exit = 1;
                break;
            } else if (age.equals("")) {
                break;
            } else if (CheckField.age(age)) {
                governor.setAge(Integer.parseInt(age));
                break;
            } else {
                InteractionWithClient.sendMessage("Invalid age input, please try again");
            }
        }
    }
    /** Method which receives governor's birthday*/
    private void receiveGovernorsBirthday(Human governor) {
        LocalDateTime birthday;
        int year;
        int month;
        int day;
        int hour;
        int minute;
        while (true) {
            InteractionWithClient.sendMessage("Enter birthday: ");
            year = receiveDateComponent(DateComponent.YEAR);
            if (exit == 1) {break;}
            month = receiveDateComponent(DateComponent.MONTH);
            if (exit == 1) {break;}
            day = receiveDateComponent(DateComponent.DAY);
            if (exit == 1) {break;}
            hour = receiveDateComponent(DateComponent.HOUR);
            if (exit == 1) {break;}
            minute = receiveDateComponent(DateComponent.MINUTE);
            if (exit == 1) {break;}
            try {
                birthday = LocalDateTime.of(year, month, day, hour, minute);
                governor.setBirthday(birthday);
                long days = LocalDate.now().toEpochDay() - birthday.toLocalDate().toEpochDay(); //сколько дней прожил человек по факту
                long days2 = LocalDate.now().toEpochDay() - LocalDate.of(LocalDate.now().getYear() - governor.getAge(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()).toEpochDay();//ровно age губернатора в днях
                if (days >= days2) {
                    break;
                } else {
                    InteractionWithClient.sendMessage("Invalid birthday input: date of birth must match age");
                }
            } catch (DateTimeException e) { //проверяет "неочевидные" ошибки(високосные года, сколько дней в каком месяце)
                InteractionWithClient.sendMessage("Invalid date input, please try again");
            }
        }
    }

    /** Method which receives dateComponent to put it to the birthday field
     * @param dateComponent - year | month | day | hour | second
     * @return int representation of dateComponent
     */
    private int receiveDateComponent(DateComponent dateComponent) {
        while (true) {
            InteractionWithClient.sendMessage("Enter " + dateComponent.toString().toLowerCase() + ":");
            String strDateComponent = scan();
            if (strDateComponent.equals("exit")) {
                exit = 1;
                break;
            } else if (CheckField.dateComponent(strDateComponent, dateComponent)) {
                return Integer.parseInt(strDateComponent);
            } else {
                InteractionWithClient.sendMessage("Invalid " + dateComponent.toString().toLowerCase() + " input, please try again");
            }
        }
        return 0;
    }

    /**
     * Method which scans input from the file with a script
     * @return String input
     */
    private String scan() {
        try {
            FileReader reader = new FileReader(pathToFile);
            StringBuilder stringBuilder = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
            String script = stringBuilder.toString();
            String[] splittedScript = script.split("\\n");
            fromWhichLine++;
            if (fromWhichLine < splittedScript.length) {
                InteractionWithClient.sendMessage(splittedScript[fromWhichLine]);
                return DeleteSpaces.delete(splittedScript[fromWhichLine]);
            } else {
                return "exit";
            }
        } catch (IOException e) {
            InteractionWithClient.sendMessage(e.getMessage());
        }
        return "";
    }
}