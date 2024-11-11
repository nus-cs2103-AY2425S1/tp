package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_REMINDER_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;

import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new {@code ReminderCommand} object
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code ReminderCommand}
     * and return a new {@code ReminderCommand} object for execution.
     *
     * @param args The user input arguments to be parsed.
     * @return A {@code ReminderCommand} created from the parsed input.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public ReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        // Show usage if no arguments are provided or if only whitespace is provided
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ReminderCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMINDER);
        Name name = ParserUtil.parseName(argMultimap.getPreamble());

        //Check for reminder prefix
        if (!argMultimap.getValue(PREFIX_REMINDER).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ReminderCommand.MESSAGE_USAGE));
        }

        // Check for multiple prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_REMINDER);

        String reminderTime = argMultimap.getValue(PREFIX_REMINDER).orElse("");

        // Check for missing reminder
        if (reminderTime.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_REMINDER_FORMAT);
        }

        return new ReminderCommand(name, reminderTime);
    }
}
