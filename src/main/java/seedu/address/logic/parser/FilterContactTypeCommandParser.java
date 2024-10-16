package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterContactTypeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContactType;
import seedu.address.model.person.ContactTypePredicate;

public class FilterContactTypeCommandParser implements Parser<FilterContactTypeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterContactTypeCommand
     * and returns a FilterContactTypeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FilterContactTypeCommand parse(String args) throws ParseException {
        // Trim the input and extract the contact type using your parseContactType method
        String trimmedArgs = args.trim();
        ContactType contactType = ParserUtil.parseContactType(trimmedArgs);

        // Create and return the FilterContactTypeCommand with the appropriate predicate
        return new FilterContactTypeCommand(new ContactTypePredicate(contactType));
    }
}
