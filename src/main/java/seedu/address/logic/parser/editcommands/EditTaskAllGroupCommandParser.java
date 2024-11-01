package seedu.address.logic.parser.editcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_ILLEGAL_PREFIX_USED;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_STATUS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.editcommands.EditTaskAllGroupCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTaskCommand object.
 */
public class EditTaskAllGroupCommandParser implements Parser<EditTaskAllGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskAllGroupCommand
     * and returns an EditTaskAllGroupCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditTaskAllGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        List<Prefix> allowedPrefix = new ArrayList<Prefix>(Arrays.asList(PREFIX_INDEX,
            PREFIX_TASK_NAME, PREFIX_TASK_DEADLINE, PREFIX_TASK_STATUS));
        List<Prefix> invalidPrefixes = ALL_PREFIX;
        invalidPrefixes.removeAll(allowedPrefix);
        if (containsInvalidPrefix(args, invalidPrefixes)) {
            throw new ParseException(MESSAGE_ILLEGAL_PREFIX_USED + "\n" + EditTaskAllGroupCommand.MESSAGE_USAGE);
        }
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_TASK_NAME,
                PREFIX_TASK_DEADLINE, PREFIX_TASK_STATUS);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditTaskAllGroupCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX, PREFIX_TASK_NAME, PREFIX_TASK_DEADLINE);

        EditTaskAllGroupCommand.EditTaskDescriptor editTaskDescriptor =
            new EditTaskAllGroupCommand.EditTaskDescriptor();

        if (argMultimap.getValue(PREFIX_TASK_NAME).isPresent()) {
            editTaskDescriptor.setTaskName(ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_TASK_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_TASK_DEADLINE).isPresent()) {
            editTaskDescriptor.setDeadline(ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_TASK_DEADLINE).get()));
        }
        if (argMultimap.getValue(PREFIX_TASK_STATUS).isPresent()) {
            throw new ParseException(EditTaskAllGroupCommand.MESSAGE_INVALID_FILED_STATUS);
        }
        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskAllGroupCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskAllGroupCommand(index, editTaskDescriptor);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
    private boolean containsInvalidPrefix(String arg, List<Prefix> invalidPreFixes) {
        for (Prefix prefix : invalidPreFixes) {
            if (arg.contains(prefix.getPrefix())) {
                return true;
            }
        }
        return false;
    }
}

