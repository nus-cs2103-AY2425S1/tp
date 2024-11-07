package seedu.address.logic.parser.addcommands;

import static seedu.address.logic.Messages.MESSAGE_ILLEGAL_PREFIX_USED;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.addcommands.AddTaskToGroupCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.TaskName;

/**
 * Parses input arguments and creates a new AddStudentToGroupCommand object.
 */
public class AddTaskToGroupCommandParser implements Parser<AddTaskToGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddTaskToGroupCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddTaskToGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME, PREFIX_TASK_DEADLINE, PREFIX_GROUP_NAME);
        List<Prefix> allowedPrefix = new ArrayList<Prefix>(Arrays.asList(
            PREFIX_TASK_NAME, PREFIX_TASK_DEADLINE, PREFIX_GROUP_NAME));
        List<Prefix> invalidPrefixes = ALL_PREFIX;
        invalidPrefixes.removeAll(allowedPrefix);
        if (containsInvalidPrefix(args, invalidPrefixes)) {
            throw new ParseException(MESSAGE_ILLEGAL_PREFIX_USED + "\n" + AddTaskToGroupCommand.MESSAGE_USAGE);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_NAME, PREFIX_TASK_DEADLINE, PREFIX_GROUP_NAME)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTaskToGroupCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TASK_NAME, PREFIX_TASK_DEADLINE);
        TaskName taskName = ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_TASK_NAME).get());
        Deadline deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_TASK_DEADLINE).get());
        List<GroupName> groupNameList = new ArrayList<>();
        for (String s : argMultimap.getAllValues(PREFIX_GROUP_NAME)) {
            groupNameList.add(ParserUtil.parseGroupName(s));
        }
        return new AddTaskToGroupCommand(taskName, deadline, groupNameList);
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
