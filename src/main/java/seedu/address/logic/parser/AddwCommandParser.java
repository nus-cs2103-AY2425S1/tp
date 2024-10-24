package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddwCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.wedding.Client;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;

/**
 * Parses input arguments and creates a new AddwCommand object
 */
public class AddwCommandParser implements Parser<AddwCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddwCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME, PREFIX_CLIENT, PREFIX_DATE, PREFIX_VENUE);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_NAME, PREFIX_CLIENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddwCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_CLIENT, PREFIX_DATE, PREFIX_VENUE);

        Name weddingName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String stringClient = ParserUtil.parseClient(argMultimap.getValue(PREFIX_CLIENT).get());

        Index index = null;
        NameMatchesKeywordPredicate predicate = null;

        if (isNumeric(stringClient)) {
            index = ParserUtil.parseIndex(stringClient);
        } else {
            String[] nameKeywords = stringClient.split("\\s+");
            predicate = new NameMatchesKeywordPredicate(Arrays.asList(nameKeywords));
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).orElse(null));
        Venue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).orElse(null));

        Wedding wedding = new Wedding(weddingName, date, venue);

        return new AddwCommand(index, predicate, wedding);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+");
    }
}
