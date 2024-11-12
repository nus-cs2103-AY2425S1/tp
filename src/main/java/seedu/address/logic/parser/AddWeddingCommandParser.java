package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_NAME;

import seedu.address.logic.commands.AddWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Parses input arguments and creates a new AddWeddingCommand object
 */
public class AddWeddingCommandParser implements Parser<AddWeddingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddWeddingCommand.
     * and returns an AddWeddingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddWeddingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEDDING_NAME, PREFIX_VENUE, PREFIX_DATE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_WEDDING_NAME, PREFIX_VENUE, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddWeddingCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_WEDDING_NAME, PREFIX_VENUE, PREFIX_DATE);
        WeddingName name = ParserUtil.parseWeddingName(argMultimap.getValue(PREFIX_WEDDING_NAME).get().trim());
        Venue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get().trim());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get().trim());

        Wedding wedding = new Wedding(name, venue, date);

        return new AddWeddingCommand(wedding);
    }

}
