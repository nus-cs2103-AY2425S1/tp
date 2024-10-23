package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.IsFavouritePredicate;
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
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE,
                PREFIX_TELEGRAM, PREFIX_FAVOURITE);

        if (!areSomePrefixesPresent(argumentMultimap, PREFIX_NAME, PREFIX_ROLE, PREFIX_TELEGRAM, PREFIX_FAVOURITE)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywords = argumentMultimap.getAllValues(PREFIX_NAME);
        List<String> roleKeywords = argumentMultimap.getAllValues(PREFIX_ROLE);
        List<String> telegramKeywords = argumentMultimap.getAllValues(PREFIX_TELEGRAM);

        boolean hasAnyEmptyInput = hasEmptyInput(nameKeywords)
                || hasEmptyInput(roleKeywords)
                || hasEmptyInput(telegramKeywords);

        if (hasAnyEmptyInput) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        nameKeywords = nameKeywords.stream().map(String::trim).toList();
        roleKeywords = roleKeywords.stream().map(String::trim).toList();
        telegramKeywords = telegramKeywords.stream().map(String::trim).toList();
        Optional<FavouriteStatus> favouriteStatusPredicate = argumentMultimap.getAllValues(PREFIX_FAVOURITE).isEmpty()
                ? Optional.ofNullable(null)
                : Optional.of(FavouriteStatus.FAVOURITE);

        return new FindCommand(new NameContainsKeywordsPredicate(nameKeywords),
                new RoleContainsKeywordsPredicate(roleKeywords),
                new TelegramContainsKeywordsPredicate(telegramKeywords),
                new IsFavouritePredicate(favouriteStatusPredicate));
    }

    /**
     * Returns true if any of the prefixes contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean areSomePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if any of the element in the list contains empty {@code String} values in the given
     * {@code List<String>}
     */
    public static boolean hasEmptyInput(List<String> list) {
        return list.stream().map(String::trim).anyMatch(str -> str.trim().isEmpty());
    }

}
