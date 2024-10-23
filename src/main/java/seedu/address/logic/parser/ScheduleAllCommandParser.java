package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ScheduleAllCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ScheduleAllCommand object.
 */
public class ScheduleAllCommandParser implements Parser<ScheduleAllCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleAllCommand
     * and returns a ScheduleAllCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleAllCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Since no arguments are required for this command, we just return a new ScheduleAllCommand
        return new ScheduleAllCommand();
    }
}
