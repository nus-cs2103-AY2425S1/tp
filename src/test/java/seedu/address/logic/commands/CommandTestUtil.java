package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NRIC_AMY = "S1234567D";
    public static final String VALID_NRIC_BOB = "S3267937D";
    public static final String VALID_NRIC_UNIQUE = "S3267937D";

    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_ROLE_AMY = "PATIENT";
    public static final String VALID_ROLE_BOB = "CAREGIVER";
    public static final String VALID_NOTE = "Note text";

    public static final String VALID_DATE_APPOINTMENT_AMY = "20/10/2025";
    public static final String VALID_START_TIME_APPOINTMENT_AMY = "10:00";
    public static final String VALID_END_TIME_APPOINTMENT_AMY = "11:00";
    public static final LocalDateTime VALID_START_DATE_TIME_APPOINTMENT_AMY = LocalDateTime.of(
            LocalDate.parse(VALID_DATE_APPOINTMENT_AMY, DATE_FORMATTER),
            LocalTime.parse(VALID_START_TIME_APPOINTMENT_AMY, TIME_FORMATTER));
    public static final LocalDateTime VALID_END_DATE_TIME_APPOINTMENT_AMY = LocalDateTime.of(
            LocalDate.parse(VALID_DATE_APPOINTMENT_AMY, DATE_FORMATTER),
            LocalTime.parse(VALID_END_TIME_APPOINTMENT_AMY, TIME_FORMATTER));
    public static final String VALID_DATE_APPOINTMENT_DESC_AMY = " " + PREFIX_DATE + VALID_DATE_APPOINTMENT_AMY;
    public static final String VALID_START_TIME_APPOINTMENT_DESC_AMY = " " + PREFIX_START_TIME
            + VALID_START_TIME_APPOINTMENT_AMY;
    public static final String VALID_END_TIME_APPOINTMENT_DESC_AMY = " " + PREFIX_END_TIME
            + VALID_END_TIME_APPOINTMENT_AMY;

    public static final String VALID_DATE_APPOINTMENT_BOB = "19/10/2025";
    public static final String VALID_START_TIME_APPOINTMENT_BOB = "09:00";
    public static final String VALID_END_TIME_APPOINTMENT_BOB = "10:00";
    public static final LocalDateTime VALID_START_DATE_TIME_APPOINTMENT_BOB = LocalDateTime.of(
            LocalDate.parse(VALID_DATE_APPOINTMENT_BOB, DATE_FORMATTER),
            LocalTime.parse(VALID_START_TIME_APPOINTMENT_BOB, TIME_FORMATTER));
    public static final LocalDateTime VALID_END_DATE_TIME_APPOINTMENT_BOB = LocalDateTime.of(
            LocalDate.parse(VALID_DATE_APPOINTMENT_BOB, DATE_FORMATTER),
            LocalTime.parse(VALID_END_TIME_APPOINTMENT_BOB, TIME_FORMATTER));
    public static final String VALID_DATE_APPOINTMENT_DESC_BOB = " " + PREFIX_DATE + VALID_DATE_APPOINTMENT_BOB;
    public static final String VALID_START_TIME_APPOINTMENT_DESC_BOB = " " + PREFIX_START_TIME
            + VALID_START_TIME_APPOINTMENT_BOB;
    public static final String VALID_END_TIME_APPOINTMENT_DESC_BOB = " " + PREFIX_END_TIME
            + VALID_END_TIME_APPOINTMENT_BOB;

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String ROLE_DESC_AMY = " " + PREFIX_ROLE + VALID_ROLE_AMY;
    public static final String ROLE_DESC_BOB = " " + PREFIX_ROLE + VALID_ROLE_BOB;
    public static final String FIND_NRIC_DESC_AMY = " " + PREFIX_FIND_NRIC + VALID_NRIC_AMY;
    public static final String FIND_NRIC_DESC_BOB = " " + PREFIX_FIND_NRIC + VALID_NRIC_BOB;
    public static final String FIND_DATE_DESC_AMY = " " + PREFIX_FIND_DATE + VALID_DATE_APPOINTMENT_AMY;
    public static final String FIND_DATE_DESC_BOB = " " + PREFIX_FIND_DATE + VALID_DATE_APPOINTMENT_BOB;
    public static final String FIND_START_TIME_DESC_AMY = " " + PREFIX_FIND_START_TIME
            + VALID_START_TIME_APPOINTMENT_AMY;
    public static final String FIND_START_TIME_DESC_BOB = " " + PREFIX_FIND_START_TIME
            + VALID_START_TIME_APPOINTMENT_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "T1234H"; // NRIC must be 9 characters long
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + "mother";
    public static final String INVALID_NOTE_DESC = " " + PREFIX_NOTE + " ";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final EditAppointmentCommand.EditAppointmentDescriptor DESC_APPOINTMENT_AMY;
    public static final EditAppointmentCommand.EditAppointmentDescriptor DESC_APPOINTMENT_BOB;

    public static final String VALID_DATE_APPOINTMENT = "22/10/2025"; // Example date in DD/MM/YYYY format
    public static final String VALID_START_TIME_APPOINTMENT = "10:00"; // Example start time in HH:mm format
    public static final LocalDateTime VALID_START_DATE_TIME_APPOINTMENT = LocalDateTime.of(
            LocalDate.parse(VALID_DATE_APPOINTMENT, DATE_FORMATTER),
            LocalTime.parse(VALID_START_TIME_APPOINTMENT, TIME_FORMATTER));

    public static final String INVALID_DATE_APPOINTMENT = "2025/10/10"; // Invalid date format in YYYY/MM/DD format
    public static final String VALID_END_TIME_APPOINTMENT = "11:00"; // Example end time in HH:mm format

    public static final String INVALID_END_TIME_APPOINTMENT = "1100";

    public static final String INVALID_START_TIME_APPOINTMENT = "1000";

    public static final String INVALID_START_TIME_DESC = " " + PREFIX_START_TIME + INVALID_START_TIME_APPOINTMENT;
    public static final String INVALID_END_TIME_DESC = " " + PREFIX_END_TIME + INVALID_END_TIME_APPOINTMENT;

    public static final String VALID_DATE_DESC_APPOINTMENT = " " + PREFIX_DATE + VALID_DATE_APPOINTMENT;
    public static final String VALID_START_TIME_DESC_APPOINTMENT = " " + PREFIX_START_TIME
            + VALID_START_TIME_APPOINTMENT;
    public static final String VALID_END_TIME_DESC_APPOINTMENT = " " + PREFIX_END_TIME + VALID_END_TIME_APPOINTMENT;

    public static final String VALID_DATE_DESC_APPOINTMENT_AMY = " " + PREFIX_DATE + VALID_DATE_APPOINTMENT_AMY;
    public static final String VALID_START_TIME_DESC_APPOINTMENT_AMY = " " + PREFIX_START_TIME
            + VALID_START_TIME_APPOINTMENT_AMY;
    public static final String VALID_END_TIME_DESC_APPOINTMENT_AMY = " " + PREFIX_END_TIME
            + VALID_END_TIME_APPOINTMENT_AMY;

    public static final String VALID_DATE_DESC_APPOINTMENT_BOB = " " + PREFIX_DATE + VALID_DATE_APPOINTMENT_BOB;
    public static final String VALID_START_TIME_DESC_APPOINTMENT_BOB = " " + PREFIX_START_TIME
            + VALID_START_TIME_APPOINTMENT_BOB;
    public static final String VALID_END_TIME_DESC_APPOINTMENT_BOB = " " + PREFIX_END_TIME
            + VALID_END_TIME_APPOINTMENT_BOB;

    public static final String VALID_NOTE_DESC = " " + PREFIX_NOTE + VALID_NOTE;

    public static final String INVALID_DATE_DESC_APPOINTMENT = " " + PREFIX_DATE
            + INVALID_DATE_APPOINTMENT; // Invalid date format in YYYY/MM/DD format

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).withNric(VALID_NRIC_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        DESC_APPOINTMENT_AMY = new EditAppointmentDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withNric(VALID_NRIC_AMY)
                .withDate(VALID_DATE_APPOINTMENT_AMY)
                .withStartTime(VALID_START_TIME_APPOINTMENT_AMY)
                .withEndTime(VALID_END_TIME_APPOINTMENT_AMY).build();
        DESC_APPOINTMENT_BOB = new EditAppointmentDescriptorBuilder()
                .withName(VALID_NAME_BOB)
                .withNric(VALID_NRIC_BOB)
                .withDate(VALID_DATE_APPOINTMENT_BOB)
                .withStartTime(VALID_START_TIME_APPOINTMENT_BOB)
                .withEndTime(VALID_END_TIME_APPOINTMENT_BOB).build();
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
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage} and also takes in three boolean values to set the
     * {@code CommandResult} to be of a certain type.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel, boolean showHelp, boolean exit,
            boolean findPerson) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, showHelp, exit, findPerson);
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
        ArgumentMultimap mapWithSplitName = new ArgumentMultimap();
        mapWithSplitName.put(PREFIX_NAME, splitName[0]);
        model.updateFilteredPersonList(new ContainsKeywordsPredicate(mapWithSplitName));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person with the given {@code nric} in the
     * {@code model}'s address book.
     */
    public static void showPersonWithNric(Model model, Nric nric) {
        Person person = null;
        for (Person p : model.getFilteredPersonList()) {
            if (p.getNric().equals(nric)) {
                person = p;
                break;
            }
        }
        assertNotEquals(null, person);
        final String[] splitName = person.getName().fullName.split("\\s+");
        ArgumentMultimap mapWithSplitName = new ArgumentMultimap();
        mapWithSplitName.put(PREFIX_NAME, splitName[0]);
        model.updateFilteredPersonList(new ContainsKeywordsPredicate(mapWithSplitName));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
