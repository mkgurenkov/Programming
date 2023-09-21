package helperClasses;

import commands.*;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that executes commands
 */
public class CommandExecutor {
    /** Field: array of commands*/
    private final Command[] commands;
    /**
     * Field collectionManager
     * @see helperClasses.CollectionManager
     */
    private final CollectionManager collectionManager;
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public CommandExecutor(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        InfoCommand info = new InfoCommand();
        ShowCommand show = new ShowCommand();
        InsertCommand insert = new InsertCommand();
        UpdateCommand update = new UpdateCommand();
        Remove_keyCommand remove_key = new Remove_keyCommand();
        ClearCommand clear = new ClearCommand();
        Execute_scriptCommand execute_script = new Execute_scriptCommand();
        Remove_lowerCommand remove_lower = new Remove_lowerCommand();
        Replace_if_greaterCommand replace_if_greater = new Replace_if_greaterCommand();
        Remove_greater_keyCommand remove_greater_key = new Remove_greater_keyCommand();
        Remove_any_by_governmentCommand remove_any_by_government = new Remove_any_by_governmentCommand();
        Filter_by_climateCommand filter_by_climate = new Filter_by_climateCommand();
        Print_field_descending_climateCommand print_field_descending_climate = new Print_field_descending_climateCommand();
        this.commands = new Command[] {info, show, insert, update, remove_key, clear, execute_script, remove_lower,
                replace_if_greater, remove_greater_key, remove_any_by_government, filter_by_climate, print_field_descending_climate};
    }
    /**
     * Execute method: executes command which gets into the method
     * @param command - instance of the class Command
     * @see commands.Command
     */
    public void execute(Command command) {
        for (Command i : commands) {
            if (i.getType().equals(command.getType())) {
                i.setArgument(command.getArgument());
                i.setElement(command.getElement());
                if ((i.getElement() == null) && Command.requiresElement(i.getType())) i.executingFromScript = true;
                i.execute(collectionManager);
                break;
            }
        }
    }
}