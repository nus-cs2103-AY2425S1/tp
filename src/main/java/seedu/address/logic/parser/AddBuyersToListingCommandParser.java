package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.AddBuyersToListingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AddBuyersToListingCommand object.
 */
public class AddBuyersToListingCommandParser implements Parser<AddBuyersToListingCommand> {

    @Override
    public AddBuyersToListingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] nameKeywords = trimmedArgs.split("\\s+");

        if (nameKeywords.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddBuyersToListingCommand.MESSAGE_USAGE));
        }

        Name listingName = new Name(nameKeywords[0]);
        Set<Name> buyerNames = new HashSet<>();
        for (int i = 1; i < nameKeywords.length; i++) {
            buyerNames.add(new Name(nameKeywords[i]));
        }

        return new AddBuyersToListingCommand(listingName, buyerNames);
    }
}
