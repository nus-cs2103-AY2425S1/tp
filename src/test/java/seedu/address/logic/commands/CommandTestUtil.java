package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;
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
import seedu.address.testutil.EditPersonPropertyToBuyDescriptorBuilder;
import seedu.address.testutil.EditPersonPropertyToSellDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_JACK = "Clif Soh";
    public static final String VALID_NAME_KATE = "Dan Teh";
    public static final String VALID_NAME_EVE = "Eve Lee";
    public static final String VALID_NAME_FOXY = "Foxy Woxy";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_PHONE_JACK = "33333333";
    public static final String VALID_PHONE_KATE = "44444444";
    public static final String VALID_PHONE_EVE = "55555555";
    public static final String VALID_PHONE_FOXY = "66666666";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_EMAIL_JACK = "clif@example.com";
    public static final String VALID_EMAIL_KATE = "dan@example.com";
    public static final String VALID_EMAIL_EVE = "eve@example.com";
    public static final String VALID_EMAIL_FOXY = "foxy@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ADDRESS_JACK = "Block 101, Jacky Street 2";
    public static final String VALID_ADDRESS_KATE = "Hillview Katy Heights, Block 200";
    public static final String VALID_ADDRESS_EVE = "Block 312, Eve Street 2";
    public static final String VALID_ADDRESS_FOXY = "Block 123, Foxy Street 4";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_HOUSING_TYPE_HDB = "h";
    public static final String VALID_HOUSING_TYPE_CONDO = "c";
    public static final String VALID_BUYING_PRICE_1650000 = "1650000";
    public static final String VALID_BUYING_PRICE_1900000 = "1900000";
    public static final String VALID_SELLING_PRICE_1500000 = "1500000";
    public static final String VALID_SELLING_PRICE_2000000 = "2000000";
    public static final String VALID_POSTAL_CODE_567510 = "567510";
    public static final String VALID_POSTAL_CODE_582090 = "582090";
    public static final String VALID_UNIT_NUMBER_10_65 = "10-65";
    public static final String VALID_UNIT_NUMBER_03_11 = "03-11";
    public static final String VALID_TAG_SPACIOUS = "spacious";
    public static final String VALID_TAG_NEAR_MRT = "near MRT";
    public static final String VALID_FIELD_NAME = "Name";
    public static final String VALID_FIELD_NUMPROP = "NumProp";
    public static final String VALID_ORDER_LOW = "L";
    public static final String VALID_ORDER_HIGH = "H";

    public static final String INVALID_FIELD = "InvalidField";
    public static final String INVALID_ORDER = "InvalidOrder";

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
    public static final String HOUSING_TYPE_DESC_HDB = " " + PREFIX_HOUSING_TYPE + VALID_HOUSING_TYPE_HDB;
    public static final String HOUSING_TYPE_DESC_CONDO = " " + PREFIX_HOUSING_TYPE + VALID_HOUSING_TYPE_CONDO;
    public static final String BUYING_PRICE_DESC_1650000 = " " + PREFIX_BUYING_PRICE + VALID_BUYING_PRICE_1650000;
    public static final String BUYING_PRICE_DESC_1900000 = " " + PREFIX_BUYING_PRICE + VALID_BUYING_PRICE_1900000;
    public static final String SELLING_PRICE_DESC_1500000 = " " + PREFIX_SELLING_PRICE + VALID_SELLING_PRICE_1500000;
    public static final String SELLING_PRICE_DESC_2000000 = " " + PREFIX_SELLING_PRICE + VALID_SELLING_PRICE_2000000;
    public static final String POSTAL_CODE_DESC_567510 = " " + PREFIX_POSTAL_CODE + VALID_POSTAL_CODE_567510;
    public static final String POSTAL_CODE_DESC_582090 = " " + PREFIX_POSTAL_CODE + VALID_POSTAL_CODE_582090;
    public static final String UNIT_NUMBER_DESC_10_65 = " " + PREFIX_UNIT_NUMBER + VALID_UNIT_NUMBER_10_65;
    public static final String UNIT_NUMBER_DESC_03_11 = " " + PREFIX_UNIT_NUMBER + VALID_UNIT_NUMBER_03_11;
    public static final String TAG_DESC_SPACIOUS = " " + PREFIX_TAG + VALID_TAG_SPACIOUS;
    public static final String TAG_DESC_NEAR_MRT = " " + PREFIX_TAG + VALID_TAG_NEAR_MRT;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_HOUSING_TYPE_DESC =
            " " + PREFIX_HOUSING_TYPE + "x"; // 'x' not a valid housing type
    public static final String INVALID_BUYING_PRICE_DESC =
            " " + PREFIX_BUYING_PRICE + "1.65million"; // 'million' not allowed in prices
    public static final String INVALID_SELLING_PRICE_DESC =
            " " + PREFIX_SELLING_PRICE + "1.65M"; // 'M' not allowed in prices
    public static final String INVALID_POSTAL_CODE_DESC =
            " " + PREFIX_POSTAL_CODE + "1234567"; // 7 digits not allowed in postal code
    public static final String INVALID_UNIT_NUMBER_DESC =
            " " + PREFIX_UNIT_NUMBER + "10-65-01"; // '01' not allowed in unit number

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
    public static final String PREAMBLE_INDEX = "1";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final DeletePropertyToBuyCommand.EditPersonPropertyToBuyDescriptor DESC_CLIF;
    public static final DeletePropertyToBuyCommand.EditPersonPropertyToBuyDescriptor DESC_DAN;
    public static final DeletePropertyToSellCommand.EditPersonPropertyToSellDescriptor DESC_EVE;
    public static final DeletePropertyToSellCommand.EditPersonPropertyToSellDescriptor DESC_FOXY;


    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_CLIF = new EditPersonPropertyToBuyDescriptorBuilder().withName(VALID_NAME_JACK)
                .withPhone(VALID_PHONE_JACK).withEmail(VALID_EMAIL_JACK).withAddress(VALID_ADDRESS_JACK)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_DAN = new EditPersonPropertyToBuyDescriptorBuilder().withName(VALID_NAME_KATE)
                .withPhone(VALID_PHONE_KATE).withEmail(VALID_EMAIL_KATE).withAddress(VALID_ADDRESS_KATE)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_EVE = new EditPersonPropertyToSellDescriptorBuilder().withName(VALID_NAME_EVE)
                .withPhone(VALID_PHONE_EVE).withEmail(VALID_EMAIL_EVE).withAddress(VALID_ADDRESS_EVE)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_FOXY = new EditPersonPropertyToSellDescriptorBuilder().withName(VALID_NAME_FOXY)
                .withPhone(VALID_PHONE_FOXY).withEmail(VALID_EMAIL_FOXY).withAddress(VALID_ADDRESS_FOXY)
                .withTags(VALID_TAG_FRIEND).build();
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
