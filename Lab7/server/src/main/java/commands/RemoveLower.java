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
 * Class that embodies "remove_lower" command
 */
public class RemoveLower extends Command {
    /** Constructor*/
    public RemoveLower(CollectionManager collectionManager) {
        super(collectionManager);
        this.setType("remove_lower");
    }
    synchronized public void execute() {
        Hashtable<Integer, City> collection = getCollectionManager().getCollection();
        Hashtable<Integer, City> collection2 = new Hashtable<>(collection);
        int numberOfElementsBefore = collection2.size();
        City element;
        if (!executingFromScript) {
            element = super.getElement();
        } else {
            ElementReceiver elementReceiver = new ElementReceiver(collection, getClientInteractor());
            element = elementReceiver.receive();
        }
        try {
            PreparedStatement st = InteractionWithDatabase.getConnection().prepareStatement("DELETE FROM collection WHERE area < ? AND username = ?");
            st.setFloat(1, element.getArea());
            st.setString(2, getUsername());
            int a = st.executeUpdate();
            getClientInteractor().sendString(a + " elements has been removed");
            getCollectionManager().loadCollection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

