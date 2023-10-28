package otherUtils;

import data.*;
import validators.ElementFieldValidator;
import enums.DateComponent;
import externalConnections.ClientInteractor;

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
    private Hashtable<Integer, City> collection;
    /** Field: exit - flag to interrupt execution of the main method*/
    private int exit;
    /** Field: keeps path to the script file*/
    public static String pathToFile;
    /** Field: fromWhichLine - field, which keeps an index of a line in file, from which input should be read*/
    public static int fromWhichLine;
    private ClientInteractor clientInteractor;
    public ElementReceiver(Hashtable<Integer, City> collection, ClientInteractor clientInteractor) {
        this.collection = collection;
        this.element = new City();
        this.exit = 0;
        this.clientInteractor = clientInteractor;
    }

    /** Main ReceiveElement class method: gets all the fields of the class from the user and assigns them to the returned element
     * @return City element - received element
     */
    public City receive() {
        clientInteractor.sendString("Type \"exit\" to exit from element input");
        clientInteractor.sendString("To enter \"null\"(0) values press \"Enter\"");
        while (true) {
            element.setCreationDate(LocalDate.now());
            element.setCreationDateHash(element.getCreationDate().hashCode());
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
    /** Method which receives name*/
    private void receiveName() {
        while (true) {
            clientInteractor.sendString("Enter name: ");
            String name = scan();
            if (name.equals("exit")) {
                exit = 1;
                break;
            } else if (ElementFieldValidator.name(name)) {
                element.setName(name);
                break;
            } else {
                clientInteractor.sendString("Invalid name input, please try again");
            }
        }
    }
    /** Method which receives coordinates*/
    private void receiveCords() {
        Coordinates coordinates = new Coordinates();
        while (true) {
            clientInteractor.sendString("Enter coordinates: ");
            coordinates.setX(receiveCord("x"));
            if(exit == 1) {break;}
            coordinates.setY(receiveCord("y"));
            if(exit == 1) {break;}
            element.setCoordinates(coordinates);
            break;
        }
    }
    /** Method which receives coordinate*/
    private Long receiveCord(String cordName) {
        while (true) {
            clientInteractor.sendString("Enter " + cordName + ": ");
            String strCord = scan();
            if (strCord.equals("exit")) {
                exit = 1;
                break;
            } else if (ElementFieldValidator.cord(strCord)) {
                return Long.parseLong(strCord);
            } else {
                clientInteractor.sendString("Invalid coordinates input, please try again");
            }
        }
        return 0L;
    }
    /** Method which receives area*/
    private void receiveArea() {
        while (true) {
            clientInteractor.sendString("Enter area: ");
            String area = scan();
            if (area.equals("exit")) {
                exit = 1;
                break;
            } else if (ElementFieldValidator.area(area)) {
                element.setArea(Float.parseFloat(area));
                break;
            } else {
                clientInteractor.sendString("Invalid area input, please try again");
            }

        }
    }
    /** Method which receives population*/
    private void receivePopulation() {
        while (true) {
            clientInteractor.sendString("Enter population: ");
            String population = scan();
            if (population.equals("exit")) {
                exit = 1;
                break;
            } else if (ElementFieldValidator.population(population)) {
                element.setPopulation(Integer.parseInt(population));
                break;
            } else {
                clientInteractor.sendString("Invalid population input, please try again");
            }

        }
    }
    /** Method which receives metersAboveSeaLevel*/
    private void receiveMetersAboveSeaLevel() {
        while (true) {
            clientInteractor.sendString("Enter meters above sea level: ");
            String metAbSeaLvl = scan();
            if (metAbSeaLvl.equals("exit")) {
                exit = 1;
                break;
            } else if (metAbSeaLvl.equals("")) {
                break;
            } else if (ElementFieldValidator.metersAboveSeaLevel(metAbSeaLvl)) {
                element.setMetersAboveSeaLevel(Integer.parseInt(metAbSeaLvl));
                break;
            } else {
                clientInteractor.sendString("Invalid meters above sea level input, please try again");
            }
        }
    }
    /** Method which receives climate*/
    private void receiveClimate() {
        while (true) {
            clientInteractor.sendString("Enter climate (MONSOON | HUMIDCONTINENTAL | MEDITERRANIAN | TUNDRA): ");
            String climate = scan().toUpperCase();
            if (climate.equals("exit".toUpperCase())) {
                exit = 1;
                break;
            } else if (ElementFieldValidator.climate(climate)) {
                element.setClimate(Climate.valueOf(climate));
                break;
            } else {
                clientInteractor.sendString("Invalid climate input, please try again");
            }
        }
    }
    /** Method which receives government*/
    private void receiveGovernment() {
        while (true) {
            clientInteractor.sendString("Enter government (KRITARCHY | PUPPET_STATE | JUNTA): ");
            String government = scan().toUpperCase();
            if (government.equals("exit".toUpperCase())) {
                exit = 1;
                break;
            } else if (government.equals("")) {
                break;
            } else if (ElementFieldValidator.government(government)) {
                element.setGovernment(Government.valueOf(government));
                break;
            } else {
                clientInteractor.sendString("Invalid government input, please try again");
            }
        }
    }
    /** Method which receives standardOfLiving*/
    private void receiveStandardOfLiving() {
        while (true) {
            clientInteractor.sendString("Enter standard of living (VERY_HIGH | HIGH | LOW | VERY_LOW): ");
            String stanOfLvng = scan().toUpperCase();
            if (stanOfLvng.equals("exit".toUpperCase())) {
                exit = 1;
                break;
            } else if (stanOfLvng.equals("")) {
                break;
            } else if (ElementFieldValidator.standardOfLiving(stanOfLvng)) {
                element.setStandardOfLiving(StandardOfLiving.valueOf(stanOfLvng));
                break;
            } else {
                clientInteractor.sendString("Invalid standard of leaving input, please try again");
            }
        }
    }
    /** Method which receives governor*/
    private void receiveGovernor() {
        Human governor = new Human();
        while (true) {
            clientInteractor.sendString("Enter governor: ");
            receiveGovernorsName(governor);
            if (exit == 1) {break;}
            receiveGovernorsAge(governor);
            if (exit == 1) {break;}
            while (true) {
                clientInteractor.sendString("Would you like to enter a date of birth? [y/n]");
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
                    clientInteractor.sendString("Invalid input, try again");
                }
            }
            break;
        }
    }
    /** Method which receives governor's name*/
    private void receiveGovernorsName(Human governor) {
        while (true) {
            clientInteractor.sendString("Enter name: ");
            String name = scan();
            if (name.equals("exit")) {
                exit = 1;
                break;
            } else if (ElementFieldValidator.name(name)) {
                governor.setName(name);
                break;
            } else {
                clientInteractor.sendString("Invalid name input, please try again");
            }
        }
    }
    /** Method which receives governor's age*/
    private void receiveGovernorsAge(Human governor) {
        while (true) {
            clientInteractor.sendString("Enter age: ");
            String age = scan();
            if (age.equals("exit")) {
                exit = 1;
                break;
            } else if (age.equals("")) {
                break;
            } else if (ElementFieldValidator.age(age)) {
                governor.setAge(Integer.parseInt(age));
                break;
            } else {
                clientInteractor.sendString("Invalid age input, please try again");
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
            clientInteractor.sendString("Enter birthday: ");
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
                    clientInteractor.sendString("Invalid birthday input: date of birth must match age");
                }
            } catch (DateTimeException e) { //проверяет "неочевидные" ошибки(високосные года, сколько дней в каком месяце)
                clientInteractor.sendString("Invalid date input, please try again");
            }
        }
    }

    /** Method which receives dateComponent to put it to the birthday field
     * @param dateComponent - year | month | day | hour | second
     * @return int representation of dateComponent
     */
    private int receiveDateComponent(DateComponent dateComponent) {
        while (true) {
            clientInteractor.sendString("Enter " + dateComponent.toString().toLowerCase() + ":");
            String strDateComponent = scan();
            if (strDateComponent.equals("exit")) {
                exit = 1;
                break;
            } else if (ElementFieldValidator.dateComponent(strDateComponent, dateComponent)) {
                return Integer.parseInt(strDateComponent);
            } else {
                clientInteractor.sendString("Invalid " + dateComponent.toString().toLowerCase() + " input, please try again");
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
                clientInteractor.sendString(splittedScript[fromWhichLine]);
                return DeleteSpaces.delete(splittedScript[fromWhichLine]);
            } else {
                return "exit";
            }
        } catch (IOException e) {
            clientInteractor.sendString(e.getMessage());
        }
        return "";
    }
    public int getExit() {return exit;}
}