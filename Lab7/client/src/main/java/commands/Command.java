package commands;

import data.City;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.Arrays;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Storage class for command
 */
public class Command implements Serializable {
    private static final long serialVersionUID = 1L;
    /** Field which keeps command type*/
    private String type;
    /** Field which keeps command City type argument*/
    private City element;
    /** Field which keeps command String type argument*/
    private String argument;
    private String username;
    /** Constructor*/
    public Command() {}
    /**
     * Setter for the field "type"
     * @param type
     */
    public void setType(String type) {this.type = type;}
    public void setUsername(String username) {this.username = username;}
    /**
     * Setter for the field "argument"
     * @param argument
     */
    public void setArgument(String argument) {this.argument = argument;}
    /**
     * Setter for the field "element"
     * @param element
     */
    public void setElement(City element) {this.element = element;}
    /**
     * Getter for the field "type"
     * @return type
     */
    public String getUsername() {return username;}
    public String getType() {return type;}
    /**
     * Getter for the field "element"
     * @return element
     */
    public City getElement() {return element;}
    /**
     * Getter for the field "argument"
     * @return argument
     */
    public String getArgument() {return argument;}
    /**
     * Method, which says if command requires element (object of the City class) as the argument
     * @param commandType - type of the command
     * @return command requires element
     */
    public static boolean requiresElement(String commandType) {
        String[] commandsRequiresElement = new String[]{"replace_if_greater", "insert", "update", "remove_lower"}; //перечень команд, требующих элемент в качестве аргумента
        return Arrays.asList(commandsRequiresElement).contains(commandType);
    }
    public static boolean argumentIsEnum(String commandType) {
        String[] commandsWithEnumArg = new String[]{"filter_by_climate", "remove_any_by_government"}; //перечень команд, чьим аргументов является enum
        return Arrays.asList(commandsWithEnumArg).contains(commandType);
    }
    public static byte[] serialize(Command command) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(command);
            objectOutputStream.close();
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Command deserialize(byte[] serializedCommand) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedCommand);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Command command = (Command) objectInputStream.readObject();
            objectInputStream.close();
            return command;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
