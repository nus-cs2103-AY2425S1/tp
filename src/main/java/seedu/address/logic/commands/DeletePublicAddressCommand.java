package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;

import java.util.HashSet;
import java.util.List;
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
public class DeletePublicAddressCommand extends Command {
    public static final String COMMAND_WORD = "deletepa"; // short for delete address

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person's public address identified by the index number "
            + "used in the displayed person list and their crypto network.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + PREFIX_PUBLIC_ADDRESS + "NETWORK\n"
            + "[" + PREFIX_PUBLIC_ADDRESS + "LABEL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PUBLIC_ADDRESS + "BTC"
            + PREFIX_PUBLIC_ADDRESS_LABEL + "default";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person's Public Address: %1$s";

    private final Index targetIndex;
    private final Network targetAddressnetwork;
    private final String targetLabel;

    /**
     * Takes in a target index and deletes all the addresses related to the network
     *
     * @param targetIndex
     * @param network
     */
    public DeletePublicAddressCommand(Index targetIndex, Network network) {
        this.targetIndex = targetIndex;
        this.targetAddressnetwork = network;
        this.targetLabel = "";
    }

    /**
     * Takes in a target index and deletes the address with the same label inside the network
     *
     * @param targetIndex
     * @param network
     * @param label
     */
    public DeletePublicAddressCommand(Index targetIndex, Network network, String label) {
        this.targetIndex = targetIndex;
        this.targetAddressnetwork = network;
        this.targetLabel = label;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        // Select from address and delete the specific ones
        Set<PublicAddress> addresses = personToDelete.getPublicAddressesByNetwork(targetAddressnetwork);
        Set<PublicAddress> newAddresses = new HashSet<PublicAddress>();
        if (!targetLabel.isEmpty()) {
            addresses.forEach(
                address -> {
                    if (!address.label.equals(targetLabel)) {
                        newAddresses.add(address);
                    }
                }
            );
        }
        personToDelete.setPublicAddressesByNetwork(targetAddressnetwork, new HashSet<PublicAddress>(newAddresses));

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getPublicAddresses()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePublicAddressCommand otherDeleteCommand)) {
            return false;
        }

        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("targetAddressnetwork", targetAddressnetwork)
                .add("targetLabel", targetLabel)
                .toString();
    }
}
