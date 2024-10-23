package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.ParserUtil.parsePersonIndexString;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingName;

/**
 * Parses input arguments and creates a new AddWeddingCommand object
 */
public class AddWeddingCommandParser implements Parser<AddWeddingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddWeddingCommand
     * and returns an AddWeddingCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddWeddingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME,
                        CliSyntax.PREFIX_DATE, CliSyntax.PREFIX_CONTACT);
        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddWeddingCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATE, PREFIX_CONTACT);
        WeddingName name = ParserUtil.parseWeddingName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        WeddingDate date = ParserUtil.parseWeddingDate(argMultimap.getValue(CliSyntax.PREFIX_DATE).get());

        Set<Index> contactIndexes = new HashSet<>();
        if (argMultimap.getValue(PREFIX_CONTACT).isPresent()) {
            contactIndexes = parsePersonIndexString(argMultimap.getValue(PREFIX_CONTACT).get());
        }

        Wedding wedding = new Wedding(name, date);
        return new AddWeddingCommand(wedding, contactIndexes);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
