//package seedu.address.logic.parser.reminder;
//
//import seedu.address.logic.commands.reminder.AddReminderCommand;
//import seedu.address.logic.parser.Parser;
//import seedu.address.logic.parser.exceptions.ParseException;
//
///**
// * Parses user input to create a new {@code AddReminderCommand}.
// */
//public class AddReminderCommandParser implements Parser<AddReminderCommand> {
//    /**
//     * Constructs an {@code AddReminderCommandParser}.
//     */
//    public AddReminderCommandParser() {
//    }
//
//    /**
//     * Parses the given {@code String} of user input to create a new {@code AddReminderCommand}.
//     *
//     * @param userInput The user input to parse.
//     * @return An {@code AddReminderCommand} based on the parsed input.
//     * @throws ParseException If the user input does not conform to the expected format.
//     */
//    @Override
//    public AddReminderCommand parse(String userInput) throws ParseException {
//        return null;
//    }
//
//}

package seedu.address.logic.parser.reminder;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.reminder.AddReminderCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDescription;

/**
 * Parses input arguments and creates a new AddReminderCommand object.
 */
public class AddReminderCommandParser implements Parser<AddReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddReminderCommand
     * and returns an AddReminderCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public AddReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE_TIME, PREFIX_DESCRIPTION);

        // To check if fields are input correctly
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATE_TIME, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
        }

        // Verify that each prefix is only used once
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATE_TIME, PREFIX_DESCRIPTION);

        // Parsing each field using the updated ParserUtil methods
        String person = argMultimap.getValue(PREFIX_NAME).get();
        LocalDateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE_TIME).get());
        ReminderDescription description = ParserUtil.parseReminderDescription(argMultimap
                .getValue(PREFIX_DESCRIPTION).get());

        // Creating a Reminder object with the parsed data
        Reminder reminder = new Reminder(person, dateTime, description);

        return new AddReminderCommand(reminder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}


