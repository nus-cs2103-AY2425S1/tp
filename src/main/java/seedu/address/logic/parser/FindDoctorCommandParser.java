package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_ARGUMENT_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindDoctorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.doctor.FindDoctorPredicate;

/**
 * Parses input arguments and creates a new FindDoctorCommand object
 */
public class FindDoctorCommandParser implements Parser<FindDoctorCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindDoctorCommand
     * and returns a FindDoctorCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDoctorCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDoctorCommand.MESSAGE_USAGE));
        }

        // Ensure that the input is a string of alphabets separated by spaces
        if (trimmedArgs.matches("^.*[^a-zA-Z ].*$")) {
            throw new ParseException(String.format(MESSAGE_INVALID_ARGUMENT_FORMAT, FindDoctorCommand.MESSAGE_USAGE));
        }

        return new FindDoctorCommand(new FindDoctorPredicate(trimmedArgs));
    }
}
