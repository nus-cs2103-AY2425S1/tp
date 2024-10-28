package seedu.address.logic.parser.reminder;

import seedu.address.logic.commands.reminder.AddReminderCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a new {@code AddReminderCommand}.
 */
public class AddReminderCommandParser implements Parser<AddReminderCommand> {
    /**
     * Constructs an {@code AddReminderCommandParser}.
     */
    public AddReminderCommandParser() {
    }

    /**
     * Parses the given {@code String} of user input to create a new {@code AddReminderCommand}.
     *
     * @param userInput The user input to parse.
     * @return An {@code AddReminderCommand} based on the parsed input.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public AddReminderCommand parse(String userInput) throws ParseException {
        return null;
    }

}
