package keycontacts.logic.commands;

import static keycontacts.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DAY;
import static keycontacts.logic.parser.CliSyntax.PREFIX_END_TIME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_GRADE_LEVEL;
import static keycontacts.logic.parser.CliSyntax.PREFIX_NAME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PHONE;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PIECE_NAME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_START_TIME;
import static keycontacts.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;
import keycontacts.model.StudentDirectory;
import keycontacts.model.student.Student;
import keycontacts.model.student.StudentDescriptorMatchesPredicate;
import keycontacts.testutil.EditStudentDescriptorBuilder;
import keycontacts.testutil.FindStudentDescriptorBuilder;

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
    public static final String VALID_GRADE_LEVEL_AMY = "RSL 2";
    public static final String VALID_GRADE_LEVEL_BOB = "ABRSM 3";
    public static final String VALID_PIANO_PIECE_BEETHOVEN = "Für Elise";
    public static final String VALID_PIANO_PIECE_PACHELBEL = "Canon in D";
    public static final String VALID_DAY = "Monday";
    public static final String VALID_START_TIME = "14:00";
    public static final String VALID_END_TIME = "16:00";
    public static final String VALID_DATE = "06-07-2022";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String GRADE_LEVEL_DESC_AMY = " " + PREFIX_GRADE_LEVEL + VALID_GRADE_LEVEL_AMY;
    public static final String GRADE_LEVEL_DESC_BOB = " " + PREFIX_GRADE_LEVEL + VALID_GRADE_LEVEL_BOB;
    public static final String PIANO_PIECE_DESC_BEETHOVEN = " " + PREFIX_PIECE_NAME + VALID_PIANO_PIECE_BEETHOVEN;
    public static final String PIANO_PIECE_DESC_PACHELBEL = " " + PREFIX_PIECE_NAME + VALID_PIANO_PIECE_PACHELBEL;
    public static final String VALID_DAY_DESC = " " + PREFIX_DAY + VALID_DAY;
    public static final String VALID_START_TIME_DESC = " " + PREFIX_START_TIME + VALID_START_TIME;
    public static final String VALID_END_TIME_DESC = " " + PREFIX_END_TIME + VALID_END_TIME;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    // '*' not allowed in grade levels
    public static final String INVALID_GRADE_LEVEL_DESC = " " + PREFIX_GRADE_LEVEL + "RSL*";
    public static final String INVALID_DAY_DESC = " " + PREFIX_DAY + "day"; // not a day of the week
    public static final String INVALID_START_TIME_DESC = " " + PREFIX_START_TIME + "10am"; // not in 24 hour format
    public static final String INVALID_END_TIME_DESC = " " + PREFIX_END_TIME + "10pm"; // not in 24 hour format
    public static final String INVALID_DATE = "13-13-2024"; // invalid month
    public static final String INVALID_PIANO_PIECE_DESC = " " + PREFIX_PIECE_NAME + " ";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditStudentDescriptor DESC_AMY;
    public static final EditCommand.EditStudentDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withAddress(VALID_ADDRESS_AMY).withGradeLevel(VALID_GRADE_LEVEL_AMY)
                .build();
        DESC_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB).withGradeLevel(VALID_GRADE_LEVEL_BOB)
                .build();
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
     * - the student directory, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        StudentDirectory expectedStudentDirectory = new StudentDirectory(actualModel.getStudentDirectory());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedStudentDirectory, actualModel.getStudentDirectory());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s student directory.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        FindStudentDescriptorBuilder builder = new FindStudentDescriptorBuilder();
        builder.withName(student.getName().fullName);
        model.updateFilteredStudentList(new StudentDescriptorMatchesPredicate(builder.build()));

        assertEquals(1, model.getFilteredStudentList().size());
    }

}
