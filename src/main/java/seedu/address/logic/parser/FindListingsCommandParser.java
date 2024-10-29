package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindListingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.listing.ListingContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindListingsCommand object
 */
public class FindListingsCommandParser implements Parser<FindListingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindListingsCommand
     * and returns a FindListingsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindListingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindListingCommand.MESSAGE_USAGE));
        }

        String[] listingKeywords = trimmedArgs.split("\\s+");

        return new FindListingCommand(new ListingContainsKeywordsPredicate(Arrays.asList(listingKeywords)));
    }

}
