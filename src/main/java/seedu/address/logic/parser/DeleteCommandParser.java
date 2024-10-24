package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_TO_EDIT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteCommand.DeleteCommandDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMERGENCY_CONTACT_TO_EDIT);

        Index personIndex;

        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EMERGENCY_CONTACT_TO_EDIT);
        DeleteCommandDescriptor deleteCommandDescriptor = new DeleteCommandDescriptor();

        if (argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_TO_EDIT).isPresent()) {
            deleteCommandDescriptor.setEmergencyContactIndex(
                    ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_TO_EDIT).get()));
        }

        return new DeleteCommand(personIndex, deleteCommandDescriptor);
    }

}
