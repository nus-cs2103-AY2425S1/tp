package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteVendorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_VENDOR, PREFIX_EVENT);

        final boolean isEventDelete = arePrefixesPresent(argMultimap, PREFIX_EVENT);
        final boolean isVendorDelete = arePrefixesPresent(argMultimap, PREFIX_VENDOR);

        // exactly one prefix should be present
        if (!(isVendorDelete ^ isEventDelete)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VENDOR, PREFIX_EVENT);

        if (isEventDelete) {
            return parseEventDelete(argMultimap);
        } else {
            return parseVendorDelete(argMultimap);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteVendorCommand
     * and returns a DeleteVendorCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private DeleteVendorCommand parseVendorDelete(ArgumentMultimap argMultimap) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_VENDOR).get());
            return new DeleteVendorCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteVendorCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteEventCommand
     * and returns a DeleteEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private DeleteEventCommand parseEventDelete(ArgumentMultimap argMultimap) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT).get());
            return new DeleteEventCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
