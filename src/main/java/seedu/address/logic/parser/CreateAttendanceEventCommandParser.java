package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_EVENT);

        List<String> eventNames = argMultimap.getAllValues(PREFIX_EVENT).stream()
                .map(String::trim)
                .collect(Collectors.toList());

        if (eventNames.isEmpty() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CreateAttendanceEventCommand.MESSAGE_USAGE));
        }

        for (String eventName : eventNames) {
            if (eventName.isEmpty()) {
                throw new ParseException("Event name cannot be empty.");
            }
            if (eventName.contains("/")) {
                throw new ParseException("Event name cannot contain '/'.");
            }
        }

        // Check for duplicate event names (case-insensitive)
        Set<String> uniqueEventNames = new HashSet<>();
        for (String eventName : eventNames) {
            String eventNameLower = eventName.toLowerCase();
            if (!uniqueEventNames.add(eventNameLower)) {
                throw new ParseException("Duplicate event names detected.");
            }
        }

        return new CreateAttendanceEventCommand(eventNames);
    }
}
