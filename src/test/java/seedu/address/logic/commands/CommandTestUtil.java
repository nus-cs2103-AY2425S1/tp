package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;
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
    public static final String VALID_PHONE_AMY = "91111111";
    public static final String VALID_PHONE_BOB = "92222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_POSTALCODE_ADMIRALTY = "654321";
    public static final String VALID_POSTALCODE_BEDOK = "321456";
    public static final String VALID_NOTFOUND_POSTALCODE_CLEMENTI = "999999";
    public static final String VALID_UNIT_ADMIRALTY = "02-22";
    public static final String VALID_UNIT_BEDOK = "11-12";
    public static final String VALID_NOTFOUND_UNIT_CLEMENTI = "16-02";
    public static final String VALID_MEETING_TITLE_ADMIRALTY = "Admiralty HDB Client Viewing";
    public static final String VALID_MEETING_DATE_ADMIRALTY = "31-10-2024";
    public static final String VALID_MEETING_TITLE_BEDOK = "Bedok Villa Finalizing Purchase Agreement";
    public static final String VALID_MEETING_DATE_BEDOK = "11-06-2024";
    public static final String VALID_TYPE_HDB = "HDB";
    public static final String VALID_TYPE_CONDO = "CONDO";
    public static final String VALID_TYPE_LANDED = "LANDED";
    public static final String VALID_BID_ADMIRALTY = "10000";
    public static final String VALID_BID_BEDOK = "20000";
    public static final String VALID_ASK_ADMIRALTY = "50000";
    public static final String VALID_ASK_BEDOK = "60000";
    public static final String VALID_MATCHINGPRICE_ADMIRALTY = "30000";
    public static final String VALID_MATCHINGPRICE_BEDOK = "40000";

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
    public static final String POSTALCODE_DESC_ADMIRALTY = " " + PREFIX_POSTALCODE + VALID_POSTALCODE_ADMIRALTY;
    public static final String POSTALCODE_DESC_BEDOK = " " + PREFIX_POSTALCODE + VALID_POSTALCODE_BEDOK;
    public static final String UNIT_DESC_ADMIRALTY = " " + PREFIX_UNITNUMBER + VALID_UNIT_ADMIRALTY;
    public static final String UNIT_DESC_BEDOK = " " + PREFIX_UNITNUMBER + VALID_UNIT_BEDOK;
    public static final String TYPE_DESC_ADMIRALTY = " " + PREFIX_TYPE + VALID_TYPE_CONDO;
    public static final String TYPE_DESC_BEDOK = " " + PREFIX_TYPE + VALID_TYPE_HDB;
    public static final String BID_DESC_ADMIRALTY = " " + PREFIX_BID + VALID_BID_ADMIRALTY;
    public static final String BID_DESC_BEDOK = " " + PREFIX_BID + VALID_BID_BEDOK;
    public static final String ASK_DESC_ADMIRALTY = " " + PREFIX_ASK + VALID_ASK_ADMIRALTY;
    public static final String ASK_DESC_BEDOK = " " + PREFIX_ASK + VALID_ASK_BEDOK;
    public static final String LTE_DESC_BEDOK = " " + PREFIX_LTE + VALID_MATCHINGPRICE_BEDOK;
    public static final String GTE_DESC_BEDOK = " " + PREFIX_GTE + VALID_MATCHINGPRICE_BEDOK;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_POSTALCODE_DESC = " " + PREFIX_POSTALCODE + "1234"; //PostalCode with few numbers
    public static final String INVALID_UNIT_DESC = " " + PREFIX_UNITNUMBER + "11 11"; //'-' delimiter missing
    public static final String INVALID_TYPE_DESC = " " + PREFIX_TYPE + "PUBLIC"; //iNVALID TYPE
    public static final String INVALID_BID_DESC = " " + PREFIX_BID + "ONE THOUSAND";
    public static final String INVALID_ASK_DESC = " " + PREFIX_ASK + "TWO THOUSAND";
    public static final String INVALID_MEETING_TITLE_DESC = " " + PREFIX_MEETING_TITLE + "Meeting @$#%&";
    public static final String INVALID_MEETING_DATE_DESC = " " + PREFIX_MEETING_DATE + "123456";

    public static final String VALID_NOTFOUND_MEETING_TITLE = "Nonexistent Meeting";
    public static final String VALID_NOTFOUND_MEETING_DATE = "31-12-2025";
    public static final String MEETING_TITLE_DESC_ADMIRALTY = " " + PREFIX_MEETING_TITLE
            + VALID_MEETING_TITLE_ADMIRALTY;
    public static final String MEETING_DATE_DESC_ADMIRALTY = " " + PREFIX_MEETING_DATE
            + VALID_MEETING_DATE_ADMIRALTY;
    public static final String MEETING_BUYER = " " + PREFIX_BUYER_PHONE + VALID_PHONE_AMY;
    public static final String MEETING_SELLER = " " + PREFIX_SELLER_PHONE + VALID_PHONE_BOB;
    public static final String TYPE = " " + PREFIX_TYPE + VALID_TYPE_CONDO;
    public static final String POSTALCODE = " " + PREFIX_POSTALCODE + VALID_POSTALCODE_ADMIRALTY;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    // Valid static keys for commands
    public static final String VALID_KEY_CLIENTS_DESC = " " + PREFIX_KEY + ListClientsCommand.KEY_WORD;
    public static final String VALID_KEY_BUYERS_DESC = " " + PREFIX_KEY + ListBuyersCommand.KEY_WORD;
    public static final String VALID_KEY_SELLERS_DESC = " " + PREFIX_KEY + ListSellersCommand.KEY_WORD;
    public static final String VALID_KEY_PROPERTIES_DESC = " " + PREFIX_KEY + ListPropertiesCommand.KEY_WORD;
    public static final String VALID_KEY_MEETINGS_DESC = " " + PREFIX_KEY + ListMeetingsCommand.KEY_WORD;
    public static final String INVALID_KEY = "someRandomKey";
    public static final String INVALID_KEY_DESC = " " + PREFIX_KEY + INVALID_KEY;


    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
