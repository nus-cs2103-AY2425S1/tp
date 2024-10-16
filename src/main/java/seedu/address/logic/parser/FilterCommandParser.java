package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagContainsKeywordsPredicate;

import static seedu.address.logic.Messages.MESSAGE_INVALID_FILTER_CRITERIA;

public class FilterCommandParser implements Parser<FilterCommand> {

    public FilterCommand parse(String args) throws ParseException  {
        String[] parsedArgs = args.trim().split(" ");
        switch (parsedArgs[0]){
        case "tag":
            return new FilterTagCommand(new TagContainsKeywordsPredicate(parsedArgs[1].trim()));
        default:
            throw new ParseException(MESSAGE_INVALID_FILTER_CRITERIA);
        }
    }
}
