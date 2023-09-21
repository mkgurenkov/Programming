package helperClasses;

import commands.Command;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class for marshalling command to the xml string
 */
public class CommandMarshaller {
    /** Method for marshalling command to the xml string*/
    public static String toXml(Command command) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Command.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(command, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
                System.out.println(e.getMessage());
                return "";
        }
    }
}
