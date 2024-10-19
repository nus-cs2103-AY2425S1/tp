package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterByTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonHasTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses the given {@code String} of arguments in the context of the FilterByTagCommand
 * and returns a FilterByTagCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class FilterByTagCommandParser implements Parser<FilterByTagCommand> {
    @Override
    public FilterByTagCommand parse(String args) throws ParseException {
        System.out.println(args);
        ArgumentMultimap argMultimap =
              ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)
              || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterByTagCommand.MESSAGE_USAGE));
        }
        Set<Tag> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        List<Tag> tagList = new ArrayList<>(tagSet);
        return new FilterByTagCommand(new PersonHasTagPredicate(tagList));
    }
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
