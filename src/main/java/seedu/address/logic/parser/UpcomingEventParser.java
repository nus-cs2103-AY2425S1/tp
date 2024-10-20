package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.eventcommands.UpcomingEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.types.common.DateTime;

/**
 * Parses input arguments and creates a new UpcomingEventCommand object
 */
public class UpcomingEventParser implements Parser<Command> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpcomingEventCommand
     * and returns a UpcomingEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(ModelType modelType, String args) throws ParseException {
        args = args.strip();
        boolean isInt = true;
        boolean isDate = true;
        int range = 0;
        DateTime date = new DateTime("1970-01-01 00:00");

        try {
            range = Integer.parseInt(args);
        } catch (NumberFormatException e) {
            isInt = false;
        }

        try {
            date = new DateTime(args + " 00:00");
        } catch (IllegalArgumentException e) {
            isDate = false;
        }

        if (!isInt && !isDate) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpcomingEventCommand.MESSAGE_USAGE));
        }

        if (isInt) {
            return new UpcomingEventCommand(range);
        } else {
            return new UpcomingEventCommand(date);
        }
    }
}
