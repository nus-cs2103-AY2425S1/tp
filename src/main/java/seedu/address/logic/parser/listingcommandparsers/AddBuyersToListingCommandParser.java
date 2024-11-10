package seedu.address.logic.parser.listingcommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.listingcommands.AddBuyersToListingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddBuyersToListingCommand object.
 */
public class AddBuyersToListingCommandParser implements Parser<AddBuyersToListingCommand> {

    @Override
    public AddBuyersToListingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_BUYER);

        // Parse index
        String indexOneBased = argMultimap.getPreamble().trim();
        if (!StringUtil.isNonZeroUnsignedInteger(indexOneBased)) {
            throw new ParseException(Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
        }
        Index index = ParserUtil.parseIndex(indexOneBased);

        // Parse buyer indexes with the "buyer/" prefix
        Set<Index> buyerIndexes = new HashSet<>();

        for (String buyerIndexStr : argMultimap.getAllValues(PREFIX_BUYER)) {
            if (!StringUtil.isNonZeroUnsignedInteger(buyerIndexStr.trim())) {
                throw new ParseException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            buyerIndexes.add(ParserUtil.parseIndex(buyerIndexStr));
        }

        // Check if there are buyer indexes; otherwise, throw an error
        if (buyerIndexes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddBuyersToListingCommand.MESSAGE_USAGE));
        }

        return new AddBuyersToListingCommand(index, buyerIndexes);
    }
}
