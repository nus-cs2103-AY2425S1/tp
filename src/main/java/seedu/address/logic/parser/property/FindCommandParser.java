package seedu.address.logic.parser.property;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.property.FindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.Address;
import seedu.address.model.property.AddressContainsKeywordsPredicate;
import seedu.address.model.property.LandlordName;
import seedu.address.model.property.LandlordNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser extends CommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS);
        LandlordName extractedName;
        Address extractedAddress;

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS)
                || !argMultimap.getPreamble().isEmpty()
                || arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_NAME) && argMultimap.getPreamble().isEmpty()) {
            extractedName = ParserUtil.parseLandlordName(argMultimap.getValue(PREFIX_NAME).get());
            String trimmedExtractedName = extractedName.toString().trim();
            if (trimmedExtractedName.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            String[] keywords = trimmedExtractedName.split("\\s+");
            return new FindCommand(new LandlordNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_ADDRESS) && argMultimap.getPreamble().isEmpty()) {
            extractedAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            String trimmedExtractedAddress = extractedAddress.toString().trim();
            if (trimmedExtractedAddress.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            String[] keywords = trimmedExtractedAddress.split("\\s+");
            return new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
