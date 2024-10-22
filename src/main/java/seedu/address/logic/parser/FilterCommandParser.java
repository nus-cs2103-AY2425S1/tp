package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonHasFeaturePredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses the given {@code String} of arguments in the context of the FilterByTagCommand
 * and returns a FilterByTagCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class FilterCommandParser implements Parser<FilterCommand> {
    @Override
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
              ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_PHONE);

        //at least one feature is present
        if ((!arePrefixesPresent(argMultimap, PREFIX_TAG)
              && !arePrefixesPresent(argMultimap, PREFIX_PHONE))
              || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        //you can only filter for one value per feature
        if (argMultimap.getAllValues(PREFIX_TAG).size() > 1 || argMultimap.getAllValues(PREFIX_PHONE).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        Tag tag = null;
        Phone phone = null;
        if (argMultimap.getAllValues(PREFIX_TAG).size() == 1) { //there is a tag to filter by
            tag = ParserUtil.parseTag(argMultimap.getAllValues(PREFIX_TAG).get(0));
        }
        if (argMultimap.getAllValues(PREFIX_PHONE).size() == 1) { //there is a phone value to filter by
            phone = ParserUtil.parsePhone(argMultimap.getAllValues(PREFIX_PHONE).get(0));
        }
        return new FilterCommand(new PersonHasFeaturePredicate(tag, phone));
    }
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
