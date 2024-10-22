package seedu.hireme.logic.parser;

import seedu.hireme.commons.util.AppUtil;
import seedu.hireme.logic.Messages;
import seedu.hireme.logic.commands.FilterCommand;
import seedu.hireme.logic.commands.exceptions.CommandException;
import seedu.hireme.logic.parser.exceptions.ParseException;
import seedu.hireme.logic.validator.StatusValidator;
import seedu.hireme.model.internshipapplication.Email;
import seedu.hireme.model.internshipapplication.Status;
import seedu.hireme.model.internshipapplication.StatusPredicate;

import static seedu.hireme.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.toUpperCase().trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        if (!StatusValidator.of().validate(trimmedArgs)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        };
        return new FilterCommand(new StatusPredicate(trimmedArgs));
    }

}
