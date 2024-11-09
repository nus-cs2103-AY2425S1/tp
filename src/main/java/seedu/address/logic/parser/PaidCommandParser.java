package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PaidCommand;
import seedu.address.logic.commands.PaidCommand.PaidPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Frequency;
import seedu.address.model.person.LastPaidDate;


/**
 * Parses input arguments and creates a new PaidCommand object
 */
public class PaidCommandParser implements Parser<PaidCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PaidCommand
     * and returns a PaidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PaidCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FREQUENCY, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_BIRTHDAY, PREFIX_TAG);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_FREQUENCY).isPresent()) {
            throw new ParseException(PaidCommand.MESSAGE_NO_FREQUENCY);
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent() || argMultimap.getValue(PREFIX_PHONE).isPresent()
                || argMultimap.getValue(PREFIX_EMAIL).isPresent() || argMultimap.getValue(PREFIX_ADDRESS).isPresent()
                || argMultimap.getValue(PREFIX_BIRTHDAY).isPresent() || argMultimap.getValue(PREFIX_TAG).isPresent()) {
            throw new ParseException("Other prefixes are not allowed for Paid Command");
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FREQUENCY);
        PaidPersonDescriptor paidPersonDescriptor = new PaidPersonDescriptor();
        Frequency frequency = ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQUENCY).get());
        if (frequency.value.equals("0")) {
            throw new ParseException(Frequency.MESSAGE_CONSTRAINTS);
        }

        paidPersonDescriptor.setHasPaid();
        paidPersonDescriptor.setFrequency(ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQUENCY).get()));

        LocalDate today = LocalDate.now();
        String day = String.format("%02d", today.getDayOfMonth());
        String month = String.format("%02d", today.getMonthValue());
        String year = String.format("%04d", today.getYear());
        String todayDate = day + " " + month + " " + year;
        paidPersonDescriptor.setLastPaidDate(new LastPaidDate(todayDate));

        return new PaidCommand(index, paidPersonDescriptor);
    }
}
