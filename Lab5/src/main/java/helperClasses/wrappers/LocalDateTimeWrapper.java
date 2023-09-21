package helperClasses.wrappers;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Wrapper class for LocalDateTime
 */
public class LocalDateTimeWrapper  extends XmlAdapter<String, LocalDateTime> {
    /** Field: formatter*/
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    /** Method for unmarshalling string representation of instance of LocalDateTime class to LocalDateTime*/
    @Override
    public LocalDateTime unmarshal(String value) {
        return LocalDateTime.parse(value, formatter);
    }
    /** Method for marshalling instance of LocalDateTime class to string representation*/
    @Override
    public String marshal(LocalDateTime value) {
        return value.format(formatter);
    }
}
