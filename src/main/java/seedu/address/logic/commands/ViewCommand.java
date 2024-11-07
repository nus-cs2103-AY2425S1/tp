package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;

/**
 * Represents a command to view the details of a client identified by their index in the displayed list.
 * This command allows users to see detailed information about a specific client in the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the client identified by the index number used in the displayed client list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: '" + COMMAND_WORD + " 1'";

    public static final String MESSAGE_VIEW_CLIENT_SUCCESS = "Viewed Client: %1$s";

    private final Index targetIndex;

    /**
     * Creates a new ViewCommand to view the client at the specified {@code targetIndex}.
     *
     * @param targetIndex The index of the client to view in the filtered client list
     * @throws NullPointerException if {@code targetIndex} is null
     */
    public ViewCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the view command to show the client at the specified index.
     *
     * @param model The model containing the client data
     * @return A CommandResult containing the viewed client's information
     * @throws CommandException if the index is invalid or out of bounds
     * @throws NullPointerException if {@code model} is null
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Client> lastShownList = model.getFilteredClientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToView = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_VIEW_CLIENT_SUCCESS, Messages.format(clientToView)),
                false, false, true, clientToView,
                false, null, false);
    }

    /**
     * Compares this ViewCommand to another object for equality.
     *
     * @param other The object to compare to
     * @return true if the other object is also a ViewCommand targeting the same index
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewCommand
                && targetIndex.equals(((ViewCommand) other).targetIndex));
    }
}
