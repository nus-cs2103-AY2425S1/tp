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
import seedu.address.logic.commands.AddRentalCommand;
import seedu.address.logic.commands.AddRentalCommand.AddRentalDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * Parses input arguments and creates a new AddRentalCommand object
 */
public class AddRentalCommandParser implements Parser<AddRentalCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddRentalCommand
     * and returns an AddRentalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddRentalCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ADDRESS, PREFIX_RENTAL_START_DATE, PREFIX_RENTAL_END_DATE,
                        PREFIX_RENT_DUE_DATE, PREFIX_MONTHLY_RENT, PREFIX_DEPOSIT, PREFIX_CUSTOMER_LIST);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRentalCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddRentalCommand.MESSAGE_REQUIRE_ADDRESS));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ADDRESS, PREFIX_RENTAL_START_DATE,
                PREFIX_RENTAL_END_DATE, PREFIX_RENT_DUE_DATE, PREFIX_MONTHLY_RENT, PREFIX_DEPOSIT,
                PREFIX_CUSTOMER_LIST);

        AddRentalDescriptor addRentalDescriptor = new AddRentalDescriptor();

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            addRentalDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (argMultimap.getValue(PREFIX_RENTAL_START_DATE).isPresent()) {
            addRentalDescriptor.setRentalStartDate(ParserUtil.parseRentalDate(
                    argMultimap.getValue(PREFIX_RENTAL_START_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_RENTAL_END_DATE).isPresent()) {
            addRentalDescriptor.setRentalEndDate(ParserUtil.parseRentalDate(
                    argMultimap.getValue(PREFIX_RENTAL_END_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_RENT_DUE_DATE).isPresent()) {
            addRentalDescriptor.setRentDueDate(ParserUtil.parseRentDueDate(
                    argMultimap.getValue(PREFIX_RENT_DUE_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_MONTHLY_RENT).isPresent()) {
            addRentalDescriptor.setMonthlyRent(ParserUtil.parseMonthlyRent(
                    argMultimap.getValue(PREFIX_MONTHLY_RENT).get()));
        }

        if (argMultimap.getValue(PREFIX_DEPOSIT).isPresent()) {
            addRentalDescriptor.setDeposit(ParserUtil.parseDeposit(
                    argMultimap.getValue(PREFIX_DEPOSIT).get()));
        }

        if (argMultimap.getValue(PREFIX_CUSTOMER_LIST).isPresent()) {
            addRentalDescriptor.setCustomerList(ParserUtil.parseCustomerList(
                    argMultimap.getValue(PREFIX_CUSTOMER_LIST).get()));
        }

        if (!addRentalDescriptor.isAddressFieldEdited()) {
            throw new ParseException(AddRentalCommand.MESSAGE_REQUIRE_ADDRESS);
        }

        RentalInformation rentalInformation = addRentalDescriptor.getRentalInformationEquivalentWithNull();

        return new AddRentalCommand(index, rentalInformation);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
