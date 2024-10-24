package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATEOFLASTVISIT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATEOFLASTVISIT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonComparator;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withTags("friends").withDateOfLastVisit("01-01-2024")
            .withEmergencyContact("92173902").withRemark("").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").withDateOfLastVisit("01-02-2024")
            .withEmergencyContact("92173902").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withDateOfLastVisit("01-01-2025")
            .withEmergencyContact().build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends")
            .withDateOfLastVisit("02-01-2024").withEmergencyContact().build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822249")
            .withEmail("werner@example.com").withAddress("michegan ave").withDateOfLastVisit("10-10-2024")
            .withEmergencyContact().build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94824270")
            .withEmail("lydia@example.com").withAddress("little tokyo").withDateOfLastVisit("23-08-2024")
            .withEmergencyContact().build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94824421")
            .withEmail("anna@example.com").withAddress("4th street").withDateOfLastVisit("05-06-2024")
            .withEmergencyContact().build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84827424")
            .withEmail("stefan@example.com").withAddress("little india").withDateOfLastVisit("01-01-2024")
            .withEmergencyContact().build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84821315")
            .withEmail("hans@example.com").withAddress("chicago ave").withDateOfLastVisit("01-01-2024")
            .withEmergencyContact().build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withDateOfLastVisit(VALID_DATEOFLASTVISIT_AMY).withEmergencyContact(VALID_EMERGENCY_CONTACT_AMY)
            .build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withDateOfLastVisit(VALID_DATEOFLASTVISIT_BOB)
            .withEmergencyContact(VALID_EMERGENCY_CONTACT_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersonsAscendingName()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons
     * listed in appropriate order for {@code SortCommandTest}.
     */
    public static AddressBook getTypicalAddressBook(String sortParameter, boolean isAscending) {
        assert (sortParameter.equals(PersonComparator.NAME)
                || sortParameter.equals(PersonComparator.DATE_OF_LAST_VISIT));
        AddressBook ab = new AddressBook();
        if (isAscending) {
            if (sortParameter.equals(PersonComparator.NAME)) {
                for (Person person : getTypicalPersonsAscendingName()) {
                    ab.addPerson(person);
                }
            } else {
                for (Person person : getTypicalPersonsAscendingDateOfLastVisit()) {
                    ab.addPerson(person);
                }
            }
        } else {
            if (sortParameter.equals(PersonComparator.NAME)) {
                for (Person person : getTypicalPersonsDescendingName()) {
                    ab.addPerson(person);
                }
            } else {
                for (Person person : getTypicalPersonsDescendingDateOfLastVisit()) {
                    ab.addPerson(person);
                }
            }
        }
        return ab;
    }

    public static List<Person> getTypicalPersonsAscendingName() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Person> getTypicalPersonsDescendingName() {
        return new ArrayList<>(Arrays.asList(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE));
    }

    public static List<Person> getTypicalPersonsAscendingDateOfLastVisit() {
        return new ArrayList<>(Arrays.asList(ALICE, DANIEL, BENSON, GEORGE, FIONA, ELLE, CARL));
    }

    public static List<Person> getTypicalPersonsDescendingDateOfLastVisit() {
        return new ArrayList<>(Arrays.asList(CARL, ELLE, FIONA, GEORGE, BENSON, DANIEL, ALICE));
    }
}
