package validators;

import commands.Help;

import java.util.Arrays;

/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class, which checks if the input from console is valid (if the command input is correct)
 */
public class CommandInputValidator {
    /** Field which stores the input from console*/
    private final String request;
    /** Field which stores the output of Help command*/
    private final String helpOutput;
    /**
     * Constructor
     * @param request - input from console
     */
    public CommandInputValidator(String request) {
        this.request = request;
        Help help = new Help();
        this.helpOutput = help.getOutput();
    }
    /**
     * Method, which checks if the input from console is valid (if the command input is correct)
     * @return requestIsValid
     */
    public void printException() {
        String[] splittedInput = request.split(" ");
        String commandName = splittedInput[0];
        if (!commandIsValid() && !request.equals("")) {
            boolean commandExists = false;
            for (String i : helpOutput.split("\n")) {
                String[] iSplitted = i.split(" ");
                if (iSplitted[0].equals(commandName)) {
                    System.out.println("invalid command input");
                    System.out.println(i);
                    commandExists = true;
                    break;
                }
            }
            if (!commandExists) {System.out.println(commandName + " " + "(command not found)");}
        }
    }
    public boolean commandIsValid() {
        String[] splittedCommand = request.split(" ");
        if (splittedCommand.length == 1) {
            return noArgsCommandFormat(request);
        } else if (splittedCommand.length == 2) {
            try {
                Integer.parseInt(splittedCommand[1]);
                return intArgCommandFormat(request);
            } catch (NumberFormatException e) {
                return strArgsCommandFormat(splittedCommand[0]);
            }
        } else {return false;}
    }
    /** private method for checking the correction of commands with no arguments input
     * @return boolean commandFormatIsCorrect
     * @param command - command
     */
    private boolean noArgsCommandFormat(String command) {
        String[] noArgsCommands = new String[]{"help", "info", "show", "clear", "exit", "remove_lower", "print_field_descending_climate"};
        if (Arrays.asList(noArgsCommands).contains(command)) {
            return true;
        } else {return false;}
    }
    /** private method for checking the correction of commands with int arguments input
     * @return boolean commandFormatIsCorrect
     * @param command - command
     */
    private boolean intArgCommandFormat(String command) {
        String[] intArgsCommands = new String[]{"insert", "update", "remove_key", "replace_if_greater", "remove_greater_key"};
        String[] splittedCommand = command.split(" ");
        if (Arrays.asList(intArgsCommands).contains(splittedCommand[0])) {
            try {
                Integer.parseInt(splittedCommand[1]);
                return true;
            } catch (NumberFormatException e) {return false;}
        } else {return false;}
    }
    /** private method for checking the correction of commands with String arguments input
     * @return boolean commandFormatIsCorrect
     * @param command - command
     */
    private static boolean strArgsCommandFormat(String command) {
        String[] strArgsCommands = new String[]{"execute_script", "remove_any_by_government", "filter_by_climate"};
        if (Arrays.asList(strArgsCommands).contains(command)) {
            return true;
        } else {return false;}
    }
}