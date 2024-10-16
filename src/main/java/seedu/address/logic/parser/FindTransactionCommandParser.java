package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FindTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TransactionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTransactionCommand object
 */
public class FindTransactionCommandParser implements Parser<FindTransactionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTransactionCommand
     * and returns a FindTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindTransactionCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTransactionCommand.MESSAGE_USAGE));
        }

        // first item is index, the rest are keywords
        String[] transactionKeywords = trimmedArgs.split("\\s+");
        Index index;
        try {
            index = ParserUtil.parseIndex(transactionKeywords[0]);
            // remove the first item (index)
            transactionKeywords = Arrays.copyOfRange(transactionKeywords, 1, transactionKeywords.length);
        } catch (ParseException e) {
            // if index is not provided, default to first index
            index = Index.fromOneBased(1);
        }
        return new FindTransactionCommand(index,
                new TransactionContainsKeywordsPredicate(Arrays.asList(transactionKeywords)));
    }
}
