package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.parser.CliSyntax.PREFIX_ADD_LESSON;
import static tuteez.logic.parser.CliSyntax.PREFIX_DELETE_LESSON;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.AddLessonCommand;
import tuteez.logic.commands.DeleteLessonCommand;
import tuteez.logic.commands.LessonCommand;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.person.lesson.Lesson;

/**
 * Parses input arguments and creates a new LessonCommand object
 */
public class LessonCommandParser implements Parser<LessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LessonCommand and returns a LessonCommand
     * object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public LessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ADD_LESSON, PREFIX_DELETE_LESSON);

        Index personIndex;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_ADD_LESSON).isPresent()) {
            String lessonString = argMultimap.getValue(PREFIX_ADD_LESSON).get();
            try {
                Lesson lesson = ParserUtil.parseLesson(lessonString);
                return new AddLessonCommand(personIndex, lesson);
            } catch (ParseException pe) {
                throw new ParseException(Lesson.MESSAGE_CONSTRAINTS, pe);
            }
        }

        if (argMultimap.getValue(PREFIX_DELETE_LESSON).isPresent()) {
            Index lessonIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_DELETE_LESSON).get());
            return new DeleteLessonCommand(personIndex, lessonIndex);
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonCommand.MESSAGE_USAGE)
        );
    }
}
