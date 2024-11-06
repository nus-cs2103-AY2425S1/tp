package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBMISSION;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteSubmissionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.submission.Submission;

/**
 * Parses input arguments and creates a new {@code DeleteSubmissionCommand} object.
 */
public class DeleteSubmissionCommandParser implements Parser<DeleteSubmissionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteSubmissionCommand}
     * and returns a {@code DeleteSubmissionCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeleteSubmissionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBMISSION);

        if (!argMultimap.getValue(PREFIX_SUBMISSION).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteSubmissionCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBMISSION);

        Submission submission;

        try {
            submission = ParserUtil.parseSubmission(argMultimap.getValue(PREFIX_SUBMISSION).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new DeleteSubmissionCommand(submission);
    }
}
