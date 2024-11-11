package seedu.eventtory.logic.parser;

import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_VENDOR;

import seedu.eventtory.logic.commands.ListCommand;
import seedu.eventtory.logic.commands.ListEventCommand;
import seedu.eventtory.logic.commands.ListVendorCommand;
import seedu.eventtory.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_VENDOR, PREFIX_EVENT);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VENDOR, PREFIX_EVENT);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        final boolean isEventList = argMultimap.getValue(PREFIX_EVENT).isPresent();
        final boolean isVendorList = argMultimap.getValue(PREFIX_VENDOR).isPresent();

        if (args.isEmpty()
                || (isVendorList && isEventList)) {
            return new ListCommand();
        } else if (isEventList) {
            return new ListEventCommand();
        } else {
            return new ListVendorCommand();
        }
    }
}
