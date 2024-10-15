package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_PHONE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EmergencyPhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmergencyPhone;

/**
 * Parses input arguments and creates a new {@code AddEmergencyContactNumberCommand} object
 */
public class EmergencyPhoneCommandParser implements Parser<EmergencyPhoneCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddEmergencyContactNumberCommand}
     * and returns a {@code AddEmergencyContactCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EmergencyPhoneCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_EMERGENCY_PHONE);
        if (!argMultiMap.getValue(PREFIX_EMERGENCY_PHONE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EmergencyPhoneCommand.MESSAGE_USAGE));
        }

        Index index;
        EmergencyPhone emergencyPhone;

        try {
            index = ParserUtil.parseIndex(argMultiMap.getPreamble());
            emergencyPhone = ParserUtil.parseEmergencyPhone(argMultiMap.getValue(PREFIX_EMERGENCY_PHONE).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EmergencyPhoneCommand.MESSAGE_USAGE), ive);
        }

        return new EmergencyPhoneCommand(index, emergencyPhone);
    }
}
