package seedu.address.logic.parser;

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

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.OwedAmount;
import seedu.address.model.student.PaidAmount;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Rate;
import seedu.address.model.student.Schedule;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_SCHEDULE, PREFIX_SUBJECT, PREFIX_RATE, PREFIX_PAID_AMOUNT, PREFIX_OWED_AMOUNT);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_SCHEDULE, PREFIX_SUBJECT, PREFIX_RATE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_SCHEDULE, PREFIX_SUBJECT, PREFIX_RATE, PREFIX_PAID_AMOUNT, PREFIX_OWED_AMOUNT);

        // get fields from argMultimap
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Schedule schedule = ParserUtil.parseSchedule(argMultimap.getValue(PREFIX_SCHEDULE).get());
        Subject subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get());
        Rate rate = ParserUtil.parseRate(argMultimap.getValue(PREFIX_RATE).get());

        // set optional fields
        PaidAmount paidAmount = null;
        OwedAmount owedAmount = null;
        if (argMultimap.getValue(PREFIX_PAID_AMOUNT).isPresent()) {
            paidAmount = ParserUtil.parsePaidAmount(argMultimap.getValue(PREFIX_PAID_AMOUNT).get());
        } else {
            paidAmount = new PaidAmount();
        }
        if (argMultimap.getValue(PREFIX_OWED_AMOUNT).isPresent()) {
            owedAmount = ParserUtil.parseOwedAmount(argMultimap.getValue(PREFIX_OWED_AMOUNT).get());
        } else {
            owedAmount = new OwedAmount();
        }

        // create student object
        Student student = new Student(name, phone, email, address, schedule, subject, rate, paidAmount, owedAmount);

        return new AddCommand(student);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
