package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = "Use \"clear\" to clear all persons and events.\n"
            + "Use exactly \"clear p\" or \"clear e\" to specify persons or events to be cleared.";

    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public static final String MESSAGE_CONFIRMATION = "Are you sure you want to clear all your contacts and events?\n"
            + "Type \"Y\" or \"Yes\" to confirm or any key to abort.";

    public static final String MESSAGE_ABORTED = "Clear command aborted.";

    protected static boolean isPrompted;
    protected static boolean isConfirmed;

    /**
     * Creates a ClearCommand and initialises the prompt and confirmation status to false.
     */
    public ClearCommand() {
        isPrompted = false;
        isConfirmed = false;
    }

    public static boolean isPrompted() {
        return isPrompted;
    }

    public static boolean isConfirmed() {
        return isConfirmed;
    }

    public static void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
        setPrompted(true);
    }

    public static void setPrompted(boolean prompted) {
        isPrompted = prompted;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (!isPrompted) {
            setPrompted(true);
            return new CommandResult(MESSAGE_CONFIRMATION);
        } else if (!isConfirmed) {
            setPrompted(false);
            return new CommandResult(MESSAGE_ABORTED);
        } else {
            setConfirmed(false);
            setPrompted(false);
            model.clearAllEvents();
            model.clearAllPersons();
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClearCommand)) {
            return false;
        }

        // all instances of clear command are equal
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("isPrompted", isPrompted)
                .add("isConfirmed", isConfirmed)
                .toString();
    }
}
