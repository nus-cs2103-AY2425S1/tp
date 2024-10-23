package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.person.Person;

/**
 * Retrieves the public addresses of a contact.
 */
public class RetrievePublicAddressCommand extends Command {

    public static final String COMMAND_WORD = "retrievePublicAddress";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves public addresses of a contact.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PUBLIC_ADDRESS_NETWORK + "NETWORK "
            + "[" + PREFIX_PUBLIC_ADDRESS_LABEL + "LABEL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
            + PREFIX_PUBLIC_ADDRESS_LABEL + "MyWallet";

    public static final String MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS =
            "Retrieved %1$d %2$s public addresses for %3$s:\n%4$s";

    private final Index index;
    private final Network network;
    private final String label;

    /**
     * Creates a RetrievePublicAddressCommand to retrieve the corresponding public address.
     *
     * @param index   of the person to retrieve the public address from
     * @param network network type of desired public address
     * @param label   label of desired public address
     */
    public RetrievePublicAddressCommand(Index index, Network network, String label) {
        requireNonNull(index);
        requireNonNull(network);

        this.index = index;
        this.network = network;
        this.label = label;
    }

    /**
     * Creates a RetrievePublicAddressCommand with default value for label.
     *
     * @param index   of the person to retrieve the public address from
     * @param network network type of desired public address
     */
    public RetrievePublicAddressCommand(Index index, Network network) {
        this(index, network, "");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person desiredPerson = lastShownList.get(index.getZeroBased());
        List<PublicAddress> desiredPublicAddresses = desiredPerson.getPublicAddressesByNetwork(network)
                .stream()
                .filter(publicAddress -> publicAddress.label.toLowerCase().contains(label.toLowerCase()))
                .sorted((a, b) -> a.label.compareToIgnoreCase(b.label))
                .toList();

        return new CommandResult(String.format(MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                desiredPublicAddresses.size(),
                network,
                desiredPerson.getName(),
                desiredPublicAddresses.isEmpty() ? "-" : Messages.format(desiredPublicAddresses)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RetrievePublicAddressCommand)) {
            return false;
        }

        RetrievePublicAddressCommand otherCommand = (RetrievePublicAddressCommand) other;
        return index.equals(otherCommand.index)
                && network.equals(otherCommand.network)
                && label.equals(otherCommand.label);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("network", network)
                .add("label", label)
                .toString();
    }

}
