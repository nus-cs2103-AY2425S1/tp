package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.addresses.Network;
import seedu.address.model.person.Person;

/**
 * Deletes the person's public address identified by the index number
 * used in the displayed person list and their crypto network.
 */
public class DeletePublicAddressCommand extends Command {
    public static final String COMMAND_WORD = "deletepa"; // short for delete address

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the person's "
        + "public address identified by the index number "
        + "used in the displayed person list and their crypto network.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + PREFIX_PUBLIC_ADDRESS_NETWORK + "NETWORK\n"
        + "[" + PREFIX_PUBLIC_ADDRESS_LABEL + "LABEL]\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
        + PREFIX_PUBLIC_ADDRESS_LABEL + "default";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted %1$s's Public Address: %2$s";
    public static final String MESSAGE_NON_MATCHING_LABEL = "Label %1$s does not match any public addresses of %2$s";
    public static final String MESSAGE_EMPTY_NETWORK = "Network %1$s of %2$s is empty and cannot be deleted from";
    private static final Logger logger = LogsCenter.getLogger(DeletePublicAddressCommand.class);

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

        if (personToDelete.getPublicAddressesByNetwork(targetAddressnetwork).isEmpty()) {
            logger.warning(String.format(
                    "Attempted to delete a public address from empty network %1$s on person %2s",
                    targetAddressnetwork, personToDelete.getName()));
            throw new CommandException(
                    String.format(MESSAGE_EMPTY_NETWORK, targetAddressnetwork, personToDelete.getName()));
        }

        if (!Objects.equals(targetLabel, "")
                && !personToDelete.hasPublicAddressWithLabelWithinNetwork(targetAddressnetwork, targetLabel)) {
            logger.warning(String.format(
                "Attempted to delete a public address with a non-matching label %1$s for network %2$s on person %3$s",
                targetLabel, targetAddressnetwork, personToDelete.getName()));
            throw new CommandException(
                String.format(MESSAGE_NON_MATCHING_LABEL, targetLabel, personToDelete.getName()));
        }

        Person deletedPerson;
        if (targetLabel.isEmpty()) {
            // Delete all the addresses in the network
            deletedPerson = personToDelete.withoutPublicAddressesByNetwork(targetAddressnetwork);
        } else {
            // Delete the specific address with the label
            deletedPerson = personToDelete.withoutPublicAddressByNetworkAndLabel(targetAddressnetwork, targetLabel);
        }
        model.setPerson(personToDelete, deletedPerson);
        return new CommandResult(String.format(
                MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName(), personToDelete.getPublicAddressesComposition()
        ));

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
