package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddGradeCommand object
 */
public class AddGradeCommandParser implements Parser<AddGradeCommand> {

    @Override
    public AddGradeCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize the input arguments
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ASSIGNMENT, PREFIX_SCORE);

        // Check that all required prefixes are present
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ASSIGNMENT, PREFIX_SCORE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE));
        }
        // Parse and validate the name
        String name = argMultimap.getValue(PREFIX_NAME).orElse("").trim();
        if (name.isEmpty()) {
            throw new ParseException("Name cannot be empty.");
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_SCORE, PREFIX_ASSIGNMENT);

        // Get and validate the assignment name
        String assignmentName = argMultimap.getValue(PREFIX_ASSIGNMENT).orElse("").trim();
        if (assignmentName.isEmpty()) {
            throw new ParseException("Assignment name cannot be empty.");
        }

        // Get and validate the score as a string
        String scoreString = argMultimap.getValue(PREFIX_SCORE).orElse("").trim();
        if (scoreString.isEmpty()) {
            throw new ParseException("Score cannot be empty.");
        }

        Float score;
        try {
            score = Float.parseFloat(scoreString);
        } catch (NumberFormatException e) {
            throw new ParseException("Score must be a valid number.");
        }

        // Create and return the AddGradeCommand
        return new AddGradeCommand(name, score, assignmentName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
