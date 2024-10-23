package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_PREFIX_FIELD;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_PARAMETER_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NICKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM_HANDLE;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContainsKeywordsPredicate;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TELEGRAM_HANDLE, PREFIX_EMAIL,
                        PREFIX_STUDENT_STATUS, PREFIX_ROLE, PREFIX_NICKNAME);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TELEGRAM_HANDLE,
                PREFIX_EMAIL, PREFIX_STUDENT_STATUS, PREFIX_ROLE, PREFIX_NICKNAME);

        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_NO_PARAMETER_FOUND, FindCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywords = List.of();
        List<String> telegramKeywords = List.of();
        List<String> emailKeywords = List.of();
        List<String> studentStatusKeywords = List.of();
        List<String> roleKeywords = List.of();
        List<String> nicknameKeywords = List.of();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String arg = argMultimap.getValue(PREFIX_NAME).get();
            nameKeywords = getKeywords(arg);
        }
        if (argMultimap.getValue(PREFIX_TELEGRAM_HANDLE).isPresent()) {
            String arg = argMultimap.getValue(PREFIX_TELEGRAM_HANDLE).get();
            telegramKeywords = getKeywords(arg);
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String arg = argMultimap.getValue(PREFIX_EMAIL).get();
            emailKeywords = getKeywords(arg);
        }
        if (argMultimap.getValue(PREFIX_STUDENT_STATUS).isPresent()) {
            String arg = argMultimap.getValue(PREFIX_STUDENT_STATUS).get();
            studentStatusKeywords = getKeywords(arg);
        }
        if (argMultimap.getValue(PREFIX_NICKNAME).isPresent()) {
            String arg = argMultimap.getValue(PREFIX_NICKNAME).get();
            nicknameKeywords = getKeywords(arg);
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            String arg = argMultimap.getValue(PREFIX_ROLE).get();
            roleKeywords = getKeywords(arg);
        }

        ContainsKeywordsPredicate containsKeywordsPredicate =
                new ContainsKeywordsPredicate(nameKeywords, telegramKeywords, emailKeywords, studentStatusKeywords,
                        roleKeywords, nicknameKeywords);

        return new FindCommand(containsKeywordsPredicate);
    }

    /**
     * Get a string array of keywords from the argument string
     *
     */
    private List<String> getKeywords(String arg) throws ParseException {
        String trimmedArg = arg.trim();

        if (trimmedArg.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_EMPTY_PREFIX_FIELD, FindCommand.MESSAGE_USAGE));
        }
        String[] keywords = trimmedArg.split("\\s+");
        return Arrays.asList(keywords);
    }
}

