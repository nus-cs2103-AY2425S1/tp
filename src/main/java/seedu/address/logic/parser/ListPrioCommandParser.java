package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import seedu.address.logic.commands.ListPrioCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Priority;
import seedu.address.model.person.PriorityMatchesPredicate;

/**
 * Parses user input for the ListPrioCommand.
 */
public class ListPrioCommandParser implements Parser<ListPrioCommand> {

    /**
     * Parses the given user input string and returns a ListPrioCommand.
     *
     * @param userInput The user input string containing the command and its parameters.
     * @return A ListPrioCommand object containing the parsed priority predicate.
     * @throws ParseException If the user input does not conform to the expected format or
     *                        if the required priority prefix is missing.
     */
    @Override
    public ListPrioCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_PRIORITY);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PRIORITY);
        if (!argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPrioCommand.MESSAGE_USAGE));
        }
        String priorityStr = argMultimap.getValue(PREFIX_PRIORITY).orElse("");
        Priority priority = ParserUtil.parsePriority(priorityStr);
        PriorityMatchesPredicate predicate = new PriorityMatchesPredicate(priority);
        return new ListPrioCommand(predicate);

    }
}
