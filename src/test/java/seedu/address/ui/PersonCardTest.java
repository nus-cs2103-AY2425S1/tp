package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.tag.Tag;

public class PersonCardTest {

    private static Person testPerson;

    @BeforeAll
    public static void initToolkit() {
        Platform.startup(() -> {});
    }

    @BeforeAll
    public static void setUp() {
        // Set up a Person with sample data
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));
        tags.add(new Tag("colleague"));

        testPerson = new Person(
                new Name("John Doe"),
                new Phone("98765432"),
                new Email("johnd@example.com"),
                new Address("123, Test St, #01-01"),
                new PostalCode("123456"),
                tags);
    }

    @Test
    public void testPersonCardDisplaysCorrectPerson() {
        PersonCard personCard = new PersonCard(testPerson, 1);
        // Check if the person in PersonCard matches the input testPerson
        assertEquals(testPerson, personCard.person);
    }
}
