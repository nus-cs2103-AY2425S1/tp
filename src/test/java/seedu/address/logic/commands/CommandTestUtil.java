package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXISTINGCONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHSERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVEALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.DefaultCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ClinicConnectSystem;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.EditPatientDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Valid fields
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_JAKE = "Jake Tio";
    public static final String VALID_NAME_KEANU = "Keanu Reeves";
    public static final String VALID_NRIC_AMY = "T0123456A";
    public static final String VALID_NRIC_BOB = "S0123456B";
    public static final String VALID_NRIC_ALICE = "S1234567J";
    public static final String VALID_NRIC_JAKE = "S9975484H";
    public static final String VALID_NRIC_KEANU = "S9975483H";
    public static final String VALID_SEX_AMY = "F";
    public static final String VALID_SEX_BOB = "M";
    public static final String VALID_SEX_JAKE = "M";
    public static final String VALID_SEX_KEANU = "M";
    public static final String VALID_BIRTHDATE_AMY = "2001-05-10";
    public static final String VALID_BIRTHDATE_BOB = "2003-11-30";
    public static final String VALID_BIRTHDATE_JAKE = "1972-12-19";
    public static final String VALID_BIRTHDATE_KEANU = "1997-11-30";
    public static final String VALID_HEALTHSERVICE_VACCINATION = "Vaccination";
    public static final String VALID_HEALTHSERVICE_BLOOD_TEST = "Blood Test";
    public static final String VALID_PHONE_AMY = "91234567";
    public static final String VALID_PHONE_BOB = "81234567";
    public static final String VALID_PHONE_JAKE = "86512312";
    public static final String VALID_PHONE_KEANU = "86526969";
    public static final String VALID_EMAIL_AMY = "amybee10@gmail.com";
    public static final String VALID_EMAIL_BOB = "bobchoo30@gmail.com";
    public static final String VALID_EMAIL_JAKE = "jakejaket@example.com";
    public static final String VALID_EMAIL_KEANU = "keanureeves@example.com";
    public static final String VALID_ADDRESS_AMY = "8 Sungei Kadut Loop";
    public static final String VALID_ADDRESS_BOB = "30 Bukit Batok Street 21, 08-04";
    public static final String VALID_ADDRESS_JAKE = "Blk 546, Clementi Road, 11-192";
    public static final String VALID_ADDRESS_KEANU = "Blk 512 Ang Mo Kio Ave 2, 12-14";
    public static final String VALID_BLOODTYPE_AMY = "O-";
    public static final String VALID_BLOODTYPE_BOB = "A+";
    public static final String VALID_BLOODTYPE_JAKE = "A+";
    public static final String VALID_BLOODTYPE_KEANU = "O-";
    public static final String VALID_NOKNAME_AMY = "Bumble Bee";
    public static final String VALID_NOKNAME_BOB = "Chu Choo";
    public static final String VALID_NOKNAME_JAKE = "Jamie Tio";
    public static final String VALID_NOKNAME_KEANU = "Mila Kunis";
    public static final String VALID_NOKPHONE_AMY = "97654321";
    public static final String VALID_NOKPHONE_BOB = "87654321";
    public static final String VALID_NOKPHONE_JAKE = "81231234";
    public static final String VALID_NOKPHONE_KEANU = "84126990";
    public static final String VALID_ALLERGIES_AMY = "peanuts, cakes";
    public static final String VALID_ALLERGIES_BOB = "ceterizine dihydrochloride";
    public static final String VALID_ALLERGIES_JAKE = "eggs, fish";
    public static final String VALID_ALLERGIES_KEANU = "wheat, seafood";
    public static final String VALID_ALLERGIES_TO_REMOVE_AMY = "peanuts";
    public static final String VALID_HEALTHRISK_AMY = "HIGH";
    public static final String VALID_HEALTHRISK_BOB = "LOW";
    public static final String VALID_HEALTHRISK_JAKE = "MEDIUM";
    public static final String VALID_HEALTHRISK_KEANU = "LOW";
    public static final String VALID_EXISTINGCONDITION_AMY = "diabetes, hypertension, dementia";
    public static final String VALID_EXISTINGCONDITION_BOB = "cancer";
    public static final String VALID_EXISTINGCONDITION_JAKE = "cancer";
    public static final String VALID_EXISTINGCONDITION_KEANU = "diabetes";
    public static final String VALID_APPOINTMENT_AMY = "2025-12-10T14:30";
    public static final String VALID_APPOINTMENT_BOB = "2025-12-11T14:00";
    public static final String VALID_NOTE_AMY = "Patient needs extra care";
    public static final String VALID_NOTE_BOB = "Patient is a fall risk";
    public static final String VALID_NOTE_JAKE = "Patient is quick-tempered";
    public static final String VALID_NOTE_KEANU = "Patient has previous gunshot wound to chest";

    // Valid fields with respective prefixes
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_JAKE = " " + PREFIX_NAME + VALID_NAME_JAKE;
    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String NRIC_DESC_JAKE = " " + PREFIX_NRIC + VALID_NRIC_JAKE;
    public static final String SEX_DESC_AMY = " " + PREFIX_SEX + VALID_SEX_AMY;
    public static final String SEX_DESC_BOB = " " + PREFIX_SEX + VALID_SEX_BOB;
    public static final String SEX_DESC_JAKE = " " + PREFIX_SEX + VALID_SEX_JAKE;
    public static final String BIRTHDATE_DESC_AMY = " " + PREFIX_BIRTHDATE + VALID_BIRTHDATE_AMY;
    public static final String BIRTHDATE_DESC_BOB = " " + PREFIX_BIRTHDATE + VALID_BIRTHDATE_BOB;
    public static final String BIRTHDATE_DESC_JAKE = " " + PREFIX_BIRTHDATE + VALID_BIRTHDATE_JAKE;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String PHONE_DESC_JAKE = " " + PREFIX_PHONE + VALID_PHONE_JAKE;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String EMAIL_DESC_JAKE = " " + PREFIX_EMAIL + VALID_EMAIL_JAKE;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String ADDRESS_DESC_JAKE = " " + PREFIX_ADDRESS + VALID_ADDRESS_JAKE;
    public static final String BLOODTYPE_DESC_AMY = " " + PREFIX_BLOODTYPE + VALID_BLOODTYPE_AMY;
    public static final String BLOODTYPE_DESC_BOB = " " + PREFIX_BLOODTYPE + VALID_BLOODTYPE_BOB;
    public static final String BLOODTYPE_DESC_JAKE = " " + PREFIX_BLOODTYPE + VALID_BLOODTYPE_JAKE;
    public static final String NOKNAME_DESC_AMY = " " + PREFIX_NOKNAME + VALID_NOKNAME_AMY;
    public static final String NOKNAME_DESC_BOB = " " + PREFIX_NOKNAME + VALID_NOKNAME_BOB;
    public static final String NOKNAME_DESC_JAKE = " " + PREFIX_NOKNAME + VALID_NOKNAME_JAKE;
    public static final String NOKPHONE_DESC_AMY = " " + PREFIX_NOKPHONE + VALID_NOKPHONE_AMY;
    public static final String NOKPHONE_DESC_BOB = " " + PREFIX_NOKPHONE + VALID_NOKPHONE_BOB;
    public static final String NOKPHONE_DESC_JAKE = " " + PREFIX_NOKPHONE + VALID_NOKPHONE_JAKE;
    public static final String ALLERGIES_DESC_AMY = " " + PREFIX_ALLERGY + "peanuts " + PREFIX_ALLERGY + "cakes";
    public static final String ALLERGIES_DESC_BOB = " " + PREFIX_ALLERGY + VALID_ALLERGIES_BOB;
    public static final String ALLERGIES_DESC_JAKE = " " + PREFIX_ALLERGY + "eggs " + PREFIX_ALLERGY + "fish";
    public static final String ALLERGIES_TO_REMOVE_DESC_AMY = " " + PREFIX_REMOVEALLERGY
            + VALID_ALLERGIES_TO_REMOVE_AMY;
    public static final String HEALTHRISK_DESC_AMY = " " + PREFIX_HEALTHRISK + VALID_HEALTHRISK_AMY;
    public static final String HEALTHRISK_DESC_BOB = " " + PREFIX_HEALTHRISK + VALID_HEALTHRISK_BOB;
    public static final String HEALTHRISK_DESC_JAKE = " " + PREFIX_HEALTHRISK + VALID_HEALTHRISK_JAKE;
    public static final String EXISTINGCONDITION_DESC_AMY = " " + PREFIX_EXISTINGCONDITION
            + VALID_EXISTINGCONDITION_AMY;
    public static final String EXISTINGCONDITION_DESC_BOB = " " + PREFIX_EXISTINGCONDITION
            + VALID_EXISTINGCONDITION_BOB;
    public static final String EXISTINGCONDITION_DESC_JAKE = " " + PREFIX_EXISTINGCONDITION
            + VALID_EXISTINGCONDITION_JAKE;
    public static final String NOTE_DESC_AMY = " " + PREFIX_NOTE + VALID_NOTE_AMY;
    public static final String NOTE_DESC_BOB = " " + PREFIX_NOTE + VALID_NOTE_BOB;
    public static final String NOTE_DESC_JAKE = " " + PREFIX_NOTE + VALID_NOTE_JAKE;

    // Invalid fields
    public static final String INVALID_NRIC = "T0123456z";

    // Invalid fields with respective prefixes
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "T0123456z"; // lowercase 'z' not allowed in nric
    public static final String INVALID_SEX_DESC = " " + PREFIX_SEX + "male"; // only 'M' or 'F' allowed
    public static final String INVALID_BIRTHDATE_DESC = " " + PREFIX_BIRTHDATE
            + "2001/12/02"; // slashes not allowed for birthdates
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "22";
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "jaker@@gmail.com";
    public static final String INVALID_BLOODTYPE_DESC = " " + PREFIX_BLOODTYPE + "a-";
    public static final String INVALID_HEALTHRISK_DESC = " " + PREFIX_HEALTHRISK + "VERY HIGH";
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPatientDescriptor DESC_AMY;
    public static final EditCommand.EditPatientDescriptor DESC_BOB;
    public static final EditCommand.EditPatientDescriptor DESC_KEANU;

    static {
        DESC_AMY = new EditPatientDescriptorBuilder().withName(VALID_NAME_AMY)
                .withNric(VALID_NRIC_AMY).withSex(VALID_SEX_AMY).withBirthDate(VALID_BIRTHDATE_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBloodType(VALID_BLOODTYPE_AMY).withNokName(VALID_NOKNAME_AMY).withNokPhone(VALID_NOKPHONE_AMY)
                .withAllergiesToAdd(VALID_ALLERGIES_AMY).withHealthRisk(VALID_HEALTHRISK_AMY)
                .withExistingCondition(VALID_EXISTINGCONDITION_AMY).withNote(VALID_NOTE_AMY).build();

        DESC_BOB = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB)
                .withNric(VALID_NRIC_BOB).withSex(VALID_SEX_BOB).withBirthDate(VALID_BIRTHDATE_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withBloodType(VALID_BLOODTYPE_BOB).withNokName(VALID_NOKNAME_BOB).withNokPhone(VALID_NOKPHONE_BOB)
                .withAllergiesToAdd(VALID_ALLERGIES_BOB).withHealthRisk(VALID_HEALTHRISK_BOB)
                .withExistingCondition(VALID_EXISTINGCONDITION_BOB).withNote(VALID_NOTE_BOB).build();

        DESC_KEANU = new EditPatientDescriptorBuilder().withName(VALID_NAME_KEANU).withNric(VALID_NRIC_KEANU)
                .withSex(VALID_SEX_KEANU).withBirthDate(VALID_BIRTHDATE_KEANU).withPhone(VALID_PHONE_KEANU)
                .withEmail(VALID_EMAIL_KEANU).withAddress(VALID_ADDRESS_KEANU).withBloodType(VALID_BLOODTYPE_KEANU)
                .withNokName(VALID_NOKNAME_KEANU).withNokPhone(VALID_NOKPHONE_KEANU)
                .withAllergiesToAdd(VALID_ALLERGIES_KEANU).withHealthRisk(VALID_HEALTHRISK_KEANU)
                .withExistingCondition(VALID_EXISTINGCONDITION_KEANU).withNote(VALID_NOTE_KEANU).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new DefaultCommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered patient list and selected patient in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ClinicConnectSystem expectedClinicConnectSystem = new ClinicConnectSystem(actualModel.getClinicConnectSystem());
        List<Patient> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPatientList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedClinicConnectSystem, actualModel.getClinicConnectSystem());
        assertEquals(expectedFilteredList, actualModel.getFilteredPatientList());
    }
}
