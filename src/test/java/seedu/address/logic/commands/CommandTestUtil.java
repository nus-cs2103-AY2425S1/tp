package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPOSIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHLY_RENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENT_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddRentalCommand.AddRentalDescriptor;
import seedu.address.logic.commands.EditRentalCommand.EditRentalDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddRentalDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditRentalDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_NAME_ALICE = "Alice";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_CHARLIE = "Charlie Diu";
    public static final String VALID_NAME_DENVER = "Denver Eng";
    public static final String VALID_PHONE_ALICE = "98761111";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_PHONE_CHARLIE = "91231231";
    public static final String VALID_PHONE_DENVER = "";
    public static final String VALID_EMAIL_ALICE = "alice@example.com";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_EMAIL_CHARLIE = "";
    public static final String VALID_EMAIL_DENVER = "denver@example.com";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_EMPTY_PHONE = "";
    public static final String VALID_EMPTY_EMAIL = "";
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;

    public static final String NAME_DESC_CHARLIE = " " + PREFIX_NAME + VALID_NAME_CHARLIE;
    public static final String NAME_DESC_DENVER = " " + PREFIX_NAME + VALID_NAME_DENVER;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String PHONE_DESC_CHARLIE = " " + PREFIX_PHONE + VALID_PHONE_CHARLIE;
    public static final String PHONE_DESC_EMPTY = " " + PREFIX_PHONE + VALID_EMPTY_PHONE;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String EMAIL_DESC_DENVER = " " + PREFIX_EMAIL + VALID_EMAIL_DENVER;
    public static final String EMAIL_DESC_EMPTY = " " + PREFIX_EMAIL + VALID_EMPTY_EMAIL;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditClientCommand.EditPersonDescriptor DESC_AMY;
    public static final EditClientCommand.EditPersonDescriptor DESC_BOB;

    public static final String VALID_ADDRESS_ONE = "Ave 2 Yishun";
    public static final String VALID_RENTAL_START_DATE_ONE = "01/01/2024";
    public static final String VALID_RENTAL_END_DATE_ONE = "30/06/2024";
    public static final String VALID_RENT_DUE_DATE_ONE = "20";
    public static final String VALID_MONTHLY_RENT_ONE = "3300";
    public static final String VALID_DEPOSIT_ONE = "9900";
    public static final String VALID_CUSTOMER_LIST_ONE = "Shaco";

    public static final String VALID_ADDRESS_TWO = "Ave 10 Kent Ridge";
    public static final String VALID_RENTAL_START_DATE_TWO = "01/06/2024";
    public static final String VALID_RENTAL_END_DATE_TWO = "30/09/2024";
    public static final String VALID_RENT_DUE_DATE_TWO = "25";
    public static final String VALID_MONTHLY_RENT_TWO = "4500";
    public static final String VALID_DEPOSIT_TWO = "13500";
    public static final String VALID_CUSTOMER_LIST_TWO = "Ben";

    public static final String INVALID_ADDRESS = "";
    public static final String INVALID_RENTAL_START_DATE = "1/1/2024";
    public static final String INVALID_RENTAL_END_DATE = "33/12/2024";
    public static final String INVALID_RENT_DUE_DATE = "40";
    public static final String INVALID_MONTHLY_RENT = "3300.1";
    public static final String INVALID_DEPOSIT = "-1000";
    public static final String INVALID_CUSTOMER_LIST = "James;;Adam";

    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS + INVALID_ADDRESS;
    public static final String INVALID_RENTAL_START_DATE_DESC = " " + PREFIX_RENTAL_START_DATE
            + INVALID_RENTAL_START_DATE;
    public static final String INVALID_RENTAL_END_DATE_DESC = " " + PREFIX_RENTAL_END_DATE + INVALID_RENTAL_END_DATE;
    public static final String INVALID_RENT_DUE_DATE_DESC = " " + PREFIX_RENT_DUE_DATE + INVALID_RENT_DUE_DATE;
    public static final String INVALID_MONTHLY_RENT_DESC = " " + PREFIX_MONTHLY_RENT + INVALID_MONTHLY_RENT;
    public static final String INVALID_DEPOSIT_DESC = " " + PREFIX_DEPOSIT + INVALID_DEPOSIT;
    public static final String INVALID_CUSTOMER_LIST_DESC = " " + PREFIX_CUSTOMER_LIST + INVALID_CUSTOMER_LIST;

    public static final String ADDRESS_DESC_ONE = " " + PREFIX_ADDRESS + VALID_ADDRESS_ONE;
    public static final String RENTAL_START_DATE_DESC_ONE = " " + PREFIX_RENTAL_START_DATE
            + VALID_RENTAL_START_DATE_ONE;
    public static final String RENTAL_END_DATE_DESC_ONE = " " + PREFIX_RENTAL_END_DATE + VALID_RENTAL_END_DATE_ONE;
    public static final String RENT_DUE_DATE_DESC_ONE = " " + PREFIX_RENT_DUE_DATE + VALID_RENT_DUE_DATE_ONE;
    public static final String MONTHLY_RENT_DESC_ONE = " " + PREFIX_MONTHLY_RENT + VALID_MONTHLY_RENT_ONE;
    public static final String DEPOSIT_DESC_ONE = " " + PREFIX_DEPOSIT + VALID_DEPOSIT_ONE;
    public static final String CUSTOMER_LIST_DESC_ONE = " " + PREFIX_CUSTOMER_LIST + VALID_CUSTOMER_LIST_ONE;

    public static final String ADDRESS_DESC_TWO = " " + PREFIX_ADDRESS + VALID_ADDRESS_TWO;
    public static final String RENTAL_START_DATE_DESC_TWO = " " + PREFIX_RENTAL_START_DATE
            + VALID_RENTAL_START_DATE_TWO;
    public static final String RENTAL_END_DATE_DESC_TWO = " " + PREFIX_RENTAL_END_DATE + VALID_RENTAL_END_DATE_TWO;
    public static final String RENT_DUE_DATE_DESC_TWO = " " + PREFIX_RENT_DUE_DATE + VALID_RENT_DUE_DATE_TWO;
    public static final String MONTHLY_RENT_DESC_TWO = " " + PREFIX_MONTHLY_RENT + VALID_MONTHLY_RENT_TWO;
    public static final String DEPOSIT_DESC_TWO = " " + PREFIX_DEPOSIT + VALID_DEPOSIT_TWO;
    public static final String CUSTOMER_LIST_DESC_TWO = " " + PREFIX_CUSTOMER_LIST + VALID_CUSTOMER_LIST_TWO;
    public static final String KEYWORD_NAME_DESC_ALICE = " " + PREFIX_KEYWORD + VALID_NAME_ALICE;
    public static final String KEYWORD_PHONE_DESC_ALICE = " " + PREFIX_KEYWORD + VALID_PHONE_ALICE;
    public static final String KEYWORD_EMAIL_DESC_ALICE = " " + PREFIX_KEYWORD + VALID_EMAIL_ALICE;
    public static final String KEYWORD_ADDRESS_DESC_ONE = " " + PREFIX_KEYWORD + VALID_ADDRESS_ONE;

    public static final EditRentalDescriptor EDIT_RENTAL_DESCRIPTOR_ONE;
    public static final EditRentalDescriptor EDIT_RENTAL_DESCRIPTOR_TWO;
    public static final AddRentalDescriptor ADD_RENTAL_DESCRIPTOR_ONE;
    public static final AddRentalDescriptor ADD_RENTAL_DESCRIPTOR_TWO;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EDIT_RENTAL_DESCRIPTOR_ONE = new EditRentalDescriptorBuilder().withAddress(VALID_ADDRESS_ONE)
                .withRentalStartDate(VALID_RENTAL_START_DATE_ONE).withRentalEndDate(VALID_RENTAL_END_DATE_ONE)
                .withRentDueDate(VALID_RENT_DUE_DATE_ONE).withMonthlyRent(VALID_MONTHLY_RENT_ONE)
                .withDeposit(VALID_DEPOSIT_ONE).withCustomerList(VALID_CUSTOMER_LIST_ONE).build();
        EDIT_RENTAL_DESCRIPTOR_TWO = new EditRentalDescriptorBuilder().withAddress(VALID_ADDRESS_TWO)
                .withRentalStartDate(VALID_RENTAL_START_DATE_TWO).withRentalEndDate(VALID_RENTAL_END_DATE_TWO)
                .withRentDueDate(VALID_RENT_DUE_DATE_TWO).withMonthlyRent(VALID_MONTHLY_RENT_TWO)
                .withDeposit(VALID_DEPOSIT_TWO).withCustomerList(VALID_CUSTOMER_LIST_TWO).build();
        ADD_RENTAL_DESCRIPTOR_ONE = new AddRentalDescriptorBuilder().withAddress(VALID_ADDRESS_ONE).build();
        ADD_RENTAL_DESCRIPTOR_TWO = new AddRentalDescriptorBuilder().withAddress(VALID_ADDRESS_TWO).build();

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
     * - the address book, filtered client list and selected client in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Client> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the client at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Client client = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = client.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
