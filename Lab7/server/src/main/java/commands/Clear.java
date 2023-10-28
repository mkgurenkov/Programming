package commands;

import application.CollectionManager;
import externalConnections.InteractionWithDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "clear" command
 */
public class Clear extends Command {
    /** Constructor*/
    public Clear(CollectionManager collectionManager) {
        super(collectionManager);
        this.setType("clear");
    }
    synchronized public void execute() {
        PreparedStatement st = null;
        try {
            st = InteractionWithDatabase.getConnection().prepareStatement("DELETE FROM collection WHERE username = ?");
            st.setString(1, getUsername());
            st.executeUpdate();
            getClientInteractor().sendString("Collection has been cleared");
            getCollectionManager().loadCollection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
