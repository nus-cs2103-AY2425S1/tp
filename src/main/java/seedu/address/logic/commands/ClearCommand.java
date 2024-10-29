package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.personcommands.DeletePersonCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = "Use \"clear\" to clear all persons and events.\n"
            + "Use \"clear p\" or \"clear e\" to specify persons or events to be cleared.";

    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public static final String MESSAGE_CONFIRMATION = "Are you sure you want to clear all your contacts and events?\n"
            + "Type \"y\" to confirm or any key to abort.";

    public static final String MESSAGE_ABORTED = "Clear command aborted.";

    protected static boolean isPrompted;
    protected static boolean isConfirmed;

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

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public void setPrompted(boolean prompted) {
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
            setPrompted(false);
            setConfirmed(false);
            model.setAddressBook(new AddressBook());
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

        ClearCommand otherClearCommand = (ClearCommand) other;
        return isPrompted == otherClearCommand.isPrompted
                && isConfirmed == otherClearCommand.isConfirmed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("isPrompted", isPrompted)
                .add("isConfirmed", isConfirmed)
                .toString();
    }
}
