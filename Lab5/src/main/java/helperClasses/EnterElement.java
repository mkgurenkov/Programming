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
 * Class, responsible for entering the City type command argument
 */
public class EnterElement {
    /** Field: City element*/
    private final City element;
    /** Field: scanner*/
    private final Scanner scanner;
    /** Field: collectionManager - instance of the class CollectionManager*/
    private final CollectionManager collectionManager;
    /** Field: exit - flag to interrupt the element input (enter()) method*/
    private int exit;
    /** Field: exit_program - flag to interrupt program execution*/
    private int exit_program;
    /** Field: fromConsole - flag which shows if method 'enter' was executed from console (or from script)*/
    public static boolean fromConsole;
    /** Field: pathToFile - field, which keeps path to the file with script, from which method 'enter' was executed*/
    public static String pathToFile;
    /** Field: fromWhichLine - field, which keeps an index of a line in file, from which input should be read*/
    public static int fromWhichLine;

    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     */
    public EnterElement(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.element = new City();
        this.scanner = new Scanner(System.in);
        this.exit = 0;
        this.exit_program = 0;
    }

    /** Main EnterElement class method: gets all the fields of the class from the user and assigns them to the returned element
     * @return City element - received element
     */
    public City enter() {
        System.out.println("Type \"exit\" to exit from element input");
        System.out.println("To enter \"null\"(0) values press \"Enter\"");
        while (true) {
            element.setCreationDate(LocalDate.now());
            element.setCreationDateHash(element.getCreationDate().hashCode());
            element.setId(generateId());
            enterName();
            if (exit == 1) {break;}
            enterCords();
            if (exit == 1) {break;}
            enterArea();
            if (exit == 1) {break;}
            enterPopulation();
            if (exit == 1) {break;}
            enterMetersAboveSeaLevel();
            if (exit == 1) {break;}
            enterClimate();
            if (exit == 1) {break;}
            enterGovernment();
            if (exit == 1) {break;}
            enterStandardOfLiving();
            if (exit == 1) {break;}
            enterGovernor();
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
    private void enterName() {
        while (true) {
            System.out.println("Enter name: ");
            String name = scan();
            name = DeleteSpaces.delete(name);
            if (name.equals("exit")) {
                exit = 1;
                break;
            } else if (CheckField.name(name)) {
                element.setName(name);
                break;
            } else {
                System.out.println("Invalid name input, please try again");
            }
        }
    }
    /** Method which receives coordinates*/
    private void enterCords() {
        Coordinates coordinates = new Coordinates();
        while (true) {
            System.out.println("Enter coordinates: ");
            enterCord(coordinates, Coordinate.X);
            if(exit == 1) {break;}
            enterCord(coordinates, Coordinate.Y);
            if(exit == 1) {break;}
            element.setCoordinates(coordinates);
            break;
        }
    }
    /** Method which receives coordinate*/
    private void enterCord(Coordinates coordinates, Coordinate cord) {
        while (true) {
            System.out.println("Enter " + cord.toString().toLowerCase() + ": ");
            String strCord = scan();
            strCord = DeleteSpaces.delete(strCord);
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
                System.out.println("Invalid coordinates input, please try again");
            }
        }
    }
    /** Method which receives area*/
    private void enterArea() {
        while (true) {
            System.out.println("Enter area: ");
            String area = scan();
            area = DeleteSpaces.delete(area);
            if (area.equals("exit")) {
                exit = 1;
                break;
            } else if (CheckField.area(area)) {
                element.setArea(Float.parseFloat(area));
                break;
            } else {
                System.out.println("Invalid area input, please try again");
            }

        }
    }
    /** Method which receives population*/
    private void enterPopulation() {
        while (true) {
            System.out.println("Enter population: ");
            String population = scan();
            population = DeleteSpaces.delete(population);
            if (population.equals("exit")) {
                exit = 1;
                break;
            } else if (CheckField.population(population)) {
                element.setPopulation(Integer.parseInt(population));
                break;
            } else {
                System.out.println("Invalid population input, please try again");
            }

        }
    }
    /** Method which receives metersAboveSeaLevel*/
    private void enterMetersAboveSeaLevel() {
        while (true) {
            System.out.println("Enter meters above sea level: ");
            String metAbSeaLvl = scan();
            metAbSeaLvl = DeleteSpaces.delete(metAbSeaLvl);
            if (metAbSeaLvl.equals("exit")) {
                exit = 1;
                break;
            } else if (metAbSeaLvl.equals("")) {
                break;
            } else if (CheckField.metersAboveSeaLevel(metAbSeaLvl)) {
                element.setMetersAboveSeaLevel(Integer.parseInt(metAbSeaLvl));
                break;
            } else {
                System.out.println("Invalid meters above sea level input, please try again");
            }
        }
    }
    /** Method which receives climate*/
    private void enterClimate() {
        while (true) {
            System.out.println("Enter climate (MONSOON | HUMIDCONTINENTAL | MEDITERRANIAN | TUNDRA): ");
            String climate = scan();
            climate = DeleteSpaces.delete(climate);
            if (climate.equals("exit")) {
                exit = 1;
                break;
            } else if (CheckField.climate(climate)) {
                element.setClimate(Climate.valueOf(climate));
                break;
            } else {
                System.out.println("Invalid climate input, please try again");
            }
        }
    }
    /** Method which receives government*/
    private void enterGovernment() {
        while (true) {
            System.out.println("Enter government (KRITARCHY | PUPPET_STATE | JUNTA): ");
            String government = scan();
            government = DeleteSpaces.delete(government);
            if (government.equals("exit")) {
                exit = 1;
                break;
            } else if (government.equals("")) {
                break;
            } else if (CheckField.government(government)) {
                element.setGovernment(Government.valueOf(government));
                break;
            } else {
                System.out.println("Invalid government input, please try again");
            }
        }
    }
    /** Method which receives standardOfLiving*/
    private void enterStandardOfLiving() {
        while (true) {
            System.out.println("Enter standard of living (VERY_HIGH | HIGH | LOW | VERY_LOW): ");
            String stanOfLvng = scan();
            stanOfLvng = DeleteSpaces.delete(stanOfLvng);
            if (stanOfLvng.equals("exit")) {
                exit = 1;
                break;
            } else if (stanOfLvng.equals("")) {
                break;
            } else if (CheckField.standardOfLiving(stanOfLvng)) {
                element.setStandardOfLiving(StandardOfLiving.valueOf(stanOfLvng));
                break;
            } else {
                System.out.println("Invalid standard of leaving input, please try again");
            }
        }
    }
    /** Method which receives governor*/
    private void enterGovernor() {
        Human governor = new Human();
        while (true) {
            System.out.println("Enter governor: ");
            enterGovernorsName(governor);
            if (exit == 1) {break;}
            enterGovernorsAge(governor);
            if (exit == 1) {break;}
            while (true) {
                System.out.println("Would you like to enter a date of birth? [y/n]");
                String response = scan();
                response = DeleteSpaces.delete(response);
                if (response.equals("y")) {
                    enterGovernorsBirthday(governor);
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
                    System.out.println("Invalid input, try again");
                }
            }
            break;
        }
    }
    /** Method which receives governor's name*/
    private void enterGovernorsName(Human governor) {
        while (true) {
            System.out.println("Enter name: ");
            String name = scan();
            name = DeleteSpaces.delete(name);
            if (name.equals("exit")) {
                exit = 1;
                break;
            } else if (CheckField.name(name)) {
                governor.setName(name);
                break;
            } else {
                System.out.println("Invalid name input, please try again");
            }
        }
    }
    /** Method which receives governor's age*/
    private void enterGovernorsAge(Human governor) {
        while (true) {
            System.out.println("Enter age: ");
            String age = scan();
            age = DeleteSpaces.delete(age);
            if (age.equals("exit")) {
                exit = 1;
                break;
            } else if (age.equals("")) {
                break;
            } else if (CheckField.age(age)) {
                governor.setAge(Integer.parseInt(age));
                break;
            } else {
                System.out.println("Invalid age input, please try again");
            }
        }
    }
    /** Method which receives governor's birthday*/
    private void enterGovernorsBirthday(Human governor) {
        LocalDateTime birthday;
        int year;
        int month;
        int day;
        int hour;
        int minute;
        while (true) {
            System.out.println("Enter birthday: ");
            year = enterDateComponent(DateComponent.YEAR);
            if (exit == 1) {break;}
            month = enterDateComponent(DateComponent.MONTH);
            if (exit == 1) {break;}
            day = enterDateComponent(DateComponent.DAY);
            if (exit == 1) {break;}
            hour = enterDateComponent(DateComponent.HOUR);
            if (exit == 1) {break;}
            minute = enterDateComponent(DateComponent.MINUTE);
            if (exit == 1) {break;}
            try {
                birthday = LocalDateTime.of(year, month, day, hour, minute);
                governor.setBirthday(birthday);
                long days = LocalDate.now().toEpochDay() - birthday.toLocalDate().toEpochDay(); //сколько дней прожил человек по факту
                long days2 = LocalDate.now().toEpochDay() - LocalDate.of(LocalDate.now().getYear() - governor.getAge(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()).toEpochDay();//ровно age губернатора в днях
                if (days >= days2) {
                    break;
                } else {
                    System.out.println("Invalid birthday input: date of birth must match age");
                }
            } catch (DateTimeException e) { //проверяет "неочевидные" ошибки(високосные года, сколько дней в каком месяце)
                System.out.println("Invalid date input, please try again");
            }
        }
    }

    /** Method which receives dateComponent to put it to the birthday field
     * @param dateComponent - year | month | day | hour | second
     * @return int representation of dateComponent
     */
    private int enterDateComponent(DateComponent dateComponent) {
        while (true) {
            System.out.println("Enter " + dateComponent.toString().toLowerCase() + ":");
            String strDateComponent = scan();
            strDateComponent = DeleteSpaces.delete(strDateComponent);
            if (strDateComponent.equals("exit")) {
                exit = 1;
                break;
            } else if (CheckField.dateComponent(strDateComponent, dateComponent)) {
                return Integer.parseInt(strDateComponent);
            } else {
                System.out.println("Invalid " + dateComponent.toString().toLowerCase() + " input, please try again");
            }
        }
        return 0;
    }

    /** Method which scans console input and raises the program's exit flag if the user clicked ctrl+d
     * @return String console input
     */
    private String scan() {
        if (fromConsole) {
            if (scanner.hasNextLine()) {
                return scanner.nextLine();
            } else {
                exit_program = 1;
                return "exit";
            }
        } else {
            try {
                FileReader reader = new FileReader(pathToFile);
                StringBuilder stringBuilder = new StringBuilder();
                int c;
                while((c = reader.read())!=-1) {
                    stringBuilder.append((char)c);
                }
                String script = stringBuilder.toString();
                String[] splittedScript = script.split("\\n");
                fromWhichLine ++;
                if (fromWhichLine < splittedScript.length) {
                    System.out.println(splittedScript[fromWhichLine]);
                    return splittedScript[fromWhichLine];
                } else {
                    return "exit";
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return "";
    }

    /** Method which gets the value of the program's exit flag
     * @return int exit_program
     */
    public int getExit_program() {
        return exit_program;
    }
}
