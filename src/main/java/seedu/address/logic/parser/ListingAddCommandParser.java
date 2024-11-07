package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ListingAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Listing;


/**
 * Parses input arguments and creates a new RemarkCommand object.
 */
public class ListingAddCommandParser implements Parser<ListingAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListingAddCommand
     * and returns a ListingAddCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ListingAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_TAG, PREFIX_ADDRESS);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListingAddCommand.MESSAGE_USAGE), ive);
        }

        String propertyType = argMultimap.getValue(PREFIX_TAG).orElse("");
        String address = argMultimap.getValue(PREFIX_ADDRESS).orElse("");

        if (propertyType.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListingAddCommand.MESSAGE_USAGE));
        }
        if (address.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListingAddCommand.MESSAGE_USAGE));
        }

        Listing listing = ParserUtil.parseListing(propertyType, address);

        return new ListingAddCommand(listing, index);
    }

}
