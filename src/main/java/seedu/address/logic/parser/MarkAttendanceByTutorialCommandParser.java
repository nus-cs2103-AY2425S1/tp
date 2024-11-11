package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_LOGGER_FOR_EXCEPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.MarkAttendanceByTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Attendance;
import seedu.address.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new MarkAttendanceByTutorialCommand object
 */
public class MarkAttendanceByTutorialCommandParser implements Parser<MarkAttendanceByTutorialCommand> {

    private final Logger logger = LogsCenter.getLogger(MarkAttendanceByTutorialCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttendanceByTutorialCommand
     * and returns a MarkAttendanceByTutorialCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkAttendanceByTutorialCommand parse(String args) throws ParseException {
        logger.info("Starting to parse arguments");
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ATTENDANCE, PREFIX_TUTORIAL);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ATTENDANCE, PREFIX_TUTORIAL);

        if (argMultimap.getValue(PREFIX_ATTENDANCE).isEmpty() || argMultimap.getValue(PREFIX_TUTORIAL).isEmpty()
                || !argMultimap.getPreamble().isEmpty()) {
            logger.warning(String.format(MESSAGE_LOGGER_FOR_EXCEPTION, MarkAttendanceByTutorialCommandParser.class
                    + "\n - Missing prefix or index specified"));
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByTutorialCommand.MESSAGE_USAGE));
        }

        Tutorial tutorial = ParserUtil.parseTutorial(argMultimap.getValue(PREFIX_TUTORIAL).get());
        Attendance attendance = ParserUtil.parseAttendance(argMultimap.getValue(PREFIX_ATTENDANCE).get());

        logger.info("Successfully parsed arguments");
        return new MarkAttendanceByTutorialCommand(tutorial, attendance);
    }
}
