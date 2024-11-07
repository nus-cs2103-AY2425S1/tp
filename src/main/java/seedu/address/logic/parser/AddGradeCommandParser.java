package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHTAGE;

import java.util.stream.Stream;

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
     * <p>
     * User input format: index examName examScore examWeightage
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddGradeCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SCORE, PREFIX_WEIGHTAGE);

        if (!arePrefixesPresent(argMultiMap, PREFIX_NAME, PREFIX_SCORE, PREFIX_WEIGHTAGE) || argMultiMap.getPreamble()
                .isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE));
        }

        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_SCORE, PREFIX_WEIGHTAGE);

        // Use ParserUtil to parse index
        Index index = ParserUtil.parseIndex(argMultiMap.getPreamble());

        // Parse the exam name
        String examName = ParserUtil.parseTestName(argMultiMap.getValue(PREFIX_NAME).get()).toLowerCase();

        // Use ParserUtil to parse score and weightage as floats
        float examScore = ParserUtil.parseScore(argMultiMap.getValue(PREFIX_SCORE).get());
        float examWeightage = ParserUtil.parseWeightage(argMultiMap.getValue(PREFIX_WEIGHTAGE).get());

        // Create and return the AddGradeCommand
        Grade grade = new Grade(examName, examScore, examWeightage);
        return new AddGradeCommand(index, grade);

    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
