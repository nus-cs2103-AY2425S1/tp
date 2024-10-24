package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBMISSION;

import seedu.address.logic.commands.AddSubmissionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.submission.Submission;

/**
 * Parses input arguments and creates a new {@code AddSubmissionCommand} object.
 */
public class AddSubmissionCommandParser implements Parser<AddSubmissionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddSubmissionCommand}
     * and returns a {@code AddSubmissionCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSubmissionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBMISSION);

        if (!argMultimap.getValue(PREFIX_SUBMISSION).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubmissionCommand.MESSAGE_USAGE));
        }

        Submission submission = ParserUtil.parseSubmission(argMultimap.getValue(PREFIX_SUBMISSION).get());
        return new AddSubmissionCommand(submission);
    }
}
