package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns a ScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SCHEDULE_NAME, PREFIX_SCHEDULE_DATE, PREFIX_SCHEDULE_TIME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SCHEDULE_NAME, PREFIX_SCHEDULE_DATE, PREFIX_SCHEDULE_TIME);

        ScheduleCommand.ScheduleDescriptor scheduleDescriptor = new ScheduleCommand.ScheduleDescriptor();

        if (argMultimap.getValue(PREFIX_SCHEDULE_NAME).isPresent()) {
            scheduleDescriptor.setScheduleName(argMultimap.getValue(PREFIX_SCHEDULE_NAME).get());
        }

        if (argMultimap.getValue(PREFIX_SCHEDULE_DATE).isPresent()) {
            scheduleDescriptor.setDateString(argMultimap.getValue(PREFIX_SCHEDULE_DATE).get());
        }

        if (argMultimap.getValue(PREFIX_SCHEDULE_TIME).isPresent()) {
            scheduleDescriptor.setTimeString(argMultimap.getValue(PREFIX_SCHEDULE_TIME).get());
        }

        return new ScheduleCommand(index, scheduleDescriptor);
    }
}
