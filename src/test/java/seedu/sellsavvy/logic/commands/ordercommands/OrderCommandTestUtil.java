package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.logic.commands.exceptions.CommandException;
import seedu.sellsavvy.model.AddressBook;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;

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

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            AddressBook actualAddressBook = (AddressBook) actualModel.getAddressBook();
            Model actualModelCopy = new ModelManager(actualAddressBook.createCopy(), new UserPrefs());
            CommandResult result = command.execute(actualModelCopy);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModelCopy);
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
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {}

    //TODO:
    /**
     * Updates {@code model}'s filtered list to show only the order at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showOrderAtIndex(Model model, Index targetIndex) {}
}
