package commands.abstracts;

import helperClasses.CollectionManager;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Superclass for all commands
 */
public abstract class Command {
    /** Field with which the command interacts with the collection*/
    public final CollectionManager collectionManager;
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public Command(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    /** Overridden toString() method to convert an instance of the subclass of Command class into a string representation*/
    @Override
    public abstract String toString();
}
