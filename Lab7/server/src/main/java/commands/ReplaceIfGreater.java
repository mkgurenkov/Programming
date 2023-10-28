package commands;

import application.CollectionManager;
import data.City;
import externalConnections.InteractionWithDatabase;
import otherUtils.ElementReceiver;
import validators.ElementValidator;
import validators.ElementFieldValidator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "replace_if_greater" command
 */
public class ReplaceIfGreater extends Command {
    /** Constructor*/
    public ReplaceIfGreater(CollectionManager collectionManager) {
        super(collectionManager);
        this.setType("replace_if_greater");
    }
    synchronized public void execute() {

        Hashtable<Integer, City> collection = getCollectionManager().getCollection();
        City element;
        if (!executingFromScript) {
            element = super.getElement();
        } else {
            ElementReceiver elementReceiver = new ElementReceiver(collection, getClientInteractor());
            element = elementReceiver.receive();
        }
        try {
            PreparedStatement st = InteractionWithDatabase.getConnection().prepareStatement("DELETE FROM collection WHERE area < ? and key = ? AND username = ?");
            st.setFloat(1, element.getArea());
            st.setInt(2, Integer.parseInt(getArgument()));
            st.setString(3, getUsername());
            int a = st.executeUpdate();
            getClientInteractor().sendString(a + " elements has been removed");
            getCollectionManager().loadCollection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

