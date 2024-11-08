package seedu.address.logic.parser.deletecommands;

import static seedu.address.logic.Messages.MESSAGE_ILLEGAL_PREFIX_USED;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.deletecommands.DeleteGroupCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;
//@@author gho7sie
/**
 * Parses input arguments and creates a new DeleteGroupCommand object
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {
    @Override
    public DeleteGroupCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_GROUP_NAME);
        List<Prefix> allowedPrefix = new ArrayList<Prefix>(Arrays.asList(PREFIX_GROUP_NAME));
        List<Prefix> invalidPrefixes = ALL_PREFIX;
        invalidPrefixes.removeAll(allowedPrefix);
        if (containsInvalidPrefix(userInput, invalidPrefixes)) {
            throw new ParseException(MESSAGE_ILLEGAL_PREFIX_USED + "\n" + DeleteGroupCommand.MESSAGE_USAGE);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_GROUP_NAME);
        GroupName groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUP_NAME).get());
        return new DeleteGroupCommand(groupName);
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
