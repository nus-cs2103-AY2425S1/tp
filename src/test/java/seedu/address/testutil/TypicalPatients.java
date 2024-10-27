package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.patient.Patient;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPatients {

    public static final Patient ALICE = new PatientBuilder().withName("Alice Pauline")
            .withNric("S1234567J")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253").build();
    public static final Patient BENSON = new PatientBuilder().withName("Benson Meier")
            .withNric("T0101280Z")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").build();
    public static final Patient CARL = new PatientBuilder().withName("Carl Kurz")
            .withNric("T0481580Z")
            .withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Patient DANIEL = new PatientBuilder().withName("Daniel Meier")
            .withNric("T0381280Z")
            .withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").build();
    public static final Patient ELLE = new PatientBuilder().withName("Elle Meyer")
            .withNric("T0081280F")
            .withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Patient FIONA = new PatientBuilder().withName("Fiona Kunz")
            .withNric("T0100280Z")
            .withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Patient GEORGE = new PatientBuilder().withName("George Best")
            .withNric("T0181003Z")
            .withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Patient HOON = new PatientBuilder().withName("Hoon Meier")
            .withNric("S1234567H")
            .withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Patient IDA = new PatientBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Patient's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PatientBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY)
            .withSex(VALID_SEX_AMY).withBirthdate(VALID_BIRTHDATE_AMY).build();
    public static final Patient BOB = new PatientBuilder().withName(VALID_NAME_BOB).withNric(VALID_NRIC_BOB)
            .withSex(VALID_SEX_BOB).withBirthdate(VALID_BIRTHDATE_BOB).build();

    public static final Patient JAKE = new PatientBuilder().withName("Jake Tio").withPhone("9982131")
            .withEmail("jakers@example.com").withAddress("nus drive").build();

    public static final Patient KEANU = new PatientBuilder().withName("Keanu Reeves").withNric("S9975483H").withSex("M")
            .withBirthdate("1997-11-30").withPhone("86526969").withEmail("keanureeves@example.com")
            .withAddress("Blk 512 Ang Mo Kio Ave 2").withBloodType("O+").withNokName("Mila Kunis")
            .withNokPhone("84126990").withAllergy("peanuts").withHealthRisk("LOW").withExistingCondition("diabetes")
            .withNote("Patient has previous gunshot wound to chest").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatients() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical patients.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Patient patient : getTypicalPatients()) {
            ab.addPatient(patient);
        }
        return ab;
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, KEANU));
    }
}
