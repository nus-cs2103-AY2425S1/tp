package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDCON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEPERIOD;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NRIC_AMY = "S1234567A";
    public static final String VALID_NRIC_BOB = "T0234567A";
    public static final String VALID_DOB_AMY = "2000-01-01";
    public static final String VALID_DOB_BOB = "2002-01-01";
    public static final String VALID_GENDER_AMY = "F";
    public static final String VALID_GENDER_BOB = "M";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ALLERGY_AMY = "milk";
    public static final String VALID_ALLERGY_BOB = "soybeans";
    public static final String VALID_PRIORITY_AMY = "HIGH";
    public static final String VALID_PRIORITY_BOB = "LOW";
    public static final String VALID_APPOINTMENT_NAME_DENTAL = "Dental";
    public static final String VALID_APPOINTMENT_DATE_DENTAL = "2024-12-25";
    public static final String VALID_APPOINTMENT_TIMEPERIOD_DENTAL = "1000-1200";
    public static final String VALID_APPOINTMENT_TIMEPERIOD_DENTAL_AFT = "1200-1300";
    public static final String VALID_APPOINTMENT_TIMEPERIOD_DENTAL_BEF = "0900-1000";
    public static final String VALID_APPOINTMENT_NAME_PHYSIO = "Physio";
    public static final String VALID_APPOINTMENT_DATE_PHYSIO = "2024-01-01";
    public static final String VALID_APPOINTMENT_TIMEPERIOD_PHYSIO = "1235-1500";
    public static final String VALID_MEDCON_AMY = "Diabetes";
    public static final String VALID_MEDCON_BOB = "Rabies";

    public static final String VALID_APPOINTMENT_NAME_AMY = VALID_APPOINTMENT_NAME_DENTAL;
    public static final String VALID_APPOINTMENT_DATE_AMY = VALID_APPOINTMENT_DATE_DENTAL;
    public static final String VALID_APPOINTMENT_TIMEPERIOD_AMY = VALID_APPOINTMENT_TIMEPERIOD_DENTAL;
    public static final String VALID_APPOINTMENT_NAME_BOB = VALID_APPOINTMENT_NAME_PHYSIO;
    public static final String VALID_APPOINTMENT_DATE_BOB = VALID_APPOINTMENT_DATE_PHYSIO;
    public static final String VALID_APPOINTMENT_TIMEPERIOD_BOB = VALID_APPOINTMENT_TIMEPERIOD_PHYSIO;
    public static final String VALID_DEL_APPT_AMY_DESC = " " + PREFIX_DATE + VALID_APPOINTMENT_DATE_AMY
                                                          + " " + PREFIX_TIMEPERIOD + VALID_APPOINTMENT_TIMEPERIOD_AMY;
    public static final String VALID_DEL_APPT_BOB_DESC = " " + PREFIX_DATE + VALID_APPOINTMENT_DATE_BOB
                                                          + " " + PREFIX_TIMEPERIOD + VALID_APPOINTMENT_TIMEPERIOD_BOB;

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String DOB_DESC_AMY = " " + PREFIX_DOB + VALID_DOB_AMY;
    public static final String DOB_DESC_BOB = " " + PREFIX_DOB + VALID_DOB_BOB;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + VALID_GENDER_AMY;
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String ALLERGY_DESC_AMY = " " + PREFIX_ALLERGY + VALID_ALLERGY_AMY;
    public static final String ALLERGY_DESC_BOB = " " + PREFIX_ALLERGY + VALID_ALLERGY_BOB;
    public static final String PRIORITY_DESC_AMY = " " + PREFIX_PRIORITY + VALID_PRIORITY_AMY;
    public static final String PRIORITY_DESC_BOB = " " + PREFIX_PRIORITY + VALID_PRIORITY_BOB;
    public static final String MEDCON_DESC_AMY = " " + PREFIX_MEDCON + VALID_MEDCON_AMY;
    public static final String MEDCON_DESC_BOB = " " + PREFIX_MEDCON + VALID_MEDCON_BOB;
    public static final String APPOINTMENT_DESC_AMY = " " + VALID_APPOINTMENT_NAME_DENTAL + " " + PREFIX_DATE
                                                      + VALID_APPOINTMENT_DATE_DENTAL + " " + PREFIX_TIMEPERIOD
                                                      + VALID_APPOINTMENT_TIMEPERIOD_DENTAL;
    public static final String APPOINTMENT_DESC_BOB = " " + VALID_APPOINTMENT_NAME_PHYSIO + " " + PREFIX_DATE
                                                      + VALID_APPOINTMENT_DATE_PHYSIO + " " + PREFIX_TIMEPERIOD
                                                      + VALID_APPOINTMENT_TIMEPERIOD_PHYSIO;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NRIC = "S1234567&"; // non-alphanumeric characters not allowed in NRIC
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "S1234567"; // missing last character
    public static final String INVALID_DOB_FORMAT_DESC = " " + PREFIX_DOB + "2000/02/02"; // '/' not allowed in DOB
    public static final String INVALID_DOB_VALUE_DESC = " " + PREFIX_DOB + LocalDate.now().plusDays(2)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // DOB in future
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "X"; // 'X' not allowed for gender
    public static final String INVALID_MEDCON_DESC = " " + PREFIX_MEDCON
            + "Pneumonoultramicroscopicsilicovolcanoconiosislolololol"; // longer than 30 characters
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_ALLERGY_DESC = " " + PREFIX_ALLERGY + "shellfish*"; // '*' not allowed in tags
    public static final String INVALID_ALLERGY_DESC_LENGTH = " " + PREFIX_ALLERGY
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZLOLOLOLOLOL";
    // Only "NONE", "LOW", "MEDIUM" or "HIGH" is allowed for Priority
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "invalidPriority";


    public static final String INVALID_APPOINTMENT_NAME = "123@abc";
    public static final String INVALID_APPOINTMENT_NAME_LENGTH = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // >30 chars
    public static final String INVALID_APPOINTMENT_DATE_FORMAT = "2024/12/25";
    public static final String INVALID_APPOINTMENT_DATE_NONEXISTANT = "2024-15-32";
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_1 = "10:00-12:00";
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_2 = "110-1200";
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_3 = "1100-120";
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_4 = "10-12";
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_ORDER = "1500-1200";
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_CLASH_DENTAL_1 = "0900-1200";
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_CLASH_DENTAL_2 = "1000-1300";
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_CLASH_DENTAL_3 = "0900-1300";
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_CLASH_DENTAL_4 = "1100-1130";
    public static final String INVALID_APPOINTMENT_NAME_DESC = " " + INVALID_APPOINTMENT_NAME + " " + PREFIX_DATE
                                                               + VALID_APPOINTMENT_DATE_PHYSIO + " "
                                                               + PREFIX_TIMEPERIOD
                                                               + VALID_APPOINTMENT_TIMEPERIOD_PHYSIO;
    public static final String INVALID_APPOINTMENT_NAME_BLANK_DESC = " " + PREFIX_DATE
                                                               + VALID_APPOINTMENT_DATE_PHYSIO + " "
                                                               + PREFIX_TIMEPERIOD
                                                               + VALID_APPOINTMENT_TIMEPERIOD_PHYSIO;
    public static final String INVALID_APPOINTMENT_NAME_LENGTH_DESC = " " + INVALID_APPOINTMENT_NAME_LENGTH + " "
                                                                      + PREFIX_DATE
                                                               + VALID_APPOINTMENT_DATE_PHYSIO + " "
                                                               + PREFIX_TIMEPERIOD
                                                               + VALID_APPOINTMENT_TIMEPERIOD_PHYSIO;
    public static final String INVALID_APPOINTMENT_DATE_FORMAT_DESC = " " + VALID_APPOINTMENT_NAME_PHYSIO + " "
                                                                      + PREFIX_DATE
                                                                      + INVALID_APPOINTMENT_DATE_FORMAT + " "
                                                                      + PREFIX_TIMEPERIOD
                                                                      + VALID_APPOINTMENT_TIMEPERIOD_PHYSIO;
    public static final String INVALID_APPOINTMENT_DATE_NONEXISTANT_DESC = " " + VALID_APPOINTMENT_NAME_PHYSIO + " "
                                                                           + PREFIX_DATE
                                                                           + INVALID_APPOINTMENT_DATE_NONEXISTANT
                                                                           + " " + PREFIX_TIMEPERIOD
                                                                           + VALID_APPOINTMENT_TIMEPERIOD_PHYSIO;
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_DESC_1 = " " + VALID_APPOINTMENT_NAME_PHYSIO + " "
                                                                            + PREFIX_DATE
                                                                            + VALID_APPOINTMENT_DATE_PHYSIO + " "
                                                                            + PREFIX_TIMEPERIOD
                                                                            + INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_1;
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_DESC_2 = " " + VALID_APPOINTMENT_NAME_PHYSIO + " "
                                                                            + PREFIX_DATE
                                                                            + VALID_APPOINTMENT_DATE_PHYSIO + " "
                                                                            + PREFIX_TIMEPERIOD
                                                                            + INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_2;
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_DESC_3 = " " + VALID_APPOINTMENT_NAME_PHYSIO + " "
                                                                            + PREFIX_DATE
                                                                            + VALID_APPOINTMENT_DATE_PHYSIO + " "
                                                                            + PREFIX_TIMEPERIOD
                                                                            + INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_3;
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_DESC_4 = " " + VALID_APPOINTMENT_NAME_PHYSIO + " "
                                                                            + PREFIX_DATE
                                                                            + VALID_APPOINTMENT_DATE_PHYSIO + " "
                                                                            + PREFIX_TIMEPERIOD
                                                                            + INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_4;
    public static final String INVALID_APPOINTMENT_TIMEPERIOD_ORDER_DESC = " " + VALID_APPOINTMENT_NAME_PHYSIO + " "
                                                                           + PREFIX_DATE
                                                                           + VALID_APPOINTMENT_DATE_PHYSIO + " "
                                                                           + PREFIX_TIMEPERIOD
                                                                           + INVALID_APPOINTMENT_TIMEPERIOD_ORDER;
    public static final String INVALID_DEL_APPT_DATE_FORMAT_DESC = " " + PREFIX_DATE
                                                                   + INVALID_APPOINTMENT_DATE_FORMAT + " "
                                                                   + PREFIX_TIMEPERIOD
                                                                   + VALID_APPOINTMENT_TIMEPERIOD_PHYSIO;
    public static final String INVALID_DEL_APPT_DATE_NONEXISTANT_DESC = " " + PREFIX_DATE
                                                                        + INVALID_APPOINTMENT_DATE_NONEXISTANT
                                                                        + " " + PREFIX_TIMEPERIOD
                                                                        + VALID_APPOINTMENT_TIMEPERIOD_PHYSIO;
    public static final String INVALID_DEL_APPT_TIMEPERIOD_FORMAT_DESC_1 = " " + PREFIX_DATE
                                                                         + VALID_APPOINTMENT_DATE_PHYSIO + " "
                                                                         + PREFIX_TIMEPERIOD
                                                                         + INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_1;
    public static final String INVALID_DEL_APPT_TIMEPERIOD_FORMAT_DESC_2 = " " + PREFIX_DATE
                                                                         + VALID_APPOINTMENT_DATE_PHYSIO + " "
                                                                         + PREFIX_TIMEPERIOD
                                                                         + INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_2;
    public static final String INVALID_DEL_APPT_TIMEPERIOD_FORMAT_DESC_3 = " " + PREFIX_DATE
                                                                           + VALID_APPOINTMENT_DATE_PHYSIO + " "
                                                                           + PREFIX_TIMEPERIOD
                                                                           + INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_3;
    public static final String INVALID_DEL_APPT_TIMEPERIOD_FORMAT_DESC_4 = " " + PREFIX_DATE
                                                                           + VALID_APPOINTMENT_DATE_PHYSIO + " "
                                                                           + PREFIX_TIMEPERIOD
                                                                           + INVALID_APPOINTMENT_TIMEPERIOD_FORMAT_4;
    public static final String INVALID_DEL_APPT_TIMEPERIOD_ORDER_DESC = " " + PREFIX_DATE
                                                                        + VALID_APPOINTMENT_DATE_PHYSIO + " "
                                                                        + PREFIX_TIMEPERIOD
                                                                        + INVALID_APPOINTMENT_TIMEPERIOD_ORDER;
    public static final String INVALID_MEDCON = "Pneumonoultramicroscopicsilicovolcanoconiosislolololol";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withGender(VALID_GENDER_AMY).withNric(VALID_NRIC_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withAllergies(VALID_ALLERGY_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withNric(VALID_NRIC_BOB).withDateOfBirth(VALID_DOB_BOB).withGender(VALID_GENDER_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withAllergies(VALID_ALLERGY_BOB, VALID_ALLERGY_AMY).build();
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
