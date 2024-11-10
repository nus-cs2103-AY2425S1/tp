package seedu.address.logic.parser.listingcommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.listingcommands.RemoveBuyersFromListingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RemoveBuyersFromListingCommand} object.
 **/
public class RemoveBuyersFromListingCommandParser implements Parser<RemoveBuyersFromListingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveBuyersFromListingCommand
     * and returns a RemoveBuyersFromListingCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public RemoveBuyersFromListingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_BUYER);

        // Parse index
        String indexOneBased = argMultimap.getPreamble().trim();
        if (!StringUtil.isNonZeroUnsignedInteger(indexOneBased)) {
            throw new ParseException(Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
        }
        Index index = ParserUtil.parseIndex(indexOneBased);

        // Parse buyer indexes
        Set<Index> buyerIndexes = new HashSet<>();
        for (String buyerIndex : argMultimap.getAllValues(PREFIX_BUYER)) {
            buyerIndexes.add(ParserUtil.parseIndex(buyerIndex));
        }

        // Check if at least one buyer is specified
        if (buyerIndexes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveBuyersFromListingCommand.MESSAGE_USAGE));
        }

        return new RemoveBuyersFromListingCommand(index, buyerIndexes);
    }
}
