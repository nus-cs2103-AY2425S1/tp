package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.ArrayList;

import seedu.address.logic.commands.findcommand.FindAddressCommand;
import seedu.address.logic.commands.findcommand.FindCommand;
import seedu.address.logic.commands.findcommand.FindEmailCommand;
import seedu.address.logic.commands.findcommand.FindNameCommand;
import seedu.address.logic.commands.findcommand.FindPhoneCommand;
import seedu.address.logic.commands.findcommand.FindTagCommand;
import seedu.address.logic.commands.findcommand.FindTaskCommand;
import seedu.address.logic.commands.findcommand.FindWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.keywordspredicate.AddressContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.EmailContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.TagContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.TaskContainsKeywordsPredicate;
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
    public static final String TASK_CANNOT_BE_EMPTY = "Task cannot be empty!";
    public static final String PARSE_FAILURE_MULTIPLE_PREFIXES = "You can only specify one prefix at a time.";



    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_WEDDING, PREFIX_TASK);

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
        boolean hasTaskPrefix = argMultimap.getValue(PREFIX_TASK).isPresent();

        int prefixCount = 0;
        if (hasNamePrefix) {
            prefixCount++;
        }
        if (hasPhonePrefix) {
            prefixCount++;
        }
        if (hasEmailPrefix) {
            prefixCount++;
        }
        if (hasAddressPrefix) {
            prefixCount++;
        }
        if (hasTagPrefix) {
            prefixCount++;
        }
        if (hasWeddingPrefix) {
            prefixCount++;
        }
        if (hasTaskPrefix) {
            prefixCount++;
        }

        if (prefixCount > 1) {
            throw new ParseException(PARSE_FAILURE_MULTIPLE_PREFIXES);
        }

        if (hasNamePrefix) {
            ArrayList<String> nameKeywords = new ArrayList<>();
            collectKeywords(argMultimap, PREFIX_NAME, NAME_CANNOT_BE_EMPTY, nameKeywords);
            return new FindNameCommand(new NameContainsKeywordsPredicate(nameKeywords));
        }

        if (hasPhonePrefix) {
            ArrayList<String> phoneKeywords = new ArrayList<>();
            collectKeywords(argMultimap, PREFIX_PHONE, PHONE_NUMBER_CANNOT_BE_EMPTY, phoneKeywords);
            return new FindPhoneCommand(new PhoneContainsKeywordsPredicate(phoneKeywords));
        }

        if (hasEmailPrefix) {
            ArrayList<String> emailKeywords = new ArrayList<>();
            collectKeywords(argMultimap, PREFIX_EMAIL, EMAIL_CANNOT_BE_EMPTY, emailKeywords);
            return new FindEmailCommand(new EmailContainsKeywordsPredicate(emailKeywords));
        }

        if (hasAddressPrefix) {
            ArrayList<String> addressKeywords = new ArrayList<>();
            collectKeywords(argMultimap, PREFIX_ADDRESS, ADDRESS_CANNOT_BE_EMPTY, addressKeywords);
            return new FindAddressCommand(new AddressContainsKeywordsPredicate(addressKeywords));
        }

        if (hasTagPrefix) {
            ArrayList<String> tagKeywords = new ArrayList<>();
            collectKeywords(argMultimap, PREFIX_TAG, TAG_CANNOT_BE_EMPTY, tagKeywords);
            return new FindTagCommand(new TagContainsKeywordsPredicate(tagKeywords));
        }

        if (hasWeddingPrefix) {
            ArrayList<String> weddingKeywords = new ArrayList<>();
            collectKeywords(argMultimap, PREFIX_WEDDING, WEDDING_CANNOT_BE_EMPTY, weddingKeywords);
            return new FindWeddingCommand(new WeddingContainsKeywordsPredicate(weddingKeywords));
        }

        if (hasTaskPrefix) {
            ArrayList<String> taskKeywords = new ArrayList<>();
            for (String task : argMultimap.getAllValues(PREFIX_TASK)) {
                String taskInput = task.trim();
                if (taskInput.isEmpty()) {
                    throw new ParseException(TASK_CANNOT_BE_EMPTY);
                }
                taskKeywords.add(taskInput);
            }
            return new FindTaskCommand(new TaskContainsKeywordsPredicate(taskKeywords));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    private void collectKeywords(ArgumentMultimap argMultimap, Prefix prefixTag, String keywordCannotBeEmpty,
                                 ArrayList<String> keywords) throws ParseException {
        for (String field : argMultimap.getAllValues(prefixTag)) {
            String prefixTagInput = field.trim();
            if (prefixTagInput.isEmpty()) {
                throw new ParseException(keywordCannotBeEmpty);
            }
            keywords.add(prefixTagInput);
        }
    }

}

