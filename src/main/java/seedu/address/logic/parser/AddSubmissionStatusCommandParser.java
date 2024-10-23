package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBMISSION_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddSubmissionStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.submission.Submission;

/**
 * Parses input arguments and creates a new {@code AddSubmissionStatusCommand} object.
 */
public class AddSubmissionStatusCommandParser implements Parser<AddSubmissionStatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddSubmissionStatusCommand}
     * and returns a {@code AddSubmissionStatusCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSubmissionStatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBMISSION, PREFIX_SUBMISSION_STATUS);

        if (!argMultimap.getValue(PREFIX_SUBMISSION).isPresent()
                || !argMultimap.getValue(PREFIX_SUBMISSION_STATUS).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddSubmissionStatusCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        Submission submission = ParserUtil.parseSubmission(argMultimap.getValue(PREFIX_SUBMISSION).get());
        String submissionStatus = ParserUtil
                .parseSubmissionStatus(argMultimap.getValue(PREFIX_SUBMISSION_STATUS).get());
        return new AddSubmissionStatusCommand(index, submission, submissionStatus);
    }
}
