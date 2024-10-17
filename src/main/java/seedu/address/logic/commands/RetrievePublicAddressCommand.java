package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
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

    // TODO: Add
    public static final String MESSAGE_USAGE = "";

    public static final String MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS =
            "Retrieved %1$d %2$s public addresses for %3$s:\n%4$s";

    private final Index index;
    private final Network network;
    private final String walletName;

    /**
     * Creates a RetrievePublicAddressCommand to retrieve the corresponding public address.
     *
     * @param index      of the person to retrieve the public address from
     * @param network    network type of desired public address
     * @param walletName wallet name of desired public address
     */
    public RetrievePublicAddressCommand(Index index, Network network, String walletName) {
        requireNonNull(index);
        requireNonNull(network);

        this.index = index;
        this.network = network;
        this.walletName = walletName;
    }

    /**
     * Creates a RetrievePublicAddressCommand with default value for walletName.
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
                .filter(publicAddress -> publicAddress.tag.toLowerCase().contains(walletName.toLowerCase()))
                .toList();

        return new CommandResult(String.format(MESSAGE_RETRIEVE_PUBLIC_ADDRESS_SUCCESS,
                desiredPublicAddresses.size(),
                network,
                desiredPerson.getName(),
                Messages.format(desiredPublicAddresses)));
    }

}
