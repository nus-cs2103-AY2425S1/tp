package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.tag.Tag;


/**
 * Unit tests for CsvUtil.
 */
public class CsvUtilTest {

    private ObservableList<Person> personList;

    @BeforeEach
    public void setUp() {
        // Create actual Person objects for the test
        Set<Tag> tags1 = new HashSet<>();
        tags1.add(new Tag("Friend"));
        tags1.add(new Tag("Colleague"));

        Set<Tag> tags2 = new HashSet<>();
        tags2.add(new Tag("Family"));

        // Update addresses to ensure door numbers have #
        Person person1 = new Person(
                new Name("Alice"),
                new Phone("82345678"),
                new Email("alice@example.com"),
                new Address("#12-345, 123 Wonderland"),
                new PostalCode("118424"),
                tags1
        );

        Person person2 = new Person(
                new Name("Bob"),
                new Phone("97654321"),
                new Email("bob@example.com"),
                new Address("#23-456, 456 Nowhere"),
                new PostalCode("118424"),
                tags2
        );

        personList = FXCollections.observableArrayList(person1, person2);
    }

    @Test
    public void testConvertObservableListToCsv() {
        String expectedCsv = "Name,Phone,Email,Address,Postal Code,Tags\n"
                + "Alice,82345678,alice@example.com,\"#12-345, 123 Wonderland\",118424,Friend;Colleague\n"
                + "Bob,97654321,bob@example.com,\"#23-456, 456 Nowhere\",118424,Family\n";

        String actualCsv = CsvUtil.convertObservableListToCsv(personList);

        assertEquals(expectedCsv, actualCsv);
    }

    @Test
    public void testConvertEmptyListToCsv() {
        // Arrange
        ObservableList<Person> emptyPersonList = FXCollections.observableArrayList();

        // Act
        String actualCsv = CsvUtil.convertObservableListToCsv(emptyPersonList);

        // Assert
        String expectedCsv = "Name,Phone,Email,Address,Postal Code,Tags\n";
        assertEquals(expectedCsv, actualCsv);
    }

    @Test
    public void testConvertSinglePersonListToCsv() {
        // Arrange
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Friend"));

        // Ensure address has # for door number
        Person person = new Person(
                new Name("Charlie"),
                new Phone("91234567"),
                new Email("charlie@example.com"),
                new Address("#123-45, Some Address, Block 1"),
                new PostalCode("123456"),
                tags
        );

        ObservableList<Person> singlePersonList = FXCollections.observableArrayList(person);

        // Act
        String actualCsv = CsvUtil.convertObservableListToCsv(singlePersonList);

        // Assert
        String expectedCsv = "Name,Phone,Email,Address,Postal Code,Tags\n"
                + "Charlie,91234567,charlie@example.com,\"#123-45, Some Address, Block 1\",123456,Friend\n";
        assertEquals(expectedCsv, actualCsv);
    }

    @Test
    public void testConvertPersonWithCommaInAddressToCsv() {
        // Arrange
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Friend"));

        // Ensure address has # for door number
        Person person = new Person(
                new Name("David"),
                new Phone("92345678"),
                new Email("david@example.com"),
                new Address("#101-202, Building 3, City"),
                new PostalCode("789012"),
                tags
        );

        ObservableList<Person> personListWithComma = FXCollections.observableArrayList(person);

        // Act
        String actualCsv = CsvUtil.convertObservableListToCsv(personListWithComma);

        // Assert
        String expectedCsv = "Name,Phone,Email,Address,Postal Code,Tags\n"
                + "David,92345678,david@example.com,\"#101-202, Building 3, City\",789012,Friend\n";
        assertEquals(expectedCsv, actualCsv);
    }
}
