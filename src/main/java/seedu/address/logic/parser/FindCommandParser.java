package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.findcommand.FindAddressCommand;
import seedu.address.logic.commands.findcommand.FindCommand;
import seedu.address.logic.commands.findcommand.FindEmailCommand;
import seedu.address.logic.commands.findcommand.FindNameCommand;
import seedu.address.logic.commands.findcommand.FindPhoneCommand;
import seedu.address.logic.commands.findcommand.FindTagCommand;
import seedu.address.logic.commands.findcommand.FindWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.keywordspredicate.AddressContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.EmailContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.TagContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.WeddingContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String NAME_CANNOT_BE_EMPTY = "Name cannot be empty!";
    public static final String PHONE_NUMBER_CANNOT_BE_EMPTY = "Phone number cannot be empty!";
    public static final String EMAIL_CANNOT_BE_EMPTY = "Email address cannot be empty!";
    public static final String ADDRESS_CANNOT_BE_EMPTY = "Address cannot be empty!";
    public static final String TAG_CANNOT_BE_EMPTY = "Tag cannot be empty!";
    public static final String WEDDING_CANNOT_BE_EMPTY = "Wedding cannot be empty!";


    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_WEDDING);

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
        boolean hasTagPrefix = argMultimap.getValue(PREFIX_TAG).isPresent();
        boolean hasWeddingPrefix = argMultimap.getValue(PREFIX_WEDDING).isPresent();

        if (hasNamePrefix) {
            List<String> nameKeywords = new ArrayList<>();
            for (String address : argMultimap.getAllValues(PREFIX_NAME)) {
                String nameInput = address.trim();
                if (nameInput.isEmpty()) {
                    throw new ParseException(NAME_CANNOT_BE_EMPTY);
                }
                nameKeywords.add(nameInput);
            }
            return new FindNameCommand(new NameContainsKeywordsPredicate(nameKeywords));
        }

        if (hasPhonePrefix) {
            List<String> phoneKeywords = new ArrayList<>();
            for (String phoneNumber : argMultimap.getAllValues(PREFIX_PHONE)) {
                String phoneNumberInput = phoneNumber.trim();
                if (phoneNumberInput.isEmpty()) {
                    throw new ParseException(PHONE_NUMBER_CANNOT_BE_EMPTY);
                }
                phoneKeywords.add(phoneNumberInput);
            }
            return new FindPhoneCommand(new PhoneContainsKeywordsPredicate(phoneKeywords));
        }

        if (hasEmailPrefix) {
            List<String> emailKeywords = new ArrayList<>();
            for (String email : argMultimap.getAllValues(PREFIX_EMAIL)) {
                String emailInput = email.trim();
                if (emailInput.isEmpty()) {
                    throw new ParseException(EMAIL_CANNOT_BE_EMPTY);
                }
                emailKeywords.add(emailInput);
            }
            return new FindEmailCommand(new EmailContainsKeywordsPredicate(emailKeywords));
        }

        if (hasAddressPrefix) {
            List<String> addressKeywords = new ArrayList<>();
            for (String address : argMultimap.getAllValues(PREFIX_ADDRESS)) {
                String addressInput = address.trim();
                if (addressInput.isEmpty()) {
                    throw new ParseException(ADDRESS_CANNOT_BE_EMPTY);
                }
                addressKeywords.add(addressInput);
            }
            return new FindAddressCommand(new AddressContainsKeywordsPredicate(addressKeywords));
        }

        if (hasTagPrefix) {
            String tagInput = argMultimap.getValue(PREFIX_TAG).get().trim();
            if (tagInput.isEmpty()) {
                throw new ParseException(TAG_CANNOT_BE_EMPTY);
            }
            List<String> tagKeywords = Arrays.asList(tagInput.split("\\s+"));
            return new FindTagCommand(new TagContainsKeywordsPredicate(tagKeywords));
        }

        if (hasWeddingPrefix) {
            String weddingInput = argMultimap.getValue(PREFIX_WEDDING).get().trim();
            if (weddingInput.isEmpty()) {
                throw new ParseException(WEDDING_CANNOT_BE_EMPTY);
            }
            List<String> weddingKeywords = Arrays.asList(weddingInput.split("\\s+"));
            return new FindWeddingCommand(new WeddingContainsKeywordsPredicate(weddingKeywords));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}

