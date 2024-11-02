package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_DUPLICATE_LESSON_INDEX;
import static tuteez.logic.Messages.MESSAGE_INVALID_ADDLESSON_PREFIX;
import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_DELETELESSON_PREFIX;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_LESSON_FIELD_PREFIX;
import static tuteez.logic.Messages.MESSAGE_MISSING_LESSON_INDEX;
import static tuteez.logic.Messages.MESSAGE_MISSING_LESSON_INDEX_FIELD_PREFIX;
import static tuteez.logic.Messages.MESSAGE_MISSING_PERSON_INDEX;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;

import java.util.ArrayList;
import java.util.List;

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
    private final String commandWord;

    public LessonCommandParser(String commandWord) {
        this.commandWord = commandWord;
    }

    private String getCommandWord() {
        return commandWord;
    }
    /**
     * Parses the given {@code String} of arguments in the context of the LessonCommand and returns a LessonCommand
     * object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public LessonCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_MISSING_PERSON_INDEX + "\n" + LessonCommand.MESSAGE_USAGE));
        }

        String[] argParts = trimmedArgs.split("\\s+", 2);
        if (argParts.length < 2) {
            // Only person index provided, no lesson information
            if (isAddCommand()) {
                throw new ParseException(MESSAGE_MISSING_LESSON_FIELD_PREFIX);
            } else {
                throw new ParseException(MESSAGE_MISSING_LESSON_INDEX_FIELD_PREFIX);
            }
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_LESSON, PREFIX_LESSON_INDEX);

        Index personIndex;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            if (argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_MISSING_PERSON_INDEX + "\n" + LessonCommand.MESSAGE_USAGE));
            }
            
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_INVALID_PERSON_INDEX_FORMAT + "\n" + LessonCommand.MESSAGE_USAGE), pe);
        }

        String commandWord = getCommandWord();

        if (commandWord.equals(LessonCommand.COMMAND_WORD_ADD)
                || commandWord.equals(LessonCommand.COMMAND_WORD_ADD_ALT)) {
            return parseAddLesson(personIndex, argMultimap);
        } else {
            return parseDeleteLesson(personIndex, argMultimap);
        }
    }

    private List<Index> parseIndices(ArgumentMultimap argMultimap) throws ParseException {
        List<String> indexStrings = argMultimap.getAllValues(PREFIX_LESSON_INDEX);
        List<Index> indices = new ArrayList<>();
        for (String indexString : indexStrings) {
            Index index = ParserUtil.parseIndex(indexString);
            if (indices.contains(index)) {
                throw new ParseException(MESSAGE_DUPLICATE_LESSON_INDEX);
            }
            indices.add(index);
        }
        return indices;
    }

    private List<Lesson> parseLessons(ArgumentMultimap argMultimap) throws ParseException {
        List<String> lessonStrings = argMultimap.getAllValues(PREFIX_LESSON);
        return ParserUtil.parseLessons(lessonStrings);
    }

    private LessonCommand parseAddLesson(Index personIndex, ArgumentMultimap argMultimap) throws ParseException {
        validateAddLessonPrefix(argMultimap);
        List<String> lessonStrings = argMultimap.getAllValues(PREFIX_LESSON);
        if (lessonStrings.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_MISSING_LESSON_FIELD_PREFIX));
        }
        return new AddLessonCommand(personIndex, parseLessons(argMultimap));
    }

    private LessonCommand parseDeleteLesson(Index personIndex, ArgumentMultimap argMultimap) throws ParseException {
        validateDeleteLessonPrefix(argMultimap);
        List<String> indexStrings = argMultimap.getAllValues(PREFIX_LESSON_INDEX);
        if (indexStrings.stream().anyMatch(String::isBlank)) {
            throw new ParseException(MESSAGE_MISSING_LESSON_INDEX);
        }
        if (indexStrings.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_MISSING_LESSON_INDEX_FIELD_PREFIX));
        }
        return new DeleteLessonCommand(personIndex, parseIndices(argMultimap));
    }

    private void validateAddLessonPrefix(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getAllValues(PREFIX_LESSON_INDEX).isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_ADDLESSON_PREFIX);
        }
    }

    private void validateDeleteLessonPrefix(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getAllValues(PREFIX_LESSON).isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_DELETELESSON_PREFIX);
        }
    }
}
