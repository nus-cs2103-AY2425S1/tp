package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ROLE, PREFIX_MAJOR, PREFIX_ADDRESS, PREFIX_TAG);

        // Check for preamble or other arguments
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ROLE, PREFIX_MAJOR, PREFIX_ADDRESS);

        String name = argMultimap.getValue(PREFIX_NAME).orElse(null);
        String phone = argMultimap.getValue(PREFIX_PHONE).orElse(null);
        String email = argMultimap.getValue(PREFIX_EMAIL).orElse(null);
        String role = argMultimap.getValue(PREFIX_ROLE).orElse(null);
        String major = argMultimap.getValue(PREFIX_MAJOR).orElse(null);
        String address = argMultimap.getValue(PREFIX_ADDRESS).orElse(null);
        List<String> tags = argMultimap.getAllValues(PREFIX_TAG);
        if (tags.isEmpty()) {
            tags = null;
        }

        // If all parameters were empty
        if (name == null && phone == null && email == null && role == null
                && major == null && address == null && (tags == null || tags.isEmpty())) {
            throw new ParseException(FindCommand.MESSAGE_INCOMPLETE);
        }

        // If any given parameter was empty
        if ((name != null && name.isEmpty()) || (phone != null && phone.isEmpty())
                || (email != null && email.isEmpty()) || (role != null && role.isEmpty())
                || (major != null && major.isEmpty()) || (address != null && address.isEmpty())
                || (tags != null && tags.isEmpty())) {
            throw new ParseException(FindCommand.MESSAGE_INCOMPLETE);
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(name, phone, email, role, major, address, tags));
    }

}
