package helperClasses;

import commands.*;
import commands.abstracts.Command;
import commands.abstracts.CommandWithArg;
import commands.abstracts.CommandWithoutArg;
import helperClasses.checkers.CheckCommandFormat;

/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that executes commands and displays exceptions
 */
public class CommandExecutor {
    /** Field: string output of the Help command*/
    private final String helpOutput;
    /** Field: array of commands*/
    private final Command[] commands;

    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public CommandExecutor(CollectionManager collectionManager) {
        HelpCommand help = new HelpCommand(collectionManager);
        InfoCommand info = new InfoCommand(collectionManager);
        ShowCommand show = new ShowCommand(collectionManager);
        InsertCommand insert = new InsertCommand(collectionManager);
        UpdateCommand update = new UpdateCommand(collectionManager);
        Remove_keyCommand remove_key = new Remove_keyCommand(collectionManager);
        ClearCommand clear = new ClearCommand(collectionManager);
        SaveCommand save = new SaveCommand(collectionManager);
        Execute_scriptCommand execute_script = new Execute_scriptCommand(collectionManager);
        ExitCommand exit = new ExitCommand(collectionManager);
        Remove_lowerCommand remove_lower = new Remove_lowerCommand(collectionManager);
        Replace_if_greaterCommand replace_if_greater = new Replace_if_greaterCommand(collectionManager);
        Remove_greater_keyCommand remove_greater_key = new Remove_greater_keyCommand(collectionManager);
        Remove_any_by_governmentCommand remove_any_by_government = new Remove_any_by_governmentCommand(collectionManager);
        Filter_by_climateCommand filter_by_climate = new Filter_by_climateCommand(collectionManager);
        Print_field_descending_climateCommand print_field_descending_climate = new Print_field_descending_climateCommand(collectionManager);
        this.commands = new Command[] {help, info, show, insert, update, remove_key, clear, save, execute_script, exit, remove_lower,
                replace_if_greater, remove_greater_key, remove_any_by_government, filter_by_climate, print_field_descending_climate};
        this.helpOutput = help.getOutput();
    }

    /** Method which executes commands
     * @param input - command and argument (if command has it)
     */
    public void execute(String input) {
        input = DeleteSpaces.delete(input);
        String[] splittedInput = input.split(" ");
        String commandName = splittedInput[0];
        String commandArg;
        if (splittedInput.length != 1) {
            commandArg = splittedInput[1];
        } else {
            commandArg = "";
        }
            if (CheckCommandFormat.commandFormat(input)) {
                for (Command  i : commands) {
                    if (i.toString().equals(commandName)) {
                        if (i instanceof CommandWithArg) {
                            try {
                                ((CommandWithArg<Integer>) i).execute(Integer.parseInt(commandArg));
                            } catch (NumberFormatException e) {
                                ((CommandWithArg<String>) i).execute(commandArg);
                            }
                        }
                        if (i instanceof CommandWithoutArg) {((CommandWithoutArg) i).execute();}
                    }
                }
            } else {
                if (!input.equals("")) {
                    int commandExists = 0;
                    for (String i : helpOutput.split("\n")) {
                        String[] iSplitted = i.split(" ");
                        if (iSplitted[0].equals(commandName)) {
                            System.out.println("invalid command input");
                            System.out.println(i);
                            commandExists++;
                            break;
                        }
                    }
                    if (commandExists == 0) {
                        System.out.println(commandName + " " + "(command not found)");
                    }
                }
            }
    }
}