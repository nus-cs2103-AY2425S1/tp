package seedu.address.model.person;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tests that a {@code Person} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private boolean isClearAll = false;

    // Identity fields
    private final List<String> nameKeywords = new ArrayList<>();
    private final List<String> phoneKeywords = new ArrayList<>();
    private final List<String> emailKeywords = new ArrayList<>();

    // Data fields
    private final List<String> addressKeywords = new ArrayList<>();
    private final List<String> tagsKeywords = new ArrayList<>();

    // TODO: Missing keywords lists for subject and classes

    /**
     * Constructs a {@code PersonContainsKeywordsPredicate}
     *
     * @param searchQuery A list representing the search or filter query by the user
     * @throws ParseException If a non-empty search query was given that didn't fit the categories
     */
    public PersonContainsKeywordsPredicate(List<String> searchQuery) throws ParseException {
        if (searchQuery.get(0).isEmpty()) {
            isClearAll = true;
            return;
        }

        Map<Prefix, List<String>> keywordMap = new HashMap<>();
        keywordMap.put(PREFIX_NAME, nameKeywords);
        keywordMap.put(PREFIX_PHONE, phoneKeywords);
        keywordMap.put(PREFIX_EMAIL, emailKeywords);
        keywordMap.put(PREFIX_ADDRESS, addressKeywords);
        keywordMap.put(PREFIX_TAG, tagsKeywords);

        List<String> currentList = null;

        for (String keyword : searchQuery) {
            if (keywordMap.containsKey(new Prefix(keyword))) {
                currentList = keywordMap.get(new Prefix(keyword));
            } else if (currentList != null) {
                currentList.add(keyword);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
            }
        }
    }

    @Override
    public boolean test(Person person) {
        if (isClearAll) {
            return true;
        }

        boolean hasMatchingName = nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));

        boolean hasMatchingPhone = phoneKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));

        boolean hasMatchingEmail = emailKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));

        boolean hasMatchingAddress = addressKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));

        boolean hasMatchingTags = tagsKeywords.stream()
                .anyMatch(keyword -> person.getTags().stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));

        return hasMatchingName || hasMatchingPhone || hasMatchingEmail || hasMatchingAddress || hasMatchingTags;
    }
}
