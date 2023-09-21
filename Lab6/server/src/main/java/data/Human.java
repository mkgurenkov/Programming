package data;

import helperClasses.wrappers.LocalDateTimeWrapper;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Objects;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class for describing a human - city field
 */
@XmlRootElement(name = "human")
public class Human {
    /** Field name*/
    private String name; //Поле не может быть null, Строка не может быть пустой
    /** Field age*/
    private int age; //Значение поля должно быть больше 0
    /** Field birthday*/
    private LocalDateTime birthday;
    /** Default constructor to create empty human*/
    public Human() {}
    /** Constructor to create human*/
    public Human(String name, int age, LocalDateTime birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }
    /** Method for getting name
     * @return String name
     */
    @XmlElement
    public String getName() {
        return name;
    }
    /** Method for setting name*/
    public void setName(String name) {
        this.name = name;
    }
    /** Method for getting age
     * @return int age
     */
    @XmlElement
    public int getAge() {
        return age;
    }
    /** Method for setting age*/
    public void setAge(int age) {
        this.age = age;
    }
    /** Method for getting birthday
     * @return LocalDateTime birthday
     */
    @XmlElement
    public LocalDateTime getBirthday() {
        return birthday;
    }
    /** Method for setting birthday*/
    @XmlJavaTypeAdapter(LocalDateTimeWrapper.class)
    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }
    /** Overridden equals() method to check for equality of instances of the Human class*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Human human = (Human) o;

        if (age != human.age) return false;
        if (!Objects.equals(name, human.name)) return false;
        return Objects.equals(birthday, human.birthday);
    }
    /** Overridden hashCode() method to check for equality of instances of the Human class*/
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }
    /** Overridden toString() method to convert an instance of the Human class into a string representation*/
    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
    /** Method for checking if fields, which must not be null, are null
     * @return boolean isNull
     */
    public boolean isNull() {
        if (name == null) {
            return true;
        } else {return false;}
    }

}
