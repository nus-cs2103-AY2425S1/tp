package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.EmailContainsKeywordsPredicate;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.model.client.PhoneContainsKeywordsPredicate;
import seedu.address.model.client.RentalInformationContainsKeywordsPredicate;
import seedu.address.model.tag.TagsContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final String SPECIAL_CHARACTERS = "+_.@-";
    private static final String VALIDATION_REGEX = "^[\\p{Alnum}" + SPECIAL_CHARACTERS + "]"
            + "[\\p{Alnum} " + SPECIAL_CHARACTERS + "]*$";

    /**
     * Message to be displayed when invalid keyword is given.
     */
    public static final String MESSAGE_INVALID_KEYWORD = "Keywords should only contain alphanumeric characters, "
            + "spaces and these special characters, excluding the parenthesis, (" + SPECIAL_CHARACTERS + ")."
            + "and it should not be blank";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        List<Prefix> prefixes = List.of(PREFIX_KEYWORD, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes.toArray(new Prefix[0]));

        if (noPrefixesPresent(argMultimap, prefixes.toArray(new Prefix[0]))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Declare lists for keywords and initialize all of them to an empty list.
        List<String> keywordList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        List<String> phoneList = new ArrayList<>();
        List<String> emailList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        List<List<String>> lists = List.of(keywordList, nameList, phoneList, emailList, tagList);

        checkValuesAndAdd(argMultimap, prefixes, lists);
        appendAll(lists.subList(1, lists.size()), keywordList);

        return new FindCommand(new NameContainsKeywordsPredicate(nameList),
                new PhoneContainsKeywordsPredicate(phoneList),
                new EmailContainsKeywordsPredicate(emailList),
                new TagsContainsKeywordsPredicate(tagList),
                new RentalInformationContainsKeywordsPredicate(keywordList));
    }

    /**
     * Checks if all values of the prefix are valid keywords in the given {@code ArgumentMultimap}
     * and adds all of them to the given {@code List}.
     */
    private void checkValuesAndAdd(ArgumentMultimap argMultimap, Prefix prefix,
                                   List<String> list) throws ParseException {
        List<String> keywords = argMultimap.getAllValues(prefix);
        List<String> trimmedKeywords = keywords.stream().map(String::trim).toList();

        if (!trimmedKeywords.stream().allMatch(FindCommandParser::isValidKeyword)) {
            throw new ParseException(MESSAGE_INVALID_KEYWORD);
        }

        list.addAll(trimmedKeywords);
    }

    /**
     * Calls the {@link #checkValuesAndAdd(ArgumentMultimap, Prefix, List) checkValuesAndAdd} method on each
     * {@code Prefix} in {@code prefixes}, and the list at the same index in {@code lists}.
     */
    private void checkValuesAndAdd(ArgumentMultimap argMultimap, List<Prefix> prefixes,
                                   List<List<String>> lists) throws ParseException {
        assert (prefixes.size() == lists.size());
        for (int i = 0; i < prefixes.size(); ++i) {
            checkValuesAndAdd(argMultimap, prefixes.get(i), lists.get(i));
        }
    }

    private static boolean isValidKeyword(String keyword) {
        return keyword.matches(VALIDATION_REGEX);
    }

    /**
     * Adds all elements in {@code listToAppend} to all given {@code lists}.
     */
    private static <T> void appendAll(List<List<T>> lists, List<T> listToAppend) {
        for (List<T> list : lists) {
            list.addAll(listToAppend);
        }
    }

    /**
     * Returns true if all the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean noPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }

}
