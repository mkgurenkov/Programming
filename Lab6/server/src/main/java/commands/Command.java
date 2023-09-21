package commands;

import data.City;
import helperClasses.CollectionManager;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Storage class for command
 */
@XmlRootElement(name = "command")
public class Command {
    /** Field which shows if command was executed from script*/
    public boolean executingFromScript = false;
    /** Field which keeps command type*/
    private String type;
    /** Field which keeps command City type argument*/
    private City element;
    /** Field which keeps command String type argument*/
    private String argument;
    /** Constructor*/
    public Command() {}
    /**
     * Setter for the field "type"
     * @param type - command type
     */
    public void setType(String type) {this.type = type;}
    /**
     * Setter for the field "argument"
     * @param argument - command argument
     */
    public void setArgument(String argument) {this.argument = argument;}
    /**
     * Setter for the field "element"
     * @param element - command argument, instance of the class City
     * @see data.City
     */
    public void setElement(City element) {this.element = element;}
    /**
     * Getter for the field "type"
     * @return type
     */
    @XmlElement
    public String getType() {return type;}
    /**
     * Getter for the field "element"
     * @return element
     */
    @XmlElement
    public City getElement() {return element;}
    /**
     * Getter for the field "argument"
     * @return argument
     */
    @XmlElement
    public String getArgument() {return argument;}
    /**
     * Execute method
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public void execute(CollectionManager collectionManager) {}
    /**
     * Method, which says if command requires element (object of the City class) as the argument
     * @param commandType - type of the command
     * @return command requires element
     */
    public static boolean requiresElement(String commandType) {
        String[] commandsRequiresElement = new String[]{"replace_if_greater", "insert", "update", "remove_lower"}; //перечень команд, требующих элемент в качестве аргумента
        return Arrays.asList(commandsRequiresElement).contains(commandType);
    }
}
