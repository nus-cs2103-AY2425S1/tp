package seedu.address.storage;

import java.util.Set;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hours;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Represents a person in the system with relevant attributes.
 * This class is used for mapping CSV data to a Java object using OpenCSV.
 */
public class PersonBean {
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String phone;
    @CsvBindByName
    private String email;
    @CsvBindByName
    private String address;
    @CsvBindByName
    private String hours;
    @CsvCustomBindByName(converter = PersonBeanTagConverter.class)
    private Set<String> tags;

    public PersonBean() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String toString() {
        return getName() + getPhone() + getEmail() + getAddress() + getHours();
    }

    /**
     * Converts this PersonBean to a Person object.
     *
     * @return A Person object representing this bean.
     */
    public Person toPerson() {
        Set<Tag> convertedTags = tags.stream().map(Tag::new).collect(Collectors.toSet());
        return new Person(new Name(name), new Phone(phone), new Email(email), new Address(address),
                new Hours(hours), convertedTags);
    }
}
