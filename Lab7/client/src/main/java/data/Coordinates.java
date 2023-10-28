package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class for describing coordinates - city field
 */
public class Coordinates implements Serializable {
    /** Field x*/
    private Long x;
    /** Field y*/
    private Long y;
    /** Default constructor to create empty coordinates*/
    public Coordinates() {}
    /** Constructor to create coordinates*/
    public Coordinates(Long x, Long y) {
        this.x = x;
        this.y = y;
    }
    /** Method for getting x coordinate
     * @return Long x
     */
    public Long getX() {
        return x;
    }
    /** Method for setting x coordinate*/
    public void setX(Long x) {
        this.x = x;
    }
    /** Method for getting y coordinate
     * @return Long y
     */
    public Long getY() {
        return y;
    }
    /** Method for setting y coordinate*/
    public void setY(Long y) {
        this.y = y;
    }
    /** Overridden equals() method to check for equality of instances of the Coordinates class*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }
    /** Overridden hashCode() method to check for equality of instances of the Coordinates class*/
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    /** Overridden toString() method to convert an instance of the Coordinates class into a string representation*/
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    /** Method for checking if fields, which must not be null, are null
     * @return boolean isNull
     */
    public boolean isNull() {
        if ((x == null) | (y == null)) {
            return true;
        } else {return false;}
    }
}