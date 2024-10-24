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
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Doctor} objects to be used in tests.
 */
public class TypicalDoctors {

    public static final Doctor ALICE = new DoctorBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withSpeciality("General")
            .withTags("friends").build();

    public static final Doctor BENSON = new DoctorBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("benson@example.com").withPhone("98765432").withSpeciality("Dentist")
            .withTags("owesMoney", "friends").build();

    public static final Doctor CARL = new DoctorBuilder().withName("Carl Kurz")
            .withPhone("95352563").withEmail("carl@example.com")
            .withAddress("Wall Street").withSpeciality("Pediatrics").build();

    public static final Doctor DANIEL = new DoctorBuilder().withName("Daniel Meier")
            .withPhone("87652533").withSpeciality("Surgery")
            .withEmail("daniel@example.com").withAddress("10th Street")
            .withTags("friends").build();

    public static final Doctor ELLE = new DoctorBuilder().withName("Elle Meyer")
            .withPhone("9482224").withSpeciality("Otorhinolaryngology")
            .withEmail("elle@example.com").withAddress("Michigan Ave").build();

    public static final Doctor FIONA = new DoctorBuilder().withName("Fiona Kunz")
            .withPhone("9482427").withSpeciality("Psychology")
            .withEmail("fiona@example.com").withAddress("Little Tokyo").build();

    public static final Doctor GEORGE = new DoctorBuilder().withName("George Best")
            .withPhone("9482442").withSpeciality("Radiology")
            .withEmail("george@example.com").withAddress("4th Street").build();

    // Manually added
    public static final Doctor HOON = new DoctorBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("hoon@example.com").withAddress("Little India").withSpeciality("Cardiology").build();

    public static final Doctor IDA = new DoctorBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("ida@example.com").withAddress("Chicago Ave").withSpeciality("Neurology").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Doctor AMY = new DoctorBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND).withSpeciality("Dermatology").build();

    public static final Doctor BOB = new DoctorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withSpeciality("Oncology").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalDoctors() {} // prevents instantiation

    public static List<Doctor> getTypicalDoctors() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalDoctors()) {
            ab.addPerson(person);
        }
        return ab;
    }
}
