package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.sellsavvy.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.logic.commands.exceptions.CommandException;
import seedu.sellsavvy.model.AddressBook;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.model.customer.NameContainsKeywordsPredicate;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.OrderList;
import seedu.sellsavvy.testutil.EditOrderDescriptorBuilder;

/**
 * Contains helper methods for testing Order commands.
 */
public class OrderCommandTestUtil {
    public static final String VALID_DATE_ATLAS = "02-12-2025";
    public static final String VALID_DATE_BOTTLE = "05-06-2027";
    public static final String VALID_DATE_OUTDATED = "02-02-2002";
    public static final String VALID_ITEM_ATLAS = "Atlas A";
    public static final String VALID_ITEM_BOTTLE = "Bottle B";
    public static final String VALID_QUANTITY_ATLAS = "5";
    public static final String VALID_QUANTITY_BOTTLE = "1";

    public static final String QUANTITY_DESC_ATLAS = " " + PREFIX_QUANTITY + VALID_QUANTITY_ATLAS;
    public static final String QUANTITY_DESC_BOTTLE = " " + PREFIX_QUANTITY + VALID_QUANTITY_BOTTLE;
    public static final String DATE_DESC_ATLAS = " " + PREFIX_DATE + VALID_DATE_ATLAS;
    public static final String DATE_DESC_BOTTLE = " " + PREFIX_DATE + VALID_DATE_BOTTLE;
    public static final String ITEM_DESC_ATLAS = " " + PREFIX_ITEM + VALID_ITEM_ATLAS;
    public static final String ITEM_DESC_BOTTLE = " " + PREFIX_ITEM + VALID_ITEM_BOTTLE;

    public static final String INVALID_ITEM_DESC = " " + PREFIX_ITEM + " ";
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "a";
    public static final String INVALID_DATE_NO_HYPHEN = " " + PREFIX_DATE + "02/12/2024";
    public static final String INVALID_DATE_DIGIT = " " + PREFIX_DATE + "2/1/2024";
    public static final String INVALID_DATE_VALUE = " " + PREFIX_DATE + "30-02-2024";
    public static final String INVALID_QUANTITY_ZERO = " " + PREFIX_QUANTITY + "0";
    public static final String INVALID_QUANTITY_NEGATIVE = " " + PREFIX_QUANTITY + "-2";
    public static final String INVALID_QUANTITY_STRING = " " + PREFIX_QUANTITY + "2 some random string";

    public static final EditOrderCommand.EditOrderDescriptor DESC_ATLAS;
    public static final EditOrderCommand.EditOrderDescriptor DESC_BOTTLE;

    static {
        DESC_ATLAS = new EditOrderDescriptorBuilder()
                .withItem(VALID_ITEM_ATLAS).withDate(VALID_DATE_ATLAS).withQuantity(VALID_QUANTITY_ATLAS).build();
        DESC_BOTTLE = new EditOrderDescriptorBuilder()
                .withItem(VALID_ITEM_BOTTLE).withDate(VALID_DATE_BOTTLE).withQuantity(VALID_QUANTITY_BOTTLE).build();
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

    //TODO: Copy data fields from CustomerCommandTestUtil

    //TODO:
    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered order list and selected order in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Customer> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCustomerList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredCustomerList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the order at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showOrderAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCustomerList().size());

        Customer customer = model.getFilteredCustomerList().get(targetIndex.getZeroBased());
        final String[] splitName = customer.getName().fullName.split("\\s+");
        model.updateFilteredCustomerList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCustomerList().size());
    }

    /**
     * Returns an {@code Order} from the model's filtered order list by the given {@code Index}.
     */
    public static Order getOrderByIndex(Model model, Index index) {
        return model.getFilteredOrderList().get(index.getZeroBased());
    }

    /**
     * Returns an {@code OrderList} of the model's selected customer by the given {@code Index}
     */
    public static OrderList getOrderListByIndex(Model model, Index index) {
        return model.getFilteredCustomerList().get(index.getZeroBased()).getOrderList();
    }
}
