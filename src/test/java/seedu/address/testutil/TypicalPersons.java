package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com").withGender("F").withAge("20").withDetail("detail")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withGender("M").withAge("40").withDetail("detail")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withEmail("heinz@example.com")
            .withGender("M").withAge("30").withDetail("detail").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withEmail("cornelia@example.com").withGender("M").withAge("50").withDetail("detail")
            .withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withEmail("werner@example.com").withGender("F").withAge("60").withDetail("detail").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withEmail("lydia@example.com").withGender("F").withAge("35").withDetail("detail").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withEmail("anna@example.com").withGender("M").withAge("36").withDetail("detail").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withEmail("stefan@example.com").withGender("M").withAge("17").withDetail("detail").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withEmail("hans@example.com").withGender("F").withAge("21").withDetail("detail").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withEmail(VALID_EMAIL_BOB).withGender("M").withAge("30").withDetail("01-01-2024 1234")
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
