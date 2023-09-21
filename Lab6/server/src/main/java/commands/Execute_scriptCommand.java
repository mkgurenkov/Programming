package commands;

import helperClasses.*;
import java.io.*;
import java.util.Stack;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "execute_script" command
 */
public class Execute_scriptCommand extends Command {
    /** Stack collection for keeping a filepaths of scripts to check presence of recursion */
    private Stack<String> scripts = new Stack<>();
    /** Field with the help of which execute() method can get out of the recursion that occurs when checking scripts*/
    private int exit;
    /** Constructor*/
    public Execute_scriptCommand() {this.setType("execute_script");}
    /**
     * Execute method: executes script
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public void execute(CollectionManager collectionManager) {
        ElementReceiver.pathToFile = super.getArgument();
        exit = 0;
        try {
            FileReader reader = new FileReader(super.getArgument());
            StringBuilder stringBuilder = new StringBuilder();
            int c;
            while((c = reader.read())!=-1) {
                stringBuilder.append((char)c);
            }
            String script = stringBuilder.toString();
            String[] splittedScript = script.split("\\n");
            scripts.push(super.getArgument());
            if (getRecursionDepth() < 100) {
                for (int i = 0; i < splittedScript.length; i ++) {
                    if (exit == 0) {
                        splittedScript[i] = DeleteSpaces.delete(splittedScript[i]);
                        RequestValidator validator = new RequestValidator(splittedScript[i]);
                        if (validator.requestIsValid()) {
                            String[] splittedCommand = splittedScript[i].split(" ");
                            Command command = new Command();
                            command.setType(splittedCommand[0]);
                            if (splittedCommand.length > 1) {
                                command.setArgument(splittedCommand[1]);
                            }
                            if (Command.requiresElement(command.getType())) {
                                ElementReceiver.fromWhichLine = i;
                                collectionManager.getCommandExecutor().execute(command);
                                i = ElementReceiver.fromWhichLine;
                            } else {
                                collectionManager.getCommandExecutor().execute(command);
                            }
                        }
                    }
                }
            } else {
                InteractionWithClient.sendMessage("The maximum recursion depth has been reached");
                exit = 1;
            }
            scripts.pop();
        } catch (IOException e) {
            InteractionWithClient.sendMessage(e.getMessage());
        }
    }
    /**
     * Method which gets recursion depth
     * @return recursion depth
     */
    private int getRecursionDepth() {
        for (String i : scripts) {
            int counter = 0;
            for (String j : scripts) {
                if (j.equals(i)) {
                    counter ++;
                }
            }
            if (counter > 1) {
                return counter - 1;
            }
        }
        return 0;
    }
}
