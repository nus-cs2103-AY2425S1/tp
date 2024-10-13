package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Grade;

/**
 * Parses input arguments and creates a new GradeCommand object
 */
public class GradeCommandParser implements Parser<GradeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GradeCommand
     * and returns a GradeCommand object for execution.
     *
     * User input format: index examName examScore examWeightage
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public GradeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] splitArgs = args.trim().split("\\s+");

        if (splitArgs.length != 4) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE));
        }

        try {
            // Use ParserUtil to parse index
            Index index = ParserUtil.parseIndex(splitArgs[0]);

            // Parse the exam name
            String examName = splitArgs[1];

            // Use ParserUtil to parse score and weightage as floats
            float examScore = ParserUtil.parseFloat(splitArgs[2]);
            float examWeightage = ParserUtil.parseFloat(splitArgs[3]);

            // Create and return the GradeCommand
            Grade grade = new Grade(examName, examScore, examWeightage);
            return new GradeCommand(index, grade);

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE), pe);
        }
    }
}
