package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.internshipapplication.InternshipApplication;
import seedu.address.model.internshipapplication.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_COMPANY_NAME_GOOGLE = "Google";
    public static final String VALID_EMAIL_GOOGLE = "google@gmail.com";
    public static final String VALID_DATE_GOOGLE = "04/09/98";
    public static final String VALID_ROLE_GOOGLE = "SWE";

    public static final String VALID_COMPANY_NAME_YAHOO = "Yahoo";
    public static final String VALID_EMAIL_YAHOO = "yahoo@yahoo.com";
    public static final String VALID_DATE_YAHOO = "01/01/94";
    public static final String VALID_ROLE_YAHOO = "Clerk";

    public static final String VALID_COMPANY_NAME_APPLE = "Apple";
    public static final String VALID_COMPANY_NAME_BOFA = "BOFA";
    public static final String VALID_DATE_APPLE = "01/01/24";
    public static final String VALID_DATE_BOFA = "02/02/24";
    public static final String VALID_COMPANY_EMAIL_APPLE = "apple@example.com";
    public static final String VALID_COMPANY_EMAIL_BOFA = "bofa@example.com";
    public static final String VALID_ROLE_APPLE = "Software Engineer Intern";
    public static final String VALID_ROLE_BOFA = "Backend Intern";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String COMPANY_NAME_DESC_APPLE = " " + PREFIX_NAME + VALID_COMPANY_NAME_APPLE;
    public static final String COMPANY_NAME_DESC_BOFA = " " + PREFIX_NAME + VALID_COMPANY_NAME_BOFA;
    public static final String DATE_DESC_APPLE = " " + PREFIX_DATE + VALID_DATE_APPLE;
    public static final String DATE_DESC_BOFA = " " + PREFIX_DATE + VALID_DATE_BOFA;
    public static final String COMPANY_EMAIL_DESC_APPLE = " " + PREFIX_EMAIL + VALID_COMPANY_EMAIL_APPLE;
    public static final String COMPANY_EMAIL_DESC_BOFA = " " + PREFIX_EMAIL + VALID_COMPANY_EMAIL_BOFA;
    public static final String ROLE_DESC_APPLE = " " + PREFIX_ROLE + VALID_ROLE_APPLE;
    public static final String ROLE_DESC_BOFA = " " + PREFIX_ROLE + VALID_ROLE_BOFA;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "911a"; // 'a' not allowed in dates
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bofa!yahoo"; // missing '@' symbol
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE; // empty string not allowed for role
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    //TODO Delete the unnecessary fields. These are here got tests legacy purposes
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses

    public static final EditCommand.EditPersonDescriptor DESC_AMY;

    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).build();
    }
    //The list ends here

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_APPLE;
    public static final EditCommand.EditPersonDescriptor DESC_BOFA;

    static {
        DESC_APPLE = new EditPersonDescriptorBuilder().withName(VALID_COMPANY_NAME_APPLE)
                .withPhone(VALID_DATE_APPLE).withEmail(VALID_COMPANY_EMAIL_APPLE).withAddress(VALID_ROLE_APPLE)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOFA = new EditPersonDescriptorBuilder().withName(VALID_COMPANY_NAME_BOFA)
                .withPhone(VALID_DATE_BOFA).withEmail(VALID_COMPANY_EMAIL_BOFA).withAddress(VALID_ROLE_BOFA)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command<InternshipApplication> command, Model<InternshipApplication> actualModel, CommandResult expectedCommandResult,
            Model<InternshipApplication> expectedModel) {
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
    public static void assertCommandSuccess(Command<InternshipApplication> command, Model<InternshipApplication> actualModel, String expectedMessage,
            Model<InternshipApplication> expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command<InternshipApplication> command, Model<InternshipApplication> actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<InternshipApplication> expectedFilteredList = new ArrayList<>(actualModel.getFilteredList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model<InternshipApplication> model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredList().size());

        InternshipApplication internshipApplication = model.getFilteredList().get(targetIndex.getZeroBased());
        final String[] splitName = internshipApplication.getCompany().getName().getValue().split("\\s+");
        model.updateFilteredList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredList().size());
    }

}
