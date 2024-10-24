package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRECORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHSERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Valid fields
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NRIC_AMY = "T0123456A";
    public static final String VALID_NRIC_BOB = "S0123456B";
    public static final String VALID_NRIC_ALICE = "S1234567J";
    public static final String VALID_SEX_AMY = "F";
    public static final String VALID_SEX_BOB = "M";
    public static final String VALID_BIRTHDATE_AMY = "2001-05-10";
    public static final String VALID_BIRTHDATE_BOB = "2003-11-30";
    public static final String VALID_HEALTHSERVICE_VACCINATION = "Vaccination";
    public static final String VALID_HEALTHSERVICE_BLOOD_TEST = "Blood Test";
    public static final String VALID_PHONE_AMY = "91234567";
    public static final String VALID_PHONE_BOB = "81234567";
    public static final String VALID_EMAIL_AMY = "amybee10@gmail.com";
    public static final String VALID_EMAIL_BOB = "bobchoo30@gmail.com";
    public static final String VALID_ADDRESS_AMY = "8 Sungei Kadut Loop";
    public static final String VALID_ADDRESS_BOB = "30 Bukit Batok Street 21 ,08-04";
    public static final String VALID_BLOODTYPE_AMY = "O-";
    public static final String VALID_BLOODTYPE_BOB = "A+";
    public static final String VALID_NOKNAME_AMY = "Bumble Bee";
    public static final String VALID_NOKNAME_BOB = "Chu Choo";
    public static final String VALID_NOKPHONE_AMY = "97654321";
    public static final String VALID_NOKPHONE_BOB = "87654321";
    public static final String VALID_ALLERGY_AMY = "peanuts, eggs, seafood";
    public static final String VALID_ALLERGY_BOB = "ceterizine dihydrochloride";
    public static final String VALID_HEALTHRISK_AMY = "HIGH";
    public static final String VALID_HEALTHRISK_BOB = "LOW";
    public static final String VALID_HEALTHRECORD_AMY = "diabetes, hypertension, dementia";
    public static final String VALID_HEALTHRECORD_BOB = "cancer";
    public static final String VALID_APPOINTMENT_AMY = "2025-12-10T14:30";
    public static final String VALID_APPOINTMENT_BOB = "2025-12-11T14:00";
    public static final String VALID_NOTE_AMY = "Patient needs extra care";
    public static final String VALID_NOTE_BOB = "Patient is a fall risk";

    // Valid fields with respective prefixes
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String SEX_DESC_AMY = " " + PREFIX_SEX + VALID_SEX_AMY;
    public static final String SEX_DESC_BOB = " " + PREFIX_SEX + VALID_SEX_BOB;
    public static final String BIRTHDATE_DESC_AMY = " " + PREFIX_BIRTHDATE + VALID_BIRTHDATE_AMY;
    public static final String BIRTHDATE_DESC_BOB = " " + PREFIX_BIRTHDATE + VALID_BIRTHDATE_BOB;
    public static final String HEALTHSERVICE_DESC_VACCINATION = " " + PREFIX_HEALTHSERVICE
            + VALID_HEALTHSERVICE_VACCINATION;
    public static final String HEALTHSERVICE_DESC_BLOOD_TEST = " " + PREFIX_HEALTHSERVICE
            + VALID_HEALTHSERVICE_BLOOD_TEST;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String BLOODTYPE_DESC_AMY = " " + PREFIX_BLOODTYPE + VALID_BLOODTYPE_AMY;
    public static final String BLOODTYPE_DESC_BOB = " " + PREFIX_BLOODTYPE + VALID_BLOODTYPE_BOB;
    public static final String NOKNAME_DESC_AMY = " " + PREFIX_NOKNAME + VALID_NOKNAME_AMY;
    public static final String NOKNAME_DESC_BOB = " " + PREFIX_NOKNAME + VALID_NOKNAME_BOB;
    public static final String NOKPHONE_DESC_AMY = " " + PREFIX_NOKPHONE + VALID_NOKPHONE_AMY;
    public static final String NOKPHONE_DESC_BOB = " " + PREFIX_NOKPHONE + VALID_NOKPHONE_BOB;
    public static final String ALLERGY_DESC_AMY = " " + PREFIX_ALLERGY + VALID_ALLERGY_AMY;
    public static final String ALLERGY_DESC_BOB = " " + PREFIX_ALLERGY + VALID_ALLERGY_BOB;
    public static final String HEALTHRISK_DESC_AMY = " " + PREFIX_HEALTHRISK + VALID_HEALTHRISK_AMY;
    public static final String HEALTHRISK_DESC_BOB = " " + PREFIX_HEALTHRISK + VALID_HEALTHRISK_BOB;
    public static final String HEALTHRECORD_DESC_AMY = " " + PREFIX_HEALTHRECORD + VALID_HEALTHRECORD_AMY;
    public static final String HEALTHRECORD_DESC_BOB = " " + PREFIX_HEALTHRECORD + VALID_HEALTHRECORD_BOB;
    public static final String APPOINTMENT_DESC_AMY = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_AMY;
    public static final String APPOINTMENT_DESC_BOB = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_BOB;
    public static final String NOTE_DESC_AMY = " " + PREFIX_NOTE + VALID_NOTE_AMY;
    public static final String NOTE_DESC_BOB = " " + PREFIX_NOTE + VALID_NOTE_BOB;

    // Invalid fields with respective prefixes
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "T0123456z"; // lowercase 'z' not allowed in nric
    public static final String INVALID_SEX_DESC = " " + PREFIX_SEX + "male"; // only 'M' or 'F' allowed
    public static final String INVALID_BIRTHDATE_DESC = " " + PREFIX_BIRTHDATE
            + "2001/12/02"; // slashes not allowed for birthdates
    public static final String INVALID_HEALTHSERVICE_DESC = " "
            + PREFIX_HEALTHSERVICE + "*"; // * not allowed for Health Services
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "22";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withNric(VALID_NRIC_AMY).withSex(VALID_SEX_AMY).withBirthDate(VALID_BIRTHDATE_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBloodType(VALID_BLOODTYPE_AMY).withNokName(VALID_NOKNAME_AMY).withNokPhone(VALID_NOKPHONE_AMY)
                .withAllergy(VALID_ALLERGY_AMY).withHealthRisk(VALID_HEALTHRISK_AMY)
                .withHealthRecord(VALID_HEALTHRECORD_AMY).withAppts(VALID_APPOINTMENT_AMY)
                .withNote(VALID_NOTE_AMY).build();

        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withNric(VALID_NRIC_BOB).withSex(VALID_SEX_BOB).withBirthDate(VALID_BIRTHDATE_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withBloodType(VALID_BLOODTYPE_BOB).withNokName(VALID_NOKNAME_BOB).withNokPhone(VALID_NOKPHONE_BOB)
                .withAllergy(VALID_ALLERGY_BOB).withHealthRisk(VALID_HEALTHRISK_BOB)
                .withHealthRecord(VALID_HEALTHRECORD_BOB).withAppts(VALID_APPOINTMENT_BOB)
                .withNote(VALID_NOTE_BOB).build();
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
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
