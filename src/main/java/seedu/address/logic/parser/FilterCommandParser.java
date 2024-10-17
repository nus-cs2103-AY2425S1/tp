package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_FILTER_CRITERIA;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses the user's input and create a FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the user's input based on the prefix provided and returns the matching FilterCommand object
     * @throws ParseException if the user enters an invalid prefix
     */
    public FilterCommand parse(String args) throws ParseException {
        String[] parsedArgs = args.trim().split("/", 2);
        switch (parsedArgs[0]) {
        case "t":
            return new FilterTagCommand(new TagContainsKeywordsPredicate(new Tag(parsedArgs[1].trim())));
        default:
            throw new ParseException(MESSAGE_INVALID_FILTER_CRITERIA);
        }
    }
}
