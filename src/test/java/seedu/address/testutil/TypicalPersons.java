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
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder()
            .withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withSpeciality(null)
            .withGender(null)
            .withDateOfBirth(null)
            .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withSpeciality(null)
            .withGender(null)
            .withDateOfBirth(null)
            .build();
    public static final Person CARL = new PersonBuilder()
            .withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withSpeciality(null)
            .withGender(null)
            .withDateOfBirth(null)
            .withAddress("wall street")
            .build();
    public static final Person DANIEL = new PersonBuilder()
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withSpeciality(null)
            .withGender(null)
            .withDateOfBirth(null)
            .withTags("friends")
            .build();
    public static final Person ELLE = new PersonBuilder()
            .withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withSpeciality(null)
            .withGender(null)
            .withDateOfBirth(null)
            .build();
    public static final Person FIONA = new PersonBuilder()
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withSpeciality(null)
            .withGender(null)
            .withDateOfBirth(null)
            .build();
    public static final Person GEORGE = new PersonBuilder()
            .withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withSpeciality(null)
            .withGender(null)
            .withDateOfBirth(null)
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder()
            .withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withSpeciality(null)
            .withGender(null)
            .withDateOfBirth(null)
            .build();
    public static final Person IDA = new PersonBuilder()
            .withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withSpeciality(null)
            .withGender(null)
            .withDateOfBirth(null)
            .withAddress("chicago ave")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder()
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND)
            .build();
    public static final Person BOB = new PersonBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    // Patients
    public static final Patient JOHN = new PatientBuilder()
            .withName("John Doe")
            .withAddress("123, Jurong West Ave 6, #08-123")
            .withEmail("johndoe@example.com")
            .withPhone("98765432")
            .withGender("M")
            .withDateOfBirth("01-01-2000")
            .withTags("friends").build();

    public static final Patient JANE = new PatientBuilder()
            .withName("Jane Doe")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("janedoe@example.com")
            .withPhone("98765432")
            .withGender("F")
            .withDateOfBirth("01-01-2000")
            .withTags("owesMoney", "friends")
            .build();

    // Doctors
    public static final Doctor KENNEDY = new DoctorBuilder()
            .withName("Kennedy Kwek")
            .withAddress("322, Sengkang Central, #02-14")
            .withEmail("kennedykwek@example.com")
            .withPhone("98765432")
            .withSpeciality("General")
            .withTags("friends")
            .build();

    public static final Doctor KAREN = new DoctorBuilder()
            .withName("Karen Koh")
            .withAddress("411, Bukit Batok West Ave 6, #02-25")
            .withEmail("karenkoh@example.com")
            .withPhone("98765432")
            .withSpeciality("Childcare")
            .withTags("owesMoney", "friends")
            .build();

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

    /**
     * Returns an {@code AddressBook} with all the typical persons and patients.
     */
    public static AddressBook getTypicalAddressBookWithPatients() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        ab.addPerson(JOHN);
        ab.addPerson(JANE);
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons and doctors.
     */
    public static AddressBook getTypicalAddressBookWithDoctors() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        ab.addPerson(KENNEDY);
        ab.addPerson(KAREN);
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
