package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOWN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEDROOMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BATHROOMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
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
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_REMARK_AMY = "";
    public static final String VALID_REMARK_BOB = "";
    public static final String VALID_BIRTHDAY_AMY = "2000-03-15";
    public static final String VALID_BIRTHDAY_BOB = "1999-01-27";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_PROPERTY_ADDRESS_AMY = "123 Main St, Springfield";
    public static final String VALID_PROPERTY_ADDRESS_BOB = "456 Elm St, Metropolis";
    public static final String VALID_TOWN_AMY = "Springfield";
    public static final String VALID_TOWN_BOB = "Metropolis";
    public static final String VALID_TYPE_AMY = "Apartment";
    public static final String VALID_TYPE_BOB = "House";
    public static final String VALID_SIZE_AMY = "1200 sqft";
    public static final String VALID_SIZE_BOB = "2500 sqft";
    public static final String VALID_BEDROOMS_AMY = "3";
    public static final String VALID_BEDROOMS_BOB = "4";
    public static final String VALID_BATHROOMS_AMY = "2";
    public static final String VALID_BATHROOMS_BOB = "3";
    public static final String VALID_PRICE_AMY = "300000";
    public static final String VALID_PRICE_BOB = "500000";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_BOB;
    public static final String BIRTHDAY_DESC_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String BIRTHDAY_DESC_BOB = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_BOB;
    public static final String PROPERTY_ADDRESS_DESC_AMY = " " + PREFIX_PROPERTY_ADDRESS + VALID_PROPERTY_ADDRESS_AMY;
    public static final String PROPERTY_ADDRESS_DESC_BOB = " " + PREFIX_PROPERTY_ADDRESS + VALID_PROPERTY_ADDRESS_BOB;
    public static final String TOWN_DESC_AMY = " " + PREFIX_TOWN + VALID_TOWN_AMY;
    public static final String TOWN_DESC_BOB = " " + PREFIX_TOWN + VALID_TOWN_BOB;
    public static final String TYPE_DESC_AMY = " " + PREFIX_TYPE + VALID_TYPE_AMY;
    public static final String TYPE_DESC_BOB = " " + PREFIX_TYPE + VALID_TYPE_BOB;
    public static final String SIZE_DESC_AMY = " " + PREFIX_SIZE + VALID_SIZE_AMY;
    public static final String SIZE_DESC_BOB = " " + PREFIX_SIZE + VALID_SIZE_BOB;
    public static final String BEDROOMS_DESC_AMY = " " + PREFIX_BEDROOMS + VALID_BEDROOMS_AMY;
    public static final String BEDROOMS_DESC_BOB = " " + PREFIX_BEDROOMS + VALID_BEDROOMS_BOB;
    public static final String BATHROOMS_DESC_AMY = " " + PREFIX_BATHROOMS + VALID_BATHROOMS_AMY;
    public static final String BATHROOMS_DESC_BOB = " " + PREFIX_BATHROOMS + VALID_BATHROOMS_BOB;
    public static final String PRICE_DESC_AMY = " " + PREFIX_PRICE + VALID_PRICE_AMY;
    public static final String PRICE_DESC_BOB = " " + PREFIX_PRICE + VALID_PRICE_BOB;

    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String LOG_MESSAGE_DESC = " " + PREFIX_LOG;
    public static final String VALID_REMARK_ALICE = "like apples";
    public static final String VALID_REMARK_BENSON = "good friend";
    public static final String VALID_REMARK_CARL = "enjoys hiking";
    public static final String VALID_REMARK_DANIEL = "plays football";
    public static final String VALID_REMARK_ELLE = "loves cooking";
    public static final String VALID_REMARK_FIONA = "travel enthusiast";
    public static final String VALID_REMARK_GEORGE = "avid reader";
    public static final String VALID_REMARK_HOON = "loves animals";
    public static final String VALID_REMARK_IDA = "enjoys painting";
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_DATE_DESC = " " + PREFIX_BIRTHDAY + "2002-02-30"; // empty string not allowed
    // for birthdays
    public static final String INVALID_DATE_AGAIN_DESC = " " + PREFIX_BIRTHDAY + "2024-13-01";
    public static final String INVALID_BIRTHDAY_EARLY_DESC = " " + PREFIX_BIRTHDAY + "1908-04-22";
    public static final String INVALID_BIRTHDAY_LATE_DESC = " " + PREFIX_BIRTHDAY + LocalDate.now()
            .plusDays(1).toString();
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    // PROPERTY STUFF
    public static final String INVALID_PROPERTY_ADDRESS_DESC = " " + PREFIX_PROPERTY_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TOWN_DESC = " " + PREFIX_TOWN; // empty string not allowed for town
    public static final String INVALID_TYPE_DESC = " " + PREFIX_TYPE; // empty string not allowed for type
    public static final String INVALID_SIZE_DESC = " " + PREFIX_SIZE; // empty string not allowed for size
    public static final String INVALID_BEDROOMS_DESC = " " + PREFIX_BEDROOMS + "two"; // not a number
    public static final String INVALID_BATHROOMS_DESC = " " + PREFIX_BATHROOMS + "three"; // not a number
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "thousand"; // not a number

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
    public static final String VALID_DATE_OF_CREATION = LocalDate.now().toString();
    public static final String VALID_LOG_DATE = LocalDate.now().toString();
    public static final String INVALID_LOG_DATE = LocalDate.now().plusDays(10).toString();
    public static final String VALID_LOG_MESSAGE = "history entry";
    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY)
                .withTags(VALID_TAG_FRIEND).withDateOfCreation(VALID_DATE_OF_CREATION).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withDateOfCreation(VALID_DATE_OF_CREATION).build();
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
