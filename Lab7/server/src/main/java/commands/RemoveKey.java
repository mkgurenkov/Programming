package commands;

import application.CollectionManager;
import data.City;
import externalConnections.InteractionWithDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "remove_key" command
 */
public class RemoveKey extends Command {
    /** Constructor*/
    public RemoveKey(CollectionManager collectionManager) {
        super(collectionManager);
        this.setType("remove_key");
    }
    synchronized public void execute() {
        try {
            PreparedStatement st = InteractionWithDatabase.getConnection().prepareStatement("DELETE FROM collection WHERE key = ? AND username = ?");
            st.setInt(1, Integer.parseInt(getArgument()));
            st.setString(2, getUsername());
            int a = st.executeUpdate();
            if (a != 0) {
                getClientInteractor().sendString("Element has been removed");
            } else {
                getClientInteractor().sendString("There is no element with such key");
            }
            getCollectionManager().loadCollection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
