package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM;

import seedu.address.logic.commands.AddExamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exam.Exam;

/**
 * Parses input arguments and creates a new {@code AddExamCommand} object.
 */
public class AddExamCommandParser implements Parser<AddExamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddExamCommand}
     * and returns a {@code AddExamCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddExamCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EXAM);

        if (!argMultimap.getValue(PREFIX_EXAM).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExamCommand.MESSAGE_USAGE));
        }

        Exam exam = ParserUtil.parseExam(argMultimap.getValue(PREFIX_EXAM).get());
        return new AddExamCommand(exam);
    }
}
