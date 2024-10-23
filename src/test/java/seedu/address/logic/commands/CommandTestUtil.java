package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;
import seedu.address.testutil.UpdateTaskDescriptorBuilder;
import seedu.address.ui.Ui.UiState;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_EMERGENCY_CONTACT_AMY = "44444444";
    public static final String VALID_EMERGENCY_CONTACT_BOB = "33333333";
    public static final String VALID_SUBJECT_MATH = "MATH";
    public static final String VALID_SUBJECT_ENGLISH = "ENGLISH";
    public static final String VALID_LEVEL_K2 = "K2";
    public static final String VALID_LEVEL_P3 = "P3";
    public static final String VALID_LEVEL_S4 = "S4";
    public static final String LEVEL_DESC_K2 = " " + PREFIX_LEVEL + VALID_LEVEL_K2;
    public static final String LEVEL_DESC_P3 = " " + PREFIX_LEVEL + VALID_LEVEL_P3;
    public static final String LEVEL_DESC_S4 = " " + PREFIX_LEVEL + VALID_LEVEL_S4;
    public static final String VALID_NOTE_AMY = "Likes asking questions.";
    public static final String VALID_NOTE_BOB = "Always sleeping";
    public static final String VALID_TASK_DESCRIPTION_AMY = "Mark homework";
    public static final String VALID_TASK_DESCRIPTION_PROJECT = "Do project";
    public static final String VALID_TASK_DEADLINE = "2024-10-15";
    public static final String VALID_TASK_DEADLINE_AMY = "2024-01-01";
    public static final String VALID_TASK_INDEX = "1";
    public static final String VALID_LESSON_TIME = "SUN-11:00-13:00";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMERGENCY_CONTACT_DESC_AMY =
            " " + PREFIX_EMERGENCY_CONTACT + VALID_EMERGENCY_CONTACT_AMY;
    public static final String EMERGENCY_CONTACT_DESC_BOB =
            " " + PREFIX_EMERGENCY_CONTACT + VALID_EMERGENCY_CONTACT_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String NOTE_DESC_AMY = " " + PREFIX_NOTE + "";
    public static final String NOTE_DESC_BOB = " " + PREFIX_NOTE + "";
    public static final String SUBJECT_DESC_ENGLISH = " " + PREFIX_SUBJECT + VALID_SUBJECT_ENGLISH;
    public static final String SUBJECT_DESC_MATH = " " + PREFIX_SUBJECT + VALID_SUBJECT_MATH;
    public static final String TASK_DESCRIPTION_DESC_AMY = " " + PREFIX_TASK_DESCRIPTION + VALID_TASK_DESCRIPTION_AMY;
    public static final String TASK_DESCRIPTION_DESC_BOB =
            " " + PREFIX_TASK_DESCRIPTION + VALID_TASK_DESCRIPTION_PROJECT;
    public static final String TASK_DEADLINE_DESC_AMY = " " + PREFIX_TASK_DEADLINE + VALID_TASK_DEADLINE_AMY;
    public static final String TASK_DEADLINE_DESC_BOB = " " + PREFIX_TASK_DEADLINE + VALID_TASK_DEADLINE;
    public static final String TASK_INDEX_DESC = " " + PREFIX_TASK_INDEX + VALID_TASK_INDEX;
    public static final String LESSON_TIME_DESC = " " + PREFIX_LESSON_TIME + VALID_LESSON_TIME;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMERGENCY_CONTACT_DESC =
            " " + PREFIX_EMERGENCY_CONTACT + "911b"; // "b" not allowed in phones
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_SUBJECT_DESC = " " + PREFIX_SUBJECT + "MATH*"; // '*' not allowed in subjects
    public static final String INVALID_LEVEL_DESC = " " + PREFIX_LEVEL + "P7";
    public static final String INVALID_TASK_DESC = " " + PREFIX_TASK_DESCRIPTION + "   "; // blank task description
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_TASK_DEADLINE + "2024-14-23"; // invalid month
    public static final String INVALID_TASK_INDEX = " " + PREFIX_TASK_INDEX + "1!";
    public static final String INVALID_LESSON_TIME_DESC = " " + PREFIX_LESSON_TIME + "every thurs";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final UpdateCommand.UpdatePersonDescriptor DESC_AMY;
    public static final UpdateCommand.UpdatePersonDescriptor DESC_BOB;
    public static final UpdateTaskCommand.UpdateTaskDescriptor DESC_TASK_AMY;
    public static final UpdateTaskCommand.UpdateTaskDescriptor DESC_TASK_BOB;

    static {
        DESC_AMY = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmergencyContact(VALID_EMERGENCY_CONTACT_AMY)
                .withAddress(VALID_ADDRESS_AMY).withNote("").withSubjects(VALID_SUBJECT_ENGLISH).build();
        DESC_BOB = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmergencyContact(VALID_EMERGENCY_CONTACT_BOB)
                .withAddress(VALID_ADDRESS_BOB).withSubjects(VALID_SUBJECT_MATH, VALID_SUBJECT_ENGLISH).build();
        DESC_TASK_AMY = new UpdateTaskDescriptorBuilder().withTaskDescription(VALID_TASK_DESCRIPTION_AMY)
                .withTaskDeadline(VALID_TASK_DEADLINE_AMY).build();
        DESC_TASK_BOB = new UpdateTaskDescriptorBuilder().withTaskDescription(VALID_TASK_DESCRIPTION_PROJECT)
                .withTaskDeadline(VALID_TASK_DEADLINE).build();
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
            UiState expectedUiState, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, expectedUiState);
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
