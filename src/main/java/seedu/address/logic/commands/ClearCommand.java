package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Makes the address book ready for clearing and
 * prompts for confirmation to clear.
 *
 * The clear command helps them bulk delete entries very quickly
 * It also support bulk deleting a filtered list
 * The confirm command ensures that researchers think twice
 * before clearing, so that they don't make irreversible mistakes
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_EMPTY = "No participants to clear.";

    public static final String MESSAGE_CHECK = "Type confirm (which can be followed by \n"
                                                + "anything else) to clear listed participants. \n"
                                                + "\nType anything else to cancel clearing.";
    private static boolean isClear;


    /**
     * Retrieves the current status of the 'isClear' flag.
     *
     * @return {@code true} if the 'isClear' flag is set to true, {@code false} otherwise.
     */
    public static boolean getIsClear() {
        return ClearCommand.isClear;
    }

    /**
     * Sets the status of the 'isClear' flag.
     *
     * @param isClear the new value to set for the 'isClear' flag.
     */
    public static void setIsClear(boolean isClear) {
        ClearCommand.isClear = isClear;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<Person> contactsList = model.getFilteredPersonList();
        if (contactsList.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        }
        ClearCommand.setIsClear(true);
        return new CommandResult(MESSAGE_CHECK);
    }
}
