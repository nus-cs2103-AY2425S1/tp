package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.addresses.Network;

/**
 * Retrieves the public addresses of a contact.
 */
public class RetrievePublicAddressCommand extends Command {

    public static final String COMMAND_WORD = "retrievePublicAddress";

    // TODO: Add
    public static final String MESSAGE_USAGE = "";

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
     * @param index      of the person to retrieve the public address from
     * @param network    network type of desired public address
     */
    public RetrievePublicAddressCommand(Index index, Network network) {
        this(index, network, "");
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("");
    }

}
