package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTUAL_PRICE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BoughtPropertyCommand;
import seedu.address.logic.commands.SoldPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Price;

/**
 * Parses input arguments and creates a new SoldPropertyCommand object
 */
public class SoldPropertyCommandParser implements Parser<SoldPropertyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SoldPropertyCommand
     * and returns an SoldPropertyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SoldPropertyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ACTUAL_PRICE);

        Index personIndex;
        Index propertyIndex;

        try {
            personIndex = ParserUtil.parsePersonIndex(argMultimap.getPreamble());
            propertyIndex = ParserUtil.parsePropertyIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SoldPropertyCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ACTUAL_PRICE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ACTUAL_PRICE);

        if (!argMultimap.getValue(PREFIX_ACTUAL_PRICE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    BoughtPropertyCommand.MESSAGE_USAGE + "\nActual price is required."));
        }

        Price actualPrice = ParserUtil.parseSellingPrice(argMultimap.getValue(PREFIX_ACTUAL_PRICE).get());

        return new SoldPropertyCommand(personIndex, propertyIndex, actualPrice);
    }
}
