package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tuteez.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;
import static tuteez.logic.parser.CliSyntax.PREFIX_NAME;
import static tuteez.logic.parser.CliSyntax.PREFIX_PHONE;
import static tuteez.logic.parser.CliSyntax.PREFIX_TAG;
import static tuteez.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import tuteez.logic.commands.FindCommand;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.person.predicates.AddressContainsKeywordsPredicate;
import tuteez.model.person.predicates.EmailContainsKeywordsPredicate;
import tuteez.model.person.predicates.LessonContainsKeywordsPredicate;
import tuteez.model.person.predicates.NameContainsKeywordsPredicate;
import tuteez.model.person.predicates.PhoneContainsKeywordsPredicate;
import tuteez.model.person.predicates.TagContainsKeywordsPredicate;
import tuteez.model.person.predicates.TelegramUsernameContainsKeywordsPredicate;

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

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TELEGRAM, PREFIX_TAG, PREFIX_LESSON);

        NameContainsKeywordsPredicate namePredicate =
                parseWithPredicate(argMultimap, PREFIX_NAME, NameContainsKeywordsPredicate::new);
        PhoneContainsKeywordsPredicate phonePredicate =
                parseWithPredicate(argMultimap, PREFIX_PHONE, PhoneContainsKeywordsPredicate::new);
        EmailContainsKeywordsPredicate emailPredicate =
                parseWithPredicate(argMultimap, PREFIX_EMAIL, EmailContainsKeywordsPredicate::new);
        AddressContainsKeywordsPredicate addressPredicate =
                parseWithPredicate(argMultimap, PREFIX_ADDRESS, AddressContainsKeywordsPredicate::new);
        TelegramUsernameContainsKeywordsPredicate telegramUsernamePredicate =
                parseWithPredicate(argMultimap, PREFIX_TELEGRAM, TelegramUsernameContainsKeywordsPredicate::new);
        TagContainsKeywordsPredicate tagPredicate =
                parseWithPredicate(argMultimap, PREFIX_TAG, TagContainsKeywordsPredicate::new);
        LessonContainsKeywordsPredicate lessonPredicate =
                parseWithPredicate(argMultimap, PREFIX_LESSON, LessonContainsKeywordsPredicate::new);

        return new FindCommand(namePredicate, phonePredicate, emailPredicate, addressPredicate,
                telegramUsernamePredicate, tagPredicate, lessonPredicate);
    }

    private <T> T parseWithPredicate(
            ArgumentMultimap argMultimap, Prefix prefix, Function<List<String>, T> predicateConstructor) {
        return argMultimap.getValue(prefix)
                .map(value -> predicateConstructor.apply(Arrays.asList(value.split("\\s+"))))
                .orElseGet(() -> predicateConstructor.apply(Collections.emptyList()));
    }
}
