package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagMatchesPredicate;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    @Override
    public SortCommand parse(String args) throws ParseException {
        String tag = args.trim();
        if (tag.isEmpty()) {
            throw new ParseException("Tag cannot be empty!");
        }
        return new SortCommand(new TagMatchesPredicate(tag));
    }
}
