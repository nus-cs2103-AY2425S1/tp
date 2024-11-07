package seedu.address.logic.parser.markcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_ILLEGAL_PREFIX_USED;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.markcommands.MarkTaskCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;

/**
 * Parses input arguments and creates a new MarkTaskCommand object.
 */
public class MarkTaskCommandParser implements Parser<MarkTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public MarkTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_GROUP_NAME, PREFIX_INDEX);

        List<Prefix> allowedPrefix = new ArrayList<Prefix>(Arrays.asList(PREFIX_GROUP_NAME, PREFIX_INDEX));
        List<Prefix> invalidPrefixes = ALL_PREFIX;
        invalidPrefixes.removeAll(allowedPrefix);
        if (containsInvalidPrefix(args, invalidPrefixes)) {
            throw new ParseException(MESSAGE_ILLEGAL_PREFIX_USED + "\n" + MarkTaskCommand.MESSAGE_USAGE);
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_GROUP_NAME)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkTaskCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        GroupName groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUP_NAME).get());

        return new MarkTaskCommand(index, groupName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private boolean containsInvalidPrefix(String arg, List<Prefix> invalidPrefixes) {
        return invalidPrefixes.stream().anyMatch(prefix -> arg.contains(prefix.getPrefix()));
    }
}
