package seedu.eventtory.logic.parser;

import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_VENDOR;

import seedu.eventtory.logic.commands.CreateCommand;
import seedu.eventtory.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CreateCommand object
 */
public class CreateCommandParser implements Parser<CreateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CreateCommand
     * and returns an CreateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public CreateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_VENDOR, PREFIX_EVENT);

        if (!(argMultimap.exactlyOnePrefixPresent(PREFIX_VENDOR, PREFIX_EVENT))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VENDOR, PREFIX_EVENT);

        final boolean isEventCreate = argMultimap.getValue(PREFIX_EVENT).isPresent();

        if (isEventCreate) {
            return new CreateEventCommandParser().parse(args);
        } else {
            return new CreateVendorCommandParser().parse(args);
        }
    }

}
