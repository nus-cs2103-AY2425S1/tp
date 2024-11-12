package tuteez.logic.parser;

import static java.util.Objects.requireNonNull;
import static tuteez.logic.Messages.MESSAGE_DUPLICATE_LESSON_INDEX;
import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_LESSON_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_LESSON_INDEX;
import static tuteez.logic.Messages.MESSAGE_MISSING_LESSON_INDEX_FIELD_PREFIX;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tuteez.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static tuteez.logic.parser.ParserUtil.parsePersonIndex;
import static tuteez.logic.parser.ParserUtil.validateNonEmptyArgs;
import static tuteez.logic.parser.ParserUtil.validatePrefixExists;

import java.util.ArrayList;
import java.util.List;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.DeleteLessonCommand;
import tuteez.logic.commands.LessonCommand;
import tuteez.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteLessonCommand object
 */
public class DeleteLessonCommandParser implements Parser<LessonCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLessonCommand
     * and returns a DeleteLessonCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LESSON_INDEX);

        validateNonEmptyArgs(args, DeleteLessonCommand.MESSAGE_USAGE);
        validatePrefixExists(argMultimap, PREFIX_LESSON_INDEX, MESSAGE_MISSING_LESSON_INDEX_FIELD_PREFIX);

        Index personIndex = parsePersonIndex(argMultimap);

        return createDeleteLessonCommand(personIndex, argMultimap);
    }

    private DeleteLessonCommand createDeleteLessonCommand(Index personIndex, ArgumentMultimap argMultimap)
            throws ParseException {
        List<String> indexStrings = argMultimap.getAllValues(PREFIX_LESSON_INDEX);

        if (indexStrings.stream().anyMatch(String::isBlank)) {
            throw new ParseException(MESSAGE_MISSING_LESSON_INDEX);
        }

        List<Index> indices = new ArrayList<>();
        for (String indexString : indexStrings) {
            try {
                Index index = ParserUtil.parseIndex(indexString);
                if (indices.contains(index)) {
                    throw new ParseException(MESSAGE_DUPLICATE_LESSON_INDEX);
                }
                indices.add(index);
            } catch (ParseException pe) {
                if (pe.getMessage().equals(MESSAGE_INVALID_INDEX)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MESSAGE_INVALID_LESSON_INDEX_FORMAT));
                }
                // Pass through other ParseExceptions (like duplicate index) as-is
                throw pe;
            }
        }

        return new DeleteLessonCommand(personIndex, indices);
    }
}
