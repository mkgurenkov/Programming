package commands;

import application.CollectionManager;
import data.City;
import externalConnections.InteractionWithDatabase;
import otherUtils.ElementReceiver;
import validators.ElementValidator;
import validators.ElementFieldValidator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Hashtable;

/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "insert" command
 */

public class Insert extends Command {
    /** Constructor*/
    public Insert(CollectionManager collectionManager) {
        super(collectionManager);
        this.setType("insert");
    }
    synchronized public void execute() {
        Hashtable<Integer, City> collection = getCollectionManager().getCollection();
        City element;
        if (!executingFromScript) {
            element = super.getElement();
        } else {
            ElementReceiver elementReceiver = new ElementReceiver(collection, getClientInteractor());
            element = elementReceiver.receive();
            if (elementReceiver.getExit() == 1) {
                getClientInteractor().sendString("The entry of the element was completed incorrectly, the command was not executed");
                return;
            }
        }
        if (collection.containsKey(Integer.parseInt(super.getArgument()))) {
            try {
                PreparedStatement updateHuman = InteractionWithDatabase.getConnection().prepareStatement("UPDATE human SET name = ?, age = ?, birthday = ? WHERE element_id = (SELECT id FROM collection WHERE key = ?");
                PreparedStatement updateCoordinates = InteractionWithDatabase.getConnection().prepareStatement("UPDATE coordinates SET x = ?, y = ? WHERE element_id = (SELECT id FROM collection WHERE key = ?)");
                PreparedStatement updateElement = InteractionWithDatabase.getConnection().prepareStatement("UPDATE collection SET name = ?, " +
                        "creation_date = ?::DATE, creation_date_hash = ?, area = ?, population = ?, meters_above_sea_level = ?, climate = ?::CLIMATE, government = ?::GOVERNMENT, " +
                        "standard_of_living = ?::STANDARD_OF_LIVING WHERE key = ?");
                int key = Integer.parseInt(super.getArgument());
                updateElement.setString(1, element.getName());
                updateElement.setString(2, element.getCreationDate().toString());
                updateElement.setInt(3, element.getCreationDateHash());
                updateElement.setFloat(4, element.getArea());
                updateElement.setInt(5, element.getPopulation());
                updateElement.setInt(6, element.getMetersAboveSeaLevel());
                updateElement.setString(7, String.valueOf(element.getClimate()));
                updateElement.setString(8, String.valueOf(element.getGovernment()));
                updateElement.setString(9, String.valueOf(element.getStandardOfLiving()));
                updateElement.setInt(10, key);

                updateHuman.setString(1, element.getGovernor().getName());
                updateHuman.setInt(2, element.getGovernor().getAge());
                if (element.getGovernor().getBirthday() != null) {
                    updateHuman.setTimestamp(3, Timestamp.valueOf(element.getGovernor().getBirthday()));
                } else {
                    updateHuman.setTimestamp(3, null);
                }
                updateHuman.setInt(4, key);

                updateCoordinates.setLong(1, element.getCoordinates().getX());
                updateCoordinates.setLong(2, element.getCoordinates().getY());
                updateCoordinates.setInt(3, key);

                updateHuman.executeUpdate();
                updateCoordinates.executeUpdate();
                updateElement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            getClientInteractor().sendString("Element has been updated successfully");
        } else {
            try {
                PreparedStatement insertHuman = InteractionWithDatabase.getConnection().prepareStatement("INSERT INTO human (name, age, birthday, element_id) VALUES (?, ?, ?, (SELECT id FROM collection WHERE key = ?))");
                PreparedStatement insertCoordinates = InteractionWithDatabase.getConnection().prepareStatement("INSERT INTO coordinates (x, y, element_id) VALUES (?, ?, (SELECT id FROM collection WHERE key = ?))");
                PreparedStatement insertElement = InteractionWithDatabase.getConnection().prepareStatement("INSERT INTO collection (username, key, name, creation_date, creation_date_hash, area, population, " +
                        "meters_above_sea_level, climate, government, standard_of_living) VALUES (?, ?, ?, ?::DATE, ?, ?, ?, ?, ?::CLIMATE, ?::GOVERNMENT, ?::STANDARD_OF_LIVING)");
                int key = Integer.parseInt(super.getArgument());
                insertElement.setString(1, getUsername());
                insertElement.setInt(2, key);
                insertElement.setString(3, element.getName());
                insertElement.setString(4, element.getCreationDate().toString());
                insertElement.setInt(5, element.getCreationDateHash());
                insertElement.setFloat(6, element.getArea());
                insertElement.setInt(7, element.getPopulation());
                insertElement.setInt(8, element.getMetersAboveSeaLevel());
                insertElement.setString(9, String.valueOf(element.getClimate()));
                insertElement.setString(10, String.valueOf(element.getGovernment()));
                insertElement.setString(11, String.valueOf(element.getStandardOfLiving()));

                insertHuman.setString(1, element.getGovernor().getName());
                insertHuman.setInt(2, element.getGovernor().getAge());
                if (element.getGovernor().getBirthday() != null) {
                    insertHuman.setTimestamp(3, Timestamp.valueOf(element.getGovernor().getBirthday()));
                } else {
                    insertHuman.setTimestamp(3, null);
                }
                insertHuman.setInt(4, key);

                insertCoordinates.setLong(1, element.getCoordinates().getX());
                insertCoordinates.setLong(2, element.getCoordinates().getY());
                insertCoordinates.setInt(3, key);

                insertElement.executeUpdate();
                insertHuman.executeUpdate();
                insertCoordinates.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            getClientInteractor().sendString("Element has been added successfully");
        }
        getCollectionManager().loadCollection();
    }
}
