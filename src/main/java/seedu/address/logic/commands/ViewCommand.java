package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Views the details of an existing person in the address book.
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view-client";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the details of the client identified by the index number used in the displayed client list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Client %1$s’s Details:\n"
            + "H/P: %2$s\nEMAIL: %3$s\nADDRESS: %4$s\n";
    public static final String MESSAGE_FAILURE = "Error! Please check if the client’s index is valid.";

    private final Index index;

    /**
     * Constructs a {@code ViewCommand} to view details of the client at the specified index.
     *
     * @param index of the person to view details
     */
    public ViewCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person clientToView = lastShownList.get(index.getZeroBased());

        return new CommandResult(String.format(MESSAGE_SUCCESS, clientToView.getName(),
                clientToView.getPhone(), clientToView.getEmail(), clientToView.getAddress()),
                false, false, true, clientToView);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ViewCommand)) {
            return false;
        }
        ViewCommand otherCommand = (ViewCommand) other;
        return index.equals(otherCommand.index);
    }

    @Override
    public int hashCode() {
        return index.hashCode();
    }
}
