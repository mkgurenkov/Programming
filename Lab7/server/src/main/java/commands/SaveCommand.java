package commands;

import helperClasses.CollectionManager;
import helperClasses.CollectionParser;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "save" command
 */
public class SaveCommand extends Command {
    /** Constructor*/
    public SaveCommand() {this.setType("save");}
    /**
     * Execute method: saves collection in the xml file
     * @param collectionManager - instance of the class CollectionManager
     * @see helperClasses.CollectionManager
     */
    public void execute(CollectionManager collectionManager) {
        CollectionParser parser = new CollectionParser(collectionManager.getCollection(), collectionManager.getPathToFile());
        parser.marshalToXml();
    }
}
