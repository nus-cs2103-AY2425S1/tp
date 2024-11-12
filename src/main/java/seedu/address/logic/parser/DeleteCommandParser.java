package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUMBER;

import seedu.address.commons.core.index.Index;
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

        boolean hasIdentityNumber = argMultimap.getValue(PREFIX_IDENTITY_NUMBER).isPresent();
        boolean hasIndex = !argMultimap.getPreamble().isEmpty();

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_IDENTITY_NUMBER);

        if (hasIndex && hasIdentityNumber) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_INDEX_AND_IDENTITY_NUMBER));
        }

        if (!hasIndex && !hasIdentityNumber) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        if (hasIdentityNumber) {
            IdentityNumber identityNumber = ParserUtil.parseIdentityNumber(
                    argMultimap.getValue(PREFIX_IDENTITY_NUMBER).get());
            return new DeleteCommand(identityNumber);
        }

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            return new DeleteCommand(index);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), e);
        }
    }
}
