package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;

/**
 * Views the rental information for a client identified using its displayed index from the address book.
 */
public class RentalViewCommand extends Command {
    public static final String COMMAND_WORD = "rview";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the rental information for a client identified by the index number used in the displayed client"
            + "list\n"
            + "Parameters: CLIENT_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public RentalViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Client clientToViewRentalInformation = lastShownList.get(targetIndex.getZeroBased());
        // TODO: Decide if the model needs to be modified and, if so, implement the methods required
        // model.doSomething(clientToViewRentalInformation); // Modify the model if needed
        // TODO: Update the placeholder message passed into CommandResult with a proper message
        return new CommandResult("WIP: The 'rview' command was called.");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RentalViewCommand)) {
            return false;
        }

        RentalViewCommand otherRentalViewCommand = (RentalViewCommand) other;
        return targetIndex.equals(otherRentalViewCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
