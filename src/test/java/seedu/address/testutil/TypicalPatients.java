package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXISTINGCONDITION_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXISTINGCONDITION_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEALTHRISK_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEALTHRISK_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKNAME_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKNAME_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKPHONE_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKPHONE_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_KEANU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_JAKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_KEANU;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ClinicConnectSystem;
import seedu.address.model.patient.Patient;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPatients {

    public static final Patient ALICE = new PatientBuilder().withName("Alice Pauline")
            .withNric("S1234567J")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withAppts("2024-12-12 14:00", "Blood Test").build();
    public static final Patient BENSON = new PatientBuilder().withName("Benson Meier")
            .withNric("T0101280Z")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withAppts("2025-12-12 14:00", "Vaccination").build();
    public static final Patient CARL = new PatientBuilder().withName("Carl Kurz")
            .withNric("T0481580Z")
            .withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withAppts("2024-12-12 10:00", "Vaccination").build();
    public static final Patient DANIEL = new PatientBuilder().withName("Daniel Meier")
            .withNric("T0381280Z")
            .withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withAppts("2030-06-06 22:00", "Cancer Screening").build();
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

    public static final Patient JAKE = new PatientBuilder().withName(VALID_NAME_JAKE).withNric(VALID_NRIC_JAKE)
            .withSex(VALID_SEX_JAKE).withBirthdate(VALID_BIRTHDATE_JAKE).withPhone(VALID_PHONE_JAKE)
            .withEmail(VALID_EMAIL_JAKE).withAddress(VALID_ADDRESS_JAKE).withBloodType(VALID_BLOODTYPE_JAKE)
            .withNokName(VALID_NOKNAME_JAKE).withNokPhone(VALID_NOKPHONE_JAKE).withAllergies(VALID_ALLERGIES_JAKE)
            .withHealthRisk(VALID_HEALTHRISK_JAKE).withExistingCondition(VALID_EXISTINGCONDITION_JAKE)
            .withNote(VALID_NOTE_JAKE).build();

    public static final Patient KEANU = new PatientBuilder().withName(VALID_NAME_KEANU).withNric(VALID_NRIC_KEANU)
            .withSex(VALID_SEX_KEANU).withBirthdate(VALID_BIRTHDATE_KEANU).withPhone(VALID_PHONE_KEANU)
            .withEmail(VALID_EMAIL_KEANU).withAddress(VALID_ADDRESS_KEANU).withBloodType(VALID_BLOODTYPE_KEANU)
            .withNokName(VALID_NOKNAME_KEANU).withNokPhone(VALID_NOKPHONE_KEANU).withAllergies(VALID_ALLERGIES_KEANU)
            .withHealthRisk(VALID_HEALTHRISK_KEANU).withExistingCondition(VALID_EXISTINGCONDITION_KEANU)
            .withNote(VALID_NOTE_KEANU).build();

    private TypicalPatients() {} // prevents instantiation

    /**
     * Returns an {@code ClinicConnectSystem} with all the typical patients.
     */
    public static ClinicConnectSystem getTypicalClinicConnectSystem() {
        ClinicConnectSystem ab = new ClinicConnectSystem();
        for (Patient patient : getTypicalPatients()) {
            ab.addPatient(patient);
        }
        return ab;
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, KEANU));
    }
}
