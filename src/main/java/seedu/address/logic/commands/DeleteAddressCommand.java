package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;

import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.person.Person;

/**
 * Deletes the person's public address identified by the index number
 * used in the displayed person list and their crypto network.
 */
public class DeleteAddressCommand extends Command {
    public static final String COMMAND_WORD = "dela"; // short for delete address

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person's public address identified by the index number "
            + "used in the displayed person list and their crypto network.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + PREFIX_PUBLIC_ADDRESS + "NETWORK\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PUBLIC_ADDRESS + "BTC";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person's Public Address: %1$s";

    private final Index targetIndex;
    private final Network targetAddressnetwork;

    public DeleteAddressCommand(Index targetIndex, Network network) {
        this.targetIndex = targetIndex;
        this.targetAddressnetwork = network;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        // TODO Implement deleting by individual address
        // Currently deleting all in the network
        Map<Network, Set<PublicAddress>> addresses = personToDelete.getPublicAddresses();
        addresses.remove(targetAddressnetwork);
        personToDelete.setPublicAddresses(addresses);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteAddressCommand otherDeleteCommand)) {
            return false;
        }

        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
