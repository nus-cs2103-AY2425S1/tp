package tutorease.address.logic.parser;

import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.logic.commands.FindLessonCommand;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.lesson.LessonContainsNamesPredicate;

/**
 * Parses input arguments and creates a new FindLessonCommand object
 */
public class FindLessonCommandParser implements Parser<FindLessonCommand> {
    private static Logger logger = LogsCenter.getLogger(FindLessonCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindLessonCommand
     * and returns a FindLessonCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindLessonCommand parse(String args) throws ParseException {
        logger.log(Level.INFO, "Parsing FindLessonCommand with args: " + args);

        validateNull(args);
        String trimmedArgs = args.trim();
        isEmptyArgs(trimmedArgs);
        String[] nameKeywords = trimmedArgs.split("\\s+");

        logger.log(Level.INFO, "Parsed FindLessonCommand with keywords: " + Arrays.toString(nameKeywords));
        return new FindLessonCommand(new LessonContainsNamesPredicate(Arrays.asList(nameKeywords)));
    }

    private static void isEmptyArgs(String trimmedArgs) throws ParseException {
        logger.log(Level.INFO, "Validating empty arguments for FindLessonCommand");
        if (trimmedArgs.isEmpty()) {
            logger.log(Level.WARNING, "Empty arguments found for FindLessonCommand");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLessonCommand.MESSAGE_USAGE));
        }
    }

    private static void validateNull(String args) throws ParseException {
        logger.log(Level.INFO, "Validating null arguments for FindLessonCommand");
        if (args == null) {
            logger.log(Level.WARNING, "Null arguments found for FindLessonCommand");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLessonCommand.MESSAGE_USAGE));
        }
    }

}

