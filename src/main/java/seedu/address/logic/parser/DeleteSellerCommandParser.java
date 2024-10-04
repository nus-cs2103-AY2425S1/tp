package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.DeleteSellerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteSellerCommand} object.
 */
public class DeleteSellerCommandParser implements Parser<DeleteSellerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteSellerCommand}
     * and returns a {@code DeleteSellerCommand} object for execution.
     *
     * @param args The input arguments to parse.
     * @return A {@code DeleteSellerCommand} object based on the parsed arguments.
     * @throws ParseException If the user input does not conform to the expected format or the phone number is invalid.
     */
    public DeleteSellerCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHONE);

        String phoneNumber = argMultimap.getValue(PREFIX_PHONE).orElse("");
        if (phoneNumber.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSellerCommand.MESSAGE_USAGE));
        }
        try {
            return new DeleteSellerCommand(phoneNumber);
        } catch (IllegalArgumentException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSellerCommand.MESSAGE_USAGE), e);
        }
    }
}
