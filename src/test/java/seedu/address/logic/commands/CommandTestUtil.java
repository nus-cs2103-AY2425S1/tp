package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_NAME;
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
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingNameContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_ALICE = "Alice Pauline";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_DANIEL = "Daniel Meier";
    public static final String VALID_NAME_FIONA = "Fiona Kunz";
    public static final String VALID_NAME_GEORGE = "George Best";
    public static final String VALID_NAME_BENSON = "Benson Meier";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_JOB_AMY = "Caterer";
    public static final String VALID_JOB_BOB = "Photographer";
    public static final String VALID_TAG_WEDDING1 = "John Loh & Jean Tan";
    public static final String VALID_TAG_WEDDING2 = "James Tan & Emily Koh";
    public static final String VALID_TAG_WEDDING3 = "Jane Tan & Tom Cruise";
    public static final String VALID_TAG_WEDDING4 = "Jim Lee & Joe Goldberg";


    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String JOB_DESC_AMY = " " + PREFIX_JOB + VALID_JOB_AMY;
    public static final String JOB_DESC_BOB = " " + PREFIX_JOB + VALID_JOB_BOB;
    public static final String TAG_DESC_WEDDING1 = " " + PREFIX_TAG + VALID_TAG_WEDDING1;
    public static final String TAG_DESC_WEDDING2 = " " + PREFIX_TAG + VALID_TAG_WEDDING2;

    public static final String INVALID_NAME = "Android";

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final String VALID_WEDDING_NAME_ONE = "John Loh & Jean Tan";
    public static final String VALID_WEDDING_NAME_TWO = "Alice Tan & Bob Lee";
    public static final String VALID_VENUE_ONE = "Orchard Hotel";
    public static final String VALID_VENUE_TWO = "Botanic Gardens";
    public static final String VALID_DATE_ONE = "12/11/2024";
    public static final String VALID_DATE_TWO = "25/12/2024";

    public static final String WEDDING_NAME_DESC_ONE = " " + PREFIX_WEDDING_NAME + VALID_WEDDING_NAME_ONE;
    public static final String WEDDING_NAME_DESC_TWO = " " + PREFIX_WEDDING_NAME + VALID_WEDDING_NAME_TWO;
    public static final String VENUE_DESC_ONE = " " + PREFIX_VENUE + VALID_VENUE_ONE;
    public static final String VENUE_DESC_TWO = " " + PREFIX_VENUE + VALID_VENUE_TWO;
    public static final String DATE_DESC_ONE = " " + PREFIX_DATE + VALID_DATE_ONE;
    public static final String DATE_DESC_TWO = " " + PREFIX_DATE + VALID_DATE_TWO;

    public static final String INVALID_WEDDING_NAME_DESC = " " + PREFIX_WEDDING_NAME
            + "John Loh and Jean Tan"; // 'and' not allowed
    public static final String INVALID_VENUE_DESC = " " + PREFIX_VENUE + " "; // empty venue description not allowed
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "42/02/2023"; // invalid date



    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withJob(VALID_JOB_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withJob(VALID_JOB_BOB).build();
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
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showWeddingAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredWeddingList().size());

        Wedding wedding = model.getFilteredWeddingList().get(targetIndex.getZeroBased());
        final String[] splitWeddingName = wedding.getWeddingName().toString().split("\\s+");
        model.updateFilteredWeddingList(new WeddingNameContainsKeywordsPredicate(Arrays.asList(splitWeddingName[0])));

        assertEquals(1, model.getFilteredWeddingList().size());
    }
}
