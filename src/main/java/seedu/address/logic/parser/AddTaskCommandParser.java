package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AddTaskCommand object.
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_DESCRIPTION, PREFIX_TASK_INDEX);

        if (!argMultimap.getValue(PREFIX_TASK_DESCRIPTION).isPresent()
                || !argMultimap.getValue(PREFIX_TASK_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        String taskDescription = argMultimap.getValue(PREFIX_TASK_DESCRIPTION).get();
        Index target = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK_INDEX).get());

        return new AddTaskCommand(target, taskDescription);
    }
}
