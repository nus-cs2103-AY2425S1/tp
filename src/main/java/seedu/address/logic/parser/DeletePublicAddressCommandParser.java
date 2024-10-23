package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DeletePublicAddressCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePublicAddressCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.addresses.Network;

/**
 * Parses input arguments and creates a new DeletePublicAddressCommand object
 */
public class DeletePublicAddressCommandParser implements Parser<DeletePublicAddressCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePublicAddressCommand
     * and returns a DeletePublicAddressCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePublicAddressCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PUBLIC_ADDRESS, PREFIX_PUBLIC_ADDRESS_LABEL);
        if (!arePrefixesPresent(argMultimap, PREFIX_PUBLIC_ADDRESS)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            Network network = ParserUtil.parseNetwork(argMultimap.getValue(PREFIX_PUBLIC_ADDRESS).get());
            if (arePrefixesPresent(argMultimap, PREFIX_PUBLIC_ADDRESS_LABEL)) {
                String label = ParserUtil.parsePublicAddressLabel(
                        argMultimap.getValue(PREFIX_PUBLIC_ADDRESS_LABEL).get()
                );
                return new DeletePublicAddressCommand(index, network, label);
            }
            return new DeletePublicAddressCommand(index, network);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }
    }

}
