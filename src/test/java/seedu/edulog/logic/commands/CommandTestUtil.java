package seedu.edulog.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_FEE;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_START_DAY;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.edulog.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.edulog.commons.core.index.Index;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.EduLog;
import seedu.edulog.model.Model;
import seedu.edulog.model.calendar.Description;
import seedu.edulog.model.student.NameContainsKeywordsPredicate;
import seedu.edulog.model.student.Student;
import seedu.edulog.testutil.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Student command Strings
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
    public static final String VALID_FEE = "100";

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
    public static final String FEE = " " + PREFIX_FEE + VALID_FEE;

    public static final String INVALID_NAME_DESC_SYMBOL = " " + PREFIX_NAME + "James&"; // '&' not allowed in names

    public static final String INVALID_NAME_DESC_NUMERIC = " " + PREFIX_NAME + "12345"; // all-numeric names not allowed
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditStudentDescriptor DESC_AMY;
    public static final EditCommand.EditStudentDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    // Lesson command Strings
    public static final String VALID_DESCRIPTION_MATH = "MATH";
    public static final String VALID_DESCRIPTION_BIO = "BIO";
    public static final String VALID_DAY_BIO = "MONDAY";
    public static final String VALID_DAY_MATH = "wED";
    public static final String VALID_START_TIME_BIO = "1400";
    public static final String VALID_START_TIME_MATH = "2300";
    public static final String VALID_END_TIME_BIO = "1700";
    public static final String VALID_END_TIME_MATH = "0100";

    public static final String DESCRIPTION_DESC_BIO = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BIO;
    public static final String DESCRIPTION_DESC_MATH = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_MATH;
    public static final String DAY_DESC_BIO = " " + PREFIX_START_DAY + VALID_DAY_BIO;
    public static final String DAY_DESC_MATH = " " + PREFIX_START_DAY + VALID_DAY_MATH;
    public static final String START_TIME_DESC_BIO = " " + PREFIX_START_TIME + VALID_START_TIME_BIO;
    public static final String START_TIME_DESC_MATH = " " + PREFIX_START_TIME + VALID_START_TIME_MATH;
    public static final String END_TIME_DESC_BIO = " " + PREFIX_END_TIME + VALID_END_TIME_BIO;
    public static final String END_TIME_DESC_MATH = " " + PREFIX_END_TIME + VALID_END_TIME_MATH;

    // lesson descriptions cannot be purely whitespace
    public static final String INVALID_DESCRIPTION_DESC_EMPTY = " " + PREFIX_DESCRIPTION + "   ";

    // lesson descriptions must fit within the provided description space
    public static final String INVALID_DESCRIPTION_DESC_TOO_LONG =
        " " + PREFIX_DESCRIPTION + "A".repeat(Description.MAX_CHARACTER_LIMIT + 1);

    // Day of week not spelt properly
    public static final String INVALID_DAY_DESC = " " + PREFIX_START_DAY + "Mondya";

    // Time does not fit in 24-hour format
    public static final String INVALID_TIME_DESC = " " + PREFIX_START_TIME + "2469";

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
     * - the edulog book, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        EduLog expectedEduLog = new EduLog(actualModel.getEduLog());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedEduLog, actualModel.getEduLog());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s edulog book.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = student.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }

}
