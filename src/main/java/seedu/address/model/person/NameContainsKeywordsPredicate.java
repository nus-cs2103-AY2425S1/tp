package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a {@code NameContainsKeywordsPredicate}.
     *
     * @param keywords A list of keywords used to check the address book.
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Checks all keywords for invalid characters
     */
    public static boolean areValidNameKeywords(List<String> keywords) {
        for (String s : keywords) {
            if (!isValidKeyword(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the name keyword is a valid and possible name in the address book,
     * or if there is no keyword, since name is an optional param
     *
     * @param keyword the name keyword used to search the list.
     */
    private static boolean isValidKeyword(String keyword) {
        return keyword.isEmpty() || Name.isValidName(keyword);
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream().filter(word -> !word.isEmpty())
                .anyMatch(keyword -> StringUtil.containsCharactersInWordIgnoreCase(person.getFullName(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
