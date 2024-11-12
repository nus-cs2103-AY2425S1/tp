package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;
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
import seedu.address.model.wedding.NameMatchesWeddingPredicate;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonWithRoleDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "91234567";
    public static final String VALID_PHONE_BOB = "98765432";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_NAME_ALICEWEDDING = "Alice's Wedding";
    public static final String VALID_NAME_AMYWEDDING = "Amy's Wedding";
    public static final String VALID_NAME_BOBWEDDING = "Bob's Wedding";
    public static final String VALID_CLIENT_INDEX_ALICEWEDDING = "1";
    public static final String VALID_CLIENT_NAME_ALICEWEDDING = "Alice";
    public static final String VALID_CLIENT_AMYWEDDING = "2";
    public static final String VALID_DATE_ALICEWEDDING = "2024-12-12";
    public static final String VALID_DATE_AMYWEDDING = "2025-12-25";
    public static final String VALID_DATE_BOBWEDDING = "2020-11-11";
    public static final String VALID_VENUE_ALICEWEDDING = "Marina Bay Sands";
    public static final String VALID_VENUE_AMYWEDDING = "Amy's Wedding Venue";
    public static final String VALID_VENUE_BOBWEDDING = "Bob's Wedding Venue";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_ROLE + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_ROLE + VALID_TAG_HUSBAND;
    public static final String WEDDING_DESC_INDEX = " " + PREFIX_WEDDING + "1";
    public static final String NAME_DESC_ALICEWEDDING = " " + PREFIX_NAME + VALID_NAME_ALICEWEDDING;
    public static final String NAME_DESC_AMYWEDDING = " " + PREFIX_NAME + VALID_NAME_AMYWEDDING;
    public static final String CLIENT_INDEX_DESC_ALICEWEDDING = " " + PREFIX_CLIENT + VALID_CLIENT_INDEX_ALICEWEDDING;
    public static final String CLIENT_NAME_DESC_ALICEWEDDING = " " + PREFIX_CLIENT + VALID_CLIENT_NAME_ALICEWEDDING;
    public static final String CLIENT_DESC_AMYWEDDING = " " + PREFIX_CLIENT + VALID_CLIENT_AMYWEDDING;
    public static final String DATE_DESC_ALICEWEDDING = " " + PREFIX_DATE + VALID_DATE_ALICEWEDDING;
    public static final String DATE_DESC_AMYWEDDING = " " + PREFIX_DATE + VALID_DATE_AMYWEDDING;
    public static final String VENUE_DESC_ALICEWEDDING = " " + PREFIX_VENUE + VALID_VENUE_ALICEWEDDING;
    public static final String VENUE_DESC_AMYWEDDING = " " + PREFIX_VENUE + VALID_VENUE_AMYWEDDING;
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + "hubby*"; // '*' not allowed in role

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    // Add new TagPersonDescriptors
    public static final AssignCommand.PersonWithRoleDescriptor DESC_JON;
    public static final AssignCommand.PersonWithRoleDescriptor DESC_DOE;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .build();
        // Initialize PersonWithRoleDescriptor using the PersonWithRoleDescriptorBuilder
        DESC_JON = new PersonWithRoleDescriptorBuilder().withRole(VALID_TAG_FRIEND).build();
        DESC_DOE = new PersonWithRoleDescriptorBuilder().withRole(VALID_TAG_HUSBAND).build();
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
     * Updates {@code model}'s filtered list to show only the wedding at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showWeddingAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Wedding wedding = model.getFilteredWeddingList().get(targetIndex.getZeroBased());
        final String[] splitName = wedding.getName().fullName.split("\\s+");
        model.updateFilteredWeddingList(new NameMatchesWeddingPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredWeddingList().size());
    }
}
