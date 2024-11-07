package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIAGNOSIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WARD;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.appointment.AppointmentContainsDatePredicate;
import seedu.address.model.person.FieldContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_ID_AMY = "P12345";
    public static final String VALID_ID_WITH_SPECIAL_CHAR_AMY = "P12345/";
    public static final String VALID_ID_BOB = "P54321";
    public static final String VALID_ID_WITH_SPECIAL_CHAR_BOB = "P54321/";
    public static final String VALID_WARD_AMY = "A1";
    public static final String VALID_WARD_WITH_SPECIAL_CHAR_AMY = "A1!";
    public static final String VALID_WARD_BOB = "B3";
    public static final String VALID_WARD_WITH_SPECIAL_CHAR_BOB = "B3!";
    public static final String VALID_DIAGNOSIS_AMY = "Celiac Disease";
    public static final String VALID_DIAGNOSIS_BOB = "Chronic Sinusitis";
    public static final String VALID_MEDICATION_AMY = "gluten-free diet";
    public static final String VALID_MEDICATION_BOB = "Fluticasone (Flonase Veramyst)";
    public static final String VALID_NOTES_AMY = "Like skiing.";
    public static final String VALID_NOTES_BOB = "Favourite pastime: Eating";
    public static final String VALID_START_DATETIME_1 = "05-11-2024-12-00";
    public static final String VALID_START_DATETIME_2 = "05-11-2024-14-00";
    public static final String VALID_END_DATETIME_1 = "25-12-2024-18-00";
    public static final String VALID_END_DATETIME_2 = "26-12-2024-06-00";
    public static final String DATETIME_START_DESC_AMY = " " + PREFIX_START + VALID_START_DATETIME_1;
    public static final String DATETIME_START_DESC_BOB = " " + PREFIX_START + VALID_START_DATETIME_2;
    public static final String DATETIME_END_DESC_AMY = " " + PREFIX_END + VALID_END_DATETIME_1;
    public static final String DATETIME_END_DESC_BOB = " " + PREFIX_END + VALID_END_DATETIME_2;
    public static final String VALID_APPOINTMENT_AMY = "Surgery";
    public static final String VALID_APPOINTMENT_BOB = "Consultation";
    public static final String APPOINTMENT_DESC_AMY = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_AMY;
    public static final String APPOINTMENT_DESC_BOB = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_BOB;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String ID_DESC_AMY = " " + PREFIX_ID + VALID_ID_AMY;
    public static final String ID_DESC_BOB = " " + PREFIX_ID + VALID_ID_BOB;
    public static final String WARD_DESC_AMY = " " + PREFIX_WARD + VALID_WARD_AMY;
    public static final String WARD_DESC_BOB = " " + PREFIX_WARD + VALID_WARD_BOB;
    public static final String DIAGNOSIS_DESC_AMY = " " + PREFIX_DIAGNOSIS + VALID_DIAGNOSIS_AMY;
    public static final String DIAGNOSIS_DESC_BOB = " " + PREFIX_DIAGNOSIS + VALID_DIAGNOSIS_BOB;
    public static final String MEDICATION_DESC_AMY = " " + PREFIX_MEDICATION + VALID_MEDICATION_AMY;
    public static final String MEDICATION_DESC_BOB = " " + PREFIX_MEDICATION + VALID_MEDICATION_BOB;
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_ID_DESC = " " + PREFIX_ID + "P12D^5"; // '^' not allowed in id
    public static final String INVALID_WARD_DESC = " " + PREFIX_WARD + ""; // No empty string allowed
    public static final String INVALID_DIAGNOSIS_DESC = " " + PREFIX_DIAGNOSIS + "$<>"; // Only a-z and .()/- allowed
    public static final String INVALID_MEDICATION_DESC = " " + PREFIX_MEDICATION + "$<>"; // Only a-z and .()/- allowed
    public static final String INVALID_APPOINTMENT_DESC = " " + PREFIX_APPOINTMENT + "\t";
    public static final String INVALID_START_DATETIME = "10/11/2024"; // Only 0-9 and - allowed
    public static final String INVALID_END_DATETIME = "10-11-202?"; // Only 0-9 and - allowed
    public static final String INVALID_START_DATETIME_DESC = " " + PREFIX_START + INVALID_START_DATETIME;
    public static final String INVALID_END_DATETIME_DESC = " " + PREFIX_END + INVALID_END_DATETIME;
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
    public static final String VALID_APPOINTMENT_START_AMY = "01-01-1999-01-00";
    public static final String VALID_APPOINTMENT_END_AMY = "21-12-2000-23-59";
    public static final String APPOINTMENT_DESCRIPTION_AMY = "Surgery";
    public static final String INVALID_APPOINTMENT_START_AMY = "01-01-2001-01-00";
    public static final String INVALID_APPOINTMENT_END_AMY = "01-01-2000-01-00";
    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).withId(VALID_ID_AMY)
                .withWard(VALID_WARD_AMY).withDiagnosis(VALID_DIAGNOSIS_AMY)
                .withMedication(VALID_MEDICATION_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).withId(VALID_ID_BOB)
                .withWard(VALID_WARD_BOB).withDiagnosis(VALID_DIAGNOSIS_BOB)
                .withMedication(VALID_MEDICATION_BOB).build();
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
        final String[] splitName = person.getName().value.split("\\s+");
        model.updateFilteredPersonList(new FieldContainsKeywordsPredicate(Arrays.asList(splitName[0]), "name"));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered Appointment list to show only the persons with appointments with the same
     * starting date as the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showAppointmentAtIndex(Model model, Index targetIndex) {
        model.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);
        assertTrue(targetIndex.getZeroBased() < model.getSortedAppointmentList().size());

        Person person = model.getSortedAppointmentList().get(targetIndex.getZeroBased());
        final LocalDate date = person.getAppointmentStart().toLocalDate();
        model.updateFilteredAppointmentList(new AppointmentContainsDatePredicate(date));
    }

}
