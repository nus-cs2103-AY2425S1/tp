package seedu.hireme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.hireme.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.hireme.commons.core.index.Index;
import seedu.hireme.logic.commands.exceptions.CommandException;
import seedu.hireme.model.AddressBook;
import seedu.hireme.model.Model;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.NameContainsKeywordsPredicate;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_COMPANY_NAME_GOOGLE = "Google";
    public static final String VALID_EMAIL_GOOGLE = "google@gmail.com";
    public static final String VALID_DATE_GOOGLE = "01/01/06";
    public static final String VALID_ROLE_GOOGLE = "SWE";

    public static final String VALID_COMPANY_NAME_YAHOO = "Yahoo";
    public static final String VALID_EMAIL_YAHOO = "yahoo@yahoo.com";
    public static final String VALID_DATE_YAHOO = "01/01/07";
    public static final String VALID_ROLE_YAHOO = "Clerk";

    public static final String VALID_COMPANY_NAME_APPLE = "Apple";
    public static final String VALID_COMPANY_NAME_BOFA = "BOFA";
    public static final String VALID_DATE_APPLE = "01/01/09";
    public static final String VALID_DATE_BOFA = "01/01/10";
    public static final String VALID_COMPANY_EMAIL_APPLE = "apple@example.com";
    public static final String VALID_COMPANY_EMAIL_BOFA = "bofa@example.com";
    public static final String VALID_ROLE_APPLE = "Software Engineer Intern";
    public static final String VALID_ROLE_BOFA = "Backend Intern";

    public static final String COMPANY_NAME_DESC_APPLE = " " + PREFIX_NAME + VALID_COMPANY_NAME_APPLE;
    public static final String COMPANY_NAME_DESC_BOFA = " " + PREFIX_NAME + VALID_COMPANY_NAME_BOFA;
    public static final String DATE_DESC_APPLE = " " + PREFIX_DATE + VALID_DATE_APPLE;
    public static final String DATE_DESC_BOFA = " " + PREFIX_DATE + VALID_DATE_BOFA;
    public static final String COMPANY_EMAIL_DESC_APPLE = " " + PREFIX_EMAIL + VALID_COMPANY_EMAIL_APPLE;
    public static final String COMPANY_EMAIL_DESC_BOFA = " " + PREFIX_EMAIL + VALID_COMPANY_EMAIL_BOFA;
    public static final String ROLE_DESC_APPLE = " " + PREFIX_ROLE + VALID_ROLE_APPLE;
    public static final String ROLE_DESC_BOFA = " " + PREFIX_ROLE + VALID_ROLE_BOFA;

    public static final String INVALID_COMPANY_NAME_DESC = " " + PREFIX_NAME + "Google!"; // '&' not allowed in names
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "911a"; // 'a' not allowed in dates
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bofa!yahoo"; // missing '@' symbol
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE; // empty string not allowed for role
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command<InternshipApplication> command, Model<InternshipApplication>
            actualModel, CommandResult expectedCommandResult,
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
    public static void assertCommandSuccess(Command<InternshipApplication> command, Model<InternshipApplication>
            actualModel, String expectedMessage,
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
    public static void assertCommandFailure(Command<InternshipApplication> command, Model<InternshipApplication>
            actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook<InternshipApplication> expectedAddressBook = new AddressBook<>(actualModel.getAddressBook());
        List<InternshipApplication> expectedFilteredList = new ArrayList<>(actualModel.getFilteredList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showInternshipApplicationAtIndex(Model<InternshipApplication> model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredList().size());

        InternshipApplication internshipApplication = model.getFilteredList().get(targetIndex.getZeroBased());
        final String[] splitName = internshipApplication.getCompany().getName().getValue().split("\\s+");
        model.updateFilteredList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredList().size());
    }

}
