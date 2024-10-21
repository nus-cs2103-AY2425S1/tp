package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Optional;

import seedu.address.logic.commands.DeleteCommand;
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
        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

            if (!areAnyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            Optional<String> name = argMultimap.getValue(PREFIX_NAME);
            Optional<String> phone = argMultimap.getValue(PREFIX_PHONE);
            Optional<String> email = argMultimap.getValue(PREFIX_EMAIL);

            // Validate the phone number format if present
            if (phone.isPresent() && !isValidPhone(phone.get())) {
                throw new ParseException("ERROR: Invalid phone number format. Enter a valid 8 digit phone number.");
            }

            // Validate the email format if present
            if (email.isPresent() && !isValidEmail(email.get())) {
                throw new ParseException("ERROR: Invalid email format. Please provide a valid email address.");
            }

            return new DeleteCommand(name, phone, email);
        } catch (ParseException pe) {
            throw new ParseException(
                    pe.getMessage(), pe);
        }
    }

    /**
     * Returns true if at least one of the specified prefixes is present in the
     * given ArgumentMultimap.
     */
    private boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        for (Prefix prefix : prefixes) {
            if (argumentMultimap.getValue(prefix).isPresent()) {
                return true;
            }
        }
        return false;
    }

    /**
     * A method to validate phone number format.
     */
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{8}"); // Validates an 8-digit phone number
    }

    /**
     * A method to validate email format.
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,3}$"); // Basic email validation
    }

}
