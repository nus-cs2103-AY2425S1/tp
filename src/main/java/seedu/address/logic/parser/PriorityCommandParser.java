package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import seedu.address.logic.commands.PriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Priority;



/**
 * Parses user input for the {@link PriorityCommand} and creates a new instance of it.
 */
public class PriorityCommandParser implements Parser<PriorityCommand> {

    /**
     * Parses the given arguments string and creates a {@link PriorityCommand} object.
     *
     * @param args the arguments string containing user input.
     * @return A {@link PriorityCommand} object containing the parsed NRIC and priority.
     * @throws ParseException if the user input does not conform to the expected format or
     *         if the NRIC or priority is not provided.
     */
    public PriorityCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NRIC, PREFIX_PRIORITY);

        if (argMultimap.getValue(PREFIX_NRIC).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_PRIORITY).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityCommand.MESSAGE_USAGE));
        }

        String nricStr = argMultimap.getValue(PREFIX_NRIC).orElse("");
        Nric nric = ParserUtil.parseNric(nricStr);
        String priorityStr = argMultimap.getValue(PREFIX_PRIORITY).orElse("");
        Priority priority = ParserUtil.parsePriority(priorityStr);

        return new PriorityCommand(nric, priority);
    }
}
