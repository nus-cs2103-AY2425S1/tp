package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.JobCodeContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NameEmailPredicate;
import seedu.address.model.person.NamePhonePredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_TAG, PREFIX_JOBCODE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent() && argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            // Name and Phone search
            String name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).fullName;
            String phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()).value;
            return new FindCommand(new NamePhonePredicate(name, phone));
        } else if (argMultimap.getValue(PREFIX_NAME).isPresent() && argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            // Name and Email search
            String name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).fullName;
            String email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()).value;
            return new FindCommand(new NameEmailPredicate(name, email));
        } else if (argMultimap.getValue(PREFIX_JOBCODE).isPresent()) {
            // Job Code search
            String jobCode = argMultimap.getValue(PREFIX_JOBCODE).get();
            return new FindCommand(new JobCodeContainsKeywordsPredicate(Arrays.asList(jobCode)));
        } else if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            // Tag search
            String tag = argMultimap.getValue(PREFIX_TAG).get();
            return new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(tag)));
        } else if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            // Name search
            String name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).fullName;
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(name)));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

}
