package commands;

import commands.abstracts.CommandWithArg;
import helperClasses.CollectionManager;
import helperClasses.EnterElement;
import java.io.*;
import java.util.Stack;

/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "execute_script" command
 */
public class Execute_scriptCommand extends CommandWithArg<String> {
    /** Stack collection for keeping a filepaths of scripts to check presence of recursion */
    private Stack<String> scripts = new Stack<>();
    /** Field with the help of which execute() method can get out of the recursion that occurs when checking scripts*/
    private int exitMethod;
    private int o = 0;
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public Execute_scriptCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }
    /** Execute method: executes script if recursion is not detected
     * @param pathToFile - path to the file with script
     */
    @Override
    public void execute(String pathToFile) {
        EnterElement.fromConsole = false;
        EnterElement.pathToFile = pathToFile;
        exitMethod = 0;
        try {
            FileReader reader = new FileReader(pathToFile);
            StringBuilder stringBuilder = new StringBuilder();
            int c;
            while((c = reader.read())!=-1) {
                stringBuilder.append((char)c);
            }
            String script = stringBuilder.toString();
            String[] splittedScript = script.split("\\n");
            scripts.push(pathToFile);
            if (getRecursionDepth() < 100) {
                for (int i = 0; i < splittedScript.length; i ++) {
                    if (exitMethod == 0) {
                        String[] splittedCommand = splittedScript[i].split(" ");
                        if (splittedCommand[0].equals("update") | splittedCommand[0].equals("insert") | splittedCommand[0].equals("remove_lower") | splittedCommand[0].equals("replace_if_greater")) {
                            EnterElement.fromWhichLine = i;
                            collectionManager.getCommandExecutor().execute(splittedScript[i]);
                            i = EnterElement.fromWhichLine;
                        } else {
                            collectionManager.getCommandExecutor().execute(splittedScript[i]);
                        }
                    }
                }
            } else {
                System.out.println("The maximum recursion depth has been reached");
                exitMethod = 1;
            }
            scripts.pop();
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
    }
    /** Overridden toString() method to convert an instance of the Execute_scriptCommand class into a string representation*/
    @Override
    public String toString() {return "execute_script";}
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
