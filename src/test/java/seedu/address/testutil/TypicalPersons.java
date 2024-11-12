package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Appointment ALICE_APPOINTMENT = new Appointment(
            "Alice Pauline", new Nric("S6283947C"),
            LocalDateTime.of(2025, 1, 1, 10, 0), LocalDateTime.of(2025, 1, 1, 11, 0));

    public static final Appointment BENSON_APPOINTMENT = new Appointment(
            "Benson Meier", new Nric("S7012345B"),
            LocalDateTime.of(2025, 2, 10, 14, 0), LocalDateTime.of(2025, 2, 10, 15, 0));

    public static final Appointment CHARLOTTE_APPOINTMENT = new Appointment(
            "Charlotte Oliveiro", new Nric("S7193475F"),
            LocalDateTime.of(2025, 3, 15, 9, 0), LocalDateTime.of(2025, 3, 15, 10, 0));

    public static final Appointment DANIEL_APPOINTMENT = new Appointment(
            "Daniel Meier", new Nric("S6483749D"),
            LocalDateTime.of(2025, 4, 5, 13, 0), LocalDateTime.of(2025, 4, 5, 14, 0));

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withNric("S6283947C")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("backPain")
            .withRole("PATIENT")
            .withCaregivers()
            .withPatients()
            .withAppointments(ALICE_APPOINTMENT)
            .withNotes("Follow up on blood pressure medication.")
            .build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withNric("S7012345B")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withTags("shortOfTime", "nurseBefore")
            .withRole("PATIENT", "CAREGIVER")
            .withCaregivers()
            .withPatients()
            .withAppointments(BENSON_APPOINTMENT)
            .withNotes("Review arthritis medication plan.")
            .build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withNric("S7193475F")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withRole("PATIENT")
            .withCaregivers()
            .withPatients()
            .withAppointments(CHARLOTTE_APPOINTMENT)
            .withNotes("Schedule fall risk assessment.")
            .build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withNric("S6483749D")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withTags("friends")
            .withRole("PATIENT")
            .withCaregivers()
            .withPatients()
            .withAppointments(DANIEL_APPOINTMENT)
            .withNotes("Consider referral for physical therapy.")
            .build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withNric("S6382947A")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withRole("CAREGIVER")
            .withCaregivers()
            .withPatients()
            .withAppointments()
            .withNotes("Provide support for medication management.")
            .build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withNric("S6482983A")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withRole("PATIENT")
            .withCaregivers()
            .withPatients()
            .withAppointments()
            .withNotes("Regular check-up scheduled.")
            .build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withNric("G3536933W")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withRole("CAREGIVER")
            .withCaregivers()
            .withPatients()
            .withAppointments()
            .withNotes("Assist Alex with daily tasks.")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withNric("F2197232X").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india")
            .withRole("PATIENT")
            .build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withNric("F8809670M").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withRole("CAREGIVER")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withNric(VALID_NRIC_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND)
            .withRole(VALID_ROLE_AMY)
            .build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withNric(VALID_NRIC_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withRole(VALID_ROLE_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

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
