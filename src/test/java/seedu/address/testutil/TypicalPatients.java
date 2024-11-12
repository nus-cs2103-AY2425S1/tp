package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
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
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPatients {

    public static final Patient ALICE = new PatientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withGender("F").withDateOfBirth("01-01-2000")
            .withTags("friends").build();

    public static final Patient BENSON = new PatientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com")
            .withPhone("98765432").withGender("M").withDateOfBirth("01-02-2000")
            .withTags("owesMoney", "friends").build();

    public static final Patient CARL = new PatientBuilder().withName("Carl Kurz")
            .withPhone("95352563").withEmail("heinz@example.com")
            .withAddress("Wall Street").withGender("M").withDateOfBirth("04-01-2002").build();

    public static final Patient DANIEL = new PatientBuilder().withName("Daniel Meier")
            .withPhone("87652533").withGender("M").withDateOfBirth("30-07-1994")
            .withEmail("cornelia@example.com").withAddress("10th Street")
            .withTags("friends").build();

    public static final Patient ELLE = new PatientBuilder().withName("Elle Meyer")
            .withPhone("9482224").withGender("F").withDateOfBirth("14-05-1985")
            .withEmail("elle@example.com").withAddress("Michigan Ave").build();

    public static final Patient FIONA = new PatientBuilder().withName("Fiona Kunz")
            .withPhone("9482427").withGender("F").withDateOfBirth("21-11-1993")
            .withEmail("fiona@example.com").withAddress("Little Tokyo").build();

    public static final Patient GEORGE = new PatientBuilder().withName("George Best")
            .withPhone("9482442").withGender("M").withDateOfBirth("29-08-2006")
            .withEmail("george@example.com").withAddress("4th Street").build();

    // Manually added
    public static final Patient HOON = new PatientBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("hoon@example.com").withAddress("Little India").withGender("M")
            .withDateOfBirth("20-11-1995").build();

    public static final Patient IDA = new PatientBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("ida@example.com").withAddress("Chicago Ave").withGender("F")
            .withDateOfBirth("15-06-1990").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PatientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withGender("F").withDateOfBirth("01-01-1998")
            .withTags(VALID_TAG_FRIEND).build();

    public static final Patient BOB = new PatientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withGender("M").withDateOfBirth("01-01-1997")
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatients() {} // prevents instantiation

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPatients()) {
            ab.addPerson(person);
        }
        return ab;
    }
}
