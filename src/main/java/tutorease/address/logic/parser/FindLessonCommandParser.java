package tutorease.address.logic.parser;

import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import tutorease.address.logic.commands.FindLessonCommand;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.lesson.LessonContainsNamesPredicate;

/**
 * Parses input arguments and creates a new FindLessonCommand object
 */
public class FindLessonCommandParser implements Parser<FindLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindLessonCommand
     * and returns a FindLessonCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindLessonCommand parse(String args) throws ParseException {
        if (args == null) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLessonCommand.MESSAGE_USAGE));
        }
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLessonCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindLessonCommand(new LessonContainsNamesPredicate(Arrays.asList(nameKeywords)));
    }

}

