package externalConnections;

import data.*;
import validators.ElementValidator;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Hashtable;

public class InteractionWithDatabase {
    private static Connection connection;
    public static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", "s368069", "JZJeDQ3xRtE1GMHo");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean checkLoginDetails(String username, String passwordHash) {
        try {
            PreparedStatement selectUsers = connection.prepareStatement("SELECT * FROM users WHERE (username = ?) AND (password_hash = ?)");
            selectUsers.setString(1, username);
            selectUsers.setString(2, passwordHash);
            ResultSet usersSet = selectUsers.executeQuery();
            while (usersSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean checkUsernameAvailability(String username) {
        try {
            PreparedStatement selectUsers = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            selectUsers.setString(1, username);
            ResultSet usersSet = selectUsers.executeQuery();
            while (usersSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void addUser(String username, String passwordHash) {
        try {
            PreparedStatement add = connection.prepareStatement("INSERT INTO users (username, password_hash) VALUES (?, ?)");
            add.setString(1, username);
            add.setString(2, passwordHash);
            add.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Hashtable<Integer, City> loadCollection() {
        int counter = 0;
        Hashtable<Integer, City > collection = new Hashtable<>();
        try {
            Statement selectCollection = connection.createStatement();
            PreparedStatement selectCoordinates = connection.prepareStatement("SELECT * FROM coordinates WHERE element_id = ?");
            PreparedStatement selectHuman = connection.prepareStatement("SELECT * FROM human WHERE element_id = ?");
            ResultSet collectionSet = selectCollection.executeQuery("SELECT * FROM collection");
//            collectionSet.next();
            while (collectionSet.next()) {
                Integer id = collectionSet.getInt("id");

                selectCoordinates.setInt(1, id);
                ResultSet coordinatesSet = selectCoordinates.executeQuery();
                coordinatesSet.next();
                Long x = coordinatesSet.getLong("x");
                Long y = coordinatesSet.getLong("y");
                Coordinates coordinates = new Coordinates(x, y); //coordinates

                selectHuman.setInt(1, id);
                ResultSet humanSet = selectHuman.executeQuery();
                humanSet.next();
                String humanName = humanSet.getString("name");
                int age = humanSet.getInt("age");
                Timestamp birthday = humanSet.getTimestamp("birthday");
                Human human = new Human(); //human
                human.setName(humanName);
                human.setAge(age);
                if (birthday != null) {
                    human.setBirthday(birthday.toLocalDateTime());
                }

                String username = collectionSet.getString("username"); //username
                int key = collectionSet.getInt("key"); //key
                String name = collectionSet.getString("name"); //name
                Timestamp creation_date = collectionSet.getTimestamp("creation_date"); //creationDate
                int creationDateHash = collectionSet.getInt("creation_date_hash"); //creationDateHash
                float area = collectionSet.getFloat("area"); //area
                Integer population = collectionSet.getInt("population"); //population
                int metersAboveSeaLevel = collectionSet.getInt("meters_above_sea_level"); //population
                Climate climate = Climate.valueOf(collectionSet.getString("climate")); //climate
                Government government = Government.valueOf(collectionSet.getString("government")); //government
                StandardOfLiving standardOfLiving = StandardOfLiving.valueOf(collectionSet.getString("standard_of_living")); //standardOfLiving
                City city = new City(id, name, coordinates, creation_date.toLocalDateTime().toLocalDate(), area,
                        population, metersAboveSeaLevel, climate, government, standardOfLiving, human);
                city.setUsername(username);
                if (ElementValidator.checkFields(city, collection, false)) {
                    collection.put(key, city);
                } else {
                    counter ++;
                }
            }
            if (counter != 0) {System.out.println(counter + " elements were not upload");}
            return collection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void parseFile() {
        try {
            FileReader reader = new FileReader(".pgpass");
            int c;
            StringBuilder builder = new StringBuilder();
            while((c=reader.read())!=-1){
                builder.append((char)c);
            }
            String string = builder.toString();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection() {return connection;}
}
