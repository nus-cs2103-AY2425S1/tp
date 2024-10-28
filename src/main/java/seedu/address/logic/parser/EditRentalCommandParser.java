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
 * Parses input arguments and creates a new EditRentalCommandParser object.
 */
public class EditRentalCommandParser implements Parser<EditRentalCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditRentalCommandParser
     * and returns an EditRentalCommandParser object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditRentalCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RENTAL_INDEX, PREFIX_ADDRESS, PREFIX_RENTAL_START_DATE,
                        PREFIX_RENTAL_END_DATE, PREFIX_RENT_DUE_DATE, PREFIX_MONTHLY_RENT, PREFIX_DEPOSIT,
                        PREFIX_CUSTOMER_LIST);

        Index clientIndex = getClientIndex(argMultimap);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_RENTAL_INDEX, PREFIX_ADDRESS, PREFIX_RENTAL_START_DATE,
                PREFIX_RENTAL_END_DATE, PREFIX_RENT_DUE_DATE, PREFIX_MONTHLY_RENT, PREFIX_DEPOSIT,
                PREFIX_CUSTOMER_LIST);

        Index rentalIndex = getRentalIndex(argMultimap);

        EditRentalDescriptor editRentalDescriptor = new EditRentalDescriptor();
        parseAllFields(argMultimap, editRentalDescriptor);

        return new EditRentalCommand(clientIndex, rentalIndex, editRentalDescriptor);
    }

    /**
     * Parses the client index from the given {@code ArgumentMultimap}
     * and returns the corresponding {@code Index} object.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @return The parsed {@code Index} of the client.
     * @throws ParseException If client index failed parsing.
     */
    private Index getClientIndex(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRentalCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses the rental index from the given {@code ArgumentMultimap}
     * and returns the corresponding {@code Index} object.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @return The parsed {@code Index} of the rental information.
     * @throws ParseException If rental index failed parsing or missing argument.
     */
    private Index getRentalIndex(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_RENTAL_INDEX).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRentalCommand.MESSAGE_USAGE));
        }

        try {
            return ParserUtil.parseIndex(argMultimap.getValue(PREFIX_RENTAL_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRentalCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses all fields from the given {@code ArgumentMultimap}
     * and populates the provided {@code EditRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code EditRentalDescriptor} to be populated with the parsed values.
     * @throws ParseException If any of the fields failed parsing.
     */
    private void parseAllFields(ArgumentMultimap argMultimap, EditRentalDescriptor descriptor) throws ParseException {
        parseAddress(argMultimap, descriptor);
        parseRentalStartDate(argMultimap, descriptor);
        parseRentalEndDate(argMultimap, descriptor);
        parseRentDueDate(argMultimap, descriptor);
        parseMonthlyRent(argMultimap, descriptor);
        parseDeposit(argMultimap, descriptor);
        parseCustomerList(argMultimap, descriptor);

        if (!descriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRentalCommand.MESSAGE_NOT_EDITED);
        }
    }

    /**
     * Parses address field from the given {@code ArgumentMultimap}
     * and populates the provided {@code EditRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code EditRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseAddress(ArgumentMultimap argMultimap, EditRentalDescriptor descriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            descriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
    }

    /**
     * Parses rental start date field from the given {@code ArgumentMultimap}
     * and populates the provided {@code EditRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code EditRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseRentalStartDate(ArgumentMultimap argMultimap, EditRentalDescriptor descriptor)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_RENTAL_START_DATE).isPresent()) {
            descriptor.setRentalStartDate(ParserUtil.parseRentalDate(
                    argMultimap.getValue(PREFIX_RENTAL_START_DATE).get()));
        }

    }

    /**
     * Parses rental end date field from the given {@code ArgumentMultimap}
     * and populates the provided {@code EditRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code EditRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseRentalEndDate(ArgumentMultimap argMultimap, EditRentalDescriptor descriptor)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_RENTAL_END_DATE).isPresent()) {
            descriptor.setRentalEndDate(ParserUtil.parseRentalDate(
                    argMultimap.getValue(PREFIX_RENTAL_END_DATE).get()));
        }
    }

    /**
     * Parses rent due date field from the given {@code ArgumentMultimap}
     * and populates the provided {@code EditRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code EditRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseRentDueDate(ArgumentMultimap argMultimap, EditRentalDescriptor descriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_RENT_DUE_DATE).isPresent()) {
            descriptor.setRentDueDate(ParserUtil.parseRentDueDate(argMultimap.getValue(PREFIX_RENT_DUE_DATE).get()));
        }
    }

    /**
     * Parses monthly rent field from the given {@code ArgumentMultimap}
     * and populates the provided {@code EditRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code EditRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseMonthlyRent(ArgumentMultimap argMultimap, EditRentalDescriptor descriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_MONTHLY_RENT).isPresent()) {
            descriptor.setMonthlyRent(ParserUtil.parseMonthlyRent(argMultimap.getValue(PREFIX_MONTHLY_RENT).get()));
        }

    }

    /**
     * Parses deposit field from the given {@code ArgumentMultimap}
     * and populates the provided {@code EditRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code EditRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseDeposit(ArgumentMultimap argMultimap, EditRentalDescriptor descriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_DEPOSIT).isPresent()) {
            descriptor.setDeposit(ParserUtil.parseDeposit(argMultimap.getValue(PREFIX_DEPOSIT).get()));
        }
    }

    /**
     * Parses customer list field from the given {@code ArgumentMultimap}
     * and populates the provided {@code EditRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code EditRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseCustomerList(ArgumentMultimap argMultimap, EditRentalDescriptor descriptor)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_CUSTOMER_LIST).isPresent()) {
            descriptor.setCustomerList(ParserUtil.parseCustomerList(argMultimap.getValue(PREFIX_CUSTOMER_LIST).get()));
        }
    }
}
