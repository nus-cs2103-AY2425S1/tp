package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.DeleteBuyerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteBuyerCommand} object.
 */
public class DeleteBuyerCommandParser implements Parser<DeleteBuyerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteBuyerCommand}
     * and returns a {@code DeleteBuyerCommand} object for execution.
     *
     * @param args The input arguments to parse.
     * @return A {@code DeleteBuyerCommand} object based on the parsed arguments.
     * @throws ParseException If the user input does not conform to the expected format or the phone number is invalid.
     */
    public DeleteBuyerCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHONE);

        String phoneNumber = argMultimap.getValue(PREFIX_PHONE).orElse("");
        if (phoneNumber.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBuyerCommand.MESSAGE_USAGE));
        }
        try {
            return new DeleteBuyerCommand(phoneNumber);
        } catch (IllegalArgumentException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBuyerCommand.MESSAGE_USAGE), e);
        }
    }
}
