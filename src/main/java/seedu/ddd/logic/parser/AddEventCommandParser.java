package seedu.ddd.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_CLIENTS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_VENDORS;

import java.util.Set;
import java.util.stream.Stream;

import seedu.ddd.logic.commands.AddEventCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.contact.common.Id;
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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESC, PREFIX_CLIENTS, PREFIX_VENDORS);

        if ((!arePrefixesPresent(argMultimap, PREFIX_DESC, PREFIX_CLIENTS, PREFIX_VENDORS)
                || !argMultimap.getPreamble().isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DESC);
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get());
        Set<Id> clientIds = ParserUtil.parseIds(argMultimap.getAllValues(PREFIX_CLIENTS));
        Set<Id> vendorIds = ParserUtil.parseIds(argMultimap.getAllValues(PREFIX_VENDORS));

        return new AddEventCommand(clientIds, vendorIds, description);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
