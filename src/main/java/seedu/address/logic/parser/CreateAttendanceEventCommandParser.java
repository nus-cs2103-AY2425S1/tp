package seedu.address.logic.parser;

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
        String eventName = userInput.trim();
        if (eventName.isEmpty()) {
            throw new ParseException("Event name cannot be empty.");
        }
        return new CreateAttendanceEventCommand(eventName);
    }
}
