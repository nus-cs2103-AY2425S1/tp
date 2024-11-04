package seedu.address.logic.parser.deletecommands;

import static seedu.address.logic.Messages.MESSAGE_ILLEGAL_PREFIX_USED;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deletecommands.DeleteTaskForAllGroupsCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTaskForAllGroupCommand object.
 */
public class DeleteTaskForAllGroupsCommandParser implements Parser<DeleteTaskForAllGroupsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTaskForAllGroupCommand
     * and returns an DeleteTaskForAllGroupCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteTaskForAllGroupsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_INDEX);
        List<Prefix> allowedPrefix = new ArrayList<Prefix>(Arrays.asList(PREFIX_INDEX));
        List<Prefix> invalidPrefixes = ALL_PREFIX;
        invalidPrefixes.removeAll(allowedPrefix);
        if (containsInvalidPrefix(args, invalidPrefixes)) {
            throw new ParseException(MESSAGE_ILLEGAL_PREFIX_USED + "\n" + DeleteTaskForAllGroupsCommand.MESSAGE_USAGE);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTaskForAllGroupsCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX);
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        return new DeleteTaskForAllGroupsCommand(index);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private boolean containsInvalidPrefix(String arg, List<Prefix> invalidPrefixes) {
        return invalidPrefixes.stream().anyMatch(prefix -> arg.contains(prefix.getPrefix()));
    }
}
