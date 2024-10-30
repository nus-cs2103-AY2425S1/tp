package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.PriorityCommand.MESSAGE_INVALID_PATIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import seedu.address.logic.commands.PriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PriorityCommand object.
 */
public class PriorityCommandParser implements Parser<PriorityCommand> {
    public static final String MESSAGE_INVALID_PRIORITY_LEVEL =
            "Invalid priority level. Please enter a valid integer (1, 2, or 3) or 'reset'.";
    private static final String RESET = "reset";

    @Override
    public PriorityCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PRIORITY);

        try {
            String[] splitArgs = argMultimap.getPreamble().trim().split("\\s+");
            if (splitArgs.length != 1) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
            }

            int id = Integer.parseInt(splitArgs[0]);

            if (id <= 0) {
                throw new ParseException(MESSAGE_INVALID_PATIENT_ID);
            }

            if (argMultimap.getValue(PREFIX_PRIORITY).isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
            }

            // Check if the priority level is specified with PREFIX_PRIORITY
            String priorityValue = argMultimap.getValue(PREFIX_PRIORITY).orElse("");
            if (priorityValue.equalsIgnoreCase(RESET)) {
                // Reset command: set to default level (3)
                return new PriorityCommand(id, 3, true);
            } else {
                // Parse the priority level as an integer and validate it
                try {
                    int level = Integer.parseInt(priorityValue);
                    if (level < 1 || level > 3) {
                        throw new ParseException(MESSAGE_INVALID_PRIORITY_LEVEL);
                    }
                    return new PriorityCommand(id, level, false);
                } catch (NumberFormatException e) {
                    throw new ParseException(MESSAGE_INVALID_PRIORITY_LEVEL);
                }
            }
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
        }
    }
}
