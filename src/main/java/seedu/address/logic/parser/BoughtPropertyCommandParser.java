package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTUAL_PRICE;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BoughtPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Price;

/**
 * Parses input arguments and creates a new BoughtPropertyCommand object
 */
public class BoughtPropertyCommandParser implements Parser<BoughtPropertyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BoughtPropertyCommand
     * and returns an BoughtPropertyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BoughtPropertyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ACTUAL_PRICE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ACTUAL_PRICE);

        Index personIndex;
        Index propertyIndex;
        ArrayList<String> parameters = ParserUtil.getParametersList(argMultimap.getPreamble());

        if (parameters.size() != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    BoughtPropertyCommand.MESSAGE_USAGE));
        }
        try {
            personIndex = ParserUtil.parseIndex(parameters.get(0));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    BoughtPropertyCommand.MESSAGE_USAGE), pe);
        }
        try {
            propertyIndex = ParserUtil.parseIndex(parameters.get(1));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX,
                    BoughtPropertyCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_ACTUAL_PRICE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    BoughtPropertyCommand.MESSAGE_USAGE + "\nActual price is required."));
        }

        Price actualPrice = ParserUtil.parseBuyingPrice(argMultimap.getValue(PREFIX_ACTUAL_PRICE).get());

        return new BoughtPropertyCommand(personIndex, propertyIndex, actualPrice);
    }
}
