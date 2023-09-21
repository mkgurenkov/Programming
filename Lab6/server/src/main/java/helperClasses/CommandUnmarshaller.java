package helperClasses;

import commands.Command;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class for unmarshalling command from xml
 */
public class CommandUnmarshaller {
    /** Method for unmarshalling command from xml*/
    public static Command fromXml(String serializedCommand) throws JAXBException {
        StringReader reader = new StringReader(serializedCommand);
        JAXBContext context = JAXBContext.newInstance(Command.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Command) unmarshaller.unmarshal(reader);
    }
}
