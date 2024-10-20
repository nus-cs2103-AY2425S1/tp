package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;

import seedu.address.logic.commands.CreateAttendanceEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CreateAttendanceEventCommand object
 */
public class CreateAttendanceEventCommandParser implements Parser<CreateAttendanceEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateAttendanceEventCommand
     * and returns a CreateAttendanceEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CreateAttendanceEventCommand parse(String userInput) throws ParseException {
        //        String eventName = userInput.trim();
        //        if (eventName.isEmpty()) {
        //            throw new ParseException("Event name cannot be empty.");
        //        }
        //        return new CreateAttendanceEventCommand(eventName);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_EVENT);

        if (!argMultimap.getValue(PREFIX_EVENT).isPresent() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CreateAttendanceEventCommand.MESSAGE_USAGE));
        }

        String eventName = argMultimap.getValue(PREFIX_EVENT).get().trim();

        if (eventName.isEmpty()) {
            throw new ParseException("Event name cannot be empty.");
        }

        return new CreateAttendanceEventCommand(eventName);
    }
}
