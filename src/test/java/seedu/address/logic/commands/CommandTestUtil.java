package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
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
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_COMPANY_AMY = "companyA";
    public static final String VALID_COMPANY_BOB = "companyB";
    public static final String VALID_STATUS_AMY = "active";
    public static final String VALID_STATUS_BOB = "inactive";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_PRODUCT_BREAD = "bread";
    public static final String VALID_PRODUCT_RICE = "rice";


    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String COMPANY_DESC_AMY = " " + PREFIX_COMPANY + VALID_COMPANY_AMY;
    public static final String COMPANY_DESC_BOB = " " + PREFIX_COMPANY + VALID_COMPANY_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String PRODUCT_DESC_BREAD = " " + PREFIX_PRODUCT + VALID_PRODUCT_BREAD;
    public static final String PRODUCT_DESC_RICE = " " + PREFIX_PRODUCT + VALID_PRODUCT_RICE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY; // empty string not allowed for companies
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_PRODUCT_DESC = " " + PREFIX_PRODUCT + "rice^"; // '^' not allowed in products
    // only 'active' and 'inactive' allowed

    public static final String VALID_DATE_APPLE = "10-10-2024 16:00";
    public static final String VALID_DATE_BREAD = "12-10-2024 17:30";
    public static final String VALID_SUPPLIER_INDEX_APPLE = "1";
    public static final String VALID_SUPPLIER_INDEX_BREAD = "2";

    public static final String VALID_PRODUCT_APPLE = "Iphone16Pro";

    public static final String VALID_COST_APPLE = "200000";
    public static final String VALID_COST_BREAD = "150";

    public static final String VALID_QUANTITY_APPLE = "200 units";
    public static final String VALID_QUANTITY_BREAD = "100 units";
    public static final String INVALID_DATE_TIME = "11-05-2024 12:@2222";
    public static final String INVALID_DATE_MONTH = "11-1-2024 12:00";
    public static final String INVALID_DATE_DAY = "41-1-2024 12:00";
    public static final String INVALID_DATE_YEAR = "11-1-202 12:00";

    public static final String TIME_DESC_APPLE = " " + PREFIX_DATETIME + " " + VALID_DATE_APPLE;
    public static final String SUPPLIER_INDEX_DESC_APPLE = " " + PREFIX_SUPPLIER_INDEX
            + " " + VALID_SUPPLIER_INDEX_APPLE;
    public static final String PRODUCT_DESC_APPLE = " " + PREFIX_PRODUCT + " " + VALID_PRODUCT_APPLE;
    public static final String QUANTITY_DESC_APPLE = " " + PREFIX_QUANTITY + " " + VALID_QUANTITY_APPLE;
    public static final String COST_DESC_APPLE = " " + PREFIX_COST + " " + VALID_COST_APPLE;
    public static final String INVALID_DATE_APPLE = " " + PREFIX_DATETIME + " 1010-2024 16:00";
    public static final String INVALID_SUPPLIER_INDEX_APPLE_ZERO = " " + PREFIX_SUPPLIER_INDEX + " 0";
    public static final String INVALID_SUPPLIER_INDEX_APPLE_NEGATIVE = " " + PREFIX_SUPPLIER_INDEX + " -3";
    public static final String INVALID_PRODUCT_APPLE = " " + PREFIX_PRODUCT + " Iphone16@@@@Pro";
    public static final String INVALID_PRODUCT_APPLE_EMPTY_SPACE = " " + PREFIX_PRODUCT + "      ";
    public static final String INVALID_COST_APPLE_NAN = " " + PREFIX_COST + " abcdedfg";
    public static final String INVALID_COST_APPLE_ZERO = " " + PREFIX_COST + " 0";

    public static final String INVALID_COST_APPLE_NEGATIVE = " " + PREFIX_COST + "-200";
    public static final String INVALID_COST_APPLE_WRONG_DP = " " + PREFIX_COST + "200.123";
    public static final String INVALID_QUANTITY_APPLE_MISSING_UNITS = " " + PREFIX_QUANTITY + "200";
    public static final String INVALID_QUANTITY_APPLE_NEGATIVE = " " + PREFIX_QUANTITY + "200";
    public static final String INVALID_QUANTITY_APPLE_ZERO = " " + PREFIX_QUANTITY + "0";
    public static final String INVALID_QUANTITY_APPLE_NAN = " " + PREFIX_QUANTITY + "abc";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withCompany(VALID_COMPANY_AMY)
                .withTags(VALID_TAG_FRIEND).withProducts(VALID_PRODUCT_BREAD, VALID_PRODUCT_RICE).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withCompany(VALID_COMPANY_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withProducts(VALID_PRODUCT_BREAD).build();
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
