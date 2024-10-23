package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUMBER;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.IdentityNumber;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        // Use the identity number prefix to tokenize the arguments
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_IDENTITY_NUMBER);

        if (!argMultimap.getValue(PREFIX_IDENTITY_NUMBER).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        // Use IdentityNumber class to parse the identity number
        IdentityNumber identityNumber = ParserUtil.parseIdentityNumber(
                argMultimap.getValue(PREFIX_IDENTITY_NUMBER).get());

        return new DeleteCommand(identityNumber);
    }
}
