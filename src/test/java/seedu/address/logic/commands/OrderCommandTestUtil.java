package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

public class OrderCommandTestUtil {

    public static final String VALID_COUNT_ATLAS = "5";
    public static final String VALID_COUNT_BOTTLE = "1";
    public static final String VALID_DATE_ATLAS = "02-12-2024";
    public static final String VALID_DATE_BOTTLE = "05-06-2027";
    public static final String VALID_ITEM_ATLAS = "Atlas A";
    public static final String VALID_ITEM_BOTTLE = "Bottle B";

    //TODO: Copy data fields from PersonCommandTestUtil

    //TODO:
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {}

    //TODO:
    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {}

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
