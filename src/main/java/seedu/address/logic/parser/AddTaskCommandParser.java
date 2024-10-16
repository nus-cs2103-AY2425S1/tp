package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.task.Task;

/**
 * Parses input arguments and creates a new AddTaskCommand object.
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TASK_DESCRIPTION, PREFIX_TASK_DEADLINE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TASK_DESCRIPTION, PREFIX_TASK_DEADLINE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TASK_DESCRIPTION, PREFIX_TASK_DEADLINE);

        Name personName;

        try {
            personName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_NAME, pe);
        }

        Task task = ParserUtil.parseTask(argMultimap.getValue(PREFIX_TASK_DESCRIPTION).get(),
                argMultimap.getValue(PREFIX_TASK_DEADLINE).get());

        return new AddTaskCommand(personName, task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
