package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ListingDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new RemarkCommand object.
 */
public class ListingDeleteCommandParser implements Parser<ListingDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListingDeleteCommand
     * and returns a ListingDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ListingDeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Index personIndex;
        Index listingIndex;
        try {
            String[] bothIndexes = argMultimap.getPreamble().trim().split(" ");
            personIndex = ParserUtil.parseIndex(bothIndexes[0]);
            listingIndex = ParserUtil.parseIndex(bothIndexes[1]);

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListingDeleteCommand.MESSAGE_USAGE), ive);
        }

        return new ListingDeleteCommand(personIndex, listingIndex);
    }

}
