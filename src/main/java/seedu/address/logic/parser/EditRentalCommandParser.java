package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPOSIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHLY_RENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENT_DUE_DATE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditRentalCommand;
import seedu.address.logic.commands.EditRentalCommand.EditRentalDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditClientCommand object
 */
public class EditRentalCommandParser implements Parser<EditRentalCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditClientCommand
     * and returns an EditClientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRentalCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RENTAL_INDEX, PREFIX_ADDRESS, PREFIX_RENTAL_START_DATE,
                        PREFIX_RENTAL_END_DATE, PREFIX_RENT_DUE_DATE, PREFIX_MONTHLY_RENT, PREFIX_DEPOSIT,
                        PREFIX_CUSTOMER_LIST);

        Index clientIndex;
        try {
            clientIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRentalCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_RENTAL_INDEX, PREFIX_ADDRESS, PREFIX_RENTAL_START_DATE,
                PREFIX_RENTAL_END_DATE, PREFIX_RENT_DUE_DATE, PREFIX_MONTHLY_RENT, PREFIX_DEPOSIT,
                PREFIX_CUSTOMER_LIST);

        if (argMultimap.getValue(PREFIX_RENTAL_INDEX).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRentalCommand.MESSAGE_USAGE));
        }

        Index rentalIndex;
        try {
            rentalIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_RENTAL_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRentalCommand.MESSAGE_USAGE), pe);
        }

        EditRentalDescriptor editRentalDescriptor = new EditRentalDescriptor();

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editRentalDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (argMultimap.getValue(PREFIX_RENTAL_START_DATE).isPresent()) {
            editRentalDescriptor.setRentalStartDate(ParserUtil.parseRentalDate(
                    argMultimap.getValue(PREFIX_RENTAL_START_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_RENTAL_END_DATE).isPresent()) {
            editRentalDescriptor.setRentalEndDate(ParserUtil.parseRentalDate(
                    argMultimap.getValue(PREFIX_RENTAL_END_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_RENT_DUE_DATE).isPresent()) {
            editRentalDescriptor.setRentDueDate(ParserUtil.parseRentDueDate(
                    argMultimap.getValue(PREFIX_RENT_DUE_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_MONTHLY_RENT).isPresent()) {
            editRentalDescriptor.setMonthlyRent(ParserUtil.parseMonthlyRent(
                    argMultimap.getValue(PREFIX_MONTHLY_RENT).get()));
        }

        if (argMultimap.getValue(PREFIX_DEPOSIT).isPresent()) {
            editRentalDescriptor.setDeposit(ParserUtil.parseDeposit(
                    argMultimap.getValue(PREFIX_DEPOSIT).get()));
        }

        if (argMultimap.getValue(PREFIX_CUSTOMER_LIST).isPresent()) {
            editRentalDescriptor.setCustomerList(ParserUtil.parseCustomerList(
                    argMultimap.getValue(PREFIX_CUSTOMER_LIST).get()));
        }

        if (!editRentalDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRentalCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRentalCommand(clientIndex, rentalIndex, editRentalDescriptor);
    }
}
