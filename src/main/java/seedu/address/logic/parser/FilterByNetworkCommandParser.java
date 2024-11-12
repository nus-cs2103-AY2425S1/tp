package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;

import seedu.address.logic.commands.FilterByNetworkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.addresses.Network;


/**
 * Parses user input for the FilterByNetworkCommand.
 */
public class FilterByNetworkCommandParser implements Parser<FilterByNetworkCommand> {

    public static final String MESSAGE_TOO_MANY_NETWORKS = "Please fill in only one network.\n%1$s";
    public static final String MESSAGE_EMPTY_NETWORK = "Please fill in at least one network.\n%1$s";
    public static final String MESSAGE_INVALID_NETWORKS = "Invalid network specified. DLTbook only supports "
            + "BTC, ETH and SOL networks.";
    public static final String MESSAGE_EMPTY_OR_WRONG_PREFIX = "Please type %1$s before the specified network.\n%2$s";

    /**
     * Parses the user input and creates a `FilterByNetworkCommand` object.
     *
     * @param args The user input string.
     * @return A `FilterByNetworkCommand` object representing the parsed command.
     * @throws ParseException If the input is invalid.
     */
    public FilterByNetworkCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Check if the userInput is empty
        if (args.isBlank()) {
            throw new ParseException(String.format(
                    MESSAGE_EMPTY_NETWORK,
                    FilterByNetworkCommand.MESSAGE_USAGE));
        }

        // Check if /c is typed in the arguments
        String prefixNetworkString = PREFIX_PUBLIC_ADDRESS_NETWORK.getPrefix();
        if (!args.contains(prefixNetworkString)) {
            throw new ParseException(String.format(
                    MESSAGE_EMPTY_OR_WRONG_PREFIX,
                    PREFIX_PUBLIC_ADDRESS_NETWORK,
                    FilterByNetworkCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PUBLIC_ADDRESS_NETWORK);

        // Check if more than one network was entered
        if (argMultimap.getAllValues(PREFIX_PUBLIC_ADDRESS_NETWORK).size() > 1) {
            throw new ParseException(String.format(
                    MESSAGE_TOO_MANY_NETWORKS,
                    FilterByNetworkCommand.MESSAGE_USAGE));
        }

        // Check if no network was entered
        if (argMultimap.getValue(PREFIX_PUBLIC_ADDRESS_NETWORK).isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_EMPTY_NETWORK,
                    FilterByNetworkCommand.MESSAGE_USAGE));
        }

        // Parse the network
        String networkString = argMultimap.getValue(PREFIX_PUBLIC_ADDRESS_NETWORK).orElse("");
        Network specifiedNetwork;

        try {
            specifiedNetwork = Network.valueOf(networkString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(MESSAGE_INVALID_NETWORKS);
        }

        return new FilterByNetworkCommand(specifiedNetwork);
    }
}
