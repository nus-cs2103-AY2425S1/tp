package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.CreateTaskCommand;
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

        List<String> taskDescriptions = argMultimap.getAllValues(PREFIX_TASK);
        if (taskDescriptions.isEmpty() || taskDescriptions.stream().anyMatch(String::isBlank)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateTaskCommand.MESSAGE_USAGE));
        }

        // Parse the tasks from the task descriptions
        Set<Task> tasks = ParserUtil.parseTasks(taskDescriptions);

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
