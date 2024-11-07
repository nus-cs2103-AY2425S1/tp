package seedu.address.logic.parser.task;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.ParserUtil.parseTask;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.task.CreateTaskCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Task;

/**
 * Parses input arguments and creates a new CreateTaskCommand object
 */
public class CreateTaskCommandParser implements Parser<CreateTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateTaskCommand
     * and returns an CreateTaskCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public CreateTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateTaskCommand.MESSAGE_USAGE));
        }

        List<String> taskInputStrings = argMultimap.getAllValues(PREFIX_TASK);
        if (taskInputStrings.isEmpty() || taskInputStrings.stream().anyMatch(String::isBlank)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateTaskCommand.MESSAGE_USAGE));
        }

        Set<Task> tasks = new HashSet<>();
        for (String taskInputString : taskInputStrings) {
            tasks.add(parseTask(taskInputString));
        }

        return new CreateTaskCommand(new HashSet<>(tasks));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
