package commands;

import commands.abstracts.CommandWithoutArg;
import helperClasses.CollectionManager;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "exit" command
 */
public class ExitCommand extends CommandWithoutArg {
    /**
     * Constructor
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public ExitCommand(CollectionManager collectionManager) {super(collectionManager);}
    /** Execute method: exits program*/
    @Override
    public void execute() {
        collectionManager.interactiveModeOff();
    }
    /** Overridden toString() method to convert an instance of the ExitCommand class into a string representation*/
    @Override
    public String toString() {return "exit";}
}
