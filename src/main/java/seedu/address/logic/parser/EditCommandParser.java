package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWED_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAID_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_SCHEDULE, PREFIX_SUBJECT, PREFIX_RATE, PREFIX_PAID_AMOUNT, PREFIX_OWED_AMOUNT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_SCHEDULE, PREFIX_SUBJECT, PREFIX_RATE, PREFIX_PAID_AMOUNT, PREFIX_OWED_AMOUNT);

        EditStudentDescriptor editStudentDescriptor = new EditStudentDescriptor();

        // set editStudentDescriptor fields if present
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editStudentDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editStudentDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editStudentDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editStudentDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_SCHEDULE).isPresent()) {
            editStudentDescriptor.setSchedule(ParserUtil.parseSchedule(argMultimap.getValue(PREFIX_SCHEDULE).get()));
        }
        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            editStudentDescriptor.setSubject(ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get()));
        }
        if (argMultimap.getValue(PREFIX_RATE).isPresent()) {
            editStudentDescriptor.setRate(ParserUtil.parseRate(argMultimap.getValue(PREFIX_RATE).get()));
        }
        if (argMultimap.getValue(PREFIX_PAID_AMOUNT).isPresent()) {
            editStudentDescriptor.setPaidAmount(ParserUtil.parsePaidAmount(
                    argMultimap.getValue(PREFIX_PAID_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_OWED_AMOUNT).isPresent()) {
            editStudentDescriptor.setOwedAmount(ParserUtil.parseOwedAmount(
                    argMultimap.getValue(PREFIX_OWED_AMOUNT).get()));
        }
        if (!editStudentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editStudentDescriptor);
    }


}
