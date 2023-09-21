package helperClasses;

import data.City;
import helperClasses.checkers.CheckElement;
import helperClasses.wrappers.CollectionWrapper;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.Hashtable;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that marshals collection to xml file and unmarshals it back
 */
public class CollectionParser {
    /** Field: keeps the collection*/
    private final Hashtable<Integer, City> collection;
    /** Field: keeps path to the file with collection*/
    private final String pathToFile;
    /** Field: counts number of not loaded elements (from xml file)*/
    private int notLoadedElements = 0;
    /** Field: counts number of elements which were not loaded because their birthday-field does not match age-field*/
    private int birthdayDoesNotMatchAge = 0;
    /** Field: counts number of elements which were not loaded because of having invalid data in their fields*/
    private int invalidFieldDataInFile = 0;
    /** Field: counts number of elements which were not loaded because their creationDate-field does not match creationDateHash-field*/
    private int creationDateDoesNotMatchHashCode = 0;
    /** Field: counts number of elements which were not loaded because of having the same key as element, which already in collection*/
    private int oneKeyForSeveralElements = 0;
    /** Field: shows if there were any exceptions in file with collection*/
    private boolean exceptionsFound = false;
    /** Field: shows if file with collection was not found*/
    private boolean fileNotFound = false;
    /**
     * Constructor
     * @param pathToFile - path to the xml file with collection
     * @param collection - the collection
     */
    public CollectionParser(Hashtable<Integer, City> collection, String pathToFile) {
        this.collection = collection;
        this.pathToFile = pathToFile;
    }
    /** Method for marshalling collection to xml file*/
    public void marshalToXml() {
        try {
            CollectionWrapper collectionWrapper = new CollectionWrapper(collection);
            JAXBContext jaxbContext = JAXBContext.newInstance(CollectionWrapper.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(collectionWrapper, stringWriter);
            String stringXml = stringWriter.toString();
            try {
                FileWriter writer = new FileWriter(pathToFile, false);
                writer.write(stringXml);
                writer.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Method for unmarshalling collection from xml file*/
    public void unmarshalFromXml() {
        try {
            final QName qName = new QName("value"); //city
            final QName qNameKey = new QName("key");
            InputStream inputStream = new FileInputStream(pathToFile);
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
            JAXBContext context = JAXBContext.newInstance(City.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            JAXBContext keyContext = JAXBContext.newInstance(Integer.class);
            Unmarshaller keyUnmarshaller = keyContext.createUnmarshaller();
            XMLEvent e;
            while ((e = xmlEventReader.peek()) != null) {
                if (e.isStartElement() && ((StartElement) e).getName().equals(qNameKey)) {
                    int key = keyUnmarshaller.unmarshal(xmlEventReader, Integer.class).getValue();
                    xmlEventReader.next();
                    City unmarshalledCity = unmarshaller.unmarshal(xmlEventReader, City.class).getValue();
                    addElementIfItIsOk(this.collection, key, unmarshalledCity);
                } else {
                    xmlEventReader.next();
                }
            }
        } catch (JAXBException jaxbException) {
            System.out.println("Collection was not loaded: ");
            System.out.println("XML syntax error");
            exceptionsFound = true;
        } catch (IOException e) {
            if (e.toString().contains("FileNotFoundException")) {
                fileNotFound = true;
            }
            System.out.println("Collection was not loaded: ");
            System.out.println(e.getMessage());
            exceptionsFound = true;
        } catch (XMLStreamException xmlStreamException) {
            System.out.println("Collection was not loaded: ");
            System.out.println("XML file reading exception");
            exceptionsFound = true;
        }
    }
    /** Method: adds element in collection if it is ok, if it is not ok - increments the counter-field, corresponding to the problem*/
    private void addElementIfItIsOk(Hashtable<Integer, City> collection, int key, City element) {
        if (!collection.containsKey(key)) {
            if (CheckElement.checkFields(element, collection, false)) {
                if (CheckElement.checkIfCreationDateMatchesHashCode(element)) {
                    if (CheckElement.checkIfBirthdayMatchesAge(element)) {
                        collection.put(key, element);
                    } else {
                        notLoadedElements++;
                        birthdayDoesNotMatchAge++;
                    }
                } else {
                    notLoadedElements++;
                    creationDateDoesNotMatchHashCode++;
                }
            } else {
                notLoadedElements++;
                invalidFieldDataInFile++;
            }
        } else {
            notLoadedElements++;
            oneKeyForSeveralElements++;
        }
    }
    /** Method which gets quantity of not loaded elements
     * @return int notLoadedElements
     */
    public int getNotLoadedElements() {
        return notLoadedElements;
    }
    /** Method which gets quantity of not loaded elements, which were not loaded because their birthday-field does not match age-field
     * @return int birthdayDoesNotMatchAge
     */
    public int getBirthdayDoesNotMatchAge() {
        return birthdayDoesNotMatchAge;
    }
    /** Method which gets quantity of not loaded elements, which were not loaded because their creationDate-field does not match creationDateHash-field
     * @return int creationDateDoesNotMatchHashCode
     */
    public int getCreationDateDoesNotMatchHashCode() {return creationDateDoesNotMatchHashCode;}
    /** Method which gets quantity of not loaded elements, which were not loaded because of having invalid data in their fields
     * @return int invalidFieldDataInFile
     */
    public int getInvalidFieldDataInFile() {
        return invalidFieldDataInFile;
    }
    /** Method which gets quantity of not loaded elements, which were not loaded because of having the same key as element, which already in collection
     * @return int oneKeyForSeveralElements
     */
    public int getOneKeyForSeveralElements() {
        return oneKeyForSeveralElements;
    }
    /** Method which gets field-flag which shows us if there were any exceptions with file with collection
     * @return boolean anyExceptionsWithFile
     */
    public boolean getExceptionsFound() {
        return exceptionsFound;
    }
    /** Method which gets field-flag which shows us if file with collection was not found
     * @return boolean fileNotFound
     */
    public boolean getFileNotFound() {return fileNotFound;}
}
