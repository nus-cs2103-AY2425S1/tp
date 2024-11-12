package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Organization} matches any of the keywords given.
 */
public class OrgContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;
    private final Boolean returnAllTrue;

    /**
     * Initialises keywords and if it is empty then ensure predicate always returns true
     * @param keywords
     */
    public OrgContainsKeywordsPredicate(List<String> keywords) {

        this.keywords = keywords;
        this.returnAllTrue = keywords.size() == 1 && keywords.get(0).trim().isEmpty();
    }

    @Override
    public boolean test(Person person) {
        return returnAllTrue || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getOrganisation().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrgContainsKeywordsPredicate)) {
            return false;
        }

        OrgContainsKeywordsPredicate otherOrgContainsKeywordsPredicate = (OrgContainsKeywordsPredicate) other;
        return keywords.equals(otherOrgContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

