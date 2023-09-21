package commands.abstracts;

import helperClasses.CollectionManager;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Superclass for all commands with arguments
 */
public abstract class CommandWithArg<T> extends Command {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public CommandWithArg(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method for commands with arguments*/
    public abstract void execute(T arg);
}
