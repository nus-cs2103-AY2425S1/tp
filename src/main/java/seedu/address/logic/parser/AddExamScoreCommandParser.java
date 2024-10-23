package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM_SCORE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddExamScoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exam.Exam;

/**
 * Parses input arguments and creates a new {@code AddExamScoreCommand} object.
 */
public class AddExamScoreCommandParser implements Parser<AddExamScoreCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddExamScoreCommand}
     * and returns a {@code AddExamScoreCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddExamScoreCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EXAM, PREFIX_EXAM_SCORE);

        if (!argMultimap.getValue(PREFIX_EXAM).isPresent() || !argMultimap.getValue(PREFIX_EXAM_SCORE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExamScoreCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        Exam exam = ParserUtil.parseExam(argMultimap.getValue(PREFIX_EXAM).get());
        String examScore = ParserUtil.parseExamScore(argMultimap.getValue(PREFIX_EXAM_SCORE).get());
        return new AddExamScoreCommand(index, exam, examScore);
    }
}
