package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Grade;

/**
 * Parses input arguments and creates a new AddGradeCommand object
 */
public class AddGradeCommandParser implements Parser<AddGradeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddGradeCommand
     * and returns a AddGradeCommand object for execution.
     *
     * User input format: index examName examScore examWeightage
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddGradeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] splitArgs = args.trim().split("\\s+");

        if (splitArgs.length != 4) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE));
        }

        try {
            // Use ParserUtil to parse index
            Index index = ParserUtil.parseIndex(splitArgs[0]);

            // Parse the exam name
            String examName = splitArgs[1];

            // Use ParserUtil to parse score and weightage as floats
            float examScore = ParserUtil.parseFloat(splitArgs[2]);
            float examWeightage = ParserUtil.parseFloat(splitArgs[3]);

            // Create and return the AddGradeCommand
            Grade grade = new Grade(examName, examScore, examWeightage);
            return new AddGradeCommand(index, grade);

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE), pe);
        }
    }
}
