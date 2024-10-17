package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindAddressCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEmailCommand;
import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;


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
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Check for the presence of any prefix
        boolean hasNamePrefix = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean hasPhonePrefix = argMultimap.getValue(PREFIX_PHONE).isPresent();
        boolean hasEmailPrefix = argMultimap.getValue(PREFIX_EMAIL).isPresent();
        boolean hasAddressPrefix = argMultimap.getValue(PREFIX_ADDRESS).isPresent();

        if (hasNamePrefix) {
            String nameInput = argMultimap.getValue(PREFIX_NAME).get().trim(); // Get the actual name input
            if (nameInput.isEmpty()) {
                throw new ParseException("Name cannot be empty!");
            }
            List<String> nameKeywords = Arrays.asList(nameInput.split("\\s+"));
            return new FindNameCommand(new NameContainsKeywordsPredicate(nameKeywords));
        }

        if (hasPhonePrefix) {
            String phoneNumberInput = argMultimap.getValue(PREFIX_PHONE).get().trim(); // Get the actual phone input
            if (phoneNumberInput.isEmpty()) {
                throw new ParseException("Phone number cannot be empty!");
            }
            List<String> phoneKeywords = Arrays.asList(phoneNumberInput.split("\\s+"));
            return new FindPhoneCommand(new PhoneContainsKeywordsPredicate(phoneKeywords));
        }

        if (hasEmailPrefix) {
            String emailInput = argMultimap.getValue(PREFIX_EMAIL).get().trim(); // Get the actual email input
            if (emailInput.isEmpty()) {
                throw new ParseException("Email address cannot be empty!");
            }
            List<String> emailKeywords = Arrays.asList(emailInput.split("\\s+"));
            return new FindEmailCommand(new EmailContainsKeywordsPredicate(emailKeywords));
        }

        if (hasAddressPrefix) {
            String addressInput = argMultimap.getValue(PREFIX_ADDRESS).get().trim(); // Get the actual address input
            if (addressInput.isEmpty()) {
                throw new ParseException("Address cannot be empty!");
            }
            List<String> addressKeywords = Arrays.asList(addressInput.split("\\s+"));
            return new FindAddressCommand(new AddressContainsKeywordsPredicate(addressKeywords));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}

