package seedu.address.logic.parser.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.lesson.AddToLessonCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;

/**
 * Parses input arguments and creates a new AddToLessonCommand object.
 */
public class AddToLessonCommandParser implements Parser<AddToLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddToLessonCommand
     * and returns an AddToLessonCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected
     *                        format
     */
    public AddToLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddToLessonCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getAllValues(PREFIX_NAME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToLessonCommand.MESSAGE_USAGE));
        }

        // Parse names
        List<Name> studentNames = new ArrayList<>();
        for (String nameString : argMultimap.getAllValues(PREFIX_NAME)) {
            try {
                Name name = ParserUtil.parseName(nameString);
                studentNames.add(name);
            } catch (ParseException e) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS, e);
            }
        }

        return new AddToLessonCommand(index, studentNames);
    }
}
