package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNUMBER;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddEcNumberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EcNumber;

/**
 * Parses input arguments and creates a new EmergencyPhoneCommand object
 */
public class AddEcNumberCommandParser implements Parser<AddEcNumberCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EmergencyPhoneCommand
     * and returns an EmergencyPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddEcNumberCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_ECNUMBER);

        if (!argMultiMap.getValue(PREFIX_ECNUMBER).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEcNumberCommand.MESSAGE_USAGE));
        }

        Index index;
        EcNumber ecNumber;

        try {
            index = ParserUtil.parseIndex(argMultiMap.getPreamble());
            ecNumber = ParserUtil.parseEcNumber(argMultiMap.getValue(PREFIX_ECNUMBER).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEcNumberCommand.MESSAGE_USAGE), ive);
        }

        return new AddEcNumberCommand(index, ecNumber);
    }
}
