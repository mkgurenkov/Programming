package commands;

import application.CollectionManager;
import data.City;
import data.Government;
import externalConnections.InteractionWithDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "remove_any_by_government" command
 */
public class RemoveAnyByGovernment extends Command {
    /** Constructor*/
    public RemoveAnyByGovernment(CollectionManager collectionManager) {
        super(collectionManager);
        this.setType("remove_any_by_government");
    }
    synchronized public void execute() {
        PreparedStatement st = null;
        try {
            st = InteractionWithDatabase.getConnection().prepareStatement("DELETE FROM collection WHERE id = (SELECT DISTINCT id FROM collection WHERE username = ? AND government = ?::GOVERNMENT LIMIT 1)");
            st.setString(1, getUsername());
            st.setString(2, getArgument());
            int a = st.executeUpdate();
            if (a == 1) {
                getClientInteractor().sendString("Element has been removed");
            }
            getCollectionManager().loadCollection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}