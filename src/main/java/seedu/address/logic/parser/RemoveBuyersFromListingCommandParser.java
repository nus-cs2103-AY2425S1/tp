package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.RemoveBuyersFromListingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

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

        // Parse listing name
        Name listingName = ParserUtil.parseName(argMultimap.getPreamble());

        // Parse buyer names
        Set<Name> buyerNames = new HashSet<>();
        for (String buyerName : argMultimap.getAllValues(PREFIX_BUYER)) {
            buyerNames.add(ParserUtil.parseName(buyerName));
        }

        // Check if at least one buyer is specified
        if (buyerNames.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveBuyersFromListingCommand.MESSAGE_USAGE));
        }

        return new RemoveBuyersFromListingCommand(listingName, buyerNames);
    }
}
