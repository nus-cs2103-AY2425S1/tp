package tuteez.logic.parser;

import static java.util.Objects.requireNonNull;
import static tuteez.logic.Messages.MESSAGE_MISSING_LESSON_FIELD_PREFIX;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;
import static tuteez.logic.parser.ParserUtil.parsePersonIndex;
import static tuteez.logic.parser.ParserUtil.validateNonEmptyArgs;
import static tuteez.logic.parser.ParserUtil.validatePrefixExists;

import java.util.List;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.AddLessonCommand;
import tuteez.logic.commands.LessonCommand;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.person.lesson.Lesson;

/**
 * Parses input arguments and creates a new AddLessonCommand object
 */
public class AddLessonCommandParser implements Parser<LessonCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddLessonCommand
     * and returns an AddLessonCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LESSON);

        validateNonEmptyArgs(args, AddLessonCommand.MESSAGE_USAGE);
        validatePrefixExists(argMultimap, PREFIX_LESSON, MESSAGE_MISSING_LESSON_FIELD_PREFIX);

        Index personIndex = parsePersonIndex(argMultimap);

        return createAddLessonCommand(personIndex, argMultimap);
    }

    private AddLessonCommand createAddLessonCommand(Index personIndex, ArgumentMultimap argMultimap)
            throws ParseException {
        List<String> lessonStrings = argMultimap.getAllValues(PREFIX_LESSON);
        List<Lesson> lessons = ParserUtil.parseLessons(lessonStrings);
        return new AddLessonCommand(personIndex, lessons);
    }
}
