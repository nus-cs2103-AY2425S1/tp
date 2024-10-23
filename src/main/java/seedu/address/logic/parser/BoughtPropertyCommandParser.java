package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BoughtPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Price;
import java.util.Optional;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTUAL_PRICE;

/**
 * Parses input arguments and creates a new AddPropertyToBuyCommand object
 */
public class BoughtPropertyCommandParser implements Parser<BoughtPropertyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPropertyToBuyCommand
     * and returns an AddPropertyToBuyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BoughtPropertyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ACTUAL_PRICE);

        Index personIndex;
        Index propertyIndex;

        try {
            personIndex = ParserUtil.parsePersonIndex(argMultimap.getPreamble());
            propertyIndex = ParserUtil.parsePropertyIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    BoughtPropertyCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ACTUAL_PRICE);

        Optional<String> actualPriceValue = argMultimap.getValue(PREFIX_ACTUAL_PRICE);
        Price actualPrice = actualPriceValue.isPresent()
                ? ParserUtil.parseBuyingPrice(actualPriceValue.get())
                : new Price("-1");

        return new BoughtPropertyCommand(personIndex, propertyIndex, Optional.of(actualPrice));
    }
}
