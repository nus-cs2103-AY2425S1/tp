package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;
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
import seedu.address.model.student.Student;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // ----------------- Student related fields --------------------------------------------
    public static final String VALID_NAME_HUGH = "Hugh Jackman";
    public static final String VALID_NAME_DIDDY = "Puff Daddy";
    public static final String VALID_PHONE_HUGH = "33333333";
    public static final String VALID_PHONE_DIDDY = "44444444";
    public static final String VALID_TUTORIAL_GROUP_HUGH = "G03";
    public static final String VALID_TUTORIAL_GROUP_DIDDY = "G04";
    public static final String VALID_STUDENT_NUMBER_HUGH = "A1234567A";
    public static final String VALID_STUDENT_NUMBER_DIDDY = "A7654321A";
    public static final String INVALID_STUDENT_NUMBER_TOO_FEW_NUMBERS = "A76B";
    public static final String INVALID_STUDENT_NUMBER_MISSING_LETTER = "A7654321";

    public static final String NAME_DESC_HUGH = " " + PREFIX_NAME + VALID_NAME_HUGH;
    public static final String NAME_DESC_DIDDY = " " + PREFIX_NAME + VALID_NAME_DIDDY;
    public static final String PHONE_DESC_HUGH = " " + PREFIX_PHONE + VALID_PHONE_HUGH;
    public static final String PHONE_DESC_DIDDY = " " + PREFIX_PHONE + VALID_PHONE_DIDDY;
    public static final String TUTORIAL_GROUP_DESC_HUGH = " " + PREFIX_TUTORIAL_GROUP + VALID_TUTORIAL_GROUP_HUGH;
    public static final String TUTORIAL_GROUP_DESC_DIDDY = " " + PREFIX_TUTORIAL_GROUP + VALID_TUTORIAL_GROUP_DIDDY;
    public static final String STUDENT_NUMBER_DESC_HUGH = " " + PREFIX_STUDENT_NUMBER + VALID_STUDENT_NUMBER_HUGH;
    public static final String STUDENT_NUMBER_DESC_DIDDY = " " + PREFIX_STUDENT_NUMBER + VALID_STUDENT_NUMBER_DIDDY;
    public static final String INVALID_TUTORIAL_GROUP_DESC = " " + PREFIX_TUTORIAL_GROUP
            + "G0!"; // '!' not allowed in tutorial groups
    public static final String INVALID_STUDENT_NUMBER_DESC = " " + PREFIX_STUDENT_NUMBER
            + "A1234567"; // missing last character

    // ----------------- Person related fields --------------------------------------------
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    // ----------------- Assignment related fields --------------------------------------------
    public static final String VALID_ASSIGNMENT_MATH = "Math Assignment";
    public static final String VALID_ASSIGNMENT_SCIENCE = "Science Project";

    // Valid deadline inputs
    public static final String VALID_DEADLINE_2024_10_20 = "2024-10-20";
    public static final String VALID_DEADLINE_2023_12_25 = "2023-12-25";

    // Valid status inputs
    public static final String VALID_STATUS_Y = "Y";
    public static final String VALID_STATUS_N = "N";

    // Valid grade inputs
    public static final String VALID_GRADE_80 = "80";
    public static final String VALID_GRADE_95 = "95";

    // Invalid inputs for testing
    public static final String INVALID_ASSIGNMENT_DESC = " " + PREFIX_ASSIGNMENT + "!!!Science";
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS + "done";
    public static final String INVALID_GRADE_DESC = " " + PREFIX_GRADE + "120";
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DEADLINE + "20th October";

    public static final String STATUS_DESC_Y = " " + PREFIX_STATUS + VALID_STATUS_Y;
    public static final String STATUS_DESC_N = " " + PREFIX_STATUS + VALID_STATUS_N;

    public static final String ASSIGNMENT_DESC_MATH = " " + PREFIX_ASSIGNMENT + VALID_ASSIGNMENT_MATH;
    public static final String ASSIGNMENT_DESC_SCIENCE = " " + PREFIX_ASSIGNMENT + VALID_ASSIGNMENT_SCIENCE;

    public static final String DEADLINE_DESC_2024_10_20 = " " + PREFIX_DEADLINE + VALID_DEADLINE_2024_10_20;
    public static final String DEADLINE_DESC_2023_12_25 = " " + PREFIX_DEADLINE + VALID_DEADLINE_2023_12_25;

    public static final String GRADE_DESC_80 = " " + PREFIX_GRADE + VALID_GRADE_80;
    public static final String GRADE_DESC_95 = " " + PREFIX_GRADE + VALID_GRADE_95;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    // ----------------- Person edit descriptors --------------------------------------------
    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    public static final EditStudentCommand.EditStudentDescriptor DESC_HUGH;
    public static final EditStudentCommand.EditStudentDescriptor DESC_DIDDY;

    // ----------------- Student edit descriptors --------------------------------------------
    static {
        DESC_HUGH = new EditStudentDescriptorBuilder().withName(VALID_NAME_HUGH)
                .withPhone(VALID_PHONE_HUGH).withTutorialGroup(VALID_TUTORIAL_GROUP_HUGH)
                .withStudentNumber(VALID_STUDENT_NUMBER_HUGH).build();
        DESC_DIDDY = new EditStudentDescriptorBuilder().withName(VALID_NAME_DIDDY)
                .withPhone(VALID_PHONE_DIDDY).withTutorialGroup(VALID_TUTORIAL_GROUP_DIDDY)
                .withStudentNumber(VALID_STUDENT_NUMBER_DIDDY).build();
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

    /**
     * Updates {@code model}'s filtered list to show only the student at the with the given {@code name} in the
     * {@code model}'s address book.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        model.updateFilteredStudentList(s -> s.getName().equals(student.getName()));

        assertEquals(1, model.getFilteredStudentList().size());
    }

}
