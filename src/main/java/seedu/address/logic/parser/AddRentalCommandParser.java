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

        Index clientIndex = getClientIndex(argMultimap);

        if (!arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddRentalCommand.MESSAGE_REQUIRE_ADDRESS));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ADDRESS, PREFIX_RENTAL_START_DATE,
                PREFIX_RENTAL_END_DATE, PREFIX_RENT_DUE_DATE, PREFIX_MONTHLY_RENT, PREFIX_DEPOSIT,
                PREFIX_CUSTOMER_LIST);

        AddRentalDescriptor addRentalDescriptor = new AddRentalDescriptor();
        parseAllFields(argMultimap, addRentalDescriptor);
        RentalInformation rentalInformation = addRentalDescriptor.getRentalInformationEquivalentWithNull();

        return new AddRentalCommand(clientIndex, rentalInformation);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRentalCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses all fields from the given {@code ArgumentMultimap}
     * and populates the provided {@code AddRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code AddRentalDescriptor} to be populated with the parsed values.
     * @throws ParseException If any of the fields failed parsing.
     */
    private void parseAllFields(ArgumentMultimap argMultimap, AddRentalDescriptor descriptor) throws ParseException {
        parseAddress(argMultimap, descriptor);
        parseRentalStartDate(argMultimap, descriptor);
        parseRentalEndDate(argMultimap, descriptor);
        parseRentDueDate(argMultimap, descriptor);
        parseMonthlyRent(argMultimap, descriptor);
        parseDeposit(argMultimap, descriptor);
        parseCustomerList(argMultimap, descriptor);

        if (!descriptor.isAddressFieldEdited()) {
            throw new ParseException(AddRentalCommand.MESSAGE_REQUIRE_ADDRESS);
        }
    }

    /**
     * Parses address field from the given {@code ArgumentMultimap}
     * and populates the provided {@code AddRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code AddRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseAddress(ArgumentMultimap argMultimap, AddRentalDescriptor descriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            descriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
    }

    /**
     * Parses rental start date field from the given {@code ArgumentMultimap}
     * and populates the provided {@code AddRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code AddRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseRentalStartDate(ArgumentMultimap argMultimap, AddRentalDescriptor descriptor)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_RENTAL_START_DATE).isPresent()) {
            descriptor.setRentalStartDate(ParserUtil.parseRentalDate(
                    argMultimap.getValue(PREFIX_RENTAL_START_DATE).get()));
        }
    }

    /**
     * Parses rental end date field from the given {@code ArgumentMultimap}
     * and populates the provided {@code AddRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code AddRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseRentalEndDate(ArgumentMultimap argMultimap, AddRentalDescriptor descriptor)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_RENTAL_END_DATE).isPresent()) {
            descriptor.setRentalEndDate(ParserUtil.parseRentalDate(
                    argMultimap.getValue(PREFIX_RENTAL_END_DATE).get()));
        }
    }

    /**
     * Parses rent due date field from the given {@code ArgumentMultimap}
     * and populates the provided {@code AddRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code AddRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseRentDueDate(ArgumentMultimap argMultimap, AddRentalDescriptor descriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_RENT_DUE_DATE).isPresent()) {
            descriptor.setRentDueDate(ParserUtil.parseRentDueDate(argMultimap.getValue(PREFIX_RENT_DUE_DATE).get()));
        }
    }

    /**
     * Parses monthly rent field from the given {@code ArgumentMultimap}
     * and populates the provided {@code AddRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code AddRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseMonthlyRent(ArgumentMultimap argMultimap, AddRentalDescriptor descriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_MONTHLY_RENT).isPresent()) {
            descriptor.setMonthlyRent(ParserUtil.parseMonthlyRent(argMultimap.getValue(PREFIX_MONTHLY_RENT).get()));
        }
    }

    /**
     * Parses deposit field from the given {@code ArgumentMultimap}
     * and populates the provided {@code AddRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code AddRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseDeposit(ArgumentMultimap argMultimap, AddRentalDescriptor descriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_DEPOSIT).isPresent()) {
            descriptor.setDeposit(ParserUtil.parseDeposit(argMultimap.getValue(PREFIX_DEPOSIT).get()));
        }
    }

    /**
     * Parses customer list field from the given {@code ArgumentMultimap}
     * and populates the provided {@code AddRentalDescriptor}.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the arguments to be parsed.
     * @param descriptor The {@code AddRentalDescriptor} to be populated with the parsed value.
     * @throws ParseException If the field failed parsing.
     */
    private void parseCustomerList(ArgumentMultimap argMultimap, AddRentalDescriptor descriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_CUSTOMER_LIST).isPresent()) {
            descriptor.setCustomerList(ParserUtil.parseCustomerList(argMultimap.getValue(PREFIX_CUSTOMER_LIST).get()));
        }
    }
}
