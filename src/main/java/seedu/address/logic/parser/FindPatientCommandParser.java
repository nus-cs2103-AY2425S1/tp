package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_ARGUMENT_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindPatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.FindPatientPredicate;

/**
 * Parses input arguments and creates a new FindPatientCommand object
 */
public class FindPatientCommandParser implements Parser<FindPatientCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindPatientCommand
     * and returns a FindPatientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPatientCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPatientCommand.MESSAGE_USAGE));
        }

        // Ensure that the input is a string of alphabets separated by spaces
        if (trimmedArgs.matches("^.*[^a-zA-Z ].*$")) {
            throw new ParseException(String.format(MESSAGE_INVALID_ARGUMENT_FORMAT, FindPatientCommand.MESSAGE_USAGE));
        }

        return new FindPatientCommand(new FindPatientPredicate(trimmedArgs));
    }
}
