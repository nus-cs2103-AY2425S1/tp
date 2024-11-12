package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AddGradeCommand object
 */
public class AddGradeCommandParser implements Parser<AddGradeCommand> {
    public static final String EMPTY_NAME = "Assignment name cannot be empty.";
    public static final String EMPTY_SCORE = "Score cannot be empty.";

    @Override
    public AddGradeCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            return AddGradeCommand.showAssignmentDefault();
        }

        // Tokenize the input arguments
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ASSIGNMENT, PREFIX_SCORE);

        // Check that all required prefixes are present
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ASSIGNMENT, PREFIX_SCORE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE));
        }
        // Parse and validate the name
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_SCORE, PREFIX_ASSIGNMENT);

        // Get  assignment name
        String assignmentName = argMultimap.getValue(PREFIX_ASSIGNMENT).orElse("").trim();
        // Get score as a string
        String scoreString = argMultimap.getValue(PREFIX_SCORE).orElse("").trim();
        // Throws exception if fields are empty
        checkAssignmentString(assignmentName, scoreString);

        float score;
        try {
            score = Float.parseFloat(scoreString);
        } catch (NumberFormatException e) {
            throw new ParseException("Score must be a valid number.");
        }

        // Create and return the AddGradeCommand
        return new AddGradeCommand(name, score, assignmentName);
    }

    /**
     * Checks if the assignment name and score string are valid (not empty).
     *
     * @param assignmentName the name of the assignment to check.
     * @param scoreString the score string to check.
     * @throws ParseException if either the assignment name or score string is empty.
     */
    private static void checkAssignmentString(String assignmentName, String scoreString) throws ParseException {
        if (assignmentName.isEmpty()) {
            throw new ParseException(EMPTY_NAME);
        }

        if (scoreString.isEmpty()) {
            throw new ParseException(EMPTY_SCORE);
        }
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
