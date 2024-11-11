package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonBuilder;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
                                                          .withNric("S1234567A")
                                                          .withDateOfBirth("2000-01-01")
                                                          .withGender("F")
                                                          .withAddress("123, Jurong West Ave 6, #08-111")
                                                          .withEmail("alice@example.com")
                                                          .withPhone("94351253")
                                                          .withPriority("HIGH")
                                                          .withAppointments("Physio:2024-12-01:1500-1600",
                                                                            "Orthopedic:2024-12-01:1200-1300")
                                                          .withAllergies("Peanuts")
                                                          .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withNric("T0234547A").withDateOfBirth("2002-01-01").withGender("M")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withPriority("HIGH")
            .withAllergies("Peanuts", "Soybeans")
            .withMedCons("Lung Cancer", "Diabetes")
            .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withNric("S1234167F").withDateOfBirth("2000-01-05").withGender("M")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withPriority("LOW")
            .withMedCons("Arthritis", "Scoliosis", "Myopia")
            .build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withNric("T0234567B").withDateOfBirth("2005-01-01").withGender("M")
            .withEmail("cornelia@example.com").withAddress("10th street").withAllergies("Peanuts")
            .withPriority("LOW")
            .withMedCons("Skin Cancer", "Diabetes")
            .build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withNric("T0234567F").withDateOfBirth("2001-01-01").withGender("F")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withPriority("MEDIUM")
            .build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withNric("S1234561A").withDateOfBirth("2000-01-01").withGender("F")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withPriority("MEDIUM")
            .build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withNric("S1234565A").withDateOfBirth("2000-01-01").withGender("M")
            .withEmail("anna@example.com").withAddress("4th street")
            .withPriority("NONE")
            .build();

    public static final Person TOM = new PersonBuilder().withName("Tom Worst").withPhone("8422624")
            .withNric("S1234268Z").withDateOfBirth("2000-01-01").withGender("F")
            .withEmail("stefani123@example.com").withAddress("chinatown")
            .build();
    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withNric("S1234568A").withDateOfBirth("2000-01-01").withGender("F")
            .withEmail("stefan@example.com").withAddress("little india")
            .build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withNric("S1234567Z").withDateOfBirth("2000-01-01").withGender("F")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withNric(VALID_NRIC_AMY).withDateOfBirth(VALID_DOB_AMY).withGender(VALID_GENDER_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withAllergies(VALID_ALLERGY_BOB)
            .build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withNric(VALID_NRIC_BOB).withDateOfBirth(VALID_DOB_BOB).withGender(VALID_GENDER_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
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

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, TOM));
    }
}
