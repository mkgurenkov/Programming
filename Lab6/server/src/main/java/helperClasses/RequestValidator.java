package helperClasses;

import commands.HelpCommand;
import helperClasses.checkers.CheckCommandFormat;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class, which checks if the input from console is valid (if the command input is correct)
 */
public class RequestValidator {
    /** Field which stores the input from console*/
    private final String request;
    /** Field which stores the output of Help command*/
    private final String helpOutput;
    /**
     * Constructor
     * @param request - input from console
     */
    public RequestValidator(String request) {
        this.request = request;
        HelpCommand help = new HelpCommand();
        this.helpOutput = help.getOutput();
    }
    /**
     * Method, which checks if the input from console is valid (if the command input is correct)
     * @return requestIsValid
     */
    public boolean requestIsValid() {
        String[] splittedInput = request.split(" ");
        String commandName = splittedInput[0];
        if (CheckCommandFormat.commandFormat(request)) {
            return true;
        } else {
            if (!request.equals("")) {
                for (String i : helpOutput.split("\n")) {
                    String[] iSplitted = i.split(" ");
                    if (iSplitted[0].equals(commandName)) {
                        InteractionWithClient.sendMessage("invalid command input");
                        InteractionWithClient.sendMessage(i);
                        return false;
                    }
                }
                InteractionWithClient.sendMessage(commandName + " " + "(command not found)");
                return false;
            }
            return false;
        }
    }
}