package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_ITEM;
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
import seedu.sellsavvy.model.person.NameContainsKeywordsPredicate;
import seedu.sellsavvy.model.person.Person;
import seedu.sellsavvy.testutil.EditOrderDescriptorBuilder;

/**
 * Contains helper methods for testing Order commands.
 */
public class OrderCommandTestUtil {

    public static final String VALID_COUNT_ATLAS = "5";
    public static final String VALID_COUNT_BOTTLE = "1";
    public static final String VALID_DATE_ATLAS = "02-12-2024";
    public static final String VALID_DATE_BOTTLE = "05-06-2027";
    public static final String VALID_ITEM_ATLAS = "Atlas A";
    public static final String VALID_ITEM_BOTTLE = "Bottle B";

    public static final String COUNT_DESC_ATLAS = " " + PREFIX_COUNT + VALID_COUNT_ATLAS;
    public static final String COUNT_DESC_BOTTLE = " " + PREFIX_COUNT + VALID_COUNT_BOTTLE;
    public static final String DATE_DESC_ATLAS = " " + PREFIX_DATE + VALID_DATE_ATLAS;
    public static final String DATE_DESC_BOTTLE = " " + PREFIX_DATE + VALID_DATE_BOTTLE;
    public static final String ITEM_DESC_ATLAS = " " + PREFIX_ITEM + VALID_ITEM_ATLAS;
    public static final String ITEM_DESC_BOTTLE = " " + PREFIX_ITEM + VALID_ITEM_BOTTLE;

    public static final String INVALID_ITEM_DESC = " " + PREFIX_ITEM + "Atlas&";
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "a";
    public static final String INVALID_DATE_NO_HYPHEN = " " + PREFIX_DATE + "02/12/2024";
    public static final String INVALID_DATE_DIGIT = " " + PREFIX_DATE + "2/1/2024";
    public static final String INVALID_DATE_VALUE = " " + PREFIX_DATE + "32-01-2024";
    public static final String INVALID_COUNT_ZERO = " " + PREFIX_COUNT + "0";
    public static final String INVALID_COUNT_NEGATIVE = " " + PREFIX_COUNT + "-2";
    public static final String INVALID_COUNT_STRING = " " + PREFIX_COUNT + "2 some random string";

    public static final EditOrderCommand.EditOrderDescriptor DESC_ATLAS;
    public static final EditOrderCommand.EditOrderDescriptor DESC_BOTTLE;

    static {
        DESC_ATLAS = new EditOrderDescriptorBuilder()
                .withItem(VALID_ITEM_ATLAS).withDate(VALID_DATE_ATLAS).withQuantity(VALID_COUNT_ATLAS).build();
        DESC_BOTTLE = new EditOrderDescriptorBuilder()
                .withItem(VALID_ITEM_BOTTLE).withDate(VALID_DATE_BOTTLE).withQuantity(VALID_COUNT_BOTTLE).build();
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

    //TODO: Copy data fields from PersonCommandTestUtil

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
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the order at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showOrderAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }
}
