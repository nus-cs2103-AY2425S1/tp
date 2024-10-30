package seedu.eventtory.logic.parser;

import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_VENDOR;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.commands.DeleteCommand;
import seedu.eventtory.logic.commands.DeleteEventCommand;
import seedu.eventtory.logic.commands.DeleteVendorCommand;
import seedu.eventtory.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand and returns a DeleteCommand
     * object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_VENDOR, PREFIX_EVENT);

        if (!(argMultimap.exactlyOnePrefixPresent(PREFIX_VENDOR, PREFIX_EVENT))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VENDOR, PREFIX_EVENT);

        final boolean isEventDelete = argMultimap.getValue(PREFIX_EVENT).isPresent();

        if (isEventDelete) {
            return parseEventDelete(argMultimap);
        } else {
            return parseVendorDelete(argMultimap);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteVendorCommand and returns a
     * DeleteVendorCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private DeleteVendorCommand parseVendorDelete(ArgumentMultimap argMultimap) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_VENDOR).get());
            return new DeleteVendorCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteVendorCommand.MESSAGE_USAGE),
                    pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEventCommand and returns a
     * DeleteEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private DeleteEventCommand parseEventDelete(ArgumentMultimap argMultimap) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT).get());
            return new DeleteEventCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE),
                    pe);
        }
    }

}
