package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewEventCommand;
import seedu.address.logic.commands.ViewVendorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand and returns a ViewCommand object
     * for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_VENDOR, PREFIX_EVENT);

        if (!(argMultimap.exactlyOnePrefixPresent(PREFIX_VENDOR, PREFIX_EVENT))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VENDOR, PREFIX_EVENT);

        final boolean isEventView = argMultimap.getValue(PREFIX_EVENT).isPresent();

        if (isEventView) {
            return parseViewEventCommand(argMultimap);
        } else {
            return parseViewVendorCommand(argMultimap);
        }

    }

    /**
     * Parses the given {@code String} of arguments in the context of the ViewVendorCommand and returns a
     * ViewVendorCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private ViewVendorCommand parseViewVendorCommand(ArgumentMultimap argMultimap) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_VENDOR).get());
            return new ViewVendorCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewVendorCommand.MESSAGE_USAGE),
                    pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ViewEventCommand and returns a
     * ViewEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private ViewEventCommand parseViewEventCommand(ArgumentMultimap argMultimap) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT).get());
            return new ViewEventCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEventCommand.MESSAGE_USAGE), pe);
        }
    }

}
