package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NETID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
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

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_STUDENTID_AMY = "A1111111E";
    public static final String VALID_STUDENTID_BOB = "A2222222P";
    public static final String VALID_EMAIL_AMY = "e1124567@u.nus.edu";
    public static final String VALID_EMAIL_BOB = "e1114566@u.nus.edu";
    public static final String VALID_NETID_AMY = "e1124567";
    public static final String VALID_NETID_BOB = "e1114566";
    public static final String VALID_MAJOR_AMY = "Computer Science";
    public static final String VALID_MAJOR_BOB = "Law";
    public static final String VALID_YEAR_AMY = "1";
    public static final String VALID_YEAR_BOB = "3";
    public static final String VALID_COMMENT_BOB = "Late for class";
    public static final String VALID_GROUP_ONE = "group 1";
    public static final String VALID_GROUP_TWO = "group 2";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String STUDENTID_DESC_AMY = " " + PREFIX_STUDENTID + VALID_STUDENTID_AMY;
    public static final String STUDENTID_DESC_BOB = " " + PREFIX_STUDENTID + VALID_STUDENTID_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_NETID + VALID_NETID_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_NETID + VALID_NETID_BOB;
    public static final String MAJOR_DESC_AMY = " " + PREFIX_MAJOR + VALID_MAJOR_AMY;
    public static final String MAJOR_DESC_BOB = " " + PREFIX_MAJOR + VALID_MAJOR_BOB;
    public static final String YEAR_DESC_AMY = " " + PREFIX_YEAR + VALID_YEAR_AMY;
    public static final String YEAR_DESC_BOB = " " + PREFIX_YEAR + VALID_YEAR_BOB;
    public static final String GROUP_DESC_TWO = " " + PREFIX_GROUP + VALID_GROUP_TWO;
    public static final String GROUP_DESC_ONE = " " + PREFIX_GROUP + VALID_GROUP_ONE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME; // empty string not allowed for name
    public static final String INVALID_STUDENTID_DESC = " " + PREFIX_STUDENTID + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_NETID + "12345678"; // first character is a number
    public static final String INVALID_MAJOR_DESC = " " + PREFIX_MAJOR; // empty string not allowed for major
    // Test case with 101 characters
    public static final String INVALID_GROUP_DESC = " " + PREFIX_GROUP
            + "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque ullamcorper sollicitudin sem, ut abc";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withStudentId(VALID_STUDENTID_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_MAJOR_AMY)
                .withGroups(VALID_GROUP_TWO).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withStudentId(VALID_STUDENTID_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_MAJOR_BOB)
                .withGroups(VALID_GROUP_ONE, VALID_GROUP_TWO).build();
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
