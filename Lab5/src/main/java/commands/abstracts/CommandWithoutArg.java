package commands.abstracts;

import helperClasses.CollectionManager;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Superclass for all commands without arguments
 */
public abstract class CommandWithoutArg extends Command {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public CommandWithoutArg(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method for commands without arguments*/
    public abstract void execute();
}