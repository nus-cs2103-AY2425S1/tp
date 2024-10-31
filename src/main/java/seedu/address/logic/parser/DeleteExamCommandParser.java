package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM;

import seedu.address.logic.commands.DeleteExamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exam.Exam;

/**
 * Parses input arguments and creates a new {@code DeleteExamCommand} object.
 */
public class DeleteExamCommandParser implements Parser<DeleteExamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteExamCommand}
     * and returns a {@code DeleteExamCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteExamCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EXAM);

        if (!argMultimap.getValue(PREFIX_EXAM).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteExamCommand.MESSAGE_USAGE));
        }

        Exam exam = ParserUtil.parseExam(argMultimap.getValue(PREFIX_EXAM).get());
        return new DeleteExamCommand(exam);
    }
}
