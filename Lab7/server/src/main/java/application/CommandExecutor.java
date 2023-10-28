package application;

import commands.*;

/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that executes commands
 */
public class CommandExecutor {
    /** Field: array of commands*/
    private final Command[] commands;
    public CommandExecutor(CollectionManager collectionManager) {
        SignIn signIn = new SignIn();
        SignUp signUp = new SignUp();
        Info info = new Info(collectionManager);
        Show show = new Show(collectionManager);
        Insert insert = new Insert(collectionManager);
        Update update = new Update(collectionManager);
        RemoveKey remove_key = new RemoveKey(collectionManager);
        Clear clear = new Clear(collectionManager);
        ExecuteScript execute_script = new ExecuteScript(this);
        RemoveLower remove_lower = new RemoveLower(collectionManager);
        ReplaceIfGreater replace_if_greater = new ReplaceIfGreater(collectionManager);
        RemoveGreaterKey remove_greater_key = new RemoveGreaterKey(collectionManager);
        RemoveAnyByGovernment remove_any_by_government = new RemoveAnyByGovernment(collectionManager);
        FilterByClimate filter_by_climate = new FilterByClimate(collectionManager);
        PrintFieldDescendingClimate print_field_descending_climate = new PrintFieldDescendingClimate(collectionManager);
        this.commands = new Command[] {signIn, signUp, info, show, insert, update, remove_key, clear, execute_script, remove_lower,
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
                i.setClientInteractor(command.getClientInteractor());
                i.setArgument(command.getArgument());
                i.setUsername(command.getUsername());
                i.setElement(command.getElement());
                if ((i.getElement() == null) && Command.requiresElement(i.getType())) i.executingFromScript = true;
                i.execute();
                break;
            }
        }
    }
}