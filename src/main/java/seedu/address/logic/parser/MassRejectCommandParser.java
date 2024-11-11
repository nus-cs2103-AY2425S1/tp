package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.MassRejectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.JobCodePredicate;
import seedu.address.model.person.JobCodeTagPredicate;
import seedu.address.model.person.TagPredicate;

/**
 * Parses input arguments and creates a new MassRejectCommand object
 */
public class MassRejectCommandParser implements Parser<MassRejectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * MassRejectCommand and returns a MassRejectCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public MassRejectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_JOBCODE, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassRejectCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_JOBCODE,
                PREFIX_TAG, PREFIX_REMARK);

        if (argMultimap.getValue(PREFIX_JOBCODE).isPresent() && argMultimap.getValue(PREFIX_TAG).isPresent()) {
            // Job Code and Tag mass reject
            String jobCode = ParserUtil.parseJobCode(argMultimap.getValue(PREFIX_JOBCODE).get()).value;
            String tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get()).tagCode;
            return new MassRejectCommand(new JobCodeTagPredicate(jobCode, tag));
        } else if (argMultimap.getValue(PREFIX_JOBCODE).isPresent() && argMultimap.getValue(PREFIX_TAG).isEmpty()) {
            // Job Code mass reject
            String jobCode = ParserUtil.parseJobCode(argMultimap.getValue(PREFIX_JOBCODE).get()).value;
            return new MassRejectCommand(new JobCodePredicate(jobCode));
        } else if (argMultimap.getValue(PREFIX_TAG).isPresent() && argMultimap.getValue(PREFIX_JOBCODE).isEmpty()) {
            // Tag mass reject
            String tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get()).tagCode;
            return new MassRejectCommand(new TagPredicate(tag));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassRejectCommand.MESSAGE_USAGE));
        }

    }
}

