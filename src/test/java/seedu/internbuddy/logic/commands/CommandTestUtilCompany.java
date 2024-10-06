package seedu.internbuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.internbuddy.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.commands.exceptions.CommandException;
import seedu.internbuddy.model.AddressBook;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.person.NameContainsKeywordsPredicate;
import seedu.internbuddy.model.person.Person;
import seedu.internbuddy.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtilCompany {

    public static final String VALID_NAME_GOOGLE = "Google LLC";
    public static final String VALID_NAME_MICROSOFT = "Microsoft Corporation";
    public static final String VALID_PHONE_GOOGLE = "12345678";
    public static final String VALID_PHONE_MICROSOFT = "87654321";
    public static final String VALID_EMAIL_GOOGLE = "contact@google.com";
    public static final String VALID_EMAIL_MICROSOFT = "contact@microsoft.com";
    public static final String VALID_ADDRESS_GOOGLE = "1600 Amphitheatre Parkway, Mountain View, CA";
    public static final String VALID_ADDRESS_MICROSOFT = "One Microsoft Way, Redmond, WA";
    public static final String VALID_TAG_TECH = "tech";
    public static final String VALID_TAG_SOFTWARE = "software";

    public static final String NAME_DESC_GOOGLE = " " + PREFIX_NAME + VALID_NAME_GOOGLE;
    public static final String NAME_DESC_MICROSOFT = " " + PREFIX_NAME + VALID_NAME_MICROSOFT;
    public static final String PHONE_DESC_GOOGLE = " " + PREFIX_PHONE + VALID_PHONE_GOOGLE;
    public static final String PHONE_DESC_MICROSOFT = " " + PREFIX_PHONE + VALID_PHONE_MICROSOFT;
    public static final String EMAIL_DESC_GOOGLE = " " + PREFIX_EMAIL + VALID_EMAIL_GOOGLE;
    public static final String EMAIL_DESC_MICROSOFT = " " + PREFIX_EMAIL + VALID_EMAIL_MICROSOFT;
    public static final String ADDRESS_DESC_GOOGLE = " " + PREFIX_ADDRESS + VALID_ADDRESS_GOOGLE;
    public static final String ADDRESS_DESC_MICROSOFT = " " + PREFIX_ADDRESS + VALID_ADDRESS_MICROSOFT;
    public static final String TAG_DESC_TECH = " " + PREFIX_TAG + VALID_TAG_TECH;
    public static final String TAG_DESC_SOFTWARE = " " + PREFIX_TAG + VALID_TAG_SOFTWARE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Invalid&Name"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "invalid!email"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "invalid*tag"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_GOOGLE;
    public static final EditCommand.EditPersonDescriptor DESC_MICROSOFT;

    static {
        DESC_GOOGLE = new EditPersonDescriptorBuilder().withName(VALID_NAME_GOOGLE)
                .withPhone(VALID_PHONE_GOOGLE).withEmail(VALID_EMAIL_GOOGLE).withAddress(VALID_ADDRESS_GOOGLE)
                .withTags(VALID_TAG_TECH).build();
        DESC_MICROSOFT = new EditPersonDescriptorBuilder().withName(VALID_NAME_MICROSOFT)
                .withPhone(VALID_PHONE_MICROSOFT).withEmail(VALID_EMAIL_MICROSOFT).withAddress(VALID_ADDRESS_MICROSOFT)
                .withTags(VALID_TAG_SOFTWARE, VALID_TAG_TECH).build();
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
