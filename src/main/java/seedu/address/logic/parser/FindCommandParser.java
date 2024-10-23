package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.person.TelegramContainsKeywordsPredicate;

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
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE, PREFIX_TELEGRAM);

        if (!areSomePrefixesPresent(argumentMultimap, PREFIX_NAME, PREFIX_ROLE, PREFIX_TELEGRAM)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        List<String> nameKeywords = argumentMultimap.getAllValues(PREFIX_NAME);
        List<String> roleKeywords = argumentMultimap.getAllValues(PREFIX_ROLE);
        List<String> telegramKeywords = argumentMultimap.getAllValues(PREFIX_TELEGRAM);

        boolean hasEmptyInput = nameKeywords.stream().anyMatch(str -> str.trim().isEmpty())
                || roleKeywords.stream().anyMatch(str -> str.trim().isEmpty())
                || telegramKeywords.stream().anyMatch(str -> str.trim().isEmpty());

        if (hasEmptyInput) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        nameKeywords = nameKeywords.stream().map(String::trim).toList();
        roleKeywords = roleKeywords.stream().map(String::trim).toList();
        telegramKeywords = telegramKeywords.stream().map(String::trim).toList();

        return new FindCommand(new NameContainsKeywordsPredicate(nameKeywords),
                new RoleContainsKeywordsPredicate(roleKeywords),
                new TelegramContainsKeywordsPredicate(telegramKeywords));
    }

    /**
     * Returns true if any of the prefixes contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean areSomePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }




}
