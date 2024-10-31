package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FinddelCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.ItemNameContainsKeywordPredicate;

import java.util.Arrays;

public class FinddelCommandParser implements Parser<FinddelCommand> {

    public FinddelCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FinddelCommand.MESSAGE_USAGE));
        }

        String[] itemNameKeywords = trimmedArgs.split("\\s+");

        return new FinddelCommand(new ItemNameContainsKeywordPredicate(Arrays.asList(itemNameKeywords)));
    }
}
