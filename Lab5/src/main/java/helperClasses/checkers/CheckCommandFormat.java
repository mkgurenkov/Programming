package helperClasses.checkers;

import java.util.Arrays;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class for checking the correction of command input format
 */
public class CheckCommandFormat {
    /** Method for checking the correction of all command input format
     * @return boolean commandFormatIsCorrect
     * @param command - command
     */
    public static boolean commandFormat(String command) {
        String[] splittedCommand = command.split(" ");
        if (splittedCommand.length == 1) {
            return noArgsCommandFormat(command);
        } else if (splittedCommand.length == 2) {
            try {
                Integer.parseInt(splittedCommand[1]);
                return intArgCommandFormat(command);
            } catch (NumberFormatException e) {
                return strArgsCommandFormat(splittedCommand[0]);
            }
        } else {return false;}
    }
    /** private method for checking the correction of commands with no arguments input
     * @return boolean commandFormatIsCorrect
     * @param command - command
     */
    private static boolean noArgsCommandFormat(String command) {
        String[] noArgsCommands = new String[]{"help", "info", "show", "clear", "save", "exit", "remove_lower", "print_field_descending_climate"};
        if (Arrays.asList(noArgsCommands).contains(command)) {
            return true;
        } else {return false;}
    }
    /** private method for checking the correction of commands with String arguments input
     * @return boolean commandFormatIsCorrect
     * @param command - command
     */
    private static boolean intArgCommandFormat(String command) {
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

