package seedu.ddd.logic.parser.add;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_EVENT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_CLIENTS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_VENDORS;

import java.util.Set;
import java.util.stream.Stream;

import seedu.ddd.logic.commands.add.AddEventCommand;
import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.ArgumentTokenizer;
import seedu.ddd.logic.parser.Parser;
import seedu.ddd.logic.parser.ParserUtil;
import seedu.ddd.logic.parser.Prefix;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.AddressBook;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.event.common.Date;
import seedu.ddd.model.event.common.Description;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_DESC, PREFIX_DATE, PREFIX_CLIENTS, PREFIX_VENDORS, FLAG_EVENT);

        if ((!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DESC, PREFIX_DATE, PREFIX_CLIENTS, PREFIX_VENDORS)
                || !argMultimap.getPreamble().isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DESC, PREFIX_DATE);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Set<Id> clientContactIds = ParserUtil.parseIds(argMultimap.getAllValues(PREFIX_CLIENTS));
        Set<Id> vendorContactIds = ParserUtil.parseIds(argMultimap.getAllValues(PREFIX_VENDORS));
        Id eventId = new Id(AddressBook.getNextEventId());

        return new AddEventCommand(name, description, date, clientContactIds, vendorContactIds, eventId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
