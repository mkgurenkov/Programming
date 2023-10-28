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
 * Class that embodies "remove_greater_key" command
 */
public class RemoveGreaterKey extends Command {
    /** Constructor*/
    public RemoveGreaterKey(CollectionManager collectionManager) {
        super(collectionManager);
        this.setType("remove_greater_key");
    }
    synchronized public void execute() {
        try {
            PreparedStatement st = InteractionWithDatabase.getConnection().prepareStatement("DELETE FROM collection WHERE key > ? and username = ?");
            st.setInt(1, Integer.parseInt(getArgument()));
            st.setString(2, getUsername());
            int a = st.executeUpdate();
            getClientInteractor().sendString(a + " elements has been removed");
            getCollectionManager().loadCollection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

