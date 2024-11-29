//@@author
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM_HANDLE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContainsKeywordsPredicate;
import seedu.address.testutil.EditContactDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_TELEGRAM_HANDLE_AMY = "11111111";
    public static final String VALID_TELEGRAM_HANDLE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ROLE_PRESIDENT = "President";
    public static final String VALID_ROLE_ADMIN = "Admin";
    public static final String VALID_STUDENT_STATUS_AMY = "undergraduate 1";
    public static final String VALID_STUDENT_STATUS_BOB = "masters";
    public static final String VALID_NICKNAME_AMY = "amy";
    public static final String VALID_NICKNAME_BOB = "bob";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String TELEGRAM_HANDLE_DESC_AMY = " " + PREFIX_TELEGRAM_HANDLE + VALID_TELEGRAM_HANDLE_AMY;
    public static final String TELEGRAM_HANDLE_DESC_BOB = " " + PREFIX_TELEGRAM_HANDLE + VALID_TELEGRAM_HANDLE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String STUDENT_STATUS_DESC_AMY = " " + PREFIX_STUDENT_STATUS + VALID_STUDENT_STATUS_AMY;
    public static final String STUDENT_STATUS_DESC_BOB = " " + PREFIX_STUDENT_STATUS + VALID_STUDENT_STATUS_BOB;
    public static final String ROLE_DESC_ADMIN = " " + PREFIX_ROLE + VALID_ROLE_ADMIN;
    public static final String ROLE_DESC_PRESIDENT = " " + PREFIX_ROLE + VALID_ROLE_PRESIDENT;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_TELEGRAM_HANDLE_DESC = " "
            + PREFIX_TELEGRAM_HANDLE + "@911"; // '@' not allowed in telegrams
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_STUDENT_STATUS_DESC = " " + PREFIX_STUDENT_STATUS; // empty string not allowed
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditContactDescriptor DESC_AMY;
    public static final EditCommand.EditContactDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY)
                .withTelegramHandle(VALID_TELEGRAM_HANDLE_AMY)
                .withEmail(VALID_EMAIL_AMY).withStudentStatus(VALID_STUDENT_STATUS_AMY)
                .withRoles(VALID_ROLE_ADMIN).build();
        DESC_BOB = new EditContactDescriptorBuilder().withName(VALID_NAME_BOB)
                .withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB)
                .withEmail(VALID_EMAIL_BOB).withStudentStatus(VALID_STUDENT_STATUS_BOB)
                .withRoles(VALID_ROLE_PRESIDENT, VALID_ROLE_ADMIN).build();
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
     * - the address book, filtered contact list and selected contact in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Contact> expectedFilteredList = new ArrayList<>(actualModel.getFilteredContactList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredContactList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the contact at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showContactAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredContactList().size());

        Contact contact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        final String[] splitName = contact.getName().fullName.split("\\s+");
        model.updateFilteredContactList(new ContainsKeywordsPredicate(
                Arrays.asList(splitName[0]), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));

        assertEquals(1, model.getFilteredContactList().size());
    }

}
