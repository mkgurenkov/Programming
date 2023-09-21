package helperClasses.wrappers;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Wrapper class for LocalDate
 */
public class LocalDateWrapper extends XmlAdapter<String, LocalDate> {
    /** Field: formatter*/
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    /** Method for unmarshalling string representation of instance of LocalDate class to LocalDate*/
    @Override
    public LocalDate unmarshal(String value) {
        return LocalDate.parse(value, formatter);
    }
    /** Method for marshalling instance of LocalDate class to string representation*/
    @Override
    public String marshal(LocalDate value) {
        return value.format(formatter);
    }
}
