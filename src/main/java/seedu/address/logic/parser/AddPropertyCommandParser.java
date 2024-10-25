package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOWN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEDROOMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BATHROOMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddPropertyCommand object.
 */
public class AddPropertyCommandParser implements Parser<AddPropertyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPropertyCommand
     * and returns an AddPropertyCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddPropertyCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_PROPERTY_ADDRESS, PREFIX_TOWN, PREFIX_TYPE,
                PREFIX_SIZE, PREFIX_BEDROOMS, PREFIX_BATHROOMS, PREFIX_PRICE);

        // Parse the index of the person to add the property to
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPropertyCommand.MESSAGE_USAGE), pe);
        }

        // Parse mandatory fields
        String address = argMultimap.getValue(PREFIX_PROPERTY_ADDRESS)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE)));

        String town = argMultimap.getValue(PREFIX_TOWN)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE)));

        String propertyType = argMultimap.getValue(PREFIX_TYPE)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE)));

        double size = ParserUtil.parseDouble(argMultimap.getValue(PREFIX_SIZE)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE))));

        int numberOfBedrooms = ParserUtil.parseInteger(argMultimap.getValue(PREFIX_BEDROOMS)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE))));

        int numberOfBathrooms = ParserUtil.parseInteger(argMultimap.getValue(PREFIX_BATHROOMS)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE))));

        double price = ParserUtil.parseDouble(argMultimap.getValue(PREFIX_PRICE)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE))));

        // Create and return the AddPropertyCommand object
        return new AddPropertyCommand(index, address, town, propertyType, size,
                numberOfBedrooms, numberOfBathrooms, price);
    }
}
