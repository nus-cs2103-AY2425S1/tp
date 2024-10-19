package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortByPriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PriorityHighToLowComparator;
import seedu.address.model.person.PriorityLowToHighComparator;

/**
 * Parses input arguments and creates a new {@code SortByPriorityCommand} object
 */
public class SortByPriorityCommandParser implements Parser<SortByPriorityCommand> {
    @Override
    public SortByPriorityCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String args = userInput.trim().toLowerCase();

        return switch (args) {
        case "high" -> new SortByPriorityCommand(new PriorityHighToLowComparator());
        case "low" -> new SortByPriorityCommand(new PriorityLowToHighComparator());
        default -> throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortByPriorityCommand.MESSAGE_USAGE));
        };
    }
}
