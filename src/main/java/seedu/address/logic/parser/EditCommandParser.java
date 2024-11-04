package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandCommons.getErrorMessage;
import static seedu.address.logic.commands.CommandCommons.parseField;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPEND_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIER;

import java.util.LinkedHashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_INCOME, PREFIX_JOB, PREFIX_TIER, PREFIX_NEW_REMARK, PREFIX_APPEND_REMARK, PREFIX_STATUS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        if (ArgumentMultimap.isBothRemarkNewAndRemarkAppendPresent(argMultimap, PREFIX_NEW_REMARK,
                PREFIX_APPEND_REMARK)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    Messages.MESSAGE_CONCURRENT_RN_RA_FIELDS + EditCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_INCOME, PREFIX_JOB, PREFIX_TIER, PREFIX_NEW_REMARK, PREFIX_APPEND_REMARK, PREFIX_STATUS);

        EditClientDescriptor editClientDescriptor = new EditClientDescriptor();
        Set<String> errors = new LinkedHashSet<>();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editClientDescriptor.setName(
                    parseField(() -> ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()), errors));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editClientDescriptor.setPhone(
                    parseField(() -> ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()), errors));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editClientDescriptor.setEmail(
                    parseField(() -> ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()), errors));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editClientDescriptor.setAddress(
                    parseField(() -> ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()),
                            errors));
        }
        if (argMultimap.getValue(PREFIX_JOB).isPresent()) {
            editClientDescriptor.setJob(parseField(() -> ParserUtil.parseJob(argMultimap.getValue(PREFIX_JOB).get()),
                    errors));
        }
        if (argMultimap.getValue(PREFIX_INCOME).isPresent()) {
            editClientDescriptor.setIncome(parseField(() -> ParserUtil.parseIncome(
                    argMultimap.getValue(PREFIX_INCOME).get()), errors));
        }
        if (argMultimap.getValue(PREFIX_TIER).isPresent()) {
            editClientDescriptor.setTier(parseField(() -> ParserUtil.parseTier(
                    argMultimap.getValue(PREFIX_TIER).get()), errors));
        }
        if (argMultimap.getValue(PREFIX_NEW_REMARK).isPresent()) {
            editClientDescriptor.setNewRemark(parseField(() -> ParserUtil.parseNewRemark(
                    argMultimap.getValue(PREFIX_NEW_REMARK).get()), errors));
        }
        if (argMultimap.getValue(PREFIX_APPEND_REMARK).isPresent()) {
            editClientDescriptor.setAppendedRemark(parseField(() -> ParserUtil.parseNewRemark(
                    argMultimap.getValue(PREFIX_APPEND_REMARK).get()), errors));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            editClientDescriptor.setStatus(parseField(() -> ParserUtil.parseStatus(
                    argMultimap.getValue(PREFIX_STATUS).get()), errors));
        }

        if (!errors.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    getErrorMessage(errors)));
        }

        if (!editClientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED + "\n"
                    + EditCommand.MESSAGE_USAGE_OPTIONAL_PARAMETERS);
        }

        return new EditCommand(index, editClientDescriptor);
    }

}
