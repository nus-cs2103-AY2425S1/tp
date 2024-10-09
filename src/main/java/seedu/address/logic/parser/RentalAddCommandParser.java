package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPOSIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHLY_RENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENT_DUE_DATE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RentalAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.rentalinformation.Address;
import seedu.address.model.rentalinformation.CustomerList;
import seedu.address.model.rentalinformation.Deposit;
import seedu.address.model.rentalinformation.MonthlyRent;
import seedu.address.model.rentalinformation.RentDueDate;
import seedu.address.model.rentalinformation.RentalDate;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class RentalAddCommandParser implements Parser<RentalAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RentalAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ADDRESS, PREFIX_RENTAL_START_DATE,
                        PREFIX_RENTAL_END_DATE, PREFIX_RENT_DUE_DATE, PREFIX_MONTHLY_RENT, PREFIX_DEPOSIT,
                        PREFIX_CUSTOMER_LIST);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RentalAddCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_ADDRESS, PREFIX_RENTAL_START_DATE,
                PREFIX_RENTAL_END_DATE, PREFIX_RENT_DUE_DATE, PREFIX_MONTHLY_RENT, PREFIX_DEPOSIT, PREFIX_CUSTOMER_LIST)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RentalAddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ADDRESS, PREFIX_RENTAL_START_DATE,
                PREFIX_RENTAL_END_DATE, PREFIX_RENT_DUE_DATE, PREFIX_MONTHLY_RENT, PREFIX_DEPOSIT,
                PREFIX_CUSTOMER_LIST);

        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        RentalDate rentalStartDate = ParserUtil.parseRentalDate(argMultimap.getValue(PREFIX_RENTAL_START_DATE).get());
        RentalDate rentalEndDate = ParserUtil.parseRentalDate(argMultimap.getValue(PREFIX_RENTAL_END_DATE).get());
        RentDueDate rentDueDate = ParserUtil.parseRentDueDate(argMultimap.getValue(PREFIX_RENT_DUE_DATE).get());
        MonthlyRent monthlyRent = ParserUtil.parseMonthlyRent(argMultimap.getValue(PREFIX_MONTHLY_RENT).get());
        Deposit deposit = ParserUtil.parseDeposit(argMultimap.getValue(PREFIX_DEPOSIT).get());
        CustomerList customerList = ParserUtil.parseCustomerList(argMultimap.getValue(PREFIX_CUSTOMER_LIST).get());

        RentalInformation rentalInformation = new RentalInformation(address, rentalStartDate, rentalEndDate,
                rentDueDate, monthlyRent, deposit, customerList);

        return new RentalAddCommand(index, rentalInformation);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
