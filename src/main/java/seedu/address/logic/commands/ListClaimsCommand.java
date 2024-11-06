package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;

/**
 * Lists all clients in the address book to the user.
 */
public class ListClaimsCommand extends Command {

    public static final String COMMAND_WORD = "listClaims";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists claims tagged to the specified client. "
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "%1$s's  claims: \n %2$s";
    public static final String MESSAGE_NO_CLAIMS = "Client %1$s has no claims";

    private final Index index;

    /**
     * Constructs a {@code ListClaimsCommand} with the specified index.
     *
     * This constructor initializes a new instance of {@code ListClaimsCommand} using the provided
     * index, which specifies the position of the claims to be listed. It ensures that the index
     * is not null by calling {@link #requireNonNull(Object...)}.
     *
     * @param index The index specifying which claims to list. Must not be null.
     * @throws NullPointerException if the provided {@code index} is null.
     */
    public ListClaimsCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToDisplay = lastShownList.get(index.getZeroBased());

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                clientToDisplay.getName().toString(),
                clientToDisplay.getInsurancePlansManager().accessClaims()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListClaimsCommand e)) {
            return false;
        }

        return index.equals(e.index);
    }
}
