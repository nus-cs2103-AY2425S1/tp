package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortByDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DateDistantToRecentComparator;
import seedu.address.model.person.DateRecentToDistantComparator;

/**
 * Parses input arguments and creates a new {@code SortByDateCommand} object
 */
public class SortByDateCommandParser implements Parser<SortByDateCommand> {
    @Override
    public SortByDateCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String args = userInput.trim().toLowerCase();

        return switch (args) {
        case "recent" -> new SortByDateCommand(new DateRecentToDistantComparator());
        case "distant" -> new SortByDateCommand(new DateDistantToRecentComparator());
        default -> throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortByDateCommand.MESSAGE_USAGE));
        };
    }
}
