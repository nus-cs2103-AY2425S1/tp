package seedu.address.logic.parser;

import java.util.stream.Stream;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.commands.AddConcertCommand;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.Address;
import seedu.address.model.commons.Name;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertDate;

/**
 * Parses input argements and create a new AddConcertCommand object
 */
public class AddConcertCommandParser implements Parser<AddConcertCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddConcertCommand and returns an AddConcertCommand object for execution.
     *
     * @param args A string representation of user arguments
     * @throws ParseException if the user input does not conform the expected
     * format
     */
    public AddConcertCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS,
                PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddConcertCommand.MESSAGE_USAGE));
        }
        assert argMultimap.getValue(PREFIX_NAME).isPresent() : "Prefix for name should be present";
        assert argMultimap.getValue(PREFIX_ADDRESS).isPresent() : "Prefix for address should be present";
        assert argMultimap.getValue(PREFIX_DATE).isPresent() : "Prefix for date should be present";

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_DATE);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        ConcertDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        Concert concert = new Concert(name, address, date);

        return new AddConcertCommand(concert);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional}
     * values in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
            Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix)
                .isPresent());
    }

}
