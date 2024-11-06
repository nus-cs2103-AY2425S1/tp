package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBMISSION_STATUS;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddSubmissionStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.submission.Submission;

/**
 * Parses input arguments and creates a new {@code AddSubmissionStatusCommand} object.
 */
public class AddSubmissionStatusCommandParser implements Parser<AddSubmissionStatusCommand> {

    private static final Logger logger = LogsCenter.getLogger(AddSubmissionStatusCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddSubmissionStatusCommand}
     * and returns a {@code AddSubmissionStatusCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddSubmissionStatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBMISSION, PREFIX_SUBMISSION_STATUS);

        if (!argMultimap.getValue(PREFIX_SUBMISSION).isPresent()
                || !argMultimap.getValue(PREFIX_SUBMISSION_STATUS).isPresent()) {
            logger.log(Level.WARNING, "Missing prefix for AddSubmissionCommand or AddSubmissionStatusCommand.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddSubmissionStatusCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBMISSION, PREFIX_SUBMISSION_STATUS);

        Index index;
        Submission submission;
        String submissionStatus;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            submission = ParserUtil.parseSubmission(argMultimap.getValue(PREFIX_SUBMISSION).get());
            submissionStatus = ParserUtil
                    .parseSubmissionStatus(argMultimap.getValue(PREFIX_SUBMISSION_STATUS).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        logger.log(Level.INFO, "Parsed AddSubmissionStatusCommand successfully.");
        return new AddSubmissionStatusCommand(index, submission, submissionStatus);
    }
}
