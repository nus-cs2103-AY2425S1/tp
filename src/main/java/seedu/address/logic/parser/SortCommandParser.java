package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DateDistantToRecentComparator;
import seedu.address.model.person.DateRecentToDistantComparator;
import seedu.address.model.person.PriorityHighToLowComparator;
import seedu.address.model.person.PriorityLowToHighComparator;

/**
 * Parses input arguments and creates a new {@code SortByPriorityCommand} object
 */
public class SortCommandParser implements Parser<SortCommand> {
    @Override
    public SortCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String args = userInput.trim().toLowerCase();

        return switch (args) {
        case "high" -> new SortCommand(new PriorityHighToLowComparator());
        case "low" -> new SortCommand(new PriorityLowToHighComparator());
        case "recent" -> new SortCommand(new DateRecentToDistantComparator());
        case "distant" -> new SortCommand(new DateDistantToRecentComparator());
        default -> throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        };
    }
}
