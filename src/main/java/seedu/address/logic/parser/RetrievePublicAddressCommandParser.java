package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RetrievePublicAddressCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.addresses.Network;

/**
 * Parses input arguments and creates a new {@link RetrievePublicAddressCommand} object.
 */
public class RetrievePublicAddressCommandParser implements Parser<RetrievePublicAddressCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of RetrievePublicAddressCommand
     * and returns an RetrievePublicAddressCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RetrievePublicAddressCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PUBLIC_ADDRESS_NETWORK, PREFIX_PUBLIC_ADDRESS_LABEL);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RetrievePublicAddressCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_PUBLIC_ADDRESS_NETWORK).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RetrievePublicAddressCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PUBLIC_ADDRESS_NETWORK, PREFIX_PUBLIC_ADDRESS_LABEL);

        Network network = ParserUtil.parseNetwork(argMultimap.getValue(PREFIX_PUBLIC_ADDRESS_NETWORK).get());

        return argMultimap.getValue(PREFIX_PUBLIC_ADDRESS_LABEL)
                .map(label -> new RetrievePublicAddressCommand(index, network, label))
                .orElseGet(() -> new RetrievePublicAddressCommand(index, network));
    }

}
